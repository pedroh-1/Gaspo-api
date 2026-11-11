package com.gaspo.api.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record AvaliacaoConsultaResponseDTO(
        Long id,
        int nota,
        String comentario,
        String especialidade,
        Long profissionalId,
        String nomeProfissional,
        LocalDate dataConsulta,
        LocalTime horarioConsulta,
        String unidade,
        Long pacienteId,
        String nomePaciente,
        LocalDateTime dataAvaliacao
) {
}
