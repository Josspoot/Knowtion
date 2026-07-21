package com.mx.tecdesoftware.knowtion.models;
import com.mx.tecdesoftware.knowtion.entities.UserEntity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String estado; // Ej: "ACTIVO", "PAUSADO", "FINALIZADO"

    // Relación: Muchos proyectos pueden pertenecer a un usuario creador
    @ManyToOne
    @JoinColumn(name = "creador_id")
    private UserEntity creador;

    // Relación: Un proyecto tiene muchos colaboradores, un colaborador tiene muchos proyectos
    @ManyToMany
    @JoinTable(
            name = "project_collaborators",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserEntity> colaboradores;

    public Project() {
        this.fechaInicio = LocalDateTime.now();
    }

    // --- Getters y Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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
    public UserEntity getCreador() { return creador; }
    public void setCreador(UserEntity creador) { this.creador = creador; }
    public List<UserEntity> getColaboradores() { return colaboradores; }
    public void setColaboradores(List<UserEntity> colaboradores) { this.colaboradores = colaboradores; }
}