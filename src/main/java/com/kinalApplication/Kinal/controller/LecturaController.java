package com.kinalApplication.Kinal.controller;

import com.kinalApplication.Kinal.model.Estudiante;
import com.kinalApplication.Kinal.model.Lectura;
import com.kinalApplication.Kinal.repository.LecturaRepository;
import com.kinalApplication.Kinal.repository.ResultadoRepository;
import com.kinalApplication.Kinal.service.EstudianteService;
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
                             RedirectAttributes ra) {

        // ── Validar id positivo ───────────────────────────────────────────────
        if (id == null || id <= 0) return "redirect:/carreras";

        Lectura lectura = lecturaRepository.findById(id).orElse(null);
        if (lectura == null) {
            ra.addFlashAttribute("error", "Lectura no encontrada.");
            return "redirect:/carreras";
        }

        model.addAttribute("lectura", lectura);

        if (auth != null) {
            Estudiante estudiante = estudianteService.findByEmail(auth.getName()).orElse(null);
            if (estudiante != null) {
                boolean yaRealizado = resultadoRepository.existsByEstudianteAndLectura(estudiante, lectura);
                model.addAttribute("yaRealizado", yaRealizado);
            }
        }

        return "lectura";
    }
}
