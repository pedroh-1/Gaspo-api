package com.gaspo.api.mapper;

import com.gaspo.api.dto.request.AvisoRequestDTO;
import com.gaspo.api.dto.response.AvisoResponseDTO;
import com.gaspo.api.model.gaspo.AvisoModel;
import org.springframework.stereotype.Component;

@Component
public class AvisoMapper {

    public AvisoModel toModel(AvisoRequestDTO dto) {
        AvisoModel model = new AvisoModel();
        model.setTitulo(dto.titulo());
        model.setMensagem(dto.mensagem());
        model.setDataExpiracao(dto.dataExpiracao());
        model.setAtivo(dto.ativo());
        return model;
    }

    public AvisoResponseDTO toResponseDTO(AvisoModel model) {
        if (model == null) {
            return null;
        }

        return new AvisoResponseDTO(
                model.getId(),
                model.getTitulo(),
                model.getMensagem(),
                model.getDataPublicacao(),
                model.getDataExpiracao(),
                model.getAtivo()
        );
    }
}
