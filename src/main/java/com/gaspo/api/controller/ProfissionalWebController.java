package com.gaspo.api.controller;

import com.gaspo.api.dto.request.ProfissionalRequestDTO;
import com.gaspo.api.model.enums.StatusProfissional;
import com.gaspo.api.service.ProfissionalService;
import com.gaspo.api.service.UnidadeSaudeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/web/profissionais")
public class ProfissionalWebController {

    private final ProfissionalService profissionalService;
    private final UnidadeSaudeService unidadeSaudeService;

    public ProfissionalWebController(ProfissionalService profissionalService,
                                     UnidadeSaudeService unidadeSaudeService) {
        this.profissionalService = profissionalService;
        this.unidadeSaudeService = unidadeSaudeService;
    }

    @GetMapping
    public String exibirProfissionais(Model model) {
        model.addAttribute("profissionais", profissionalService.listarTodos());
        model.addAttribute("unidades", unidadeSaudeService.exibirInformacoes());
        model.addAttribute("statusProfissional", StatusProfissional.values());
        return "profissionais";
    }

    @PostMapping
    public String cadastrar(@RequestParam String nome,
                            @RequestParam(required = false) String especialidade,
                            @RequestParam(required = false) String email,
                            @RequestParam(required = false) String telefone,
                            @RequestParam(required = false) StatusProfissional status,
                            @RequestParam(required = false) Long unidadeSaudeId,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        if (!funcionarioLogado(session)) {
            redirectAttributes.addFlashAttribute("erro", "Faça login como funcionário para cadastrar profissionais.");
            return "redirect:/web/login";
        }

        try {
            profissionalService.cadastrar(new ProfissionalRequestDTO(nome, especialidade, email, telefone, status, unidadeSaudeId));
            redirectAttributes.addFlashAttribute("mensagem", "Profissional cadastrado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao cadastrar profissional: " + e.getMessage());
        }

        return "redirect:/web/profissionais";
    }

    @PostMapping("/{id}/status")
    public String atualizarStatus(@PathVariable Long id,
                                  @RequestParam StatusProfissional status,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {
        if (!funcionarioLogado(session)) {
            redirectAttributes.addFlashAttribute("erro", "Faça login como funcionário para atualizar profissionais.");
            return "redirect:/web/login";
        }

        try {
            profissionalService.atualizarStatus(id, status);
            redirectAttributes.addFlashAttribute("mensagem", "Status do profissional atualizado!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao atualizar status: " + e.getMessage());
        }

        return "redirect:/web/profissionais";
    }

    @PostMapping("/{id}/remover")
    public String remover(@PathVariable Long id,
                          HttpSession session,
                          RedirectAttributes redirectAttributes) {
        if (!funcionarioLogado(session)) {
            redirectAttributes.addFlashAttribute("erro", "Faça login como funcionário para remover profissionais.");
            return "redirect:/web/login";
        }

        try {
            profissionalService.remover(id);
            redirectAttributes.addFlashAttribute("mensagem", "Profissional removido!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao remover profissional: " + e.getMessage());
        }

        return "redirect:/web/profissionais";
    }

    private boolean funcionarioLogado(HttpSession session) {
        return "FUNCIONARIO".equals(session.getAttribute("usuarioTipo"));
    }
}
