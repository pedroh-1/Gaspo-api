package com.gaspo.api.dto.response;

public record LotacaoResumoDTO(
    Long id,
    String nomeProfissional,
    String especialidadeProfissional,
    String nomeUnidade
) {}
