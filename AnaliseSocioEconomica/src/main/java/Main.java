import com.fasterxml.jackson.databind.JsonNode;
import Controller.Controller;
import Model.Pais;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();

        Map<String, Map<String, JsonNode>> resultadosPorPais = controller.makeRequestIbgeAPI();  // dados brutos json da API
        List<Pais> meusPaises = controller.filtraPaises(resultadosPorPais);  // filtro inicial, limpando mapas e anos desejado

        controller.callCSVFilter(meusPaises);

        controller.insertIntoDB(meusPaises);

//        controller.printarMeusPaises(meusPaises);
    }
}
