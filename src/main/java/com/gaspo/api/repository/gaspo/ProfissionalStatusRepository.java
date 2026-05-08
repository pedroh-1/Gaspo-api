package com.gaspo.api.repository.gaspo;

import com.gaspo.api.model.gaspo.ProfissionalStatusModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfissionalStatusRepository extends JpaRepository<ProfissionalStatusModel, Long> {
}
