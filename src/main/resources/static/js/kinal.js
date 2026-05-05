/**
 * kinal.js — Utilidades globales de UI para la plataforma Kinal
 * Fundación Kinal · Guatemala
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
  /* BUG FIX: use IntersectionObserver so counters only animate when
     the stat card enters the viewport (also fixes timing issues when
     elements are server-rendered but hidden until JS runs).          */
  function animateCounter(el) {
    var target = parseFloat(el.getAttribute('data-count')) || 0;
    if (isNaN(target)) return;
    var duration = 1200;
    var start = null;
    var startVal = 0;
    function step(ts) {
      if (!start) start = ts;
      var progress = Math.min((ts - start) / duration, 1);
      var ease = 1 - Math.pow(1 - progress, 3);
      var val = startVal + (target - startVal) * ease;
      el.textContent = Number.isInteger(target) ? Math.round(val) : val.toFixed(1);
      if (progress < 1) requestAnimationFrame(step);
    }
    requestAnimationFrame(step);
  }

  var statEls = document.querySelectorAll('[data-count]');
  if ('IntersectionObserver' in window) {
    var statObserver = new IntersectionObserver(function (entries) {
      entries.forEach(function (entry) {
        if (entry.isIntersecting) {
          animateCounter(entry.target);
          statObserver.unobserve(entry.target);
        }
      });
    }, { threshold: 0.2 });
    statEls.forEach(function (el) { statObserver.observe(el); });
  } else {
    statEls.forEach(function (el) { animateCounter(el); });
  }

})();