package com.gaspo.api.service;

import com.gaspo.api.model.gaspo.AvisoModel;
import com.gaspo.api.repository.gaspo.AvisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AvisoService {

    @Autowired
    private AvisoRepository repository;

    public List<AvisoModel> listarTodos() {
        return repository.findAll();
    }

    public AvisoModel publicar(AvisoModel aviso) {
        if (aviso.getTitulo() == null || aviso.getTitulo().isBlank()) {
            aviso.setTitulo("Aviso");
        }

        if (aviso.getDataPublicacao() == null) {
            aviso.setDataPublicacao(new Date());
        }

        if (aviso.getAtivo() == null) {
            aviso.setAtivo(true);
        }

        return repository.save(aviso);
    }

    public void remover(Long id) {
        repository.deleteById(id);
    }
}
