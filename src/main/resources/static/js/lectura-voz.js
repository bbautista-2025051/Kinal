(function () {
    'use strict';

    var btnMic    = document.getElementById('btnMic');
    var micIco    = document.getElementById('micIco');
    var micTxt    = document.getElementById('micTxt');
    var voiceMsg  = document.getElementById('voiceMsg');
    var covFill   = document.getElementById('covFill');
    var covPct    = document.getElementById('covPct');
    var svFill    = document.getElementById('sideVoiceFill');
    var svPct     = document.getElementById('sideVoicePct');
    var lockBox   = document.getElementById('lockBox');
    var lockMsg   = document.getElementById('lockMsg');
    var readyBox  = document.getElementById('readyBox');
    var testBtn   = document.getElementById('startTestBtn');
    var scrollHint= document.getElementById('scrollHint');
    var contentEl = document.getElementById('readingContent');

    if (!btnMic || !contentEl) return;

    /* ── Verificar soporte ── */
    var SR = window.SpeechRecognition || window.webkitSpeechRecognition;
    if (!SR) {
        btnMic.disabled = true;
        btnMic.style.opacity = '0.4';
        setMsg('⛔ Tu navegador no soporta reconocimiento de voz. Usa Google Chrome.');
        return;
    }

    /* Avisar si el micrófono ya está bloqueado */
    if (navigator.permissions) {
        navigator.permissions.query({ name: 'microphone' }).then(function(r) {
            if (r.state === 'denied') {
                btnMic.style.opacity = '0.6';
                setMsg('⚠️ Micrófono bloqueado. Haz clic en 🔒 junto a la URL → Micrófono → Permitir → recarga.');
            }
            r.onchange = function() {
                if (r.state === 'granted') {
                    btnMic.style.opacity = '1';
                    setMsg('Presiona Iniciar y lee el texto en voz alta');
                }
            };
        }).catch(function(){});
    }

    /* ── Preparar spans ── */
    var raw = contentEl.textContent;
    var wi  = 0;
    contentEl.innerHTML = raw.split(/(\s+)/).map(function(p) {
        if (/^\s*$/.test(p)) return p.replace(/\n/g, '<br>');
        return '<span class="rw" data-i="' + (wi++) + '">' +
               p.replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;') + '</span>';
    }).join('');

    var spans     = Array.from(contentEl.querySelectorAll('.rw'));
    var total     = spans.length;
    var okBits    = new Array(total).fill(false);
    var coverage  = 0;
    var cursor    = 0;   /* próxima posición esperada en el texto */

    /* Normalizar: minúsculas, sin tildes, solo alfanumérico */
    function norm(s) {
        return s.toLowerCase()
            .normalize('NFD').replace(/[\u0300-\u036f]/g, '')
            .replace(/[^a-z0-9]/g, '');
    }
    var spanNorms = spans.map(function(sp) { return norm(sp.textContent); });

    /* ── Estado ── */
    var active       = false;
    var rec          = null;
    var reiniciando  = false;

    /*
     * lastInterimCursor: guardamos dónde estaba el cursor cuando llegó
     * el último resultado interim. Al llegar el resultado FINAL de esa
     * misma frase, restauramos el cursor a ese punto y procesamos de nuevo
     * con el texto definitivo (más preciso). Así evitamos que los interim
     * "consuman" posiciones que el resultado final corregiría.
     */
    var cursorAntesDeFrase = 0;
    var okBitsSnapshot     = null;   /* copia de okBits antes de cada frase */

    function setMsg(t) { if (voiceMsg) voiceMsg.textContent = t; }

    function resetBtn() {
        btnMic.classList.remove('active');
        if (micIco) micIco.className   = 'fa fa-microphone';
        if (micTxt) micTxt.textContent = 'Iniciar';
        btnMic.disabled = false;
    }
    function setActiveBtn() {
        btnMic.classList.add('active');
        if (micIco) micIco.className   = 'fa fa-stop';
        if (micTxt) micTxt.textContent = 'Detener';
        btnMic.disabled = false;
    }

    /* ── Solicitar permiso ── */
    function pedirPermisoYArrancar() {
        setMsg('⏳ Solicitando permiso… acepta el diálogo del navegador.');
        if (navigator.permissions) {
            navigator.permissions.query({ name: 'microphone' })
                .then(function(r) {
                    if (r.state === 'denied') {
                        active = false; resetBtn();
                        setMsg('⛔ Micrófono bloqueado. Haz clic en 🔒 junto a la URL → Micrófono → Permitir → recarga.');
                        return;
                    }
                    solicitarMicrofono();
                }).catch(solicitarMicrofono);
        } else {
            solicitarMicrofono();
        }
    }

    function solicitarMicrofono() {
        navigator.mediaDevices.getUserMedia({ audio: true, video: false })
            .then(function(stream) {
                stream.getTracks().forEach(function(t) { t.stop(); });
                setMsg('✅ Permiso concedido. Iniciando…');
                startRec();
            })
            .catch(function(err) {
                active = false; resetBtn();
                if (err.name === 'NotAllowedError' || err.name === 'PermissionDeniedError')
                    setMsg('⛔ Permiso denegado. Haz clic en 🔒 junto a la URL → Micrófono → Permitir → recarga.');
                else if (err.name === 'NotFoundError')
                    setMsg('⛔ No se detectó micrófono. Conecta uno e intenta de nuevo.');
                else if (err.name === 'NotReadableError')
                    setMsg('⛔ El micrófono está en uso por otra aplicación. Ciérrala e intenta.');
                else
                    setMsg('⛔ Error: ' + err.name + '. Recarga la página.');
            });
    }

    /* ─────────────────────────────────────────────────────────────────
     * ESTRATEGIA DE RECONOCIMIENTO EN TIEMPO REAL
     *
     * Problema con continuous=true:
     *   Chrome acumula 5-10 segundos de audio antes de emitir un
     *   resultado, lo que causa el retraso que se nota.
     *
     * Solución — sesiones cortas (non-continuous):
     *   Cada sesión dura hasta que el motor emite su primer resultado
     *   final (normalmente 1-3 segundos de habla). Al recibir ese
     *   resultado, paramos y arrancamos una nueva sesión de inmediato.
     *   Esto da resultados cada 1-3 segundos en lugar de cada 8-10.
     *
     * Procesamiento de interim:
     *   Los resultados interim se procesan para marcar palabras
     *   visualmente EN TIEMPO REAL mientras el usuario habla.
     *   Antes de aplicarlos, guardamos una "foto" del estado actual
     *   (cursor + okBits). Cuando llega el resultado FINAL (más preciso),
     *   restauramos esa foto y re-procesamos con el texto definitivo,
     *   corrigiendo cualquier error del interim.
     * ───────────────────────────────────────────────────────────────── */
    function startRec() {
        if (!active || reiniciando) return;

        rec = new SR();
        rec.lang            = 'es-GT';
        rec.continuous      = false;   /* sesiones cortas = resultados frecuentes */
        rec.interimResults  = true;    /* marcar palabras en tiempo real */
        rec.maxAlternatives = 3;       /* más alternativas = mejor cobertura */

        /* Al iniciar cada sesión guardamos el estado para poder revertir
           si el interim marcó algo que el final corrija */
        cursorAntesDeFrase = cursor;
        okBitsSnapshot     = okBits.slice();

        rec.onstart = function() {
            setActiveBtn();
            setMsg('🎤 Escuchando… lee en voz alta');
        };

        rec.onresult = function(e) {
            for (var i = e.resultIndex; i < e.results.length; i++) {
                var resultado = e.results[i];

                if (!resultado.isFinal) {
                    /*
                     * INTERIM: marcar en tiempo real.
                     * Revertimos al estado previo a la frase y re-aplicamos
                     * el interim desde ahí, para no acumular marcas falsas.
                     */
                    cursor = cursorAntesDeFrase;
                    okBits = okBitsSnapshot.slice();
                    /* Limpiar marcas .ok que no estaban antes */
                    spans.forEach(function(s, idx) {
                        if (!okBits[idx]) s.classList.remove('ok');
                        s.classList.remove('now');
                    });
                    markWords(resultado[0].transcript, false);
                    setMsg('🎤 "' + resultado[0].transcript.trim() + '"');

                } else {
                    /*
                     * FINAL: resultado definitivo y más preciso.
                     * Revertimos al snapshot y aplicamos el final limpio.
                     * Procesamos todas las alternativas.
                     */
                    cursor = cursorAntesDeFrase;
                    okBits = okBitsSnapshot.slice();
                    spans.forEach(function(s, idx) {
                        if (!okBits[idx]) s.classList.remove('ok');
                        s.classList.remove('now');
                    });
                    for (var a = 0; a < resultado.length; a++) {
                        markWords(resultado[a].transcript, true);
                    }
                    setMsg('🎤 Escuchando… lee en voz alta');

                    /* Actualizar snapshot para la siguiente frase */
                    cursorAntesDeFrase = cursor;
                    okBitsSnapshot     = okBits.slice();
                }
            }
        };

        rec.onerror = function(e) {
            if (e.error === 'not-allowed' || e.error === 'service-not-allowed') {
                active = false; resetBtn();
                setMsg('⛔ Permiso denegado. Recarga y permite el micrófono.');
            }
            /* 'no-speech', 'aborted', 'network': se reinicia solo con onend */
        };

        rec.onend = function() {
            if (!active) { resetBtn(); setMsg('Detenido.'); return; }
            /* Reiniciar de inmediato para capturar la siguiente frase */
            reiniciando = false;
            setTimeout(startRec, 30);
        };

        reiniciando = true;
        try {
            rec.start();
            reiniciando = false;
        } catch(ex) {
            reiniciando = false;
            setTimeout(startRec, 200);
        }
    }

    /* ── Botón ── */
    btnMic.addEventListener('click', function() {
        if (!active) {
            active = true;
            btnMic.disabled = true;
            pedirPermisoYArrancar();
        } else {
            active = false;
            reiniciando = false;
            try { if (rec) rec.stop(); } catch(ex) {}
            resetBtn();
            setMsg('Detenido.');
        }
    });

    /* ─────────────────────────────────────────────────────────────────
     * ALGORITMO DE MARCADO — coincidencia secuencial con ventana
     *
     * Recorre las palabras dichas en orden y las busca en el texto
     * desde el cursor hacia adelante (ventana de 40 palabras).
     * El cursor solo avanza, nunca retrocede.
     *
     * commitFinal=false → modo interim (visual, reversible)
     * commitFinal=true  → modo final (definitivo)
     * ───────────────────────────────────────────────────────────────── */
    var VENTANA = 40;

    function markWords(transcript, commitFinal) {
        var spoken  = transcript.trim().split(/\s+/).map(norm).filter(Boolean);
        var changed = false;

        for (var si = 0; si < spoken.length; si++) {
            var word   = spoken[si];
            if (!word) continue;

            var limite    = Math.min(cursor + VENTANA, total);
            var encontrado = -1;

            for (var ti = cursor; ti < limite; ti++) {
                if (okBits[ti]) continue;
                if (spanNorms[ti] === word) { encontrado = ti; break; }
            }

            if (encontrado !== -1) {
                okBits[encontrado] = true;
                spans[encontrado].classList.add('ok');
                spans.forEach(function(s) { s.classList.remove('now'); });

                /* Señalar la próxima palabra pendiente */
                var sig = encontrado + 1;
                while (sig < total && okBits[sig]) sig++;
                if (sig < total) spans[sig].classList.add('now');

                cursor  = encontrado + 1;
                changed = true;
            }
        }

        if (changed) refreshCoverage();
    }

    function refreshCoverage() {
        var count = okBits.filter(Boolean).length;
        coverage  = Math.round((count / total) * 100);
        if (covFill) covFill.style.width = coverage + '%';
        if (covPct)  covPct.textContent  = coverage + '%';
        if (svFill)  svFill.style.width  = coverage + '%';
        if (svPct)   svPct.textContent   = coverage + '%';
        if (lockMsg && coverage < 60)
            lockMsg.textContent = 'Lee en voz alta el 60% (' + coverage + '% reconocido)';
        checkUnlock();
    }

    var unlocked = false;
    function checkUnlock() {
        if (unlocked || coverage < 60) return;
        unlocked = true;
        active   = false;
        reiniciando = false;
        try { if (rec) rec.stop(); } catch(e) {}
        resetBtn();
        if (lockBox)    lockBox.style.display  = 'none';
        if (readyBox)   readyBox.style.display = 'flex';
        if (testBtn)    testBtn.classList.remove('locked');
        if (scrollHint) {
            scrollHint.textContent = '✓ Lectura verificada';
            scrollHint.style.color = '#198754';
        }
        setMsg('✅ ¡60% alcanzado! El test está desbloqueado.');
    }

    if (testBtn) {
        testBtn.addEventListener('click', function(e) {
            e.preventDefault();
            if (this.classList.contains('locked')) return;
            active = false; reiniciando = false;
            try { if (rec) rec.stop(); } catch(ex) {}
            document.cookie = 'lecturaMs=' + (window._lecturaElapsed || 0) + ';path=/;SameSite=Strict';
            window.location.href = this.href;
        });
    }

})();
