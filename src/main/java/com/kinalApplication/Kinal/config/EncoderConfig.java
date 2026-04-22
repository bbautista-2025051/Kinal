package com.kinalApplication.Kinal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class EncoderConfig {

    /**
     * BCrypt con factor de costo 12 (2^12 = 4096 iteraciones).
     * Factor 10 es el default; 12 hace brute-force ~4x más lento
     * sin impacto notable para el usuario legítimo.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
