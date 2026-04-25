package com.gaspo.api.repository.gaspo;

import com.gaspo.api.model.esus.AgendaModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendaRepository extends JpaRepository<AgendaModel, Long>  {

}
