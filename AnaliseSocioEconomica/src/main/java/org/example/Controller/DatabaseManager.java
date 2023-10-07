package org.example.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Classe que permite conexão ao banco de dados de forma génerica, limpo e simples de ser utilizado
// em qualquer outro lugar do código de forma mais bem otimizada
public class DatabaseManager {
    private static final String DB_URL = "jdbc:postgresql://sicm.dc.uel.br:5432/t1bd";
    private static final String DB_USER = "marcotab";
    private static final String DB_PASSWORD = "bd23";

    private Connection connection;

    public void connect() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

}