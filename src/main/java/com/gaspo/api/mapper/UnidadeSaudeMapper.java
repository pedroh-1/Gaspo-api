package com.gaspo.api.mapper;

import com.gaspo.api.dto.response.UnidadeSaudeResumoDTO;
import com.gaspo.api.model.esus.UnidadeSaudeModel;
import org.springframework.stereotype.Component;

@Component
public class UnidadeSaudeMapper {

    public UnidadeSaudeResumoDTO toResumoDTO(UnidadeSaudeModel model) {
        if (model == null) {
            return null;
        }

        return new UnidadeSaudeResumoDTO(
                model.getId(),
                model.getNome(),
                model.getCnes(),
                model.getTelefoneComercial(),
                model.getEmail(),
                model.getEnderecoFormatado(),
                model.getComplemento(),
                model.getPontoReferencia(),
                null
        );
    }
}
