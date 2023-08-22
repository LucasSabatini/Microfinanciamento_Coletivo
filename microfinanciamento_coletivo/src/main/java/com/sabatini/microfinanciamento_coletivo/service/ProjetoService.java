package com.sabatini.microfinanciamento_coletivo.service;

import com.sabatini.microfinanciamento_coletivo.model.Projeto;
import com.sabatini.microfinanciamento_coletivo.repository.ProjetoRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjetoService {

    ProjetoRepository projetoRepository;

    // Construtor
    public ProjetoService(ProjetoRepository projetoRepository) {
        this.projetoRepository = projetoRepository;
    }

    // Métodos

    public Projeto getProjeto(Long id) {
        return projetoRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
    }

    public void cadastrarProjeto(Projeto projeto) {
        projetoRepository.save(projeto);
    }


    public Projeto atualizarProjeto(Long id, Projeto projeto) {

        Projeto atualizarProjeto = projetoRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));

        if (projeto.getNomeProj() != null)
            atualizarProjeto.setNomeProj(projeto.getNomeProj());

        if (projeto.getDescricaoProj() != null)
            atualizarProjeto.setDescricaoProj(projeto.getDescricaoProj());

        if (projeto.getArea() != null)
            atualizarProjeto.setArea(projeto.getArea());

        if (projeto.getObjetivo() != null)
            atualizarProjeto.setObjetivo(projeto.getObjetivo());

        if (projeto.getValorFinal() != null)
            atualizarProjeto.setValorFinal(projeto.getValorFinal());

        if (projeto.getValorAtual() != null)
            atualizarProjeto.setValorAtual(projeto.getValorAtual());

        projetoRepository.save(atualizarProjeto);
        return atualizarProjeto;

    }

    public void excluirProjeto(Long id) {
        Projeto excluirProjeto = projetoRepository.findById(id).orElseThrow(() -> new RuntimeException("Não existe o projeto."));
    }
}
