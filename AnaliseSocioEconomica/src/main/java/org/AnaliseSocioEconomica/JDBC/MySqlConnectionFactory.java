package org.AnaliseSocioEconomica.JDBC;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnectionFactory extends ConnectionFactory {

    public MySqlConnectionFactory() {}

    @Override
    public Connection getConnection() throws IOException, SQLException, ClassNotFoundException {
        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            readProperties();

            String dbUrl = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbSchema;
//            System.out.println("CONEXAO MYSQL " + dbUrl);
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

        } catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
            throw new ClassNotFoundException("Erro de conexão ao banco de dados.");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro de conexão ao banco de dados.");
        }

        return connection;
    }
}
