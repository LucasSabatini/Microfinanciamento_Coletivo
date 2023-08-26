package com.sabatini.microfinanciamento_coletivo.controller;

import com.sabatini.microfinanciamento_coletivo.model.Projeto;
import com.sabatini.microfinanciamento_coletivo.service.ProjetoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(path ="/projeto")
public class ProjetoController {


    private final ProjetoService projetoService;

    // Métodos
    @GetMapping(path = "/{idProjeto}")
    public ResponseEntity<Projeto> consultarProjetoPorId(@PathVariable Long idProjeto){
        return ResponseEntity.ok().body(projetoService.getProjetoId(idProjeto));
    }

    @PostMapping(path = "/cadastro")
    public @ResponseBody ResponseEntity<Void> cadastrarProjeto(@RequestBody Projeto projeto){
        projetoService.cadastrarProjeto(projeto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(projeto.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Projeto> atualizarProjetoTotalmente(@PathVariable Long id, @RequestBody Projeto projeto){

        return ResponseEntity.ok(projetoService.atualizarProjeto(id, projeto));
    }
    

    @DeleteMapping(path = "/{id}")
    public void excluirProjeto(@PathVariable("id") Long id){
        projetoService.excluirProjeto(id);
    }


    // Construtors
    public ProjetoController(ProjetoService projetoService) {
        this.projetoService = projetoService;
    }
}
