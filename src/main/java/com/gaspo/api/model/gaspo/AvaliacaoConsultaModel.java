package com.gaspo.api.model.gaspo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Avaliação do atendimento de uma consulta (diferente da avaliação do
 * profissional, em tb_avaliacao).
 */
@Entity
@Table(name = "tb_avaliacao_consulta")
@Data
@NoArgsConstructor
public class AvaliacaoConsultaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "co_seq_avaliacao_consulta")
    private Long id;

    @Column(name = "nu_nota", nullable = false)
    private int nota;

    @Column(name = "ds_comentario", length = 500)
    private String comentario;

    @Column(name = "ds_especialidade")
    private String especialidade;

    @Column(name = "co_profissional", nullable = false)
    private Long profissionalId;

    @Column(name = "dt_consulta", nullable = false)
    private LocalDate dataConsulta;

    @Column(name = "hr_consulta")
    private LocalTime horarioConsulta;

    @Column(name = "ds_unidade")
    private String unidade;

    @Column(name = "co_usuario", nullable = false)
    private Long pacienteId;

    @Column(name = "dt_avaliacao", nullable = false)
    private LocalDateTime dataAvaliacao;
}
