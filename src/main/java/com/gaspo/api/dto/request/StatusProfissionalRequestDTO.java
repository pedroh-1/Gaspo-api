package com.gaspo.api.dto.request;

import com.gaspo.api.model.enums.StatusProfissional;
import jakarta.validation.constraints.NotNull;

public record StatusProfissionalRequestDTO(
        @NotNull
        Long profissionalId,

        @NotNull
        StatusProfissional novoStatus
) {}
