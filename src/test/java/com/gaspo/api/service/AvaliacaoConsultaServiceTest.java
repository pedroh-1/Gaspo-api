package com.gaspo.api.service;

import com.gaspo.api.dto.request.AvaliacaoConsultaRequestDTO;
import com.gaspo.api.model.gaspo.AvaliacaoConsultaModel;
import com.gaspo.api.model.gaspo.ProfissionalModel;
import com.gaspo.api.model.gaspo.UsuarioModel;
import com.gaspo.api.repository.AvaliacaoConsultaRepository;
import com.gaspo.api.repository.ProfissionalRepository;
import com.gaspo.api.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class AvaliacaoConsultaServiceTest {

    private AvaliacaoConsultaRepository repository;
    private ProfissionalRepository profissionais;
    private AvaliacaoConsultaService service;
    private UsuarioModel paciente;

    @BeforeEach
    void setUp() {
        repository = mock(AvaliacaoConsultaRepository.class);
        profissionais = mock(ProfissionalRepository.class);
        service = new AvaliacaoConsultaService(repository, profissionais, mock(UsuarioRepository.class));
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(profissionais.findById(anyLong())).thenReturn(Optional.empty());

        ProfissionalModel matheus = new ProfissionalModel();
        matheus.setId(6L);
        matheus.setNome("Dr. Matheus Castro");
        matheus.setEspecialidade("Clínico Geral");
        when(profissionais.findById(6L)).thenReturn(Optional.of(matheus));

        paciente = new UsuarioModel();
        paciente.setId(10L);
        paciente.setNome("Maria");
    }

    private AvaliacaoConsultaRequestDTO pedido(int nota, LocalDate data, Long profissionalId) {
        return new AvaliacaoConsultaRequestDTO(nota, "  Fui bem atendida.  ", " ", profissionalId, data,
                LocalTime.of(9, 0), "UBS Centro");
    }

    @Test
    void registraComPacienteDoTokenEEspecialidadeDoProfissional() {
        var resposta = service.avaliar(pedido(5, LocalDate.now(), 6L), paciente);

        assertEquals(5, resposta.nota());
        assertEquals("Fui bem atendida.", resposta.comentario());
        assertEquals("Clínico Geral", resposta.especialidade());
        assertEquals("Dr. Matheus Castro", resposta.nomeProfissional());
        assertEquals(10L, resposta.pacienteId());
        assertNotNull(resposta.dataAvaliacao());
        verify(repository).save(any(AvaliacaoConsultaModel.class));
    }

    @Test
    void recusaPedidosInvalidos() {
        assertEquals(HttpStatus.UNAUTHORIZED, assertThrows(ResponseStatusException.class,
                () -> service.avaliar(pedido(5, LocalDate.now(), 6L), null)).getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, assertThrows(ResponseStatusException.class,
                () -> service.avaliar(pedido(0, LocalDate.now(), 6L), paciente)).getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, assertThrows(ResponseStatusException.class,
                () -> service.avaliar(pedido(4, LocalDate.now().plusDays(1), 6L), paciente)).getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, assertThrows(ResponseStatusException.class,
                () -> service.avaliar(pedido(4, LocalDate.now(), 99L), paciente)).getStatusCode());
        verify(repository, never()).save(any());
    }
}
