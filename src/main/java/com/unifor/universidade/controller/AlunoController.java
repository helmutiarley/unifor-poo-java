package com.unifor.universidade.controller;

import com.unifor.universidade.model.Aluno;
import com.unifor.universidade.model.Bolsista;
import com.unifor.universidade.model.Regular;
import com.unifor.universidade.service.AlunoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/alunos")
public class AlunoController {

    private final AlunoService service;

    public AlunoController(AlunoService service) {
        this.service = service;
    }

    @PostMapping("/regular")
    public ResponseEntity<Regular> criarRegular(@RequestBody Regular regular) {
        return ResponseEntity.ok(service.criarRegular(regular));
    }

    @PostMapping("/bolsista")
    public ResponseEntity<Bolsista> criarBolsista(@RequestBody Bolsista bolsista) {
        return ResponseEntity.ok(service.criarBolsista(bolsista));
    }

    @GetMapping
    public List<Aluno> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Aluno buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    /** Demonstra polimorfismo: cada subclasse retorna um valor diferente. */
    @GetMapping("/{id}/mensalidade")
    public Map<String, Object> pagarMensalidade(@PathVariable Long id) {
        Aluno aluno = service.buscarPorId(id);
        return Map.of(
                "id", aluno.getId(),
                "nome", aluno.getNome(),
                "tipo", aluno.getClass().getSimpleName(),
                "valor", aluno.pagarMensalidade()
        );
    }
}
