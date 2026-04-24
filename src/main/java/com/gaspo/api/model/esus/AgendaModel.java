package com.gaspo.api.model.esus;

import com.gaspo.api.model.enums.Disponibilidade;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_agenda")
public class AgendaModel {

    @Id
    private Long     id1;
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

    public Long getId1() {
        return id1;
    }

    public void setId1(Long id1) {
        this.id1 = id1;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
