package com.gaspo.api.mapper;

import com.gaspo.api.dto.request.ConsultaRequestDTO;
import com.gaspo.api.dto.response.AgendaDisponibilidadeResponseDTO;
import com.gaspo.api.dto.response.ConsultaResponseDTO;
import com.gaspo.api.dto.response.LotacaoResumoDTO;
import com.gaspo.api.dto.response.ProntuarioOpcaoDTO;
import com.gaspo.api.dto.response.ProntuarioResumoDTO;
import com.gaspo.api.model.gaspo.AgendaModel;
import com.gaspo.api.model.esus.ConsultaModel;
import com.gaspo.api.model.esus.LotacaoModel;
import com.gaspo.api.model.esus.ProntuarioModel;
import org.springframework.stereotype.Component;

@Component
public class ConsultaMapper {

    public ConsultaModel toModel(ConsultaRequestDTO dto) {
        ConsultaModel consulta = new ConsultaModel();
        consulta.setData(dto.data());

        if (dto.lotacaoId() != null) {
            LotacaoModel lotacao = new LotacaoModel();
            lotacao.setId(dto.lotacaoId());
            consulta.setLotacao(lotacao);
        }

        if (dto.prontuarioId() != null) {
            ProntuarioModel prontuario = new ProntuarioModel();
            prontuario.setId(dto.prontuarioId());
            consulta.setProntuario(prontuario);
        }

        return consulta;
    }

    public ConsultaResponseDTO toResponseDTO(ConsultaModel model) {
        return new ConsultaResponseDTO(
                model.getId(),
                model.getData(),
                model.getStatus(),
                model.getStatusSincronizacao(),
                model.getForaUbs(),
                toLotacaoResumoDTO(model.getLotacao()),
                toProntuarioResumoDTO(model.getProntuario())
        );
    }

    public LotacaoResumoDTO toLotacaoResumoDTO(LotacaoModel model) {
        if (model == null) {
            return null;
        }

        String nomeProfissional = null;
        String especialidadeProfissional = null;
        if (model.getProfissional() != null) {
            nomeProfissional = model.getProfissional().getNome();
            especialidadeProfissional = model.getProfissional().getEspecialidade();
        }

        Long unidadeId = null;
        String nomeUnidade = null;
        String enderecoUnidade = null;
        if (model.getUnidadeSaude() != null) {
            unidadeId = model.getUnidadeSaude().getId();
            nomeUnidade = model.getUnidadeSaude().getNome();
            enderecoUnidade = model.getUnidadeSaude().getEnderecoFormatado();
        }

        return new LotacaoResumoDTO(
                model.getId(),
                nomeProfissional,
                especialidadeProfissional,
                unidadeId,
                nomeUnidade,
                enderecoUnidade
        );
    }

    public ProntuarioResumoDTO toProntuarioResumoDTO(ProntuarioModel model) {
        if (model == null) {
            return null;
        }

        String nomePaciente = null;
        String cpfPaciente = null;
        if (model.getPaciente() != null) {
            nomePaciente = model.getPaciente().getNome();
            cpfPaciente = model.getPaciente().getCpf();
        }

        return new ProntuarioResumoDTO(model.getId(), nomePaciente, cpfPaciente);
    }

    public ProntuarioOpcaoDTO toProntuarioOpcaoDTO(ProntuarioModel model) {
        if (model == null) {
            return null;
        }

        Long pacienteId = null;
        String nomePaciente = null;
        String cpfPaciente = null;
        if (model.getPaciente() != null) {
            pacienteId = model.getPaciente().getId();
            nomePaciente = model.getPaciente().getNome();
            cpfPaciente = model.getPaciente().getCpf();
        }

        return new ProntuarioOpcaoDTO(model.getId(), pacienteId, nomePaciente, cpfPaciente);
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
