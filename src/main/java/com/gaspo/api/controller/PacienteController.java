package com.gaspo.api.controller;

import com.gaspo.api.model.PacienteModel;
//import com.gaspo.api.repository.PacienteRepository;
import com.gaspo.api.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
}