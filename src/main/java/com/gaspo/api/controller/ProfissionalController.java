package com.gaspo.api.controller;

import com.gaspo.api.dto.request.ProfissionalRequestDTO;
import com.gaspo.api.dto.request.StatusProfissionalRequestDTO;
import com.gaspo.api.dto.response.ProfissionalResponseDTO;
import com.gaspo.api.dto.response.StatusProfissionalResponseDTO;
import com.gaspo.api.model.enums.StatusProfissional;
import com.gaspo.api.model.gaspo.ProfissionalModel;
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

    @GetMapping("/buscar")
    public ResponseEntity<List<ProfissionalModel>> filtrar(@RequestParam(required = false) String nome,
                                                           @RequestParam(required = false) String especialidade,
                                                           @RequestParam(required = false) StatusProfissional status) {
        if (nome != null && !nome.isBlank()) {
            return ResponseEntity.ok(service.buscarPorNome(nome));
        }
        if (especialidade != null && !especialidade.isBlank()) {
            return ResponseEntity.ok(service.buscarPorEspecialidade(especialidade));
        }
        if (status != null) {
            return ResponseEntity.ok(service.buscarPorStatus(status));
        }
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
    @PutMapping("/status")
    public ResponseEntity<StatusProfissionalResponseDTO> atualizarStatus(@Valid @RequestBody StatusProfissionalRequestDTO request) {
        ProfissionalModel atualizado = service.atualizarStatus(request.profissionalId(), request.novoStatus());
        return ResponseEntity.ok(new StatusProfissionalResponseDTO(atualizado.getId(), atualizado.getNome(), atualizado.getStatus()));
    }

    @PostMapping
    public ResponseEntity<ProfissionalResponseDTO> criar(@Valid @RequestBody ProfissionalRequestDTO profissional) {
        return ResponseEntity.ok(service.cadastrar(profissional));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfissionalResponseDTO> atualizar(@PathVariable Long id,
                                                             @Valid @RequestBody ProfissionalRequestDTO profissional) {
        return ResponseEntity.ok(service.atualizar(id, profissional));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
