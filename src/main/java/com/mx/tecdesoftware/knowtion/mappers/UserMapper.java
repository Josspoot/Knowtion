package com.mx.tecdesoftware.knowtion.mappers;

import com.mx.tecdesoftware.knowtion.domain.User;
import com.mx.tecdesoftware.knowtion.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // 1. Convierte de Entidad (BD) a Dominio (Negocio)
    // Le decimos explícitamente a MapStruct cómo conectar las variables con nombres distintos
    @Mapping(source = "passwordHash", target = "password")
    User toDomain(UserEntity entity);

    // 2. Convierte de Dominio (Negocio) a Entidad (BD)
    @Mapping(source = "password", target = "passwordHash")
    UserEntity toEntity(User domain);
}