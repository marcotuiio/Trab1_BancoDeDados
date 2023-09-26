package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();

        Map<String, Map<String, JsonNode>> resultadosPorPais = controller.makeRequestAPI();
        controller.filtraPaises(resultadosPorPais);

    }
}