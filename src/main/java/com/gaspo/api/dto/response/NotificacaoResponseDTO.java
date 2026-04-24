package com.gaspo.api.dto.response;

import java.time.LocalDateTime;

public record NotificacaoResponseDTO(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataEnvio
) {}
