package com.gaspo.api.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AvaliacaoRequestDTO(
        @Min(1) @Max(5)
        int nota,

        String comentario,

        @NotNull
        Long pacienteId
) {}
