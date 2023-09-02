package com.sabatini.microfinanciamento_coletivo.service;

import com.sabatini.microfinanciamento_coletivo.model.Endereco;
import com.sabatini.microfinanciamento_coletivo.model.Projeto;
import com.sabatini.microfinanciamento_coletivo.model.User;
import com.sabatini.microfinanciamento_coletivo.repository.UserRepository;
import com.sabatini.microfinanciamento_coletivo.service.exceptions.DataBindingViolationException;
import com.sabatini.microfinanciamento_coletivo.service.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    List<Projeto> projeto = new ArrayList<>();
    Endereco endereco = new Endereco();

    User user1 = new User(1L, "user1@teste.com", "123456789", "Teste1", "Unitario", endereco, "123.412.453-45", "1232312312", "1199898598", projeto);
    User user2 = new User(2L, "user2@teste.com", "123456789", "Teste2", "Unitario", endereco, "123.412.453-45", "1232312312", "1199898598", projeto);
    User user3 = new User(3L, "user3@teste.com", "123456789", "Teste3", "Unitario", endereco, "123.412.453-45", "1232312312", "1199898598", projeto);
    List<User> usersList = Arrays.asList(user1, user2, user3);
    User updatedUser = new User();

    @Test
    void getAllUsers() {
        when(userRepository.findAll()).thenReturn(usersList);

        List<User> getAllUsersList = userService.getAllUsers();

        assertEquals(user1, getAllUsersList.get(0));
        assertEquals(user2, getAllUsersList.get(1));
        verify(userRepository).findAll();
    }

    @Test
    void getUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));

        User wantedUser = userService.getUserById(1L);

        assertEquals(1L, wantedUser.getId());
        assertEquals("user1@teste.com", wantedUser.getEmail());
        verify(userRepository).findById(wantedUser.getId());
    }

    @Test
    void getUserById_UserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        var exception = assertThrows(UserNotFoundException.class, () -> userService.getUserById(anyLong()));

        assertEquals(UserNotFoundException.class, exception.getClass());
        verify(userRepository).findById(anyLong());
    }

    @Test
    void createUser() {
        when(userRepository.existsUserByEmail(anyString())).thenReturn(false);

        userService.createUser(user1);

        verify(userRepository).save(user1);
    }

    @Test
    void createUser_UserExistsByEmailException() {
        when(userRepository.existsUserByEmail(anyString())).thenReturn(true);

        var exception = assertThrows(DataIntegrityViolationException.class, () -> userService.createUser(user1));

        assertEquals(DataIntegrityViolationException.class, exception.getClass());
        verify(userRepository, never()).save(user1);
    }

    @Test
    void createUser_InvalidPassword() {
        user1.setSenha("1256");

        var exception = assertThrows(DataBindingViolationException.class, () -> userService.createUser(user1));

        assertEquals(DataBindingViolationException.class, exception.getClass());
        verify(userRepository, never()).save(user1);
    }

    @Test
    void updateUser() {
        updatedUser.setId(1L);
        updatedUser.setEmail("unitario@atualizado.com");
        updatedUser.setSenha("87654321");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user1));

        userService.updateUser(user1.getId(), updatedUser);

        assertEquals("unitario@atualizado.com", updatedUser.getEmail());
        assertEquals("87654321", updatedUser.getSenha());
        verify(userRepository).save(updatedUser);
    }

    @Test
    void updateUser_UserNotFound() {
        updatedUser.setEmail("userNotFound@naoAtualizado.com");
        updatedUser.setSenha("963852741");

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        var exception = assertThrows(UserNotFoundException.class, () -> userService.updateUser(anyLong(), updatedUser));

        assertEquals(UserNotFoundException.class, exception.getClass());
        verify(userRepository, never()).save(updatedUser);
    }

    @Test
    void updateUser_InvalidPassword() {
        updatedUser.setEmail("invalidPassword@naoAtualizado.com");
        updatedUser.setSenha("1234");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user1));

        var exception = assertThrows(DataBindingViolationException.class, () -> userService.updateUser(anyLong(), updatedUser));

        assertEquals(DataBindingViolationException.class, exception.getClass());
        verify(userRepository, never()).save(updatedUser);
    }

    @Test
    void deleteUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user1));

        userService.deleteUser(anyLong());

        verify(userRepository).delete(user1);
    }

    @Test
    void deleteUser_UserNotFound() {
        when(userRepository.findById(user1.getId())).thenReturn(Optional.empty());

        var exception = assertThrows(UserNotFoundException.class, () -> userService.deleteUser(user1.getId()));

        assertEquals(UserNotFoundException.class, exception.getClass());
        verify(userRepository, never()).delete(user1);
    }
}
