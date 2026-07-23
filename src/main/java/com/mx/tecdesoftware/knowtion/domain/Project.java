package com.mx.tecdesoftware.knowtion.domain; // <- Carpeta domain

import java.time.LocalDateTime;
import java.util.List;

public class Project {

    private Integer id;
    private String titulo;
    private String descripcion;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String estado;

    private User creador;
    private List<User> colaboradores;

    public Project() {}

    // --- Getters y Setters ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDateTime fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDateTime getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDateTime fechaFin) { this.fechaFin = fechaFin; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public User getCreador() { return creador; }
    public void setCreador(User creador) { this.creador = creador; }
    public List<User> getColaboradores() { return colaboradores; }
    public void setColaboradores(List<User> colaboradores) { this.colaboradores = colaboradores; }
}