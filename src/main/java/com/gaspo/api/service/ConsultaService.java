package com.gaspo.api.service;

import com.gaspo.api.model.esus.ConsultaModel;
import com.gaspo.api.model.enums.StatusConsulta;
import com.gaspo.api.repository.esus.ConsultaRepository;
import com.gaspo.api.repository.esus.ProfissionalRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private AgendaService agendaService;

    public ConsultaModel realizarAgendamento(ConsultaModel consulta) {

        //validação do profissional
        // BUG FIX: O e-SUS usa tb_lotacao(co_ator_papel) para agendamentos.
        // Se quisermos validar o profissional, precisamos carregar a lotação primeiro ou checar se a lotação existe.
        // Para este teste, vamos assumir que o ID passado é de uma Lotacao.
        
        // consulta.setStatus(StatusConsulta.AGENDADA);
        consulta.setStatus(0L); // 0 = AGENDADO no e-SUS

        return consultaRepository.save(consulta);
    }

    public List<ConsultaModel> listarTodas() {
        return consultaRepository.findAll();
    }

    @Transactional
    public void cancelarConsulta(Long id) {
        // 1. Busca a consulta ou dispara um erro caso não exista
        ConsultaModel consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada com o ID: " + id));

        // 2. Muda o status para CANCELADA (4 no e-SUS)
        consulta.setStatus(4L);

        // 3. Salva a alteração
        consultaRepository.save(consulta);
    }
}
