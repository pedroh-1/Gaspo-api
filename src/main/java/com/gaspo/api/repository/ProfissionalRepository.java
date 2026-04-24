package com.gaspo.api.repository;

import com.gaspo.api.model.ProfessionalModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProfissionalRepository
        extends JpaRepository<ProfessionalModel, Long> {

    @Query("""
        SELECT p
        FROM ProfissionalEntity p
        JOIN LotacaoEntity l ON l.profissional.id = p.id
        WHERE l.unidadeSaude.id = :unidadeId
    """)
    List<ProfessionalModel> buscarPorUnidade(
            @Param("unidadeId") Long unidadeId
    );
}
