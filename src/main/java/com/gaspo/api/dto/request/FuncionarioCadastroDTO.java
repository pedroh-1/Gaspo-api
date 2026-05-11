package com.gaspo.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record FuncionarioCadastroDTO(
        @NotBlank
        String nome,

        @Email
        @NotBlank
        String email,

        String telefone,

        @NotBlank
        String senha,

        String cargo
) {
}
