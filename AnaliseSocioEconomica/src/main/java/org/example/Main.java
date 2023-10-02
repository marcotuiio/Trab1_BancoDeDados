package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.opencsv.exceptions.CsvException;
import org.example.Controller.Controller;
import org.example.Model.ReceitaFiscal;

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();

//        Map<String, Map<String, JsonNode>> resultadosPorPais = controller.makeRequestAPI();
//        controller.filtraPaises(resultadosPorPais);
        try {
            controller.LeituraCSV("tax_revenue", ReceitaFiscal.class);
        } catch (InstantiationException | IllegalAccessException | IOException |
                 CsvException e) {
            e.printStackTrace();
        }
    }
}