package com.gaspo.api.model.gaspo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_aviso", schema = "public") // Verifique se o nome da tabela no e-SUS é este
public class AvisoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "co_seq_aviso") // Nome padrão para sequências no e-SUS
    private Long id;

    @Column(name = "no_titulo", nullable = false)
    private String titulo;

    @Column(name = "ds_aviso", length = 1000)
    private String descricao;

    @Column(name = "dt_publicacao")
    private LocalDateTime dataPublicacao;

    @Column(name = "dt_expiracao")
    private LocalDateTime dataExpiracao;

    @Column(name = "st_ativo")
    private Boolean ativo;

    // Construtor vazio (obrigatório para o JPA)
    public AvisoModel() {
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(LocalDateTime dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public LocalDateTime getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataExpiracao(LocalDateTime dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
