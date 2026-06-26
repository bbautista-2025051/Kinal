package com.kinalApplication.Kinal.service;

import com.kinalApplication.Kinal.model.Estudiante;
import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.Optional;

public interface EstudianteService extends UserDetailsService {
    Estudiante registrar(Estudiante estudiante, String rawPassword);
    Optional<Estudiante> findByEmail(String email);
    void registrarIntentoFallido(String email);
    void registrarLoginExitoso(String email);
}
