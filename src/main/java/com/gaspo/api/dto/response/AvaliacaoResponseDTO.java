package com.gaspo.api.dto.response;

import java.util.Date;

public record AvaliacaoResponseDTO(
        Long id,
        int nota,
        String comentario,
        Long pacienteId,
        String nomePaciente,
        Long profissionalId,
        String nomeProfissional,
        String especialidadeProfissional,
        Date dataAvaliacao
) {}
