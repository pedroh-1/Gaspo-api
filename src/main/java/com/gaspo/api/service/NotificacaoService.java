package com.gaspo.api.service;

import com.gaspo.api.model.gaspo.NotificacaoModel;
import com.gaspo.api.repository.gaspo.NotificacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificacaoService {

    @Autowired
    private NotificacaoRepository repository;

    /**
     * Retorna todas as notificações registadas no sistema.
     */
    public List<NotificacaoModel> listarTodas() {
        return repository.findAll();
    }

    /**
     * Procura uma notificação específica pelo seu ID.
     */
    public Optional<NotificacaoModel> buscarPorId(Long id) {
        return repository.findById(id);
    }

    /**
     * Cria e guarda uma nova notificação na base de dados.
     * Garante que a data de envio e o status "lida" têm valores padrão.
     */
    public NotificacaoModel criar(NotificacaoModel notificacao) {
        if (notificacao.getDataEnvio() == null) {
            notificacao.setDataEnvio(LocalDateTime.now());
        }
        if (notificacao.getLida() == null) {
            notificacao.setLida(false);
        }
        return repository.save(notificacao);
    }

    /**
     * Marca uma notificação específica como lida, atualizando-a na base de dados.
     */
    public Optional<NotificacaoModel> marcarComoLida(Long id) {
        Optional<NotificacaoModel> notificacaoOpt = repository.findById(id);

        if (notificacaoOpt.isPresent()) {
            NotificacaoModel notificacao = notificacaoOpt.get();
            notificacao.setLida(true);
            return Optional.of(repository.save(notificacao));
        }

        return Optional.empty(); // Retorna vazio se a notificação não for encontrada
    }
}