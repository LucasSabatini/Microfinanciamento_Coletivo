package com.sabatini.microfinanciamento_coletivo.service;

import com.sabatini.microfinanciamento_coletivo.model.Projeto;
import com.sabatini.microfinanciamento_coletivo.model.User;
import com.sabatini.microfinanciamento_coletivo.repository.ProjetoRepository;
import com.sabatini.microfinanciamento_coletivo.service.exceptions.DataBindingViolationException;
import com.sabatini.microfinanciamento_coletivo.service.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
public class ProjetoServiceTest {

    @Mock
    ProjetoRepository projetoRepository;

    @InjectMocks
    ProjetoService projetoService;

    public static User user = new User();

    public static Projeto projTeste = new Projeto
            (1L,
                    "Nome",
                    "Descriao",
                    "Area",
                    "Objetivo",
                    new BigDecimal("100.0"),
                    new BigDecimal("0"),
                    false,
                    user);

    public static Optional<Projeto> projetoOptional = Optional.of(projTeste);

    public static Projeto projetoDTOTest = new Projeto();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    //FindByIdException
    @Test
    void deveLancarExceptionQuandoBuscarIdQueNaoExiste_Repository() {

        projTeste.setId(150L);

        when(projetoRepository.findById(projTeste.getId())).thenReturn(projetoOptional);

        assertThrows(ObjectNotFoundException.class, () -> projetoService.getProjetoId(100L));
    }

    // getProjetosAll
    @Test
    void deveRetornarUmaListaDeProjetos_GetProjetosAll() {

        Projeto proj1 = new Projeto();
        Projeto proj2 = new Projeto();
        Projeto proj3 = new Projeto();

        proj1.setId(10L);
        proj2.setId(50L);
        proj3.setNomeProj("Teste");

        when(projetoRepository.findAll()).thenReturn(List.of(proj1, proj2, proj3));

        List<Projeto> response = projetoService.getProjetosAll();

        assertNotNull(response);
        assertEquals(3, response.size());
        assertEquals(Projeto.class, response.get(0).getClass());
        assertEquals(10L, response.get(0).getId());
        assertEquals(50L, response.get(1).getId());
        assertEquals("Teste", response.get(2).getNomeProj());
    }

    // GetProjetoId
    @Test
    void deveRetornarProjetoPorIdEncontradoComSucesso_GetProjetoId() {

        when(projetoRepository.findById(anyLong())).thenReturn(projetoOptional);

        Projeto response = projetoService.getProjetoId(projTeste.getId());

        assertNotNull(response);
        assertEquals(Projeto.class, response.getClass());
        assertEquals(projTeste.getId(), response.getId());
    }

    // GetProjetoIdException
    @Test
    void deveLancarExceptionQuandoNaoEncontrarProjetoPorId_GetProjetoID() {

        when(projetoRepository.findById(anyLong())).thenThrow(new ObjectNotFoundException("Projeto não encontrado."));

        try {
            projetoService.getProjetoId(projTeste.getId());
        } catch (Exception exception) {
            assertEquals(ObjectNotFoundException.class, exception.getClass());
            assertEquals("Projeto não encontrado.", exception.getMessage());
        }
    }

    // cadastrarProjeto
    @Test
    void deveCadastrarUmProjetoComSucesso_cadastrarProjeto() {

        projetoDTOTest.setId(1L);

        when(projetoRepository.save(any())).thenReturn(projTeste);

        Projeto response = projetoService.cadastrarProjeto(projetoDTOTest);

        assertNotNull(response);
        assertEquals(Projeto.class, response.getClass());
        assertEquals(projTeste.getId(), response.getId());
    }

