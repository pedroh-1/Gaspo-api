package com.gaspo.api.repository;

import com.gaspo.api.model.ProfissionalModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProfissionalRepository
        extends JpaRepository<ProfissionalModel, Long> {

    @Query("""
        SELECT p
        FROM ProfissionalEntity p
        JOIN LotacaoEntity l ON l.profissional.id = p.id
        WHERE l.unidadeSaude.id = :unidadeId
    """)
    List<ProfissionalModel> buscarPorUnidade(
            @Param("unidadeId") Long unidadeId
    );
}
