package org.example.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TotalExportacoes {
    private int id;
    private String indicador;
    private List<SerieAnoAtrib> series;

    public TotalExportacoes(int id, String indicador, List<SerieAnoAtrib> series) {
        this.id = id;
        this.indicador = indicador;
        this.series = series;
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

    public List<SerieAnoAtrib> getSeries() {
        return series;
    }

    public void setSeries(List<SerieAnoAtrib> series) {
        this.series = series;
    }
}
