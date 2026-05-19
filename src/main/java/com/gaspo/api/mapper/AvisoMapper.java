package com.gaspo.api.mapper;

import com.gaspo.api.dto.request.AvisoRequestDTO;
import com.gaspo.api.dto.response.AvisoResponseDTO;
import com.gaspo.api.model.gaspo.AvisoModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AvisoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataPublicacao", ignore = true)
    AvisoModel toModel(AvisoRequestDTO dto);

    AvisoResponseDTO toResponseDTO(AvisoModel model);
}
