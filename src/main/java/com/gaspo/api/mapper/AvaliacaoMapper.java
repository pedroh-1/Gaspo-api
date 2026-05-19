package com.gaspo.api.mapper;

import com.gaspo.api.dto.request.AvaliacaoRequestDTO;
import com.gaspo.api.dto.response.AvaliacaoResponseDTO;
import com.gaspo.api.model.gaspo.AvaliacaoModel;
import com.gaspo.api.model.gaspo.ProfissionalModel;
import com.gaspo.api.model.gaspo.UsuarioModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AvaliacaoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataAvaliacao", ignore = true)
    AvaliacaoModel toModel(AvaliacaoRequestDTO dto);

    @Mapping(target = "id", source = "avaliacao.id")
    @Mapping(target = "nota", source = "avaliacao.nota")
    @Mapping(target = "comentario", source = "avaliacao.comentario")
    @Mapping(target = "pacienteId", source = "avaliacao.pacienteId")
    @Mapping(target = "nomePaciente", expression = "java(paciente != null ? paciente.getNome() : \"Paciente não encontrado\")")
    @Mapping(target = "profissionalId", source = "avaliacao.profissionalId")
    @Mapping(target = "nomeProfissional", expression = "java(profissional != null ? profissional.getNome() : \"Profissional não encontrado\")")
    @Mapping(target = "especialidadeProfissional", expression = "java(profissional != null ? profissional.getEspecialidade() : null)")
    @Mapping(target = "dataAvaliacao", source = "avaliacao.dataAvaliacao")
    AvaliacaoResponseDTO toResponseDTO(AvaliacaoModel avaliacao, UsuarioModel paciente, ProfissionalModel profissional);
}
