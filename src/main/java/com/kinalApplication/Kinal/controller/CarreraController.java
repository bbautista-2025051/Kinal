package com.kinalApplication.Kinal.controller;

import com.kinalApplication.Kinal.model.Carrera;
import com.kinalApplication.Kinal.repository.CarreraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/carreras")
public class CarreraController {

    @Autowired
    private CarreraRepository carreraRepository;

    @Autowired
    private com.kinalApplication.Kinal.service.LecturaService lecturaService;

    @GetMapping
    public String listarCarreras(Model model) {
        model.addAttribute("carreras", carreraRepository.findAll());
        return "carreras";
    }

    @GetMapping("/{carreraId}/lecturas")
    public String verLecturas(@PathVariable Long carreraId, Model model) {
        Carrera carrera = carreraRepository.findById(carreraId)
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada"));
        model.addAttribute("carrera", carrera);
        model.addAttribute("lecturas", lecturaService.findByCarreraId(carreraId));
        return "lecturas";
    }
}