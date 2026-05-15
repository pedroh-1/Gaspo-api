package com.gaspo.api.controller;

import com.gaspo.api.dto.request.UnidadeSaudeRequestDTO;
import jakarta.servlet.http.HttpSession;
import com.gaspo.api.service.UnidadeSaudeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/web/unidades-saude")
public class UnidadeSaudeWebController {

    private final UnidadeSaudeService unidadeSaudeService;

    public UnidadeSaudeWebController(UnidadeSaudeService unidadeSaudeService) {
        this.unidadeSaudeService = unidadeSaudeService;
    }

    @GetMapping
    public String exibirUnidades(Model model) {
        model.addAttribute("unidades", unidadeSaudeService.exibirInformacoes());
        return "unidades-saude";
    }

    @PostMapping
    public String cadastrar(@RequestParam String nome,
                            @RequestParam(required = false) String cnes,
                            @RequestParam(required = false) String telefone,
                            @RequestParam(required = false) String email,
                            @RequestParam(required = false) String endereco,
                            @RequestParam(required = false) String complemento,
                            @RequestParam(required = false) String pontoReferencia,
                            @RequestParam(required = false) String horarioFuncionamento,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        if (!funcionarioLogado(session)) {
            redirectAttributes.addFlashAttribute("erro", "Faça login como funcionário para cadastrar unidades.");
            return "redirect:/web/login";
        }

        try {
            unidadeSaudeService.salvar(new UnidadeSaudeRequestDTO(nome, cnes, telefone, email, endereco, complemento, pontoReferencia, horarioFuncionamento));
            redirectAttributes.addFlashAttribute("mensagem", "Unidade cadastrada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao cadastrar unidade: " + e.getMessage());
        }

        return "redirect:/web/unidades-saude";
    }

    @PostMapping("/{id}/remover")
    public String remover(@PathVariable Long id,
                          HttpSession session,
                          RedirectAttributes redirectAttributes) {
        if (!funcionarioLogado(session)) {
            redirectAttributes.addFlashAttribute("erro", "Faça login como funcionário para remover unidades.");
            return "redirect:/web/login";
        }

        try {
            unidadeSaudeService.remover(id);
            redirectAttributes.addFlashAttribute("mensagem", "Unidade removida!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao remover unidade: " + e.getMessage());
        }

        return "redirect:/web/unidades-saude";
    }

    private boolean funcionarioLogado(HttpSession session) {
        return "FUNCIONARIO".equals(session.getAttribute("usuarioTipo"));
    }
}
