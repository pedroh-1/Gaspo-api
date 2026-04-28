package com.gaspo.api.model.gaspo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "tb_funcionario", schema = "public") // ATENÇÃO: Verifique o nome real da tabela no banco
public class FuncionarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "co_seq_funcionario") // Nome comum para chaves primárias no padrão e-SUS
    private Long id;

    @Column(name = "no_funcionario", nullable = false)
    private String nome;

    @Column(name = "nu_cpf", unique = true)
    private String cpf;

    @Column(name = "nu_matricula")
    private String matricula;

    @Column(name = "ds_cargo")
    private String cargo;

    @Column(name = "dt_admissao")
    private LocalDate dataAdmissao;

    // Construtor vazio obrigatório para o JPA
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(LocalDate dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }
}