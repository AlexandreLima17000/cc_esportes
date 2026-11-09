package org.example.controller;

import org.example.repository.CategoriaRepository;
import org.example.repository.LigaRepository;
import org.example.repository.ProdutoRepository;
import org.example.repository.SeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private LigaRepository ligaRepository;

    @Autowired
    private SeloRepository seloRepository;

    @GetMapping("/")
    public String index(@RequestParam(value = "categoriaId", required = false) Integer categoriaId,
                        @RequestParam(value = "busca", required = false) String busca,
                        Model model) {

        model.addAttribute("categorias", categoriaRepository.findAll());
        model.addAttribute("ligas", ligaRepository.findAll());
        model.addAttribute("selos", seloRepository.findAll());

        if (busca != null && !busca.isBlank()) {
            model.addAttribute("produtos", produtoRepository.findByNomeContainingIgnoreCase(busca));
            model.addAttribute("categoriaNome", "Busca: " + busca);
        } else if (categoriaId != null) {
            model.addAttribute("produtos", produtoRepository.findByCategoriaId(categoriaId));
            categoriaRepository.findById(categoriaId).ifPresent(cat ->
                    model.addAttribute("categoriaNome", cat.getNome())
            );
        } else {
            model.addAttribute("produtos", produtoRepository.findAllComRelacionamentos());
            model.addAttribute("categoriaNome", null);
        }

        return "index";
    }
}