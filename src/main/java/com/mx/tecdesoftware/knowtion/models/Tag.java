package com.mx.tecdesoftware.knowtion.models;

import com.mx.tecdesoftware.knowtion.domain.Task;
import com.mx.tecdesoftware.knowtion.entities.TaskEntity;
import jakarta.persistence.*;
import java.util.List;


@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    private String color; // Ej: "#FF0000" o "red"

    // La relación inversa de ManyToMany (la tabla intermedia ya se definió en Task)
    @ManyToMany(mappedBy = "etiquetas")
    private List<TaskEntity> tasks;

    public Tag() {}

    // --- Getters y Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<TaskEntity> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskEntity> tasks) {
        this.tasks = tasks;
    }
}