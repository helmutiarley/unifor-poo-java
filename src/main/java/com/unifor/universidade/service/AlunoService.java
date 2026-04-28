package com.unifor.universidade.service;

import com.unifor.universidade.exception.NotFoundException;
import com.unifor.universidade.model.Aluno;
import com.unifor.universidade.model.Bolsista;
import com.unifor.universidade.model.Regular;
import com.unifor.universidade.repository.AlunoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoService {

    private final AlunoRepository repository;

    public AlunoService(AlunoRepository repository) {
        this.repository = repository;
    }

    public Regular criarRegular(Regular regular) {
        return repository.save(regular);
    }

    public Bolsista criarBolsista(Bolsista bolsista) {
        return repository.save(bolsista);
    }

    public List<Aluno> listar() {
        return repository.findAll();
    }

    public Aluno buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Aluno com id " + id + " não encontrado."));
    }

    /** Demonstra polimorfismo — cada subclasse implementa pagarMensalidade() de forma diferente. */
    public double pagarMensalidade(Long id) {
        return buscarPorId(id).pagarMensalidade();
    }
}
