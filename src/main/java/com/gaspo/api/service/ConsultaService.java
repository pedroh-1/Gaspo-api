package com.gaspo.api.service;

import com.gaspo.api.dto.request.AgendamentoPorNomeDTO;
import com.gaspo.api.dto.request.ConsultaRequestDTO;
import com.gaspo.api.dto.response.ConsultaResponseDTO;
import com.gaspo.api.dto.response.LotacaoResumoDTO;
import com.gaspo.api.dto.response.ProntuarioResumoDTO;
import com.gaspo.api.model.esus.ConsultaModel;
import com.gaspo.api.model.esus.LotacaoModel;
import com.gaspo.api.model.esus.ProntuarioModel;
import com.gaspo.api.model.enums.StatusConsulta;
import com.gaspo.api.repository.esus.ConsultaRepository;
import com.gaspo.api.repository.esus.LotacaoRepository;
import com.gaspo.api.repository.esus.ProfissionalRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private LotacaoRepository lotacaoRepository;

    @Autowired
    private AgendaService agendaService;

    public ConsultaResponseDTO prepararAgendamento(AgendamentoPorNomeDTO dto) {
        List<LotacaoModel> lotacoes = lotacaoRepository.findByProfissionalNomeAndUnidadeSaudeNome(dto.nomeProfissional(), dto.nomeUnidade());

        if (lotacoes.isEmpty()) {
            throw new RuntimeException("Não foi possível localizar o vínculo deste profissional nesta unidade");
        }

        LotacaoModel lotacaoEncontrada = lotacoes.get(0);

        ConsultaRequestDTO request = new ConsultaRequestDTO(dto.data(), lotacaoEncontrada.getId(), dto.prontuarioId());
        return realizarAgendamento(request);
    }

    public ConsultaResponseDTO realizarAgendamento(ConsultaRequestDTO dto) {

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

        consulta.setStatus(0L); // 0 = AGENDADO no e-SUS

        ConsultaModel saved = consultaRepository.save(consulta);
        return toResponseDTO(saved);
    }

    public List<ConsultaResponseDTO> listarTodas() {
        return consultaRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void cancelarConsulta(Long id) {
        ConsultaModel consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada com o ID: " + id));

        consulta.setStatus(4L);

        consultaRepository.save(consulta);
    }

    private ConsultaResponseDTO toResponseDTO(ConsultaModel model) {
        LotacaoResumoDTO lotacaoDTO = null;
        if (model.getLotacao() != null) {
            String nomeProf = null;
            String espProf = null;
            if (model.getLotacao().getProfissional() != null) {
                nomeProf = model.getLotacao().getProfissional().getNome();
                espProf = model.getLotacao().getProfissional().getEspecialidade();
            }
            String nomeUni = null;
            if (model.getLotacao().getUnidadeSaude() != null) {
                nomeUni = model.getLotacao().getUnidadeSaude().getNome();
            }
            lotacaoDTO = new LotacaoResumoDTO(model.getLotacao().getId(), nomeProf, espProf, nomeUni);
        }

        ProntuarioResumoDTO prontuarioDTO = null;
        if (model.getProntuario() != null) {
            String nomePac = null;
            String cpfPac = null;
            if (model.getProntuario().getPaciente() != null) {
                nomePac = model.getProntuario().getPaciente().getNome();
                cpfPac = model.getProntuario().getPaciente().getCpf();
            }
            prontuarioDTO = new ProntuarioResumoDTO(model.getProntuario().getId(), nomePac, cpfPac);
        }

        return new ConsultaResponseDTO(
                model.getId(),
                model.getData(),
                model.getStatus(),
                model.getStatusSincronizacao(),
                model.getForaUbs(),
                lotacaoDTO,
                prontuarioDTO
        );
    }
}
