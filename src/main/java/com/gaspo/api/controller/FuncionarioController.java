package com.gaspo.api.controller;

import com.gaspo.api.dto.request.FuncionarioCadastroDTO;
import com.gaspo.api.dto.request.LoginRequestDTO;
import com.gaspo.api.dto.response.FuncionarioResponseDTO;
import com.gaspo.api.dto.response.LoginResponseDTO;
import com.gaspo.api.security.TokenService;
import com.gaspo.api.service.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public FuncionarioController(FuncionarioService funcionarioService,
                                 AuthenticationManager authenticationManager,
                                 TokenService tokenService) {
        this.funcionarioService = funcionarioService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((UserDetails) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
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
