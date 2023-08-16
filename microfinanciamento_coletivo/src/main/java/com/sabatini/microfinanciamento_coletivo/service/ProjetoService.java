package com.sabatini.microfinanciamento_coletivo.service;

import com.sabatini.microfinanciamento_coletivo.model.Projeto;
import com.sabatini.microfinanciamento_coletivo.repository.ProjetoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjetoService {

    ProjetoRepository projetoRepository;

    // Construtor
    public ProjetoService(ProjetoRepository projetoRepository) {
        this.projetoRepository = projetoRepository;
    }

    // Métodos
    public Projeto getProjetoRepository(Long id) {
        return projetoRepository.findById(id).orElseThrow(() -> new RuntimeException("Id não encontrado."));
    }
}
