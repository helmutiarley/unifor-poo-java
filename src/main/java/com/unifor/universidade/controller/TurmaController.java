package com.unifor.universidade.controller;

import com.unifor.universidade.dto.CreateTurmaRequest;
import com.unifor.universidade.model.Aluno;
import com.unifor.universidade.model.Turma;
import com.unifor.universidade.service.TurmaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/turmas")
public class TurmaController {

    private final TurmaService service;

    public TurmaController(TurmaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Turma> criar(@RequestBody CreateTurmaRequest req) {
        return ResponseEntity.ok(service.criar(req));
    }

    @GetMapping
    public List<Turma> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Turma buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @GetMapping("/{id}/alunos")
    public List<Aluno> listarAlunos(@PathVariable Long id) {
        return service.listarAlunos(id);
    }

    @PostMapping("/{id}/alunos/{alunoId}")
    public Turma adicionarAluno(@PathVariable Long id, @PathVariable Long alunoId) {
        return service.adicionarAluno(id, alunoId);
    }

    @DeleteMapping("/{id}/alunos/{alunoId}")
    public Turma removerAluno(@PathVariable Long id, @PathVariable Long alunoId) {
        return service.removerAluno(id, alunoId);
    }
}
