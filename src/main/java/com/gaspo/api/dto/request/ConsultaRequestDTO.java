package com.gaspo.api.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record ConsultaRequestDTO(
    @NotNull
    @Future
    Date data,

    @NotNull
    Long profissionalId
) {}
