package com.gaspo.api.controller;

import com.gaspo.api.dto.request.AvaliacaoRequestDTO;
import com.gaspo.api.dto.response.AvaliacaoResponseDTO;
import com.gaspo.api.model.gaspo.AvaliacaoModel;
import com.gaspo.api.model.esus.PacienteModel;
import com.gaspo.api.service.AvaliacaoService;
import com.gaspo.api.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService service;

    @PostMapping
    public ResponseEntity<AvaliacaoModel> registrar(@RequestBody AvaliacaoModel avaliacao) {
        AvaliacaoModel salva = service.salvar(avaliacao);
        return ResponseEntity.ok(salva);
    }

    @Autowired
    private AvaliacaoService avaliacaoService;

    @Autowired
    private PacienteService pacienteService;

    @PostMapping("/avaliar")
    public ResponseEntity<AvaliacaoResponseDTO> avaliar(@RequestBody AvaliacaoRequestDTO request) {
        // Busca o paciente
        PacienteModel paciente = pacienteService.buscarPorId(request.pacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        // Converte Request -> Model
        AvaliacaoModel avaliacao = new AvaliacaoModel(request.nota(), request.comentario(), paciente);
        AvaliacaoModel salva = avaliacaoService.salvar(avaliacao);

        // Converte Model -> Response
        AvaliacaoResponseDTO response = new AvaliacaoResponseDTO(
                salva.getId(),
                salva.getNota(),
                salva.getComentario(),
                paciente.getNome()
        );

        return ResponseEntity.ok(response);
    }
}
