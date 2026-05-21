package com.gaspo.api.repository;

import com.gaspo.api.model.enums.StatusConsulta;
import com.gaspo.api.model.gaspo.ConsultaModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ConsultaRepository extends JpaRepository<ConsultaModel, Long> {

    Page<ConsultaModel> findByStatus(StatusConsulta status, Pageable pageable);

    @Query("SELECT c FROM ConsultaModel c WHERE c.status = :status AND c.data >= :agora ORDER BY c.data ASC")
    Page<ConsultaModel> findAgendamentosAtivos(@Param("status") StatusConsulta status, @Param("agora") Date agora, Pageable pageable);

    @Query("SELECT c FROM ConsultaModel c WHERE c.status <> :statusAgendado OR c.data < :agora ORDER BY c.data DESC")
    Page<ConsultaModel> findHistoricoPage(@Param("statusAgendado") StatusConsulta statusAgendado, @Param("agora") Date agora, Pageable pageable);

    @Query("SELECT c FROM ConsultaModel c WHERE c.paciente.id = :pacienteId AND c.status = :status AND c.data >= :agora ORDER BY c.data ASC")
    Page<ConsultaModel> findAgendamentosAtivosDoPaciente(
            @Param("pacienteId") Long pacienteId,
            @Param("status") StatusConsulta status,
            @Param("agora") Date agora,
            Pageable pageable
    );

    @Query("SELECT c FROM ConsultaModel c WHERE c.paciente.id = :pacienteId AND (c.status <> :statusAgendado OR c.data < :agora) ORDER BY c.data DESC")
    Page<ConsultaModel> findHistoricoDoPaciente(
            @Param("pacienteId") Long pacienteId,
            @Param("statusAgendado") StatusConsulta statusAgendado,
            @Param("agora") Date agora,
            Pageable pageable
    );
}
