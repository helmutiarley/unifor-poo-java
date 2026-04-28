package com.unifor.universidade.controller;

import com.unifor.universidade.model.Visitante;
import com.unifor.universidade.service.VisitanteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visitantes")
public class VisitanteController {

    private final VisitanteService service;

    public VisitanteController(VisitanteService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Visitante> criar(@RequestBody Visitante visitante) {
        return ResponseEntity.ok(service.criar(visitante));
    }

    @GetMapping
    public List<Visitante> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Visitante buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }
}
