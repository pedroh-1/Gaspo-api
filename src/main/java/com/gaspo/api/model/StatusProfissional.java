package com.gaspo.api.model;

public enum StatusProfissional {
    ATENDENDO("Atendendo"),
    PAUSA("Em pausa"),
    AUSENTE("Ausente");

    private final String descricao;

    StatusProfissional(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
