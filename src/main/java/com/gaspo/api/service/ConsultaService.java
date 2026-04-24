package com.gaspo.api.service;

import com.gaspo.api.model.ConsultaModel;
import com.gaspo.api.repository.ConsultaRepository;
import com.gaspo.api.repository.ProfissionalRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    public ConsultaModel realizarAgendamento(ConsultaModel consulta) {

        //validação do profissional
        if (!profissionalRepository.existsById(consulta.getProfissional().getId())) {
            throw new RuntimeException("Profissional não foi encontrado no sistema!");
        }
    }
}
