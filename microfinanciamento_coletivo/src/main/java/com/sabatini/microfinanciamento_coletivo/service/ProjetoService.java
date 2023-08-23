package com.sabatini.microfinanciamento_coletivo.service;

import com.sabatini.microfinanciamento_coletivo.model.Projeto;
import com.sabatini.microfinanciamento_coletivo.repository.ProjetoRepository;
import com.sabatini.microfinanciamento_coletivo.service.exceptions.ObjectNotFoundException;
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

    public Projeto getProjetoId(Long id) {
        Optional<Projeto> projeto = this.projetoRepository.findById(id);
        return projeto.orElseThrow(() -> new ObjectNotFoundException("Projeto não encontrado."));
    }

//    public Projeto getProjetoNome(String nomeProj){
//        Optional<Projeto> projeto = this.projetoRepository.findAllByNomeProj(nomeProj);
//        return projeto.orElseThrow(() -> new ObjectNotFoundException("Projeto não encontrado."));
//    }

    public void cadastrarProjeto(Projeto projeto) {
        projetoRepository.save(projeto);
    }


    // REFATORAR: acrescentar os SETs para o parâmetro Projeto projeto
    public Projeto atualizarProjeto(Long id, Projeto projeto) {

        Projeto atualizarProjeto = projetoRepository
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Projeto não encontrado."));

        projetoRepository.save(atualizarProjeto);
        return atualizarProjeto;

    }

    public void excluirProjeto(Long id) {

        Projeto projeto = projetoRepository.findById(id).get();

        if(projeto.isStatusFinalizado()){
            if (projetoRepository.findById(id).isPresent()) {
                projetoRepository.deleteById(id);
            } else {
                projetoRepository.findById(id)
                        .orElseThrow(() -> new ObjectNotFoundException("Projeto não encontrado"));
            }
        } else{

            // REFATORAR: não é Not_Found, é Conflict
            projetoRepository.findById(id)
                    .orElseThrow(() -> new ObjectNotFoundException("Finalize o projeto antes de excluí-lo."));
        }

    }
}
