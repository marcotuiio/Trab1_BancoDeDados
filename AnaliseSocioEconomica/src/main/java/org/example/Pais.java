package org.example;

public class Pais {
    private String id;
    private String nome;
    private PibTotal pibTotal;
    private PibPerCapita pibPerCapita;
    private TotalExportacoes totalExportacoes;
    private TotalImportacoes totalImportacoes;
    private InvestimentoPD investimentoPD;
    private IndividuosAI individuosAI;
    private Idh idh;

    public Pais(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public PibTotal getPibTotal() {
        return pibTotal;
    }

    public void setPibTotal(PibTotal pibTotal) {
        this.pibTotal = pibTotal;
    }

    public PibPerCapita getPibPerCapita() {
        return pibPerCapita;
    }

    public void setPibPerCapita(PibPerCapita pibPerCapita) {
        this.pibPerCapita = pibPerCapita;
    }

    public TotalExportacoes getTotalExportacoes() {
        return totalExportacoes;
    }

    public void setTotalExportacoes(TotalExportacoes totalExportacoes) {
        this.totalExportacoes = totalExportacoes;
    }

    public TotalImportacoes getTotalImportacoes() {
        return totalImportacoes;
    }

    public void setTotalImportacoes(TotalImportacoes totalImportacoes) {
        this.totalImportacoes = totalImportacoes;
    }

    public InvestimentoPD getInvestimentoPD() {
        return investimentoPD;
    }

    public void setInvestimentoPD(InvestimentoPD investimentoPD) {
        this.investimentoPD = investimentoPD;
    }

    public IndividuosAI getIndividuosAI() {
        return individuosAI;
    }

    public void setIndividuosAI(IndividuosAI individuosAI) {
        this.individuosAI = individuosAI;
    }

    public Idh getIdh() {
        return idh;
    }

    public void setIdh(Idh idh) {
        this.idh = idh;
    }
}
