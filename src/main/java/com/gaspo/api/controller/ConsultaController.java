package com.gaspo.api.controller;

import com.gaspo.api.dto.request.AgendamentoPorNomeDTO;
import com.gaspo.api.dto.request.ConsultaAgendamentoRequestDTO;
import com.gaspo.api.dto.request.ConsultaCancelamentoRequestDTO;
import com.gaspo.api.dto.response.AgendaDisponibilidadeResponseDTO;
import com.gaspo.api.dto.response.ConsultaResponseDTO;
import com.gaspo.api.dto.response.ConsultaResumoPageDTO;
import com.gaspo.api.model.gaspo.UsuarioModel;
import com.gaspo.api.service.ConsultaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    // cria um agendamento
    @PostMapping
    public ResponseEntity<ConsultaResponseDTO> agendar(@Valid @RequestBody ConsultaAgendamentoRequestDTO consulta,
                                                       @AuthenticationPrincipal UsuarioModel usuarioLogado){
        ConsultaResponseDTO novaConsulta = consultaService.agendarParaUsuarioLogado(consulta, usuarioLogado);
        return ResponseEntity.ok(novaConsulta);
    }

    @PostMapping("/por-nome")
    public ResponseEntity<ConsultaResponseDTO> agendarPorNome(@RequestBody AgendamentoPorNomeDTO consulta,
                                                              @AuthenticationPrincipal UsuarioModel usuarioLogado){
        ConsultaResponseDTO novaConsulta = consultaService.prepararAgendamentoParaUsuarioLogado(consulta, usuarioLogado);
        return ResponseEntity.ok(novaConsulta);
    }

    //Listar todas as consultas
    @GetMapping
    public ResponseEntity<ConsultaResumoPageDTO> listarTodas(Pageable pageable) {
        return ResponseEntity.ok(consultaService.listarTodas(pageable));
    }

    @GetMapping("/ativos")
    public ResponseEntity<ConsultaResumoPageDTO> listarMeusAgendamentosAtivos(@AuthenticationPrincipal UsuarioModel usuarioLogado,
                                                                               Pageable pageable) {
        return ResponseEntity.ok(consultaService.listarAgendamentosAtivosDoUsuario(usuarioLogado, pageable));
    }

    @GetMapping("/meu-historico")
    public ResponseEntity<ConsultaResumoPageDTO> listarMeuHistorico(@AuthenticationPrincipal UsuarioModel usuarioLogado,
                                                                    Pageable pageable) {
        return ResponseEntity.ok(consultaService.listarHistoricoDoUsuario(usuarioLogado, pageable));
    }

    @GetMapping("/horarios-disponiveis")
    public ResponseEntity<List<AgendaDisponibilidadeResponseDTO>> listarHorariosDisponiveis(
            @RequestParam Long profissionalId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        return ResponseEntity.ok(consultaService.listarHorariosDisponiveis(profissionalId, data));
    }

    @PutMapping("/cancelar/{id}")
    public ResponseEntity<String> cancelar(@PathVariable Long id,
                                           @RequestBody(required = false) ConsultaCancelamentoRequestDTO request,
                                           @AuthenticationPrincipal UsuarioModel usuarioLogado){
        consultaService.cancelarConsulta(id, usuarioLogado);
        return ResponseEntity.ok("Consulta " + id + " cancelada com sucesso");
    }

}
