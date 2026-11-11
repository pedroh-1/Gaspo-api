package com.gaspo.api.service;

import com.gaspo.api.model.gaspo.FuncionarioModel;
import com.gaspo.api.model.gaspo.UsuarioModel;
import com.gaspo.api.repository.FuncionarioRepository;
import com.gaspo.api.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class LoginServiceTest {

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();
    private UsuarioRepository usuarios;
    private FuncionarioRepository funcionarios;
    private LoginService service;

    @BeforeEach
    void setUp() {
        usuarios = mock(UsuarioRepository.class);
        funcionarios = mock(FuncionarioRepository.class);
        when(usuarios.findByCpf(anyString())).thenReturn(Optional.empty());
        when(funcionarios.findByCpf(anyString())).thenReturn(Optional.empty());
        service = new LoginService(usuarios, funcionarios, encoder);
    }

    private UsuarioModel paciente(String cpf) {
        UsuarioModel u = new UsuarioModel();
        u.setCpf(cpf);
        u.setEmail("paciente@gaspo.com");
        u.setSenha(encoder.encode("segredo"));
        return u;
    }

    @Test
    void pacienteEntraComCpfComOuSemMascara() {
        UsuarioModel u = paciente("52998224725");
        when(usuarios.findByCpf("52998224725")).thenReturn(Optional.of(u));

        assertSame(u, service.autenticar("529.982.247-25", "segredo", LoginService.TipoAcesso.USUARIO));
        assertSame(u, service.autenticar("52998224725", "segredo", LoginService.TipoAcesso.USUARIO));
    }

    @Test
    void encontraCpfAntigoGravadoComMascara() {
        UsuarioModel u = paciente("529.982.247-25");
        when(usuarios.findByCpf("529.982.247-25")).thenReturn(Optional.of(u));

        assertSame(u, service.autenticar("52998224725", "segredo", LoginService.TipoAcesso.USUARIO));
    }

    @Test
    void senhaErradaOuCpfDesconhecidoSaoRecusados() {
        when(usuarios.findByCpf("52998224725")).thenReturn(Optional.of(paciente("52998224725")));

        assertThrows(BadCredentialsException.class,
                () -> service.autenticar("52998224725", "errada", LoginService.TipoAcesso.USUARIO));
        assertThrows(BadCredentialsException.class,
                () -> service.autenticar("11144477735", "segredo", LoginService.TipoAcesso.USUARIO));
        assertThrows(BadCredentialsException.class,
                () -> service.autenticar("123", "segredo", LoginService.TipoAcesso.USUARIO));
    }

    @Test
    void cadaTipoDeAcessoProcuraSoASuaTabela() {
        // Mesmo CPF como paciente e como funcionário.
        when(usuarios.findByCpf("52998224725")).thenReturn(Optional.of(paciente("52998224725")));
        FuncionarioModel f = new FuncionarioModel();
        f.setCpf("52998224725");
        f.setEmail("func@gaspo.com");
        f.setSenha(encoder.encode("outra"));
        when(funcionarios.findByCpf("52998224725")).thenReturn(Optional.of(f));

        assertSame(f, service.autenticar("52998224725", "outra", LoginService.TipoAcesso.FUNCIONARIO));
        // A senha do paciente não serve para o acesso de funcionário.
        assertThrows(BadCredentialsException.class,
                () -> service.autenticar("52998224725", "segredo", LoginService.TipoAcesso.FUNCIONARIO));
    }
}
