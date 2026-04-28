package com.unifor.universidade.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;

@Entity
@Table(name = "pessoas")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_pessoa", discriminatorType = DiscriminatorType.STRING)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "tipo")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Visitante.class, name = "VISITANTE"),
        @JsonSubTypes.Type(value = Professor.class, name = "PROFESSOR"),
        @JsonSubTypes.Type(value = Bolsista.class, name = "BOLSISTA"),
        @JsonSubTypes.Type(value = Regular.class, name = "REGULAR")
})
public abstract class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 14)
    private String cpf;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Integer idade;

    public Pessoa() {}

    public Pessoa(String cpf, String nome, Integer idade) {
        this.cpf = cpf;
        this.nome = nome;
        this.idade = idade;
    }

    /** Incrementa a idade da pessoa em 1 ano. */
    public void fazerAniversario() {
        this.idade++;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Integer getIdade() { return idade; }
    public void setIdade(Integer idade) { this.idade = idade; }
}
