package com.gaspo.api.controller;

import com.gaspo.api.service.UnidadeSaudeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web/unidades-saude")
public class UnidadeSaudeWebController {

    private final UnidadeSaudeService unidadeSaudeService;

    public UnidadeSaudeWebController(UnidadeSaudeService unidadeSaudeService) {
        this.unidadeSaudeService = unidadeSaudeService;
    }

    @GetMapping
    public String exibirUnidades(Model model) {
        model.addAttribute("unidades", unidadeSaudeService.listarResumo());
        return "unidades-saude";
    }
}
