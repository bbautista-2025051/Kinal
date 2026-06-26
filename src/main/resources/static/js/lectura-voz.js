(function () {
    'use strict';

    var btnMic     = document.getElementById('btnMic');
    var micIco     = document.getElementById('micIco');
    var micTxt     = document.getElementById('micTxt');
    var voiceMsg   = document.getElementById('voiceMsg');
    var covFill    = document.getElementById('covFill');
    var covPct     = document.getElementById('covPct');
    var svFill     = document.getElementById('sideVoiceFill');
    var svPct      = document.getElementById('sideVoicePct');
    var lockBox    = document.getElementById('lockBox');
    var lockMsg    = document.getElementById('lockMsg');
    var readyBox   = document.getElementById('readyBox');
    var testBtn    = document.getElementById('startTestBtn');
    var scrollHint = document.getElementById('scrollHint');
    var contentEl  = document.getElementById('readingContent');

    if (!btnMic || !contentEl) return;

    var SR = window.SpeechRecognition || window.webkitSpeechRecognition;
    if (!SR) {
        btnMic.disabled = true;
        btnMic.style.opacity = '0.4';
        if (voiceMsg) voiceMsg.textContent = 'Tu navegador no soporta reconocimiento de voz. Usa Google Chrome.';
        return;
    }

    /* ── Preparar spans de palabras ── */
    var raw   = contentEl.textContent;
    var wi    = 0;
    contentEl.innerHTML = raw.split(/(\s+)/).map(function (p) {
        if (/^\s*$/.test(p)) return p.replace(/\n/g, '<br>');
        return '<span class="rw" data-i="' + (wi++) + '">' +
               p.replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;') + '</span>';
    }).join('');

    var spans    = Array.from(contentEl.querySelectorAll('.rw'));
    var total    = spans.length;
    var okBits   = new Array(total).fill(false);
    var coverage = 0;
    var cursor   = 0;

    function norm(s) {
        return s.toLowerCase()
            .normalize('NFD').replace(/[\u0300-\u036f]/g, '')
            .replace(/[^a-z0-9]/g, '');
    }

    var idx = {};
    spans.forEach(function (sp, i) {
        var w = norm(sp.textContent);
        if (!w) return;
        if (!idx[w]) idx[w] = [];
        idx[w].push(i);
    });

    /* ── Estado ── */
    var rec         = null;
    var active      = false;
    var permGranted = false;   // true después de getUserMedia exitoso

    function setMsg(txt) { if (voiceMsg) voiceMsg.textContent = txt; }

    function resetBtn() {
        btnMic.classList.remove('active');
        if (micIco) micIco.className = 'fa fa-microphone';
        if (micTxt) micTxt.textContent = 'Iniciar';
    }

    function setActiveBtn() {
        btnMic.classList.add('active');
        if (micIco) micIco.className = 'fa fa-stop';
        if (micTxt) micTxt.textContent = 'Detener';
    }

    /* ── Iniciar reconocimiento (solo después de tener permiso) ── */
    function startRec() {
        if (!active) return;

        rec = new SR();
        rec.lang            = 'es-ES';
        rec.continuous      = true;
        rec.interimResults  = true;
        rec.maxAlternatives = 1;

        rec.onstart = function () {
            setActiveBtn();
            setMsg('🎤 Escuchando… lee el texto en voz alta');
        };

        rec.onresult = function (e) {
            var interim = '';
            for (var i = e.resultIndex; i < e.results.length; i++) {
                var t = e.results[i][0].transcript;
                if (e.results[i].isFinal) {
                    markWords(t);
                } else {
                    interim = t;
                }
            }
            if (interim) setMsg('🎤 "' + interim.trim() + '"');
        };

        rec.onerror = function (e) {
            /* no-speech y aborted son normales con continuous=true, ignorar */
            if (e.error === 'not-allowed' || e.error === 'service-not-allowed') {
                active = false;
                resetBtn();
                setMsg('⛔ Permiso denegado. Haz clic en el candado 🔒 de la barra de Chrome → Micrófono → Permitir → recarga.');
            }
        };

        rec.onend = function () {
            /* Si el usuario NO detuvo, reiniciar inmediatamente */
            if (active) {
                setTimeout(startRec, 100);
            } else {
                resetBtn();
                setMsg('Detenido. Las palabras verdes quedan guardadas.');
            }
        };

        try {
            rec.start();
        } catch (e) {
            /* InvalidStateError: ya hay una sesión activa — esperar y reintentar */
            setTimeout(startRec, 300);
        }
    }

    /* ── Pedir permiso explícito con getUserMedia primero ── */
    function requestPermissionAndStart() {
        if (permGranted) {
            /* Ya tenemos permiso, iniciar directo */
            active = true;
            setMsg('⏳ Iniciando micrófono…');
            startRec();
            return;
        }

        setMsg('⏳ Solicitando permiso de micrófono…');
        btnMic.disabled = true;

        navigator.mediaDevices.getUserMedia({ audio: true })
            .then(function (stream) {
                /* Detener el stream inmediatamente — solo lo necesitábamos para el permiso */
                stream.getTracks().forEach(function (t) { t.stop(); });
                permGranted = true;
                btnMic.disabled = false;
                active = true;
                setMsg('🎤 Escuchando… lee el texto en voz alta');
                startRec();
            })
            .catch(function (err) {
                btnMic.disabled = false;
                resetBtn();
                active = false;
                if (err.name === 'NotAllowedError' || err.name === 'PermissionDeniedError') {
                    setMsg('⛔ Permiso denegado. Haz clic en el candado 🔒 de la barra de Chrome → Micrófono → Permitir → recarga la página.');
                } else if (err.name === 'NotFoundError') {
                    setMsg('⛔ No se encontró micrófono. Conecta uno e intenta de nuevo.');
                } else {
                    setMsg('⛔ Error: ' + err.name + '. Recarga la página e intenta de nuevo.');
                }
            });
    }

    /* ── Clic del botón ── */
    btnMic.addEventListener('click', function () {
        if (!active) {
            requestPermissionAndStart();
        } else {
            active = false;
            try { if (rec) rec.stop(); } catch (ex) {}
        }
    });

    /* ── Marcar palabras ── */
    function markWords(transcript) {
        var spoken  = transcript.trim().split(/\s+/).map(norm).filter(Boolean);
        var WINDOW  = 80;
        var changed = false;

        spoken.forEach(function (word) {
            var positions = idx[word];
            if (!positions) return;
            var best = -1, bestD = Infinity;
            for (var j = 0; j < positions.length; j++) {
                var p = positions[j];
                if (p < cursor || p > cursor + WINDOW) continue;
                var d = p - cursor;
                if (d < bestD) { bestD = d; best = p; }
            }
            if (best !== -1 && !okBits[best]) {
                okBits[best] = true;
                spans[best].classList.add('ok');
                spans.forEach(function (s) { s.classList.remove('now'); });
                if (spans[best + 1]) spans[best + 1].classList.add('now');
                if (best >= cursor) cursor = best + 1;
                changed = true;
            }
        });

        if (changed) refreshCoverage();
    }

    function refreshCoverage() {
        var count = okBits.filter(Boolean).length;
        coverage  = Math.round((count / total) * 100);
        if (covFill) covFill.style.width  = coverage + '%';
        if (covPct)  covPct.textContent   = coverage + '%';
        if (svFill)  svFill.style.width   = coverage + '%';
        if (svPct)   svPct.textContent    = coverage + '%';
        if (lockMsg && coverage < 60)
            lockMsg.textContent = 'Lee en voz alta el 60% del texto (' + coverage + '% reconocido)';
        checkUnlock();
    }

    /* ── Desbloqueo ── */
    var unlocked = false;
    function checkUnlock() {
        if (unlocked || coverage < 60) return;
        unlocked = true;
        active = false;
        try { if (rec) rec.stop(); } catch (e) {}
        resetBtn();
        if (lockBox)    lockBox.style.display  = 'none';
        if (readyBox)   readyBox.style.display = 'flex';
        if (testBtn)    testBtn.classList.remove('locked');
        if (scrollHint) { scrollHint.textContent = '✓ Lectura verificada por voz'; scrollHint.style.color = '#198754'; }
        setMsg('✅ ¡60% alcanzado! El test está desbloqueado.');
    }

    /* ── Navegar al test ── */
    if (testBtn) {
        testBtn.addEventListener('click', function (e) {
            e.preventDefault();
            if (this.classList.contains('locked')) return;
            active = false;
            try { if (rec) rec.stop(); } catch (ex) {}
            document.cookie = 'lecturaMs=' + (window._lecturaElapsed || 0) + ';path=/;SameSite=Strict';
            window.location.href = this.href;
        });
    }

})();
