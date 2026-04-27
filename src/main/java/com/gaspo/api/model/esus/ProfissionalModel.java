package com.gaspo.api.model.esus;

import com.gaspo.api.model.enums.StatusProfissional;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import java.util.List;

@Entity
@Table(name = "tb_prof")
@Getter
@Setter
@NoArgsConstructor
public class ProfissionalModel {

    @Id
    @Column(name = "co_seq_prof")
    private Long id;

    @Column(name = "no_profissional", nullable = false)
    private String nome;

    @Formula("(SELECT c.no_cbo FROM tb_lotacao l JOIN tb_cbo c ON l.co_cbo = c.co_cbo WHERE l.co_prof = co_seq_prof LIMIT 1)")
    private String especialidade;

    @Transient
    private StatusProfissional status = StatusProfissional.ATENDENDO;

    @OneToMany(mappedBy = "profissional", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("profissional")
    private List<LotacaoModel> lotacoes;
}
