package com.gaspo.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


// Acho que não precisa dessa classe. -> Pedro :), essa classe representa a especialidade do medico, pode ser um atributo da classe profissional
@Entity
@Table(name = "tb_cbo", schema = "public")
public class CboModel {

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