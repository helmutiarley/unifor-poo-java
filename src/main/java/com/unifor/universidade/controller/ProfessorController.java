package com.unifor.universidade.controller;

import com.unifor.universidade.model.Professor;
import com.unifor.universidade.service.ProfessorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/professores")
public class ProfessorController {

    private final ProfessorService service;

    public ProfessorController(ProfessorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Professor> criar(@RequestBody Professor professor) {
        return ResponseEntity.ok(service.criar(professor));
    }

    @GetMapping
    public List<Professor> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Professor buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @GetMapping("/{id}/dar-aula")
    public Map<String, String> darAula(@PathVariable Long id) {
        return Map.of("mensagem", service.darAula(id));
    }
}
