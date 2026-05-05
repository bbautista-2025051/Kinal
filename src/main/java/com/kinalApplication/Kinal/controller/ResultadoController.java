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

import java.util.List;

@Controller
@RequestMapping("/mis-notas")
public class ResultadoController {

    @Autowired private ResultadoRepository resultadoRepository;
    @Autowired private EstudianteService estudianteService;

    @GetMapping
    public String verHistorial(Authentication auth, Model model) {
        // Siempre carga los datos del usuario autenticado — nunca de un parámetro externo
        Estudiante estudiante = estudianteService.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

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
            model.addAttribute("aprobados", 0);
        }

        return "misNotas";
    }
}
