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
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
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

    private static final ConcurrentHashMap<String, AtomicInteger> failedAttempts = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, Long>          lockoutTime    = new ConcurrentHashMap<>();
    private static final int  MAX_ATTEMPTS = 5;
    private static final long LOCKOUT_MS   = 15 * 60 * 1000L;

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
                try { Thread.sleep(500); } catch (InterruptedException ignored) {}
                super.onAuthenticationFailure(req, res, ex);
            }
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // Spring Security 6: usar CsrfTokenRequestAttributeHandler para compatibilidad
        // con Thymeleaf. Esto expone _csrf como atributo de request para los templates.
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        // CRITICO: setCsrfRequestAttributeName(null) hace que Spring Security resuelva
        // el token de forma lazy, lo que es necesario para que funcione con cookie CSRF
        // detrás de un proxy como Railway.
        requestHandler.setCsrfRequestAttributeName(null);

        // CookieCsrfTokenRepository.withHttpOnlyFalse() permite que JS lea la cookie
        // para incluirla en requests AJAX, y Thymeleaf la lee del request attribute.
        CookieCsrfTokenRepository tokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();

        http
                .csrf(csrf -> csrf
                        .csrfTokenRepository(tokenRepository)
                        .csrfTokenRequestHandler(requestHandler)
                )

                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())
                        .httpStrictTransportSecurity(hsts -> hsts
                                .includeSubDomains(true)
                                .maxAgeInSeconds(31536000)
                        )
                        .contentTypeOptions(ct -> {})
                        .xssProtection(xss -> {})
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
                        .referrerPolicy(ref -> ref
                                .policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
                        )
                        .permissionsPolicy(pp -> pp
                                .policy("camera=(), microphone=(), geolocation=(), payment=(), usb=(), interest-cohort=()")
                        )
                )

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**",
                                "/registro", "/login", "/").permitAll()
                        .requestMatchers("/actuator/health").permitAll()
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/carreras", true)
                        .failureHandler(failureHandler())
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .clearAuthentication(true)
                        .permitAll()
                )

                .sessionManagement(session -> session
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)
                        .and()
                        .sessionFixation().changeSessionId()
                )

                .userDetailsService(estudianteService);

        return http.build();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    public static String getClientIp(HttpServletRequest request) {
        String xf = request.getHeader("X-Forwarded-For");
        if (xf != null && !xf.isBlank()) {
            return xf.split(",")[0].trim();
        }
        String xri = request.getHeader("X-Real-IP");
        if (xri != null && !xri.isBlank()) {
            return xri.trim();
        }
        return request.getRemoteAddr();
    }
}