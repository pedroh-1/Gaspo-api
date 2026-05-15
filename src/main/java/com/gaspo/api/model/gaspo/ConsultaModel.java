package com.gaspo.api.model.gaspo;

import com.gaspo.api.model.enums.StatusConsulta;
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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "tb_consulta")
@Data
@NoArgsConstructor
public class ConsultaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "co_seq_consulta")
    private Long id;

    @Column(name = "dt_consulta", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;

    @Enumerated(EnumType.STRING)
    @Column(name = "st_consulta", nullable = false)
    private StatusConsulta status = StatusConsulta.AGENDADA;

    @Column(name = "ds_motivo_cancelamento", length = 500)
    private String motivoCancelamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "co_usuario", nullable = false)
    private UsuarioModel paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "co_profissional", nullable = false)
    private ProfissionalModel profissional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "co_unidade_saude")
    private UnidadeSaudeModel unidadeSaude;
}
