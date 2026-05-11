package com.gaspo.api.aggregate;

import com.gaspo.api.model.esus.AgendaModel;
import com.gaspo.api.model.esus.ConsultaModel;
import com.gaspo.api.model.esus.LotacaoModel;
import com.gaspo.api.model.esus.ProntuarioModel;
import com.gaspo.api.model.gaspo.UsuarioModel;

public class ConsultaAggregate {

    private final ConsultaModel consulta;
    private final AgendaModel agenda;
    private final LotacaoModel lotacao;
    private final ProntuarioModel prontuario;
    private final UsuarioModel usuario;

    public ConsultaAggregate(ConsultaModel consulta,
                             AgendaModel agenda,
                             LotacaoModel lotacao,
                             ProntuarioModel prontuario,
                             UsuarioModel usuario) {
        this.consulta = consulta;
        this.agenda = agenda;
        this.lotacao = lotacao;
        this.prontuario = prontuario;
        this.usuario = usuario;
    }

    public ConsultaModel getConsulta() {
        return consulta;
    }

    public AgendaModel getAgenda() {
        return agenda;
    }

    public LotacaoModel getLotacao() {
        return lotacao;
    }

    public ProntuarioModel getProntuario() {
        return prontuario;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }
}
