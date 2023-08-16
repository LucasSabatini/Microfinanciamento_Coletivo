package com.sabatini.microfinanciamento_coletivo.controller;

import com.sabatini.microfinanciamento_coletivo.model.Projeto;
import com.sabatini.microfinanciamento_coletivo.service.ProjetoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path ="/projeto")
public class ProjetoController {


    private ProjetoService projetoService;

    // Métodos
    @GetMapping(path = "{idProjeto}")
    public ResponseEntity<Projeto> consultarProjetoPorId(@PathVariable Long idProjeto){
        return ResponseEntity.ok().body(projetoService.getProjetoRepository(idProjeto));
    }


    // Construtors

    public ProjetoController(ProjetoService projetoService) {
        this.projetoService = projetoService;
    }
}
