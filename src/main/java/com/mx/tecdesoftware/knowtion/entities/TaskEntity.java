package com.mx.tecdesoftware.knowtion.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tasks")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    private String prioridad;
    private String estado;
    private LocalDateTime fechaVencimiento;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectEntity proyecto;

    @ManyToOne
    @JoinColumn(name = "creador_id")
    private UserEntity creador;

    @ManyToOne
    @JoinColumn(name = "asignado_a_id")
    private UserEntity asignadoA;

    // Cambiamos a TagEntity para que se conecte correctamente con la base de datos
    @ManyToMany
    @JoinTable(
            name = "task_tags",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<TagEntity> etiquetas;

    public TaskEntity() {}

    // --- Getters y Setters ---

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDateTime fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public ProjectEntity getProyecto() {
        return proyecto;
    }

    public void setProyecto(ProjectEntity proyecto) {
        this.proyecto = proyecto;
    }

    public UserEntity getCreador() {
        return creador;
    }

    public void setCreador(UserEntity creador) {
        this.creador = creador;
    }

    public UserEntity getAsignadoA() {
        return asignadoA;
    }

    public void setAsignadoA(UserEntity asignadoA) {
        this.asignadoA = asignadoA;
    }

    // --- Nuevos Getters y Setters actualizados para TagEntity ---
    public List<TagEntity> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<TagEntity> etiquetas) {
        this.etiquetas = etiquetas;
    }
}