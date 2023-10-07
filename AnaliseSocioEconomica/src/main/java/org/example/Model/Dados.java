package org.example.Model;

import java.util.List;

public class Dados {
    private int id;
    private String indicador;
    private SerieAnoAtrib series;

    public Dados() {}

    public Dados(int id, String indicador, SerieAnoAtrib series) {
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

    public SerieAnoAtrib getSeries() {
        return series;
    }

    public void setSeries(SerieAnoAtrib series) {
        this.series = series;
    }
}
