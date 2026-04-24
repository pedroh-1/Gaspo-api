package com.gaspo.api.model;

import jakarta.persistence.*;

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

    @ManyToOne
    @JoinColumn(name = "co_seq_cidadao")
    private PacienteModel paciente; // Relacionamento com Paciente

    // Construtor vazio obrigatório para o JPA
    public AvaliacaoModel() {
    }

    // Construtor completo para facilitar o uso no Service/Controller
    public AvaliacaoModel(int nota, String comentario, PacienteModel paciente) {
        this.nota = nota;
        this.comentario = comentario;
        this.paciente = paciente;
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

    public PacienteModel getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteModel paciente) {
        this.paciente = paciente;
    }
}
