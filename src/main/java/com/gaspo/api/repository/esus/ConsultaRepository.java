package com.gaspo.api.repository.esus;

import com.gaspo.api.model.ConsultaModel;
import com.gaspo.api.model.StatusConsulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultaRepository extends JpaRepository<ConsultaModel, Long> {


    //Buscar consultas por status
    List<ConsultaModel> findByStatus(StatusConsulta status);

    //Buscar consultas de um profissional
    List<ConsultaModel> findByProfissionalId(Long profissionalId);

    //Buscar consultas em um intervalo de tempo
    List<ConsultaModel> findByDataConsulta(LocalDateTime inicio, LocalDateTime fim);

    //Buscar pelo CPF do paciente
    List<ConsultaModel> findByCpfPaciente(String cpfPaciente);

}
