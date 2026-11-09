package org.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "times")
public class Time {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(name = "escudo_url")
    private String escudoUrl;

    @Column(name = "is_retro")
    private Boolean isRetro = false;

    @ManyToOne
    @JoinColumn(name = "liga_id", nullable = false)
    private Liga liga;

    // Construtor Padrão
    public Time() {}

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEscudoUrl() {
        return escudoUrl;
    }

    public void setEscudoUrl(String escudoUrl) {
        this.escudoUrl = escudoUrl;
    }

    public Boolean getIsRetro() {
        return isRetro;
    }

    public void setIsRetro(Boolean isRetro) {
        this.isRetro = isRetro;
    }

    public Liga getLiga() {
        return liga;
    }

    public void setLiga(Liga liga) {
        this.liga = liga;
    }
}