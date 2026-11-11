package org.example.repository;

import org.example.model.Promocao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromocaoRepository extends JpaRepository<Promocao, Integer> {
    List<Promocao> findByAtivaTrue();
}