package com.unifor.universidade.controller;

import com.unifor.universidade.model.Pessoa;
import com.unifor.universidade.service.PessoaService;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {

    private final PessoaService service;

    public PessoaController(PessoaService service) {
        this.service = service;
    }

    /** Endpoint genérico — funciona para Visitante, Professor, Bolsista e Regular. */
    @PatchMapping("/{id}/aniversario")
    public Pessoa fazerAniversario(@PathVariable Long id) {
        return service.fazerAniversario(id);
    }
}
