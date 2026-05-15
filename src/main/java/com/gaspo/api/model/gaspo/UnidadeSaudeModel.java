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

    @Column(name = "nu_cnes", unique = true)
    private String cnes;

    @Column(name = "ds_endereco")
    private String endereco;

    @Column(name = "ds_complemento")
    private String complemento;

    @Column(name = "ds_ponto_referencia")
    private String pontoReferencia;

    @Column(name = "nu_telefone")
    private String telefone;

    @Column(name = "ds_email")
    private String email;

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

    public String getCnes() {
        return cnes;
    }

    public void setCnes(String cnes) {
        this.cnes = cnes;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getPontoReferencia() {
        return pontoReferencia;
    }

    public void setPontoReferencia(String pontoReferencia) {
        this.pontoReferencia = pontoReferencia;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHorarioFuncionamento() {
        return horarioFuncionamento;
    }

    public void setHorarioFuncionamento(String horarioFuncionamento) {
        this.horarioFuncionamento = horarioFuncionamento;
    }
}
