package com.kinalApplication.Kinal.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Solo aplica a POST /login
        if ("POST".equalsIgnoreCase(request.getMethod())
                && "/login".equals(request.getServletPath())) {

            String ip = SecurityConfig.getClientIp(request);

            // Si la IP es interna del proxy, no bloquear (evita bloquear a todos)
            if (!isInternalProxyIp(ip) && SecurityConfig.isLocked(ip)) {
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

    private boolean isInternalProxyIp(String ip) {
        if (ip == null) return true;
        return ip.startsWith("10.") ||
                ip.startsWith("172.16.") || ip.startsWith("172.17.") ||
                ip.startsWith("172.18.") || ip.startsWith("172.19.") ||
                ip.startsWith("172.2")   || ip.startsWith("172.3") ||
                ip.startsWith("192.168.") ||
                ip.equals("127.0.0.1") ||
                ip.equals("0:0:0:0:0:0:0:1") ||
                ip.equals("::1");
    }
}
