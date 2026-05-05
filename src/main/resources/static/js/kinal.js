/**
 * kinal.js — Utilidades globales de UI para la plataforma Kinal
 */
(function () {
  'use strict';

  /* ── Auto-dismiss alerts ────────────────────────────────────────── */
  document.querySelectorAll('.auto-dismiss').forEach(function (el) {
    setTimeout(function () {
      el.style.transition = 'opacity .5s ease';
      el.style.opacity = '0';
      setTimeout(function () { el.remove(); }, 500);
    }, 5000);
  });

  /* ── Fade-up animation with IntersectionObserver ───────────────── */
  var fadeEls = document.querySelectorAll('.fade-up');
  if ('IntersectionObserver' in window) {
    var observer = new IntersectionObserver(function (entries) {
      entries.forEach(function (entry) {
        if (entry.isIntersecting) {
          entry.target.classList.add('visible');
          observer.unobserve(entry.target);
        }
      });
    }, { threshold: 0.1 });
    fadeEls.forEach(function (el) { observer.observe(el); });
  } else {
    fadeEls.forEach(function (el) { el.classList.add('visible'); });
  }

  /* ── Reveal rows (table rows) ───────────────────────────────────── */
  var revealEls = document.querySelectorAll('.reveal');
  if ('IntersectionObserver' in window) {
    var rowObserver = new IntersectionObserver(function (entries) {
      entries.forEach(function (entry) {
        if (entry.isIntersecting) {
          entry.target.classList.add('visible');
          rowObserver.unobserve(entry.target);
        }
      });
    }, { threshold: 0.05 });
    revealEls.forEach(function (el) { rowObserver.observe(el); });
  } else {
    revealEls.forEach(function (el) { el.classList.add('visible'); });
  }

  /* ── Stat counter animation ─────────────────────────────────────── */
  document.querySelectorAll('[data-count]').forEach(function (el) {
    var target = parseFloat(el.getAttribute('data-count')) || 0;
    var duration = 1200;
    var start = null;
    var startVal = 0;
    function step(ts) {
      if (!start) start = ts;
      var progress = Math.min((ts - start) / duration, 1);
      var val = startVal + (target - startVal) * progress;
      el.textContent = Number.isInteger(target) ? Math.round(val) : val.toFixed(1);
      if (progress < 1) requestAnimationFrame(step);
    }
    requestAnimationFrame(step);
  });

})();
