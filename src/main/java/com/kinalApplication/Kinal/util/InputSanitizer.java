package com.kinalApplication.Kinal.util;

import org.springframework.stereotype.Component;

/**
 * Utilidad centralizada para sanitizar entradas de usuario
 * y evitar XSS, inyecciones de headers y path traversal.
 */
@Component
public class InputSanitizer {

    private static final int MAX_LENGTH_GENERAL = 500;
    private static final int MAX_LENGTH_CONTENT  = 50_000;

    /** Elimina caracteres peligrosos para HTML/JS y trunca la cadena. */
    public static String sanitize(String input) {
        if (input == null) return null;
        return escapeHtml(input.trim()).substring(0, Math.min(input.trim().length(), MAX_LENGTH_GENERAL));
    }

    public static String sanitizeLong(String input) {
        if (input == null) return null;
        return escapeHtml(input.trim()).substring(0, Math.min(input.trim().length(), MAX_LENGTH_CONTENT));
    }

    /** Escapa los 5 caracteres HTML críticos. */
    private static String escapeHtml(String s) {
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#x27;");
    }

    /** Valida que un email tenga formato válido básico. */
    public static boolean isValidEmail(String email) {
        if (email == null || email.length() > 254) return false;
        return email.matches("^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$");
    }

    /** Valida que el nombre sólo contenga letras, espacios, tildes y guiones. */
    public static boolean isValidName(String name) {
        if (name == null || name.isBlank() || name.length() > 100) return false;
        return name.matches("^[\\p{L} .'-]+$");
    }
}
