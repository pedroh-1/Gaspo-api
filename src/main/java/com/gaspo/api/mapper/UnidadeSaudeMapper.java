package com.gaspo.api.mapper;

import com.gaspo.api.dto.request.UnidadeSaudeRequestDTO;
import com.gaspo.api.dto.response.UnidadeSaudeResumoDTO;
import com.gaspo.api.model.gaspo.UnidadeSaudeModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UnidadeSaudeMapper {

    UnidadeSaudeResumoDTO toResumoDTO(UnidadeSaudeModel model);

    @Mapping(target = "id", ignore = true)
    UnidadeSaudeModel toModel(UnidadeSaudeRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateModel(UnidadeSaudeRequestDTO dto, @MappingTarget UnidadeSaudeModel model);
}
