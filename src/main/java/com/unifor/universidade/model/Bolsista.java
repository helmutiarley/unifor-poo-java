package com.unifor.universidade.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("BOLSISTA")
public class Bolsista extends Aluno {

    public Bolsista() {}

    public Bolsista(String cpf, String nome, Integer idade, String matricula) {
        super(cpf, nome, idade, matricula);
    }

    /** Bolsistas têm desconto integral. */
    @Override
    public double pagarMensalidade() {
        return 0.00;
    }
}
