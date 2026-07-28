package com.mx.tecdesoftware.knowtion.controllers;

import com.mx.tecdesoftware.knowtion.domain.Project;
import com.mx.tecdesoftware.knowtion.entities.ProjectEntity;
import com.mx.tecdesoftware.knowtion.mappers.ProjectMapper;
import com.mx.tecdesoftware.knowtion.repositories.ProjectRepository;
import com.mx.tecdesoftware.knowtion.services.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public ProjectController(ProjectService projectService, ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectService = projectService;
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los proyectos", description = "Retorna la lista de proyectos registrados en la base de datos")
    public List<Project> obtenerProyectos() {
        List<ProjectEntity> entidades = projectRepository.findAll();

        return entidades.stream()
                .map(projectMapper::toDomain)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear un nuevo proyecto", description = "Crea un proyecto y lo vincula con el ID del usuario creador")
    public Project crearProyecto(@RequestBody Project proyecto, @RequestParam Integer creadorId) {
        // Pasamos el proyecto y el ID del creador al servicio que ya arreglamos
        return projectService.crearProyecto(proyecto, creadorId);
    }

    @PostMapping("/{projectId}/colaboradores/{userId}")
    @Operation(summary = "Agregar un colaborador", description = "Asigna un usuario existente como colaborador de un proyecto")
    public Project agregarColaborador(@PathVariable Integer projectId, @PathVariable Integer userId) {
        // Reutilizamos la lógica transaccional de tu servicio
        return projectService.agregarColaborador(projectId, userId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener proyecto por ID", description = "Busca y retorna los detalles de un proyecto en específico")
    public Project obtenerProyectoPorId(@PathVariable Integer id) {
        ProjectEntity entidad = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
        return projectMapper.toDomain(entidad);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar proyecto", description = "Borra físicamente un proyecto de la base de datos")
    public void eliminarProyecto(@PathVariable Integer id) {
        projectRepository.deleteById(id);
    }
}