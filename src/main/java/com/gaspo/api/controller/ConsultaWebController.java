package com.gaspo.api.controller;

import com.gaspo.api.dto.request.ConsultaAgendamentoRequestDTO;
import com.gaspo.api.dto.request.ConsultaFormDTO;
import com.gaspo.api.model.gaspo.UsuarioModel;
import com.gaspo.api.repository.UsuarioRepository;
import com.gaspo.api.service.ConsultaService;
import com.gaspo.api.service.ProfissionalService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/web/consultas")
public class ConsultaWebController {

    @Autowired
    private ConsultaService consultaService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProfissionalService profissionalService;

    @GetMapping("/agendar")
    public String exibirFormulario(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        UsuarioModel usuarioLogado = usuarioDaSessao(session);
        if (usuarioLogado == null) {
            redirectAttributes.addFlashAttribute("erro", "Faça login como paciente para agendar e visualizar suas consultas.");
            return "redirect:/web/login";
        }

        preencherModelo(model, new ConsultaFormDTO(), usuarioLogado);
        return "agendar-consulta";
    }

    @PostMapping("/agendar")
    public String agendarConsulta(@ModelAttribute("form") ConsultaFormDTO form,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {
        try {
            UsuarioModel usuarioLogado = usuarioDaSessao(session);
            if (usuarioLogado == null) {
                redirectAttributes.addFlashAttribute("erro", "Faça login como paciente para agendar uma consulta.");
                return "redirect:/web/login";
            }

            // O input type="datetime-local" retorna no formato yyyy-MM-dd'T'HH:mm
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date dataConvertida = sdf.parse(form.getData());

            ConsultaAgendamentoRequestDTO requestDTO = new ConsultaAgendamentoRequestDTO(
                    dataConvertida,
                    form.getProfissionalId()
            );

            consultaService.agendarParaUsuarioLogado(requestDTO, usuarioLogado);

            redirectAttributes.addFlashAttribute("mensagem", "Consulta agendada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao agendar consulta: " + mensagemErro(e));
        }

        return "redirect:/web/consultas/agendar";
    }

    @PostMapping("/cancelar/{id}")
    public String cancelarConsulta(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            UsuarioModel usuarioLogado = usuarioDaSessao(session);
            if (usuarioLogado == null) {
                redirectAttributes.addFlashAttribute("erro", "Faça login como paciente para cancelar sua consulta.");
                return "redirect:/web/login";
            }

            consultaService.cancelarConsulta(id, usuarioLogado);
            redirectAttributes.addFlashAttribute("mensagem", "Consulta cancelada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao cancelar consulta: " + mensagemErro(e));
        }

        return "redirect:/web/consultas/agendar";
    }

    private void preencherModelo(Model model, ConsultaFormDTO form, UsuarioModel usuarioLogado) {
        model.addAttribute("form", form);
        model.addAttribute("pacienteLogado", usuarioLogado);
        model.addAttribute("profissionais", profissionalService.listarTodos());
        model.addAttribute("agendamentosAtivos", consultaService.listarAgendamentosAtivosDoUsuario(usuarioLogado, PageRequest.of(0, 20)).content());
        model.addAttribute("historico", consultaService.listarHistoricoDoUsuario(usuarioLogado, PageRequest.of(0, 20)).content());
    }

    private UsuarioModel usuarioDaSessao(HttpSession session) {
        Object tipo = session.getAttribute("usuarioTipo");
        Object email = session.getAttribute("usuarioEmail");
        if (!"USUARIO".equals(tipo) || email == null) {
            return null;
        }

        return usuarioRepository.findByEmail(email.toString()).orElse(null);
    }

    private String mensagemErro(Exception e) {
        if (e instanceof org.springframework.web.server.ResponseStatusException responseStatusException
                && responseStatusException.getReason() != null) {
            return responseStatusException.getReason();
        }
        return e.getMessage();
    }
}
