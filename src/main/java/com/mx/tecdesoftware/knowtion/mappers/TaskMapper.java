package com.mx.tecdesoftware.knowtion.mappers;

import com.mx.tecdesoftware.knowtion.domain.Task;
import com.mx.tecdesoftware.knowtion.entities.TaskEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class, ProjectMapper.class})
public interface TaskMapper {

    Task toDomain(TaskEntity entity);
    TaskEntity toEntity(Task domain);
}