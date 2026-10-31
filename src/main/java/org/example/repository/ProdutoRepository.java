package org.example.repository;

import org.example.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    // Busca todos os produtos trazendo as categorias e selos juntas no mesmo SQL
    @Query("SELECT p FROM Produto p LEFT JOIN FETCH p.categoria LEFT JOIN FETCH p.selo LEFT JOIN FETCH p.time")
    List<Produto> findAllComRelacionamentos();

    // Busca filtrada pelo nome da categoria direto no Banco de Dados
    @Query("SELECT p FROM Produto p LEFT JOIN FETCH p.categoria LEFT JOIN FETCH p.selo LEFT JOIN FETCH p.time WHERE LOWER(p.categoria.nome) = LOWER(:categoriaNome)")
    List<Produto> findByCategoriaNome(@Param("categoriaNome") String categoriaNome);
}