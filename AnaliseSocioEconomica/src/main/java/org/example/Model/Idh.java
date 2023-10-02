package org.example.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Idh extends AbstrataDadosIBGE {
    private int id;
    private String indicador;
    private List<SerieAnoAtrib> series;

    public Idh(int id, String indicador, List<SerieAnoAtrib> series) {
        this.id = id;
        this.indicador = indicador;
        this.series = series;
    }
}
