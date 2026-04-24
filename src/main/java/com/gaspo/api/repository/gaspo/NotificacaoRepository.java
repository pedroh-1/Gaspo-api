package com.gaspo.api.repository.gaspo;

import com.gaspo.api.model.gaspo.NotificacaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacaoRepository extends JpaRepository<NotificacaoModel, Long> {

    // Método extra muito útil para notificações:
    // O Spring Data JPA cria a query SQL automaticamente apenas pelo nome do método!
    List<NotificacaoModel> findByLidaFalse();
}