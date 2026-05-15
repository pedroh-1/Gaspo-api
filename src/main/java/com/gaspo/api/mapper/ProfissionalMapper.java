package com.gaspo.api.mapper;

import com.gaspo.api.dto.response.ProfissionalResponseDTO;
import com.gaspo.api.model.gaspo.ProfissionalModel;
import com.gaspo.api.model.gaspo.UnidadeSaudeModel;
import org.springframework.stereotype.Component;

@Component
public class ProfissionalMapper {

    public ProfissionalResponseDTO toResponseDTO(ProfissionalModel model) {
        if (model == null) {
            return null;
        }

        UnidadeSaudeModel unidade = model.getUnidadeSaude();

        return new ProfissionalResponseDTO(
                model.getId(),
                model.getNome(),
                model.getEspecialidade(),
                model.getEmail(),
                model.getTelefone(),
                model.getStatus(),
                unidade != null ? unidade.getId() : null,
                unidade != null ? unidade.getNome() : null,
                unidade != null ? unidade.getEndereco() : null
        );
    }
}
