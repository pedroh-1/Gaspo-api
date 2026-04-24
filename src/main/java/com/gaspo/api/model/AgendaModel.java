package com.gaspo.api.model;

public class AgendaModel {

    private Long id;
    private String disponibilidade;


    public AgendaModel(String disponibilidade, Long id) {
        this.disponibilidade = disponibilidade;
        this.id = id;
    }

    public String getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(String disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
