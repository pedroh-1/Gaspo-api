package com.gaspo.api.model.esus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "tb_cidadao", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PacienteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "co_seq_cidadao")
    private Long id;

    @Column(name = "no_cidadao", nullable = false)
    private String nome;

    @Column(name = "nu_cpf", unique = true)
    private String cpf;

    @Column(name = "nu_cns")
    private String cns;

    @Column(name = "dt_nascimento")
    private LocalDate dataNascimento;
}