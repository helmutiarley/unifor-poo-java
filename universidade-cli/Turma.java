import java.util.ArrayList;
import java.util.List;

/**
 * Questão 4 — Turma faz associação com Disciplina e Professor (1 cada),
 * e composição com uma lista de Alunos.
 */
public class Turma {

    private String codigo;
    private Disciplina disciplina;
    private Professor professor;
    private List<Aluno> alunos = new ArrayList<>();

    public Turma(String codigo, Disciplina disciplina, Professor professor) {
        this.codigo = codigo;
        this.disciplina = disciplina;
        this.professor = professor;
    }

    public void adicionarAluno(Aluno aluno) {
        if (!alunos.contains(aluno)) alunos.add(aluno);
    }

    public void removerAluno(Aluno aluno) {
        alunos.remove(aluno);
    }

    public List<Aluno> listarAlunos() {
        return alunos;
    }

    public String getCodigo() { return codigo; }
    public Disciplina getDisciplina() { return disciplina; }
    public Professor getProfessor() { return professor; }
}
