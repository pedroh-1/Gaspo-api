package com.gaspo.api.controller;

import com.gaspo.api.model.gaspo.AvisoModel;
import com.gaspo.api.service.AvisoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/avisos")
@RequiredArgsConstructor
public class AvisoController {

    private final AvisoService service;

    /**
     * Rota: GET /api/avisos
     * Lista todos os avisos registrados.
     */
    @GetMapping
    public ResponseEntity<List<AvisoModel>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    /**
     * Rota: GET /api/avisos/ativos
     * Retorna apenas os avisos que estão marcados como ativos.
     */
    @GetMapping("/ativos")
    public ResponseEntity<List<AvisoModel>> listarAtivos() {
        return ResponseEntity.ok(service.listarAtivos());
    }

    /**
     * Rota: POST /api/avisos
     * Cria um novo aviso no sistema.
     */
    @PostMapping
    public ResponseEntity<AvisoModel> criar(@RequestBody AvisoModel aviso) {
        return ResponseEntity.status(201).body(service.salvar(aviso));
    }
}