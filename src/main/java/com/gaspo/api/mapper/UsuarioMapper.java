package com.gaspo.api.mapper;

import com.gaspo.api.dto.response.UsuarioResponseDTO;
import com.gaspo.api.model.gaspo.UsuarioModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioResponseDTO toResponseDTO(UsuarioModel model);

    List<UsuarioResponseDTO> toResponseDTOList(List<UsuarioModel> models);
}
