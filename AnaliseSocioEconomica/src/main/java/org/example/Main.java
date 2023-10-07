package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.opencsv.exceptions.CsvException;
import org.example.Controller.Controller;
import org.example.Model.Dados;
import org.example.Model.Pais;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();

        Map<String, Map<String, JsonNode>> resultadosPorPais = controller.makeRequestAPI();  // dados brutos json da API
        List<Pais> meusPaises = controller.filtraPaises(resultadosPorPais);  // filtro inicial, limpando mapas e anos desejado

        controller.callCSVFilter(meusPaises);

        controller.insertIntoDB(meusPaises);

        controller.printarMeusPaises(meusPaises);
    }
}
