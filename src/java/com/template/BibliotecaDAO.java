package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.dto.BibliotecaDTO;
import model.dao.Conexao;

public class BibliotecaDAO {

    Connection c;
    PreparedStatement ps;
    ResultSet rs;

    // LISTAR
    public void selecionarLivro() {
        String sql = "SELECT id, nome, autor, genero, dificuldade FROM livro ORDER BY id";

        try (
                Connection conexao = Conexao.conectaDB();
                PreparedStatement ps = conexao.prepareStatement(sql);
                ResultSet resultado = ps.executeQuery() //armazena os dados que o banco de dados devolveu após processar o comando
        ) {
            boolean encontrouLivro = false;

            while (resultado.next())//.next serve para mostrar a linha depois do resultado
            {
                encontrouLivro = true;
                System.out.println("ID: " + resultado.getInt("id"));
                System.out.println("Nome: " + resultado.getString("nome"));
                System.out.println("Autor: " + resultado.getString("autor"));
                System.out.println("Genero: " + resultado.getString("genero"));
                System.out.println("Dificuldade: " + resultado.getInt("dificuldade"));
                System.out.println();
            }

            if (!encontrouLivro) {
                System.out.println("Nenhum livro cadastrado."); //Uma variável de controle para avisar caso a tabela esteja vazia.
            }
        } catch (SQLException excecao) {
            Logger.getLogger(BibliotecaDAO.class.getName()).log(Level.SEVERE, null, excecao);
        }
    }

    // INSERT
    public void cadastrarLivro(BibliotecaDTO livro) {
        String sql = "INSERT INTO livro (nome, autor, genero, dificuldade) VALUES (?, ?, ?, ?)"; //placeholders

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
    public void deletarLivro(int id) {
        String sql = "DELETE FROM livro WHERE id = ?";

        try (
                Connection conexao = Conexao.conectaDB();
                PreparedStatement ps = conexao.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException excecao) {
            Logger.getLogger(BibliotecaDAO.class.getName()).log(Level.SEVERE, null, excecao);
        }
    }
}
