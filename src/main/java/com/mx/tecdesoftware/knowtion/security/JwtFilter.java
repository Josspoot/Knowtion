package com.mx.tecdesoftware.knowtion.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * Intercepta cada petición HTTP, busca el encabezado Authorization,
 * remueve el prefijo "Bearer " y valida el token. Si es válido, registra
 * al usuario como autenticado en el contexto de Spring Security.
 *
 * No lleva @Component a propósito: se instancia en SecurityConfig para
 * evitar que Spring Boot lo registre además como filtro global del servlet.
 */
public class JwtFilter extends OncePerRequestFilter {

    private static final String ENCABEZADO = "Authorization";
    private static final String PREFIJO = "Bearer ";

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String encabezado = request.getHeader(ENCABEZADO);

        if (encabezado != null && encabezado.startsWith(PREFIJO)) {
            String token = encabezado.substring(PREFIJO.length());

            boolean sinAutenticarAun = SecurityContextHolder.getContext().getAuthentication() == null;

            if (sinAutenticarAun && jwtUtil.esValido(token)) {
                String email = jwtUtil.extraerEmail(token);

                // Credenciales en null: ya no hay contraseña que validar,
                // el token firmado ES la prueba de identidad.
                UsernamePasswordAuthenticationToken autenticacion =
                        new UsernamePasswordAuthenticationToken(email, null, List.of());

                autenticacion.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(autenticacion);
            }
        }

        // Siempre continuamos la cadena. Si el token faltaba o era inválido,
        // el contexto queda vacío y SecurityConfig responderá 401/403.
        filterChain.doFilter(request, response);
    }
}
