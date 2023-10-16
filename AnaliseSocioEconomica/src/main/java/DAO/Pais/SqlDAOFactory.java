package DAO.Pais;

import DAO.DAOFactory;
import DAO.Pais.PaisDAO;
import DAO.Pais.SqlPaisDAO;

import java.sql.Connection;

public class SqlDAOFactory extends DAOFactory {

    public SqlDAOFactory(Connection connection) {
        this.connection = connection;
    }

    @Override
    public PaisDAO getPaisDAO() {
        return new SqlPaisDAO(this.connection);
    }

}
