package com.mx.tecdesoftware.knowtion.repositories;

import com.mx.tecdesoftware.knowtion.models.Subtask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubtaskRepository extends JpaRepository<Subtask, Long> {
}