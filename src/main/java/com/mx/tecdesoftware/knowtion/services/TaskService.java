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

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper; // Inyectamos tu mapper

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository, UserRepository userRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.taskMapper = taskMapper;
    }

    public Task crearTarea(Task task, Integer projectId, Integer creadorId) {
        ProjectEntity proyecto = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
        UserEntity creador = userRepository.findById(creadorId)
                .orElseThrow(() -> new RuntimeException("Creador no encontrado"));
        TaskEntity entidadNueva = taskMapper.toEntity(task);
        entidadNueva.setProyecto(proyecto);
        entidadNueva.setCreador(creador);
        entidadNueva.setEstado("PENDIENTE");

        TaskEntity entidadGuardada = taskRepository.save(entidadNueva);
        return taskMapper.toDomain(entidadGuardada);
    }

    public Task asignarUsuario(Integer taskId, Integer userId) {
        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
        UserEntity usuario = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        taskEntity.setAsignadoA(usuario);

        TaskEntity entidadGuardada = taskRepository.save(taskEntity);
        return taskMapper.toDomain(entidadGuardada);
    }

    public Task cambiarEstado(Integer taskId, String nuevoEstado) {
        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        taskEntity.setEstado(nuevoEstado);

        TaskEntity entidadGuardada = taskRepository.save(taskEntity);
        return taskMapper.toDomain(entidadGuardada);
    }
}