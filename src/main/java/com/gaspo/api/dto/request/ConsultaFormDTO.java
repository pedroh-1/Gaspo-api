package com.gaspo.api.dto.request;

import lombok.Data;

@Data
public class ConsultaFormDTO {
    private String data; // Formato esperado do input datetime-local do HTML: yyyy-MM-ddTHH:mm
    private Long profissionalId;
}
