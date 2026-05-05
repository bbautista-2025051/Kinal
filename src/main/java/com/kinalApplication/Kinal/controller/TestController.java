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
    private static final int TIMER_SEGUNDOS     = 300;
    private static final String SESSION_TOKEN   = "testSessionToken";

    private static final int PPM_BUENA = 200;
    private static final int PPM_MUY_BUENA = 300;

    @GetMapping("/{lecturaId}")
    public String iniciarTest(@PathVariable Long lecturaId,
                              @RequestParam(value = "token", required = false) String urlToken,
                              HttpSession session,
                              Model model,
                              Authentication auth,
                              HttpServletRequest request,
                              RedirectAttributes redirectAttributes) {

        if (lecturaId == null || lecturaId <= 0) {
            return "redirect:/carreras";
        }

        Estudiante estudiante = estudianteService.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        Lectura lectura = lecturaRepository.findById(lecturaId).orElse(null);
        if (lectura == null) {
            redirectAttributes.addFlashAttribute("error", "Lectura no encontrada.");
            return "redirect:/carreras";
        }

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

        String sessionUrlToken = (String) session.getAttribute("lecturaUrlToken_" + lecturaId);
        if (sessionUrlToken != null && urlToken != null && !sessionUrlToken.equals(urlToken)) {
            SecurityAuditLogger.logAccessDenied("/test/" + lecturaId + " (url token inválido)", SecurityConfig.getClientIp(request));
            redirectAttributes.addFlashAttribute("error", "Acceso inválido al test.");
            return "redirect:/carreras";
        }

        session.removeAttribute("preguntasTest");
        session.removeAttribute("lecturaId");
        session.removeAttribute("testInicio");
        session.removeAttribute(SESSION_TOKEN);

        String token = java.util.UUID.randomUUID().toString();
        session.setAttribute(SESSION_TOKEN,    token);
        session.setAttribute("preguntasTest",  preguntas);
        session.setAttribute("lecturaId",      lecturaId);
        session.setAttribute("testInicio",     System.currentTimeMillis());

        Long tiempoLecturaMs = (Long) session.getAttribute("tiempoLectura_" + lecturaId);
        if (tiempoLecturaMs == null || tiempoLecturaMs <= 0) {
            Long lecturaInicio = (Long) session.getAttribute("lecturaInicio_" + lecturaId);
            if (lecturaInicio != null) {
                tiempoLecturaMs = System.currentTimeMillis() - lecturaInicio;
            }
        }
        if (tiempoLecturaMs != null && tiempoLecturaMs > 0) {
            session.setAttribute("tiempoLecturaGuardado_" + lecturaId, tiempoLecturaMs);
        }

        model.addAttribute("preguntas",         preguntas);
        model.addAttribute("respuestaTestDTO",  new RespuestaTestDTO());
        model.addAttribute("timerSegundos",     TIMER_SEGUNDOS);
        model.addAttribute("testToken",         token);
        model.addAttribute("lecturaId",         lecturaId);
        return "test";
    }

    @PostMapping("/calificar")
    public String calificar(@Valid @ModelAttribute RespuestaTestDTO respuestasDto,
                            @RequestParam(value = "testToken", required = false) String testToken,
                            @RequestParam(value = "tiempoUsado", required = false) Integer tiempoUsado,
                            @RequestParam(value = "tiempoLecturaSegundos", required = false) Integer tiempoLecturaSegundos,
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

        if (preguntas == null || lecturaId == null) {
            redirectAttributes.addFlashAttribute("error", "Sesión expirada. Vuelve a iniciar el test.");
            return "redirect:/carreras";
        }
        if (sessionToken == null || !sessionToken.equals(testToken)) {
            SecurityAuditLogger.logAccessDenied("/test/calificar (token inválido)", ip);
            redirectAttributes.addFlashAttribute("error", "Token de sesión inválido.");
            return "redirect:/carreras";
        }

        if (inicio != null) {
            long transcurrido = (System.currentTimeMillis() - inicio) / 1000;
            if (transcurrido > TIMER_SEGUNDOS + 30) {
                limpiarSesionTest(session, lecturaId);
                redirectAttributes.addFlashAttribute("error", "El tiempo del test ha expirado.");
                return "redirect:/carreras";
            }
        }

        List<Long> respuestasIds = respuestasDto.getRespuestas();
        if (respuestasIds == null || respuestasIds.size() != preguntas.size()) {
            limpiarSesionTest(session, lecturaId);
            redirectAttributes.addFlashAttribute("error", "Respuestas incompletas.");
            return "redirect:/carreras";
        }

        Estudiante estudiante = estudianteService.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        Lectura lectura = lecturaRepository.findById(lecturaId)
                .orElseThrow(() -> new RuntimeException("Lectura no encontrada"));

        if (resultadoRepository.existsByEstudianteAndLectura(estudiante, lectura)) {
            ResultadoTest r = resultadoRepository.findByEstudianteAndLectura(estudiante, lectura).get();
            limpiarSesionTest(session, lecturaId);
            redirectAttributes.addFlashAttribute("nota",        r.getNota());
            redirectAttributes.addFlashAttribute("aciertos",    r.getAciertos());
            redirectAttributes.addFlashAttribute("total",       r.getTotalPreguntas());
            redirectAttributes.addFlashAttribute("yaRealizado", true);
            return "redirect:/test/resultado";
        }

        for (int i = 0; i < preguntas.size(); i++) {
            Pregunta p = preguntas.get(i);
            Long opcionEnviada = respuestasIds.get(i);
            boolean valida = p.getOpciones().stream()
                    .anyMatch(o -> o.getId().equals(opcionEnviada));
            if (!valida) {
                limpiarSesionTest(session, lecturaId);
                SecurityAuditLogger.logAccessDenied("/test/calificar (opción no válida)", ip);
                redirectAttributes.addFlashAttribute("error", "Respuesta inválida detectada.");
                return "redirect:/carreras";
            }
        }

        double nota     = testService.calificar(respuestasIds, preguntas);
        int    aciertos = (int) Math.round(nota * preguntas.size() / 100.0);

        int tiempoRealTest = (inicio != null)
                ? (int) ((System.currentTimeMillis() - inicio) / 1000)
                : (tiempoUsado != null ? tiempoUsado : 0);

        Integer tiempoLecturaFinal = tiempoLecturaSegundos;
        if (tiempoLecturaFinal == null || tiempoLecturaFinal <= 0) {
            Long tiempoMs = (Long) session.getAttribute("tiempoLecturaGuardado_" + lecturaId);
            if (tiempoMs != null) tiempoLecturaFinal = (int)(tiempoMs / 1000);
        }

        int ppm = 0;
        double bonus = 0.0;
        String nivelVelocidad = "Normal";
        if (tiempoLecturaFinal != null && tiempoLecturaFinal > 0) {
            int palabras = lectura.getContenido().trim().split("\\s+").length;
            double minutos = tiempoLecturaFinal / 60.0;
            ppm = (int) Math.round(palabras / minutos);

            if (ppm >= PPM_MUY_BUENA) {
                bonus = 10.0;
                nivelVelocidad = "Excelente";
            } else if (ppm >= PPM_BUENA) {
                bonus = 5.0;
                nivelVelocidad = "Buena";
            } else if (ppm >= 150) {
                nivelVelocidad = "Normal";
            } else {
                nivelVelocidad = "Lenta";
            }
        }

        double notaFinal = Math.min(100.0, nota + bonus);

        ResultadoTest resultado = new ResultadoTest(estudiante, lectura, aciertos, preguntas.size(), notaFinal);
        resultado.setTiempoTestSegundos(tiempoRealTest);
        resultado.setTiempoLecturaSegundos(tiempoLecturaFinal);
        resultado.setVelocidadLectoraPpm(ppm > 0 ? ppm : null);
        resultado.setBonusVelocidad(bonus);
        resultadoRepository.save(resultado);

        SecurityAuditLogger.logTestSubmit(auth.getName(), lecturaId, ip);
        limpiarSesionTest(session, lecturaId);

        redirectAttributes.addFlashAttribute("nota",              notaFinal);
        redirectAttributes.addFlashAttribute("notaBase",          nota);
        redirectAttributes.addFlashAttribute("aciertos",          aciertos);
        redirectAttributes.addFlashAttribute("total",             preguntas.size());
        redirectAttributes.addFlashAttribute("tiempoTest",        tiempoRealTest);
        redirectAttributes.addFlashAttribute("tiempoLectura",     tiempoLecturaFinal);
        redirectAttributes.addFlashAttribute("velocidadLectora",  ppm > 0 ? ppm : null);
        redirectAttributes.addFlashAttribute("nivelVelocidad",    nivelVelocidad);
        redirectAttributes.addFlashAttribute("bonusVelocidad",    bonus);
        return "redirect:/test/resultado";
    }

    @GetMapping("/resultado")
    public String mostrarResultado(Model model) {
        return "resultado";
    }

    private void limpiarSesionTest(HttpSession session, Long lecturaId) {
        session.removeAttribute("preguntasTest");
        session.removeAttribute("lecturaId");
        session.removeAttribute("testInicio");
        session.removeAttribute(SESSION_TOKEN);
        if (lecturaId != null) {
            session.removeAttribute("tiempoLectura_" + lecturaId);
            session.removeAttribute("tiempoLecturaGuardado_" + lecturaId);
            session.removeAttribute("lecturaUrlToken_" + lecturaId);
        }
    }
}