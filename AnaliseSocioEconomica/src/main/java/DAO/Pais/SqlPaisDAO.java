package DAO.Pais;

import Controller.RequestController;
import Model.Pais;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SqlPaisDAO implements PaisDAO {
    private final Connection connection;

    private static final String CREATE_QUERY = "INSERT INTO t1bd.pais(sigla, nome_extenso) VALUES(?, ?);";

    private static final String READ_QUERY = "SELECT * FROM t1bd.pais WHERE sigla = ?;";

    private static final String UPDATE_QUERY = "UPDATE t1bd.pais SET sigla = ? nome_extenso = ? WHERE sigla = ?;";

    private static final String DELETE_QUERY = "DELETE FROM t1bd.pais WHERE sigla = ?;";

    private static final String ALL_QUERY = "SELECT sigla, nome_extenso FROM t1bd.pais ORDER BY sigla;";  // ORDER BY funciona aqui???
    // BASE:
    // https://bitbucket.org/dskaster/bd2022/src/master/bd2022/src/main/java/dao/PgUserDAO.java
    // Fazer sqlDadosDAO usando switch case para montar o texto de consulta (concatenando com "+") e switch case dentro do if (result.next())

    public SqlPaisDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Pais pais) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            statement.setString(1, pais.getId());
            statement.setString(2, pais.getNome());

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SqlPaisDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().contains("uq_pais_sigla")) {
                throw new SQLException("Erro ao inserir país: país já existente.");
            } else if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao inserir país: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao inserir novo país.");
            }
        }
    }

    @Override
    public Pais read(String siglaPais) throws SQLException {
        Pais pais = new Pais(siglaPais);

        try (PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            statement.setString(1, siglaPais);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    pais.setNome(result.getString("nome_extenso"));
                } else {
                    throw new SQLException("Erro ao visualizar: País não encontrado.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SqlPaisDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao visualizar: País não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao visualizar País.");
            }
        }

        return pais;
    }

    @Override
    public void update(Pais pais) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, pais.getId());
            statement.setString(2, pais.getNome());

            statement.setString(3, pais.getId());

            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao editar: país não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SqlPaisDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao editar: país não encontrado.")) {
                throw ex;
            } else if (ex.getMessage().contains("uq_pais_login")) {
                throw new SQLException("Erro ao editar país: país já existente.");
            } else if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao editar país: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao editar país.");
            }
        }
    }

    @Override
    public void delete(String sigla) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setString(1, sigla);

            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao excluir: país não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SqlPaisDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao excluir: país não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao excluir país.");
            }
        }
    }

    @Override
    public List<Pais> all() throws SQLException {
        List<Pais> paisList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(ALL_QUERY); ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                Pais pais = new Pais(result.getString("sigla"));
                pais.setNome(result.getString("nome_extenso"));

                paisList.add(pais);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SqlPaisDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar países.");
        }

        return paisList;
    }
}
