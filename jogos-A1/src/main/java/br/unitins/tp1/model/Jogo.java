package br.unitins.tp1.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Jogo extends DefaultEntity {

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(length = 1000)
    private String descricao;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @Convert(converter = br.unitins.tp1.model.converterjpa.GeneroConverter.class)
    private Genero genero;

    // Associação (N:1 com Desenvolvedora)
    @ManyToOne
    @JoinColumn(name = "id_desenvolvedora")
    private Desenvolvedora desenvolvedora;

    // Agregação (N:N com Plataforma)
    @ManyToMany
    @JoinTable(
        name = "jogo_plataforma",
        joinColumns = @JoinColumn(name = "id_jogo"),
        inverseJoinColumns = @JoinColumn(name = "id_plataforma")
    )
    private List<Plataforma> plataformas = new ArrayList<>();

    // Composição (1:1 com RequisitoSistema)
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_requisito_sistema")
    private RequisitoSistema requisitoSistema;

    public Jogo() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Desenvolvedora getDesenvolvedora() {
        return desenvolvedora;
    }

    public void setDesenvolvedora(Desenvolvedora desenvolvedora) {
        this.desenvolvedora = desenvolvedora;
    }

    public List<Plataforma> getPlataformas() {
        return plataformas;
    }

    public void setPlataformas(List<Plataforma> plataformas) {
        this.plataformas = plataformas;
    }

    public RequisitoSistema getRequisitoSistema() {
        return requisitoSistema;
    }

    public void setRequisitoSistema(RequisitoSistema requisitoSistema) {
        this.requisitoSistema = requisitoSistema;
    }
}