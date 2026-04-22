document.addEventListener('DOMContentLoaded', function() {
    const fill = document.getElementById('progressFill');
    const pct = document.getElementById('progressPct');
    if (!fill || !pct) return;

    function updateProgress() {
        const d = document.documentElement;
        const scrolled = d.scrollTop / (d.scrollHeight - d.clientHeight);
        const val = Math.round(Math.min(scrolled * 100, 100));
        fill.style.width = val + '%';
        pct.textContent = val + '%';
    }

    window.addEventListener('scroll', updateProgress);
    updateProgress(); // inicializar
});