package com.mx.tecdesoftware.knowtion.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/** Credenciales que envía el cliente al endpoint POST /auth/login. */
public class LoginRequest {

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El correo electrónico debe tener un formato válido")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    public LoginRequest() {}

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
