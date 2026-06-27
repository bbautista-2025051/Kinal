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

    var SR = window.SpeechRecognition || window.webkitSpeechRecognition;
    if (!SR) {
        btnMic.disabled = true;
        btnMic.style.opacity = '0.4';
        setMsg('⛔ Tu navegador no soporta reconocimiento de voz. Usa Google Chrome.');
        return;
    }

    if (navigator.permissions) {
        navigator.permissions.query({ name: 'microphone' }).then(function(r) {
            if (r.state === 'denied') {
                btnMic.style.opacity = '0.6';
                setMsg('⚠️ Micrófono bloqueado. Haz clic en 🔒 junto a la URL → Micrófono → Permitir → recarga.');
            }
            r.onchange = function() {
                if (r.state === 'granted') { btnMic.style.opacity = '1'; setMsg('Presiona Iniciar y lee el texto en voz alta'); }
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

    var spans  = Array.from(contentEl.querySelectorAll('.rw'));
    var total  = spans.length;

    function norm(s) {
        return s.toLowerCase()
            .normalize('NFD').replace(/[\u0300-\u036f]/g, '')
            .replace(/[^a-z0-9]/g, '');
    }
    var spanNorms = spans.map(function(sp) { return norm(sp.textContent); });

    /*
     * ═══════════════════════════════════════════════════════════════
     * ESTADO DE LA LECTURA
     *
     * cursor   → índice de la PRÓXIMA palabra que esperamos escuchar.
     *            Avanza de uno en uno, estrictamente en orden.
     *
     * okBits[] → palabras definitivamente confirmadas (resultado final).
     *
     * La única forma de avanzar el cursor es que la palabra en
     * spanNorms[cursor] coincida con la primera palabra del transcript.
     * Si no coincide, el cursor NO avanza — el usuario debe repetir.
     * ═══════════════════════════════════════════════════════════════
     */
    var cursor   = 0;
    var okBits   = new Array(total).fill(false);
    var coverage = 0;

    /* Snapshot del estado al inicio de cada frase (para revertir interims) */
    var snapCursor = 0;
    var snapBits   = null;

    var active      = false;
    var rec         = null;
    var reiniciando = false;

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

    /* ── Permisos ── */
    function pedirPermisoYArrancar() {
        setMsg('⏳ Solicitando permiso… acepta el diálogo del navegador.');
        if (navigator.permissions) {
            navigator.permissions.query({ name: 'microphone' })
                .then(function(r) {
                    if (r.state === 'denied') { active = false; resetBtn(); setMsg('⛔ Micrófono bloqueado. Haz clic en 🔒 junto a la URL → Micrófono → Permitir → recarga.'); return; }
                    solicitarMicrofono();
                }).catch(solicitarMicrofono);
        } else { solicitarMicrofono(); }
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

    /* ── Motor de reconocimiento ── */
    function startRec() {
        if (!active || reiniciando) return;

        /* Guardar snapshot ANTES de cada frase */
        snapCursor = cursor;
        snapBits   = okBits.slice();

        rec = new SR();
        rec.lang            = 'es-GT';
        rec.continuous      = false;   /* sesiones cortas: resultados cada ~2 seg */
        rec.interimResults  = true;
        rec.maxAlternatives = 1;       /* UNA sola alternativa: evita saltos por alternativas erróneas */

        rec.onstart = function() { setActiveBtn(); setMsg('🎤 Escuchando… lee en voz alta'); };

        rec.onresult = function(e) {
            for (var i = e.resultIndex; i < e.results.length; i++) {
                var res = e.results[i];
                var transcript = res[0].transcript;

                if (!res.isFinal) {
                    /* INTERIM: mostrar visualmente, revertible */
                    aplicarTranscript(transcript, snapCursor, snapBits, false);
                    setMsg('🎤 "' + transcript.trim() + '"');
                } else {
                    /* FINAL: confirmar definitivamente desde el snapshot */
                    aplicarTranscript(transcript, snapCursor, snapBits, true);
                    setMsg('🎤 Escuchando… lee en voz alta');
                    /* Nuevo snapshot para la siguiente frase */
                    snapCursor = cursor;
                    snapBits   = okBits.slice();
                }
            }
        };

        rec.onerror = function(e) {
            if (e.error === 'not-allowed' || e.error === 'service-not-allowed') {
                active = false; resetBtn();
                setMsg('⛔ Permiso denegado. Recarga y permite el micrófono.');
            }
        };

        rec.onend = function() {
            if (!active) { resetBtn(); setMsg('Detenido.'); return; }
            reiniciando = false;
            setTimeout(startRec, 30);
        };

        reiniciando = true;
        try { rec.start(); reiniciando = false; }
        catch(ex) { reiniciando = false; setTimeout(startRec, 200); }
    }

    /* ── Botón ── */
    btnMic.addEventListener('click', function() {
        if (!active) {
            active = true;
            btnMic.disabled = true;
            pedirPermisoYArrancar();
        } else {
            active = false; reiniciando = false;
            try { if (rec) rec.stop(); } catch(ex) {}
            resetBtn(); setMsg('Detenido.');
        }
    });

    /*
     * ═══════════════════════════════════════════════════════════════
     * ALGORITMO DE SECUENCIA ESTRICTA
     *
     * Regla fundamental: cada palabra dicha debe coincidir con la
     * palabra en `cursor` o en las SIGUIENTES 3 posiciones como máximo
     * (tolerancia mínima para palabras que el motor omite/confunde).
     *
     * Si la palabra NO coincide en esa ventana pequeña → se descarta.
     * El cursor avanza SOLO cuando hay coincidencia.
     *
     * TOLERANCIA = 3: si el motor se "come" 1-2 palabras cortas como
     * artículos ("el", "la", "de") podemos saltar sobre ellas.
     * Pero NO permite saltar hasta posiciones lejanas del texto como
     * antes hacía la ventana de 40, que rompía el orden.
     *
     * isFinal=false → modo interim: usamos fromCursor/fromBits del
     *                 snapshot para no contaminar el estado real.
     * isFinal=true  → confirma y actualiza cursor/okBits globales.
     * ═══════════════════════════════════════════════════════════════
     */
    var TOLERANCIA = 3;   /* palabras que podemos saltar si el motor las omite */

    function aplicarTranscript(transcript, fromCursor, fromBits, isFinal) {
        var spoken = transcript.trim().split(/\s+/).map(norm).filter(Boolean);
        if (!spoken.length) return;

        /* Trabajamos sobre copias locales para no mutar el estado si es interim */
        var cur  = fromCursor;
        var bits = fromBits.slice();
        var changed = false;

        for (var si = 0; si < spoken.length; si++) {
            var word = spoken[si];
            if (!word) continue;

            /*
             * Buscar la palabra SOLO dentro de [cur, cur + TOLERANCIA].
             * Si no está ahí, descartamos esta palabra del transcript
             * (el usuario dijo algo fuera de orden o el motor lo inventó).
             */
            var limite    = Math.min(cur + TOLERANCIA + 1, total);
            var encontrado = -1;

            for (var ti = cur; ti < limite; ti++) {
                if (bits[ti]) continue;            /* ya marcada */
                if (spanNorms[ti] === word) { encontrado = ti; break; }
            }

            if (encontrado !== -1) {
                bits[encontrado] = true;
                cur = encontrado + 1;
                changed = true;
            }
            /* Si no encontrado: ignorar esta palabra, cursor NO avanza */
        }

        /* Aplicar cambios visuales */
        if (changed || isFinal) {
            /* Sincronizar clases .ok */
            spans.forEach(function(s, idx) {
                if (bits[idx]) s.classList.add('ok');
                else           s.classList.remove('ok');
                s.classList.remove('now');
            });
            /* Resaltar la próxima palabra pendiente */
            if (cur < total) spans[cur].classList.add('now');

            if (isFinal) {
                /* Confirmar estado global */
                cursor = cur;
                okBits = bits;
                refreshCoverage();
            } else {
                /* Solo visual, no toca cursor/okBits globales */
                refreshCoverageVisual(bits);
            }
        }
    }

    function refreshCoverage() {
        var count = okBits.filter(Boolean).length;
        coverage  = Math.round((count / total) * 100);
        actualizarUI(coverage);
        checkUnlock();
    }

    function refreshCoverageVisual(bits) {
        /* Para interims: actualiza barras visualmente sin modificar coverage real */
        var count = bits.filter(Boolean).length;
        var pct   = Math.round((count / total) * 100);
        actualizarUI(pct);
    }

    function actualizarUI(pct) {
        if (covFill) covFill.style.width = pct + '%';
        if (covPct)  covPct.textContent  = pct + '%';
        if (svFill)  svFill.style.width  = pct + '%';
        if (svPct)   svPct.textContent   = pct + '%';
        if (lockMsg && pct < 100)
            lockMsg.textContent = 'Lee en voz alta el texto completo (' + pct + '% reconocido)';
    }

    var unlocked = false;
    function checkUnlock() {
        if (unlocked || coverage < 100) return;
        unlocked = true;
        active   = false; reiniciando = false;
        try { if (rec) rec.stop(); } catch(e) {}
        resetBtn();
        if (lockBox)    lockBox.style.display  = 'none';
        if (readyBox)   readyBox.style.display = 'flex';
        if (testBtn)    testBtn.classList.remove('locked');
        if (scrollHint) { scrollHint.textContent = '✓ Lectura completa verificada'; scrollHint.style.color = '#198754'; }
        setMsg('✅ ¡Lectura completa! El test está desbloqueado.');
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
