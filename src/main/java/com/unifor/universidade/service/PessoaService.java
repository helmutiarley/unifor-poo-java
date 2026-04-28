package com.unifor.universidade.service;

import com.unifor.universidade.exception.NotFoundException;
import com.unifor.universidade.model.Pessoa;
import com.unifor.universidade.repository.PessoaRepository;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {

    private final PessoaRepository repository;

    public PessoaService(PessoaRepository repository) {
        this.repository = repository;
    }

    /** Implementação universal de fazerAniversario para qualquer Pessoa. */
    public Pessoa fazerAniversario(Long id) {
        Pessoa pessoa = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pessoa com id " + id + " não encontrada."));
        pessoa.fazerAniversario();
        return repository.save(pessoa);
    }
}
