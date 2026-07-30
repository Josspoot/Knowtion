package com.mx.tecdesoftware.knowtion.services;

import com.mx.tecdesoftware.knowtion.domain.Task;
import com.mx.tecdesoftware.knowtion.entities.ProjectEntity;
import com.mx.tecdesoftware.knowtion.entities.TaskEntity;
import com.mx.tecdesoftware.knowtion.entities.UserEntity;
import com.mx.tecdesoftware.knowtion.mappers.TaskMapper;
import com.mx.tecdesoftware.knowtion.repositories.ProjectRepository;
import com.mx.tecdesoftware.knowtion.repositories.TaskRepository;
import com.mx.tecdesoftware.knowtion.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository, UserRepository userRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.taskMapper = taskMapper;
    }

    public Task crearTarea(Task task, Integer projectId, Integer creadorId) {
        ProjectEntity proyecto = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proyecto no encontrado con ID: " + projectId));

        UserEntity creador = userRepository.findById(creadorId)
                .orElseThrow(() ->new ResponseStatusException(HttpStatus.NOT_FOUND, "Creador no encontrado con ID: " + creadorId));

        // VALIDACIÓN 1: Evitar tareas duplicadas en el mismo proyecto
        if (taskRepository.existsByTituloAndProyecto(task.getTitulo(), proyecto)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe una tarea con el título '" + task.getTitulo() + "' en este proyecto."
            );
        }

        TaskEntity entidadNueva = taskMapper.toEntity(task);
        entidadNueva.setProyecto(proyecto);
        entidadNueva.setCreador(creador);
        entidadNueva.setEstado("PENDIENTE");

        TaskEntity entidadGuardada = taskRepository.save(entidadNueva);
        return taskMapper.toDomain(entidadGuardada);
    }

    public Task asignarUsuario(Integer taskId, Integer userId) {
        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Tarea no encontrada con ID: " + taskId));

        // VALIDACIÓN 2: Evitar reasignar si ya tiene un usuario
        if (taskEntity.getAsignadoA() != null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esta tarea ya fue asignada previamente al usuario con ID: " + taskEntity.getAsignadoA().getId()
            );
        }

        UserEntity usuario = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Usuario no encontrado con ID: " + userId));

        taskEntity.setAsignadoA(usuario);

        TaskEntity entidadGuardada = taskRepository.save(taskEntity);
        return taskMapper.toDomain(entidadGuardada);
    }

    public Task cambiarEstado(Integer taskId, String nuevoEstado) {
        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Tarea no encontrada con ID: " + taskId));


        if ("COMPLETADA".equalsIgnoreCase(taskEntity.getEstado())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se puede cambiar el estado de una tarea que ya está COMPLETADA."
            );
        }

        taskEntity.setEstado(nuevoEstado);

        TaskEntity entidadGuardada = taskRepository.save(taskEntity);
        return taskMapper.toDomain(entidadGuardada);
    }
}