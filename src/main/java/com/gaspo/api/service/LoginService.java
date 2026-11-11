package com.gaspo.api.service;

import com.gaspo.api.model.gaspo.FuncionarioModel;
import com.gaspo.api.model.gaspo.UsuarioModel;
import com.gaspo.api.repository.FuncionarioRepository;
import com.gaspo.api.repository.UsuarioRepository;
import com.gaspo.api.util.CpfUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Login por CPF e senha. Cada tipo de acesso procura só a sua tabela, para que
 * uma pessoa que é paciente e funcionária entre com o perfil escolhido.
 */
@Service
public class LoginService {

    public enum TipoAcesso { USUARIO, FUNCIONARIO }

    private static final String CREDENCIAIS_INVALIDAS = "CPF ou senha inválidos.";

    private final UsuarioRepository usuarioRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginService(UsuarioRepository usuarioRepository,
                        FuncionarioRepository funcionarioRepository,
                        PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDetails autenticar(String cpf, String senha, TipoAcesso tipo) {
        String digitos = CpfUtils.normalizar(cpf);
        if (digitos.length() != 11 || senha == null || senha.isEmpty()) {
            throw new BadCredentialsException(CREDENCIAIS_INVALIDAS);
        }

        Optional<? extends UserDetails> encontrado = tipo == TipoAcesso.FUNCIONARIO
                ? buscarFuncionario(digitos)
                : buscarUsuario(digitos);

        UserDetails principal = encontrado
                .orElseThrow(() -> new BadCredentialsException(CREDENCIAIS_INVALIDAS));
        if (!passwordEncoder.matches(senha, principal.getPassword())) {
            throw new BadCredentialsException(CREDENCIAIS_INVALIDAS);
        }
        return principal;
    }

    // Registros antigos podem ter o CPF gravado com máscara.
    private Optional<UsuarioModel> buscarUsuario(String digitos) {
        return usuarioRepository.findByCpf(digitos)
                .or(() -> usuarioRepository.findByCpf(CpfUtils.formatar(digitos)));
    }

    private Optional<FuncionarioModel> buscarFuncionario(String digitos) {
        return funcionarioRepository.findByCpf(digitos)
                .or(() -> funcionarioRepository.findByCpf(CpfUtils.formatar(digitos)));
    }
}
