package com.gaspo.api.model.gaspo;

import com.gaspo.api.model.esus.PacienteModel;
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

    @JoinColumn(name = "co_seq_cidadao")
    private Long pacienteId; // Relacionamento com Paciente

    // Construtor vazio obrigatório para o JPA
    public AvaliacaoModel() {
    }

    // Construtor completo para facilitar o uso no Service/Controller
    public AvaliacaoModel(int nota, String comentario, Long pacienteId) {
        this.nota = nota;
        this.comentario = comentario;
        this.pacienteId = pacienteId;
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
}
