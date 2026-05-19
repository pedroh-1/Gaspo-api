package com.gaspo.api.controller;

import com.gaspo.api.dto.request.AgendaRequestDTO;
import com.gaspo.api.dto.response.AgendaDisponibilidadeResponseDTO;
import com.gaspo.api.model.gaspo.ProfissionalModel;
import com.gaspo.api.service.AgendaService;
import com.gaspo.api.service.ProfissionalService;
import jakarta.validation.Valid;
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
    private final ProfissionalService profissionalService;

    public AgendaController(AgendaService agendaService, ProfissionalService profissionalService) {
        this.agendaService = agendaService;
        this.profissionalService = profissionalService;
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

        ProfissionalModel prof = profissionalService.buscarPorId(profissionalId)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));

        agendaService.gerarGradeHoraria(prof, data, horarios);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Grade horária gerada com sucesso!");
    }

    // --- MÉTODOS CRUD BÁSICOS ---

    @GetMapping
    public ResponseEntity<List<AgendaDisponibilidadeResponseDTO>> listarTodos() {
        return ResponseEntity.ok(agendaService.listarTodosDTO());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendaDisponibilidadeResponseDTO> buscarPorId(@PathVariable Long id) {
        return agendaService.buscarPorIdDTO(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        agendaService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgendaDisponibilidadeResponseDTO> atualizar(@PathVariable Long id,
                                                                      @Valid @RequestBody AgendaRequestDTO agenda) {
        return ResponseEntity.ok(agendaService.atualizar(id, agenda));
    }
}
