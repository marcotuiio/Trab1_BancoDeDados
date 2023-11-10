package DAO.Dados;

import Controller.RequestController;
import DAO.Pais.SqlPaisDAO;
import Model.Dados;
import Model.SerieAnoAtrib;
import org.apache.el.stream.StreamELResolverImpl;
import org.apache.tomcat.util.http.parser.Upgrade;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SqlDadosDAO implements DadosDAO {
    private final Connection connection;

    private  static final String START_CREATE_QUERY = "INSERT INTO t1bd.";
    private  static final String END_CREATE_QUERY = "(ano, sigla, valor) VALUES(?, ?, ?);";
    private static final String START_READBYSIGLA_QUERY = "SELECT * FROM t1bd.";
    private static final String END_READBYSIGLA_QUERY = " WHERE sigla = ?;";
    private static final String START_DELETE_QUERY = "DELETE * FROM t1bd.";
    private static final String END_DELETE_QUERY = ";";
    private static final String START_UPDATE_QUERY = "UPDATE t1bd.";
    private static final String END_UPDATE_QUERY = " SET valor = ? WHERE ano = ? AND sigla = ?;";  // NÂO SEI SE ESSE AND FUNCIONA

    public SqlDadosDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Dados dados) throws SQLException {
        String createQuery = START_CREATE_QUERY + dados.getIndicador() + END_CREATE_QUERY;

        try (PreparedStatement statement = connection.prepareStatement(createQuery)) {
            Map.Entry<Integer, String> entry = (Map.Entry<Integer, String>) dados.getSeries().getDuplaAnoAtributo().entrySet();
            statement.setInt(1, entry.getKey());
            statement.setString(2, dados.getSigla());
            statement.setString(3, entry.getValue());

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SqlDadosDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao criar dados por país.");
        }
    }

    public Dados readBySigla(String indicador, String siglaPais) throws SQLException {
        String readQuery = START_READBYSIGLA_QUERY + indicador + END_READBYSIGLA_QUERY;
        SerieAnoAtrib series = new SerieAnoAtrib();

        try (PreparedStatement statement = connection.prepareStatement(readQuery)) {
            statement.setString(1, siglaPais);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    int ano = result.getInt("ano");
                    String valor = result.getString("valor");
                    series.setDuplaAnoAtributo(ano, valor);
                } else {
                    throw new SQLException("Erro ao visualizar dados por país: dado não encontrado.");
                }
            } catch (SQLException ex) {
                Logger.getLogger(SqlDadosDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
                throw ex;
            }
        }

        Dados dados = new Dados(siglaPais, indicador, series);

        return dados;
    }

    @Override
    public void update(Dados dados) throws SQLException {
        String updateQuery = START_UPDATE_QUERY + dados.getIndicador() + END_UPDATE_QUERY;
        Map.Entry<Integer, String> entry = (Map.Entry<Integer, String>) dados.getSeries().getDuplaAnoAtributo().entrySet();

        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setString(1, entry.getValue());
            statement.setInt(2, entry.getKey());
            statement.setString(3, dados.getSigla());

            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao editar dados.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SqlPaisDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw ex;
        }
    }

    @Override
    public void delete(String indicador) throws SQLException {
        String deleteQuery = START_DELETE_QUERY + indicador + END_DELETE_QUERY;

        try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao excluir: dado não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SqlPaisDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw ex;
        }
    }

    @Override
    public Dados read(String indicador) throws SQLException { return null; }
    @Override
    public List<Dados> all() throws SQLException { return null; }
}
