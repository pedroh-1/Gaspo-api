package com.gaspo.api.repository.esus;

import com.gaspo.api.model.esus.AgendaModel;
import com.gaspo.api.model.enums.Disponibilidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AgendaRepository extends JpaRepository<AgendaModel,Long> {
    Optional<AgendaModel> findFirstByProfissionalIdAndDataAndHorario(Long profissionalId, LocalDate data, LocalTime horario);

    List<AgendaModel> findByProfissionalIdAndDataAndDisponibilidadeOrderByHorarioAsc(
            Long profissionalId,
            LocalDate data,
            Disponibilidade disponibilidade
    );
}
