package DatabaseController;

import JDBC.DatabaseManager;
import Model.Dados;
import Model.Pais;
import Model.SerieAnoAtrib;
import Model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseController {

    private DatabaseManager databaseManager = new DatabaseManager();
    PreparedStatement preparedStatement;

    public Pais queryPaises(String siglaPais) {
        databaseManager.connect();
        Connection connection = databaseManager.getConnection();

        Pais paisAlvo = new Pais(siglaPais);

        try {
            String sql = "SELECT * FROM t1bd.pais WHERE sigla = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, siglaPais);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String sigla = resultSet.getString("sigla");
                String nome = resultSet.getString("nome_entenso");
                paisAlvo.setNome(nome);

            } else {
                System.out.println("ERRO DE QUERY :: PAÍS COM SIGLA " + siglaPais + " NÃO ENCONTRADO\n");
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return paisAlvo;
    }

    public Dados queryPibTotal(String siglaPais) {
        databaseManager.connect();
        Connection connection = databaseManager.getConnection();

        Dados pipTotal = new Dados();
        try {
            String sql = "SELECT * FROM t1bd.t_pib_total WHERE sigla = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, siglaPais);

            ResultSet resultSet = preparedStatement.executeQuery();
            SerieAnoAtrib series = new SerieAnoAtrib();

            if (resultSet.next() == false) {
                System.out.println("ERRO DE QUERY EM PIB TOTAL :: PAÍS COM SIGLA " + siglaPais + " NÃO ENCONTRADO\n");
                return null;
            }

            while (resultSet.next()) {
                int ano = resultSet.getInt("sigla");
                String valor = resultSet.getString("pib_total_valor");

                series.setDuplaAnoAtributo(ano, valor);
            }
            pipTotal.setSeries(series);

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
        }

        pipTotal.setId(1);
        pipTotal.setIndicador("Economia - Total do PIB");
        return pipTotal;
    }

    public Dados queryPibPerCapita(String siglaPais) {
        databaseManager.connect();
        Connection connection = databaseManager.getConnection();

        Dados pipPerCapita = new Dados();
        try {
            String sql = "SELECT * FROM t1bd.t_pib_per_capita WHERE sigla = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, siglaPais);

            ResultSet resultSet = preparedStatement.executeQuery();
            SerieAnoAtrib series = new SerieAnoAtrib();

            if (resultSet.next() == false) {
                System.out.println("ERRO DE QUERY EM PIB PER CAPITA :: PAÍS COM SIGLA " + siglaPais + " NÃO ENCONTRADO\n");
                return null;
            }

            while (resultSet.next()) {
                int ano = resultSet.getInt("sigla");
                String valor = resultSet.getString("pib_per_capita_valor");

                series.setDuplaAnoAtributo(ano, valor);
            }
            pipPerCapita.setSeries(series);

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
        }

        pipPerCapita.setId(2);
        pipPerCapita.setIndicador("Economia - PIB per capita");
        return pipPerCapita;
    }

    public Dados queryTotalExportacao(String siglaPais) {
        databaseManager.connect();
        Connection connection = databaseManager.getConnection();

        Dados totalExportacao = new Dados();
        try {
            String sql = "SELECT * FROM t1bd.t_total_exportacao WHERE sigla = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, siglaPais);

            ResultSet resultSet = preparedStatement.executeQuery();
            SerieAnoAtrib series = new SerieAnoAtrib();

            if (resultSet.next() == false) {
                System.out.println("ERRO DE QUERY EM TOTAL EXPORTAÇÃO :: PAÍS COM SIGLA " + siglaPais + " NÃO ENCONTRADO\n");
                return null;
            }

            while (resultSet.next()) {
                int ano = resultSet.getInt("sigla");
                String valor = resultSet.getString("total_exportacao_valor");

                series.setDuplaAnoAtributo(ano, valor);
            }
            totalExportacao.setSeries(series);

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
        }

        totalExportacao.setId(3);
        totalExportacao.setIndicador("Economia - Total de exportações");
        return totalExportacao;
    }

    public Dados queryTotalImportacao(String siglaPais) {
        databaseManager.connect();
        Connection connection = databaseManager.getConnection();

        Dados totalImportacao = new Dados();
        try {
            String sql = "SELECT * FROM t1bd.t_total_importacao WHERE sigla = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, siglaPais);

            ResultSet resultSet = preparedStatement.executeQuery();
            SerieAnoAtrib series = new SerieAnoAtrib();

            if (resultSet.next() == false) {
                System.out.println("ERRO DE QUERY EM TOTAL IMPORTAÇÃO :: PAÍS COM SIGLA " + siglaPais + " NÃO ENCONTRADO\n");
                return null;
            }

            while (resultSet.next()) {
                int ano = resultSet.getInt("sigla");
                String valor = resultSet.getString("total_importacao_valor");

                series.setDuplaAnoAtributo(ano, valor);
            }
            totalImportacao.setSeries(series);

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
        }

        totalImportacao.setId(4);
        totalImportacao.setIndicador("Economia - Total de importações");
        return totalImportacao;
    }

    public Dados queryInvestPesqDesenv(String siglaPais) {
        databaseManager.connect();
        Connection connection = databaseManager.getConnection();

        Dados investPesqDesenv = new Dados();
        try {
            String sql = "SELECT * FROM t1bd.t_invest_pesq_desenv WHERE sigla = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, siglaPais);

            ResultSet resultSet = preparedStatement.executeQuery();
            SerieAnoAtrib series = new SerieAnoAtrib();

            if (resultSet.next() == false) {
                System.out.println("ERRO DE QUERY EM INVESTIMENTO PESQUISA E DESENVOLVIMENTO :: PAÍS COM SIGLA " + siglaPais + " NÃO ENCONTRADO\n");
                return null;
            }

            while (resultSet.next()) {
                int ano = resultSet.getInt("sigla");
                String valor = resultSet.getString("invest_pesq_desenv_valor");

                series.setDuplaAnoAtributo(ano, valor);
            }
            investPesqDesenv.setSeries(series);

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
        }

        investPesqDesenv.setId(5);
        investPesqDesenv.setIndicador("Economia - Investimentos em pesquisa e desenvolvimento");
        return investPesqDesenv;
    }

    public Dados queryIndivAcesNet(String siglaPais) {
        databaseManager.connect();
        Connection connection = databaseManager.getConnection();

        Dados indivAcesNet = new Dados();
        try {
            String sql = "SELECT * FROM t1bd.t_indiv_aces_net WHERE sigla = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, siglaPais);

            ResultSet resultSet = preparedStatement.executeQuery();
            SerieAnoAtrib series = new SerieAnoAtrib();

            if (resultSet.next() == false) {
                System.out.println("ERRO DE QUERY EM INDIVÍDUOS ACESSO INTERNET :: PAÍS COM SIGLA " + siglaPais + " NÃO ENCONTRADO\n");
                return null;
            }

            while (resultSet.next()) {
                int ano = resultSet.getInt("sigla");
                String valor = resultSet.getString("indiv_aces_net_valor");

                series.setDuplaAnoAtributo(ano, valor);
            }
            indivAcesNet.setSeries(series);

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
        }

        indivAcesNet.setId(6);
        indivAcesNet.setIndicador("Redes - Indivíduos com acesso à internet");
        return indivAcesNet;
    }

    public Dados queryIdh(String siglaPais) {
        databaseManager.connect();
        Connection connection = databaseManager.getConnection();

        Dados idh = new Dados();
        try {
            String sql = "SELECT * FROM t1bd.t_idh WHERE sigla = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, siglaPais);

            ResultSet resultSet = preparedStatement.executeQuery();
            SerieAnoAtrib series = new SerieAnoAtrib();

            if (resultSet.next() == false) {
                System.out.println("ERRO DE QUERY EM IDH :: PAÍS COM SIGLA " + siglaPais + " NÃO ENCONTRADO\n");
                return null;
            }

            while (resultSet.next()) {
                int ano = resultSet.getInt("sigla");
                String valor = resultSet.getString("idh_valor");

                series.setDuplaAnoAtributo(ano, valor);
            }
            idh.setSeries(series);

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
        }

        idh.setId(7);
        idh.setIndicador("Indicadores sociais - Índice de desenvolvimento humano");
        return idh;
    }
    public Dados queryImpComInter(String siglaPais) {
        databaseManager.connect();
        Connection connection = databaseManager.getConnection();

        Dados impComInter = new Dados();
        try {
            String sql = "SELECT * FROM t1bd.t_imp_com_inter WHERE sigla = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, siglaPais);

            ResultSet resultSet = preparedStatement.executeQuery();
            SerieAnoAtrib series = new SerieAnoAtrib();

            if (resultSet.next() == false) {
                System.out.println("ERRO DE QUERY EM IMPOSTO COMÉRCIO EXTERIOR :: PAÍS COM SIGLA " + siglaPais + " NÃO ENCONTRADO\n");
                return null;
            }

            while (resultSet.next()) {
                int ano = resultSet.getInt("sigla");
                String valor = resultSet.getString("imp_com_inter_valor");

                series.setDuplaAnoAtributo(ano, valor);
            }
            impComInter.setSeries(series);

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
        }

        impComInter.setId(8);
        impComInter.setIndicador("Impostos - Imposto sob comércio exterior");
        return impComInter;
    }

    public Dados queryImpExportacao(String siglaPais) {
        databaseManager.connect();
        Connection connection = databaseManager.getConnection();

        Dados impExportacao = new Dados();
        try {
            String sql = "SELECT * FROM t1bd.t_imp_exportacao WHERE sigla = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, siglaPais);

            ResultSet resultSet = preparedStatement.executeQuery();
            SerieAnoAtrib series = new SerieAnoAtrib();

            if (resultSet.next() == false) {
                System.out.println("ERRO DE QUERY EM IMPOSTO EXPORTAÇÃO :: PAÍS COM SIGLA " + siglaPais + " NÃO ENCONTRADO\n");
                return null;
            }

            while (resultSet.next()) {
                int ano = resultSet.getInt("sigla");
                String valor = resultSet.getString("imp_exportacao_valor");

                series.setDuplaAnoAtributo(ano, valor);
            }
            impExportacao.setSeries(series);

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
        }

        impExportacao.setId(9);
        impExportacao.setIndicador("Impostos - Imposto sob exportação");
        return impExportacao;
    }

    public Dados queryImpReceitaFiscal(String siglaPais) {
        databaseManager.connect();
        Connection connection = databaseManager.getConnection();

        Dados impReceitaFiscal = new Dados();
        try {
            String sql = "SELECT * FROM t1bd.t_imp_receita_fiscal WHERE sigla = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, siglaPais);

            ResultSet resultSet = preparedStatement.executeQuery();
            SerieAnoAtrib series = new SerieAnoAtrib();

            if (resultSet.next() == false) {
                System.out.println("ERRO DE QUERY EM RECEITA FISCAL :: PAÍS COM SIGLA " + siglaPais + " NÃO ENCONTRADO\n");
                return null;
            }

            while (resultSet.next()) {
                int ano = resultSet.getInt("sigla");
                String valor = resultSet.getString("imp_receita_fiscal_valor");

                series.setDuplaAnoAtributo(ano, valor);
            }
            impReceitaFiscal.setSeries(series);

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
        }

        impReceitaFiscal.setId(10);
        impReceitaFiscal.setIndicador("Impostos - Receita fiscal");
        return impReceitaFiscal;
    }

    public Dados queryImpAlfanImport(String siglaPais) {
        databaseManager.connect();
        Connection connection = databaseManager.getConnection();

        Dados impAlfanImport = new Dados();
        try {
            String sql = "SELECT * FROM t1bd.t_imp_alfan_import WHERE sigla = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, siglaPais);

            ResultSet resultSet = preparedStatement.executeQuery();
            SerieAnoAtrib series = new SerieAnoAtrib();

            if (resultSet.next() == false) {
                System.out.println("ERRO DE QUERY EM IMPOSTO ALFANDEGÁRIO :: PAÍS COM SIGLA " + siglaPais + " NÃO ENCONTRADO\n");
                return null;
            }

            while (resultSet.next()) {
                int ano = resultSet.getInt("sigla");
                String valor = resultSet.getString("imp_alfan_import_valor");

                series.setDuplaAnoAtributo(ano, valor);
            }
            impAlfanImport.setSeries(series);

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
        }

        impAlfanImport.setId(11);
        impAlfanImport.setIndicador("Impostos - Imposto alfandegário de importação");
        return impAlfanImport;
    }

    public Dados queryImpRenda(String siglaPais) {
        databaseManager.connect();
        Connection connection = databaseManager.getConnection();

        Dados impRenda = new Dados();
        try {
            String sql = "SELECT * FROM t1bd.t_imp_renda WHERE sigla = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, siglaPais);

            ResultSet resultSet = preparedStatement.executeQuery();
            SerieAnoAtrib series = new SerieAnoAtrib();

            if (resultSet.next() == false) {
                System.out.println("ERRO DE QUERY EM IMPOSTO DE RENDA :: PAÍS COM SIGLA " + siglaPais + " NÃO ENCONTRADO\n");
                return null;
            }

            while (resultSet.next()) {
                int ano = resultSet.getInt("sigla");
                String valor = resultSet.getString("imp_renda_valor");

                series.setDuplaAnoAtributo(ano, valor);
            }
            impRenda.setSeries(series);

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
        }

        impRenda.setId(12);
        impRenda.setIndicador("Impostos - Imposto de renda");
        return impRenda;
    }

    public void insertPais(List<Pais> meusPaises) {
        databaseManager.connect();
        Connection connection = databaseManager.getConnection();

        for (Pais pais : meusPaises) {
            try {
                String sqlInsertPais = "INSERT INTO T1BD.pais (sigla, nome_extenso) VALUES (?, ?)";
                preparedStatement = connection.prepareStatement(sqlInsertPais);

                preparedStatement.setString(1, pais.getId());
                preparedStatement.setString(2, pais.getNome());

                int linhasAfetadas = preparedStatement.executeUpdate();
                if (linhasAfetadas == 0) {
                    return;
                }
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void insertPibTotal(List<Pais> meusPaises) {
        databaseManager.connect();
        Connection connection = databaseManager.getConnection();

        for (Pais pais : meusPaises) {
            for (Map.Entry<Integer, String> entry : pais.getPibTotal().getSeries().getDuplaAnoAtributo().entrySet()) {
                try {
                    String sqlInsertPibTotal = "INSERT INTO T1BD.t_pib_total (ano, sigla, pib_total_valor) VALUES (?, ?, ?)";
                    preparedStatement = connection.prepareStatement(sqlInsertPibTotal);

                    preparedStatement.setInt(1, entry.getKey());
                    preparedStatement.setString(2, pais.getId());
                    preparedStatement.setString(3, entry.getValue());

                    int linhasAfetadas = preparedStatement.executeUpdate();
                    if (linhasAfetadas == 0) {
                        return;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void insertPibPerCapita(List<Pais> meusPaises) {
        databaseManager.connect();
        Connection connection = databaseManager.getConnection();

        for (Pais pais : meusPaises) {
            for (Map.Entry<Integer, String> entry : pais.getPibPerCapita().getSeries().getDuplaAnoAtributo().entrySet()) {
                try {
                    String sqlInsertPibPerCapita = "INSERT INTO T1BD.t_pib_per_capita (ano, sigla, pib_per_capita_valor) VALUES (?, ?, ?)";
                    preparedStatement = connection.prepareStatement(sqlInsertPibPerCapita);

                    preparedStatement.setInt(1, entry.getKey());
                    preparedStatement.setString(2, pais.getId());
                    preparedStatement.setString(3, entry.getValue());

                    int linhasAfetadas = preparedStatement.executeUpdate();
                    if (linhasAfetadas == 0) {
                        return;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void insertTotalExportacao(List<Pais> meusPaises) {
        databaseManager.connect();
        Connection connection = databaseManager.getConnection();

        for (Pais pais : meusPaises) {
            for (Map.Entry<Integer, String> entry : pais.getTotalExportacao().getSeries().getDuplaAnoAtributo().entrySet()) {
                try {
                    String sqlInsertTotalExportacao = "INSERT INTO T1BD.t_total_exportacao (ano, sigla, total_exportacao_valor) VALUES (?, ?, ?)";
                    preparedStatement = connection.prepareStatement(sqlInsertTotalExportacao);

                    preparedStatement.setInt(1, entry.getKey());
                    preparedStatement.setString(2, pais.getId());
                    preparedStatement.setString(3, entry.getValue());

                    int linhasAfetadas = preparedStatement.executeUpdate();
                    if (linhasAfetadas == 0) {
                        return;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void insertTotalImportacao(List<Pais> meusPaises) {
        databaseManager.connect();
        Connection connection = databaseManager.getConnection();

        for (Pais pais : meusPaises) {
            for (Map.Entry<Integer, String> entry : pais.getTotalImportacao().getSeries().getDuplaAnoAtributo().entrySet()) {
                try {
                    String sqlInsertTotalImportacao = "INSERT INTO T1BD.t_total_importacao (ano, sigla, total_importacao_valor) VALUES (?, ?, ?)";
                    preparedStatement = connection.prepareStatement(sqlInsertTotalImportacao);

                    preparedStatement.setInt(1, entry.getKey());
                    preparedStatement.setString(2, pais.getId());
                    preparedStatement.setString(3, entry.getValue());

                    int linhasAfetadas = preparedStatement.executeUpdate();
                    if (linhasAfetadas == 0) {
                        return;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void insertInvestPesqDesenv(List<Pais> meusPaises) {
        databaseManager.connect();
        Connection connection = databaseManager.getConnection();

        for (Pais pais : meusPaises) {
            for (Map.Entry<Integer, String> entry : pais.getInvestPesqDesenv().getSeries().getDuplaAnoAtributo().entrySet()) {
                try {
                    String sqlInsertPesqDesenv = "INSERT INTO T1BD.t_invest_pesq_desenv (ano, sigla, invest_pesq_desenv_valor) VALUES (?, ?, ?)";
                    preparedStatement = connection.prepareStatement(sqlInsertPesqDesenv);

                    preparedStatement.setInt(1, entry.getKey());
                    preparedStatement.setString(2, pais.getId());
                    preparedStatement.setString(3, entry.getValue());

                    int linhasAfetadas = preparedStatement.executeUpdate();
                    if (linhasAfetadas == 0) {
                        return;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void insertIndivAcesNet(List<Pais> meusPaises) {
        databaseManager.connect();
        Connection connection = databaseManager.getConnection();

        for (Pais pais : meusPaises) {
            for (Map.Entry<Integer, String> entry : pais.getIndivAcesNet().getSeries().getDuplaAnoAtributo().entrySet()) {
                try {
                    String sqlInsertIndivAcesNet = "INSERT INTO T1BD.t_indiv_aces_net (ano, sigla, indiv_aces_net_valor) VALUES (?, ?, ?)";
                    preparedStatement = connection.prepareStatement(sqlInsertIndivAcesNet);

                    preparedStatement.setInt(1, entry.getKey());
                    preparedStatement.setString(2, pais.getId());
                    preparedStatement.setString(3, entry.getValue());

                    int linhasAfetadas = preparedStatement.executeUpdate();
                    if (linhasAfetadas == 0) {
                        return;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void insertIdh(List<Pais> meusPaises) {
        databaseManager.connect();
        Connection connection = databaseManager.getConnection();

        for (Pais pais : meusPaises) {
            for (Map.Entry<Integer, String> entry : pais.getIdh().getSeries().getDuplaAnoAtributo().entrySet()) {
                try {
                    String sqlInsertIdh = "INSERT INTO T1BD.t_idh (ano, sigla, idh_valor) VALUES (?, ?, ?)";
                    preparedStatement = connection.prepareStatement(sqlInsertIdh);

                    preparedStatement.setInt(1, entry.getKey());
                    preparedStatement.setString(2, pais.getId());
                    preparedStatement.setString(3, entry.getValue());

                    int linhasAfetadas = preparedStatement.executeUpdate();
                    if (linhasAfetadas == 0) {
                        return;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void insertImpComInter(List<Pais> meusPaises) {
        databaseManager.connect();
        Connection connection = databaseManager.getConnection();

        for (Pais pais : meusPaises) {
            for (Map.Entry<Integer, String> entry : pais.getImpComInter().getSeries().getDuplaAnoAtributo().entrySet()) {
                try {
                    String sqlInsertImpComInter = "INSERT INTO T1BD.t_imp_com_inter (ano, sigla, imp_com_inter_valor) VALUES (?, ?, ?)";
                    preparedStatement = connection.prepareStatement(sqlInsertImpComInter);

                    preparedStatement.setInt(1, entry.getKey());
                    preparedStatement.setString(2, pais.getId());
                    preparedStatement.setString(3, entry.getValue());

                    int linhasAfetadas = preparedStatement.executeUpdate();
                    if (linhasAfetadas == 0) {
                        return;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void insertImpExportacao(List<Pais> meusPaises) {
        databaseManager.connect();
        Connection connection = databaseManager.getConnection();

        for (Pais pais : meusPaises) {
            for (Map.Entry<Integer, String> entry : pais.getImpExportacao().getSeries().getDuplaAnoAtributo().entrySet()) {
                try {
                    String sqlInsertImpExportacao = "INSERT INTO T1BD.t_imp_exportacao (ano, sigla, imp_exportacao_valor) VALUES (?, ?, ?)";
                    preparedStatement = connection.prepareStatement(sqlInsertImpExportacao);

                    preparedStatement.setInt(1, entry.getKey());
                    preparedStatement.setString(2, pais.getId());
                    preparedStatement.setString(3, entry.getValue());

                    int linhasAfetadas = preparedStatement.executeUpdate();
                    if (linhasAfetadas == 0) {
                        return;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void insertImpReceitaFiscal(List<Pais> meusPaises) {
        databaseManager.connect();
        Connection connection = databaseManager.getConnection();

        for (Pais pais : meusPaises) {
            for (Map.Entry<Integer, String> entry : pais.getImpReceitaFiscal().getSeries().getDuplaAnoAtributo().entrySet()) {
                try {
                    String sqlInsertImpReceitaFiscal = "INSERT INTO T1BD.t_imp_receita_fiscal (ano, sigla, imp_receita_fiscal_valor) VALUES (?, ?, ?)";
                    preparedStatement = connection.prepareStatement(sqlInsertImpReceitaFiscal);

                    preparedStatement.setInt(1, entry.getKey());
                    preparedStatement.setString(2, pais.getId());
                    preparedStatement.setString(3, entry.getValue());

                    int linhasAfetadas = preparedStatement.executeUpdate();
                    if (linhasAfetadas == 0) {
                        return;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void insertImpAlfanImport(List<Pais> meusPaises) {
        databaseManager.connect();
        Connection connection = databaseManager.getConnection();

        for (Pais pais : meusPaises) {
            for (Map.Entry<Integer, String> entry : pais.getImpAlfanImport().getSeries().getDuplaAnoAtributo().entrySet()) {
                try {
                    String sqlInsertImpAlfanImport = "INSERT INTO T1BD.t_imp_alfan_import (ano, sigla, imp_alfan_import_valor) VALUES (?, ?, ?)";
                    preparedStatement = connection.prepareStatement(sqlInsertImpAlfanImport);

                    preparedStatement.setInt(1, entry.getKey());
                    preparedStatement.setString(2, pais.getId());
                    preparedStatement.setString(3, entry.getValue());

                    int linhasAfetadas = preparedStatement.executeUpdate();
                    if (linhasAfetadas == 0) {
                        return;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void insertImpRenda(List<Pais> meusPaises) {
        databaseManager.connect();
        Connection connection = databaseManager.getConnection();

        for (Pais pais : meusPaises) {
            for (Map.Entry<Integer, String> entry : pais.getImpRenda().getSeries().getDuplaAnoAtributo().entrySet()) {
                try {
                    String sqlInsertImpRenda = "INSERT INTO T1BD.t_imp_renda (ano, sigla, imp_renda_valor) VALUES (?, ?, ?)";
                    preparedStatement = connection.prepareStatement(sqlInsertImpRenda);

                    preparedStatement.setInt(1, entry.getKey());
                    preparedStatement.setString(2, pais.getId());
                    preparedStatement.setString(3, entry.getValue());

                    int linhasAfetadas = preparedStatement.executeUpdate();
                    if (linhasAfetadas == 0) {
                        return;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
