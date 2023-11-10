package DAO.Dados;

import Controller.RequestController;
import Model.Dados;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SqlDadosDAO implements DadosDAO {
    private final Connection connection;

    public SqlDadosDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Dados dados) throws SQLException {
        String createQuery;
        switch (dados.getIndicador()) {
            case "PibTotal":
                createQuery = "INSERT INTO t1bd.pib_total(ano, sigla, pib_total_valor) VALUES(?, ?, ?)";
                break;
            case "PibPerCapita":
                break;
            case "Total_Exportacao":
                break;
            case "Total_Importacao":
                break;
            case "Invest_Pesq_Desenv":
                break;
            case "Indiv_Aces_Net":
                break;
            case "IDH":
                break;
            case "Imp_Exportacao":
                break;
            case "Imp_Receita_Fiscal":
                break;
            case "Imp_Alfan_Import":
                break;
            case "Imp_Renda":
                break;
        }
    }

    @Override
    public Dados read(String seiLa) throws SQLException {
        return null;
    }

    @Override
    public void update(Dados dados) throws SQLException {
    }

    @Override
    public void delete(String seiLa) throws SQLException {

    }

    @Override
    public List<Dados> all() throws SQLException {
        return null;
    }
}

//String createQuery;
//switch () {
//        case "PibTotal":
//        break;
//        case "PibPerCapita":
//        break;
//        case "Total_Exportacao":
//        break;
//        case "Total_Importacao":
//        break;
//        case "Invest_Pesq_Desenv":
//        break;
//        case "Indiv_Aces_Net":
//        break;
//        case "IDH":
//        break;
//        case "Imp_Exportacao":
//        break;
//        case "Imp_Receita_Fiscal":
//        break;
//        case "Imp_Alfan_Import":
//        break;
//        case "Imp_Renda":
//        break;
//        }
