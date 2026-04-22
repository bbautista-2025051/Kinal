package com.kinalApplication.Kinal.controller;

import com.kinalApplication.Kinal.config.SecurityConfig;
import com.kinalApplication.Kinal.util.SecurityAuditLogger;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ModelAndView handleError(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("error");

        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        HttpStatus status  = statusCode != null ? HttpStatus.resolve(statusCode) : null;

        // Registrar accesos denegados (posibles intentos de acceso no autorizado)
        if (status == HttpStatus.FORBIDDEN || status == HttpStatus.UNAUTHORIZED) {
            String ip   = SecurityConfig.getClientIp(request);
            String path = String.valueOf(request.getAttribute("jakarta.servlet.error.request_uri"));
            SecurityAuditLogger.logAccessDenied(path, ip);
        }

        mav.addObject("status", statusCode != null ? statusCode : "");

        // Mensajes genéricos — NUNCA mostrar stack traces ni rutas internas
        if (status == HttpStatus.NOT_FOUND) {
            mav.addObject("error", "Página no encontrada");
            mav.addObject("message", "La dirección que buscas no existe o fue movida.");
        } else if (status == HttpStatus.FORBIDDEN || status == HttpStatus.UNAUTHORIZED) {
            mav.addObject("error", "Acceso denegado");
            mav.addObject("message", "No tienes permiso para acceder a esta sección.");
        } else {
            mav.addObject("error", "Ha ocurrido un error inesperado");
            mav.addObject("message", "Por favor intenta de nuevo o regresa al inicio.");
        }

        return mav;
    }
}
