package com.mx.tecdesoftware.knowtion.config;

import com.mx.tecdesoftware.knowtion.security.JwtFilter;
import com.mx.tecdesoftware.knowtion.security.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /** Rutas públicas: login y toda la documentación de OpenAPI/Swagger. */
    private static final String[] RUTAS_PUBLICAS = {
            "/auth/login",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/**",
            // Spring hace un forward interno a /error cuando un controlador
            // lanza una excepción. Sin esto, un 401 de credenciales inválidas
            // se convertiría en un 403 engañoso.
            "/error"
    };

    private final JwtUtil jwtUtil;

    public SecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Sin CSRF: es una API REST sin estado, no hay cookies de sesión que proteger.
                .csrf(csrf -> csrf.disable())

                // STATELESS: Spring no crea ni consulta HttpSession.
                // Cada petición se autentica sola, con su token.
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(RUTAS_PUBLICAS).permitAll()
                        .anyRequest().authenticated()
                )

                // Sin token válido responde 401 Unauthorized. El default de
                // Spring Security aquí sería 403 Forbidden, que significa otra
                // cosa: "sé quién eres, pero no te alcanza el permiso".
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(
                                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))

                // Nuestro filtro corre ANTES del de usuario/contraseña de Spring,
                // para que el contexto ya tenga la autenticación cuando se
                // evalúen las reglas de arriba.
                .addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
