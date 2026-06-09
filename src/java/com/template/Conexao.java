package com.template;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    static String conexao = "jdbc:postgresql://localhost:5432/db_biblioteca";
    static String usuario = "postgres";
    static String senha = "postgres";

    public static Connection conectaDB() throws SQLException {
        try {
            return DriverManager.getConnection(conexao, usuario, senha);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar: " + e.getMessage());
        }
    }
}
