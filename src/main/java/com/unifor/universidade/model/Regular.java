package com.unifor.universidade.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("REGULAR")
public class Regular extends Aluno {

    private static final double VALOR_MENSALIDADE = 1500.00;

    public Regular() {}

    public Regular(String cpf, String nome, Integer idade, String matricula) {
        super(cpf, nome, idade, matricula);
    }

    /** Aluno regular paga a mensalidade integral. */
    @Override
    public double pagarMensalidade() {
        return VALOR_MENSALIDADE;
    }
}
