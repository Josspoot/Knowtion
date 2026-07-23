package com.mx.tecdesoftware.knowtion.mappers;

import com.mx.tecdesoftware.knowtion.domain.Project;
import com.mx.tecdesoftware.knowtion.entities.ProjectEntity;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ProjectMapper {

    Project toDomain(ProjectEntity entity);
    ProjectEntity toEntity(Project domain);
}