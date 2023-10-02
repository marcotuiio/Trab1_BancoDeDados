package org.example.Model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SerieAnoAtrib {
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
}
