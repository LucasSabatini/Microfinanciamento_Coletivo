package com.sabatini.microfinanciamento_coletivo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sabatini.microfinanciamento_coletivo.model.User;
import com.sabatini.microfinanciamento_coletivo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    MockMvc mockMvc;

    User user1 = new User(1L, "user1@teste.com", "123456789", "Teste1", "Unitario", null, "123.412.453-45", "1232312312", "1199898598", null);
    User user2 = new User(2L, "user2@teste.com", "123456789", "Teste2", "Unitario", null, "123.412.453-45", "1232312312", "1199898598", null);
    List<User> usersList = Arrays.asList(user1, user2);
    User updatedUser = new User();

    @Test
    void getUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(usersList);

        mockMvc.perform(get("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(usersList)))
                .andExpect(status().isOk());

        verify(userService).getAllUsers();
    }

    @Test
    void getUserById() throws Exception {
        when(userService.getUserById(1L)).thenReturn(user1);

        mockMvc.perform(get("/user/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.email").value("user1@teste.com"));

        verify(userService).getUserById(1L);
    }

    @Test
    void createUser() throws Exception {
        doNothing().when(userService).createUser(user1);

        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user1)))
                .andExpect(status().isCreated());

        verify(userService).createUser(user1);
    }

    @Test
    void updateUser() throws Exception {
        when(userService.updateUser(user1.getId(), updatedUser)).thenReturn(user1);

        mockMvc.perform(put("/user/{id}", user1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(userService).updateUser(user1.getId(), updatedUser);

    }

    @Test
    void deleteUser() throws Exception {
        doNothing().when(userService).deleteUser(user1.getId());

        mockMvc.perform(delete("/user/{id}", user1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userService).deleteUser(user1.getId());
    }
}