package com.gaspo.api.model.gaspo;

import com.gaspo.api.model.enums.StatusProfissional;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_profissional")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfissionalModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "co_seq_profissional")
    private Long id;

    @Column(name = "no_profissional", nullable = false)
    private String nome;

    @Column(name = "ds_especialidade")
    private String especialidade;

    @Column(name = "ds_email", unique = true)
    private String email;

    @Column(name = "nu_telefone")
    private String telefone;

    @Enumerated(EnumType.STRING)
    @Column(name = "st_atendimento", nullable = false)
    private StatusProfissional status = StatusProfissional.ATENDENDO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "co_unidade_saude")
    private UnidadeSaudeModel unidadeSaude;
}
