package com.gaspo.api.model.enums;

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
