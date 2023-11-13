package org.AnaliseSocioEconomica.DAO.Dados;

import java.sql.SQLException;

import org.AnaliseSocioEconomica.DAO.DAO;
import org.AnaliseSocioEconomica.Model.Dados;

public interface DadosDAO extends DAO<Dados> {

    public Dados readBySigla(String indicador, String siglaPais) throws SQLException;
}
