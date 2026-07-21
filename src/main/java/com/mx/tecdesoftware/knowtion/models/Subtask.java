package com.mx.tecdesoftware.knowtion.models;

import jakarta.persistence.*;

@Entity
@Table(name = "subtasks")
public class Subtask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private boolean completada = false;

    // Relación: Muchas subtareas pertenecen a una sola Tarea
    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    public Subtask() {}

    // --- Getters y Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public boolean isCompletada() { return completada; }
    public void setCompletada(boolean completada) { this.completada = completada; }
    public Task getTask() { return task; }
    public void setTask(Task task) { this.task = task; }
}