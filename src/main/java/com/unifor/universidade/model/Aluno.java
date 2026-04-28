package com.unifor.universidade.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public abstract class Aluno extends Pessoa {

    @Column(unique = true)
    private String matricula;

    public Aluno() {}

    public Aluno(String cpf, String nome, Integer idade, String matricula) {
        super(cpf, nome, idade);
        this.matricula = matricula;
    }

    /** Cada tipo de aluno paga a mensalidade de forma diferente. */
    public abstract double pagarMensalidade();

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
}
