package com.gaspo.api.model.gaspo;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_unidade_saude")
public class UnidadeSaudeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "no_unidade", nullable = false)
    private String nome;

    @Column(name = "ds_endereco")
    private String endereco;

    @Column(name = "nu_telefone")
    private String telefone;

    @Column(name = "ds_horario_funcionamento")
    private String horarioFuncionamento;

    public UnidadeSaudeModel() {
    }

    // --- Getters e Setters ---

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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getHorarioFuncionamento() {
        return horarioFuncionamento;
    }

    public void setHorarioFuncionamento(String horarioFuncionamento) {
        this.horarioFuncionamento = horarioFuncionamento;
    }
}