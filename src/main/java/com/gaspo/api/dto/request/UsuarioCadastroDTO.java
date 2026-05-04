package com.gaspo.api.dto.request;

public record UsuarioCadastroDTO(
        String cpf,
        String email,
        String senha
) {}
