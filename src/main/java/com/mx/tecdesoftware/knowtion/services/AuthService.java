package com.mx.tecdesoftware.knowtion.services;

import com.mx.tecdesoftware.knowtion.dto.LoginRequest;
import com.mx.tecdesoftware.knowtion.dto.LoginResponse;
import com.mx.tecdesoftware.knowtion.entities.UserEntity;
import com.mx.tecdesoftware.knowtion.repositories.UserRepository;
import com.mx.tecdesoftware.knowtion.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponse login(LoginRequest peticion) {

        // 1. Buscamos al usuario por su correo.
        UserEntity usuario = userRepository.findByEmail(peticion.getEmail())
                .orElseThrow(this::credencialesInvalidas);

        // 2. Comparamos la contraseña plana contra el hash BCrypt de la BD.
        //    matches() re-hashea la plana con la sal que viene dentro del hash;
        //    nunca se desencripta nada.
        boolean coincide = passwordEncoder.matches(
                peticion.getPassword(),
                usuario.getPasswordHash());

        if (!coincide) {
            throw credencialesInvalidas();
        }

        // 3. Credenciales correctas: emitimos el token.
        return new LoginResponse(jwtUtil.generarToken(usuario.getEmail()));
    }

    /**
     * El mismo error para "correo inexistente" y "contraseña incorrecta":
     * si distinguiéramos, un atacante podría averiguar qué correos están
     * registrados probando el endpoint.
     */
    private ResponseStatusException credencialesInvalidas() {
        return new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
    }
}
