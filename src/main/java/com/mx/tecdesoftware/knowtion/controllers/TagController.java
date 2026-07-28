package com.mx.tecdesoftware.knowtion.controllers;

import com.mx.tecdesoftware.knowtion.models.Tag;
import com.mx.tecdesoftware.knowtion.repositories.TagRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagRepository tagRepository;

    public TagController(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las etiquetas", description = "Lista todas las etiquetas disponibles para las tareas")
    public List<Tag> obtenerEtiquetas() {
        return tagRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear una etiqueta", description = "Guarda una nueva etiqueta en la base de datos")
    public Tag crearEtiqueta(@RequestBody Tag nuevaEtiqueta) {
        return tagRepository.save(nuevaEtiqueta);
    }
}
