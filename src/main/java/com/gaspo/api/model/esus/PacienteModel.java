package com.gaspo.api.model.esus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
// IMPORTANTE: Ajuste o import abaixo para o pacote correto onde está o seu UsuarioModel
import com.gaspo.api.model.gaspo.UsuarioModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "tb_cidadao", schema = "public")
@Data
@EqualsAndHashCode(callSuper = true) // Necessário no Lombok ao usar herança
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@AttributeOverrides({
        // Sobrescreve as colunas da classe pai (UsuarioModel) para baterem com a tabela do e-SUS
        @AttributeOverride(name = "id", column = @Column(name = "co_seq_cidadao")),
        @AttributeOverride(name = "nome", column = @Column(name = "no_cidadao"))
        // Se o UsuarioModel já tiver o campo 'cpf', você também deve sobrescrevê-lo aqui:
        // @AttributeOverride(name = "cpf", column = @Column(name = "nu_cpf"))
})
public class PacienteModel extends UsuarioModel {

    // Removidos os atributos "id" e "nome", pois agora são herdados de UsuarioModel

    @Column(name = "nu_cpf", unique = true) // Remova daqui se o CPF já existir na classe UsuarioModel
    private String cpf;

    @Column(name = "nu_cns")
    private String cns;

    @Column(name = "dt_nascimento")
    private LocalDate dataNascimento;
}