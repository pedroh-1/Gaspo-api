package com.gaspo.api.model.gaspo;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tb_avaliacao", schema = "public")
public class AvaliacaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int nota; //

    @Column(length = 500)
    private String comentario; //

    @Column(name = "co_seq_cidadao")
    private Long pacienteId; // Relacionamento com Paciente

    @Column(name = "co_seq_prof")
    private Long profissionalId; // Relacionamento com Profissional

    @Column(name = "dt_avaliacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAvaliacao;

    // Construtor vazio obrigatório para o JPA
    public AvaliacaoModel() {
    }

    // Construtor completo para facilitar o uso no Service/Controller
    public AvaliacaoModel(int nota, String comentario, Long pacienteId, Long profissionalId) {
        this.nota = nota;
        this.comentario = comentario;
        this.pacienteId = pacienteId;
        this.profissionalId = profissionalId;
    }

    // --- Getters e Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public Long getProfissionalId() {
        return profissionalId;
    }

    public void setProfissionalId(Long profissionalId) {
        this.profissionalId = profissionalId;
    }

    public Date getDataAvaliacao() {
        return dataAvaliacao;
    }

    public void setDataAvaliacao(Date dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }
}
