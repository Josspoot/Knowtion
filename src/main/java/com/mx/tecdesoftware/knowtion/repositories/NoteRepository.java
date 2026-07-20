package com.mx.tecdesoftware.knowtion.repositories;

import com.mx.tecdesoftware.knowtion.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
}