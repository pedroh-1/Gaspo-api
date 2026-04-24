package com.gaspo.api.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "tb_agendado", schema = "public")
public class ConsultaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "co_seg_agendado")
    private Long id;

    @Column(name = "dt_agendado", nullable = false)
    private Date data;

    @Enumerated(EnumType.STRING)
    @Column(name = "st_agendamento", nullable = false)
    private StatusConsulta status;

    @ManyToOne
    @JoinColumn(name = "co_seq_profissional")
    private ProfessionalModel profissional;

    public ConsultaModel(){}

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

    public StatusConsulta getStatus() {
        return status;
    }

    public void setStatus(StatusConsulta status) {
        this.status = status;
    }

    public ProfessionalModel getProfissional(){
        return profissional;
    }

    public void setProfissional(ProfessionalModel profissional){
        this.profissional = profissional;
    }

}
