package com.mx.tecdesoftware.knowtion.services;

import com.mx.tecdesoftware.knowtion.models.Project;
import com.mx.tecdesoftware.knowtion.models.Task;
import com.mx.tecdesoftware.knowtion.models.User;
import com.mx.tecdesoftware.knowtion.repositories.ProjectRepository;
import com.mx.tecdesoftware.knowtion.repositories.TaskRepository;
import com.mx.tecdesoftware.knowtion.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public Task crearTarea(Task task, Long projectId, Long creadorId) {
        Project proyecto = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
        User creador = userRepository.findById(creadorId)
                .orElseThrow(() -> new RuntimeException("Creador no encontrado"));

        task.setProject(proyecto);
        task.setCreador(creador);
        task.setEstado("PENDIENTE");
        return taskRepository.save(task);
    }

    public Task asignarUsuario(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
        User usuario = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        task.setAsignadoA(usuario);
        return taskRepository.save(task);
    }

    public Task cambiarEstado(Long taskId, String nuevoEstado) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        task.setEstado(nuevoEstado);
        return taskRepository.save(task);
    }
}