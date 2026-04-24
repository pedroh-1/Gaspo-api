package com.gaspo.api.model.esus;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_lotacao", schema = "public")
public class LotacaoModel {

    @Id
    @Column(name = "co_ator_papel")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "co_prof")
    private ProfissionalModel profissional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "co_unidade_saude")
    private UnidadeSaudeModel unidadeSaude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "co_cbo")
    private CboModel cbo;

    // GETTERS E SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProfissionalModel getProfissional() {
        return profissional;
    }

    public void setProfissional(ProfissionalModel profissional) {
        this.profissional = profissional;
    }

    public UnidadeSaudeModel getUnidadeSaude() {
        return unidadeSaude;
    }

    public void setUnidadeSaude(UnidadeSaudeModel unidadeSaude) {
        this.unidadeSaude = unidadeSaude;
    }

    public CboModel getCbo() {
        return cbo;
    }

    public void setCbo(CboModel cbo) {
        this.cbo = cbo;
    }
}
