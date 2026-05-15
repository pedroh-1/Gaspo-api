package com.gaspo.api.service;

import com.gaspo.api.dto.request.ProfissionalRequestDTO;
import com.gaspo.api.dto.response.ProfissionalResponseDTO;
import com.gaspo.api.mapper.ProfissionalMapper;
import com.gaspo.api.model.enums.StatusProfissional;
import com.gaspo.api.model.gaspo.ProfissionalModel;
import com.gaspo.api.model.gaspo.UnidadeSaudeModel;
import com.gaspo.api.repository.gaspo.ProfissionalRepository;
import com.gaspo.api.repository.gaspo.UnidadeSaudeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProfissionalService {

    private final ProfissionalRepository repository;
    private final UnidadeSaudeRepository unidadeSaudeRepository;
    private final ProfissionalMapper mapper;

    public ProfissionalService(ProfissionalRepository repository,
                               UnidadeSaudeRepository unidadeSaudeRepository,
                               ProfissionalMapper mapper) {
        this.repository = repository;
        this.unidadeSaudeRepository = unidadeSaudeRepository;
        this.mapper = mapper;
    }

    public List<ProfissionalModel> listarTodos() {
        return repository.findAll();
    }

    public Optional<ProfissionalModel> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public List<ProfissionalModel> buscarPorUnidade(Long unidadeId) {
        return repository.findByUnidadeSaudeId(unidadeId);
    }

    public List<ProfissionalModel> buscarPorNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome);
    }

    public List<ProfissionalModel> buscarPorEspecialidade(String especialidade) {
        return repository.findByEspecialidadeContainingIgnoreCase(especialidade);
    }

    public List<ProfissionalModel> buscarPorStatus(StatusProfissional status) {
        return repository.findByStatus(status);
    }

    @Transactional
    public ProfissionalResponseDTO cadastrar(ProfissionalRequestDTO dto) {
        ProfissionalModel profissional = new ProfissionalModel();
        preencher(profissional, dto);
        return mapper.toResponseDTO(repository.save(profissional));
    }

    @Transactional
    public ProfissionalResponseDTO atualizar(Long id, ProfissionalRequestDTO dto) {
        ProfissionalModel profissional = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));
        preencher(profissional, dto);
        return mapper.toResponseDTO(repository.save(profissional));
    }

    @Transactional
    public ProfissionalModel atualizarStatus(Long id, StatusProfissional novoStatus) {
        ProfissionalModel profissional = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));
        profissional.setStatus(novoStatus);
        return repository.save(profissional);
    }

    @Transactional
    public void remover(Long id) {
        repository.deleteById(id);
    }

    private void preencher(ProfissionalModel profissional, ProfissionalRequestDTO dto) {
        profissional.setNome(dto.nome());
        profissional.setEspecialidade(dto.especialidade());
        profissional.setEmail(dto.email());
        profissional.setTelefone(dto.telefone());
        profissional.setStatus(dto.status() != null ? dto.status() : StatusProfissional.ATENDENDO);

        UnidadeSaudeModel unidade = null;
        if (dto.unidadeSaudeId() != null) {
            unidade = unidadeSaudeRepository.findById(dto.unidadeSaudeId())
                    .orElseThrow(() -> new RuntimeException("Unidade de saúde não encontrada"));
        }
        profissional.setUnidadeSaude(unidade);
    }
}
