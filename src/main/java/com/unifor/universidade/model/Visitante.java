package com.unifor.universidade.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("VISITANTE")
public class Visitante extends Pessoa {

    public Visitante() {}

    public Visitante(String cpf, String nome, Integer idade) {
        super(cpf, nome, idade);
    }
}
