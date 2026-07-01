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

    /* ─────────────────────────────────────────────────────────────────
     * NORMALIZACIÓN
     * Elimina tildes, puntuación, convierte a minúsculas.
     * ───────────────────────────────────────────────────────────────── */
    function norm(s) {
        return s.toLowerCase()
            .normalize('NFD').replace(/[\u0300-\u036f]/g, '')
            .replace(/[^a-z0-9]/g, '');
    }

    var spanNorms = spans.map(function(sp) { return norm(sp.textContent); });

    /* ─────────────────────────────────────────────────────────────────
     * DISTANCIA DE LEVENSHTEIN (edición)
     * Cuántos caracteres hay que cambiar/insertar/borrar para
     * transformar una cadena en otra. Permite detectar palabras mal
     * reconocidas fonéticamente.
     *
     * Ejemplo: "electromotris" vs "electromotriz" → distancia 1 ✓
     *          "westing"       vs "westinghouse"  → distancia 5 ✗
     * ───────────────────────────────────────────────────────────────── */
    function levenshtein(a, b) {
        if (a === b) return 0;
        if (!a.length) return b.length;
        if (!b.length) return a.length;
        var la = a.length, lb = b.length;
        /* Limitar cómputo para palabras muy largas */
        if (Math.abs(la - lb) > 4) return 99;
        var prev = [], curr = [];
        for (var j = 0; j <= lb; j++) prev[j] = j;
        for (var i = 1; i <= la; i++) {
            curr[0] = i;
            for (var k = 1; k <= lb; k++) {
                curr[k] = a[i-1] === b[k-1]
                    ? prev[k-1]
                    : 1 + Math.min(prev[k-1], prev[k], curr[k-1]);
            }
            var tmp = prev; prev = curr; curr = tmp;
        }
        return prev[lb];
    }

    /* ─────────────────────────────────────────────────────────────────
     * FUNCIÓN DE COINCIDENCIA
     * Para palabras cortas (≤4 chars): coincidencia exacta.
     * Para palabras medias (5-7 chars): toleramos 1 error de edición.
     * Para palabras largas (≥8 chars): toleramos hasta 2 errores.
     *
     * Esto cubre errores comunes del motor:
     *   - "electromotriz" → "electromotris"  (1 error)
     *   - "disyuntores"   → "disyuntores"    (0 errores)
     *   - "conductores"   → "conductore"     (1 error)
     *   - "instalaciones" → "instalacion"    (2 errores)
     * ───────────────────────────────────────────────────────────────── */
    function coincide(spoken, target) {
        if (spoken === target) return true;
        var ls = spoken.length, lt = target.length;
        /* Palabras muy cortas: exacto */
        if (lt <= 4) return spoken === target;
        /* Palabras medias: 1 error */
        if (lt <= 7) return levenshtein(spoken, target) <= 1;
        /* Palabras largas: 2 errores */
        return levenshtein(spoken, target) <= 2;
    }

    /* Estado */
    var cursor   = 0;
    var okBits   = new Array(total).fill(false);
    var coverage = 0;
    var snapCursor = 0;
    var snapBits   = null;
    var active      = false;
    var rec         = null;
    var reiniciando = false;

    /* ── Auto-avance: si el cursor no se mueve en 4 s, saltar esa palabra ── */
    var autoAvanceTimer  = null;
    var lastCursorChange = Date.now();

    function resetAutoAvance() {
        lastCursorChange = Date.now();
        clearTimeout(autoAvanceTimer);
        if (!active) return;
        autoAvanceTimer = setTimeout(function saltarPalabra() {
            if (!active) return;
            if (cursor < total && Date.now() - lastCursorChange >= 3800) {
                /* Marcar palabra como superada y avanzar */
                okBits[cursor] = true;
                cursor++;
                /* Saltar también artículos cortos seguidos */
                while (cursor < total && spanNorms[cursor].length <= 2) {
                    okBits[cursor] = true;
                    cursor++;
                }
                snapCursor = cursor;
                snapBits   = okBits.slice();
                renderBits(okBits, cursor);
                refreshCoverage();
                lastCursorChange = Date.now();
            }
            autoAvanceTimer = setTimeout(saltarPalabra, 4000);
        }, 4000);
    }

    /* ── Desbloqueo por scroll: si llegó al final, desbloquear con >= 70% ── */
    var scrollAlFinal = false;
    window.addEventListener('scroll', function () {
        if (unlocked) return;
        var content = document.getElementById('readingContent');
        if (!content) return;
        var rect    = content.getBoundingClientRect();
        var total_h = content.offsetHeight - window.innerHeight;
        var scrolled = Math.max(0, -rect.top);
        var pct = total_h > 0 ? (scrolled / (total_h + 150)) * 100 : 100;
        if (pct >= 98 && !scrollAlFinal) {
            scrollAlFinal = true;
            if (coverage >= 70) {
                desbloquearTest('scroll');
            }
        }
    });

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
                    if (r.state === 'denied') {
                        active = false; resetBtn();
                        setMsg('⛔ Micrófono bloqueado. Haz clic en 🔒 junto a la URL → Micrófono → Permitir → recarga.');
                        return;
                    }
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

        snapCursor = cursor;
        snapBits   = okBits.slice();

        rec = new SR();
        rec.lang            = 'es-GT';
        rec.continuous      = false;
        rec.interimResults  = true;
        rec.maxAlternatives = 3;   /* 3 alternativas: procesamos todas para mayor cobertura */

        rec.onstart = function() { setActiveBtn(); setMsg('🎤 Escuchando… lee en voz alta'); resetAutoAvance(); };

        rec.onresult = function(e) {
            for (var i = e.resultIndex; i < e.results.length; i++) {
                var res = e.results[i];

                if (!res.isFinal) {
                    /* Interim: solo la alternativa más probable */
                    aplicarTranscript(res[0].transcript, snapCursor, snapBits, false);
                    setMsg('🎤 "' + res[0].transcript.trim() + '"');
                } else {
                    /*
                     * Final: intentar con cada alternativa desde el snapshot.
                     * Nos quedamos con la que avanza MÁS el cursor.
                     * Esto permite que si la alternativa 0 no reconoce
                     * "electromotriz" pero la alternativa 1 la dice diferente
                     * y coincide fonéticamente, igual se marca.
                     */
                    var mejorCursor = snapCursor;
                    var mejorBits   = snapBits.slice();

                    for (var a = 0; a < res.length; a++) {
                        var result = aplicarTranscriptSilencioso(
                            res[a].transcript, snapCursor, snapBits.slice()
                        );
                        /* Elegir la alternativa que avanzó más */
                        if (result.cur > mejorCursor) {
                            mejorCursor = result.cur;
                            mejorBits   = result.bits;
                        }
                    }

                    /* Aplicar la mejor alternativa */
                    cursor = mejorCursor;
                    okBits = mejorBits;
                    renderBits(okBits, cursor);
                    refreshCoverage();
                    /* Reiniciar temporizador de auto-avance si el cursor avanzó */
                    if (mejorCursor > snapCursor) resetAutoAvance();

                    snapCursor = cursor;
                    snapBits   = okBits.slice();
                    setMsg('🎤 Escuchando… lee en voz alta');
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
            clearTimeout(autoAvanceTimer);
            try { if (rec) rec.stop(); } catch(ex) {}
            resetBtn(); setMsg('Detenido.');
        }
    });

    /* ─────────────────────────────────────────────────────────────────
     * ALGORITMO DE MARCADO — secuencial con similitud fonética
     *
     * VENTANA = 6: permite saltar hasta 6 posiciones para tolerar
     * artículos omitidos ("el", "la", "de", "y", "a", "en") que el
     * motor frecuentemente se come.
     *
     * La coincidencia NO es exacta: usa la función `coincide()` que
     * aplica distancia de Levenshtein proporcional al largo de la palabra.
     * ───────────────────────────────────────────────────────────────── */
    var VENTANA = 6;

    function aplicarTranscriptSilencioso(transcript, fromCursor, fromBits) {
        var spoken = transcript.trim().split(/\s+/).map(norm).filter(Boolean);
        var cur  = fromCursor;
        var bits = fromBits;

        for (var si = 0; si < spoken.length; si++) {
            var word   = spoken[si];
            if (!word) continue;
            var limite = Math.min(cur + VENTANA + 1, total);
            var encontrado = -1;

            for (var ti = cur; ti < limite; ti++) {
                if (bits[ti]) continue;
                if (coincide(word, spanNorms[ti])) { encontrado = ti; break; }
            }

            if (encontrado !== -1) {
                bits[encontrado] = true;
                cur = encontrado + 1;
            }
        }
        return { cur: cur, bits: bits };
    }

    function aplicarTranscript(transcript, fromCursor, fromBits, isFinal) {
        var result = aplicarTranscriptSilencioso(transcript, fromCursor, fromBits.slice());

        if (isFinal) {
            cursor = result.cur;
            okBits = result.bits;
            renderBits(okBits, cursor);
            refreshCoverage();
        } else {
            renderBits(result.bits, result.cur);
            refreshCoverageVisual(result.bits);
        }
    }

    function renderBits(bits, cur) {
        spans.forEach(function(s, idx) {
            if (bits[idx]) s.classList.add('ok');
            else           s.classList.remove('ok');
            s.classList.remove('now');
        });
        if (cur < total) spans[cur].classList.add('now');
    }

    function refreshCoverage() {
        var count = okBits.filter(Boolean).length;
        coverage  = Math.round((count / total) * 100);
        actualizarUI(coverage);
        checkUnlock();
    }

    function refreshCoverageVisual(bits) {
        var count = bits.filter(Boolean).length;
        actualizarUI(Math.round((count / total) * 100));
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
        /* Desbloqueo por reconocimiento: 100% */
        if (!unlocked && coverage >= 100) desbloquearTest('voz');
        /* Desbloqueo por scroll + voz: llegó al final con >= 70% */
        if (!unlocked && scrollAlFinal && coverage >= 70) desbloquearTest('scroll');
    }

    function desbloquearTest(motivo) {
        if (unlocked) return;
        unlocked = true;
        active   = false; reiniciando = false;
        clearTimeout(autoAvanceTimer);
        try { if (rec) rec.stop(); } catch(e) {}
        resetBtn();
        if (lockBox)    lockBox.style.display  = 'none';
        if (readyBox)   readyBox.style.display = 'flex';
        if (testBtn)    testBtn.classList.remove('locked');
        var msg = motivo === 'scroll'
            ? '✓ Lectura completada — test desbloqueado'
            : '✓ Lectura completa verificada';
        if (scrollHint) { scrollHint.textContent = msg; scrollHint.style.color = '#198754'; }
        setMsg('✅ ¡Test desbloqueado! Ya puedes comenzar.');
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
