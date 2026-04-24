package com.gaspo.api.repository;

import com.gaspo.api.model.LotacaoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LotacaoRepository
        extends JpaRepository<LotacaoModel, Long> {
}
