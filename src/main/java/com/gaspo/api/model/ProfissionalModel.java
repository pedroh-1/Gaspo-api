package com.gaspo.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="tb_profissional")
public class ProfissionalModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String especialidade;

    @Enumerated(EnumType.STRING)
    private StatusProfissional status;

    @ManyToOne
    @JoinColumn(name="co_unidade_saude")
    private UnidadeSaudeModel unidadeSaude;

    //@JsonIgnore
   // @OneToMany(mappedBy ="profissional")
    private List<ConsultaModel> consultas; // CALMO aqui ta acusando erro mas pq a classe ConsultaModel ainda nao virou entity
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public StatusProfissional getStatus() {
        return status;
    }

    public void setStatus(StatusProfissional status) {
        this.status = status;
    }

    public UnidadeSaudeModel getUnidadeSaude() {
        return unidadeSaude;
    }

    public void setUnidadeSaude(UnidadeSaudeModel unidadeSaude) {
        this.unidadeSaude = unidadeSaude;
    }

    public List<ConsultaModel> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<ConsultaModel> consultas) {
        this.consultas = consultas;
    }
}
