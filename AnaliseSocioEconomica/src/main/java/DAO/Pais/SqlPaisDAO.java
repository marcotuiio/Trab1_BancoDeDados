package DAO.Pais;

import Controller.Controller;
import Model.Pais;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SqlPaisDAO implements PaisDAO {
    private final Connection connection;

    // BASE:
    // https://bitbucket.org/dskaster/bd2022/src/master/bd2022/src/main/java/dao/PgUserDAO.java

    public SqlPaisDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Pais pais) throws SQLException {
        // VINICIUUSSSS FAZER INSERT DO PAIS AQUI
    }

    @Override
    public Pais read(String siglaPais) throws SQLException {
        Pais pais = new Pais(siglaPais);
//        Fazer sqlDadosDAO usando switch case para montar o texto de consulta (concatenando com "+") e switch case dentro do if (result.next())
        try (PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            statement.setString(1, siglaPais);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    pais.setNome(result.getString("nome_extenso"));
                } else {
                    throw new SQLException("Erro ao visualizar: País não encontrado.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgUserDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao visualizar: País não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao visualizar País.");
            }
        }

        return pais;
    }

    @Override
    public void update(Pais pais) throws SQLException {
        // VINICIUUSSSS FAZER UPDATE DO PAIS AQUI
    }

    @Override
    public void delete(Integer id) throws SQLException {
        // VINICIUUSSSS FAZER DELETE DO PAIS AQUI
    }

    @Override
    public List<Pais> all() throws SQLException {
        // VINICIUUSSSS FAZER LISTA DE TODOS OS PAISES AQUI
        return null;
    }

    @Override
    public Pais getBySigla(String sigla) throws SQLException {
        return null;
    }
}
