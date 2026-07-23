package com.mx.tecdesoftware.knowtion.domain;

public class User {

    private Integer id;
    private String nombre;
    private String email;
    private String password; // Nombre diferente (sin "Hash") para el negocio
    private String rol;

    public User() {}

    // --- Getters y Setters ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}