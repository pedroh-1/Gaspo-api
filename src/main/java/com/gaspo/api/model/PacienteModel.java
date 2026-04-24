package com.gaspo.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "tb_cidadao", schema = "public") // ATENÇÃO: Substitua "tb_cidadao" pelo nome exato da tabela no e-SUS
public class PacienteModel {

    @Id
    @Column(name = "co_seq_cidadao") // Substitua pelo nome exato da chave primária
    private Long id;

    @Column(name = "no_cidadao") // Substitua pelo nome da coluna que guarda o nome
    private String nome;

    @Column(name = "nu_cpf") // Exemplo de coluna para CPF
    private String cpf;

    @Column(name = "nu_cns") // Exemplo de coluna para o Cartão Nacional de Saúde (CNS)
    private String cns;

    @Column(name = "dt_nascimento") // Exemplo de coluna para a data de nascimento
    private LocalDate dataNascimento;

    // Construtor vazio (obrigatório para o JPA/Hibernate)
    public PacienteModel() {
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

    public String getCns() {
        return cns;
    }

    public void setCns(String cns) {
        this.cns = cns;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}