package org.AnaliseSocioEconomica.JDBC;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PgConnectionFactory extends ConnectionFactory {

    public PgConnectionFactory() {}

    @Override
    public Connection getConnection() throws IOException, SQLException, ClassNotFoundException {
        Connection connection = null;

        try {
            Class.forName("org.postgresql.Driver");

            readProperties();

            String dbUrl = "jdbc:postgre://" + dbHost + ":" + dbPort + "/" + dbName + "?currentSchema= " + dbSchema;
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
