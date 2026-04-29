package com.gaspo.api.service;

import com.gaspo.api.repository.gaspo.AvisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvisoService {
    @Autowired
    private AvisoRepository repository;

    public List<AvisoService> listarTodos() { return repository.findAll(); }
    public AvisoService salvar(AvisoService aviso) { return repository.save(aviso); }
}