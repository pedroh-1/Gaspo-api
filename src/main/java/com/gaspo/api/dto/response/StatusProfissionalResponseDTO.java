package com.gaspo.api.dto.response;

import com.gaspo.api.model.enums.StatusProfissional;

public record StatusProfissionalResponseDTO(
        Long profissionalId,
        String nomeProfissional,
        StatusProfissional statusAtual
) {}
