package com.sabatini.microfinanciamento_coletivo.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "projeto")
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeProj;
    private String descricaoProj;
    private String area;
    private String objetivo;
    private BigDecimal valorFinal;
    private BigDecimal valorAtual;

    @JoinColumn(name = "id_responsavel")
    @ManyToOne
    private User userResponsavel;

    @JoinColumn(name = "id_contribuintes")
    @ManyToMany
    private List<User> userContribuintes;

    public Projeto() {
    }

    public Projeto(Long id, String nomeProj, String descricaoProj, String area, String objetivo, BigDecimal valorFinal, BigDecimal valorAtual, User userResponsavel, List<User> userContribuintes) {
        this.id = id;
        this.nomeProj = nomeProj;
        this.descricaoProj = descricaoProj;
        this.area = area;
        this.objetivo = objetivo;
        this.valorFinal = valorFinal;
        this.valorAtual = valorAtual;
        this.userResponsavel = userResponsavel;
        this.userContribuintes = userContribuintes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeProj() {
        return nomeProj;
    }

    public void setNomeProj(String nomeProj) {
        this.nomeProj = nomeProj;
    }

    public String getDescricaoProj() {
        return descricaoProj;
    }

    public void setDescricaoProj(String descricaoProj) {
        this.descricaoProj = descricaoProj;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public BigDecimal getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(BigDecimal valorFinal) {
        this.valorFinal = valorFinal;
    }

    public BigDecimal getValorAtual() {
        return valorAtual;
    }

    public void setValorAtual(BigDecimal valorAtual) {
        this.valorAtual = valorAtual;
    }

    public User getUserResponsavel() {
        return userResponsavel;
    }

    public void setUserResponsavel(User user) {
        this.userResponsavel = user;
    }

    public List<User> getUserContribuintes() {
        return userContribuintes;
    }

    public void setUserContribuintes(List<User> userContribuintes) {
        this.userContribuintes = userContribuintes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Projeto projeto = (Projeto) o;
        return Objects.equals(id, projeto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
