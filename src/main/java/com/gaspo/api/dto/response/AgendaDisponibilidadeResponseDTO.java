package com.gaspo.api.dto.response;

import com.gaspo.api.model.enums.Disponibilidade;

import java.time.LocalDate;
import java.time.LocalTime;

public record AgendaDisponibilidadeResponseDTO(
        Long id,
        LocalDate data,
        LocalTime horario,
        Disponibilidade disponibilidade,
        Long profissionalId,
        String nomeProfissional
) {
}
