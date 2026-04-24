package com.gaspo.api.controller;

import com.gaspo.api.model.esus.ConsultaModel;
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
    public ResponseEntity<ConsultaModel> agendar(@RequestBody ConsultaModel consulta){
        ConsultaModel novaConsulta = consultaService.realizarAgendamento(consulta);
        return ResponseEntity.ok(novaConsulta);
    }

    //Listar todas as consultas
    @GetMapping
    public ResponseEntity<List<ConsultaModel>> listarTodas() {
        return ResponseEntity.ok(consultaService.listarTodas());
    }

    @PutMapping("/cancelar/{id}")
    public ResponseEntity<String> cancelar(@PathVariable Long id){
        consultaService.cancelarConsulta(id);
        return ResponseEntity.ok("Consulta " + id + " cancelada om sucesso");
    }


}
