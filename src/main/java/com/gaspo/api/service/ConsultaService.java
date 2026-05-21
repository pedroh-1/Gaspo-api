package com.gaspo.api.service;

import com.gaspo.api.dto.request.AgendamentoPorNomeDTO;
import com.gaspo.api.dto.request.ConsultaAgendamentoRequestDTO;
import com.gaspo.api.dto.request.ConsultaRequestDTO;
import com.gaspo.api.dto.response.AgendaDisponibilidadeResponseDTO;
import com.gaspo.api.dto.response.ConsultaResponseDTO;
import com.gaspo.api.dto.response.ConsultaResumoPageDTO;
import com.gaspo.api.mapper.ConsultaMapper;
import com.gaspo.api.model.enums.Disponibilidade;
import com.gaspo.api.model.enums.StatusConsulta;
import com.gaspo.api.model.gaspo.AgendaModel;
import com.gaspo.api.model.gaspo.ConsultaModel;
import com.gaspo.api.model.gaspo.ProfissionalModel;
import com.gaspo.api.model.gaspo.UsuarioModel;
import com.gaspo.api.repository.AgendaRepository;
import com.gaspo.api.repository.ConsultaRepository;
import com.gaspo.api.repository.ProfissionalRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class ConsultaService {

    private static final ZoneId ZONE_ID = ZoneId.systemDefault();

    private final ConsultaRepository consultaRepository;
    private final AgendaRepository agendaRepository;
    private final ProfissionalRepository profissionalRepository;
    private final ConsultaMapper consultaMapper;

    public ConsultaService(ConsultaRepository consultaRepository,
                           AgendaRepository agendaRepository,
                           ProfissionalRepository profissionalRepository,
                           ConsultaMapper consultaMapper) {
        this.consultaRepository = consultaRepository;
        this.agendaRepository = agendaRepository;
        this.profissionalRepository = profissionalRepository;
        this.consultaMapper = consultaMapper;
    }

    @Transactional
    public ConsultaResponseDTO prepararAgendamentoParaUsuarioLogado(AgendamentoPorNomeDTO dto, UsuarioModel usuarioLogado) {
        validarUsuarioLogado(usuarioLogado);

        ProfissionalModel profissional = profissionalRepository.findByNomeContainingIgnoreCase(dto.nomeProfissional()).stream()
                .filter(item -> dto.nomeUnidade() == null
                        || item.getUnidadeSaude() == null
                        || dto.nomeUnidade().equalsIgnoreCase(item.getUnidadeSaude().getNome()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profissional não encontrado"));

        return realizarAgendamento(new ConsultaRequestDTO(dto.data(), profissional.getId()), usuarioLogado);
    }

    @Transactional
    public ConsultaResponseDTO agendarParaUsuarioLogado(ConsultaAgendamentoRequestDTO dto, UsuarioModel usuarioLogado) {
        validarUsuarioLogado(usuarioLogado);
        return realizarAgendamento(new ConsultaRequestDTO(dto.data(), dto.profissionalId()), usuarioLogado);
    }

    @Transactional
    public ConsultaResponseDTO realizarAgendamento(ConsultaRequestDTO dto, UsuarioModel usuarioLogado) {
        validarUsuarioLogado(usuarioLogado);
        LocalDateTime dataHora = validarDadosDeAgendamento(dto);

        ProfissionalModel profissional = profissionalRepository.findById(dto.profissionalId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profissional não encontrado"));

        AgendaModel agenda = agendaRepository.findFirstByProfissionalIdAndDataAndHorario(
                        profissional.getId(),
                        dataHora.toLocalDate(),
                        dataHora.toLocalTime()
                )
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "Horário não cadastrado na agenda do profissional"));

        if (!Disponibilidade.DISPONIVEL.equals(agenda.getDisponibilidade())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Horário indisponível para agendamento");
        }

        ConsultaModel consulta = new ConsultaModel();
        consulta.setData(dto.data());
        consulta.setStatus(StatusConsulta.AGENDADA);
        consulta.setPaciente(usuarioLogado);
        consulta.setProfissional(profissional);
        consulta.setUnidadeSaude(profissional.getUnidadeSaude());

        agenda.setDisponibilidade(Disponibilidade.OCUPADO);
        ConsultaModel saved = consultaRepository.save(consulta);
        agendaRepository.save(agenda);

        return consultaMapper.toResponseDTO(saved);
    }

    public ConsultaResumoPageDTO listarTodas(Pageable pageable) {
        return toPageDTO(consultaRepository.findByStatus(StatusConsulta.AGENDADA, pageable));
    }

    public ConsultaResumoPageDTO listarAgendamentosAtivos(Pageable pageable) {
        return toPageDTO(consultaRepository.findAgendamentosAtivos(StatusConsulta.AGENDADA, new Date(), pageable));
    }

    public ConsultaResumoPageDTO listarHistorico(Pageable pageable) {
        return toPageDTO(consultaRepository.findHistoricoPage(StatusConsulta.AGENDADA, new Date(), pageable));
    }

    public List<ConsultaResponseDTO> listarHistoricoDoUsuario(UsuarioModel usuarioLogado) {
        return listarHistoricoDoUsuario(usuarioLogado, PageRequest.of(0, 50)).content();
    }

    public ConsultaResumoPageDTO listarHistoricoDoUsuario(UsuarioModel usuarioLogado, Pageable pageable) {
        validarUsuarioLogado(usuarioLogado);
        return toPageDTO(consultaRepository.findHistoricoDoPaciente(
                usuarioLogado.getId(),
                StatusConsulta.AGENDADA,
                new Date(),
                pageable
        ));
    }

    public ConsultaResumoPageDTO listarAgendamentosAtivosDoUsuario(UsuarioModel usuarioLogado, Pageable pageable) {
        validarUsuarioLogado(usuarioLogado);
        return toPageDTO(consultaRepository.findAgendamentosAtivosDoPaciente(
                usuarioLogado.getId(),
                StatusConsulta.AGENDADA,
                new Date(),
                pageable
        ));
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
                .toList();
    }

    @Transactional
    public void cancelarConsulta(Long id, UsuarioModel usuarioLogado) {
        validarUsuarioLogado(usuarioLogado);

        ConsultaModel consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Consulta não encontrada"));

        if (consulta.getPaciente() == null || !usuarioLogado.getId().equals(consulta.getPaciente().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Esta consulta não pertence ao usuário logado");
        }

        if (!StatusConsulta.AGENDADA.equals(consulta.getStatus())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Somente consultas agendadas podem ser canceladas");
        }

        if (!consulta.getData().after(new Date())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Não é possível cancelar consulta antiga ou já realizada");
        }

        agendaDaConsulta(consulta).ifPresent(agenda -> {
            agenda.setDisponibilidade(Disponibilidade.DISPONIVEL);
            agendaRepository.save(agenda);
        });

        consulta.setStatus(StatusConsulta.CANCELADA);
        consultaRepository.save(consulta);
    }

    private LocalDateTime validarDadosDeAgendamento(ConsultaRequestDTO dto) {
        LocalDateTime dataHora = toLocalDateTime(dto.data());
        if (dataHora == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data e horário da consulta são obrigatórios");
        }
        if (!dataHora.isAfter(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A consulta deve ser agendada para uma data futura");
        }
        if (dto.profissionalId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profissional é obrigatório");
        }
        return dataHora;
    }

    private java.util.Optional<AgendaModel> agendaDaConsulta(ConsultaModel consulta) {
        if (consulta.getProfissional() == null || consulta.getData() == null) {
            return java.util.Optional.empty();
        }

        LocalDateTime dataHora = toLocalDateTime(consulta.getData());
        return agendaRepository.findFirstByProfissionalIdAndDataAndHorario(
                consulta.getProfissional().getId(),
                dataHora.toLocalDate(),
                dataHora.toLocalTime()
        );
    }

    private LocalDateTime toLocalDateTime(Date data) {
        if (data == null) {
            return null;
        }
        return LocalDateTime.ofInstant(data.toInstant(), ZONE_ID).withSecond(0).withNano(0);
    }

    private void validarUsuarioLogado(UsuarioModel usuarioLogado) {
        if (usuarioLogado == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário não autenticado");
        }
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
}
