package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class Controller {
    private static List<String> paises = List.of("BR", "US", "AR", "BE", "FR", "ZM");
//    List<String> paises = List.of("BR");
//    List<String> indicadores = List.of("77827", "77825", "77826", "77821", "77823", "77857", "77831");
    List<String> indicadores = List.of("77827");
    String url = "https://servicodados.ibge.gov.br/api/v1/paises/{pais}/indicadores/{indicador}";

    private static HttpClient client = HttpClient.newHttpClient();
    private static ObjectMapper objectMapper = new ObjectMapper();

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
                        PibTotal indicadorPib = new PibTotal(idInd, nomeInd, serieAnoAtrib);
                        paisTeste.setPibTotal(indicadorPib);
                        break;
                    case 77825: // Total Exportações
                        TotalExportacoes indicadorExportacoes = new TotalExportacoes(idInd, nomeInd, serieAnoAtrib);
                        paisTeste.setTotalExportacoes(indicadorExportacoes);
                        break;
                    case 77826: // Total Importações
                        TotalImportacoes indicadorImportacoes = new TotalImportacoes(idInd, nomeInd, serieAnoAtrib);
                        paisTeste.setTotalImportacoes(indicadorImportacoes);
                        break;
                    case 77821: // Investimentos em pesquisa e desenvolvimento
                        InvestimentoPD indicadorInvestimento = new InvestimentoPD(idInd, nomeInd, serieAnoAtrib);
                        paisTeste.setInvestimentoPD(indicadorInvestimento);
                        break;
                    case 77823: // PIB per capita
                        PibPerCapita indicadorPibPC = new PibPerCapita(idInd, nomeInd, serieAnoAtrib);
                        paisTeste.setPibPerCapita(indicadorPibPC);
                        break;
                    case 77857: // Indivíduos com acesso à internet
                        IndividuosAI indicadorIndividuos = new IndividuosAI(idInd, nomeInd, serieAnoAtrib);
                        paisTeste.setIndividuosAI(indicadorIndividuos);
                        break;
                    case 77831: // IDH
                        Idh indicadorIdh = new Idh(idInd, nomeInd, serieAnoAtrib);
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
            for (SerieAnoAtrib m : p.getPibTotal().getSeries()) {
                System.out.println(m.getDuplaAnoAtributo());
            }
            System.out.println("\n\n");
        }
    }
}
