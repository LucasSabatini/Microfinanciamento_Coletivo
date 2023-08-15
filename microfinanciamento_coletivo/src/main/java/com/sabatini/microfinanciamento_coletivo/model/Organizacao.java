package com.sabatini.microfinanciamento_coletivo.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "organizacao")
public class Organizacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String emailOrganizacao;
    private String nomeOrganizacao;

    @ManyToMany(mappedBy = "organizacoes")
    private List<User> users;

    @OneToMany
    private List<Projeto> projetos;

    private User user;

    @OneToOne
    private Endereco endereco;

    private String ramo;
    private String descricao;
    private Integer numIntegrantes;
    private String telefone;
    private String telefone2;
    private String whatsapp;
    private String site;
    private String facebook;
    private String linkedIn;

    public Organizacao() {
    }

    public Organizacao(String emailOrganizacao, String nomeOrganizacao, List<User> users, List<Projeto> projetos, Endereco endereco, String ramo, String descricao, Integer numIntegrantes, String telefone, String telefone2, String whatsapp, String site, String facebook, String linkedIn) {
        this.emailOrganizacao = emailOrganizacao;
        this.nomeOrganizacao = nomeOrganizacao;
        this.users = users;
        this.projetos = projetos;
        this.endereco = endereco;
        this.ramo = ramo;
        this.descricao = descricao;
        this.numIntegrantes = numIntegrantes;
        this.telefone = telefone;
        this.telefone2 = telefone2;
        this.whatsapp = whatsapp;
        this.site = site;
        this.facebook = facebook;
        this.linkedIn = linkedIn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailOrganizacao() {
        return emailOrganizacao;
    }

    public void setEmailOrganizacao(String emailOrganizacao) {
        this.emailOrganizacao = emailOrganizacao;
    }

    public String getNomeOrganizacao() {
        return nomeOrganizacao;
    }

    public void setNomeOrganizacao(String nomeOrganizacao) {
        this.nomeOrganizacao = nomeOrganizacao;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Projeto> getProjetos() {
        return projetos;
    }

    public void setProjetos(List<Projeto> projetos) {
        this.projetos = projetos;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getRamo() {
        return ramo;
    }

    public void setRamo(String ramo) {
        this.ramo = ramo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getNumIntegrantes() {
        return numIntegrantes;
    }

    public void setNumIntegrantes(Integer numIntegrantes) {
        this.numIntegrantes = numIntegrantes;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organizacao that = (Organizacao) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
