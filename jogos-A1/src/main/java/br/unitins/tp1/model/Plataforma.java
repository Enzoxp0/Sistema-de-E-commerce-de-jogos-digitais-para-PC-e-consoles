package br.unitins.tp1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Plataforma extends DefaultEntity {

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(length = 100)
    private String fabricante;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }
}
