package com.gaspo.api.repository.esus;

import com.gaspo.api.model.esus.ProfissionalModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProfissionalRepository
        extends JpaRepository<ProfissionalModel, Long> {

    @Query("""
        SELECT p
        FROM ProfissionalModel p
        JOIN LotacaoModel l ON l.profissional.id = p.id
        WHERE l.unidadeSaude.id = :unidadeId
    """)
    List<ProfissionalModel> buscarPorUnidade(
            @Param("unidadeId") Long unidadeId
    );
}
