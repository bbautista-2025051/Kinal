package com.kinalApplication.Kinal.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro que bloquea IPs que han superado el límite de intentos fallidos
 * de login antes de que siquiera lleguen al controlador.
 */
@Component
public class RateLimitFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Solo aplica a POST /login (autenticación)
        if ("POST".equalsIgnoreCase(request.getMethod())
                && "/login".equals(request.getServletPath())) {

            String ip = SecurityConfig.getClientIp(request);
            if (SecurityConfig.isLocked(ip)) {
                response.setStatus(429);
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write(
                    "<!DOCTYPE html><html><body style='font-family:sans-serif;text-align:center;padding:4rem'>" +
                    "<h2>Demasiados intentos fallidos</h2>" +
                    "<p>Tu acceso ha sido bloqueado temporalmente (15 minutos).</p>" +
                    "<a href='/login'>Volver</a></body></html>"
                );
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
