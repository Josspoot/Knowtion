package com.mx.tecdesoftware.knowtion.repositories;

import com.mx.tecdesoftware.knowtion.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}