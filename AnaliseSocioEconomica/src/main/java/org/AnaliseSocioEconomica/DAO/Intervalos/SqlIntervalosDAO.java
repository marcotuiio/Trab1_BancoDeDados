package org.AnaliseSocioEconomica.DAO.Intervalos;

import org.AnaliseSocioEconomica.DAO.Pais.SqlPaisDAO;
import org.AnaliseSocioEconomica.Model.IntervaloAnosRequest;
import org.AnaliseSocioEconomica.Model.Pais;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SqlIntervalosDAO implements IntervalosDAO{
    private final Connection connection;

    private static final String CREATE_QUERY = "INSERT INTO t1bd.intervalos(anoInicial, anoFinal, requestDate) VALUES(?, ?, ?);";
    private static final String READ_QUERY = "SELECT * FROM t1bd.intervalos";
    private static final String UPDATE_QUERY = "UPDATE t1bd.intervalos SET anoInicial = ?, anoFinal = ?, requestDate = ? WHERE anoInicial = ?";
    private static final String DELETE_QUERY = "DELETE FROM t1bd.intervalos WHERE anoInicial = ?";

    public SqlIntervalosDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(IntervaloAnosRequest intervaloAnosRequest) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            statement.setInt(1, intervaloAnosRequest.getAnoInicial());
            statement.setInt(2, intervaloAnosRequest.getAnoFinal());
            statement.setDate(3, intervaloAnosRequest.getRequestDate());

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SqlPaisDAO.class.getName()).log(Level.SEVERE, "org/AnaliseSocioEconomica/DAO", ex);

            if (ex.getMessage().contains("pk_intervalos")) {
                throw new SQLException("Erro ao inserir data: intervalo repetido.");
            } else if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao inserir data: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao inserir novo intervalo.");
            }
        }
    }

    @Override
    public IntervaloAnosRequest read(String id) throws SQLException {
        IntervaloAnosRequest intervaloAnosRequest = new IntervaloAnosRequest();

        try (PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    intervaloAnosRequest.setAnoInicial(result.getInt("anoInicial"));
                    intervaloAnosRequest.setAnoFinal(result.getInt("anoFinal"));
                    intervaloAnosRequest.setRequestDate(result.getDate("requestDate"));
                } else {
                    throw new SQLException("Erro ao visualizar: Intervalo não encontrado.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SqlPaisDAO.class.getName()).log(Level.SEVERE, "org/AnaliseSocioEconomica/DAO", ex);

            if (ex.getMessage().equals("Erro ao visualizar: Intervalo não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao visualizar Intervalo.");
            }
        }
        return intervaloAnosRequest;
    }

    @Override
    public void update(IntervaloAnosRequest intervaloAnosRequest) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setInt(1, intervaloAnosRequest.getAnoFinal()+1);
            statement.setInt(2, intervaloAnosRequest.getAnoFinal()+1);
            statement.setDate(3, intervaloAnosRequest.getRequestDate());

            statement.setInt(4, intervaloAnosRequest.getAnoInicial());

            System.out.println("\nATUALIZANDO INTERVALO: " + statement);

            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao editar: intervalo não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SqlPaisDAO.class.getName()).log(Level.SEVERE, "org/AnaliseSocioEconomica/DAO", ex);

            if (ex.getMessage().equals("Erro ao editar: país não encontrado.")) {
                throw ex;
            } else if (ex.getMessage().contains("pk_intervalo")) {
                throw new SQLException("Erro ao editar intervalo: intervalo já existente.");
            } else if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao editar intervalo: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao editar intervalo.");
            }
        }
    }

    @Override
    public void delete(String id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, Integer.parseInt(id));

            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao excluir: ano inicial não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SqlPaisDAO.class.getName()).log(Level.SEVERE, "org/AnaliseSocioEconomica/DAO", ex);

            if (ex.getMessage().equals("Erro ao excluir: ano inicial não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao excluir intervalo.");
            }
        }
    }

    @Override
    public List<IntervaloAnosRequest> all() throws SQLException {
        return null;
    }
}
