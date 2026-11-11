package com.gaspo.api.controller;

import com.gaspo.api.dto.request.FuncionarioCadastroDTO;
import com.gaspo.api.dto.request.LoginRequestDTO;
import com.gaspo.api.dto.response.FuncionarioResponseDTO;
import com.gaspo.api.dto.response.LoginResponseDTO;
import com.gaspo.api.security.TokenService;
import com.gaspo.api.service.FuncionarioService;
import com.gaspo.api.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;
    private final LoginService loginService;
    private final TokenService tokenService;

    public FuncionarioController(FuncionarioService funcionarioService,
                                 LoginService loginService,
                                 TokenService tokenService) {
        this.funcionarioService = funcionarioService;
        this.loginService = loginService;
        this.tokenService = tokenService;
    }

    /** Login do funcionário com CPF e senha. */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO data) {
        var principal = loginService.autenticar(data.cpf(), data.senha(), LoginService.TipoAcesso.FUNCIONARIO);
        return ResponseEntity.ok(new LoginResponseDTO(tokenService.generateToken(principal)));
    }

    @PostMapping("/cadastro")
    public ResponseEntity<FuncionarioResponseDTO> cadastrar(@Valid @RequestBody FuncionarioCadastroDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioService.cadastrar(dto));
    }

    @GetMapping
    public ResponseEntity<List<FuncionarioResponseDTO>> listarTodos() {
        return ResponseEntity.ok(funcionarioService.listarTodos());
    }
}
