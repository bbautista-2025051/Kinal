package com.kinalApplication.Kinal.config;

import com.kinalApplication.Kinal.service.EstudianteService;
import com.kinalApplication.Kinal.util.SecurityAuditLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

/**
 * Escucha eventos de autenticación de Spring Security para actualizar
 * contadores de intentos fallidos en la base de datos.
 */
@Component
public class AuthEventListener {

    @Autowired
    private EstudianteService estudianteService;

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent event) {
        String email = event.getAuthentication().getName();
        estudianteService.registrarLoginExitoso(email);
        SecurityAuditLogger.logLoginSuccess(email, "N/A");
        SecurityConfig.registerSuccess(email);
    }

    @EventListener
    public void onFailure(AuthenticationFailureBadCredentialsEvent event) {
        Object principal = event.getAuthentication().getPrincipal();
        if (principal instanceof String email) {
            estudianteService.registrarIntentoFallido(email);
            SecurityAuditLogger.logLoginFailure(email, "N/A");
        }
    }
}
