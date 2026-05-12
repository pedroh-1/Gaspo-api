package com.gaspo.api.dto.response;

public record UnidadeSaudeResumoDTO(
        Long id,
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
