package com.sabatini.microfinanciamento_coletivo.service;

import com.sabatini.microfinanciamento_coletivo.model.Projeto;
import com.sabatini.microfinanciamento_coletivo.repository.ProjetoRepository;
import com.sabatini.microfinanciamento_coletivo.service.exceptions.DataBindingViolationException;
import com.sabatini.microfinanciamento_coletivo.service.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjetoService {

    ProjetoRepository projetoRepository;

    // Construtor
    public ProjetoService(ProjetoRepository projetoRepository) {
        this.projetoRepository = projetoRepository;
    }

    public List<Projeto> getProjetosAll() {
        return projetoRepository.findAll();
    }

    // Métodos
    public Projeto getProjetoId(Long id) {
        Optional<Projeto> projeto = this.projetoRepository.findById(id);
        return projeto.orElseThrow(() -> new ObjectNotFoundException("Projeto não encontrado."));
    }

    // INCLUIR método de busca por nome

    public Projeto cadastrarProjeto(Projeto projeto) {
        projetoRepository.save(projeto);
        return projeto;
    }

    public Projeto atualizarProjeto(Long id, Projeto projeto) {

        Projeto atualizarProj = projetoRepository
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Projeto não encontrado."));

        if (!atualizarProj.isStatusFinalizado()) {
            if ((projeto.getNomeProj() != null) || !projeto.getNomeProj().isEmpty())
                atualizarProj.setNomeProj(projeto.getNomeProj());

            if ((projeto.getDescricaoProj() != null) && !projeto.getDescricaoProj().isEmpty())
                atualizarProj.setDescricaoProj(projeto.getDescricaoProj());

            if ((projeto.getArea() != null) && !projeto.getArea().isEmpty())
                atualizarProj.setArea(projeto.getArea());

            if ((projeto.getObjetivo() != null) && !projeto.getObjetivo().isEmpty())
                atualizarProj.setObjetivo(projeto.getObjetivo());

            // O usuário poderá alterar o valor final??????????
            if (projeto.getValorFinal() != null)
                atualizarProj.setValorFinal(projeto.getValorFinal());

            if(projeto.getValorAtual() != null)
                atualizarProj.setValorAtual(projeto.getValorAtual());

            atualizarProj.setStatusFinalizado(projeto.isStatusFinalizado());

        } else {
            throw new DataBindingViolationException("Projeto finalizado! Seu status não pode ser alterado.");
        }

        projetoRepository.save(atualizarProj);
        return atualizarProj;
    }

    public void excluirProjeto(Long id) {

        Projeto projeto = projetoRepository
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Projeto não encontrado"));

        if (projeto.isStatusFinalizado()) {
            projetoRepository.delete(projeto);
        } else {
            throw new DataBindingViolationException("Finalize o projeto antes de excluí-lo.");
        }
    }
}
