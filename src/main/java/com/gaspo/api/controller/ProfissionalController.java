package com.gaspo.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
// Importe o seu ProfissionalEntity e ProfissionalRepository aqui quando criá-los

@RestController
@RequestMapping("/api/profissionais")
public class ProfissionalController {

    // @Autowired
    // private ProfissionalRepository repository;

    // Rota: GET /api/profissionais
    @GetMapping
    public List<?> listarTodos() {
        // Substitua o '?' pelo seu Model/Entity de Profissional
        // return repository.findAll();

        return List.of(); // Retorno vazio temporário para não dar erro de compilação
    }
}