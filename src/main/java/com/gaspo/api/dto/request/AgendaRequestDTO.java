package com.gaspo.api.dto.request;

import com.gaspo.api.model.enums.Disponibilidade;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record AgendaRequestDTO(
        @NotNull
        LocalDate data,

        @NotNull
        LocalTime horario,

        @NotNull
        Disponibilidade disponibilidade,

        @NotNull
        Long profissionalId
) {
}
