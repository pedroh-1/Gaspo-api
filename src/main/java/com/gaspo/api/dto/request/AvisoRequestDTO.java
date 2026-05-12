package com.gaspo.api.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.util.Date;

public record AvisoRequestDTO(
        @NotBlank(message = "Título é obrigatório")
        String titulo,

        @NotBlank(message = "Mensagem é obrigatória")
        String mensagem,

        Date dataExpiracao,

        Boolean ativo
) {
}
