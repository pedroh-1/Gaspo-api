package com.gaspo.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_usuario", schema = "public") // ATENÇÃO: Substitua "tb_usuario" pelo nome real da tabela no banco e-SUS, caso seja diferente.
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "co_seq_usuario") // Substitua pelo nome exato da coluna da chave primária
    private Long id;

    @Column(name = "no_usuario") // Substitua pelo nome exato da coluna de nome
    private String nome;

    @Column(name = "nu_cpf") // Exemplo de coluna para CPF
    private String cpf;

    @Column(name = "ds_email") // Exemplo de coluna para E-mail
    private String email;

    @Column(name = "ds_senha") // Exemplo de coluna para Senha
    private String senha;

    // Construtor vazio (obrigatório para o JPA/Hibernate)
    public UsuarioModel() {
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}