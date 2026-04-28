package com.unifor.universidade.config;

import com.unifor.universidade.model.*;
import com.unifor.universidade.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Popula o banco com o cenário da Questão 4 da atividade,
 * caso o banco esteja vazio:
 *  - 3 disciplinas, 1 professor, 2 alunos regulares + 1 bolsista, 2 visitantes, 3 turmas.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private final DisciplinaRepository disciplinaRepo;
    private final ProfessorRepository professorRepo;
    private final AlunoRepository alunoRepo;
    private final VisitanteRepository visitanteRepo;
    private final TurmaRepository turmaRepo;

    public DataSeeder(DisciplinaRepository disciplinaRepo,
                      ProfessorRepository professorRepo,
                      AlunoRepository alunoRepo,
                      VisitanteRepository visitanteRepo,
                      TurmaRepository turmaRepo) {
        this.disciplinaRepo = disciplinaRepo;
        this.professorRepo = professorRepo;
        this.alunoRepo = alunoRepo;
        this.visitanteRepo = visitanteRepo;
        this.turmaRepo = turmaRepo;
    }

    @Override
    public void run(String... args) {
        if (turmaRepo.count() > 0) {
            log.info("Banco já populado — pulando seed.");
            return;
        }
        log.info("Populando banco com o cenário da Questão 4...");

        // --- 3 Disciplinas ---
        Disciplina poo = disciplinaRepo.save(new Disciplina("N685", "Programação Orientada a Objetos", 3));
        Disciplina algoritmos = disciplinaRepo.save(new Disciplina("N420", "Algoritmos e Estruturas de Dados", 2));
        Disciplina banco = disciplinaRepo.save(new Disciplina("N780", "Banco de Dados", 4));

        // --- 1 Professor ---
        Professor prof = professorRepo.save(new Professor("111.111.111-11", "Carlos Souza", 45, "CCT"));

        // --- 3 Alunos: 2 regulares + 1 bolsista ---
        Regular ana = alunoRepo.save(new Regular("222.222.222-22", "Ana Lima", 20, "2026A001"));
        Regular joao = alunoRepo.save(new Regular("333.333.333-33", "João Pereira", 22, "2026A002"));
        Bolsista maria = alunoRepo.save(new Bolsista("444.444.444-44", "Maria Costa", 19, "2026B001"));

        // --- 2 Visitantes ---
        visitanteRepo.save(new Visitante("555.555.555-55", "Pedro Visitante", 30));
        visitanteRepo.save(new Visitante("666.666.666-66", "Lucia Visitante", 28));

        // --- 3 Turmas ---
        Turma t1 = new Turma("T-POO-2026.1", poo, prof);
        t1.adicionarAluno(ana);
        t1.adicionarAluno(maria);
        turmaRepo.save(t1);

        Turma t2 = new Turma("T-ALG-2026.1", algoritmos, prof);
        t2.adicionarAluno(joao);
        turmaRepo.save(t2);

        Turma t3 = new Turma("T-BD-2026.1", banco, prof);
        t3.adicionarAluno(ana);
        t3.adicionarAluno(joao);
        t3.adicionarAluno(maria);
        turmaRepo.save(t3);

        log.info("Seed concluído: {} disciplinas, {} professores, {} alunos, {} visitantes, {} turmas.",
                disciplinaRepo.count(), professorRepo.count(), alunoRepo.count(),
                visitanteRepo.count(), turmaRepo.count());

        imprimirTurmas(List.of(t1, t2, t3));
    }

    /** Imprime os dados de cada turma — equivalente ao output da Questão 4. */
    private void imprimirTurmas(List<Turma> turmas) {
        log.info("=== TURMAS CADASTRADAS ===");
        for (Turma t : turmas) {
            log.info("Turma {} | Disciplina: {} ({}) | Professor: {} ({})",
                    t.getCodigo(),
                    t.getDisciplina().getNome(),
                    t.getDisciplina().getCodigo(),
                    t.getProfessor().getNome(),
                    t.getProfessor().getCentro());
            for (Aluno a : t.listarAlunos()) {
                log.info("  - {} ({}): matrícula {} | mensalidade R$ {}",
                        a.getNome(), a.getClass().getSimpleName(),
                        a.getMatricula(), a.pagarMensalidade());
            }
        }
    }
}
