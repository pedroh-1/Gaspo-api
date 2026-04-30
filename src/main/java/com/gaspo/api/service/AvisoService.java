package com.gaspo.api.service;

import com.gaspo.api.model.gaspo.AvisoModel;
import com.gaspo.api.repository.gaspo.AvisoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvisoService {

    private final AvisoRepository repository;

    public List<AvisoModel> listarTodos() { 
        return repository.findAll(); 
    }

    public List<AvisoModel> listarAtivos() {
        return repository.findAll().stream()
                .filter(a -> a.getAtivo() != null && a.getAtivo())
                .collect(Collectors.toList());
    }

    public AvisoModel salvar(AvisoModel aviso) { 
        return repository.save(aviso); 
    }
}