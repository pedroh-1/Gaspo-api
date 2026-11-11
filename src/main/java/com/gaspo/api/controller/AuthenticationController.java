package com.gaspo.api.controller;

import com.gaspo.api.dto.request.LoginRequestDTO;
import com.gaspo.api.dto.response.LoginResponseDTO;
import com.gaspo.api.security.TokenService;
import com.gaspo.api.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pacientes")
public class AuthenticationController {

    private final LoginService loginService;
    private final TokenService tokenService;

    public AuthenticationController(LoginService loginService, TokenService tokenService) {
        this.loginService = loginService;
        this.tokenService = tokenService;
    }

    /** Login do paciente com CPF e senha. */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO data) {
        var principal = loginService.autenticar(data.cpf(), data.senha(), LoginService.TipoAcesso.USUARIO);
        return ResponseEntity.ok(new LoginResponseDTO(tokenService.generateToken(principal)));
    }
}
