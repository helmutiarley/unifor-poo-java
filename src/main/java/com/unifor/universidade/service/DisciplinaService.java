package com.unifor.universidade.service;

import com.unifor.universidade.exception.NotFoundException;
import com.unifor.universidade.model.Disciplina;
import com.unifor.universidade.repository.DisciplinaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisciplinaService {

    private final DisciplinaRepository repository;

    public DisciplinaService(DisciplinaRepository repository) {
        this.repository = repository;
    }

    public Disciplina criar(Disciplina disciplina) {
        return repository.save(disciplina);
    }

    public List<Disciplina> listar() {
        return repository.findAll();
    }

    public Disciplina buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Disciplina com id " + id + " não encontrada."));
    }
}
