package com.gaspo.api.service;

import com.gaspo.api.model.enums.StatusProfissional;
import com.gaspo.api.model.esus.ProfissionalModel;
import com.gaspo.api.model.gaspo.ProfissionalStatusModel;
import com.gaspo.api.repository.esus.ProfissionalRepository;
import com.gaspo.api.repository.gaspo.ProfissionalStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProfissionalService {

    @Autowired
    private ProfissionalRepository repository;

    @Autowired
    private ProfissionalStatusRepository statusRepository;

    private ProfissionalModel preencherStatus(ProfissionalModel profissional) {
        if (profissional != null) {
            statusRepository.findById(profissional.getId()).ifPresent(statusModel -> {
                profissional.setStatus(statusModel.getStatus());
            });
        }
        return profissional;
    }

    public List<ProfissionalModel> listarTodos() {
        List<ProfissionalModel> lista = repository.findAll();
        lista.forEach(this::preencherStatus);
        return lista;
    }

    public Optional<ProfissionalModel> buscarPorId(Long id) {
        Optional<ProfissionalModel> prof = repository.findById(id);
        prof.ifPresent(this::preencherStatus);
        return prof;
    }

    public List<ProfissionalModel> buscarPorUnidade(Long unidadeId) {
        List<ProfissionalModel> lista = repository.buscarPorUnidade(unidadeId);
        lista.forEach(this::preencherStatus);
        return lista;
    }

    @Transactional(transactionManager = "gaspoTransactionManager")
    public ProfissionalModel atualizarStatus(Long id, StatusProfissional novoStatus) {
        ProfissionalModel profissional = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado no e-SUS"));

        ProfissionalStatusModel statusGaspo = statusRepository.findById(id)
                .orElse(new ProfissionalStatusModel(id, StatusProfissional.ATENDENDO));

        statusGaspo.setStatus(novoStatus);
        statusRepository.save(statusGaspo);

        profissional.setStatus(novoStatus);
        return profissional;
    }
}