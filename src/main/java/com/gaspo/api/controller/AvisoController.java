package com.gaspo.api.controller;

import com.gaspo.api.model.gaspo.AvisoModel;
import com.gaspo.api.service.AvisoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/avisos")

public class AvisoController {
    @Autowired
private AvisoService service;

    /**
     * Rota: GET /api/avisos
     * Lista todos os avisos registados.
     */
    @GetMapping
    public List<AvisoModel> listarTodos() {
        return service.listarTodos();
    }

    /**
     * Rota: GET /api/avisos/ativos
     * Retorna apenas os avisos que estão marcados como ativos.
     */
    @GetMapping("/ativos")
    public List<AvisoModel> listarAtivos() {
        return service.listarAtivos();
    }

    /**
     * Rota: POST /api/avisos
     * Cria um novo aviso no sistema.
     */
    @PostMapping
    public AvisoModel criar(@RequestBody AvisoModel aviso) {
        return service.salvar(aviso);
    }
}