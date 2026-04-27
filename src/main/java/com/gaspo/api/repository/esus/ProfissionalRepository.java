package com.gaspo.api.repository.esus;

import com.gaspo.api.model.esus.ProfissionalModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProfissionalRepository
        extends JpaRepository<ProfissionalModel, Long> {

    @Query(value = "SELECT DISTINCT p.* FROM tb_prof p " +
                   "JOIN tb_lotacao l ON p.co_seq_prof = l.co_prof " +
                   "WHERE l.co_unidade_saude = :unidadeId", nativeQuery = true)
    List<ProfissionalModel> buscarPorUnidade(@Param("unidadeId") Long unidadeId);
}
