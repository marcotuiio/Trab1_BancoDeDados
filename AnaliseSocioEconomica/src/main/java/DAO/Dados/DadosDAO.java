package DAO.Dados;

import java.sql.SQLException;
import java.util.List;

import DAO.DAO;
import Model.Dados;

public interface DadosDAO extends DAO<Dados> {

    public Dados readBySigla(String indicador, String siglaPais) throws SQLException;
}
