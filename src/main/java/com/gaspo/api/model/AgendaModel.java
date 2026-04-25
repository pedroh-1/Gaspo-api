package com.gaspo.api.model;


public class AgendaModel {

    private Long id;
    private Disponibilidade disponbilidade;


    public AgendaModel(Long id, Disponibilidade disponbilidade) {
        this.id = id;
        this.disponbilidade = disponbilidade;
    }

    public AgendaModel() {
    }

    public Disponibilidade getDisponbilidade() {
        return disponbilidade;
    }

    public void setDisponbilidade(Disponibilidade disponbilidade) {
        this.disponbilidade = disponbilidade;
    }

    public Long getId() {
        return id;
    }


}
