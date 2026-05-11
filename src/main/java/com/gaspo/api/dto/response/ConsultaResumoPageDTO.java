package com.gaspo.api.dto.response;

import java.util.List;

public record ConsultaResumoPageDTO(
        List<ConsultaResponseDTO> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean first,
        boolean last
) {
}
