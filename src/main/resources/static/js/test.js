history.pushState(null, null, location.href);
window.onpopstate = function () { history.go(1); };

const total    = 5;
const answered = new Set();
const dots     = document.getElementById('progressDots');
const ptext    = document.getElementById('progressText');

for (let i = 0; i < total; i++) {
    const d = document.createElement('div');
    d.className = 'q-dot';
    d.id = 'dot_' + i;
    dots.appendChild(d);
}

window.markAnswered = function (input) {
    const idx = parseInt(input.name.match(/\[(\d+)\]/)[1]);
    answered.add(idx);
    document.getElementById('dot_' + idx).classList.add('done');
    document.getElementById('qcard_' + idx).classList.add('answered');
    ptext.textContent = answered.size + ' de ' + total + ' respondidas';
};

const TOTAL_SECS  = (typeof timerSegundos !== 'undefined') ? timerSegundos : 300;
const startEpoch  = Date.now();

const display   = document.getElementById('timerDisplay');
const timerTxt  = document.getElementById('timerText');
const fill      = document.getElementById('timerFill');
const overlay   = document.getElementById('timeoutOverlay');
const form      = document.getElementById('testForm');
const hiddenTiempo = document.getElementById('tiempoUsadoInput');

let alreadySubmitted = false;

function formatTime(s) {
    const m   = Math.floor(s / 60);
    const sec = s % 60;
    return String(m).padStart(2, '0') + ':' + String(sec).padStart(2, '0');
}

function submitForm() {
    if (alreadySubmitted) return;
    alreadySubmitted = true;

    const usado = Math.floor((Date.now() - startEpoch) / 1000);
    if (hiddenTiempo) hiddenTiempo.value = usado;

    const lecturaId  = document.body.dataset.lecturaId;
    const hiddenLect = document.getElementById('tiempoLecturaInput');
    if (hiddenLect && lecturaId) {
        const tLectura = localStorage.getItem('tiempoLectura_' + lecturaId);
        if (tLectura) hiddenLect.value = tLectura;
    }

    form.submit();
}

function tick() {
    const elapsed   = Math.floor((Date.now() - startEpoch) / 1000);
    const remaining = Math.max(0, TOTAL_SECS - elapsed);

    timerTxt.textContent = formatTime(remaining);
    const pct = (remaining / TOTAL_SECS) * 100;
    fill.style.width = pct + '%';

    if (remaining <= 60) {
        display.className = 'timer-display danger';
        fill.className    = 'timer-fill danger';
    } else if (remaining <= 120) {
        display.className = 'timer-display warning';
        fill.className    = 'timer-fill warning';
    }

    if (remaining <= 0) {
        clearInterval(timerInterval);
        overlay.classList.add('show');
        setTimeout(submitForm, 2000);
    }
}

timerTxt.textContent = formatTime(TOTAL_SECS);
const timerInterval = setInterval(tick, 500);

form.addEventListener('submit', function (e) {
    if (alreadySubmitted) { e.preventDefault(); return; }
    const usado = Math.floor((Date.now() - startEpoch) / 1000);
    if (hiddenTiempo) hiddenTiempo.value = usado;

    const lecturaId  = document.body.dataset.lecturaId;
    const hiddenLect = document.getElementById('tiempoLecturaInput');
    if (hiddenLect && lecturaId) {
        const tLectura = localStorage.getItem('tiempoLectura_' + lecturaId);
        if (tLectura) hiddenLect.value = tLectura;
    }
    alreadySubmitted = true;
});
