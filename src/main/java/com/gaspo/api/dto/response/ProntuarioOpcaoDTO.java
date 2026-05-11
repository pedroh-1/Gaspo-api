package com.gaspo.api.dto.response;

public record ProntuarioOpcaoDTO(
        Long id,
        Long pacienteId,
        String nomePaciente,
        String cpfPaciente
) {
}
