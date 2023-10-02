package org.example.Model;

import java.util.List;

public class ImpostoComercioInter implements InterfaceDadosCSV {
    private String tipoIndicador;
    private List<SerieAnoAtrib> series;
    @Override
    public String getTipoIndicador() {
        return tipoIndicador;
    }

    @Override
    public void setTipoIndicador(String tipoIndicador) {
        this.tipoIndicador = tipoIndicador;
    }

    @Override
    public List<SerieAnoAtrib> getSeries() {
        return series;
    }

    @Override
    public void setSeries(List<SerieAnoAtrib> series) {
        this.series = series;
    }
}
