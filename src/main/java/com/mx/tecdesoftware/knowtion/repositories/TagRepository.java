package com.mx.tecdesoftware.knowtion.repositories;

import com.mx.tecdesoftware.knowtion.entities.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Integer> {
    boolean existsByNombre(String nombre);
}