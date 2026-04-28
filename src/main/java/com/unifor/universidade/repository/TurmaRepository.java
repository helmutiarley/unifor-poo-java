package com.unifor.universidade.repository;

import com.unifor.universidade.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Long> {
    Optional<Turma> findByCodigo(String codigo);
}
