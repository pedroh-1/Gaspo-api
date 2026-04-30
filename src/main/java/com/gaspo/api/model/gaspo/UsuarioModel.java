package com.gaspo.api.model.gaspo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_usuario", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioModel {

    @Id
    @Column(name = "co_seq_usuario")
    private Long id;

    @Column(name = "no_usuario")
    private String nome;

    @Column(name = "nu_cpf", unique = true)
    private String cpf;

    @Column(name = "ds_email", unique = true)
    private String email;

    @Column(name = "ds_senha")
    private String senha;
}