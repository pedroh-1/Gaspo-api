package com.gaspo.api.repository.esus;

import com.gaspo.api.model.esus.UnidadeSaudeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadeSaudeRepository extends JpaRepository<UnidadeSaudeModel, Long> {
}
