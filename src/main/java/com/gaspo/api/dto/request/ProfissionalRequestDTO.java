package com.gaspo.api.dto.request;

import com.gaspo.api.model.enums.StatusProfissional;
import jakarta.validation.constraints.NotBlank;

public record ProfissionalRequestDTO(
        @NotBlank(message = "Nome do profissional é obrigatório")
        String nome,
        String especialidade,
        String email,
        String telefone,
        StatusProfissional status,
        Long unidadeSaudeId
) {
}
