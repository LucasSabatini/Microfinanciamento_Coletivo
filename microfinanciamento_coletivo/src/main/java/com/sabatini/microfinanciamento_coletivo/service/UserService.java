package com.sabatini.microfinanciamento_coletivo.service;

import com.sabatini.microfinanciamento_coletivo.exceptions.GlobalExceptionHandler;
import com.sabatini.microfinanciamento_coletivo.model.User;
import com.sabatini.microfinanciamento_coletivo.repository.UserRepository;
import com.sabatini.microfinanciamento_coletivo.service.exceptions.UserJaCadastradoException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void createUser(User user) {
        if(userRepository.existsUserByEmail(user.getEmail())) {
            throw new UserJaCadastradoException("Este email já está cadastrado");
        }
        user.setId(null);
        userRepository.save(user);
    }

    public void updateUser(Long id, User user) {
        User userToUpdate = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Id não encontrado!"));
        if(user.getEmail() != null && !user.getEmail().isEmpty()) userToUpdate.setEmail(user.getEmail());
        if(user.getSenha() != null && !user.getSenha().isEmpty()) userToUpdate.setSenha(user.getSenha());
        if(user.getNome() != null && !user.getNome().isEmpty()) userToUpdate.setNome(user.getNome());
        if(user.getSobrenome() != null && !user.getSobrenome().isEmpty()) userToUpdate.setSobrenome(user.getSobrenome());
        if(user.getEndereco() != null) userToUpdate.setEndereco(user.getEndereco());
        if(user.getCpf() != null && !user.getCpf().isEmpty()) userToUpdate.setCpf(user.getCpf());
        if(user.getRg() != null && !user.getRg().isEmpty()) userToUpdate.setRg(user.getRg());
        if(user.getCelular() != null && !user.getCelular().isEmpty()) userToUpdate.setCelular(user.getCelular());
        if(user.getProjetos() != null && !user.getProjetos().isEmpty()) userToUpdate.setProjetos(user.getProjetos());
        userRepository.save(userToUpdate);
    }

    public void deleteUser(Long id) {
        User userToDelete = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Id não encontrado!"));
        userRepository.delete(userToDelete);
    }
}
