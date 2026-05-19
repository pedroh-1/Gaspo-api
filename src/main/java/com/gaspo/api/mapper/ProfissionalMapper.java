package com.gaspo.api.mapper;

import com.gaspo.api.dto.request.ProfissionalRequestDTO;
import com.gaspo.api.dto.response.ProfissionalResponseDTO;
import com.gaspo.api.dto.response.StatusProfissionalResponseDTO;
import com.gaspo.api.model.gaspo.ProfissionalModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProfissionalMapper {

    @Mapping(target = "unidadeId", source = "unidadeSaude.id")
    @Mapping(target = "nomeUnidade", source = "unidadeSaude.nome")
    @Mapping(target = "enderecoUnidade", source = "unidadeSaude.endereco")
    ProfissionalResponseDTO toResponseDTO(ProfissionalModel model);

    List<ProfissionalResponseDTO> toResponseDTOList(List<ProfissionalModel> models);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "unidadeSaude", ignore = true)
    @Mapping(target = "status", expression = "java(dto.status() != null ? dto.status() : com.gaspo.api.model.enums.StatusProfissional.ATENDENDO)")
    ProfissionalModel toModel(ProfissionalRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "unidadeSaude", ignore = true)
    @Mapping(target = "status", expression = "java(dto.status() != null ? dto.status() : com.gaspo.api.model.enums.StatusProfissional.ATENDENDO)")
    void updateModel(ProfissionalRequestDTO dto, @MappingTarget ProfissionalModel model);

    @Mapping(target = "profissionalId", source = "id")
    @Mapping(target = "nomeProfissional", source = "nome")
    @Mapping(target = "statusAtual", source = "status")
    StatusProfissionalResponseDTO toStatusResponseDTO(ProfissionalModel model);
}
