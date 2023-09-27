package it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model;

public class Prodotto {
    private int id;
    private String nome;
    private double prezzo;

    public Prodotto(){}

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getPrezzo() {
        return prezzo;
    }
}
