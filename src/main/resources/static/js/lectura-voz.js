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

    /* ── Verificar API ── */
    var SR = window.SpeechRecognition || window.webkitSpeechRecognition;

    if (!SR) {
        btnMic.disabled = true;
        btnMic.style.opacity = '0.4';
        setMsg('⛔ Tu navegador no soporta reconocimiento de voz. Usa Google Chrome.');
        return;
    }

    /* Verificar si el micrófono ya fue bloqueado previamente */
    if (navigator.permissions) {
        navigator.permissions.query({ name: 'microphone' }).then(function(result) {
            if (result.state === 'denied') {
                btnMic.style.opacity = '0.6';
                setMsg('⚠️ Micrófono bloqueado. Haz clic en 🔒 junto a la URL → Micrófono → Permitir → recarga la página.');
            }
            result.onchange = function() {
                if (result.state === 'granted') {
                    btnMic.style.opacity = '1';
                    setMsg('Presiona Iniciar y lee el texto en voz alta');
                }
            };
        }).catch(function() {});
    }

    /* ── Preparar spans: una palabra por span ── */
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
    var coverage = 0;

    /*
     * ── CURSOR SECUENCIAL ──
     * El cursor apunta a la siguiente palabra esperada en el texto.
     * Solo avanzamos hacia adelante, nunca hacia atrás.
     * Esto obliga al usuario a leer en orden y hace la detección mucho
     * más precisa porque eliminamos ambigüedad en palabras repetidas.
     */
    var cursor = 0;

    /* Normalizar: minúsculas, sin tildes, solo alfanumérico */
    function norm(s) {
        return s.toLowerCase()
            .normalize('NFD').replace(/[\u0300-\u036f]/g, '')
            .replace(/[^a-z0-9]/g, '');
    }

    /* Texto normalizado de cada span, precalculado */
    var spanNorms = spans.map(function(sp) { return norm(sp.textContent); });

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

    /* ── Solicitar permiso ── */
    function pedirPermisoYArrancar() {
        setMsg('⏳ Solicitando permiso de micrófono… Acepta el diálogo del navegador.');
        if (navigator.permissions) {
            navigator.permissions.query({ name: 'microphone' }).then(function(result) {
                if (result.state === 'denied') {
                    active = false;
                    resetBtn();
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
            .then(function (stream) {
                stream.getTracks().forEach(function(t) { t.stop(); });
                setMsg('✅ Permiso concedido. Iniciando reconocimiento…');
                startRec();
            })
            .catch(function (err) {
                active = false;
                resetBtn();
                if (err.name === 'NotAllowedError' || err.name === 'PermissionDeniedError') {
                    setMsg('⛔ Permiso denegado. Haz clic en 🔒 junto a la URL → Micrófono → Permitir → recarga.');
                } else if (err.name === 'NotFoundError' || err.name === 'DevicesNotFoundError') {
                    setMsg('⛔ No se detectó micrófono. Conecta uno e intenta de nuevo.');
                } else if (err.name === 'NotReadableError') {
                    setMsg('⛔ El micrófono está en uso por otra aplicación. Ciérrala e intenta.');
                } else {
                    setMsg('⛔ Error al acceder al micrófono: ' + err.name + '. Recarga la página.');
                }
            });
    }

    function startRec() {
        if (!active) return;

        rec = new SR();
        rec.lang            = 'es-GT';   // español guatemalteco para mejor precisión local
        rec.continuous      = true;
        rec.interimResults  = true;
        rec.maxAlternatives = 3;         // pedir 3 alternativas mejora la cobertura

        rec.onstart = function() {
            setActiveBtn();
            setMsg('🎤 Escuchando… lee el texto en voz alta');
        };

        rec.onresult = function(e) {
            var interim = '';
            for (var i = e.resultIndex; i < e.results.length; i++) {
                if (e.results[i].isFinal) {
                    /* Procesar todas las alternativas disponibles */
                    for (var a = 0; a < e.results[i].length; a++) {
                        markWords(e.results[i][a].transcript);
                    }
                } else {
                    interim = e.results[i][0].transcript;
                }
            }
            if (interim) setMsg('🎤 "' + interim.trim() + '"');
        };

        rec.onerror = function(e) {
            if (e.error === 'not-allowed' || e.error === 'service-not-allowed') {
                active = false;
                resetBtn();
                setMsg('⛔ Permiso denegado. Recarga y permite el micrófono cuando Chrome lo solicite.');
            }
            /* 'no-speech' y 'aborted' son normales, simplemente reiniciar */
        };

        rec.onend = function() {
            if (!active) { resetBtn(); setMsg('Detenido.'); return; }
            setTimeout(startRec, 80);
        };

        try {
            rec.start();
        } catch(ex) {
            setTimeout(startRec, 300);
        }
    }

    /* ── Clic del botón ── */
    btnMic.addEventListener('click', function() {
        if (!active) {
            active = true;
            btnMic.disabled = true;
            pedirPermisoYArrancar();
        } else {
            active = false;
            try { if (rec) rec.stop(); } catch(ex) {}
            resetBtn();
            setMsg('Detenido.');
        }
    });

    /*
     * ── ALGORITMO DE MARCADO MEJORADO ──
     *
     * Estrategia: coincidencia secuencial estricta.
     *
     * En lugar de buscar la palabra en un índice de posiciones (que falla
     * con palabras repetidas), recorremos las palabras del transcript en
     * orden y las buscamos en el texto a partir del cursor actual con una
     * ventana hacia adelante. Esto garantiza que:
     *   1. Las palabras se marcan en el orden en que aparecen en el texto.
     *   2. Una palabra repetida solo se marca si está en el lugar correcto.
     *   3. El cursor nunca retrocede, así que palabras anteriores no se
     *      cuentan dos veces.
     *
     * VENTANA_ADELANTE: cuántas palabras hacia adelante buscamos desde el
     * cursor. Un valor pequeño (15-20) fuerza precisión pero puede perder
     * palabras si el reconocedor omite alguna. Un valor mayor (40-60) es
     * más tolerante con errores de reconocimiento.
     */
    var VENTANA_ADELANTE = 40;

    function markWords(transcript) {
        var spoken  = transcript.trim().split(/\s+/).map(norm).filter(Boolean);
        var changed = false;

        /* Recorrer cada palabra dicha en el orden en que fue dicha */
        for (var si = 0; si < spoken.length; si++) {
            var word = spoken[si];
            if (!word) continue;

            /* Buscar la palabra desde el cursor hasta cursor+VENTANA */
            var limite = Math.min(cursor + VENTANA_ADELANTE, total);
            var encontrado = -1;

            for (var ti = cursor; ti < limite; ti++) {
                if (okBits[ti]) continue;          /* ya marcada, saltar */
                if (spanNorms[ti] === word) {
                    encontrado = ti;
                    break;
                }
            }

            if (encontrado !== -1) {
                okBits[encontrado] = true;
                spans[encontrado].classList.add('ok');

                /* Resaltar la siguiente palabra esperada */
                spans.forEach(function(s) { s.classList.remove('now'); });
                var siguiente = encontrado + 1;
                /* Saltar palabras ya marcadas para señalar la próxima pendiente */
                while (siguiente < total && okBits[siguiente]) siguiente++;
                if (siguiente < total) spans[siguiente].classList.add('now');

                /* Avanzar el cursor a justo después de la palabra marcada */
                cursor = encontrado + 1;
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
        active = false;
        try { if (rec) rec.stop(); } catch(e) {}
        resetBtn();
        if (lockBox)    lockBox.style.display  = 'none';
        if (readyBox)   readyBox.style.display = 'flex';
        if (testBtn)    testBtn.classList.remove('locked');
        if (scrollHint) { scrollHint.textContent = '✓ Lectura verificada'; scrollHint.style.color = '#198754'; }
        setMsg('✅ ¡60% alcanzado! El test está desbloqueado.');
    }

    if (testBtn) {
        testBtn.addEventListener('click', function(e) {
            e.preventDefault();
            if (this.classList.contains('locked')) return;
            active = false;
            try { if (rec) rec.stop(); } catch(ex) {}
            document.cookie = 'lecturaMs=' + (window._lecturaElapsed || 0) + ';path=/;SameSite=Strict';
            window.location.href = this.href;
        });
    }

})();
