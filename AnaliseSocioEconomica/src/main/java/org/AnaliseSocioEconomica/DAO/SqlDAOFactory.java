package org.AnaliseSocioEconomica.DAO;

import org.AnaliseSocioEconomica.DAO.Dados.SqlDadosDAO;
import org.AnaliseSocioEconomica.DAO.Intervalos.IntervalosDAO;
import org.AnaliseSocioEconomica.DAO.Intervalos.SqlIntervalosDAO;
import org.AnaliseSocioEconomica.DAO.Pais.PaisDAO;
import org.AnaliseSocioEconomica.DAO.Pais.SqlPaisDAO;

import org.AnaliseSocioEconomica.DAO.Dados.DadosDAO;

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

    @Override
    public IntervalosDAO getIntervalosDAO() { return new SqlIntervalosDAO(this.connection); }
}
