package com.unifor.universidade.model;

import jakarta.persistence.*;

@Entity
@Table(name = "disciplinas")
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String codigo;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Integer semestre;

    public Disciplina() {}

    public Disciplina(String codigo, String nome, Integer semestre) {
        this.codigo = codigo;
        this.nome = nome;
        this.semestre = semestre;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Integer getSemestre() { return semestre; }
    public void setSemestre(Integer semestre) { this.semestre = semestre; }
}
