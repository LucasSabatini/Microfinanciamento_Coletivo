package com.sabatini.microfinanciamento_coletivo.repository;

import com.sabatini.microfinanciamento_coletivo.model.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

    String findAllByNomeProj(String nomeProj);

}
