history.pushState(null, null, location.href);
window.onpopstate = function() { history.go(1); };

const total = 5;
const answered = new Set();
const dots = document.getElementById('progressDots');
const ptext = document.getElementById('progressText');

for (let i = 0; i < total; i++) {
    const d = document.createElement('div');
    d.className = 'q-dot';
    d.id = 'dot_' + i;
    dots.appendChild(d);
}

window.markAnswered = function(input) {
    const idx = parseInt(input.name.match(/\[(\d+)\]/)[1]);
    answered.add(idx);
    document.getElementById('dot_' + idx).classList.add('done');
    document.getElementById('qcard_' + idx).classList.add('answered');
    ptext.textContent = answered.size + ' de ' + total + ' respondidas';
};

// Temporizador
const TOTAL_SECS = (typeof timerSegundos !== 'undefined') ? timerSegundos : 600;
let remaining = TOTAL_SECS;

const display = document.getElementById('timerDisplay');
const timerTxt = document.getElementById('timerText');
const fill = document.getElementById('timerFill');
const overlay = document.getElementById('timeoutOverlay');
const form = document.getElementById('testForm');

function formatTime(s) {
    const m = Math.floor(s / 60);
    const sec = s % 60;
    return String(m).padStart(2, '0') + ':' + String(sec).padStart(2, '0');
}

function tick() {
    remaining--;
    timerTxt.textContent = formatTime(remaining);
    const pct = (remaining / TOTAL_SECS) * 100;
    fill.style.width = pct + '%';

    if (remaining <= 60) {
        display.className = 'timer-display danger';
        fill.className = 'timer-fill danger';
    } else if (remaining <= 180) {
        display.className = 'timer-display warning';
        fill.className = 'timer-fill warning';
    }

    if (remaining <= 0) {
        clearInterval(timer);
        overlay.classList.add('show');
        setTimeout(() => form.submit(), 2000);
    }
}

timerTxt.textContent = formatTime(remaining);
const timer = setInterval(tick, 1000);