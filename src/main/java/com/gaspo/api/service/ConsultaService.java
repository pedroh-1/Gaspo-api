package com.gaspo.api.service;

import com.gaspo.api.dto.request.AgendamentoPorNomeDTO;
import com.gaspo.api.dto.request.ConsultaRequestDTO;
import com.gaspo.api.dto.response.ConsultaResponseDTO;
import com.gaspo.api.dto.response.LotacaoResumoDTO;
import com.gaspo.api.mapper.ConsultaMapper;
import com.gaspo.api.model.esus.ConsultaModel;
import com.gaspo.api.model.esus.LotacaoModel;
import com.gaspo.api.model.gaspo.UsuarioModel;
import com.gaspo.api.repository.esus.ConsultaRepository;
import com.gaspo.api.repository.esus.LotacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultaService {

    private static final Long STATUS_AGENDADO = 0L;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private LotacaoRepository lotacaoRepository;

    @Autowired
    private AgendaService agendaService;

    @Autowired
    private ConsultaMapper consultaMapper;

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

        ConsultaModel consulta = consultaMapper.toModel(dto);
        consulta.setStatus(STATUS_AGENDADO); // 0 = AGENDADO no e-SUS

        ConsultaModel saved = consultaRepository.save(consulta);
        return consultaMapper.toResponseDTO(saved);
    }

    public List<ConsultaResponseDTO> listarTodas() {
        return consultaRepository.findByStatus(STATUS_AGENDADO).stream()
                .map(consultaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<ConsultaResponseDTO> listarAgendamentosAtivos() {
        return consultaRepository.findAgendamentosAtivos(STATUS_AGENDADO, new Date()).stream()
                .map(consultaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<ConsultaResponseDTO> listarHistorico() {
        return consultaRepository.findHistorico(STATUS_AGENDADO, new Date(), PageRequest.of(0, 50)).stream()
                .map(consultaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<ConsultaResponseDTO> listarHistoricoDoUsuario(UsuarioModel usuarioLogado) {
        if (usuarioLogado == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário não autenticado");
        }

        return consultaRepository.findByProntuarioPacienteIdOrderByDataDesc(usuarioLogado.getIdCidadaoEsus()).stream()
                .map(consultaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<LotacaoResumoDTO> listarLotacoesResumo() {
        return lotacaoRepository.findAll().stream()
                .map(consultaMapper::toLotacaoResumoDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void cancelarConsulta(Long id) {
        ConsultaModel consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada com o ID: " + id));

        consulta.setStatus(4L);

        consultaRepository.save(consulta);
    }
}
