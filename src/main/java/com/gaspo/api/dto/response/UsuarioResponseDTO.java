package com.gaspo.api.dto.response;

import java.time.LocalDate;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String cpf,
        String cns,
        String telefone,
        LocalDate dataNascimento,
        String email
) {
}
