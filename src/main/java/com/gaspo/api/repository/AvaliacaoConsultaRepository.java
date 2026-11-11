package com.gaspo.api.repository;

import com.gaspo.api.model.gaspo.AvaliacaoConsultaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvaliacaoConsultaRepository extends JpaRepository<AvaliacaoConsultaModel, Long> {
    List<AvaliacaoConsultaModel> findAllByOrderByDataAvaliacaoDesc();
    List<AvaliacaoConsultaModel> findByPacienteIdOrderByDataAvaliacaoDesc(Long pacienteId);
}
