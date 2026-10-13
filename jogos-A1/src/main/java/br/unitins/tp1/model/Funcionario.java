package br.unitins.tp1.model;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Funcionario extends Pessoa {

    @Column(nullable = false, length = 30, unique = true)
    private String matricula;

    @Column(nullable = false, length = 60)
    private String cargo;

    @Column(precision = 10, scale = 2)
    private BigDecimal salario;

    public Funcionario() {
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }
}
