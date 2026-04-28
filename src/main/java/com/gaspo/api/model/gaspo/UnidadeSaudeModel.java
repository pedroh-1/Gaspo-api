package com.gaspo.api.model.gaspo;



import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "unidades_saude")
public class UnidadeSaudeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String endereco;

    @Column(nullable = false)
    private String telefone;

    @Column(name = "horario_funcionamento", nullable = false)
    private String horarioFuncionamento;

    @Column(name = "cidade", nullable = false)
    private String cidade;

    @Column(name = "estado", nullable = false)
    private String estado;

    @OneToMany(mappedBy = "unidadeSaude")
    private List<FuncionarioModel> funcionarios = new ArrayList<>();

    @OneToMany(mappedBy = "unidadeSaude")
    private List<AvisoModel> avisos = new ArrayList<>();

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getHorarioFuncionamento() { return horarioFuncionamento; }
    public void setHorarioFuncionamento(String horarioFuncionamento) { this.horarioFuncionamento = horarioFuncionamento; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public List<FuncionarioModel> getFuncionarios() { return funcionarios; }
    public void setFuncionarios(List<FuncionarioModel> funcionarios) { this.funcionarios = funcionarios; }

    public List<AvisoModel> getAvisos() { return avisos; }
    public void setAvisos(List<AvisoModel> avisos) { this.avisos = avisos; }
}