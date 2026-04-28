/**
 * Questão 3 — Regular é uma especialização de Aluno.
 * Sobrescreve pagarMensalidade() retornando o valor da mensalidade integral.
 */
public class Regular extends Aluno {

    private static final double VALOR_MENSALIDADE = 1500.00;

    public Regular(String cpf, String nome, int idade, String matricula) {
        super(cpf, nome, idade, matricula);
    }

    @Override
    public double pagarMensalidade() {
        return VALOR_MENSALIDADE;
    }
}
