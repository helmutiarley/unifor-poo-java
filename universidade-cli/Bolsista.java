/**
 * Questão 3 — Bolsista é uma especialização de Aluno.
 * Sobrescreve pagarMensalidade() retornando 0 (bolsista não paga).
 */
public class Bolsista extends Aluno {

    public Bolsista(String cpf, String nome, int idade, String matricula) {
        super(cpf, nome, idade, matricula);
    }

    @Override
    public double pagarMensalidade() {
        return 0.00;
    }
}
