package com.mx.tecdesoftware.knowtion.services;

import com.mx.tecdesoftware.knowtion.domain.Project;
import com.mx.tecdesoftware.knowtion.entities.ProjectEntity;
import com.mx.tecdesoftware.knowtion.entities.UserEntity;
import com.mx.tecdesoftware.knowtion.mappers.ProjectMapper;
import com.mx.tecdesoftware.knowtion.repositories.ProjectRepository;
import com.mx.tecdesoftware.knowtion.repositories.TaskRepository;
import com.mx.tecdesoftware.knowtion.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final ProjectMapper projectMapper; // 1. Inyectamos tu mapper

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository, TaskRepository taskRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.projectMapper = projectMapper;
    }

    public Project crearProyecto(Project project, Integer creadorId) {
        // 2. Buscamos el creador como Entidad
        UserEntity creador = userRepository.findById(creadorId)
                .orElseThrow(() -> new RuntimeException("Creador no encontrado"));

        ProjectEntity projectEntity = projectMapper.toEntity(project);

        projectEntity.setCreador(creador);
        projectEntity.setEstado("ACTIVO");

        ProjectEntity proyectoGuardado = projectRepository.save(projectEntity);
        return projectMapper.toDomain(proyectoGuardado);
    }

    @Transactional
    public Project agregarColaborador(Integer projectId, Integer userId) {
        ProjectEntity projectEntity = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        UserEntity nuevoColaborador = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        projectEntity.getColaboradores().add(nuevoColaborador);

        ProjectEntity proyectoActualizado = projectRepository.save(projectEntity);
        return projectMapper.toDomain(proyectoActualizado);
    }
}