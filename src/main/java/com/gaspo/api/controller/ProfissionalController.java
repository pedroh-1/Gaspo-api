package com.gaspo.api.controller;

import com.gaspo.api.dto.request.StatusProfissionalRequestDTO;
import com.gaspo.api.dto.response.StatusProfissionalResponseDTO;
import com.gaspo.api.model.esus.ProfissionalModel;
import com.gaspo.api.service.ProfissionalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profissionais")
public class ProfissionalController {

    @Autowired
    private ProfissionalService service;

    //Rota para buscar médicos
    @GetMapping
    public ResponseEntity<List<ProfissionalModel>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    //Rota para visualizar em tempo real o status de um profissional específico
    @GetMapping("/{id}")
    public ResponseEntity<ProfissionalModel> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Filtro adicional por Unidade de Saúde
    @GetMapping("/unidade/{unidadeId}")
    public ResponseEntity<List<ProfissionalModel>> listarPorUnidade(@PathVariable Long unidadeId) {
        return ResponseEntity.ok(service.buscarPorUnidade(unidadeId));
    }

    //Rota para atualizar o status de atendimento(funcionário)
    @PatchMapping("/status")
    public ResponseEntity<StatusProfissionalResponseDTO> atualizarStatus(
            @RequestBody @Valid StatusProfissionalRequestDTO request) {

        ProfissionalModel atualizado = service.atualizarStatus(
                request.profissionalId(),
                request.novoStatus()
        );

        //retorna o DTO de resposta para confirmação instantânea na interface
        StatusProfissionalResponseDTO response = new StatusProfissionalResponseDTO(
                atualizado.getId(),
                atualizado.getNome(),
                atualizado.getStatus()
        );

        return ResponseEntity.ok(response);
    }
    //rota para criar novo profissional
    @PostMapping
    public ResponseEntity<ProfissionalModel> criar(@RequestBody ProfissionalModel profissional) {
        ProfissionalModel novo = service.salvar(profissional);
        return ResponseEntity.status(201).body(novo);
    }

    //rota para remover profissional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}