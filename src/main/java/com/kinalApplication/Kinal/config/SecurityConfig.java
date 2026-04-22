package com.kinalApplication.Kinal.config;

import com.kinalApplication.Kinal.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private EstudianteService estudianteService;

    // ── Rate limiting simple en memoria ─────────────────────────────────────
    private static final ConcurrentHashMap<String, AtomicInteger> failedAttempts = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, Long>          lockoutTime    = new ConcurrentHashMap<>();
    private static final int  MAX_ATTEMPTS      = 5;
    private static final long LOCKOUT_MS        = 15 * 60 * 1000L; // 15 minutos

    public static boolean isLocked(String ip) {
        Long locked = lockoutTime.get(ip);
        if (locked == null) return false;
        if (System.currentTimeMillis() - locked > LOCKOUT_MS) {
            lockoutTime.remove(ip);
            failedAttempts.remove(ip);
            return false;
        }
        return true;
    }

    public static void registerFailure(String ip) {
        AtomicInteger attempts = failedAttempts.computeIfAbsent(ip, k -> new AtomicInteger(0));
        if (attempts.incrementAndGet() >= MAX_ATTEMPTS) {
            lockoutTime.put(ip, System.currentTimeMillis());
        }
    }

    public static void registerSuccess(String ip) {
        failedAttempts.remove(ip);
        lockoutTime.remove(ip);
    }

    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return new SimpleUrlAuthenticationFailureHandler("/login?error") {
            @Override
            public void onAuthenticationFailure(HttpServletRequest req,
                                                HttpServletResponse res,
                                                AuthenticationException ex) throws IOException, ServletException {
                String ip = getClientIp(req);
                registerFailure(ip);
                // Timing-safe: delay fijo para no revelar si el email existe
                try { Thread.sleep(500); } catch (InterruptedException ignored) {}
                super.onAuthenticationFailure(req, res, ex);
            }
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // ── CSRF con cookie segura ──────────────────────────────────────
            .csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            )

            // ── Cabeceras de seguridad HTTP ─────────────────────────────────
            .headers(headers -> headers
                // Evita que el contenido se renderice en iframes externos (clickjacking)
                .frameOptions(frame -> frame.sameOrigin())
                // HSTS: fuerza HTTPS durante 1 año
                .httpStrictTransportSecurity(hsts -> hsts
                    .includeSubDomains(true)
                    .maxAgeInSeconds(31536000)
                )
                // No detectar tipo MIME automáticamente
                .contentTypeOptions(ct -> {})
                // XSS Protection
                .xssProtection(xss -> {})
                // Content Security Policy: solo recursos propios + Google Fonts + CDN permitidos
                .contentSecurityPolicy(csp -> csp
                    .policyDirectives(
                        "default-src 'self'; " +
                        "script-src 'self' 'unsafe-inline' https://cdn.jsdelivr.net https://cdnjs.cloudflare.com; " +
                        "style-src 'self' 'unsafe-inline' https://fonts.googleapis.com https://cdnjs.cloudflare.com; " +
                        "font-src 'self' https://fonts.gstatic.com https://cdnjs.cloudflare.com data:; " +
                        "img-src 'self' https://static.wixstatic.com data:; " +
                        "connect-src 'self'; " +
                        "frame-ancestors 'self'; " +
                        "form-action 'self'"
                    )
                )
                // No filtrar el Referer fuera del origen
                .referrerPolicy(ref -> ref
                    .policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
                )
                // Permissions Policy: deshabilitar APIs del navegador no necesarias
                .permissionsPolicy(pp -> pp
                    .policy("camera=(), microphone=(), geolocation=(), payment=(), usb=(), interest-cohort=()")
                )
            )

            // ── Autorización de rutas ───────────────────────────────────────
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**",
                                 "/registro", "/login", "/").permitAll()
                .anyRequest().authenticated()
            )

            // ── Login ───────────────────────────────────────────────────────
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/carreras", true)
                .failureHandler(failureHandler())
                .permitAll()
            )

            // ── Logout seguro ───────────────────────────────────────────────
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .clearAuthentication(true)
                .permitAll()
            )

            // ── Gestión de sesión ───────────────────────────────────────────
            .sessionManagement(session -> session
                // Un solo inicio de sesión simultáneo por cuenta
                .maximumSessions(1)
                    .maxSessionsPreventsLogin(false)
                .and()
                // Regenerar ID de sesión tras login (previene session fixation)
                .sessionFixation().changeSessionId()
            )

            .userDetailsService(estudianteService);

        return http.build();
    }

    // ── Publicar eventos de sesión (necesario para maximumSessions) ─────────
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    // ── Extraer IP real aunque haya proxy ───────────────────────────────────
    public static String getClientIp(HttpServletRequest request) {
        String xf = request.getHeader("X-Forwarded-For");
        if (xf != null && !xf.isBlank()) {
            return xf.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
