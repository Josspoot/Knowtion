package com.mx.tecdesoftware.knowtion.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

/**
 * Declara el esquema de seguridad "bearerAuth" para que Swagger UI muestre
 * el botón Authorize y agregue el encabezado Authorization: Bearer <token>
 * a las peticiones de los endpoints protegidos.
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Knowtion API", version = "1.0",
                description = "API de gestión de proyectos, tareas y notas."),
        security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER,
        description = "Pega aquí el token que devuelve POST /auth/login (sin la palabra Bearer)."
)
public class OpenApiConfig {
}
