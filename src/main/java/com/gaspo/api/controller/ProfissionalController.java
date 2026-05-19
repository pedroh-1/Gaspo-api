package com.gaspo.api.controller;

import com.gaspo.api.dto.request.ProfissionalRequestDTO;
import com.gaspo.api.dto.request.StatusProfissionalRequestDTO;
import com.gaspo.api.dto.response.ProfissionalResponseDTO;
import com.gaspo.api.dto.response.StatusProfissionalResponseDTO;
import com.gaspo.api.mapper.ProfissionalMapper;
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

    @Autowired
    private ProfissionalMapper mapper;

    //Rota para buscar médicos
    @GetMapping
    public ResponseEntity<List<ProfissionalResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodosDTO());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ProfissionalResponseDTO>> filtrar(@RequestParam(required = false) String nome,
                                                                 @RequestParam(required = false) String especialidade,
                                                                 @RequestParam(required = false) StatusProfissional status) {
        if (nome != null && !nome.isBlank()) {
            return ResponseEntity.ok(service.buscarPorNomeDTO(nome));
        }
        if (especialidade != null && !especialidade.isBlank()) {
            return ResponseEntity.ok(service.buscarPorEspecialidadeDTO(especialidade));
        }
        if (status != null) {
            return ResponseEntity.ok(service.buscarPorStatusDTO(status));
        }
        return ResponseEntity.ok(service.listarTodosDTO());
    }

    //Rota para visualizar em tempo real o status de um profissional específico
    @GetMapping("/{id}")
    public ResponseEntity<ProfissionalResponseDTO> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(mapper::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Filtro adicional por Unidade de Saúde
    @GetMapping("/unidade/{unidadeId}")
    public ResponseEntity<List<ProfissionalResponseDTO>> listarPorUnidade(@PathVariable Long unidadeId) {
        return ResponseEntity.ok(service.buscarPorUnidadeDTO(unidadeId));
    }

    //Rota para atualizar o status de atendimento(funcionário)
    @PutMapping("/status")
    public ResponseEntity<StatusProfissionalResponseDTO> atualizarStatus(@Valid @RequestBody StatusProfissionalRequestDTO request) {
        ProfissionalModel atualizado = service.atualizarStatus(request.profissionalId(), request.novoStatus());
        return ResponseEntity.ok(mapper.toStatusResponseDTO(atualizado));
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
