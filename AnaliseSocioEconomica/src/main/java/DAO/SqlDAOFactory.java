package DAO;

import DAO.Pais.PaisDAO;
import DAO.Pais.SqlPaisDAO;

import DAO.Dados.DadosDAO;
import DAO.Dados.SqlDadosDAO;

import java.sql.Connection;

public class SqlDAOFactory extends DAOFactory {

    public SqlDAOFactory(Connection connection) {
        this.connection = connection;
    }

    @Override
    public PaisDAO getPaisDAO() {
        return new SqlPaisDAO(this.connection);
    }

    @Override
    public DadosDAO getDadosDAO() {
        return new SqlDadosDAO(this.connection);
    }
}
