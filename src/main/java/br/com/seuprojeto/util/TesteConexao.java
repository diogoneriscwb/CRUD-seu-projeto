package br.com.seuprojeto.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TesteConexao {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/crud_java";
        String usuario = "root";
        String senha = "M@$ter10.";

        try (Connection conn = DriverManager.getConnection(url, usuario, senha)) {
            if (conn != null) {
                System.out.println("✅ Conexão bem-sucedida!");
            } else {
                System.out.println("⚠️ Falha na conexão.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao conectar: " + e.getMessage());
        }
    }
}
