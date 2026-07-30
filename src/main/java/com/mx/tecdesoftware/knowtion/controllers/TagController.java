package com.mx.tecdesoftware.knowtion.controllers;

import com.mx.tecdesoftware.knowtion.domain.Tag;
import com.mx.tecdesoftware.knowtion.entities.TagEntity;
import com.mx.tecdesoftware.knowtion.mappers.TagMapper;
import com.mx.tecdesoftware.knowtion.repositories.TagRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper; // Inyectamos el mapper

    public TagController(TagRepository tagRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las etiquetas", description = "Lista todas las etiquetas disponibles para las tareas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de etiquetas obtenida exitosamente")
    })
    public List<Tag> obtenerEtiquetas() {
        List<TagEntity> entidades = tagRepository.findAll();

        return entidades.stream()
                .map(tagMapper::toDomain)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear una etiqueta", description = "Guarda una nueva etiqueta en la base de datos, validando que no exista previamente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Etiqueta creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Bad Request: Datos inválidos o vacíos", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflict: La etiqueta ya existe", content = @Content)
    })
    public Tag crearEtiqueta(@Valid @RequestBody Tag nuevaEtiqueta) {

        // --- VALIDACIÓN DE NEGOCIO: Evitar etiquetas duplicadas ---
        if (tagRepository.existsByNombre(nuevaEtiqueta.getNombre())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "La etiqueta '" + nuevaEtiqueta.getNombre() + "' ya existe en el sistema."
            );
        }

        // Convertimos a entidad para guardar
        TagEntity entidad = tagMapper.toEntity(nuevaEtiqueta);
        TagEntity entidadGuardada = tagRepository.save(entidad);

        // Devolvemos como dominio
        return tagMapper.toDomain(entidadGuardada);
    }
}