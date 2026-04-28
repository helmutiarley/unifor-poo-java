package com.unifor.universidade.controller;

import com.unifor.universidade.model.Disciplina;
import com.unifor.universidade.service.DisciplinaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disciplinas")
public class DisciplinaController {

    private final DisciplinaService service;

    public DisciplinaController(DisciplinaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Disciplina> criar(@RequestBody Disciplina disciplina) {
        return ResponseEntity.ok(service.criar(disciplina));
    }

    @GetMapping
    public List<Disciplina> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Disciplina buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }
}
