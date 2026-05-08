package com.gaspo.api.model.gaspo;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tb_aviso")
public class AvisoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "co_seq_aviso")
    private Long id;

    @Column(name = "no_titulo", nullable = false)
    private String titulo;

    @Column(name = "ds_mensagem", nullable = false, length = 1000)
    private String mensagem;

    @Column(name = "dt_publicacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPublicacao;

    @Column(name = "dt_expiracao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataExpiracao;

    @Column(name = "st_ativo")
    private Boolean ativo;

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

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Date getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(Date dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public Date getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataExpiracao(Date dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
