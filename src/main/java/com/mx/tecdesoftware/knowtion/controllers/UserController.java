package com.mx.tecdesoftware.knowtion.controllers;

import com.mx.tecdesoftware.knowtion.domain.User;
import com.mx.tecdesoftware.knowtion.entities.ProjectEntity;
import com.mx.tecdesoftware.knowtion.entities.UserEntity;
import com.mx.tecdesoftware.knowtion.mappers.UserMapper;
import com.mx.tecdesoftware.knowtion.models.Task;
import com.mx.tecdesoftware.knowtion.repositories.UserRepository;
import com.mx.tecdesoftware.knowtion.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserService userService; // Añadimos el servicio

    // Actualizamos el constructor para inyectar el UserService
    public UserController(UserRepository userRepository, UserMapper userMapper, UserService userService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los usuarios", description = "Retorna una lista completa de todos los usuarios registrados")
    public List<User> obtenerUsuarios() {
        List<UserEntity> entidades = userRepository.findAll();

        return entidades.stream()
                .map(userMapper::toDomain)
                .collect(Collectors.toList());
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Guardar un nuevo usuario",
            description = "Registra un nuevo usuario en la base de datos y lo retorna",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Ejemplo de Usuario",
                                    value = """
                                            {
                                                "nombre": "Tony Stark",
                                                "email": "tony@stark.com",
                                                "password": "password_seguro_123"
                                            }
                                            """
                            )
                    )
            )
    )
    public User crearUsuario(@RequestBody User nuevoUsuario) {
        // Usamos el servicio que ya tiene la lógica de negocio (como asignar el rol)
        return userService.crearUsuario(nuevoUsuario);
    }



}