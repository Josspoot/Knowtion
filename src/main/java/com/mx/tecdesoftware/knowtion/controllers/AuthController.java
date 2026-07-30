package com.mx.tecdesoftware.knowtion.controllers;

import com.mx.tecdesoftware.knowtion.dto.LoginRequest;
import com.mx.tecdesoftware.knowtion.dto.LoginResponse;
import com.mx.tecdesoftware.knowtion.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(
            summary = "Iniciar sesión",
            description = "Valida las credenciales del usuario y retorna un token JWT. " +
                    "Copia el valor de 'token' y pégalo en el botón Authorize para consumir los endpoints protegidos.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Credenciales de prueba",
                                    value = """
                                            {
                                                "email": "tony@stark.com",
                                                "password": "1234"
                                            }
                                            """
                            )
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login exitoso, token generado"),
            @ApiResponse(responseCode = "400", description = "Bad Request: faltan campos o el email no es válido", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized: credenciales inválidas", content = @Content)
    })
    public LoginResponse login(@Valid @RequestBody LoginRequest peticion) {
        return authService.login(peticion);
    }
}
