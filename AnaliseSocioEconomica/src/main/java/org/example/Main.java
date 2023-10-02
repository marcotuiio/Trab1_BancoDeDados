package org.example;

import com.opencsv.exceptions.CsvException;
import org.example.Controller.Controller;
import org.example.Model.Dados;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();

//        Map<String, Map<String, JsonNode>> resultadosPorPais = controller.makeRequestAPI();
//        controller.filtraPaises(resultadosPorPais);
        try {
            controller.LeituraCSV("tax_revenue", Dados.class);
        } catch (InstantiationException | IllegalAccessException | IOException |
                 CsvException e) {
            e.printStackTrace();
        }
    }
}
