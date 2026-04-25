package com.gaspo.api.service;


import com.gaspo.api.model.AgendaModel;
import com.gaspo.api.repository.gaspo.AgendaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgendaService {

    //instaciando a interface repository
    private AgendaRepository agendaRepository;


    //construtor AgendaRepository
    public AgendaService(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    //metodo Salvar
    public AgendaModel salvar (AgendaModel agenda) {
        return agendaRepository.save(agenda);

    }
    /*metodo listar todos metodo errado
    public AgendaModel listartodos(AgendaModel agenda){
        return List<agendaRepository.findAll(agenda)>;
    }

     */
    //metodo listar todos

    public List<AgendaModel> listarTodos(){
        return agendaRepository.findAll();

    }

    //buscar por id
    public Optional<AgendaModel> buscarPorId (Long id){
        return agendaRepository.findById(id);
    }

    //metodo deletar por id
    public void deletarPorId (Long id){
        agendaRepository.deleteById(id);
    }
    //metodo Atualizar

    public AgendaModel atualizar(Long id,AgendaModel agendaAtualizada){
        AgendaModel existe = agendaRepository.findById(id).orElseThrow(()-> new RuntimeException("Agenda não encontrada"));

        existe.setDisponbilidade(agendaAtualizada.getDisponbilidade());

        return agendaRepository.save(existe);


    }


}
