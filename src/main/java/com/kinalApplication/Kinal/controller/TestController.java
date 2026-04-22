package com.kinalApplication.Kinal.controller;

import com.kinalApplication.Kinal.config.SecurityConfig;
import com.kinalApplication.Kinal.dto.RespuestaTestDTO;
import com.kinalApplication.Kinal.model.Estudiante;
import com.kinalApplication.Kinal.model.Lectura;
import com.kinalApplication.Kinal.model.Pregunta;
import com.kinalApplication.Kinal.model.ResultadoTest;
import com.kinalApplication.Kinal.repository.LecturaRepository;
import com.kinalApplication.Kinal.repository.ResultadoRepository;
import com.kinalApplication.Kinal.service.EstudianteService;
import com.kinalApplication.Kinal.service.TestService;
import com.kinalApplication.Kinal.util.SecurityAuditLogger;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired private TestService testService;
    @Autowired private LecturaRepository lecturaRepository;
    @Autowired private ResultadoRepository resultadoRepository;
    @Autowired private EstudianteService estudianteService;

    private static final int CANTIDAD_PREGUNTAS = 5;
    private static final int TIMER_SEGUNDOS     = 600;
    // Token de sesión único por test para evitar replay attacks
    private static final String SESSION_TOKEN   = "testSessionToken";

    @GetMapping("/{lecturaId}")
    public String iniciarTest(@PathVariable Long lecturaId,
                              HttpSession session,
                              Model model,
                              Authentication auth,
                              HttpServletRequest request,
                              RedirectAttributes redirectAttributes) {

        // ── Validar que lecturaId sea positivo (path manipulation) ───────────
        if (lecturaId == null || lecturaId <= 0) {
            return "redirect:/carreras";
        }

        Estudiante estudiante = estudianteService.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        Lectura lectura = lecturaRepository.findById(lecturaId)
                .orElse(null);
        if (lectura == null) {
            redirectAttributes.addFlashAttribute("error", "Lectura no encontrada.");
            return "redirect:/carreras";
        }

        // ── Verificar si ya realizó el test ──────────────────────────────────
        Optional<ResultadoTest> intento = resultadoRepository.findByEstudianteAndLectura(estudiante, lectura);
        if (intento.isPresent()) {
            ResultadoTest r = intento.get();
            redirectAttributes.addFlashAttribute("nota",       r.getNota());
            redirectAttributes.addFlashAttribute("aciertos",   r.getAciertos());
            redirectAttributes.addFlashAttribute("total",      r.getTotalPreguntas());
            redirectAttributes.addFlashAttribute("yaRealizado", true);
            return "redirect:/test/resultado";
        }

        List<Pregunta> preguntas = testService.obtenerPreguntasAleatorias(lecturaId, CANTIDAD_PREGUNTAS);
        if (preguntas.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Esta lectura aún no tiene preguntas.");
            return "redirect:/carreras";
        }

        // ── Invalidar sesión anterior de test (evita tests abiertos en paralelo)
        session.removeAttribute("preguntasTest");
        session.removeAttribute("lecturaId");
        session.removeAttribute("testInicio");
        session.removeAttribute(SESSION_TOKEN);

        // ── Generar token único para este test (anti-CSRF de formulario) ─────
        String token = java.util.UUID.randomUUID().toString();
        session.setAttribute(SESSION_TOKEN,    token);
        session.setAttribute("preguntasTest",  preguntas);
        session.setAttribute("lecturaId",      lecturaId);
        session.setAttribute("testInicio",     System.currentTimeMillis());

        model.addAttribute("preguntas",         preguntas);
        model.addAttribute("respuestaTestDTO",  new RespuestaTestDTO());
        model.addAttribute("timerSegundos",     TIMER_SEGUNDOS);
        model.addAttribute("testToken",         token);
        return "test";
    }

    @PostMapping("/calificar")
    public String calificar(@Valid @ModelAttribute RespuestaTestDTO respuestasDto,
                            @RequestParam(value = "testToken", required = false) String testToken,
                            HttpSession session,
                            Authentication auth,
                            HttpServletRequest request,
                            RedirectAttributes redirectAttributes) {

        String ip = SecurityConfig.getClientIp(request);

        @SuppressWarnings("unchecked")
        List<Pregunta> preguntas = (List<Pregunta>) session.getAttribute("preguntasTest");
        Long lecturaId           = (Long) session.getAttribute("lecturaId");
        Long inicio              = (Long) session.getAttribute("testInicio");
        String sessionToken      = (String) session.getAttribute(SESSION_TOKEN);

        // ── Validar que la sesión de test exista y el token coincida ─────────
        if (preguntas == null || lecturaId == null) {
            redirectAttributes.addFlashAttribute("error", "Sesión expirada. Vuelve a iniciar el test.");
            return "redirect:/carreras";
        }
        if (sessionToken == null || !sessionToken.equals(testToken)) {
            SecurityAuditLogger.logAccessDenied("/test/calificar (token inválido)", ip);
            redirectAttributes.addFlashAttribute("error", "Token de sesión inválido.");
            return "redirect:/carreras";
        }

        // ── Validar tiempo máximo en el servidor ─────────────────────────────
        if (inicio != null) {
            long transcurrido = (System.currentTimeMillis() - inicio) / 1000;
            if (transcurrido > TIMER_SEGUNDOS + 30) {
                limpiarSesionTest(session);
                redirectAttributes.addFlashAttribute("error", "El tiempo del test ha expirado.");
                return "redirect:/carreras";
            }
        }

        // ── Validar que las respuestas recibidas coincidan en cantidad ────────
        List<Long> respuestasIds = respuestasDto.getRespuestas();
        if (respuestasIds == null || respuestasIds.size() != preguntas.size()) {
            limpiarSesionTest(session);
            redirectAttributes.addFlashAttribute("error", "Respuestas incompletas.");
            return "redirect:/carreras";
        }

        Estudiante estudiante = estudianteService.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        Lectura lectura = lecturaRepository.findById(lecturaId)
                .orElseThrow(() -> new RuntimeException("Lectura no encontrada"));

        // ── Doble-check: evitar doble envío ───────────────────────────────────
        if (resultadoRepository.existsByEstudianteAndLectura(estudiante, lectura)) {
            ResultadoTest r = resultadoRepository.findByEstudianteAndLectura(estudiante, lectura).get();
            limpiarSesionTest(session);
            redirectAttributes.addFlashAttribute("nota",        r.getNota());
            redirectAttributes.addFlashAttribute("aciertos",    r.getAciertos());
            redirectAttributes.addFlashAttribute("total",       r.getTotalPreguntas());
            redirectAttributes.addFlashAttribute("yaRealizado", true);
            return "redirect:/test/resultado";
        }

        // ── Validar que cada opción enviada pertenezca a la pregunta ─────────
        for (int i = 0; i < preguntas.size(); i++) {
            Pregunta p = preguntas.get(i);
            Long opcionEnviada = respuestasIds.get(i);
            boolean valida = p.getOpciones().stream()
                    .anyMatch(o -> o.getId().equals(opcionEnviada));
            if (!valida) {
                limpiarSesionTest(session);
                SecurityAuditLogger.logAccessDenied("/test/calificar (opción no válida)", ip);
                redirectAttributes.addFlashAttribute("error", "Respuesta inválida detectada.");
                return "redirect:/carreras";
            }
        }

        double nota     = testService.calificar(respuestasIds, preguntas);
        int    aciertos = (int) Math.round(nota * preguntas.size() / 100.0);

        ResultadoTest resultado = new ResultadoTest(estudiante, lectura, aciertos, preguntas.size(), nota);
        resultadoRepository.save(resultado);

        SecurityAuditLogger.logTestSubmit(auth.getName(), lecturaId, ip);
        limpiarSesionTest(session);

        redirectAttributes.addFlashAttribute("nota",     nota);
        redirectAttributes.addFlashAttribute("aciertos", aciertos);
        redirectAttributes.addFlashAttribute("total",    preguntas.size());
        return "redirect:/test/resultado";
    }

    @GetMapping("/resultado")
    public String mostrarResultado(Model model) {
        return "resultado";
    }

    private void limpiarSesionTest(HttpSession session) {
        session.removeAttribute("preguntasTest");
        session.removeAttribute("lecturaId");
        session.removeAttribute("testInicio");
        session.removeAttribute(SESSION_TOKEN);
    }
}
