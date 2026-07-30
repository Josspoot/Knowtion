package com.mx.tecdesoftware.knowtion.controllers;

import com.mx.tecdesoftware.knowtion.domain.User;
import com.mx.tecdesoftware.knowtion.entities.UserEntity;
import com.mx.tecdesoftware.knowtion.mappers.UserMapper;
import com.mx.tecdesoftware.knowtion.repositories.UserRepository;
import com.mx.tecdesoftware.knowtion.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserService userService;

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
    @ResponseStatus(HttpStatus.CREATED) // Este es tu "if" automático para el caso de éxito (201)
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
    // Aquí documentamos las posibles respuestas para Swagger
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
            @ApiResponse(responseCode = "409", description = "Conflict: El correo del usuario ya está registrado", content = @Content)
    })
    public User crearUsuario(@Valid @RequestBody User nuevoUsuario) {
        return userService.crearUsuario(nuevoUsuario);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Bad Request: Datos inválidos enviados", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found: Usuario con el id asignado no ha sido encontrado", content = @Content)
    })
    @Operation(
            summary = "Borrar un usuario",
            description = "Elimina físicamente a un usuario de la base de datos mediante su ID"
    )
    public void eliminarUsuario(@PathVariable Integer id) {
        userRepository.deleteById(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener perfil de usuario", description = "Busca y retorna los detalles de un usuario en específico mediante su ID")
    public User obtenerUsuarioPorId(@PathVariable Integer id) {
        UserEntity entidad = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return userMapper.toDomain(entidad);
    }
}