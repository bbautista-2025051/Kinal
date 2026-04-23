document.addEventListener('DOMContentLoaded', function () {
    const fill = document.getElementById('progressFill');
    const pct  = document.getElementById('progressPct');

    function updateProgress() {
        const d = document.documentElement;
        const scrolled = d.scrollTop / (d.scrollHeight - d.clientHeight);
        const val = Math.round(Math.min(scrolled * 100, 100));
        if (fill) fill.style.width = val + '%';
        if (pct)  pct.textContent  = val + '%';
    }
    window.addEventListener('scroll', updateProgress);
    updateProgress();
    const startTime = Date.now();
    const timerEl   = document.getElementById('readingTimerDisplay');
    const hiddenEl  = document.getElementById('tiempoLecturaInput');

    function formatReadTime(seconds) {
        const m = Math.floor(seconds / 60);
        const s = seconds % 60;
        return String(m).padStart(2, '0') + ':' + String(s).padStart(2, '0');
    }

    const readTimerInterval = setInterval(function () {
        const elapsed = Math.floor((Date.now() - startTime) / 1000);
        if (timerEl) timerEl.textContent = formatReadTime(elapsed);
    }, 1000);

    const testLinks = document.querySelectorAll('.btn-ir-test');
    testLinks.forEach(function (link) {
        link.addEventListener('click', function () {
            const elapsed = Math.floor((Date.now() - startTime) / 1000);
            const lecturaId = link.dataset.lecturaId;
            if (lecturaId) {
                localStorage.setItem('tiempoLectura_' + lecturaId, elapsed);
            }
        });
    });

    setInterval(function () {
        const elapsed = Math.floor((Date.now() - startTime) / 1000);
        const lecturaId = document.body.dataset.lecturaId;
        if (lecturaId) {
            localStorage.setItem('tiempoLectura_' + lecturaId, elapsed);
        }
    }, 5000);
});
