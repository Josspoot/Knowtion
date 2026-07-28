package com.mx.tecdesoftware.knowtion.controllers;

import com.mx.tecdesoftware.knowtion.domain.Task;
import com.mx.tecdesoftware.knowtion.entities.TaskEntity;
import com.mx.tecdesoftware.knowtion.mappers.TaskMapper;
import com.mx.tecdesoftware.knowtion.repositories.TaskRepository;
import com.mx.tecdesoftware.knowtion.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final TaskService taskService;


    public TaskController(TaskRepository taskRepository, TaskMapper taskMapper, TaskService taskService) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.taskService = taskService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Obtener todas las tareas",
            description = "Retorna una lista de tareas asignadas/creadas"
    )
    public List<Task> obtenerTareas() {
        List<TaskEntity> entidadesTarea = taskRepository.findAll();

        return entidadesTarea.stream()
                .map(taskMapper::toDomain)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Crear una nueva tarea",
            description = "Crea una tarea y la asocia a un proyecto y a un usuario creador",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Ejemplo de Tarea",
                                    value = """
                                            {
                                                "titulo": "Diseñar base de datos",
                                                "descripcion": "Crear las tablas de SQL para el nuevo módulo",
                                                "prioridad": "ALTA",
                                                "fechaVencimiento": "2026-08-15T10:00:00"
                                            }
                                            """
                            )
                    )
            )
    )
    public Task crearTarea(
            @RequestBody Task tareaNueva,
            @RequestParam Integer projectId,
            @RequestParam Integer creadorId) {

        return taskService.crearTarea(tareaNueva, projectId, creadorId);
    }

    @PutMapping("/{taskId}/asignar/{userId}")
    @Operation(summary = "Asignar tarea a usuario", description = "Asigna una tarea existente a un usuario específico mediante sus IDs")
    public Task asignarUsuario(@PathVariable Integer taskId, @PathVariable Integer userId) {
        return taskService.asignarUsuario(taskId, userId);
    }

    @PatchMapping("/{taskId}/estado")
    @Operation(summary = "Cambiar estado de la tarea", description = "Actualiza únicamente el estado de una tarea (ej. PENDIENTE, EN_PROGRESO, COMPLETADA)")
    public Task cambiarEstado(@PathVariable Integer taskId, @RequestParam String nuevoEstado) {
        return taskService.cambiarEstado(taskId, nuevoEstado);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener tarea por ID", description = "Busca y retorna los detalles de una tarea en específico")
    public Task obtenerTareaPorId(@PathVariable Integer id) {
        TaskEntity entidad = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
        return taskMapper.toDomain(entidad);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar tarea", description = "Borra físicamente una tarea de la base de datos")
    public void eliminarTarea(@PathVariable Integer id) {
        taskRepository.deleteById(id);
    }

}