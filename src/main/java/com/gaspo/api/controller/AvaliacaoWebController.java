package com.gaspo.api.controller;

import com.gaspo.api.dto.request.AvaliacaoRequestDTO;
import com.gaspo.api.model.gaspo.UsuarioModel;
import com.gaspo.api.repository.gaspo.UsuarioRepository;
import com.gaspo.api.service.AvaliacaoService;
import com.gaspo.api.service.ProfissionalService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/web/avaliacoes")
public class AvaliacaoWebController {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @Autowired
    private ProfissionalService profissionalService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public String exibirAvaliacoes(@RequestParam(required = false) Long profissionalId,
                                   Model model,
                                   HttpSession session) {
        UsuarioModel pacienteLogado = usuarioDaSessao(session);

        model.addAttribute("profissionais", profissionalService.listarTodos());
        model.addAttribute("pacienteLogado", pacienteLogado);
        model.addAttribute("novaAvaliacao", new AvaliacaoFormDTO());
        model.addAttribute("profissionalSelecionadoId", profissionalId);
        model.addAttribute("avaliacoes", profissionalId != null
                ? avaliacaoService.listarPorProfissional(profissionalId)
                : avaliacaoService.listarTodas());
        return "avaliacoes";
    }

    @PostMapping
    public String avaliar(@ModelAttribute("novaAvaliacao") AvaliacaoFormDTO form,
                          HttpSession session,
                          RedirectAttributes redirectAttributes) {
        try {
            UsuarioModel pacienteLogado = usuarioDaSessao(session);
            if (pacienteLogado == null) {
                redirectAttributes.addFlashAttribute("erro", "Faça login como paciente para publicar uma avaliação.");
                return "redirect:/web/login";
            }

            avaliacaoService.avaliar(new AvaliacaoRequestDTO(
                    form.getNota(),
                    form.getComentario(),
                    pacienteLogado.getIdCidadaoEsus(),
                    form.getProfissionalId()
            ));
            redirectAttributes.addFlashAttribute("mensagem", "Avaliação registrada com sucesso!");
            return "redirect:/web/avaliacoes?profissionalId=" + form.getProfissionalId();
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("erro", ex.getMessage());
            return "redirect:/web/avaliacoes";
        }
    }

    public static class AvaliacaoFormDTO {
        private Integer nota = 5;
        private String comentario;
        private Long profissionalId;

        public Integer getNota() {
            return nota;
        }

        public void setNota(Integer nota) {
            this.nota = nota;
        }

        public String getComentario() {
            return comentario;
        }

        public void setComentario(String comentario) {
            this.comentario = comentario;
        }

        public Long getProfissionalId() {
            return profissionalId;
        }

        public void setProfissionalId(Long profissionalId) {
            this.profissionalId = profissionalId;
        }
    }

    private UsuarioModel usuarioDaSessao(HttpSession session) {
        Object tipo = session.getAttribute("usuarioTipo");
        Object email = session.getAttribute("usuarioEmail");
        if (!"USUARIO".equals(tipo) || email == null) {
            return null;
        }

        return usuarioRepository.findByEmail(email.toString()).orElse(null);
    }
}
