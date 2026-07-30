package com.mx.tecdesoftware.knowtion.controllers;

import com.mx.tecdesoftware.knowtion.domain.Project;
import com.mx.tecdesoftware.knowtion.services.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/creador/{creadorId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear un proyecto", description = "Crea un nuevo proyecto asignado a un creador específico")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Proyecto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Bad Request: Datos inválidos o incompletos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found: El usuario creador no existe", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflict: Ya existe un proyecto con ese título", content = @Content)
    })
    public Project crearProyecto(@Valid @RequestBody Project project, @PathVariable Integer creadorId) {
        return projectService.crearProyecto(project, creadorId);
    }

    @PutMapping("/{projectId}/colaboradores/{userId}")
    @Operation(summary = "Agregar colaborador", description = "Agrega un usuario existente como colaborador a un proyecto")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Colaborador agregado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Not Found: El proyecto o el usuario no existen", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflict: El usuario ya es colaborador de este proyecto", content = @Content)
    })
    public Project agregarColaborador(@PathVariable Integer projectId, @PathVariable Integer userId) {
        return projectService.agregarColaborador(projectId, userId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar proyecto", description = "Borra un proyecto de la base de datos siempre y cuando no tenga tareas activas")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Proyecto eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Not Found: El proyecto no existe", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflict: El proyecto tiene tareas pendientes y no puede ser borrado", content = @Content)
    })
    public void eliminarProyecto(@PathVariable Integer id) {
        projectService.eliminarProyecto(id);
    }
}