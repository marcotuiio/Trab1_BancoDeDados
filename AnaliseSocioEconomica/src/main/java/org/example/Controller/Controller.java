package org.example.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;

import com.opencsv.exceptions.CsvException;
import org.example.Model.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.io.FileReader;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {
    private static List<String> paises = List.of("AR", "AU", "BR", "CA", "CN", "DE", "FR", "GB", "ID",
                                                    "IN", "IT", "JP", "KR", "MX", "RU", "SA", "TR", "US", "ZA");
    private static List<String> indicadores = List.of("77827", "77825", "77826", "77821", "77823", "77857", "77831");
//    private static List<String> paises = List.of("BR");
//    private static List<String> indicadores = List.of("77827");
    private static String url = "https://servicodados.ibge.gov.br/api/v1/paises/{pais}/indicadores/{indicador}";

    private static HttpClient client = HttpClient.newHttpClient();

    private static ObjectMapper objectMapper = new ObjectMapper();

    private DatabaseManager databaseManager = new DatabaseManager();

    PreparedStatement preparedStatement;
    private static String PATH_TO_CSV = "C:\\Users\\marco\\Desktop\\UEL\\Database\\Trab1_BancoDeDados\\CSVs\\";
//    private static String PATH_TO_CSV = "/home/vfs/Documents/Database_I/Trab1_BancoDeDados/CSVs/";

    private static List<String> arquivos = List.of("impComInter", "impExportacao", "impReceitaFiscal",
                                                    "impAlfanImport", "impRenda");

    public Map<String, Map<String, JsonNode>> makeRequestAPI() {

        // Mapa de Mapas para armazenar os resultados
        // A chave geral é o país, e a chave de chada sub-mapa é o indicador
        // Então por linhas gerais tem-se mapa do páis com vários atributos de indicadores
        Map<String, Map<String, JsonNode>> resultadosPorPais = new HashMap<>();

            // Fazer um request para cada país e indicador
            for (String pais : paises) {

                // Para cada país, criar um sub-mapa para armazenar os resultados dos indicadores
                Map<String, JsonNode> resultadosPorIndicador = new HashMap<>();

                for (String indicador : indicadores) {

                    // Fazer o request de GET para a API para aquele país e indicador
                    String urlCompleta = url.replace("{pais}", pais).replace("{indicador}", indicador);
                    HttpRequest request = HttpRequest.newBuilder()
                            .GET()
                            .uri(URI.create(urlCompleta))
                            .build();

                    try {

                        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                        if (response.statusCode() == 200) {
                            // Converter a resposta para um objeto JsonNode e armazenar no mapa dos indicadores
                            JsonNode jsonResponse = objectMapper.readTree(response.body());
                            resultadosPorIndicador.put(indicador, jsonResponse);

                        } else {
                            System.out.println("ERRO NO REQUEST COM RESPONSE CODE = " + response.statusCode());
                        }

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }
                // Armazenar o sub-mapa dos indicadores no mapa geral dos países
                resultadosPorPais.put(pais, resultadosPorIndicador);
            }
        return resultadosPorPais;
    }

    public List<Pais> filtraPaises(Map<String, Map<String, JsonNode>> resultadosPorPais) {

        ArrayList<Pais> meusPaises = new ArrayList<>();

        // Imprimir os resultados de forma mais legível
        for (Map.Entry<String, Map<String, JsonNode>> entry : resultadosPorPais.entrySet()) {

            String paisSigla = entry.getKey(); // Chave do mapa geral, país
            Map<String, JsonNode> resultadosPorIndicador = (Map<String, JsonNode>) entry.getValue(); // Sub-mapa dos indicadores
//            System.out.println("Resultados para o país: " + paisSigla);

            Pais paisTeste = new Pais(paisSigla);
            meusPaises.add(paisTeste);

            for (Map.Entry<String, JsonNode> indicadorEntry : resultadosPorIndicador.entrySet()) {

                int idInd = Integer.parseInt(indicadorEntry.getKey());  // Chave do sub-mapa, indicador
                JsonNode resposta = indicadorEntry.getValue(); // Valor do sub-mapa, resposta da API para tal indicador
                String nomeInd = resposta.get(0).get("indicador").asText(); // pega nome do indicador
                String nomePais = resposta.get(0).get("series").get(0).get("pais").get("nome").asText();  // pega nome do pais
                JsonNode seriesDados = resposta.get(0).get("series").get(0).get("serie"); // pega serie de anos e valores
                paisTeste.setNome(nomePais);

//                System.out.println(idInd + " | " + nomeInd + " | " + nomePais);

                SerieAnoAtrib serieAnoAtrib = new SerieAnoAtrib();

                for (JsonNode ponto : seriesDados) {
                    Iterator<String> fieldNames = ponto.fieldNames();
                    while (fieldNames.hasNext()) {
                        String ano = fieldNames.next();
                        String valor = ponto.get(ano).asText();

                        // Intervalo de anos definido como foco de estudo 2010 - 2021
                        // A escolha foi feita em grande parte pela falta de dados
                        // para muitos países em anos anteriores

                        if (ano.length() == 4) {
                            if (Integer.parseInt(ano) >= 2010 && Integer.parseInt(ano) <= 2021) {
                                serieAnoAtrib.setDuplaAnoAtributo(Integer.parseInt(ano), valor);
//                        System.out.println("Ano: " + ano + ", Valor: " + valor);
                            }
                        }
                    }
                }

                // Fiz a série temporal para ser generica, recebendo duas strings no mapa, entao pode ser aproveitada
                // Classe de todos os indicativos é genericas, pode ser amplamente reusado ja que os
                // dados filtrados sao os mesmos.

                switch (idInd) {
                    case 77827: // Total PIB
                        Dados indicadorPib = new Dados(idInd, nomeInd, serieAnoAtrib);
                        paisTeste.setPibTotal(indicadorPib);
                        break;
                    case 77825: // Total Exportações
                        Dados indicadorExportacoes = new Dados(idInd, nomeInd, serieAnoAtrib);
                        paisTeste.setTotalExportacao(indicadorExportacoes);
                        break;
                    case 77826: // Total Importações
                        Dados indicadorImportacoes = new Dados(idInd, nomeInd, serieAnoAtrib);
                        paisTeste.setTotalImportacao(indicadorImportacoes);
                        break;
                    case 77821: // Investimentos em pesquisa e desenvolvimento
                        Dados indicadorInvestimento = new Dados(idInd, nomeInd, serieAnoAtrib);
                        paisTeste.setInvestPesqDesenv(indicadorInvestimento);
                        break;
                    case 77823: // PIB per capita
                        Dados indicadorPibPC = new Dados(idInd, nomeInd, serieAnoAtrib);
                        paisTeste.setPibPerCapita(indicadorPibPC);
                        break;
                    case 77857: // Indivíduos com acesso à internet
                        Dados indicadorIndividuos = new Dados(idInd, nomeInd, serieAnoAtrib);
                        paisTeste.setIndivAcesNet(indicadorIndividuos);
                        break;
                    case 77831: // IDH
                        Dados indicadorIdh = new Dados(idInd, nomeInd, serieAnoAtrib);
                        paisTeste.setIdh(indicadorIdh);
                        break;
                }
            }
        }
        return meusPaises;
    }

    public void callCSVFilter(List<Pais> meusPaises) {
        for (String arq : arquivos) {
            try {
                LeituraCSV(meusPaises, arq, Dados.class);
            } catch (InstantiationException | IllegalAccessException | IOException |
                     CsvException e) {
                e.printStackTrace();
            }
        }

    }

    public void LeituraCSV(List<Pais> meusPaises, String arquivo, Class<? extends Dados> type)
            throws IOException, CsvException, InstantiationException, IllegalAccessException {

        CSVReader reader = new CSVReader(new FileReader(PATH_TO_CSV + arquivo + ".csv"));
        List<String[]> linhas = reader.readAll();
//        System.out.println("ARQUIVO:" + PATH_TO_CSV + arquivo + ".csv");

        boolean isFirst = true;
        for (String[] linha : linhas) {
            if (isFirst == true) {
                isFirst = false;
                continue;
            }
            String nomePaisCsv = linha[0]; // traduzir nome ou colocar a sigla tal qual nos do IBGE
//            System.out.println("-----> País linha[0]: " + nomePaisCsv);
            int ano = 2010;
            SerieAnoAtrib serieAnoAtrib = new SerieAnoAtrib();
            for (int i = 1; i < linha.length; i++) {
                String valor = linha[i];
                serieAnoAtrib.setDuplaAnoAtributo(ano, valor);
                ano++;
            }

            Dados objeto = new Dados();
            objeto.setIndicador(arquivo);
            objeto.setSeries(serieAnoAtrib);

            Pais paisToSet = null;
            switch (nomePaisCsv) {
                case "Argentina":
                    paisToSet = meusPaises.stream()
                            .filter(pais -> pais.getId().equals("AR"))
                            .findFirst()
                            .orElse(null);
                    break;
                case "Australia":
                    paisToSet = meusPaises.stream()
                            .filter(pais -> pais.getId().equals("AU"))
                            .findFirst()
                            .orElse(null);
                    break;
                case "Brazil":
                    paisToSet = meusPaises.stream()
                            .filter(pais -> pais.getId().equals("BR"))
                            .findFirst()
                            .orElse(null);
                    break;
                case "Canada":
                    paisToSet = meusPaises.stream()
                            .filter(pais -> pais.getId().equals("CA"))
                            .findFirst()
                            .orElse(null);
                    break;
                case "China":
                    paisToSet = meusPaises.stream()
                            .filter(pais -> pais.getId().equals("CN"))
                            .findFirst()
                            .orElse(null);
                    break;
                case "Germany":
                    paisToSet = meusPaises.stream()
                            .filter(pais -> pais.getId().equals("DE"))
                            .findFirst()
                            .orElse(null);
                    System.out.println(paisToSet.getNome());
                    break;
                case "France":
                    paisToSet = meusPaises.stream()
                            .filter(pais -> pais.getId().equals("FR"))
                            .findFirst()
                            .orElse(null);
                    break;
                case "United Kingdom":
                    paisToSet = meusPaises.stream()
                            .filter(pais -> pais.getId().equals("GB"))
                            .findFirst()
                            .orElse(null);
                    break;
                case "Indonesia":
                    paisToSet = meusPaises.stream()
                            .filter(pais -> pais.getId().equals("ID"))
                            .findFirst()
                            .orElse(null);
                    break;
                case "India":
                    paisToSet = meusPaises.stream()
                            .filter(pais -> pais.getId().equals("IN"))
                            .findFirst()
                            .orElse(null);
                    break;
                case "Italy":
                    paisToSet = meusPaises.stream()
                            .filter(pais -> pais.getId().equals("IT"))
                            .findFirst()
                            .orElse(null);
                    break;
                case "Japan":
                    paisToSet = meusPaises.stream()
                            .filter(pais -> pais.getId().equals("JP"))
                            .findFirst()
                            .orElse(null);
                    break;
                case "Korea, Rep.":
                    paisToSet = meusPaises.stream()
                            .filter(pais -> pais.getId().equals("KR"))
                            .findFirst()
                            .orElse(null);
                    break;
                case "Mexico":
                    paisToSet = meusPaises.stream()
                            .filter(pais -> pais.getId().equals("MX"))
                            .findFirst()
                            .orElse(null);
                    break;
                case "Russian Federation":
                    paisToSet = meusPaises.stream()
                            .filter(pais -> pais.getId().equals("RU"))
                            .findFirst()
                            .orElse(null);
                    break;
                case "Saudi Arabia":
                    paisToSet = meusPaises.stream()
                            .filter(pais -> pais.getId().equals("SA"))
                            .findFirst()
                            .orElse(null);
                    break;
                case "Turkiye":
                    paisToSet = meusPaises.stream()
                            .filter(pais -> pais.getId().equals("TR"))
                            .findFirst()
                            .orElse(null);
                    break;
                case "United States":
                    paisToSet = meusPaises.stream()
                            .filter(pais -> pais.getId().equals("US"))
                            .findFirst()
                            .orElse(null);
                    break;
                case "South Africa":
                    paisToSet = meusPaises.stream()
                            .filter(pais -> pais.getId().equals("ZA"))
                            .findFirst()
                            .orElse(null);
                    break;
                default:
                    System.out.println("ERRO -> País não está no CSV");
                    break;
            }

            if (paisToSet == null) {
                System.out.println("ERRO -> País " + nomePaisCsv + " não está no filtro do IBGE");
                break;
            }

            switch (arquivo) {
                case "impComInter":
                    paisToSet.setImpComInter(objeto);
                    break;
                case "impExportacao":
                    paisToSet.setImpExportacao(objeto);
                    break;
                case "impReceitaFiscal":
                    paisToSet.setImpReceitaFiscal(objeto);
                    break;
                case "impAlfanImport":
                    paisToSet.setImpAlfanImport(objeto);
                    break;
                case "impRenda":
                    paisToSet.setImpRenda(objeto);
                    break;
                default:
                    System.out.println("ERRO -> Atibuto" + arquivo + "não está nos dados de interesse dos países");
                    break;
            }
        }
    }

    public void printarMeusPaises(List<Pais> meusPaises) {
        System.out.println("\n---- TESTE POS FILTRO ----");
        for (Pais p : meusPaises) {
            System.out.println("\n"+ p.getId() + " || " + p.getNome());

            System.out.println("-- DADOS DO IBGE --");

            System.out.println("\n<Serie anual PibTotal>");
            Map<Integer, String> pib = p.getPibTotal().getSeries().getDuplaAnoAtributo();
            for (Map.Entry<Integer, String> entry : pib.entrySet()) {
                System.out.println("ANO: " + entry.getKey() + " VALOR: " + entry.getValue());
            }

            System.out.println("\n<Serie anual PibPerCapita>");
            Map<Integer, String> pipCapita = p.getPibPerCapita().getSeries().getDuplaAnoAtributo();
            for (Map.Entry<Integer, String> entry : pipCapita.entrySet()) {
                System.out.println("ANO: " + entry.getKey() + " VALOR: " + entry.getValue());
            }

            System.out.println("\n<Serie anual TotalExportacoes>");
            Map<Integer, String> totalExp = p.getTotalExportacao().getSeries().getDuplaAnoAtributo();
            for (Map.Entry<Integer, String> entry : totalExp.entrySet()) {
                System.out.println("ANO: " + entry.getKey() + " VALOR: " + entry.getValue());
            }

            System.out.println("\n<Serie anual TotalImportacoes>");
            Map<Integer, String> totalImp = p.getTotalImportacao().getSeries().getDuplaAnoAtributo();
            for (Map.Entry<Integer, String> entry : totalImp.entrySet()) {
                System.out.println("ANO: " + entry.getKey() + " VALOR: " + entry.getValue());
            }

            System.out.println("\n<Serie anual IndividuosAcessoInternet>");
            Map<Integer, String> acessoNet = p.getIndivAcesNet().getSeries().getDuplaAnoAtributo();
            for (Map.Entry<Integer, String> entry : acessoNet.entrySet()) {
                System.out.println("ANO: " + entry.getKey() + " VALOR: " + entry.getValue());
            }

            System.out.println("\n<Serie anual IDH>");
            Map<Integer, String> idh = p.getIdh().getSeries().getDuplaAnoAtributo();
            for (Map.Entry<Integer, String> entry : idh.entrySet()) {
                System.out.println("ANO: " + entry.getKey() + " VALOR: " + entry.getValue());
            }

            System.out.println("\n-- DADOS DO CSV --");

            System.out.println("\n<Serie anual impComInter>");
            Map<Integer, String> impInter = p.getImpComInter().getSeries().getDuplaAnoAtributo();
            for (Map.Entry<Integer, String> entry : impInter.entrySet()) {
                System.out.println("ANO: " + entry.getKey() + " VALOR: " + entry.getValue());
            }

            System.out.println("\n<Serie anual impExportacao>");
            Map<Integer, String> impExp = p.getImpExportacao().getSeries().getDuplaAnoAtributo();
            for (Map.Entry<Integer, String> entry : impExp.entrySet()) {
                System.out.println("ANO: " + entry.getKey() + " VALOR: " + entry.getValue());
            }

            System.out.println("\n<Serie anual impReceitaFiscal>");
            Map<Integer, String> receitaFiscal = p.getImpReceitaFiscal().getSeries().getDuplaAnoAtributo();
            for (Map.Entry<Integer, String> entry : receitaFiscal.entrySet()) {
                System.out.println("ANO: " + entry.getKey() + " VALOR: " + entry.getValue());
            }

            System.out.println("\n<Serie anual impAlfanImport>");
            Map<Integer, String> impAlf = p.getImpAlfanImport().getSeries().getDuplaAnoAtributo();
            for (Map.Entry<Integer, String> entry : impAlf.entrySet()) {
                System.out.println("ANO: " + entry.getKey() + " VALOR: " + entry.getValue());
            }

            System.out.println("\n<Serie anual impRenda>");
            Map<Integer, String> impRenda = p.getImpRenda().getSeries().getDuplaAnoAtributo();
            for (Map.Entry<Integer, String> entry : impRenda.entrySet()) {
                System.out.println("ANO: " + entry.getKey() + " VALOR: " + entry.getValue());
            }

            System.out.println("\n\n");
        }
    }

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
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        pipTotal.setId(1);
        pipTotal.setIndicador("Economia - Total do PIB");
        return pipTotal;
    }

    public void insertPais(List<Pais> meusPaises) {
        databaseManager.connect();
        Connection connection = databaseManager.getConnection();

        for (Pais pais : meusPaises) {
            try {
                String sqlInsertUser = "INSERT INTO pais (sigla, nome_extenso) VALUES (?, ?)";
                preparedStatement = connection.prepareStatement(sqlInsertUser);

                preparedStatement.setString(1, pais.getId());
                preparedStatement.setString(2, pais.getNome());

                int linhasAfetadas = preparedStatement.executeUpdate();

                if (linhasAfetadas == 0) {
                    return;
                }
            } catch (SQLException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
 }
