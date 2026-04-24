package com.gaspo.api.controller;

import com.gaspo.api.model.AgendaModel;
import com.gaspo.api.repository.AgendaRepository;
import com.gaspo.api.service.AgendaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

public class AgendaController {

    private final AgendaService agendaService;


    public AgendaController(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    @PostMapping
    public ResponseEntity<AgendaModel> criarAgenda(@RequestBody AgendaModel agenda){
        AgendaModel novaAgenda = agendaService.salvar(agenda);
        return new ResponseEntity<>(novaAgenda, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AgendaModel>> listarAgenda(){
        List<AgendaModel> agenda = agendaService.listarTodos();
        return ResponseEntity.ok(agenda);

    }
    @GetMapping("/{id}")
    public ResponseEntity<AgendaModel> buscarAgenda (@PathVariable Long id){
        Optional<AgendaModel> agenda = agendaService.buscarPorId(id);

        // Se encontrar o usuário, retorna 200 OK. Se não, retorna 404 Not Found.
        return agenda.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());//


    }
    @DeleteMapping("/{id}") // Adicione o /{id} aqui!
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        agendaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgendaModel> atualizarAgenda(@PathVariable Long id, @RequestBody AgendaModel agenda){
        agenda.setDisponibilidade(agenda.getDisponibilidade());
        return ResponseEntity.ok(agendaService.salvar(agenda));
    }
}
