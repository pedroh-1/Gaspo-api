package com.gaspo.api.dto.response;

import com.gaspo.api.model.enums.StatusProfissional;

public record ProfissionalResponseDTO(
        Long id,
        String nome,
        String especialidade,
        String email,
        String telefone,
        StatusProfissional status,
        Long unidadeId,
        String nomeUnidade,
        String enderecoUnidade
) {
}
