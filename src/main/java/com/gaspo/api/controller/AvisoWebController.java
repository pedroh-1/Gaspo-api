package com.gaspo.api.controller;

import com.gaspo.api.model.gaspo.AvisoModel;
import com.gaspo.api.service.AvisoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/web/avisos")
public class AvisoWebController {

    @Autowired
    private AvisoService avisoService;

    @GetMapping
    public String exibirAvisos(Model model) {
        model.addAttribute("aviso", novoAvisoAtivo());
        model.addAttribute("avisos", avisoService.listarTodos());
        return "avisos";
    }

    @PostMapping
    public String publicarAviso(@ModelAttribute("aviso") AvisoModel aviso,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        try {
            if (!"FUNCIONARIO".equals(session.getAttribute("usuarioTipo"))) {
                redirectAttributes.addFlashAttribute("erro", "Faça login como funcionário para publicar avisos.");
                return "redirect:/web/login";
            }
            avisoService.publicar(aviso);
            redirectAttributes.addFlashAttribute("mensagem", "Aviso publicado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao publicar aviso: " + e.getMessage());
        }

        return "redirect:/web/avisos";
    }

    private AvisoModel novoAvisoAtivo() {
        AvisoModel aviso = new AvisoModel();
        aviso.setAtivo(true);
        return aviso;
    }
}
