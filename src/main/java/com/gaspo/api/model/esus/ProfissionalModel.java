package com.gaspo.api.model.esus;

import com.gaspo.api.model.enums.StatusProfissional;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "tb_profissional")
@Data // gera getters, setters e hashCode automaticamente
@NoArgsConstructor // vai gerar o construtor vazio obrigatório para o JPA
public class ProfissionalModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String especialidade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusProfissional status;

    @ManyToOne
    @JoinColumn(name = "co_unidade_saude")
    private UnidadeSaudeModel unidadeSaude;

    @OneToMany(mappedBy = "profissional")
    private List<ConsultaModel> consultas;
}