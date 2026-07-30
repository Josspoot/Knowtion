package com.mx.tecdesoftware.knowtion.services;

import com.mx.tecdesoftware.knowtion.domain.User;
import com.mx.tecdesoftware.knowtion.entities.UserEntity;
import com.mx.tecdesoftware.knowtion.mappers.UserMapper;
import com.mx.tecdesoftware.knowtion.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    // Inyectamos el repositorio, el mapper y nuestro nuevo motor de encriptación
    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public User crearUsuario(User nuevoUsuario) {
        // 1. Validamos si el correo ya existe
        boolean existeCorreo = userRepository.existsByEmail(nuevoUsuario.getEmail());

        if (existeCorreo) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "El correo del usuario ya está registrado"
            );
        }

        // 2. Encriptamos la contraseña original y la reemplazamos
        String passwordEncriptada = passwordEncoder.encode(nuevoUsuario.getPassword());
        nuevoUsuario.setPassword(passwordEncriptada);

        // 3. Convertimos a entidad, guardamos en BD y devolvemos como dominio puro
        UserEntity entidad = userMapper.toEntity(nuevoUsuario);
        UserEntity entidadGuardada = userRepository.save(entidad);

        return userMapper.toDomain(entidadGuardada);
    }

    public List<User> obtenerUsuarios() {
        List<UserEntity> entidades = userRepository.findAll();

        return entidades.stream()
                .map(userMapper::toDomain)
                .collect(Collectors.toList());
    }

    public void eliminarUsuario(Integer id) {
        userRepository.deleteById(id);
    }
}