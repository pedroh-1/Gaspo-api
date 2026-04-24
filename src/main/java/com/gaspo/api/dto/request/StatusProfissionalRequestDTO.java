package com.gaspo.api.dto.request;

import com.gaspo.api.model.StatusProfissional;
import jakarta.validation.constraints.NotNull;

public record StatusProfissionalRequestDTO(
        @NotNull
        Long profissionalId,

        @NotNull
        StatusProfissional novoStatus
) {}
