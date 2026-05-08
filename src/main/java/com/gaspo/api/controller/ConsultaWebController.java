package com.gaspo.api.controller;

import com.gaspo.api.dto.request.ConsultaFormDTO;
import com.gaspo.api.dto.request.ConsultaRequestDTO;
import com.gaspo.api.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/agendar")
    public String exibirFormulario(Model model) {
        preencherModelo(model, new ConsultaFormDTO());
        return "agendar-consulta";
    }

    @PostMapping("/agendar")
    public String agendarConsulta(@ModelAttribute("form") ConsultaFormDTO form, RedirectAttributes redirectAttributes) {
        try {
            // O input type="datetime-local" retorna no formato yyyy-MM-dd'T'HH:mm
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date dataConvertida = sdf.parse(form.getData());

            ConsultaRequestDTO requestDTO = new ConsultaRequestDTO(
                    dataConvertida,
                    form.getLotacaoId(),
                    form.getProntuarioId()
            );

            consultaService.realizarAgendamento(requestDTO);

            redirectAttributes.addFlashAttribute("mensagem", "Consulta agendada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao agendar consulta: " + e.getMessage());
        }

        return "redirect:/web/consultas/agendar";
    }

    @PostMapping("/cancelar/{id}")
    public String cancelarConsulta(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            consultaService.cancelarConsulta(id);
            redirectAttributes.addFlashAttribute("mensagem", "Consulta cancelada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao cancelar consulta: " + e.getMessage());
        }

        return "redirect:/web/consultas/agendar";
    }

    private void preencherModelo(Model model, ConsultaFormDTO form) {
        model.addAttribute("form", form);
        model.addAttribute("lotacoes", consultaService.listarLotacoesResumo());
        model.addAttribute("agendamentosAtivos", consultaService.listarAgendamentosAtivos());
        model.addAttribute("historico", consultaService.listarHistorico());
    }
}
