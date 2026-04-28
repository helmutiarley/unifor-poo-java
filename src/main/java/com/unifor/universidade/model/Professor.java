package com.unifor.universidade.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PROFESSOR")
public class Professor extends Pessoa {

    @Column
    private String centro;

    public Professor() {}

    public Professor(String cpf, String nome, Integer idade, String centro) {
        super(cpf, nome, idade);
        this.centro = centro;
    }

    /** Retorna uma string descrevendo a aula sendo dada. */
    public String darAula() {
        return "Professor " + getNome() + " está dando aula no centro " + centro + ".";
    }

    public String getCentro() { return centro; }
    public void setCentro(String centro) { this.centro = centro; }
}
