package com.gaspo.api.mapper;

import com.gaspo.api.dto.response.AgendaDisponibilidadeResponseDTO;
import com.gaspo.api.dto.response.ConsultaResponseDTO;
import com.gaspo.api.model.gaspo.AgendaModel;
import com.gaspo.api.model.gaspo.ConsultaModel;
import com.gaspo.api.model.gaspo.ProfissionalModel;
import com.gaspo.api.model.gaspo.UnidadeSaudeModel;
import com.gaspo.api.model.gaspo.UsuarioModel;
import org.springframework.stereotype.Component;

@Component
public class ConsultaMapper {

    public ConsultaResponseDTO toResponseDTO(ConsultaModel model) {
        if (model == null) {
            return null;
        }

        ProfissionalModel profissional = model.getProfissional();
        UsuarioModel paciente = model.getPaciente();
        UnidadeSaudeModel unidade = model.getUnidadeSaude();

        return new ConsultaResponseDTO(
                model.getId(),
                model.getData(),
                model.getStatus() != null ? model.getStatus().name() : null,
                profissional != null ? profissional.getId() : null,
                profissional != null ? profissional.getNome() : null,
                profissional != null ? profissional.getEspecialidade() : null,
                unidade != null ? unidade.getId() : null,
                unidade != null ? unidade.getNome() : null,
                unidade != null ? unidade.getEndereco() : null,
                paciente != null ? paciente.getId() : null,
                paciente != null ? paciente.getNome() : null,
                paciente != null ? paciente.getCpf() : null
        );
    }

    public AgendaDisponibilidadeResponseDTO toAgendaDisponibilidadeDTO(AgendaModel model) {
        if (model == null) {
            return null;
        }

        return new AgendaDisponibilidadeResponseDTO(
                model.getId(),
                model.getData(),
                model.getHorario(),
                model.getDisponibilidade(),
                model.getProfissionalId(),
                model.getProfissionalNome()
        );
    }
}
