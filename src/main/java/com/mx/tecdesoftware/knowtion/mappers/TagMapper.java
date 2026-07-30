package com.mx.tecdesoftware.knowtion.mappers;

import com.mx.tecdesoftware.knowtion.domain.Tag;
import com.mx.tecdesoftware.knowtion.entities.TagEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {
    Tag toDomain(TagEntity entity);
    TagEntity toEntity(Tag domain);
}