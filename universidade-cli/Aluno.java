/**
 * Questão 3 — Aluno herda de Pessoa.
 * Classe abstrata: pagarMensalidade() é definido nas subclasses Bolsista e Regular.
 */
public abstract class Aluno extends Pessoa {

    private String matricula;

    public Aluno(String cpf, String nome, int idade, String matricula) {
        super(cpf, nome, idade);
        this.matricula = matricula;
    }

    /** Cada tipo de aluno paga a mensalidade de forma diferente. */
    public abstract double pagarMensalidade();

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
}
