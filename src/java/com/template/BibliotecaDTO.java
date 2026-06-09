package com.template;

public class BibliotecaDTO {
    private int id;
    private String nome;
    private String autor;
    private String genero;
    private int dificuldade;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    public int getDificuldade() { return dificuldade; }

    public void setDificuldade(int dificuldade) { this.dificuldade = dificuldade; }
}