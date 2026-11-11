package com.gaspo.api.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;

/** O paciente vem do token de login, não do corpo da requisição. */
public record AvaliacaoConsultaRequestDTO(
        @Min(1) @Max(5)
        int nota,

        @Size(max = 500, message = "O comentário deve ter até 500 caracteres")
        String comentario,

        String especialidade,

        @NotNull(message = "Profissional é obrigatório")
        Long profissionalId,

        @NotNull(message = "Data da consulta é obrigatória")
        @PastOrPresent(message = "A data da consulta não pode ser no futuro")
        LocalDate dataConsulta,

        LocalTime horarioConsulta,

        String unidade
) {
}
