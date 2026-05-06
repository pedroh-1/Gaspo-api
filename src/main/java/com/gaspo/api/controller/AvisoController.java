package com.gaspo.api.controller;

import com.gaspo.api.model.gaspo.AvisoModel;
import com.gaspo.api.service.AvisoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/avisos")
public class AvisoController {

    @Autowired
    private AvisoService service;

    @GetMapping
    public List<AvisoModel> listarAvisos() {
        return service.listarTodos();
    }

    @PostMapping("/publicar")
    public AvisoModel publicarAviso(@RequestBody AvisoModel aviso) {
        return service.publicar(aviso);
    }

    @DeleteMapping("/{id}")
    public void removerAviso(@PathVariable Long id) {
        service.remover(id);
    }
}