package com.mx.tecdesoftware.knowtion.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class User {

    private Integer id;

    @NotBlank(message = "El nombre no puede estar vacío ni tener solo espacios")
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El correo electrónico debe tener un formato válido (ej. usuario@mail.com)")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres por seguridad")
    private String password; // Nombre diferente (sin "Hash") para el negocio

    private String rol;

    public User() {}

    // --- Getters y Setters ---
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}