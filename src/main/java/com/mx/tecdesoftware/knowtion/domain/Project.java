package com.mx.tecdesoftware.knowtion.domain; // <- Carpeta domain

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;

public class Project {

    @Schema(description = "ID del proyecto (Autogenerado)", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Schema(description = "Título principal del proyecto", example = "Mi Proyecto Definitivo")
    private String titulo;

    @Schema(description = "Detalles del proyecto", example = "Descripción detallada de lo que vamos a hacer en este proyecto.")
    private String descripcion;

    @Schema(description = "Fecha en la que arranca el proyecto", example = "2026-08-01T08:00:00", type = "string")
    private LocalDateTime fechaInicio;

    @Schema(description = "Fecha límite para terminar", example = "2026-12-31T23:59:59", type = "string")
    private LocalDateTime fechaFin;

    @Schema(description = "Estado actual", example = "ACTIVO")
    private String estado;

    @Schema(description = "Usuario creador del proyecto", accessMode = Schema.AccessMode.READ_ONLY)
    private User creador;

    @Schema(description = "Lista de colaboradores del proyecto", accessMode = Schema.AccessMode.READ_ONLY)
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