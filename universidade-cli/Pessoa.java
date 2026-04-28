/**
 * Questão 1 — Classe abstrata Pessoa.
 * Questão 2 — Encapsulamento + método fazerAniversario().
 */
public abstract class Pessoa {

    private String cpf;
    private String nome;
    private int idade;

    public Pessoa(String cpf, String nome, int idade) {
        this.cpf = cpf;
        this.nome = nome;
        this.idade = idade;
    }

    /** Incrementa a idade da pessoa em 1 ano. */
    public void fazerAniversario() {
        this.idade++;
    }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }
}
