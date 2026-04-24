package com.gaspo.api.repository.esus;

import com.gaspo.api.model.esus.LotacaoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LotacaoRepository
        extends JpaRepository<LotacaoModel, Long> {
}
