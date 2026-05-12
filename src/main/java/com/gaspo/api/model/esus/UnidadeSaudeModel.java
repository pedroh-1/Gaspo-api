package com.gaspo.api.model.esus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_unidade_saude", schema = "public")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UnidadeSaudeModel {

    @Id
    @Column(name = "co_seq_unidade_saude")
    private Long id;

    @Column(name = "no_unidade_saude")
    private String nome;

    @Column(name = "nu_cnes")
    private String cnes;

    @Column(name = "nu_telefone_comercial")
    private String telefoneComercial;

    @Column(name = "ds_email")
    private String email;

    @Column(name = "ds_cep")
    private String cep;

    @Column(name = "ds_logradouro")
    private String logradouro;

    @Column(name = "nu_numero")
    private String numero;

    @Column(name = "no_bairro")
    private String bairro;

    @Column(name = "ds_complemento")
    private String complemento;

    @Column(name = "ds_ponto_referencia")
    private String pontoReferencia;

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCnes() {
        return cnes;
    }

    public String getTelefoneComercial() {
        return telefoneComercial;
    }

    public String getEmail() {
        return email;
    }

    public String getCep() {
        return cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public String getBairro() {
        return bairro;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getPontoReferencia() {
        return pontoReferencia;
    }

    public String getEnderecoFormatado() {
        StringBuilder endereco = new StringBuilder();
        append(endereco, logradouro);
        append(endereco, numero);
        append(endereco, bairro);
        append(endereco, cep != null && !cep.isBlank() ? "CEP " + cep : null);
        return endereco.isEmpty() ? null : endereco.toString();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCnes(String cnes) {
        this.cnes = cnes;
    }

    public void setTelefoneComercial(String telefoneComercial) {
        this.telefoneComercial = telefoneComercial;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public void setPontoReferencia(String pontoReferencia) {
        this.pontoReferencia = pontoReferencia;
    }

    private void append(StringBuilder builder, String valor) {
        if (valor == null || valor.isBlank()) {
            return;
        }
        if (!builder.isEmpty()) {
            builder.append(", ");
        }
        builder.append(valor);
    }
}
