package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PibTotal {
    private int id;
    private String indicador;
    private List<SeriePib> series = new ArrayList<>();

    public PibTotal(int id, String ind) {
        this.id = id;
        this.indicador = ind;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIndicador() {
        return indicador;
    }

    public void setIndicador(String indicador) {
        this.indicador = indicador;
    }

    public List<SeriePib> getSeries() {
        return series;
    }

    public void setSeries(List<SeriePib> series) {
        this.series = series;
    }
}
