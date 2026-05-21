package com.gaspo.api.service;

import com.gaspo.api.model.gaspo.AvisoModel;
import com.gaspo.api.repository.AvisoRepository;
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

    public AvisoModel buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aviso não encontrado"));
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

    public AvisoModel editar(Long id, AvisoModel dados) {
        AvisoModel aviso = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aviso não encontrado"));

        if (dados.getTitulo() != null && !dados.getTitulo().isBlank()) {
            aviso.setTitulo(dados.getTitulo());
        }
        if (dados.getMensagem() != null && !dados.getMensagem().isBlank()) {
            aviso.setMensagem(dados.getMensagem());
        }
        if (dados.getDataExpiracao() != null) {
            aviso.setDataExpiracao(dados.getDataExpiracao());
        }
        if (dados.getAtivo() != null) {
            aviso.setAtivo(dados.getAtivo());
        }

        return repository.save(aviso);
    }

    public void remover(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Aviso não encontrado");
        }
        repository.deleteById(id);
    }
}
