package com.unifor.universidade.service;

import com.unifor.universidade.exception.NotFoundException;
import com.unifor.universidade.model.Professor;
import com.unifor.universidade.repository.ProfessorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorService {

    private final ProfessorRepository repository;

    public ProfessorService(ProfessorRepository repository) {
        this.repository = repository;
    }

    public Professor criar(Professor professor) {
        return repository.save(professor);
    }

    public List<Professor> listar() {
        return repository.findAll();
    }

    public Professor buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Professor com id " + id + " não encontrado."));
    }

    public String darAula(Long id) {
        return buscarPorId(id).darAula();
    }
}
