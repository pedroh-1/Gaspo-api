package com.gaspo.api.controller;

import com.gaspo.api.dto.request.AvaliacaoRequestDTO;
import com.gaspo.api.dto.response.AvaliacaoResponseDTO;
import com.gaspo.api.model.gaspo.AvaliacaoModel;
import com.gaspo.api.service.AvaliacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/avaliacoes"})
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService service;

    @PostMapping
    public ResponseEntity<AvaliacaoModel> registrar(@RequestBody AvaliacaoModel avaliacao) {
        AvaliacaoModel salva = service.salvar(avaliacao);
        return ResponseEntity.ok(salva);
    }

    @GetMapping
    public ResponseEntity<List<AvaliacaoResponseDTO>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @PostMapping("/avaliar")
    public ResponseEntity<AvaliacaoResponseDTO> avaliar(@Valid @RequestBody AvaliacaoRequestDTO request) {
        return ResponseEntity.ok(service.avaliar(request));
    }

    @GetMapping("/profissional/{profissionalId}")
    public ResponseEntity<List<AvaliacaoResponseDTO>> listarPorProfissional(@PathVariable Long profissionalId) {
        return ResponseEntity.ok(service.listarPorProfissional(profissionalId));
    }
}
