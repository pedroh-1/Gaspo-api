package com.gaspo.api.repository.gaspo;

import com.gaspo.api.model.gaspo.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {
    boolean existsByEmail(String email);
    boolean existsByIdCidadaoEsus(Long idCidadaoEsus);
}
