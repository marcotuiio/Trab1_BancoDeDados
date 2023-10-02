package org.example.Model;

import java.util.List;

public class AbstrataDadosCSV {
    private String tipoIndicador;
    private List<SerieAnoAtrib> series;

    public String getTipoIndicador() {
        return tipoIndicador;
    }

    public void setTipoIndicador(String tipoIndicador) {
        this.tipoIndicador = tipoIndicador;
    }

    public List<SerieAnoAtrib> getSeries() {
        return series;
    }

    public void setSeries(List<SerieAnoAtrib> series) {
        this.series = series;
    }
}
