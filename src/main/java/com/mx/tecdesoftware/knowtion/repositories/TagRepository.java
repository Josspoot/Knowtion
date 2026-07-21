package com.mx.tecdesoftware.knowtion.repositories;

import com.mx.tecdesoftware.knowtion.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

}