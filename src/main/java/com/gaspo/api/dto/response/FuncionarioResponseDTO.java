package com.gaspo.api.dto.response;

public record FuncionarioResponseDTO(
        Long id,
        String nome,
        String cpf,
        String email,
        String telefone,
        String cargo
) {
}
