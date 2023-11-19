package org.AnaliseSocioEconomica.DAO.Dados;

import org.AnaliseSocioEconomica.Model.Dados;
import org.AnaliseSocioEconomica.DAO.Pais.SqlPaisDAO;
import org.AnaliseSocioEconomica.Model.SerieAnoAtrib;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SqlDadosDAO implements DadosDAO {
    private final Connection connection;

    private  static final String START_CREATE_QUERY = "INSERT INTO t1bd.";
    private  static final String END_CREATE_QUERY = "(ano, sigla, valor) VALUES(?, ?, ?);";
    private static final String START_READBYSIGLA_QUERY = "SELECT * FROM t1bd.";
    private static final String END_READBYSIGLA_QUERY = " WHERE sigla = ?";
    private static final String START_READUNIQUE_QUERY = "SELECT * FROM t1bd.";
    private static final String END_READUNIQUE_QUERY = " WHERE sigla = ? AND ano = ?";
    private static final String START_DELETE_QUERY = "DELETE FROM t1bd.";
    private static final String END_DELETEALL_QUERY = ";";
    private static final String END_DELETEUNIQUE_QUERY = " WHERE sigla = ? and ano = ?;";
    private static final String END_DELETEALLPAIS_QUERY = " WHERE sigla = ?;";
    private static final String START_UPDATE_QUERY = "UPDATE t1bd.";
    private static final String END_UPDATE_QUERY = " SET valor = ? WHERE ano = ? AND sigla = ?;";

    public SqlDadosDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Dados dados) throws SQLException {
        String createQuery = START_CREATE_QUERY + dados.getIndicador() + END_CREATE_QUERY;

        try (PreparedStatement statement = connection.prepareStatement(createQuery)) {
            Map<Integer, String> duplaAnoAtributo = dados.getSeries().getDuplaAnoAtributo();
            for (Map.Entry<Integer, String> entry : duplaAnoAtributo.entrySet()) {
                statement.setInt(1, entry.getKey());
                statement.setString(2, dados.getSigla());
                statement.setString(3, entry.getValue());
                statement.addBatch();
                statement.clearParameters();
            }
            statement.executeBatch();

        } catch (SQLException ex) {
            Logger.getLogger(SqlDadosDAO.class.getName()).log(Level.SEVERE, "org/AnaliseSocioEconomica/DAO", ex);
            throw new SQLException("Erro ao criar dados por país.");
        }
    }


    @Override
    public Dados readBySigla(String indicador, String siglaPais) throws SQLException {
        String readQuery = START_READBYSIGLA_QUERY + indicador + END_READBYSIGLA_QUERY;
        SerieAnoAtrib series = new SerieAnoAtrib();

        try (PreparedStatement statement = connection.prepareStatement(readQuery)) {
            statement.setString(1, siglaPais);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    int ano = result.getInt("ano");
                    String valor = result.getString("valor");
                    series.setDuplaAnoAtributo(ano, valor);
                }
            } catch (SQLException ex) {
                Logger.getLogger(SqlDadosDAO.class.getName()).log(Level.SEVERE, "org/AnaliseSocioEconomica/DAO", ex);
                throw ex;
            }
        }

        Dados dados = new Dados(siglaPais, indicador, series);

        return dados;
    }

    @Override
    public SerieAnoAtrib readUniqueAno(String indicador, String siglaPais, int ano) throws SQLException {
        String readQuery = START_READUNIQUE_QUERY + indicador + END_READUNIQUE_QUERY;
        SerieAnoAtrib series = new SerieAnoAtrib();

        try (PreparedStatement statement = connection.prepareStatement(readQuery)) {
            statement.setString(1, siglaPais);
            statement.setInt(2, ano);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    String valor = result.getString("valor");
                    series.setDuplaAnoAtributo(ano, valor);
                }
            } catch (SQLException ex) {
                Logger.getLogger(SqlDadosDAO.class.getName()).log(Level.SEVERE, "org/AnaliseSocioEconomica/DAO", ex);
                throw ex;
            }
        }
        return series;
    }

    @Override
    public void update(Dados dados) throws SQLException {
        String updateQuery = START_UPDATE_QUERY + dados.getIndicador() + END_UPDATE_QUERY;

        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            Map<Integer, String> duplaAnoAtributo = dados.getSeries().getDuplaAnoAtributo();
            for (Map.Entry<Integer, String> entry : duplaAnoAtributo.entrySet()) {
                statement.setString(1, entry.getValue());
                statement.setInt(2, entry.getKey());
                statement.setString(3, dados.getSigla());
                statement.addBatch();
            }

            for (int i : statement.executeBatch()) {
                if (i < 0) System.out.println("Erro ao editar dados\n");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SqlPaisDAO.class.getName()).log(Level.SEVERE, "org/AnaliseSocioEconomica/DAO", ex);
            throw ex;
        }
    }

    @Override
    public void delete(String indicador) throws SQLException {
        String deleteQuery = START_DELETE_QUERY + indicador + END_DELETEALL_QUERY;

        try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            System.out.println(statement);
            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao excluir: dado não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SqlPaisDAO.class.getName()).log(Level.SEVERE, "org/AnaliseSocioEconomica/DAO", ex);
            throw ex;
        }
    }

    @Override
    public void deleteSpecificDados(String indicador, String siglaPais, int ano) throws SQLException {
        String deleteQuery = null;
        if (ano == 0) {
            deleteQuery = START_DELETE_QUERY + indicador + END_DELETEALLPAIS_QUERY;
        } else {
            deleteQuery = START_DELETE_QUERY + indicador + END_DELETEUNIQUE_QUERY;
        }

        try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            if (ano == 0) {
                statement.setString(1, siglaPais);
            } else {
                statement.setString(1, siglaPais);
                statement.setInt(2, ano);
            }
            System.out.println(statement);
            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao excluir: dado não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SqlPaisDAO.class.getName()).log(Level.SEVERE, "org/AnaliseSocioEconomica/DAO", ex);
            throw ex;
        }

    }


    @Override
    public Dados read(String indicador) throws SQLException { return null; }
    @Override
    public List<Dados> all() throws SQLException { return null; }

}