    // atualizarProjeto
    @Test
    void deveAtualizarUmProjetoComSucesso_atualizarProjeto() {

        Projeto projTemp = new Projeto();
        projTemp.setStatusFinalizado(false);
        projTemp.setId(10L);
        projTemp.setNomeProj("Teste");

        Optional<Projeto> projTempOptional = Optional.of(projTemp);

        when(projetoRepository.findById(anyLong())).thenReturn(projTempOptional);
        Projeto response = projetoService.getProjetoId(projTemp.getId());

        assertNotNull(response);

        when(projetoRepository.save(any())).thenReturn(projTemp);
        response.setStatusFinalizado(false);
        response = projetoService.atualizarProjeto(10L, projTemp);

        assertEquals(Projeto.class, response.getClass());
        assertEquals(10, projTemp.getId());
        assertEquals("Teste", projTemp.getNomeProj());
        assertFalse(projTemp.isStatusFinalizado());
        assertFalse(response.isStatusFinalizado());
    }

    // atualizarProjeto_Condicionais
    @Test
    void deveIgnorarSetSeCampoForNullOuVazioAoAtualizarProjeto_atualizarProjeto() {

        when(projetoRepository.findById(anyLong())).thenReturn(projetoOptional);

        Projeto response = projetoService.atualizarProjeto(projetoOptional.get().getId(), projTeste);

        assertNotNull(response.getNomeProj());
        assertNotNull(response.getDescricaoProj());
        assertNotNull(response.getArea());
        assertNotNull(response.getObjetivo());
        assertNotNull(response.getValorFinal());
    }

    // atualizarProjeto_Condicionais
    @Test
    void deveFinalizarProjetoApenasSeStatusForFalse_atualizarProjeto(){

        projetoOptional.get().setStatusFinalizado(false);
        when(projetoRepository.findById(anyLong())).thenReturn(projetoOptional);

        Boolean response = projetoService.getProjetoId(anyLong()).isStatusFinalizado();

        assertEquals(false, response);
    }

    // atualizarProjeto_Exception
    @Test()
    void deveLancarExceptionAoNaoEncontrarProjetoAntesDeAtualizar_atualizarProjeto() {

        when(projetoRepository.findById(anyLong())).thenThrow(new ObjectNotFoundException("Projeto não encontrado."));

        try {
            projetoService.atualizarProjeto(projTeste.getId(), projTeste);
        } catch (Exception exception) {
            assertEquals(ObjectNotFoundException.class, exception.getClass());
            assertEquals("Projeto não encontrado.", exception.getMessage());
        }
    }

    // atualizarProjeto_Exception
    @Test
    void deveLancarExceptionSeStatusFinalizadoForTrue_atualizarProjeto() {

        when(projetoRepository.findById(anyLong())).thenReturn(projetoOptional)
                .thenThrow(new DataBindingViolationException("Projeto finalizado! Seu status não pode ser alterado."));

        projTeste.setStatusFinalizado(true);

        var exception =
                assertThrows(DataBindingViolationException.class, () -> projetoService.atualizarProjeto(projTeste.getId(), projTeste));

        assertEquals(DataBindingViolationException.class, exception.getClass());
        assertEquals("Projeto finalizado! Seu status não pode ser alterado.", exception.getMessage());
    }

    // excluirProjeto
    @Test
    void deveExcluirUmProjetoComSucesso_excluirProjeto(){

        when(projetoRepository.findById(anyLong())).thenReturn(projetoOptional);

        doNothing().when(projetoRepository).deleteById(anyLong());

        projetoOptional.get().setStatusFinalizado(true);

        projetoService.excluirProjeto(projetoOptional.get().getId());

        assertEquals(true, projetoOptional.get().isStatusFinalizado());
    }

    // excluirProjeto
    @Test
    void deveLancarUmaExceptionAoExcluirUmProjetoSeStatusFinalizadoForFalse_excluirProjeto() {

        when(projetoRepository.findById(projTeste.getId())).thenReturn(projetoOptional);

        doNothing().when(projetoRepository).deleteById(projTeste.getId());

        projTeste.setStatusFinalizado(false);

        var exception =
                assertThrows(DataBindingViolationException.class, () -> projetoService.excluirProjeto(projTeste.getId()));

        assertEquals(DataBindingViolationException.class, exception.getClass());
        assertEquals("Finalize o projeto antes de excluí-lo.", exception.getMessage());

        verify(projetoRepository, never()).deleteById(anyLong());
    }
}
