package com.gaspo.api.controller;

import com.gaspo.api.dto.request.FuncionarioCadastroDTO;
import com.gaspo.api.service.FuncionarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/web/funcionarios")
public class FuncionarioWebController {

    private final FuncionarioService funcionarioService;

    public FuncionarioWebController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @GetMapping
    public String exibirFuncionarios(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!"FUNCIONARIO".equals(session.getAttribute("usuarioTipo"))) {
            redirectAttributes.addFlashAttribute("erro", "Faça login como funcionário para acessar essa área.");
            return "redirect:/web/login";
        }

        model.addAttribute("funcionarios", funcionarioService.listarTodos());
        return "funcionarios";
    }

    @PostMapping
    public String cadastrar(@RequestParam String nome,
                            @RequestParam String email,
                            @RequestParam(required = false) String telefone,
                            @RequestParam String senha,
                            @RequestParam(required = false) String cargo,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        try {
            if (!"FUNCIONARIO".equals(session.getAttribute("usuarioTipo"))) {
                redirectAttributes.addFlashAttribute("erro", "Faça login como funcionário para cadastrar funcionários.");
                return "redirect:/web/login";
            }

            funcionarioService.cadastrar(new FuncionarioCadastroDTO(nome, email, telefone, senha, cargo));
            redirectAttributes.addFlashAttribute("mensagem", "Funcionário cadastrado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao cadastrar funcionário: " + e.getMessage());
        }

        return "redirect:/web/funcionarios";
    }
}
