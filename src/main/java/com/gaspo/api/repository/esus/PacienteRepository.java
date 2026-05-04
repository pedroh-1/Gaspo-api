package com.gaspo.api.repository.esus;

import com.gaspo.api.model.esus.PacienteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<PacienteModel, Long> {
    Optional<PacienteModel> findFirstByCpf(String cpf);
}