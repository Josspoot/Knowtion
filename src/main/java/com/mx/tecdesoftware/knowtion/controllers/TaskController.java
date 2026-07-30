package com.mx.tecdesoftware.knowtion.controllers;

import com.mx.tecdesoftware.knowtion.domain.Task;
import com.mx.tecdesoftware.knowtion.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/proyecto/{projectId}/creador/{creadorId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear una tarea", description = "Crea una nueva tarea dentro de un proyecto específico")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Tarea creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Bad Request: Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found: El proyecto o creador no existen", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflict: Ya existe una tarea con ese título en el proyecto", content = @Content)
    })
    public Task crearTarea(@Valid @RequestBody Task task, @PathVariable Integer projectId, @PathVariable Integer creadorId) {
        return taskService.crearTarea(task, projectId, creadorId);
    }

    @PutMapping("/{taskId}/asignar/{userId}")
    @Operation(summary = "Asignar tarea a usuario", description = "Asigna una tarea existente a un usuario específico mediante sus IDs")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tarea asignada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Not Found: La tarea o el usuario no existen", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflict: La tarea ya tiene un usuario asignado previamente", content = @Content)
    })
    public Task asignarUsuario(@PathVariable Integer taskId, @PathVariable Integer userId) {
        return taskService.asignarUsuario(taskId, userId);
    }

    @PatchMapping("/{taskId}/estado")
    @Operation(summary = "Cambiar estado de la tarea", description = "Actualiza el estado de una tarea (ej. PENDIENTE, EN_PROGRESO, COMPLETADA)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado de la tarea actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Not Found: La tarea no existe", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflict: No se puede modificar una tarea que ya está COMPLETADA", content = @Content)
    })
    public Task cambiarEstado(@PathVariable Integer taskId, @RequestParam String nuevoEstado) {
        return taskService.cambiarEstado(taskId, nuevoEstado);
    }
}