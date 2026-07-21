package com.mx.tecdesoftware.knowtion.repositories;

import com.mx.tecdesoftware.knowtion.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}