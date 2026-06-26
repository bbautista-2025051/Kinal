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

    /* ── Caja de diagnóstico (se elimina al funcionar) ── */
    var diagBox = document.createElement('div');
    diagBox.id  = 'voiceDiag';
    diagBox.style.cssText = [
        'font-size:11px','font-family:monospace','background:#1e1e1e','color:#d4d4d4',
        'padding:8px 12px','border-radius:6px','margin-bottom:8px',
        'max-height:120px','overflow-y:auto','display:none'
    ].join(';');
    var voiceBar = document.getElementById('voiceBar');
    if (voiceBar) voiceBar.insertAdjacentElement('afterend', diagBox);

    var logLines = [];
    function log(msg) {
        var ts = new Date().toLocaleTimeString();
        logLines.push('[' + ts + '] ' + msg);
        if (logLines.length > 30) logLines.shift();
        diagBox.innerHTML = logLines.join('<br>');
        diagBox.style.display = 'block';
        diagBox.scrollTop = diagBox.scrollHeight;
    }

    /* ── Verificar API ── */
    var SR = window.SpeechRecognition || window.webkitSpeechRecognition;
    log('SpeechRecognition: ' + (SR ? 'disponible ✓' : 'NO DISPONIBLE ✗'));
    log('navigator.mediaDevices: ' + (navigator.mediaDevices ? 'OK' : 'NO'));
    log('Protocolo: ' + location.protocol);
    log('Host: ' + location.host);

    if (!SR) {
        btnMic.disabled = true;
        btnMic.style.opacity = '0.4';
        setMsg('Usa Google Chrome para esta función.');
        return;
    }

    /* ── Preparar spans ── */
    var raw = contentEl.textContent;
    var wi  = 0;
    contentEl.innerHTML = raw.split(/(\s+)/).map(function (p) {
        if (/^\s*$/.test(p)) return p.replace(/\n/g, '<br>');
        return '<span class="rw" data-i="' + (wi++) + '">' +
               p.replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;') + '</span>';
    }).join('');

    var spans  = Array.from(contentEl.querySelectorAll('.rw'));
    var total  = spans.length;
    var okBits = new Array(total).fill(false);
    var coverage = 0, cursor = 0;

    function norm(s) {
        return s.toLowerCase()
            .normalize('NFD').replace(/[\u0300-\u036f]/g,'')
            .replace(/[^a-z0-9]/g,'');
    }
    var idx = {};
    spans.forEach(function(sp, i) {
        var w = norm(sp.textContent);
        if (!w) return;
        if (!idx[w]) idx[w] = [];
        idx[w].push(i);
    });

    /* ── Estado ── */
    var active = false, rec = null;

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

    /* ── Solicitar permiso de micrófono explícitamente ── */
    function pedirPermisoYArrancar() {
        log('Solicitando permiso con getUserMedia...');
        setMsg('⏳ Solicitando permiso de micrófono…');

        navigator.mediaDevices.getUserMedia({ audio: true, video: false })
            .then(function (stream) {
                log('getUserMedia OK — permiso concedido');
                /* Detener el stream, ya tenemos el permiso */
                stream.getTracks().forEach(function(t) { t.stop(); });
                /* Verificar estado del permiso */
                if (navigator.permissions) {
                    navigator.permissions.query({ name: 'microphone' }).then(function(r) {
                        log('Permissions API: ' + r.state);
                    });
                }
                startRec();
            })
            .catch(function (err) {
                log('getUserMedia ERROR: ' + err.name + ' — ' + err.message);
                active = false;
                resetBtn();
                if (err.name === 'NotAllowedError' || err.name === 'PermissionDeniedError') {
                    setMsg('⛔ Permiso denegado. Haz clic en el ícono 🎙 o 🔒 junto a la URL → Micrófono → Permitir → recarga.');
                } else if (err.name === 'NotFoundError') {
                    setMsg('⛔ No se detectó micrófono. Conecta uno e intenta de nuevo.');
                } else {
                    setMsg('⛔ Error al acceder al micrófono: ' + err.name);
                }
            });
    }

    function startRec() {
        if (!active) return;
        log('Creando SpeechRecognition...');

        rec = new SR();
        rec.lang           = 'es-ES';
        rec.continuous     = true;
        rec.interimResults = true;
        rec.maxAlternatives = 1;

        rec.onstart = function() {
            log('onstart ✓ — escuchando activamente');
            setActiveBtn();
            setMsg('🎤 Escuchando… lee el texto en voz alta');
        };

        rec.onsoundstart  = function() { log('onsoundstart — detectó sonido'); };
        rec.onspeechstart = function() { log('onspeechstart — detectó habla'); };
        rec.onspeechend   = function() { log('onspeechend'); };

        rec.onresult = function(e) {
            var interim = '';
            for (var i = e.resultIndex; i < e.results.length; i++) {
                var t = e.results[i][0].transcript;
                if (e.results[i].isFinal) { log('FINAL: "' + t.trim() + '"'); markWords(t); }
                else interim = t;
            }
            if (interim) setMsg('🎤 "' + interim.trim() + '"');
        };

        rec.onerror = function(e) {
            log('onerror: ' + e.error + (e.message ? ' / ' + e.message : ''));
            if (e.error === 'not-allowed' || e.error === 'service-not-allowed') {
                active = false;
                resetBtn();
                setMsg('⛔ Permiso denegado. Recarga y permite el micrófono cuando Chrome lo solicite.');
            }
        };

        rec.onend = function() {
            log('onend (active=' + active + ')');
            if (!active) { resetBtn(); setMsg('Detenido.'); return; }
            setTimeout(startRec, 80);
        };

        try {
            rec.start();
            log('rec.start() llamado');
        } catch(ex) {
            log('ERROR en rec.start(): ' + ex.name + ' — ' + ex.message);
            setTimeout(startRec, 300);
        }
    }

    /* ── Clic del botón ── */
    btnMic.addEventListener('click', function() {
        log('--- Botón presionado (active=' + active + ') ---');
        if (!active) {
            active = true;
            btnMic.disabled = true;
            pedirPermisoYArrancar();
        } else {
            active = false;
            try { if (rec) rec.stop(); } catch(ex) { log('stop error: ' + ex.message); }
            resetBtn();
            setMsg('Detenido.');
        }
    });

    /* ── Marcar palabras ── */
    function markWords(transcript) {
        var spoken  = transcript.trim().split(/\s+/).map(norm).filter(Boolean);
        var WINDOW  = 80, changed = false;
        spoken.forEach(function(word) {
            var positions = idx[word];
            if (!positions) return;
            var best = -1, bestD = Infinity;
            positions.forEach(function(p) {
                if (p < cursor || p > cursor + WINDOW) return;
                var d = p - cursor;
                if (d < bestD) { bestD = d; best = p; }
            });
            if (best !== -1 && !okBits[best]) {
                okBits[best] = true;
                spans[best].classList.add('ok');
                spans.forEach(function(s){ s.classList.remove('now'); });
                if (spans[best+1]) spans[best+1].classList.add('now');
                if (best >= cursor) cursor = best + 1;
                changed = true;
            }
        });
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
        active = false;
        try { if (rec) rec.stop(); } catch(e) {}
        resetBtn();
        if (lockBox)    lockBox.style.display  = 'none';
        if (readyBox)   readyBox.style.display = 'flex';
        if (testBtn)    testBtn.classList.remove('locked');
        if (scrollHint) { scrollHint.textContent = '✓ Lectura verificada'; scrollHint.style.color='#198754'; }
        setMsg('✅ ¡60% alcanzado! El test está desbloqueado.');
        diagBox.style.display = 'none'; /* ocultar logs al terminar */
    }

    if (testBtn) {
        testBtn.addEventListener('click', function(e) {
            e.preventDefault();
            if (this.classList.contains('locked')) return;
            active = false;
            try { if (rec) rec.stop(); } catch(ex) {}
            document.cookie = 'lecturaMs=' + (window._lecturaElapsed||0) + ';path=/;SameSite=Strict';
            window.location.href = this.href;
        });
    }

})();
