package com.sabatini.microfinanciamento_coletivo.repository;

import com.sabatini.microfinanciamento_coletivo.model.Projeto;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public interface ProjetoRepositoryTest extends JpaRepository<Projeto, Long> {
}
