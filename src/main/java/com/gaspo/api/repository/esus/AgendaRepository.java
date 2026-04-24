package com.gaspo.api.repository.esus;

import com.gaspo.api.model.esus.AgendaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendaRepository extends JpaRepository<AgendaModel,Long> {
}
