package com.gaspo.api.controller;

import com.gaspo.api.model.enums.Disponibilidade;
import com.gaspo.api.model.esus.ProfissionalModel;
import com.gaspo.api.model.gaspo.AgendaModel;
import com.gaspo.api.service.AgendaService;
import com.gaspo.api.service.ProfissionalService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/web/agendas")
public class AgendaWebController {

    private final AgendaService agendaService;
    private final ProfissionalService profissionalService;

    public AgendaWebController(AgendaService agendaService, ProfissionalService profissionalService) {
        this.agendaService = agendaService;
        this.profissionalService = profissionalService;
    }

    @GetMapping
    public String exibirAgenda(Model model) {
        try {
            model.addAttribute("agendas", agendaService.listarTodos());
        } catch (Exception e) {
            model.addAttribute("agendas", new ArrayList<>());
            model.addAttribute("erro", "Erro ao carregar agenda: " + e.getMessage());
        }

        try {
            model.addAttribute("profissionais", profissionalService.listarTodos());
        } catch (Exception e) {
            model.addAttribute("profissionais", new ArrayList<>());
            if (!model.containsAttribute("erro")) {
                model.addAttribute("erro", "Erro ao carregar profissionais: " + e.getMessage());
            }
        }

        model.addAttribute("disponibilidades", Disponibilidade.values());
        return "agendas";
    }

    @PostMapping("/gerar-grade")
    public String gerarGrade(@RequestParam Long profissionalId,
                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
                             @RequestParam String horarios,
                             RedirectAttributes redirectAttributes) {
        try {
            ProfissionalModel profissional = profissionalService.buscarPorId(profissionalId)
                    .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));
            List<LocalTime> horariosConvertidos = Arrays.stream(horarios.split(","))
                    .map(String::trim)
                    .filter(valor -> !valor.isBlank())
                    .map(LocalTime::parse)
                    .toList();

            if (horariosConvertidos.isEmpty()) {
                throw new IllegalArgumentException("Informe pelo menos um horário válido.");
            }

            agendaService.gerarGradeHoraria(profissional, data, horariosConvertidos);
            redirectAttributes.addFlashAttribute("mensagem", "Grade gerada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao gerar grade: " + e.getMessage());
        }

        return "redirect:/web/agendas";
    }

    @PostMapping("/{id}/disponibilidade")
    public String atualizarDisponibilidade(@PathVariable Long id,
                                           @RequestParam Disponibilidade disponibilidade,
                                           RedirectAttributes redirectAttributes) {
        try {
            AgendaModel agenda = agendaService.buscarPorId(id)
                    .orElseThrow(() -> new RuntimeException("Agenda não encontrada"));
            agenda.setDisponibilidade(disponibilidade);
            agendaService.atualizar(id, agenda);
            redirectAttributes.addFlashAttribute("mensagem", "Disponibilidade atualizada!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao atualizar agenda: " + e.getMessage());
        }

        return "redirect:/web/agendas";
    }

    @PostMapping("/{id}/remover")
    public String remover(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            agendaService.deletarPorId(id);
            redirectAttributes.addFlashAttribute("mensagem", "Horário removido!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao remover horário: " + e.getMessage());
        }

        return "redirect:/web/agendas";
    }
}
