package com.gaspo.api.model;

public class AgendaModel {

    private Long id;
    private Disponibilidade disponibilidade;


    public AgendaModel(Long id, Disponibilidade disponibilidade) {
        this.id = id;
        this.disponibilidade = disponibilidade;
    }

    public AgendaModel() {
    }

    public Disponibilidade getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(Disponibilidade disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
