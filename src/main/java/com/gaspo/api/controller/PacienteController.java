package com.gaspo.api.controller;

import com.gaspo.api.dto.request.UsuarioCadastroDTO;
import com.gaspo.api.model.esus.PacienteModel;
import com.gaspo.api.model.gaspo.UsuarioModel;
import com.gaspo.api.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService service; // Agora usamos o Service

    @GetMapping
    public List<PacienteModel> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/cadastrados")
    public List<UsuarioModel> listarUsuariosGaspo() {
        return service.listarUsuariosGaspo();
    }

    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioModel> cadastrar(@RequestBody UsuarioCadastroDTO dto) {
        UsuarioModel novoUsuario = service.cadastrarUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }
}