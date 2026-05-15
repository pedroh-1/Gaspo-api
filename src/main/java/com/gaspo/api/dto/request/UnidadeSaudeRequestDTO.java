package com.gaspo.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UnidadeSaudeRequestDTO(
        @NotBlank(message = "Nome da unidade é obrigatório")
        String nome,
        String cnes,
        String telefone,
        String email,
        String endereco,
        String complemento,
        String pontoReferencia,
        String horarioFuncionamento
) {
}
