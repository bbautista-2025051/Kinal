package com.kinalApplication.Kinal.controller;

import com.kinalApplication.Kinal.model.Estudiante;
import com.kinalApplication.Kinal.model.Lectura;
import com.kinalApplication.Kinal.repository.LecturaRepository;
import com.kinalApplication.Kinal.repository.ResultadoRepository;
import com.kinalApplication.Kinal.service.EstudianteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/lectura")
public class LecturaController {

    @Autowired private LecturaRepository lecturaRepository;
    @Autowired private ResultadoRepository resultadoRepository;
    @Autowired private EstudianteService estudianteService;

    @GetMapping("/{id}")
    public String verLectura(@PathVariable Long id,
                             Model model,
                             Authentication auth,
                             HttpSession session,
                             RedirectAttributes ra) {

        if (id == null || id <= 0) return "redirect:/carreras";

        Lectura lectura = lecturaRepository.findById(id).orElse(null);
        if (lectura == null) {
            ra.addFlashAttribute("error", "Lectura no encontrada.");
            return "redirect:/carreras";
        }

        model.addAttribute("lectura", lectura);

        boolean yaRealizado = false;
        if (auth != null) {
            Estudiante estudiante = estudianteService.findByEmail(auth.getName()).orElse(null);
            if (estudiante != null) {
                yaRealizado = resultadoRepository.existsByEstudianteAndLectura(estudiante, lectura);
            }
        }
        model.addAttribute("yaRealizado", yaRealizado);

        String urlToken = java.util.UUID.randomUUID().toString();
        session.setAttribute("lecturaUrlToken_" + id, urlToken);

        session.setAttribute("lecturaInicio_" + id, System.currentTimeMillis());
        // Almacenar también con la clave que usa TestController para compatibilidad
        session.setAttribute("tiempoLectura_" + id, 0L); // Se actualizará vía cookie/JS

        String testUrl = "/test/" + id + "?token=" + urlToken;
        model.addAttribute("testUrl",   testUrl);
        model.addAttribute("urlToken",  urlToken);

        int palabras = lectura.getContenido().trim().split("\\s+").length;
        int minEstimado = (int) Math.ceil(palabras / 200.0); // 200 ppm promedio
        model.addAttribute("palabrasLectura",  palabras);
        model.addAttribute("minEstimadoLectura", minEstimado);

        return "lectura";
    }
}
