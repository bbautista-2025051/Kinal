/* lectura-voz.js — reconocimiento de voz para lectura.html
   Se carga con defer, corre después de que el DOM está listo.
   Requiere que el HTML tenga los elementos con los IDs listados abajo.
*/
(function () {
    'use strict';

    /* ── elementos del DOM ── */
    var btnMic   = document.getElementById('btnMic');
    var micIco   = document.getElementById('micIco');
    var micTxt   = document.getElementById('micTxt');
    var voiceMsg = document.getElementById('voiceMsg');
    var covFill  = document.getElementById('covFill');
    var covPct   = document.getElementById('covPct');
    var svFill   = document.getElementById('sideVoiceFill');
    var svPct    = document.getElementById('sideVoicePct');
    var lockBox  = document.getElementById('lockBox');
    var lockMsg  = document.getElementById('lockMsg');
    var readyBox = document.getElementById('readyBox');
    var testBtn  = document.getElementById('startTestBtn');
    var scrollHint = document.getElementById('scrollHint');

    if (!btnMic) return; // página sin bloque de voz

    /* ── comprobar soporte ── */
    var SR = window.SpeechRecognition || window.webkitSpeechRecognition;
    if (!SR) {
        btnMic.disabled = true;
        btnMic.style.opacity = '0.4';
        if (voiceMsg) voiceMsg.textContent = 'Tu navegador no soporta reconocimiento de voz. Usa Google Chrome.';
        return;
    }

    /* ── palabras del texto ── */
    var contentEl = document.getElementById('readingContent');
    if (!contentEl) return;

    // Envolver palabras en spans si aún no se hizo (puede haberlo hecho otro script)
    if (!contentEl.querySelector('.rw')) {
        var raw   = contentEl.textContent;
        var parts = raw.split(/(\s+)/);
        var wi    = 0;
        contentEl.innerHTML = parts.map(function (p) {
            if (/^\s*$/.test(p)) return p.replace(/\n/g, '<br>');
            var safe = p.replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;');
            return '<span class="rw" data-i="' + (wi++) + '">' + safe + '</span>';
        }).join('');
    }

    var spans    = Array.from(contentEl.querySelectorAll('.rw'));
    var total    = spans.length;
    var okBits   = new Array(total).fill(false);
    var coverage = 0;
    var cursor   = 0;   // ventana deslizante

    function norm(s) {
        return s.toLowerCase()
            .normalize('NFD').replace(/[\u0300-\u036f]/g, '')
            .replace(/[^a-z0-9]/g, '');
    }

    /* índice invertido: palabra → posiciones */
    var idx = {};
    spans.forEach(function (sp, i) {
        var w = norm(sp.textContent);
        if (!w) return;
        if (!idx[w]) idx[w] = [];
        idx[w].push(i);
    });

    /* ── estado del reconocimiento ── */
    var rec    = null;
    var active = false;   // intención del usuario

    function buildAndStart() {
        rec = new SR();
        rec.lang            = 'es-ES';
        rec.continuous      = true;
        rec.interimResults  = true;
        rec.maxAlternatives = 1;

        rec.onstart = function () {
            btnMic.classList.add('active');
            if (micIco) micIco.className = 'fa fa-stop';
            if (micTxt) micTxt.textContent = 'Detener';
            if (voiceMsg) voiceMsg.textContent = '🎤 Escuchando… lee el texto en voz alta';
        };

        rec.onresult = function (e) {
            var interim = '';
            for (var i = e.resultIndex; i < e.results.length; i++) {
                var t = e.results[i][0].transcript;
                if (e.results[i].isFinal) {
                    markWords(t);
                } else {
                    interim += t;
                }
            }
            if (voiceMsg && interim) voiceMsg.textContent = '🎤 "' + interim.trim() + '"';
        };

        rec.onerror = function (e) {
            if (e.error === 'not-allowed' || e.error === 'service-not-allowed') {
                active = false;
                resetBtn();
                if (voiceMsg) voiceMsg.textContent = '⛔ Permiso de micrófono denegado. Haz clic en el candado de la barra de direcciones y permite el micrófono.';
            }
            /* no-speech, aborted, network → onend reinicia */
        };

        rec.onend = function () {
            if (active) {
                /* reinicio inmediato con setTimeout 0 evita AbortError en Chrome */
                setTimeout(function () {
                    if (active) buildAndStart();
                }, 0);
            } else {
                resetBtn();
                if (voiceMsg) voiceMsg.textContent = 'Detenido. Las palabras verdes quedan guardadas.';
            }
        };

        rec.start();
    }

    function resetBtn() {
        btnMic.classList.remove('active');
        if (micIco) micIco.className = 'fa fa-microphone';
        if (micTxt) micTxt.textContent = 'Iniciar';
    }

    /* ── clic del botón ── */
    btnMic.addEventListener('click', function () {
        if (!active) {
            active = true;
            buildAndStart();
        } else {
            active = false;
            try { if (rec) rec.stop(); } catch (ex) { resetBtn(); }
        }
    });

    /* ── marcar palabras reconocidas ── */
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
                if (p < cursor) continue;
                if (p > cursor + WINDOW) continue;
                var d = p - cursor;
                if (d < bestD) { bestD = d; best = p; }
            }

            if (best !== -1 && !okBits[best]) {
                okBits[best] = true;
                spans[best].classList.add('ok');
                /* resaltar siguiente como "ahora leyendo" */
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

        if (covFill)  covFill.style.width   = coverage + '%';
        if (covPct)   covPct.textContent    = coverage + '%';
        if (svFill)   svFill.style.width    = coverage + '%';
        if (svPct)    svPct.textContent     = coverage + '%';
        if (lockMsg && coverage < 60)
            lockMsg.textContent = 'Lee en voz alta el 60% del texto (' + coverage + '% reconocido)';

        checkUnlock();
    }

    /* ── desbloqueo del test ── */
    var unlocked = false;

    function checkUnlock() {
        if (unlocked || coverage < 60) return;
        unlocked = true;

        active = false;
        try { if (rec) rec.stop(); } catch (e) {}
        resetBtn();

        if (lockBox)    lockBox.style.display   = 'none';
        if (readyBox)   readyBox.style.display  = 'flex';
        if (testBtn)    testBtn.classList.remove('locked');
        if (scrollHint) { scrollHint.textContent = '✓ Lectura verificada por voz'; scrollHint.style.color = '#198754'; }
        if (voiceMsg)   voiceMsg.textContent = '✅ ¡60% alcanzado! El test está desbloqueado.';
    }

    /* ── detener al navegar al test ── */
    if (testBtn) {
        testBtn.addEventListener('click', function (e) {
            e.preventDefault();
            if (this.classList.contains('locked')) return;
            active = false;
            try { if (rec) rec.stop(); } catch (ex) {}
            /* guardar tiempo de lectura en cookie */
            var elapsed = window._lecturaElapsed || 0;
            document.cookie = 'lecturaMs=' + elapsed + ';path=/;SameSite=Strict';
            window.location.href = this.href;
        });
    }

})();
