package com.gaspo.api.controller;

import com.gaspo.api.model.UnidadeSaudeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/unidades")
public class UnidadeSaudeController {

    @Autowired
    private UnidadeSaudeRepository repository;

    @GetMapping
    public List<UnidadeSaudeModel> listarTodas() {
        return repository.findAll(); // Vai no banco e traz tudo formatado em JSON!
    }
}
