package com.gaspo.api.controller;

import com.gaspo.api.dto.request.AvisoRequestDTO;
import com.gaspo.api.dto.response.AvisoResponseDTO;
import com.gaspo.api.mapper.AvisoMapper;
import com.gaspo.api.service.AvisoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/avisos")
public class AvisoController {

    @Autowired
    private AvisoService service;

    @Autowired
    private AvisoMapper mapper;

    @GetMapping
    public List<AvisoResponseDTO> listarAvisos() {
        return service.listarTodos().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @PostMapping("/publicar")
    public AvisoResponseDTO publicarAviso(@Valid @RequestBody AvisoRequestDTO aviso) {
        return mapper.toResponseDTO(service.publicar(mapper.toModel(aviso)));
    }

    @PutMapping("/{id}")
    public AvisoResponseDTO editarAviso(@PathVariable Long id, @Valid @RequestBody AvisoRequestDTO aviso) {
        return mapper.toResponseDTO(service.editar(id, mapper.toModel(aviso)));
    }

    @DeleteMapping("/{id}")
    public void removerAviso(@PathVariable Long id) {
        service.remover(id);
    }
}
