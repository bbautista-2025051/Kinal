package com.kinalApplication.Kinal.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Logger de auditoría para eventos de seguridad.
 * En producción conectar a un SIEM o exportar a un fichero aparte.
 */
@Component
public class SecurityAuditLogger {

    private static final Logger audit = LoggerFactory.getLogger("SECURITY_AUDIT");
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void logLoginSuccess(String email, String ip) {
        audit.info("[LOGIN_OK]   user={} ip={} ts={}", mask(email), ip, now());
    }

    public static void logLoginFailure(String email, String ip) {
        audit.warn("[LOGIN_FAIL] user={} ip={} ts={}", mask(email), ip, now());
    }

    public static void logRegistro(String email, String ip) {
        audit.info("[REGISTRO]   user={} ip={} ts={}", mask(email), ip, now());
    }

    public static void logTestSubmit(String email, Long lecturaId, String ip) {
        audit.info("[TEST_SUBMIT] user={} lecturaId={} ip={} ts={}", mask(email), lecturaId, ip, now());
    }

    public static void logAccessDenied(String path, String ip) {
        audit.warn("[ACCESS_DENIED] path={} ip={} ts={}", path, ip, now());
    }

    public static void logRateLimit(String ip) {
        audit.warn("[RATE_LIMIT] ip={} ts={}", ip, now());
    }

    // Ofusca el email: a***@kinal.edu.gt
    private static String mask(String email) {
        if (email == null || !email.contains("@")) return "***";
        String[] parts = email.split("@", 2);
        String local = parts[0];
        String masked = local.charAt(0) + "***";
        return masked + "@" + parts[1];
    }

    private static String now() {
        return LocalDateTime.now().format(FMT);
    }
}
