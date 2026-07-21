package com.mx.tecdesoftware.knowtion.services;

import com.mx.tecdesoftware.knowtion.models.Project;
import com.mx.tecdesoftware.knowtion.models.Task;
import com.mx.tecdesoftware.knowtion.models.User;
import com.mx.tecdesoftware.knowtion.repositories.ProjectRepository;
import com.mx.tecdesoftware.knowtion.repositories.TaskRepository;
import com.mx.tecdesoftware.knowtion.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    public Project crearProyecto(Project project, Long creadorId) {
        User creador = userRepository.findById(creadorId)
                .orElseThrow(() -> new RuntimeException("Creador no encontrado"));

        project.setCreador(creador);
        project.setEstado("ACTIVO");
        return projectRepository.save(project);
    }

    @Transactional
    public Project agregarColaborador(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
        User nuevoColaborador = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        project.getColaboradores().add(nuevoColaborador);
        return projectRepository.save(project);
    }
}