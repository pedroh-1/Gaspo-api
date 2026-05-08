package com.gaspo.api.model.gaspo;

import com.gaspo.api.model.enums.StatusProfissional;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_profissional_status", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfissionalStatusModel {

    @Id
    @Column(name = "co_prof_esus")
    private Long idProfissionalEsus;

    @Enumerated(EnumType.STRING)
    @Column(name = "st_atendimento", nullable = false)
    private StatusProfissional status;
}
