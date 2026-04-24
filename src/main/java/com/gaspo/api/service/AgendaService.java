package com.gaspo.api.service;

import com.gaspo.api.repository.AgendaRepository;
import com.gaspo.api.model.AgendaModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gaspo.api.repository.AgendaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AgendaService {

    @Autowired
    private final AgendaRepository agendaRepository;
    public AgendaService(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;

    }

    public AgendaModel salvar(AgendaModel agenda){
        return agendaRepository.save(agenda);
    }

    public List<AgendaModel> listarTodos(){
        return agendaRepository.findAll();

    }

    public Optional<AgendaModel> buscarPorId(Long id){
        return agendaRepository.findById (id);
    }
    public void deletar (long id){
        agendaRepository.deleteById(id);
    }

    public AgendaModel update(Long id,AgendaModel agendaAtualizada){
        AgendaModel existente = agendaRepository.findById(id).orElseThrow(()-> new RuntimeException("Agenda não encontrada"));

        existente.setDisponibilidade(agendaAtualizada.getDisponibilidade());
        return agendaRepository.save(existente);


    }
}
