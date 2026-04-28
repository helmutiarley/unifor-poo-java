package com.unifor.universidade.service;

import com.unifor.universidade.dto.CreateTurmaRequest;
import com.unifor.universidade.exception.NotFoundException;
import com.unifor.universidade.model.Aluno;
import com.unifor.universidade.model.Turma;
import com.unifor.universidade.repository.TurmaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurmaService {

    private final TurmaRepository repository;
    private final DisciplinaService disciplinaService;
    private final ProfessorService professorService;
    private final AlunoService alunoService;

    public TurmaService(TurmaRepository repository,
                        DisciplinaService disciplinaService,
                        ProfessorService professorService,
                        AlunoService alunoService) {
        this.repository = repository;
        this.disciplinaService = disciplinaService;
        this.professorService = professorService;
        this.alunoService = alunoService;
    }

    public Turma criar(CreateTurmaRequest req) {
        Turma turma = new Turma(
                req.codigo(),
                disciplinaService.buscarPorId(req.disciplinaId()),
                professorService.buscarPorId(req.professorId())
        );
        return repository.save(turma);
    }

    public List<Turma> listar() {
        return repository.findAll();
    }

    public Turma buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Turma com id " + id + " não encontrada."));
    }

    public Turma adicionarAluno(Long turmaId, Long alunoId) {
        Turma turma = buscarPorId(turmaId);
        Aluno aluno = alunoService.buscarPorId(alunoId);
        turma.adicionarAluno(aluno);
        return repository.save(turma);
    }

    public Turma removerAluno(Long turmaId, Long alunoId) {
        Turma turma = buscarPorId(turmaId);
        Aluno aluno = alunoService.buscarPorId(alunoId);
        turma.removerAluno(aluno);
        return repository.save(turma);
    }

    public List<Aluno> listarAlunos(Long turmaId) {
        return buscarPorId(turmaId).listarAlunos();
    }
}
