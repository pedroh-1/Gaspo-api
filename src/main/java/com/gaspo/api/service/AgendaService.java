package com.gaspo.api.service;

import com.gaspo.api.repository.esus.AgendaRepository;
import com.gaspo.api.model.esus.AgendaModel;
import org.springframework.stereotype.Service;

@Service
public class AgendaService {

    private final AgendaRepository agendaRepository;

    public AgendaService(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }
}
