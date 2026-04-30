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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ConsultaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_agendado")
    @SequenceGenerator(name = "seq_agendado", sequenceName = "sq_agendado_coseqagendado", allocationSize = 1)
    @Column(name = "co_seq_agendado")
    private Long id;

    @Column(name = "dt_agendado", nullable = false)
    private Date data;

    @Column(name = "st_agendado", nullable = false)
    private Long status;

    @Column(name = "st_sincronizacao", nullable = false)
    private String statusSincronizacao = "AGUARDANDO_SINCRONIZACAO";

    @Column(name = "st_fora_ubs", nullable = false)
    private Integer foraUbs = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "co_lotacao_agendada") 
    @JsonIgnoreProperties("consultas")
    private LotacaoModel lotacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "co_prontuario")
    private ProntuarioModel prontuario;

}
