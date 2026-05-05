package com.kinalApplication.Kinal.config;

import com.kinalApplication.Kinal.service.EstudianteService;
import com.kinalApplication.Kinal.util.SecurityAuditLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import jakarta.servlet.http.HttpServletRequest;

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
        // Limpiar el bloqueo por IP usando la IP real del cliente
        try {
            ServletRequestAttributes attrs =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attrs != null) {
                HttpServletRequest req = attrs.getRequest();
                String ip = SecurityConfig.getClientIp(req);
                SecurityConfig.registerSuccess(ip);
            }
        } catch (Exception ignored) {}
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
