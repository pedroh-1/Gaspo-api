package com.gaspo.api.dto.response;

import java.util.Date;

public record AvisoResponseDTO(
        Long id,
        String titulo,
        String mensagem,
        Date dataPublicacao,
        Date dataExpiracao,
        Boolean ativo
) {
}
