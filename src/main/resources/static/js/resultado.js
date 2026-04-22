document.addEventListener('DOMContentLoaded', function () {
    // Animate the score number counting up
    const notaEl = document.querySelector('.resultado-nota');
    if (!notaEl) return;

    const target = parseFloat(notaEl.textContent);
    if (isNaN(target)) return;

    let current = 0;
    const step = target / 40;
    const interval = setInterval(function () {
        current += step;
        if (current >= target) {
            current = target;
            clearInterval(interval);
        }
        notaEl.textContent = Math.round(current);
    }, 30);
});
