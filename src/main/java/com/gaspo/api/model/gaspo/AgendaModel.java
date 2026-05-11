package com.gaspo.api.model.gaspo;

import com.gaspo.api.model.enums.Disponibilidade;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "tb_agenda")
@Data
@NoArgsConstructor
public class AgendaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private LocalTime horario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Disponibilidade disponibilidade;

    @Column(nullable = false)
    private Long profissionalId;

    @Column(nullable = false)
    private String profissionalNome;

    public AgendaModel(LocalDate data, LocalTime horario, Disponibilidade disponibilidade, Long profissionalId, String profissionalNome) {
        this.data = data;
        this.horario = horario;
        this.disponibilidade = disponibilidade;
        this.profissionalId = profissionalId;
        this.profissionalNome = profissionalNome;
    }
}
