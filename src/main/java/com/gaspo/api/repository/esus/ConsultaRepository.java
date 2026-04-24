package com.gaspo.api.repository.esus;

import com.gaspo.api.model.esus.ConsultaModel;
import com.gaspo.api.model.enums.StatusConsulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultaRepository extends JpaRepository<ConsultaModel, Long> {


    //Buscar consultas por status
    List<ConsultaModel> findByStatus(StatusConsulta status);

    //Buscar consultas de um profissional
    List<ConsultaModel> findByProfissionalId(Long profissionalId);

    //Buscar consultas em um intervalo de tempo
    @Query("SELECT c FROM ConsultaModel c WHERE c.data BETWEEN :inicio AND :fim")
    List<ConsultaModel> findByDataConsulta(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);


}
