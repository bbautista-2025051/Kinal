package com.kinalApplication.Kinal.service;

import com.kinalApplication.Kinal.model.Estudiante;
import com.kinalApplication.Kinal.repository.EstudianteRepository;
import com.kinalApplication.Kinal.util.SecurityAuditLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class IEstudianteService implements EstudianteService {

    private static final int  MAX_INTENTOS  = 5;
    private static final long BLOQUEO_MINS  = 15L;

    @Autowired private EstudianteRepository estudianteRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public Estudiante registrar(Estudiante estudiante, String rawPassword) {
        // Normalizar email
        estudiante.setEmail(estudiante.getEmail().toLowerCase().trim());
        estudiante.setPassword(passwordEncoder.encode(rawPassword));
        return estudianteRepository.save(estudiante);
    }

    @Override
    public Optional<Estudiante> findByEmail(String email) {
        if (email == null) return Optional.empty();
        return estudianteRepository.findByEmail(email.toLowerCase().trim());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        String emailNorm = email == null ? "" : email.toLowerCase().trim();

        Estudiante est = estudianteRepository.findByEmail(emailNorm)
                .orElseThrow(() -> new UsernameNotFoundException("Credenciales inválidas"));

        // ── Verificar bloqueo de cuenta (nivel BD) ───────────────────────────
        if (est.isCuentaBloqueada()) {
            LocalDateTime bloqueadoHasta = est.getBloqueadoHasta();
            if (bloqueadoHasta != null && LocalDateTime.now().isBefore(bloqueadoHasta)) {
                throw new UsernameNotFoundException("Cuenta bloqueada temporalmente");
            } else {
                // Expiró el bloqueo → desbloquear
                est.setCuentaBloqueada(false);
                est.setIntentosFallidos(0);
                est.setBloqueadoHasta(null);
                estudianteRepository.save(est);
            }
        }

        return User.builder()
                .username(est.getEmail())
                .password(est.getPassword())
                .roles("ESTUDIANTE")
                .accountLocked(est.isCuentaBloqueada())
                .build();
    }

    /** Llamado tras login fallido — incrementa contador y bloquea si necesario */
    @Override
    public void registrarIntentoFallido(String email) {
        estudianteRepository.findByEmail(email.toLowerCase().trim()).ifPresent(est -> {
            int intentos = est.getIntentosFallidos() + 1;
            est.setIntentosFallidos(intentos);
            if (intentos >= MAX_INTENTOS) {
                est.setCuentaBloqueada(true);
                est.setBloqueadoHasta(LocalDateTime.now().plusMinutes(BLOQUEO_MINS));
                SecurityAuditLogger.logLoginFailure(email, "N/A");
            }
            estudianteRepository.save(est);
        });
    }

    /** Llamado tras login exitoso — resetea contador */
    @Override
    public void registrarLoginExitoso(String email) {
        estudianteRepository.findByEmail(email.toLowerCase().trim()).ifPresent(est -> {
            if (est.getIntentosFallidos() > 0) {
                est.setIntentosFallidos(0);
                est.setCuentaBloqueada(false);
                est.setBloqueadoHasta(null);
                estudianteRepository.save(est);
            }
        });
    }
}
