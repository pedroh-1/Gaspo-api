package com.gaspo.api.controller;

import com.gaspo.api.dto.response.UnidadeSaudeResumoDTO;
import com.gaspo.api.service.UnidadeSaudeService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
