package com.gaspo.api.dto.response;

import java.util.Date;

public record ConsultaResponseDTO(
    Long id,
    Date data,
    Long status,
    String statusSincronizacao,
    Integer foraUbs,
    LotacaoResumoDTO lotacao,
    ProntuarioResumoDTO prontuario
) {}
