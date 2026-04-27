package com.gaspo.api.model.esus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tb_lotacao", schema = "public")
@Getter
@Setter
@NoArgsConstructor
public class LotacaoModel {

    @Id
    @Column(name = "co_ator_papel")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "co_prof")
    @JsonIgnoreProperties({"lotacoes", "consultas"})
    private ProfissionalModel profissional;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "co_unidade_saude")
    private UnidadeSaudeModel unidadeSaude;

    @Column(name = "co_cbo")
    private String codigoCbo;

    @OneToMany(mappedBy = "lotacao", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("lotacao")
    private List<ConsultaModel> consultas;
}
