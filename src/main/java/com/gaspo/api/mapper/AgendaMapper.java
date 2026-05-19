package com.gaspo.api.mapper;

import com.gaspo.api.dto.request.AgendaRequestDTO;
import com.gaspo.api.dto.response.AgendaDisponibilidadeResponseDTO;
import com.gaspo.api.model.gaspo.AgendaModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AgendaMapper {

    @Mapping(target = "nomeProfissional", source = "profissionalNome")
    AgendaDisponibilidadeResponseDTO toResponseDTO(AgendaModel model);

    List<AgendaDisponibilidadeResponseDTO> toResponseDTOList(List<AgendaModel> models);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "profissionalNome", ignore = true)
    AgendaModel toModel(AgendaRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "profissionalNome", ignore = true)
    void updateModel(AgendaRequestDTO dto, @MappingTarget AgendaModel model);
}
