package com.gaspo.api.service;

import com.gaspo.api.model.gaspo.AvisoModel;
import com.gaspo.api.repository.gaspo.AvisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class AvisoService {

    @Autowired
    private AvisoRepository repository;

    public List<AvisoModel> listarTodos() {
        return repository.findAll();
    }

    public List<AvisoModel> listarAtivos() {
        return repository.findByAtivoTrue();
    }

    public AvisoModel salvar(AvisoModel aviso) {
        if (aviso.getDataPublicacao() == null) {
            aviso.setDataPublicacao(LocalDateTime.now());
        }
        return repository.save(aviso);
    }
}