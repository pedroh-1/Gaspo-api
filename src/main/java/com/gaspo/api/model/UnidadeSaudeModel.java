package com.gaspo.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_unidade_saude", schema = "public")
public class UnidadeSaudeModel {

    @Id
    @Column(name = "co_seq_unidade_saude")
    private Long id;

    @Column(name = "no_unidade_saude")
    private String nome;

    @Column(name = "nu_cnes")
    private String cnes;

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCnes() {
        return cnes;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCnes(String cnes) {
        this.cnes = cnes;
    }
}
