package com.gaspo.api.service;

import com.gaspo.api.dto.response.UnidadeSaudeResumoDTO;
import com.gaspo.api.mapper.UnidadeSaudeMapper;
import com.gaspo.api.model.esus.UnidadeSaudeModel;
import com.gaspo.api.repository.esus.UnidadeSaudeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UnidadeSaudeService {

    @Autowired
    private UnidadeSaudeRepository repository;

    @Autowired
    private UnidadeSaudeMapper mapper;

    public List<UnidadeSaudeModel> exibirInformacoes() {
        return repository.findAll();
    }

    public List<UnidadeSaudeResumoDTO> listarResumo() {
        return repository.findAll().stream()
                .map(mapper::toResumoDTO)
                .toList();
    }

    public Optional<UnidadeSaudeModel> buscarPorId(Long id) {
        return repository.findById(id);
    }
}
