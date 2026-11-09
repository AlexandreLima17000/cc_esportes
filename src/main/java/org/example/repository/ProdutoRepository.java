package org.example.repository;

import org.example.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    @Query("SELECT p FROM Produto p LEFT JOIN FETCH p.categoria LEFT JOIN FETCH p.time LEFT JOIN FETCH p.selo")
    List<Produto> findAllComRelacionamentos();

    List<Produto> findByNomeContainingIgnoreCase(String nome);

    List<Produto> findByCategoriaId(Integer categoriaId);
}