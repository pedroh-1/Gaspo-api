package com.gaspo.api.repository;

import com.gaspo.api.model.gaspo.UnidadeSaudeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadeSaudeRepository extends JpaRepository<UnidadeSaudeModel, Long> {
}
