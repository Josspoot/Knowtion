package com.mx.tecdesoftware.knowtion.repositories;

import com.mx.tecdesoftware.knowtion.entities.ProjectEntity;
import com.mx.tecdesoftware.knowtion.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {
    boolean existsByTituloAndProyecto(String titulo, ProjectEntity proyecto);
}