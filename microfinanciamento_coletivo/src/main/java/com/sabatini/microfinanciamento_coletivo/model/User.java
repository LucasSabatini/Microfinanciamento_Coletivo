package com.sabatini.microfinanciamento_coletivo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 60)
    private String email;

    @NotBlank
    @Size(min = 8, max = 60)
    private String senha;

    private String nome;

    private String sobrenome;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_endereco")
    private Endereco endereco;

    private String cpf;

    private String rg;

    private String celular;

    @OneToMany
    private List<Projeto> projetos;

    // Constructors
    public User() {

    }

    public User(Long id, String email, String senha, String nome, String sobrenome, Endereco endereco, String cpf, String rg, String celular, List<Projeto> projetos) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.endereco = endereco;
        this.cpf = cpf;
        this.rg = rg;
        this.celular = celular;
        this.projetos = projetos;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public List<Projeto> getProjetos() {
        return projetos;
    }

    public void setProjetos(List<Projeto> projetos) {
        this.projetos = projetos;
    }

    // Equals and Hashs

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
