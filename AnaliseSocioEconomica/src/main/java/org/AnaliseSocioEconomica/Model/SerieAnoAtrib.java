package org.AnaliseSocioEconomica.Model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SerieAnoAtrib {
    private Map<Integer, String> duplaAnoAtributo;

    public SerieAnoAtrib() {
        duplaAnoAtributo = new HashMap<>();
    }

    public Map<Integer, String> getDuplaAnoAtributo() {
        return duplaAnoAtributo;
    }

    public void setDuplaAnoAtributo(int ano, String atributo) {
        this.duplaAnoAtributo.put(ano, atributo);
    }
}
