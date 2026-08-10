package com.template.service;

import com.template.model.dao.BibliotecaDAO;
import com.template.model.dto.BibliotecaDTO;

import java.util.List;

public class BibliotecaService {

    private final BibliotecaDAO bibliotecaDAO = new BibliotecaDAO();

    public List<BibliotecaDTO> listar() {
        return bibliotecaDAO.listarLivro();
    }

    public void salvar(String nome, String autor, String genero, String dificuldadeStr) {
        int dificuldade = Integer.parseInt(dificuldadeStr.trim());
        BibliotecaDTO dto = criarDTO(null, nome, autor, genero, dificuldade);
        bibliotecaDAO.salvarLivro(dto);
    }

    public void atualizar(int id, String nome, String autor, String genero, String dificuldadeStr) {
        int dificuldade = Integer.parseInt(dificuldadeStr.trim());
        BibliotecaDTO dto = criarDTO(id, nome, autor, genero, dificuldade);
        bibliotecaDAO.alterarLivro(dto);
    }

    public void deletar(BibliotecaDTO livro) {
        bibliotecaDAO.deletarLivro(livro);
    }

    // Helper para construir DTO sem expor construtores repetitivos no Controller
    private BibliotecaDTO criarDTO(Integer id, String nome, String autor, String genero, int dificuldade) {
        BibliotecaDTO dto = new BibliotecaDTO();
        if (id != null) {
            dto.setId(id);
        }
        dto.setNome(nome);
        dto.setAutor(autor);
        dto.setGenero(genero);
        dto.setDificuldade(dificuldade);
        return dto;
    }
}