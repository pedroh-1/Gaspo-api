package com.gaspo.api.mapper;

import com.gaspo.api.dto.request.FuncionarioCadastroDTO;
import com.gaspo.api.dto.response.FuncionarioResponseDTO;
import com.gaspo.api.model.gaspo.FuncionarioModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FuncionarioMapper {

    FuncionarioResponseDTO toResponseDTO(FuncionarioModel model);

    List<FuncionarioResponseDTO> toResponseDTOList(List<FuncionarioModel> models);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "senha", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    FuncionarioModel toModel(FuncionarioCadastroDTO dto);
}
