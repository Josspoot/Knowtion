package com.mx.tecdesoftware.knowtion.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    private String prioridad; // Ej: "BAJA", "MEDIA", "ALTA"
    private String estado;    // Ej: "PENDIENTE", "EN_PROCESO", "COMPLETADA"
    private LocalDateTime fechaVencimiento;

    // Relación: Muchas tareas pertenecen a un solo proyecto
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    // Relación: Creador de la tarea
    @ManyToOne
    @JoinColumn(name = "creador_id")
    private User creador;

    // Relación: Usuario asignado para resolver la tarea
    @ManyToOne
    @JoinColumn(name = "asignado_a_id")
    private User asignadoA;

    // Relación de muchos a muchos con Etiquetas (Tags)
    @ManyToMany
    @JoinTable(
            name = "task_tags",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> etiquetas;

    public Task() {}

    // --- Getters y Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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
    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }
    public User getCreador() { return creador; }
    public void setCreador(User creador) { this.creador = creador; }
    public User getAsignadoA() { return asignadoA; }
    public void setAsignadoA(User asignadoA) { this.asignadoA = asignadoA; }
    public List<Tag> getEtiquetas() { return etiquetas; }
    public void setEtiquetas(List<Tag> etiquetas) { this.etiquetas = etiquetas; }
}