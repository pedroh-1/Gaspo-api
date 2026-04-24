package com.gaspo.api.model.gaspo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_notificacao", schema = "public") // ATENÇÃO: Verifique o nome correto da tabela na sua base de dados
public class NotificacaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Assumindo que a tabela tem auto-incremento (SERIAL)
    @Column(name = "co_seq_notificacao") // Substitua pelo nome exato da chave primária
    private Long id;

    @Column(name = "ds_titulo") // Exemplo de coluna para o título
    private String titulo;

    @Column(name = "ds_mensagem") // Exemplo de coluna para o corpo da mensagem
    private String mensagem;

    @Column(name = "dt_envio") // Data e hora em que a notificação foi gerada
    private LocalDateTime dataEnvio;

    @Column(name = "st_lida") // Status para saber se já foi lida (booleano)
    private Boolean lida;

    // Construtor vazio (obrigatório para o JPA/Hibernate)
    public NotificacaoModel() {
    }

    // --- Getters e Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDateTime getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(LocalDateTime dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public Boolean getLida() {
        return lida;
    }

    public void setLida(Boolean lida) {
        this.lida = lida;
    }
}