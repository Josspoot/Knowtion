package com.mx.tecdesoftware.knowtion.services;

import com.mx.tecdesoftware.knowtion.models.User;
import com.mx.tecdesoftware.knowtion.repositories.UserRepository;
import org.springframework.stereotype.Service;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User crearUsuario(User user) {
        user.setRol("MEMBER");
        return userRepository.save(user);
    }

    public User actualizarPerfil(Long userId, User nuevosDatos) {
        User usuarioExistente = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuarioExistente.setNombre(nuevosDatos.getNombre());
        return userRepository.save(usuarioExistente);


    }
}
