package com.unifor.universidade.service;

import com.unifor.universidade.exception.NotFoundException;
import com.unifor.universidade.model.Visitante;
import com.unifor.universidade.repository.VisitanteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitanteService {

    private final VisitanteRepository repository;

    public VisitanteService(VisitanteRepository repository) {
        this.repository = repository;
    }

    public Visitante criar(Visitante visitante) {
        return repository.save(visitante);
    }

    public List<Visitante> listar() {
        return repository.findAll();
    }

    public Visitante buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Visitante com id " + id + " não encontrado."));
    }
}
