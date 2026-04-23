package com.gaspo.api.model;

import java.util.Date;

public class ConsultaModel {

    private Long id;
    private Date data;
    private String horario;
    private StatusConsulta status;
    private String nome;

    public ConsultaModel(){}

    public ConsultaModel(Long id, Date data, String horario, StatusConsulta status) {
        this.id = id;
        this.data = data;
        this.horario = horario;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public StatusConsulta getStatus() {
        return status;
    }

    public void setStatus(StatusConsulta status) {
        this.status = status;
    }
}
