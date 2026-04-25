package com.gaspo.api.model.esus;

import com.gaspo.api.model.enums.Disponibilidade;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "tb_agenda")
@Data // Gera getters, setters, toString automaticamente
@NoArgsConstructor
public class AgendaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate data; // O dia da agenda

    @Column(nullable = false)
    private LocalTime horario; // O horário específico (ex: 08:30)

    @Enumerated(EnumType.STRING)
    private Disponibilidade disponibilidade;

    // Relacionamento com o Profissional (conforme o diagrama de classe)
    // Muitos horários de agenda pertencem a um Profissional
    @ManyToOne
    @JoinColumn(name = "profissional_id")
    private ProfissionalModel profissional;

    public AgendaModel(LocalDate data, LocalTime horario, Disponibilidade disponibilidade, ProfissionalModel profissional) {
        this.data = data;
        this.horario = horario;
        this.disponibilidade = disponibilidade;
        this.profissional = profissional;
    }

}