package com.gaspo.api.mapper;

import com.gaspo.api.dto.response.UnidadeSaudeResumoDTO;
import com.gaspo.api.model.gaspo.UnidadeSaudeModel;
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
                model.getTelefone(),
                model.getEmail(),
                model.getEndereco(),
                model.getComplemento(),
                model.getPontoReferencia(),
                model.getHorarioFuncionamento()
        );
    }
}
