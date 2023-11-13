package org.AnaliseSocioEconomica.Model;


public class Pais {
    private String id;
    private String nome;
    private Dados pibTotal;
    private Dados pibPerCapita;
    private Dados totalExportacao;
    private Dados totalImportacao;
    private Dados investPesqDesenv;
    private Dados indivAcesNet;
    private Dados idh;
    private Dados impComInter;
    private Dados impExportacao;
    private Dados impReceitaFiscal;
    private Dados impAlfanImport;
    private Dados impRenda;

    public Pais(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean equals(String sigla) {
        if (this.id.equals(sigla)) {
            return true;
        }
        return false;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Dados getPibTotal() {
        return pibTotal;
    }

    public void setPibTotal(Dados pibTotal) {
        this.pibTotal = pibTotal;
    }

    public Dados getPibPerCapita() {
        return pibPerCapita;
    }

    public void setPibPerCapita(Dados pibPerCapita) {
        this.pibPerCapita = pibPerCapita;
    }

    public Dados getTotalExportacao() {
        return totalExportacao;
    }

    public void setTotalExportacao(Dados totalExportacao) {
        this.totalExportacao = totalExportacao;
    }

    public Dados getTotalImportacao() {
        return totalImportacao;
    }

    public void setTotalImportacao(Dados totalImportacao) {
        this.totalImportacao = totalImportacao;
    }

    public Dados getInvestPesqDesenv() {
        return investPesqDesenv;
    }

    public void setInvestPesqDesenv(Dados investPesqDesenv) {
        this.investPesqDesenv = investPesqDesenv;
    }

    public Dados getIndivAcesNet() {
        return indivAcesNet;
    }

    public void setIndivAcesNet(Dados indivAcesNet) {
        this.indivAcesNet = indivAcesNet;
    }

    public Dados getIdh() {
        return idh;
    }

    public void setIdh(Dados idh) {
        this.idh = idh;
    }

    public Dados getImpComInter() {
        return impComInter;
    }

    public void setImpComInter(Dados impComInter) {
        this.impComInter = impComInter;
    }

    public Dados getImpExportacao() {
        return impExportacao;
    }

    public void setImpExportacao(Dados impExportacao) {
        this.impExportacao = impExportacao;
    }

    public Dados getImpReceitaFiscal() {
        return impReceitaFiscal;
    }

    public void setImpReceitaFiscal(Dados impReceitaFiscal) {
        this.impReceitaFiscal = impReceitaFiscal;
    }

    public Dados getImpAlfanImport() {
        return impAlfanImport;
    }

    public void setImpAlfanImport(Dados impAlfanImport) {
        this.impAlfanImport = impAlfanImport;
    }

    public Dados getImpRenda() {
        return impRenda;
    }

    public void setImpRenda(Dados impRenda) {
        this.impRenda = impRenda;
    }
}
