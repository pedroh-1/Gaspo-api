package com.gaspo.api.dto.request;

/** Login por CPF (com ou sem máscara) e senha. */
public record LoginRequestDTO(String cpf, String senha) {}
