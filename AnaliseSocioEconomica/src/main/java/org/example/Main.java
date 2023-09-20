package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        List<String> paises = List.of("BR", "US", "AR", "BE", "FR", "ZM");
        List<String> indicadores = List.of("77827", "77825", "77826", "77821", "77823", "77857", "77831");
        String url = "https://servicodados.ibge.gov.br/api/v1/paises/{pais}/indicadores/{indicador}";

        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();

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

        // Imprimir os resultados de forma mais legível
        for (Map.Entry<String, Map<String, JsonNode>> entry : resultadosPorPais.entrySet()) {
            String pais = entry.getKey(); // Chave do mapa geral, país
            Map<String, JsonNode> resultadosPorIndicador = entry.getValue(); // Sub-mapa dos indicadores
            System.out.println("Resultados para o país: " + pais);
            for (Map.Entry<String, JsonNode> indicadorEntry : resultadosPorIndicador.entrySet()) { 
                String indicador = indicadorEntry.getKey();  // Chave do sub-mapa, indicador
                JsonNode resposta = indicadorEntry.getValue(); // Valor do sub-mapa, resposta da API para tal indicador
                System.out.println("Indicador: " + indicador);
                System.out.println("Resposta da API:");
                System.out.println(resposta.toPrettyString()); // Imprime JSON +- formatado
            }
        }

//        BINGO fazendo um request so, lista de países e indicadores, NÃO DEU
//        String paisesParam = String.join(",", paises);
//        String indicadoresParam = String.join(",", indicadores);
//        String urlCompleta = url.replace("{pais}", paisesParam).replace("{indicador}", indicadoresParam);
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest request = HttpRequest.newBuilder()
//            .GET()
//            .uri(URI.create(urlCompleta))
//            .build();
//
//        try {
//            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//            System.out.println(response.statusCode());
//            System.out.println(response.body());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

    }
}