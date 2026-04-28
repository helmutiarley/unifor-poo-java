/**
 * Questão 3 — Professor herda de Pessoa.
 */
public class Professor extends Pessoa {

    private String centro;

    public Professor(String cpf, String nome, int idade, String centro) {
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
