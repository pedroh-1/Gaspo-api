package com.gaspo.api.service;

import com.gaspo.api.dto.request.UnidadeSaudeRequestDTO;
import com.gaspo.api.dto.response.UnidadeSaudeResumoDTO;
import com.gaspo.api.mapper.UnidadeSaudeMapper;
import com.gaspo.api.model.gaspo.UnidadeSaudeModel;
import com.gaspo.api.repository.gaspo.UnidadeSaudeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public UnidadeSaudeResumoDTO salvar(UnidadeSaudeRequestDTO dto) {
        UnidadeSaudeModel unidade = new UnidadeSaudeModel();
        preencher(unidade, dto);
        return mapper.toResumoDTO(repository.save(unidade));
    }

    @Transactional
    public UnidadeSaudeResumoDTO atualizar(Long id, UnidadeSaudeRequestDTO dto) {
        UnidadeSaudeModel unidade = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unidade de saúde não encontrada"));
        preencher(unidade, dto);
        return mapper.toResumoDTO(repository.save(unidade));
    }

    @Transactional
    public void remover(Long id) {
        repository.deleteById(id);
    }

    private void preencher(UnidadeSaudeModel unidade, UnidadeSaudeRequestDTO dto) {
        unidade.setNome(dto.nome());
        unidade.setCnes(dto.cnes());
        unidade.setTelefone(dto.telefone());
        unidade.setEmail(dto.email());
        unidade.setEndereco(dto.endereco());
        unidade.setComplemento(dto.complemento());
        unidade.setPontoReferencia(dto.pontoReferencia());
        unidade.setHorarioFuncionamento(dto.horarioFuncionamento());
    }
}
