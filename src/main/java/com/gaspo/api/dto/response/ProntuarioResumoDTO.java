package com.gaspo.api.dto.response;

public record ProntuarioResumoDTO(
    Long id,
    String nomePaciente,
    String cpfPaciente
) {}
