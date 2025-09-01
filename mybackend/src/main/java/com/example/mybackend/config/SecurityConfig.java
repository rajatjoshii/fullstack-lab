package com.example.mybackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    // Allow all requests (temporary)
      @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        // For APIs, disable CSRF (Swagger won't send tokens)
        .csrf(csrf -> csrf.disable())

        // Needed for H2 console to render in an iframe
        .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))

        // Allow unauthenticated access to Swagger + H2 (adjust as needed)
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/swagger-ui.html",
                "/h2-console/**"
            ).permitAll()
            // you can keep everything open for now while building:
            .anyRequest().permitAll()
        );

    return http.build();
}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
