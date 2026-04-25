package com.gaspo.api.controller;

import com.gaspo.api.model.esus.AgendaModel;
import com.gaspo.api.model.esus.ProfissionalModel;
import com.gaspo.api.service.AgendaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/agendas")
public class AgendaController {

    private final AgendaService agendaService;

    public AgendaController(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    /**
     * RF013 - Gerenciar Agenda (O "Coração" do seu trabalho)
     * Este endpoint recebe a lista de horários e a data para gerar a grade.
     */
    @PostMapping("/gerar-grade")
    public ResponseEntity<String> gerarGrade(
            @RequestParam Long profissionalId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            @RequestBody List<LocalTime> horarios) {

        // Aqui buscaríamos o profissional pelo ID (usando o service do grupo correspondente)
        // Por enquanto, simulamos passando um novo profissional
        ProfissionalModel prof = new ProfissionalModel();
        prof.setId(profissionalId);

        agendaService.gerarGradeHoraria(prof, data, horarios);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Grade horária gerada com sucesso e sincronizada com e-SUS!");
    }

    // --- MÉTODOS CRUD BÁSICOS ---

    @GetMapping
    public ResponseEntity<List<AgendaModel>> listarTodos() {
        return ResponseEntity.ok(agendaService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendaModel> buscarPorId(@PathVariable Long id) {
        return agendaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        agendaService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgendaModel> atualizar(@PathVariable Long id, @RequestBody AgendaModel agenda) {
        return ResponseEntity.ok(agendaService.atualizar(id, agenda));
    }
}