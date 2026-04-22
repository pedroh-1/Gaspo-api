package com.gaspo.api.service;

import com.gaspo.api.model.UnidadeSaudeModel;
import com.gaspo.api.repository.UnidadeSaudeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UnidadeSaudeService {

    @Autowired
    private UnidadeSaudeRepository repository;

    /**
     * Retorna todas as unidades de saúde cadastradas no e-SUS.
     * Útil para carregar o mapa ou a lista de postos no app.
     */
    public List<UnidadeSaudeModel> listarTodas() {
        return repository.findAll();
    }
}