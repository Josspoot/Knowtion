package com.mx.tecdesoftware.knowtion.domain;

import java.time.LocalDateTime;

// Cero anotaciones de JPA
public class Task {

    private Integer id;
    private String titulo;
    private String descripcion;
    private String prioridad;
    private String estado;
    private LocalDateTime fechaVencimiento;

    // Usamos las clases puras de dominio
    private Project proyecto;
    private User creador;
    private User asignadoA;

    public Task() {}

    // --- Genera aquí los Getters y Setters ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String prioridad) { this.prioridad = prioridad; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public LocalDateTime getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDateTime fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
    public Project getProyecto() { return proyecto; }
    public void setProyecto(Project proyecto) { this.proyecto = proyecto; }
    public User getCreador() { return creador; }
    public void setCreador(User creador) { this.creador = creador; }
    public User getAsignadoA() { return asignadoA; }
    public void setAsignadoA(User asignadoA) { this.asignadoA = asignadoA; }


}