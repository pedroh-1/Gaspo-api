package com.gaspo.api.controller;

import com.gaspo.api.dto.request.AgendamentoPorNomeDTO;
import com.gaspo.api.dto.request.ConsultaRequestDTO;
import com.gaspo.api.dto.response.ConsultaResponseDTO;
import com.gaspo.api.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    // cria um agendamento
    @PostMapping
    public ResponseEntity<ConsultaResponseDTO> agendar(@RequestBody AgendamentoPorNomeDTO consulta){
        ConsultaResponseDTO novaConsulta = consultaService.prepararAgendamento(consulta);
        return ResponseEntity.ok(novaConsulta);
    }

    //Listar todas as consultas
    @GetMapping
    public ResponseEntity<List<ConsultaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(consultaService.listarTodas());
    }

    @PutMapping("/cancelar/{id}")
    public ResponseEntity<String> cancelar(@PathVariable Long id){
        consultaService.cancelarConsulta(id);
        return ResponseEntity.ok("Consulta " + id + " cancelada om sucesso");
    }

}
