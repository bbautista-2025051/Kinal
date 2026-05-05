package com.kinalApplication.Kinal.controller;

import com.kinalApplication.Kinal.config.SecurityConfig;
import com.kinalApplication.Kinal.dto.RegistroDTO;
import com.kinalApplication.Kinal.model.Carrera;
import com.kinalApplication.Kinal.model.Estudiante;
import com.kinalApplication.Kinal.repository.CarreraRepository;
import com.kinalApplication.Kinal.service.EstudianteService;
import com.kinalApplication.Kinal.util.InputSanitizer;
import com.kinalApplication.Kinal.util.SecurityAuditLogger;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired private EstudianteService estudianteService;
    @Autowired private CarreraRepository carreraRepository;

    /**
     * ✅ Ahora la raíz muestra directamente la página de inicio (index)
     * en lugar de redirigir a /login.
     */
    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        String ip = SecurityConfig.getClientIp(request);
        if (SecurityConfig.isLocked(ip)) {
            model.addAttribute("bloqueado", true);
        }
        return "login";
    }

    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("registroDTO", new RegistroDTO());
        model.addAttribute("carreras", carreraRepository.findAll());
        return "registro";
    }

    @PostMapping("/registro")
    public String procesarRegistro(@Valid @ModelAttribute("registroDTO") RegistroDTO dto,
                                   BindingResult result,
                                   Model model,
                                   HttpServletRequest request,
                                   RedirectAttributes ra) {

        String ip = SecurityConfig.getClientIp(request);

        // ── Validación extra: formato de nombre ──────────────────────────────
        if (!result.hasFieldErrors("nombreCompleto")
                && !InputSanitizer.isValidName(dto.getNombreCompleto())) {
            result.rejectValue("nombreCompleto", "error.nombre",
                    "El nombre solo puede contener letras, espacios y guiones.");
        }

        // ── Validación extra: email solo dominios institucionales ────────────
        if (!result.hasFieldErrors("email") && dto.getEmail() != null) {
            String emailLower = dto.getEmail().toLowerCase().trim();
            if (!emailLower.endsWith("@kinal.edu.gt") && !emailLower.endsWith("@fundacionkinal.org")) {
                result.rejectValue("email", "error.email",
                        "Solo se permiten correos institucionales (@kinal.edu.gt).");
            }
        }

        if (result.hasErrors()) {
            model.addAttribute("carreras", carreraRepository.findAll());
            return "registro";
        }

        // ── Timing-safe: misma respuesta si email ya existe ──────────────────
        if (estudianteService.findByEmail(dto.getEmail().toLowerCase().trim()).isPresent()) {
            // No revelar que el email ya existe (enumeración de usuarios)
            ra.addFlashAttribute("exito", "Cuenta creada exitosamente. Ahora inicia sesión.");
            SecurityAuditLogger.logRegistro(dto.getEmail(), ip);
            return "redirect:/login";
        }

        Carrera carrera = carreraRepository.findById(dto.getCarreraId())
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada"));

        Estudiante estudiante = new Estudiante();
        // Sanitizar antes de guardar
        estudiante.setEmail(dto.getEmail().toLowerCase().trim());
        estudiante.setNombreCompleto(InputSanitizer.sanitize(dto.getNombreCompleto()));
        estudiante.setCarrera(carrera);

        estudianteService.registrar(estudiante, dto.getPassword());
        SecurityAuditLogger.logRegistro(dto.getEmail(), ip);

        ra.addFlashAttribute("exito", "Cuenta creada exitosamente. Ahora inicia sesión.");
        return "redirect:/login";
    }
}