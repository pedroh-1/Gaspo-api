package com.gaspo.api.model.esus;

import com.gaspo.api.model.enums.StatusConsulta;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "tb_agendado", schema = "public")
@Data
@NoArgsConstructor
public class ConsultaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "co_seq_agendado")
    private Long id;

    @Column(name = "dt_agendado", nullable = false)
    private Date data;

    @Enumerated(EnumType.STRING)
    @Column(name = "st_agendamento", nullable = false)
    private StatusConsulta status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "co_lotacao") 
    @JsonIgnoreProperties("consultas")
    private LotacaoModel lotacao;

}
