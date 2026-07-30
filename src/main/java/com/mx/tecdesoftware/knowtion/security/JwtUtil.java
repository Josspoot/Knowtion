package com.mx.tecdesoftware.knowtion.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Clase utilitaria responsable de construir tokens JWT y de extraer
 * la información que contienen validando su firma.
 */
@Component
public class JwtUtil {

    private final SecretKey clave;
    private final long expiracionMs;

    public JwtUtil(@Value("${jwt.secret}") String secreto,
                   @Value("${jwt.expiration-ms}") long expiracionMs) {

        byte[] bytes = secreto.getBytes(StandardCharsets.UTF_8);

        // HS256 exige matemáticamente una clave de al menos 256 bits (32 bytes).
        // Fallamos aquí, al arrancar, con un mensaje claro en lugar de reventar
        // en la primera petición de login.
        if (bytes.length < 32) {
            throw new IllegalStateException(
                    "La propiedad jwt.secret debe tener al menos 32 caracteres para firmar con HS256. " +
                            "Longitud actual: " + bytes.length);
        }

        this.clave = Keys.hmacShaKeyFor(bytes);
        this.expiracionMs = expiracionMs;
    }

    /** Construye un token firmado cuyo "subject" es el correo del usuario. */
    public String generarToken(String email) {
        Date ahora = new Date();

        return Jwts.builder()
                .subject(email)
                .issuedAt(ahora)
                .expiration(new Date(ahora.getTime() + expiracionMs))
                // Forzamos HS256. Si solo pasáramos la clave, jjwt elegiría el
                // algoritmo más fuerte que su longitud permita (HS384 o HS512).
                .signWith(clave, Jwts.SIG.HS256)
                .compact();
    }

    /** Extrae el correo del usuario. Verifica la firma en el proceso. */
    public String extraerEmail(String token) {
        return extraerClaims(token).getSubject();
    }

    /** true si la firma es nuestra y el token no ha expirado ni está manipulado. */
    public boolean esValido(String token) {
        try {
            extraerClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Firma inválida, token expirado, malformado o vacío.
            return false;
        }
    }

    private Claims extraerClaims(String token) {
        return Jwts.parser()
                .verifyWith(clave)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
