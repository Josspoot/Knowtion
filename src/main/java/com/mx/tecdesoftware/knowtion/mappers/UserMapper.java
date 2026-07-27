package com.mx.tecdesoftware.knowtion.mappers;

import com.mx.tecdesoftware.knowtion.domain.User;
import com.mx.tecdesoftware.knowtion.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "passwordHash", target = "password")
    User toDomain(UserEntity entity);

    @Mapping(source = "password", target = "passwordHash")
    UserEntity toEntity(User domain);
}