package org.example.Model;

import java.util.List;

public interface InterfaceDadosCSV {
    public String getTipoIndicador();
    public void setTipoIndicador(String tipoIndicador);
    public List<SerieAnoAtrib> getSeries();
    public void setSeries(List<SerieAnoAtrib> series);
}
