package com.gaspo.api.mapper;

import com.gaspo.api.dto.response.AgendaDisponibilidadeResponseDTO;
import com.gaspo.api.dto.response.ConsultaResponseDTO;
import com.gaspo.api.model.gaspo.AgendaModel;
import com.gaspo.api.model.gaspo.ConsultaModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ConsultaMapper {

    @Mapping(target = "status", expression = "java(model.getStatus() != null ? model.getStatus().name() : null)")
    @Mapping(target = "profissionalId", source = "profissional.id")
    @Mapping(target = "nomeProfissional", source = "profissional.nome")
    @Mapping(target = "especialidadeProfissional", source = "profissional.especialidade")
    @Mapping(target = "unidadeId", source = "unidadeSaude.id")
    @Mapping(target = "nomeUnidade", source = "unidadeSaude.nome")
    @Mapping(target = "enderecoUnidade", source = "unidadeSaude.endereco")
    @Mapping(target = "pacienteId", source = "paciente.id")
    @Mapping(target = "nomePaciente", source = "paciente.nome")
    @Mapping(target = "cpfPaciente", source = "paciente.cpf")
    ConsultaResponseDTO toResponseDTO(ConsultaModel model);

    @Mapping(target = "nomeProfissional", source = "profissionalNome")
    AgendaDisponibilidadeResponseDTO toAgendaDisponibilidadeDTO(AgendaModel model);
}
