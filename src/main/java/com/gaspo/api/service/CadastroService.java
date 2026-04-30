package com.gaspo.api.service;

import com.gaspo.api.dto.request.CadastroPacienteDTO;
import com.gaspo.api.model.esus.PacienteModel;
import com.gaspo.api.model.gaspo.UsuarioModel;
import com.gaspo.api.repository.esus.PacienteRepository;
import com.gaspo.api.repository.gaspo.UsuarioRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroService {

    private final PacienteRepository pacienteEsusRepository;
    private final UsuarioRepository usuarioGaspoRepository;
    private final CadastroService self; // Self-injection para garantir o uso do proxy @Transactional

    public CadastroService(PacienteRepository pacienteEsusRepository, 
                           UsuarioRepository usuarioGaspoRepository,
                           @Lazy CadastroService self) {
        this.pacienteEsusRepository = pacienteEsusRepository;
        this.usuarioGaspoRepository = usuarioGaspoRepository;
        this.self = self;
    }

    /**
     * Orquestração principal. 
     * Não é @Transactional aqui para evitar confusão entre os Managers.
     */
    public void cadastrarPacienteCompleto(CadastroPacienteDTO dto) {
        // 1. Salva no e-SUS primeiro (Passo crítico)
        // Se falhar aqui, lança exceção e para tudo.
        PacienteModel pacienteEsus = self.salvarNoEsus(dto);

        // 2. Salva no Gaspo usando o ID retornado
        self.salvarNoGaspo(dto, pacienteEsus.getId());
    }

    @Transactional("esusTransactionManager")
    public PacienteModel salvarNoEsus(CadastroPacienteDTO dto) {
        PacienteModel paciente = new PacienteModel();
        paciente.setNome(dto.getNome());
        paciente.setCpf(dto.getCpf());
        paciente.setCns(dto.getCns());
        paciente.setDataNascimento(dto.getDataNascimento());
        
        return pacienteEsusRepository.save(paciente);
    }

    @Transactional("gaspoTransactionManager")
    public void salvarNoGaspo(CadastroPacienteDTO dto, Long esusId) {
        UsuarioModel usuario = new UsuarioModel();
        usuario.setId(esusId); // Sincronizando IDs
        usuario.setNome(dto.getNome());
        usuario.setCpf(dto.getCpf());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        
        usuarioGaspoRepository.save(usuario);
    }
}
