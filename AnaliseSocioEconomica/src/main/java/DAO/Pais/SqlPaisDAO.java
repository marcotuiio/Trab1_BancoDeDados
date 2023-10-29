package DAO.Pais;

import Model.Pais;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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
    public Pais read(Integer id) throws SQLException {
        // VINICIUUSSSS FAZER GET DO PAIS AQUI
        return null;
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
