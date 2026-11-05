package org.example.controller;

import org.example.model.Categoria;
import org.example.model.Selo;
import org.example.model.Produto;
import org.example.model.Time;
import org.example.repository.CategoriaRepository;
import org.example.repository.LigaRepository;
import org.example.repository.ProdutoRepository;
import org.example.repository.SeloRepository;
import org.example.repository.TimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private TimeRepository timeRepository;

    @Autowired
    private SeloRepository seloRepository;

    @Autowired
    private LigaRepository ligaRepository;

    @GetMapping
    public String dashboard(Model model) {

        Produto produtoForm = new Produto();
        produtoForm.setCategoria(new Categoria());
        produtoForm.setTime(new Time());
        produtoForm.setSelo(new Selo());

        model.addAttribute("produtos", produtoRepository.findAllComRelacionamentos());
        model.addAttribute("categorias", categoriaRepository.findAll());
        model.addAttribute("times", timeRepository.findAll());
        model.addAttribute("ligas", ligaRepository.findAll());
        model.addAttribute("selos", seloRepository.findAll());

        model.addAttribute("novoProduto", produtoForm);
        model.addAttribute("novaCategoria", new Categoria());
        model.addAttribute("categoria", new Categoria());
        model.addAttribute("novoTime", new Time());
        model.addAttribute("time", new Time());
        model.addAttribute("temporada", "");
        return "admin/dashboard";
    }

    @PostMapping("/produtos/salvar")
    public String salvarProduto(@ModelAttribute Produto produto,
                                @RequestParam(value = "ligaPersonalizada", required = false) String ligaPersonalizada,
                                @RequestParam(value = "timePersonalizado", required = false) String timePersonalizado,
                                @RequestParam(value = "fileImagem", required = false) MultipartFile fileImagem) {

        if (produto.getId() != null && (fileImagem == null || fileImagem.isEmpty())) {
            produtoRepository.findById(produto.getId()).ifPresent(prodExistente -> {
                produto.setImagemUrl(prodExistente.getImagemUrl());
            });
        }

        if (timePersonalizado != null && !timePersonalizado.isBlank()){
            Time timeNovo = new Time();
            timeNovo.setNome(timePersonalizado);
            timeNovo.setIsRetro(false);
            timeRepository.save(timeNovo);

            produto.setTime(timeNovo);
        }

        if (produto.getQualidade() == null || produto.getQualidade().isBlank()) {
            produto.setQualidade("Qualidade Thai 1.1");
        }

        if (produto.getAtivo() == null) {
            produto.setAtivo(true);
        }

        // SALVA NA PASTA UPLOADS DA RAIZ DO PROJETO (FORA DE SRC)
        if (fileImagem != null && !fileImagem.isEmpty()) {
            try {
                String diretorioUpload = "uploads/";
                File pasta = new File(diretorioUpload);
                if (!pasta.exists()) {
                    pasta.mkdirs();
                }

                String nomeArquivo = UUID.randomUUID().toString() + "_" + fileImagem.getOriginalFilename();
                Path caminhoCompleto = Paths.get(diretorioUpload + nomeArquivo);

                Files.copy(fileImagem.getInputStream(), caminhoCompleto, StandardCopyOption.REPLACE_EXISTING);

                // Grava a URL publica para o navegador
                produto.setImagemUrl("/uploads/" + nomeArquivo);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        produtoRepository.save(produto);
        return "redirect:/admin";
    }
    @GetMapping("/produtos/excluir/{id}")
    public String excluirProduto(@PathVariable("id") Integer id) {
        produtoRepository.deleteById(id);
        return "redirect:/admin";
    }

    @PostMapping("/categorias/salvar")
    public String salvarCategoria(@ModelAttribute Categoria categoria) {
        categoriaRepository.save(categoria);
        return "redirect:/admin";
    }

    @PostMapping("/times/salvar")
    public String salvarTime(@ModelAttribute Time time) {
        timeRepository.save(time);
        return "redirect:/admin";
    }

    @GetMapping("/produtos/editar/{id}")
    public String editarProduto(@PathVariable("id") Integer id, Model model) {
        Produto produto = produtoRepository.findById(id).orElse(null);

        if (produto == null) {
            return "redirect:/admin";
        }

        if (produto.getCategoria() == null) produto.setCategoria(new Categoria());
        if (produto.getTime() == null) produto.setTime(new Time());
        if (produto.getSelo() == null) produto.setSelo(new Selo());

        model.addAttribute("produtos", produtoRepository.findAllComRelacionamentos());
        model.addAttribute("categorias", categoriaRepository.findAll());
        model.addAttribute("times", timeRepository.findAll());
        model.addAttribute("ligas", ligaRepository.findAll());
        model.addAttribute("selos", seloRepository.findAll());

        model.addAttribute("novoProduto", produto);
        model.addAttribute("novaCategoria", new Categoria());
        model.addAttribute("categoria", new Categoria());
        model.addAttribute("novoTime", new Time());
        model.addAttribute("time", new Time());
        model.addAttribute("temporada", produto.getTemporada());

        return "admin/dashboard";
    }
}