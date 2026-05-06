package com.gaspo.api.model.gaspo;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_funcionario")
public class FuncionarioModel {

    // Atributos herdados de Pessoa
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "no_funcionario", nullable = false)
    private String nome;

    @Column(name = "ds_email", unique = true)
    private String email;

    @Column(name = "nu_telefone")
    private String telefone;

    @Column(name = "ds_senha", nullable = false)
    private String senha;

    // Atributo específico de Funcionario
    @Column(name = "ds_cargo")
    private String cargo;

    public FuncionarioModel() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}