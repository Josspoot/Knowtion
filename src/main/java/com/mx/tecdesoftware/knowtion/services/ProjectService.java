package com.mx.tecdesoftware.knowtion.services;

import com.mx.tecdesoftware.knowtion.domain.Project;
import com.mx.tecdesoftware.knowtion.entities.ProjectEntity;
import com.mx.tecdesoftware.knowtion.entities.UserEntity;
import com.mx.tecdesoftware.knowtion.mappers.ProjectMapper;
import com.mx.tecdesoftware.knowtion.repositories.ProjectRepository;
import com.mx.tecdesoftware.knowtion.repositories.TaskRepository;
import com.mx.tecdesoftware.knowtion.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final ProjectMapper projectMapper;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository, TaskRepository taskRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.projectMapper = projectMapper;
    }

    public Project crearProyecto(Project project, Integer creadorId) {

        // CORRECCIÓN APLICADA: Validamos usando 'Titulo' para que coincida con tu base de datos
        if (projectRepository.existsByTitulo(project.getTitulo())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe un proyecto con el título: " + project.getTitulo()
            );
        }

        UserEntity creador = userRepository.findById(creadorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Creador no encontrado con el ID: " + creadorId));

        ProjectEntity projectEntity = projectMapper.toEntity(project);

        projectEntity.setCreador(creador);
        projectEntity.setEstado("ACTIVO");

        ProjectEntity proyectoGuardado = projectRepository.save(projectEntity);
        return projectMapper.toDomain(proyectoGuardado);
    }

    @Transactional
    public Project agregarColaborador(Integer projectId, Integer userId) {
        ProjectEntity projectEntity = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proyecto no encontrado con el ID: " + projectId));

        UserEntity nuevoColaborador = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado con el ID: " + userId));

        // VALIDACIÓN: Evitar agregar al mismo colaborador dos veces
        if (projectEntity.getColaboradores().contains(nuevoColaborador)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "El usuario ya es colaborador de este proyecto."
            );
        }

        projectEntity.getColaboradores().add(nuevoColaborador);

        ProjectEntity proyectoActualizado = projectRepository.save(projectEntity);
        return projectMapper.toDomain(proyectoActualizado);
    }

    public void eliminarProyecto(Integer id) {
        ProjectEntity proyecto = projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proyecto no encontrado con ID: " + id));

        // VALIDACIÓN: Evitar borrar proyectos que tengan tareas asociadas
        if (proyecto.getTareas() != null && !proyecto.getTareas().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se puede eliminar el proyecto porque aún tiene " + proyecto.getTareas().size() + " tarea(s) asociada(s)."
            );
        }

        projectRepository.deleteById(id);
    }
}