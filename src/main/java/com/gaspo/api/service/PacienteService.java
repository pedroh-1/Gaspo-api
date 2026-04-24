package com.gaspo.api.service;

import com.gaspo.api.model.PacienteModel;
import com.gaspo.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository repository;

    /**
     * Retorna todos os pacientes (cidadãos) registados no e-SUS.
     */
    public List<PacienteModel> listarTodos() {
        return repository.findAll();
    }

    /**
     * Procura um paciente específico pelo ID interno do sistema.
     */
    public Optional<PacienteModel> buscarPorId(Long id) {
        return repository.findById(id);
    }

    // Futuramente, poderá adicionar métodos como:
    // public Optional<PacienteModel> buscarPorCpf(String cpf) { ... }
}