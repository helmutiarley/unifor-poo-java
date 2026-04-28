import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

/**
 * Aplicação CLI interativa de Minha Universidade.
 *
 * Ao iniciar, popula o estado em memória com o cenário da Questão 4
 * (3 disciplinas, 1 professor, 3 alunos, 2 visitantes, 3 turmas) e
 * exibe um menu permitindo cadastrar, listar, matricular alunos,
 * calcular mensalidades, fazer pessoas envelhecerem etc.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final List<Visitante> visitantes = new ArrayList<>();
    private static final List<Aluno> alunos = new ArrayList<>();
    private static final List<Professor> professores = new ArrayList<>();
    private static final List<Disciplina> disciplinas = new ArrayList<>();
    private static final List<Turma> turmas = new ArrayList<>();

    public static void main(String[] args) {
        seedDados();

        System.out.println();
        System.out.println("==============================================");
        System.out.println("       MINHA UNIVERSIDADE — TERMINAL CLI      ");
        System.out.println("==============================================");
        System.out.println("Estado inicial populado com o cenário da Questão 4:");
        System.out.println("  " + disciplinas.size() + " disciplinas, "
                + professores.size() + " professor, "
                + alunos.size() + " alunos, "
                + visitantes.size() + " visitantes, "
                + turmas.size() + " turmas.");

        int opcao;
        do {
            mostrarMenu();
            opcao = lerInt("Escolha: ");
            try {
                executar(opcao);
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (opcao != 0);

        System.out.println("Encerrando.");
        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("------------------- MENU -------------------");
        System.out.println(" 1) Adicionar visitante");
        System.out.println(" 2) Adicionar aluno regular");
        System.out.println(" 3) Adicionar aluno bolsista");
        System.out.println(" 4) Adicionar professor");
        System.out.println(" 5) Adicionar disciplina");
        System.out.println(" 6) Criar turma");
        System.out.println(" 7) Matricular aluno em turma");
        System.out.println(" 8) Remover aluno de turma");
        System.out.println(" 9) Listar visitantes");
        System.out.println("10) Listar alunos");
        System.out.println("11) Listar professores");
        System.out.println("12) Listar disciplinas");
        System.out.println("13) Listar turmas (com detalhes)");
        System.out.println("14) Calcular mensalidade de um aluno");
        System.out.println("15) Professor dar aula");
        System.out.println("16) Incrementar idade (fazer aniversário)");
        System.out.println(" 0) Sair");
    }

    private static void executar(int opcao) {
        switch (opcao) {
            case 1 -> adicionarVisitante();
            case 2 -> adicionarAlunoRegular();
            case 3 -> adicionarAlunoBolsista();
            case 4 -> adicionarProfessor();
            case 5 -> adicionarDisciplina();
            case 6 -> criarTurma();
            case 7 -> matricularAluno();
            case 8 -> removerAluno();
            case 9 -> listarVisitantes();
            case 10 -> listarAlunos();
            case 11 -> listarProfessores();
            case 12 -> listarDisciplinas();
            case 13 -> listarTurmas();
            case 14 -> calcularMensalidade();
            case 15 -> professorDarAula();
            case 16 -> fazerAniversario();
            case 0 -> { /* sai */ }
            default -> System.out.println("Opção inválida.");
        }
    }

    // -------------------- Cadastros --------------------

    private static void adicionarVisitante() {
        Visitante v = new Visitante(lerString("CPF: "), lerString("Nome: "), lerInt("Idade: "));
        visitantes.add(v);
        System.out.println("Visitante adicionado.");
    }

    private static void adicionarAlunoRegular() {
        Regular r = new Regular(
                lerString("CPF: "),
                lerString("Nome: "),
                lerInt("Idade: "),
                lerString("Matrícula: "));
        alunos.add(r);
        System.out.println("Aluno regular adicionado.");
    }

    private static void adicionarAlunoBolsista() {
        Bolsista b = new Bolsista(
                lerString("CPF: "),
                lerString("Nome: "),
                lerInt("Idade: "),
                lerString("Matrícula: "));
        alunos.add(b);
        System.out.println("Aluno bolsista adicionado.");
    }

    private static void adicionarProfessor() {
        Professor p = new Professor(
                lerString("CPF: "),
                lerString("Nome: "),
                lerInt("Idade: "),
                lerString("Centro: "));
        professores.add(p);
        System.out.println("Professor adicionado.");
    }

    private static void adicionarDisciplina() {
        Disciplina d = new Disciplina(
                lerString("Código: "),
                lerString("Nome: "),
                lerInt("Semestre: "));
        disciplinas.add(d);
        System.out.println("Disciplina adicionada.");
    }

    private static void criarTurma() {
        if (disciplinas.isEmpty() || professores.isEmpty()) {
            System.out.println("É necessário ter ao menos uma disciplina e um professor cadastrados.");
            return;
        }
        String codigo = lerString("Código da turma: ");
        Disciplina d = selecionar(disciplinas, "Selecione a disciplina:", Main::descreverDisciplina);
        if (d == null) return;
        Professor p = selecionar(professores, "Selecione o professor:", Main::descreverProfessor);
        if (p == null) return;
        turmas.add(new Turma(codigo, d, p));
        System.out.println("Turma criada.");
    }

    // -------------------- Operações em turmas --------------------

    private static void matricularAluno() {
        if (turmas.isEmpty() || alunos.isEmpty()) {
            System.out.println("É necessário ter ao menos uma turma e um aluno cadastrados.");
            return;
        }
        Turma t = selecionar(turmas, "Selecione a turma:", Main::descreverTurmaResumo);
        if (t == null) return;
        Aluno a = selecionar(alunos, "Selecione o aluno:", Main::descreverAluno);
        if (a == null) return;
        t.adicionarAluno(a);
        System.out.println(a.getNome() + " matriculado(a) na turma " + t.getCodigo() + ".");
    }

    private static void removerAluno() {
        if (turmas.isEmpty()) {
            System.out.println("Nenhuma turma cadastrada.");
            return;
        }
        Turma t = selecionar(turmas, "Selecione a turma:", Main::descreverTurmaResumo);
        if (t == null) return;
        if (t.listarAlunos().isEmpty()) {
            System.out.println("Turma sem alunos matriculados.");
            return;
        }
        Aluno a = selecionar(t.listarAlunos(), "Selecione o aluno a remover:", Main::descreverAluno);
        if (a == null) return;
        t.removerAluno(a);
        System.out.println(a.getNome() + " removido(a) da turma " + t.getCodigo() + ".");
    }

    // -------------------- Listagens --------------------

    private static void listarVisitantes() {
        if (visitantes.isEmpty()) { System.out.println("Nenhum visitante."); return; }
        System.out.println("=== VISITANTES ===");
        for (int i = 0; i < visitantes.size(); i++) {
            Visitante v = visitantes.get(i);
            System.out.printf("[%d] %s (%d anos) — CPF %s%n", i + 1, v.getNome(), v.getIdade(), v.getCpf());
        }
    }

    private static void listarAlunos() {
        if (alunos.isEmpty()) { System.out.println("Nenhum aluno."); return; }
        System.out.println("=== ALUNOS ===");
        for (int i = 0; i < alunos.size(); i++) {
            System.out.printf("[%d] %s%n", i + 1, descreverAluno(alunos.get(i)));
        }
    }

    private static void listarProfessores() {
        if (professores.isEmpty()) { System.out.println("Nenhum professor."); return; }
        System.out.println("=== PROFESSORES ===");
        for (int i = 0; i < professores.size(); i++) {
            System.out.printf("[%d] %s%n", i + 1, descreverProfessor(professores.get(i)));
        }
    }

    private static void listarDisciplinas() {
        if (disciplinas.isEmpty()) { System.out.println("Nenhuma disciplina."); return; }
        System.out.println("=== DISCIPLINAS ===");
        for (int i = 0; i < disciplinas.size(); i++) {
            System.out.printf("[%d] %s%n", i + 1, descreverDisciplina(disciplinas.get(i)));
        }
    }

    private static void listarTurmas() {
        if (turmas.isEmpty()) { System.out.println("Nenhuma turma."); return; }
        System.out.println("=== TURMAS ===");
        for (Turma t : turmas) {
            System.out.println();
            System.out.println("Turma: " + t.getCodigo());
            System.out.println("  Disciplina: " + descreverDisciplina(t.getDisciplina()));
            System.out.println("  Professor:  " + descreverProfessor(t.getProfessor()));
            System.out.println("  Alunos (" + t.listarAlunos().size() + "):");
            for (Aluno a : t.listarAlunos()) {
                System.out.printf("    - %s | mensalidade R$ %.2f%n",
                        descreverAluno(a), a.pagarMensalidade());
            }
        }
    }

    // -------------------- Métodos das classes (POO) --------------------

    private static void calcularMensalidade() {
        if (alunos.isEmpty()) { System.out.println("Nenhum aluno cadastrado."); return; }
        Aluno a = selecionar(alunos, "Selecione o aluno:", Main::descreverAluno);
        if (a == null) return;
        System.out.printf("Mensalidade de %s (%s): R$ %.2f%n",
                a.getNome(), a.getClass().getSimpleName(), a.pagarMensalidade());
    }

    private static void professorDarAula() {
        if (professores.isEmpty()) { System.out.println("Nenhum professor cadastrado."); return; }
        Professor p = selecionar(professores, "Selecione o professor:", Main::descreverProfessor);
        if (p == null) return;
        System.out.println(p.darAula());
    }

    private static void fazerAniversario() {
        List<Pessoa> todas = todasAsPessoas();
        if (todas.isEmpty()) { System.out.println("Nenhuma pessoa cadastrada."); return; }
        Pessoa p = selecionar(todas, "Selecione a pessoa:", Main::descreverPessoa);
        if (p == null) return;
        int antes = p.getIdade();
        p.fazerAniversario();
        System.out.printf("%s passou de %d para %d anos.%n", p.getNome(), antes, p.getIdade());
    }

    private static List<Pessoa> todasAsPessoas() {
        List<Pessoa> todas = new ArrayList<>();
        todas.addAll(visitantes);
        todas.addAll(alunos);
        todas.addAll(professores);
        return todas;
    }

    // -------------------- Formatadores --------------------

    private static String descreverDisciplina(Disciplina d) {
        return String.format("%s — %s (%dº semestre)", d.getCodigo(), d.getNome(), d.getSemestre());
    }

    private static String descreverProfessor(Professor p) {
        return String.format("%s, %d anos — Centro %s", p.getNome(), p.getIdade(), p.getCentro());
    }

    private static String descreverAluno(Aluno a) {
        return String.format("%s — %s, matrícula %s, %d anos",
                a.getClass().getSimpleName(), a.getNome(), a.getMatricula(), a.getIdade());
    }

    private static String descreverPessoa(Pessoa p) {
        return String.format("%s — %s (%d anos)", p.getClass().getSimpleName(), p.getNome(), p.getIdade());
    }

    private static String descreverTurmaResumo(Turma t) {
        return String.format("%s — %s (%d aluno(s))",
                t.getCodigo(), t.getDisciplina().getNome(), t.listarAlunos().size());
    }

    // -------------------- I/O --------------------

    private static int lerInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String linha = scanner.nextLine().trim();
            try {
                return Integer.parseInt(linha);
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Digite um número inteiro.");
            }
        }
    }

    private static String lerString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static <T> T selecionar(List<T> lista, String prompt, Function<T, String> formatador) {
        System.out.println(prompt);
        for (int i = 0; i < lista.size(); i++) {
            System.out.printf("  [%d] %s%n", i + 1, formatador.apply(lista.get(i)));
        }
        int idx = lerInt("Número (0 para cancelar): ");
        if (idx == 0) return null;
        if (idx < 1 || idx > lista.size()) {
            System.out.println("Índice inválido.");
            return null;
        }
        return lista.get(idx - 1);
    }

    // -------------------- Seed inicial (cenário da Questão 4) --------------------

    private static void seedDados() {
        Disciplina poo = new Disciplina("N685", "Programação Orientada a Objetos", 3);
        Disciplina algoritmos = new Disciplina("N420", "Algoritmos e Estruturas de Dados", 2);
        Disciplina banco = new Disciplina("N780", "Banco de Dados", 4);
        disciplinas.add(poo);
        disciplinas.add(algoritmos);
        disciplinas.add(banco);

        Professor prof = new Professor("111.111.111-11", "Carlos Souza", 45, "CCT");
        professores.add(prof);

        Regular ana = new Regular("222.222.222-22", "Ana Lima", 20, "2026A001");
        Regular joao = new Regular("333.333.333-33", "João Pereira", 22, "2026A002");
        Bolsista maria = new Bolsista("444.444.444-44", "Maria Costa", 19, "2026B001");
        alunos.add(ana);
        alunos.add(joao);
        alunos.add(maria);

        visitantes.add(new Visitante("555.555.555-55", "Pedro Visitante", 30));
        visitantes.add(new Visitante("666.666.666-66", "Lucia Visitante", 28));

        Turma t1 = new Turma("T-POO-2026.1", poo, prof);
        t1.adicionarAluno(ana);
        t1.adicionarAluno(maria);

        Turma t2 = new Turma("T-ALG-2026.1", algoritmos, prof);
        t2.adicionarAluno(joao);

        Turma t3 = new Turma("T-BD-2026.1", banco, prof);
        t3.adicionarAluno(ana);
        t3.adicionarAluno(joao);
        t3.adicionarAluno(maria);

        turmas.add(t1);
        turmas.add(t2);
        turmas.add(t3);
    }
}
