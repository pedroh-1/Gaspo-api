package com.gaspo.api.repository.gaspo;

import com.gaspo.api.model.enums.StatusProfissional;
import com.gaspo.api.model.gaspo.ProfissionalModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfissionalRepository extends JpaRepository<ProfissionalModel, Long> {
    List<ProfissionalModel> findByUnidadeSaudeId(Long unidadeId);
    List<ProfissionalModel> findByNomeContainingIgnoreCase(String nome);
    List<ProfissionalModel> findByEspecialidadeContainingIgnoreCase(String especialidade);
    List<ProfissionalModel> findByStatus(StatusProfissional status);
}
