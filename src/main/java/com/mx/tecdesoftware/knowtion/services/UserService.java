package com.mx.tecdesoftware.knowtion.services;

import com.mx.tecdesoftware.knowtion.domain.User;
import com.mx.tecdesoftware.knowtion.entities.UserEntity;
import com.mx.tecdesoftware.knowtion.mappers.UserMapper;
import com.mx.tecdesoftware.knowtion.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper; // Inyectamos MapStruct

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public User crearUsuario(User userDomain) {
        userDomain.setRol("MEMBER");

        // 1. Convertimos el Dominio (Negocio) a Entidad (Base de Datos)
        UserEntity entity = userMapper.toEntity(userDomain);

        // 2. Guardamos en PostgreSQL
        UserEntity entityGuardada = userRepository.save(entity);

        // 3. Convertimos la Entidad de vuelta a Dominio y la retornamos
        return userMapper.toDomain(entityGuardada);
    }

    public User actualizarPerfil(Integer userId, User nuevosDatos) {
        UserEntity usuarioExistente = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuarioExistente.setNombre(nuevosDatos.getNombre());
        UserEntity entityActualizada = userRepository.save(usuarioExistente);

        return userMapper.toDomain(entityActualizada);
    }
}