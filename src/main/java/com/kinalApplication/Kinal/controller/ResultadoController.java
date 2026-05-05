package com.kinalApplication.Kinal.controller;

import com.kinalApplication.Kinal.model.Estudiante;
import com.kinalApplication.Kinal.model.ResultadoTest;
import com.kinalApplication.Kinal.repository.ResultadoRepository;
import com.kinalApplication.Kinal.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/mis-notas")
public class ResultadoController {

    @Autowired private ResultadoRepository resultadoRepository;
    @Autowired private EstudianteService estudianteService;

    @GetMapping
    public String verHistorial(Authentication auth, Model model, RedirectAttributes ra) {
        // Verificar que el usuario esté autenticado
        if (auth == null || !auth.isAuthenticated()) {
            ra.addFlashAttribute("error", "Debes iniciar sesión para ver tus notas.");
            return "redirect:/login";
        }

        // Obtener el estudiante autenticado
        Estudiante estudiante = estudianteService.findByEmail(auth.getName())
                .orElse(null);

        // Si por alguna razón no se encuentra el estudiante (poco probable)
        if (estudiante == null) {
            ra.addFlashAttribute("error", "No se encontró tu cuenta. Contacta al administrador.");
            return "redirect:/login";
        }

        List<ResultadoTest> resultados = resultadoRepository.findByEstudianteOrderByFechaDesc(estudiante);
        model.addAttribute("resultados", resultados);

        if (!resultados.isEmpty()) {
            double mejorNota = resultados.stream().mapToDouble(ResultadoTest::getNota).max().orElse(0);
            double promedio  = resultados.stream().mapToDouble(ResultadoTest::getNota).average().orElse(0);
            long   aprobados = resultados.stream().filter(r -> r.getNota() >= 60).count();
            model.addAttribute("mejorNota", (int) mejorNota);
            model.addAttribute("promedio",  Math.round(promedio));
            model.addAttribute("aprobados", aprobados);
        } else {
            model.addAttribute("mejorNota", 0);
            model.addAttribute("promedio",  0);
            model.addAttribute("aprobados", 0L);
        }

        return "misNotas";
    }
}