package org.example;

public class Pais {
    private String id;
    private String nome;
    private PibTotal pib;

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
        return pib;
    }

    public void setPibTotal(PibTotal pib) {
        this.pib = pib;
    }
}
