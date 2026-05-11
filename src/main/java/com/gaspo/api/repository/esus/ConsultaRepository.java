package com.gaspo.api.repository.esus;

import com.gaspo.api.model.esus.ConsultaModel;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface ConsultaRepository extends JpaRepository<ConsultaModel, Long> {

    // Buscar consultas por status
    List<ConsultaModel> findByStatus(Long status);

    Page<ConsultaModel> findByStatus(Long status, Pageable pageable);

    @Query("SELECT c FROM ConsultaModel c WHERE c.status = :status AND c.data >= :agora ORDER BY c.data ASC")
    List<ConsultaModel> findAgendamentosAtivos(@Param("status") Long status, @Param("agora") Date agora);

    @Query("SELECT c FROM ConsultaModel c WHERE c.status = :status AND c.data >= :agora ORDER BY c.data ASC")
    Page<ConsultaModel> findAgendamentosAtivos(@Param("status") Long status, @Param("agora") Date agora, Pageable pageable);

    @Query("SELECT c FROM ConsultaModel c WHERE c.status <> :statusAgendado OR c.data < :agora ORDER BY c.data DESC")
    List<ConsultaModel> findHistorico(@Param("statusAgendado") Long statusAgendado, @Param("agora") Date agora, Pageable pageable);

    @Query("SELECT c FROM ConsultaModel c WHERE c.status <> :statusAgendado OR c.data < :agora ORDER BY c.data DESC")
    Page<ConsultaModel> findHistoricoPage(@Param("statusAgendado") Long statusAgendado, @Param("agora") Date agora, Pageable pageable);

    // Buscar consultas de um profissional através da lotação
    List<ConsultaModel> findByLotacaoProfissionalId(Long profissionalId);

    // Buscar consultas vinculadas ao paciente do prontuário
    List<ConsultaModel> findByProntuarioPacienteIdOrderByDataDesc(Long pacienteId);

    Page<ConsultaModel> findByProntuarioPacienteIdOrderByDataDesc(Long pacienteId, Pageable pageable);

    @Query("SELECT c FROM ConsultaModel c WHERE c.prontuario.paciente.id = :pacienteId AND c.status = :status AND c.data >= :agora ORDER BY c.data ASC")
    Page<ConsultaModel> findAgendamentosAtivosDoPaciente(
            @Param("pacienteId") Long pacienteId,
            @Param("status") Long status,
            @Param("agora") Date agora,
            Pageable pageable
    );

    @Query("SELECT c FROM ConsultaModel c WHERE c.prontuario.paciente.id = :pacienteId AND (c.status <> :statusAgendado OR c.data < :agora) ORDER BY c.data DESC")
    Page<ConsultaModel> findHistoricoDoPaciente(
            @Param("pacienteId") Long pacienteId,
            @Param("statusAgendado") Long statusAgendado,
            @Param("agora") Date agora,
            Pageable pageable
    );

    // Buscar consultas em um intervalo de tempo
    @Query("SELECT c FROM ConsultaModel c WHERE c.data BETWEEN :inicio AND :fim")
    List<ConsultaModel> findByDataConsulta(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
}
