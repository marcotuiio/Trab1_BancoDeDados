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

public class Controller {
    private static List<String> paises = List.of("AR", "AU", "BR", "CA", "CN", "DE", "FR", "GB", "ID",
                                                    "IN", "IT", "JP", "KR", "MX", "RU", "SA", "TR", "US", "ZA");
    List<String> indicadores = List.of("77827", "77825", "77826", "77821", "77823", "77857", "77831");
    String url = "https://servicodados.ibge.gov.br/api/v1/paises/{pais}/indicadores/{indicador}";

    private static HttpClient client = HttpClient.newHttpClient();
    private static ObjectMapper objectMapper = new ObjectMapper();

    private static String PATH_TO_CSV = "C:\\Users\\marco\\Desktop\\UEL\\Database\\Trab1_BancoDeDados\\CSVs\\";
//    private static String PATH_TO_CSV = "/home/vfs/Documents/Database_I/Trab1_BancoDeDados/CSVs/";

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

    public void filtraPaises(Map<String, Map<String, JsonNode>> resultadosPorPais) {

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

                System.out.println(idInd + " | " + nomeInd + " | " + nomePais);

                List<SerieAnoAtrib> serieAnoAtrib = new ArrayList<>();

                for (JsonNode ponto : seriesDados) {
                    Iterator<String> fieldNames = ponto.fieldNames();
                    while (fieldNames.hasNext()) {
                        String ano = fieldNames.next();
                        String valor = ponto.get(ano).asText();

                        if (ano.length() == 4) {
                            SerieAnoAtrib dupla = new SerieAnoAtrib(Integer.parseInt(ano), valor);
                            serieAnoAtrib.add(dupla);
//                        System.out.println("Ano: " + ano + ", Valor: " + valor);
                        }
                    }
                }

                // Observe que as classes seguem um padrão parecido com PIB total. Id, nome, dados que podem ser
                // filtrados e serie temporal de valores.
                // Fiz a série temporal para ser generica, recebendo duas strings no mapa, entao pode ser aproveitada
                // Tem que fazer agora as classes para todos os outros indicativos

                // Observe que nesse trecho não mexemos com os impostos ainda

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
    }

    public void printarMeusPaises(List<Pais> meusPaises) {
        System.out.println("---- TESTE POS FILTRO ----");
        for (Pais p : meusPaises) {
            System.out.println(p.getId() + " || " + p.getNome());
            // colocar um loop para cada atributo (ponto de interrogação)
            for (SerieAnoAtrib pib : p.getPibTotal().getSeries()){
                System.out.println("<Serie anual PibTotal>");
                System.out.println(pib.getDuplaAnoAtributo());
            }

            for (SerieAnoAtrib pibcapita : p.getPibPerCapita().getSeries()) {
                System.out.println("<Serie anual PibPerCapita>");
                System.out.println(pibcapita.getDuplaAnoAtributo());
            }

            for (SerieAnoAtrib exp : p.getTotalExportacao().getSeries()) {
                System.out.println("<Serie anual TotalExportacoes>");
                System.out.println(exp.getDuplaAnoAtributo());
            }

            for (SerieAnoAtrib imp : p.getTotalImportacao().getSeries()) {
                System.out.println("<Serie anual TotalImportacoes>");
                System.out.println(imp.getDuplaAnoAtributo());
            }

            for (SerieAnoAtrib net : p.getIndivAcesNet().getSeries()) {
                System.out.println("<Serie anual IndividuosAcessoInternet>");
                System.out.println(net.getDuplaAnoAtributo());
            }

            for (SerieAnoAtrib idh : p.getIdh().getSeries()) {
                System.out.println("<Serie anual IDH>");
                System.out.println(idh.getDuplaAnoAtributo());
            }

            System.out.println("\n\n");
        }
    }

    public void LeituraCSV(String arquivo, Class<? extends Dados> type)
            throws IOException, CsvException, InstantiationException, IllegalAccessException {
        CSVReader reader = new CSVReader(new FileReader(PATH_TO_CSV+arquivo+".csv"));
        List<String[]> linhas = reader.readAll();

        for (String[] linha : linhas) {
            String pais = linha[0]; // traduzir nome ou colocar a sigla tal qual nos do IBGE
            int ano = 2010;
            List<SerieAnoAtrib> serieAnoAtrib = new ArrayList<>();
            for (int i = 1; i < linha.length; i++) {
                String valor = linha[i];
                SerieAnoAtrib dupla = new SerieAnoAtrib(ano, valor);
                ano++;
                serieAnoAtrib.add(dupla);
            }

            Dados objeto = new Dados();
            objeto.setIndicador(arquivo);
            objeto.setSeries(serieAnoAtrib);

            System.out.println("\nArquivo " + arquivo + " País " + pais);
            for (SerieAnoAtrib s : serieAnoAtrib) {
                System.out.println(s.getDuplaAnoAtributo());
            }

            // Falta setar a qual país isso pertence
        }
    }
 }
