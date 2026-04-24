package com.gaspo.api.service;

import com.gaspo.api.model.gaspo.AvaliacaoModel;
import com.gaspo.api.repository.gaspo.AvaliacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvaliacaoService {

    @Autowired
    private AvaliacaoRepository repository;

    public AvaliacaoModel salvar(AvaliacaoModel avaliacao) {
        // Aqui pode adicionar validações antes de salvar
        return repository.save(avaliacao);
    }
}
