package com.gaspo.api.dto.response;

import java.util.Date;

public record ConsultaResponseDTO(
    Long id,
    Date data,
    String status,
    Long profissionalId,
    String nomeProfissional,
    String especialidadeProfissional,
    Long unidadeId,
    String nomeUnidade,
    String enderecoUnidade,
    Long pacienteId,
    String nomePaciente,
    String cpfPaciente
) {}
