package com.gaspo.api.service;



import com.gaspo.api.model.enums.Disponibilidade;
import com.gaspo.api.model.esus.AgendaModel;
import com.gaspo.api.model.esus.ProfissionalModel;
import com.gaspo.api.repository.esus.AgendaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class AgendaService {

    private final AgendaRepository agendaRepository;


      public AgendaService(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }


    /**
     * RF013 - Gerenciar Agenda de Atendimento
     * Este método implementa o LOOP do Diagrama de Sequência para criar vários horários de uma vez.
     */
    @Transactional
    public void gerarGradeHoraria(ProfissionalModel profissional, LocalDate data, List<LocalTime> horarios) {
        // Implementação do 'loop [para cada horário]' do diagrama de sequência [cite: 342]
        for (LocalTime hora : horarios) {
            AgendaModel slot = new AgendaModel();
            slot.setProfissional(profissional);
            slot.setData(data);
            slot.setHorario(hora);
            slot.setDisponibilidade(Disponibilidade.DISPONIVEL);

            // Operação 'adicionarHorario()' do diagrama [cite: 344]
            agendaRepository.save(slot);
        }

        // Chamada para a sincronização externa exigida pelo projeto
        sincronizarComPecESus();
    }

    private void sincronizarComPecESus() {
        // Placeholder para a integração com o banco do PEC e-SUS v5.4.33
        System.out.println("Sincronizando agenda com o sistema PEC e-SUS...");
    }

    public List<AgendaModel> listarTodos() {
        return agendaRepository.findAll();
    }

    public Optional<AgendaModel> buscarPorId(Long id) {
        return agendaRepository.findById(id);
    }

    public void deletarPorId(Long id) {
        agendaRepository.deleteById(id);
    }

    /**
     * Método Atualizar corrigido (Removido o erro 'disponbilidade' -> 'disponibilidade')
     */
    @Transactional
    public AgendaModel atualizar(Long id, AgendaModel agendaAtualizada) {
        AgendaModel existe = agendaRepository.findById(id).orElseThrow(() -> new RuntimeException("Agenda não encontrada"));

        // Correção do typo: setDisponbilidade para setDisponibilidade
        existe.setDisponibilidade(agendaAtualizada.getDisponibilidade());
        existe.setData(agendaAtualizada.getData());
        existe.setHorario(agendaAtualizada.getHorario());

        return agendaRepository.save(existe);
    }
}