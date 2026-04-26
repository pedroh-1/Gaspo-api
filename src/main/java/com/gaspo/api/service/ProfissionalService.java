package com.gaspo.api.service;

import com.gaspo.api.model.enums.StatusProfissional;
import com.gaspo.api.model.esus.ProfissionalModel;
import com.gaspo.api.repository.esus.ProfissionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProfissionalService {

    @Autowired
    private ProfissionalRepository repository;

    public List<ProfissionalModel> listarTodos() {
        return repository.findAll();
    }

    public Optional<ProfissionalModel> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public List<ProfissionalModel> buscarPorUnidade(Long unidadeId) {
        return repository.buscarPorUnidade(unidadeId);
    }

    //atualizar status do profissional
    @Transactional
    public ProfissionalModel atualizarStatus(Long id, StatusProfissional novoStatus) {
        ProfissionalModel profissional = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));

        profissional.setStatus(novoStatus);

        //simulação da sincronização externa exigida pelo requisito
        System.out.println("Sincronizando status com PEC e-SUS: " + novoStatus.getDescricao());

        return repository.save(profissional);
    }
    public ProfissionalModel salvar(ProfissionalModel profissional) {
        return repository.save(profissional);
    }

    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Profissional não encontrado para exclusão.");
        }
        repository.deleteById(id);
    }
}