package com.gaspo.api.controller;

import com.gaspo.api.service.CidadaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controller responsável pela consulta de cidadãos no sistema e-SUS.
 */
@RestController
@RequestMapping("/api/cidadaos")
public class CidadaoController {

    private final CidadaoService cidadaoService;

    public CidadaoController(CidadaoService cidadaoService) {
        this.cidadaoService = cidadaoService;
    }

    /**
     Verifica se um cidadão existe no e-SUS pelo CPF.
       cpf CPF do cidadão
       status de existência no sistema
     */
    @GetMapping("/{cpf}")
    public ResponseEntity<?> buscarCidadao(@PathVariable String cpf) {

        boolean existe = cidadaoService.existeNoESUS(cpf);

        return ResponseEntity.ok(
                Map.of(
                        "cpf", cpf,
                        "existe", existe,
                        "sistema", "e-SUS"
                )
        );
    }
}