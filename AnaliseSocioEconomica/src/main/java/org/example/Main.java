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

        Map<String, Map<String, JsonNode>> resultadosPorPais = new HashMap<>();

        for (String pais : paises) {
            Map<String, JsonNode> resultadosPorIndicador = new HashMap<>();
            for (String indicador : indicadores) {
                String urlCompleta = url.replace("{pais}", pais).replace("{indicador}", indicador);

                HttpRequest request = HttpRequest.newBuilder()
                        .GET()
                        .uri(URI.create(urlCompleta))
                        .build();

                try {
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    if (response.statusCode() == 200) {
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
            resultadosPorPais.put(pais, resultadosPorIndicador);
        }

        // Imprimir os resultados de forma mais legível
        for (Map.Entry<String, Map<String, JsonNode>> entry : resultadosPorPais.entrySet()) {
            String pais = entry.getKey();
            Map<String, JsonNode> resultadosPorIndicador = entry.getValue();
            System.out.println("Resultados para o país: " + pais);
            for (Map.Entry<String, JsonNode> indicadorEntry : resultadosPorIndicador.entrySet()) {
                String indicador = indicadorEntry.getKey();
                JsonNode resposta = indicadorEntry.getValue();
                System.out.println("Indicador: " + indicador);
                System.out.println("Resposta da API:");
                System.out.println(resposta.toPrettyString()); // Imprime JSON formatado
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