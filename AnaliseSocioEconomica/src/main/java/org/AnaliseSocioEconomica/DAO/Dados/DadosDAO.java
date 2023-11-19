package org.AnaliseSocioEconomica.DAO.Dados;

import java.sql.SQLException;

import org.AnaliseSocioEconomica.DAO.DAO;
import org.AnaliseSocioEconomica.Model.Dados;
import org.AnaliseSocioEconomica.Model.SerieAnoAtrib;

public interface DadosDAO extends DAO<Dados> {

    public Dados readBySigla(String indicador, String siglaPais) throws SQLException;
    public SerieAnoAtrib readUniqueAno(String indicador, String siglaPais, int ano) throws SQLException;
}
