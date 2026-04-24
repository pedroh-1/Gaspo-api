package com.gaspo.api.dto.response;

public record AvaliacaoResponseDTO(
        Long id,
        int nota,
        String comentario,
        String nomePaciente
) {}
