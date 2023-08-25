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


    public Projeto atualizarProjeto(Long id, Projeto projeto) {

        Projeto atualizarProj = projetoRepository
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Projeto não encontrado."));


        if((projeto.getNomeProj() != null) || !projeto.getNomeProj().isEmpty())
            atualizarProj.setNomeProj(projeto.getNomeProj());

        if((projeto.getDescricaoProj() != null) && !projeto.getDescricaoProj().isEmpty())
            atualizarProj.setDescricaoProj(projeto.getDescricaoProj());

        if((projeto.getArea() != null) && !projeto.getArea().isEmpty())
            atualizarProj.setArea(projeto.getArea());

        if((projeto.getObjetivo() != null) && !projeto.getObjetivo().isEmpty())
            atualizarProj.setObjetivo(projeto.getObjetivo());

        // O usuário poderá alterar o valor final??????????
        if(projeto.getValorFinal() != null)
            atualizarProj.setValorFinal(projeto.getValorFinal());

        if(projeto.isStatusFinalizado())
            atualizarProj.setStatusFinalizado(projeto.isStatusFinalizado());


        projetoRepository.save(atualizarProj);
        return atualizarProj;
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
        } else {
            // REFATORAR: não é Not_Found, é Conflict
            // PAROOOU DE FUNCIONAR
            projetoRepository.findById(id)
                    .orElseThrow(() -> new ObjectNotFoundException("Finalize o projeto antes de excluí-lo."));
        }
    }
}
