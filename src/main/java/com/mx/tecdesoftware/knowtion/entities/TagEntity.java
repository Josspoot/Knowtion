package com.mx.tecdesoftware.knowtion.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "tags")
public class TagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String nombre;


    @ManyToMany(mappedBy = "etiquetas")
    private List<TaskEntity> tareas;

    public TagEntity() {}

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

    public List<TaskEntity> getTareas() {
        return tareas;
    }

    public void setTareas(List<TaskEntity> tareas) {
        this.tareas = tareas;
    }
}