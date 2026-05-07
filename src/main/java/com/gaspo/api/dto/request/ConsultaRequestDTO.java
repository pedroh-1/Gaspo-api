package com.gaspo.api.dto.request;

import java.util.Date;

public record ConsultaRequestDTO(
    Date data,
    Long lotacaoId,
    Long prontuarioId
) {}
