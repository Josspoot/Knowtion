package com.mx.tecdesoftware.knowtion.repositories;

import com.mx.tecdesoftware.knowtion.entities.UserEntity; // <- Importación correcta
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> { // <- Cambiar a UserEntity
    Optional<UserEntity> findByEmail(String email);
}