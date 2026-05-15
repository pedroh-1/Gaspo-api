package com.gaspo.api.controller;

import com.gaspo.api.dto.request.UnidadeSaudeRequestDTO;
import com.gaspo.api.dto.response.UnidadeSaudeResumoDTO;
import com.gaspo.api.service.UnidadeSaudeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/unidades-saude")
public class UnidadeSaudeController {

    @Autowired
    private UnidadeSaudeService service;

    @GetMapping
    public List<UnidadeSaudeResumoDTO> exibirInformacoes() {
        return service.listarResumo();
    }

    @PostMapping
    public ResponseEntity<UnidadeSaudeResumoDTO> cadastrar(@Valid @RequestBody UnidadeSaudeRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UnidadeSaudeResumoDTO> atualizar(@PathVariable Long id,
                                                           @Valid @RequestBody UnidadeSaudeRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
