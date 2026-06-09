package com.template;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BibliotecaDAO {

    // LISTAR
    public ArrayList<BibliotecaDTO> listarLivro() {
        String sql = "SELECT id, nome, autor, genero, dificuldade FROM livro ORDER BY id";

        ArrayList<BibliotecaDTO> lista = new ArrayList<>();
        try (
                Connection conexao = Conexao.conectaDB();
                PreparedStatement ps = conexao.prepareStatement(sql);
                ResultSet resultado = ps.executeQuery()
        ) {
            while (resultado.next()) {
                BibliotecaDTO livro = new BibliotecaDTO();

                livro.setId(resultado.getInt("id"));
                livro.setNome(resultado.getString("nome"));
                livro.setAutor(resultado.getString("autor"));
                livro.setGenero(resultado.getString("genero"));
                livro.setDificuldade(resultado.getInt("dificuldade"));

                System.out.println("ID: " + resultado.getInt("id"));
                System.out.println("Nome: " + resultado.getString("nome"));
                System.out.println("Autor: " + resultado.getString("autor"));
                System.out.println("Genero: " + resultado.getString("genero"));
                System.out.println("Dificuldade: " + resultado.getInt("dificuldade"));
                System.out.println();

                lista.add(livro);
            }

            if (lista.isEmpty()) {
                System.out.println("Nenhum livro cadastrado.");
            }
        } catch (SQLException excecao) {
            Logger.getLogger(BibliotecaDAO.class.getName()).log(Level.SEVERE, null, excecao);
        }

        return lista;
    }

    // INSERT
    public void salvarLivro(BibliotecaDTO livro) {
    String sql = "INSERT INTO livro (nome, autor, genero, dificuldade) VALUES (?, ?, ?, ?)";

        try (
                Connection conexao = Conexao.conectaDB();
                PreparedStatement ps = conexao.prepareStatement(sql)
        ) {
            ps.setString(1, livro.getNome());
            ps.setString(2, livro.getAutor());
            ps.setString(3, livro.getGenero());
            ps.setInt(4, livro.getDificuldade());

            ps.executeUpdate();
        } catch (SQLException excecao) {
            Logger.getLogger(BibliotecaDAO.class.getName()).log(Level.SEVERE, null, excecao);
        }
    }

    // UPDATE
    public void alterarLivro(BibliotecaDTO livro) {
        String sql = "UPDATE livro SET nome = ?, autor = ?, genero = ?, dificuldade = ? WHERE id = ?";

        try (
                Connection conexao = Conexao.conectaDB();
                PreparedStatement ps = conexao.prepareStatement(sql)
        ) {
            ps.setString(1, livro.getNome());
            ps.setString(2, livro.getAutor());
            ps.setString(3, livro.getGenero());
            ps.setInt(4, livro.getDificuldade());
            ps.setInt(5, livro.getId());

            ps.executeUpdate();
        } catch (SQLException excecao) {
            Logger.getLogger(BibliotecaDAO.class.getName()).log(Level.SEVERE, null, excecao);
        }
    }

    // DELETE
    public void deletarLivro(BibliotecaDTO objBibliotecaDTO) {
        String sql = "DELETE FROM livro WHERE id = ?";

        try (
                Connection conexao = Conexao.conectaDB();
                PreparedStatement ps = conexao.prepareStatement(sql)
        ) {
            ps.setInt(1, objBibliotecaDTO.getId());
            ps.executeUpdate();
        } catch (SQLException excecao) {
            Logger.getLogger(BibliotecaDAO.class.getName()).log(Level.SEVERE, null, excecao);
        }
    }
}