package org.example.controller;

import org.example.model.Produto;
import org.example.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping("/")
    public String index(@RequestParam(name = "categoria", required = false) String categoria, Model model) {
        List<Produto> produtos;

        if (categoria != null && !categoria.trim().isEmpty()) {
            // Busca direto do Banco de Dados com a Categoria especificada
            produtos = produtoRepository.findByCategoriaNome(categoria);
        } else {
            // Busca todos trazendo as associações zeradas
            produtos = produtoRepository.findAllComRelacionamentos();
        }

        model.addAttribute("produtos", produtos);
        model.addAttribute("categoriaAtiva", (categoria != null && !categoria.isBlank()) ? categoria : "Todos");
        return "index";
    }
}