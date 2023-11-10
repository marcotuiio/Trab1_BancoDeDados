package Model;

public class Dados {
    private String sigla;
    private String indicador;
    private SerieAnoAtrib series;

    public Dados() {}

    public Dados(String sigla, String indicador, SerieAnoAtrib series) {
        this.sigla = sigla;
        this.indicador = indicador;
        this.series = series;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
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
