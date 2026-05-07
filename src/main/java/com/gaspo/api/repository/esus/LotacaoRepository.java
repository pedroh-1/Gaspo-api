package com.gaspo.api.repository.esus;

import com.gaspo.api.model.esus.LotacaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LotacaoRepository extends JpaRepository<LotacaoModel, Long> {
    List<LotacaoModel> findByProfissionalNomeAndUnidadeSaudeNome(String nomeProfissional, String nomeUnidade);
}
