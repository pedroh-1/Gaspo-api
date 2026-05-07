package com.gaspo.api.controller;

import com.gaspo.api.dto.request.ConsultaFormDTO;
import com.gaspo.api.dto.request.ConsultaRequestDTO;
import com.gaspo.api.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/web/consultas")
public class ConsultaWebController {

    @Autowired
    private ConsultaService consultaService;

    @GetMapping("/agendar")
    public String exibirFormulario(Model model) {
        model.addAttribute("form", new ConsultaFormDTO());
        model.addAttribute("lotacoes", consultaService.listarLotacoesResumo());
        return "agendar-consulta";
    }

    @PostMapping("/agendar")
    public String agendarConsulta(@ModelAttribute("form") ConsultaFormDTO form, Model model) {
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
            
            model.addAttribute("mensagem", "Consulta agendada com sucesso!");
            model.addAttribute("form", new ConsultaFormDTO()); // Limpa o formulário
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao agendar consulta: " + e.getMessage());
            e.printStackTrace();
        }
        model.addAttribute("lotacoes", consultaService.listarLotacoesResumo());
        return "agendar-consulta";
    }
}
