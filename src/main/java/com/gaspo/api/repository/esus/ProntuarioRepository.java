package com.gaspo.api.repository.esus;

import com.gaspo.api.model.esus.ProntuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProntuarioRepository extends JpaRepository<ProntuarioModel, Long> {
    Optional<ProntuarioModel> findFirstByPacienteId(Long pacienteId);
}
