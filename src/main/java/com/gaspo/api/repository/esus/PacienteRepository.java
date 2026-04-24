package com.gaspo.api.repository.esus;

import com.gaspo.api.model.esus.PacienteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<PacienteModel, Long> {
    // Aqui pode adicionar métodos de busca personalizados no futuro,
    // como buscar por CPF ou CNS.
}