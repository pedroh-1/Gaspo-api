package com.gaspo.api.dto.request;

import java.util.Date;

public record AgendamentoPorNomeDTO(
    Date data,
    String nomeProfissional,
    String nomeUnidade
) {}
