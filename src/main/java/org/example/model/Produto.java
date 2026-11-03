package org.example.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(length = 50)
    private String qualidade;

    @Column(name = "preco_a_partir", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoAPartir;

    @Column(name = "imagem_url", nullable = false)
    private String imagemUrl;

    @Column(nullable = false)
    private Boolean ativo = true;

    @Column(name = "criado_em", insertable = false, updatable = false)
    private LocalDateTime criadoEm;

    @Column (name = "temporada", length = 20)
    private String temporada;

    @Column(name = "modelo", length = 50)
    private String modelo;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "selo_id")
    private Selo selo;

    @ManyToOne
    @JoinColumn(name = "time_id")
    private Time time;

    public Produto() {}

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getQualidade() { return qualidade; }
    public void setQualidade(String qualidade) { this.qualidade = qualidade; }

    public BigDecimal getPrecoAPartir() { return precoAPartir; }
    public void setPrecoAPartir(BigDecimal precoAPartir) { this.precoAPartir = precoAPartir; }

    public String getImagemUrl() { return imagemUrl; }
    public void setImagemUrl(String imagemUrl) { this.imagemUrl = imagemUrl; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public LocalDateTime getCriadoEm() { return criadoEm; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public Selo getSelo() { return selo; }
    public void setSelo(Selo selo) { this.selo = selo; }

    public Time getTime() { return time; }
    public void setTime(Time time) { this.time = time; }

    public  String getTemporada() { return temporada; }
    public void setTemporada(String temporada) { this.temporada = temporada; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
}
