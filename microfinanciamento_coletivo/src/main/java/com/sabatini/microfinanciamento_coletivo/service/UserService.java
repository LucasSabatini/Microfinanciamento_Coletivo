package com.sabatini.microfinanciamento_coletivo.service;

import com.sabatini.microfinanciamento_coletivo.model.User;
import com.sabatini.microfinanciamento_coletivo.repository.UserRepository;
import com.sabatini.microfinanciamento_coletivo.service.exceptions.DataBindingViolationException;
import com.sabatini.microfinanciamento_coletivo.service.exceptions.UserNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void createUser(User user) {
        if(userRepository.existsUserByEmail(user.getEmail())) {
            throw new DataIntegrityViolationException("Este email já está cadastrado");
        }
        if(user.getSenha().length() < 8 || user.getSenha().length() > 60) {
            throw new DataBindingViolationException("Sua senha deve conter entre 8 e 60 dígitos.");
        }
        user.setId(null);
        userRepository.save(user);
    }

    public void updateUser(Long id, User user) {
        User userToUpdate = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado!"));
        if(user.getEmail() != null && !user.getEmail().isEmpty()) userToUpdate.setEmail(user.getEmail());
        if(user.getSenha().length() < 8 || user.getSenha().length() > 60) {
            throw new DataBindingViolationException("Sua senha deve conter entre 8 e 60 dígitos.");
        }
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
        User userToDelete = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado!"));
        userRepository.delete(userToDelete);
    }
}
