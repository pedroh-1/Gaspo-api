package com.gaspo.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_cbo", schema = "public")
public class CboEntity {

    @Id
    @Column(name = "co_cbo")
    private String codigoCbo;

    @Column(name = "no_cbo")
    private String nomeEspecialidade;

    // Getters e Setters
    public String getCodigoCbo() {
        return codigoCbo;
    }

    public void setCodigoCbo(String codigoCbo) {
        this.codigoCbo = codigoCbo;
    }

    public String getNomeEspecialidade() {
        return nomeEspecialidade;
    }

    public void setNomeEspecialidade(String nomeEspecialidade) {
        this.nomeEspecialidade = nomeEspecialidade;
    }
}