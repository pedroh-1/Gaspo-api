package com.gaspo.api.service;

import com.gaspo.api.aggregate.ConsultaAggregate;
import com.gaspo.api.dto.request.AgendamentoPorNomeDTO;
import com.gaspo.api.dto.request.ConsultaAgendamentoRequestDTO;
import com.gaspo.api.dto.request.ConsultaRequestDTO;
import com.gaspo.api.dto.response.AgendaDisponibilidadeResponseDTO;
import com.gaspo.api.dto.response.ConsultaResponseDTO;
import com.gaspo.api.dto.response.ConsultaResumoPageDTO;
import com.gaspo.api.dto.response.LotacaoResumoDTO;
import com.gaspo.api.dto.response.ProntuarioOpcaoDTO;
import com.gaspo.api.mapper.ConsultaMapper;
import com.gaspo.api.model.enums.Disponibilidade;
import com.gaspo.api.model.esus.ConsultaModel;
import com.gaspo.api.model.esus.LotacaoModel;
import com.gaspo.api.model.esus.ProntuarioModel;
import com.gaspo.api.model.gaspo.AgendaModel;
import com.gaspo.api.model.gaspo.UsuarioModel;
import com.gaspo.api.repository.esus.ConsultaRepository;
import com.gaspo.api.repository.esus.LotacaoRepository;
import com.gaspo.api.repository.esus.ProntuarioRepository;
import com.gaspo.api.repository.gaspo.AgendaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultaService {

    private static final Long STATUS_AGENDADO = 0L;
    private static final Long STATUS_CANCELADO = 4L;
    private static final ZoneId ZONE_ID = ZoneId.systemDefault();

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private LotacaoRepository lotacaoRepository;

    @Autowired
    private AgendaRepository agendaRepository;

    @Autowired
    private ProntuarioRepository prontuarioRepository;

    @Autowired
    private AgendaService agendaService;

    @Autowired
    private ConsultaMapper consultaMapper;

    @Transactional
    public ConsultaResponseDTO prepararAgendamento(AgendamentoPorNomeDTO dto) {
        List<LotacaoModel> lotacoes = lotacaoRepository.findByProfissionalNomeAndUnidadeSaudeNome(dto.nomeProfissional(), dto.nomeUnidade());

        if (lotacoes.isEmpty()) {
            throw new RuntimeException("Não foi possível localizar o vínculo deste profissional nesta unidade");
        }

        LotacaoModel lotacaoEncontrada = lotacoes.get(0);

        ConsultaRequestDTO request = new ConsultaRequestDTO(dto.data(), lotacaoEncontrada.getId(), dto.prontuarioId());
        return realizarAgendamento(request);
    }

    @Transactional
    public ConsultaResponseDTO prepararAgendamentoParaUsuarioLogado(AgendamentoPorNomeDTO dto, UsuarioModel usuarioLogado) {
        validarUsuarioLogado(usuarioLogado);

        List<LotacaoModel> lotacoes = lotacaoRepository.findByProfissionalNomeAndUnidadeSaudeNome(dto.nomeProfissional(), dto.nomeUnidade());
        if (lotacoes.isEmpty()) {
            throw new RuntimeException("Não foi possível localizar o vínculo deste profissional nesta unidade");
        }

        ProntuarioModel prontuario = prontuarioRepository.findFirstByPacienteId(usuarioLogado.getIdCidadaoEsus())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prontuário do usuário logado não encontrado"));

        ConsultaRequestDTO request = new ConsultaRequestDTO(dto.data(), lotacoes.get(0).getId(), prontuario.getId());
        return realizarAgendamento(request, usuarioLogado);
    }

    @Transactional
    public ConsultaResponseDTO agendarParaUsuarioLogado(ConsultaAgendamentoRequestDTO dto, UsuarioModel usuarioLogado) {
        validarUsuarioLogado(usuarioLogado);
        ProntuarioModel prontuario = prontuarioRepository.findFirstByPacienteId(usuarioLogado.getIdCidadaoEsus())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prontuário do usuário logado não encontrado"));

        ConsultaRequestDTO request = new ConsultaRequestDTO(dto.data(), dto.lotacaoId(), prontuario.getId());
        return realizarAgendamento(request, usuarioLogado);
    }

    @Transactional
    public ConsultaResponseDTO realizarAgendamento(ConsultaRequestDTO dto) {
        return realizarAgendamento(dto, null);
    }

    @Transactional
    public ConsultaResponseDTO realizarAgendamento(ConsultaRequestDTO dto, UsuarioModel usuarioLogado) {
        ConsultaAggregate aggregate = prepararAggregate(dto, usuarioLogado);

        ConsultaModel consulta = aggregate.getConsulta();
        consulta.setStatus(STATUS_AGENDADO); // 0 = AGENDADO no e-SUS
        aggregate.getAgenda().setDisponibilidade(Disponibilidade.OCUPADO);

        ConsultaModel saved = consultaRepository.save(consulta);
        agendaRepository.save(aggregate.getAgenda());
        return consultaMapper.toResponseDTO(saved);
    }

    public List<ConsultaResponseDTO> listarTodas() {
        return consultaRepository.findByStatus(STATUS_AGENDADO).stream()
                .map(consultaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ConsultaResumoPageDTO listarTodas(Pageable pageable) {
        return toPageDTO(consultaRepository.findByStatus(STATUS_AGENDADO, pageable));
    }

    public List<ConsultaResponseDTO> listarAgendamentosAtivos() {
        return consultaRepository.findAgendamentosAtivos(STATUS_AGENDADO, new Date()).stream()
                .map(consultaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ConsultaResumoPageDTO listarAgendamentosAtivos(Pageable pageable) {
        return toPageDTO(consultaRepository.findAgendamentosAtivos(STATUS_AGENDADO, new Date(), pageable));
    }

    public List<ConsultaResponseDTO> listarHistorico() {
        return consultaRepository.findHistorico(STATUS_AGENDADO, new Date(), PageRequest.of(0, 50)).stream()
                .map(consultaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ConsultaResumoPageDTO listarHistorico(Pageable pageable) {
        return toPageDTO(consultaRepository.findHistoricoPage(STATUS_AGENDADO, new Date(), pageable));
    }

    public List<ConsultaResponseDTO> listarHistoricoDoUsuario(UsuarioModel usuarioLogado) {
        return listarHistoricoDoUsuario(usuarioLogado, PageRequest.of(0, 50)).content();
    }

    public ConsultaResumoPageDTO listarHistoricoDoUsuario(UsuarioModel usuarioLogado, Pageable pageable) {
        if (usuarioLogado == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário não autenticado");
        }

        return toPageDTO(consultaRepository.findHistoricoDoPaciente(
                usuarioLogado.getIdCidadaoEsus(),
                STATUS_AGENDADO,
                new Date(),
                pageable
        ));
    }

    public ConsultaResumoPageDTO listarAgendamentosAtivosDoUsuario(UsuarioModel usuarioLogado, Pageable pageable) {
        validarUsuarioLogado(usuarioLogado);
        return toPageDTO(consultaRepository.findAgendamentosAtivosDoPaciente(
                usuarioLogado.getIdCidadaoEsus(),
                STATUS_AGENDADO,
                new Date(),
                pageable
        ));
    }

    public List<LotacaoResumoDTO> listarLotacoesResumo() {
        return lotacaoRepository.findAll().stream()
                .map(consultaMapper::toLotacaoResumoDTO)
                .collect(Collectors.toList());
    }

    public List<ProntuarioOpcaoDTO> listarProntuariosResumo() {
        return prontuarioRepository.findAll().stream()
                .map(consultaMapper::toProntuarioOpcaoDTO)
                .collect(Collectors.toList());
    }

    public List<AgendaDisponibilidadeResponseDTO> listarHorariosDisponiveis(Long profissionalId, LocalDate data) {
        if (profissionalId == null || data == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profissional e data são obrigatórios");
        }

        return agendaRepository.findByProfissionalIdAndDataAndDisponibilidadeOrderByHorarioAsc(
                        profissionalId,
                        data,
                        Disponibilidade.DISPONIVEL
                ).stream()
                .map(consultaMapper::toAgendaDisponibilidadeDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void cancelarConsulta(Long id) {
        cancelarConsulta(id, null);
    }

    @Transactional
    public void cancelarConsulta(Long id, UsuarioModel usuarioLogado) {
        ConsultaModel consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada com o ID: " + id));

        if (usuarioLogado != null && !consultaPertenceAoUsuario(consulta, usuarioLogado)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Esta consulta não pertence ao usuário logado");
        }

        if (!STATUS_AGENDADO.equals(consulta.getStatus())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Somente consultas agendadas podem ser canceladas");
        }

        if (!consulta.getData().after(new Date())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Não é possível cancelar consulta antiga ou já realizada");
        }

        agendaDaConsulta(consulta).ifPresent(agenda -> {
            agenda.setDisponibilidade(Disponibilidade.DISPONIVEL);
            agendaRepository.save(agenda);
        });

        consulta.setStatus(STATUS_CANCELADO);

        consultaRepository.save(consulta);
    }

    private ConsultaAggregate prepararAggregate(ConsultaRequestDTO dto, UsuarioModel usuarioLogado) {
        LocalDateTime dataHora = validarDadosDeAgendamento(dto);

        LotacaoModel lotacao = lotacaoRepository.findById(dto.lotacaoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lotação/profissional não encontrado"));

        ProntuarioModel prontuario = prontuarioRepository.findById(dto.prontuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prontuário não encontrado"));

        if (usuarioLogado != null && !prontuarioPertenceAoUsuario(prontuario, usuarioLogado)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Prontuário não pertence ao usuário logado");
        }

        Long profissionalId = lotacao.getProfissional() != null ? lotacao.getProfissional().getId() : null;
        if (profissionalId == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Lotação sem profissional vinculado");
        }

        AgendaModel agenda = agendaRepository.findFirstByProfissionalIdAndDataAndHorario(
                        profissionalId,
                        dataHora.toLocalDate(),
                        dataHora.toLocalTime()
                )
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "Horário não cadastrado na agenda do profissional"));

        if (!Disponibilidade.DISPONIVEL.equals(agenda.getDisponibilidade())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Horário indisponível para agendamento");
        }

        ConsultaModel consulta = new ConsultaModel();
        consulta.setData(dto.data());
        consulta.setLotacao(lotacao);
        consulta.setProntuario(prontuario);

        return new ConsultaAggregate(consulta, agenda, lotacao, prontuario, usuarioLogado);
    }

    private LocalDateTime validarDadosDeAgendamento(ConsultaRequestDTO dto) {
        LocalDateTime dataHora = toLocalDateTime(dto.data());
        if (dataHora == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data e horário da consulta são obrigatórios");
        }
        if (!dataHora.isAfter(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A consulta deve ser agendada para uma data futura");
        }
        if (dto.lotacaoId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lotação é obrigatória");
        }
        if (dto.prontuarioId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Prontuário é obrigatório");
        }
        return dataHora;
    }

    private java.util.Optional<AgendaModel> agendaDaConsulta(ConsultaModel consulta) {
        if (consulta.getLotacao() == null || consulta.getLotacao().getProfissional() == null || consulta.getData() == null) {
            return java.util.Optional.empty();
        }

        LocalDateTime dataHora = toLocalDateTime(consulta.getData());
        return agendaRepository.findFirstByProfissionalIdAndDataAndHorario(
                consulta.getLotacao().getProfissional().getId(),
                dataHora.toLocalDate(),
                dataHora.toLocalTime()
        );
    }

    private boolean prontuarioPertenceAoUsuario(ProntuarioModel prontuario, UsuarioModel usuarioLogado) {
        return prontuario.getPaciente() != null
                && usuarioLogado.getIdCidadaoEsus() != null
                && usuarioLogado.getIdCidadaoEsus().equals(prontuario.getPaciente().getId());
    }

    private boolean consultaPertenceAoUsuario(ConsultaModel consulta, UsuarioModel usuarioLogado) {
        return consulta.getProntuario() != null && prontuarioPertenceAoUsuario(consulta.getProntuario(), usuarioLogado);
    }

    private LocalDateTime toLocalDateTime(Date data) {
        if (data == null) {
            return null;
        }
        return LocalDateTime.ofInstant(data.toInstant(), ZONE_ID).withSecond(0).withNano(0);
    }

    private ConsultaResumoPageDTO toPageDTO(Page<ConsultaModel> page) {
        return new ConsultaResumoPageDTO(
                page.getContent().stream().map(consultaMapper::toResponseDTO).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }

    private void validarUsuarioLogado(UsuarioModel usuarioLogado) {
        if (usuarioLogado == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário não autenticado");
        }
    }
}
