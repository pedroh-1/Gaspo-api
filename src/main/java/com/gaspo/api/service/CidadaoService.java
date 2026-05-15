package com.gaspo.api.service;

import org.springframework.stereotype.Service;

@Service
public class CidadaoService {

    public boolean existeNoESUS(String cpf) {

        if (cpf == null || cpf.isBlank()) {
            throw new RuntimeException("CPF inválido");
        }

        // simulação
        return cpf.startsWith("1");
    }
}