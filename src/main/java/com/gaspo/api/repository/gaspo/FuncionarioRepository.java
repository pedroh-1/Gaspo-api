package com.gaspo.api.repository.gaspo;


import com.gaspo.api.model.gaspo.FuncionarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<FuncionarioModel, Long> {

    /**
     * Procura um funcionário específico utilizando o seu CPF.
     * O Spring Data JPA traduz isto automaticamente para:
     * SELECT * FROM tb_funcionario WHERE nu_cpf = ?
     */
    Optional<FuncionarioModel> findByCpf(String cpf);

    /**
     * Procura um funcionário utilizando o seu número de matrícula.
     */
    Optional<FuncionarioModel> findByMatricula(String matricula);
}
