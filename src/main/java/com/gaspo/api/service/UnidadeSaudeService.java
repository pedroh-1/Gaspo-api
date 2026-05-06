package com.gaspo.api.service;

import com.gaspo.api.model.gaspo.UnidadeSaudeModel;
import com.gaspo.api.repository.gaspo.UnidadeSaudeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UnidadeSaudeService {

    @Autowired
    private UnidadeSaudeRepository repository;

    public List<UnidadeSaudeModel> exibirInformacoes() {
        return repository.findAll();
    }

    public Optional<UnidadeSaudeModel> buscarPorId(Long id) {
        return repository.findById(id);
    }
}