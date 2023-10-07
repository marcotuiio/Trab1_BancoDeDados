package org.example.Model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SerieAnoAtrib {
    private int anoToSet;
    private String valueToSet;
    private Map<Integer, String> duplaAnoAtributo = new HashMap<>();

    public SerieAnoAtrib(int ano, String atributo) {
        this.duplaAnoAtributo.put(ano, atributo);
    }

    public Map<Integer, String> getDuplaAnoAtributo() {
        return duplaAnoAtributo;
    }

    public void setDuplaAnoAtributo(Map<Integer, String> value) {
        this.duplaAnoAtributo = value;
    }

    public void setAnoValorFromDupla(Map<Integer, String> dupla) {
        for (Map.Entry d : dupla.entrySet()) {
            this.anoToSet = (int) d.getKey();
            this.valueToSet = (String) d.getValue();
        }
    }

    public int getAnoToSet() { return anoToSet; }

    public String getValueToSet() { return valueToSet; }
}
