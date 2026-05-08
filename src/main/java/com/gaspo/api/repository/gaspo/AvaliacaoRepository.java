package com.gaspo.api.repository.gaspo;

import com.gaspo.api.model.gaspo.AvaliacaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvaliacaoRepository extends JpaRepository<AvaliacaoModel, Long> {
    List<AvaliacaoModel> findByProfissionalIdOrderByDataAvaliacaoDesc(Long profissionalId);
}
