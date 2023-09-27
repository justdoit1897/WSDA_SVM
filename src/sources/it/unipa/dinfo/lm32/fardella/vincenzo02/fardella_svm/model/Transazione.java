package it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model;

import java.util.Date;

public class Transazione {
    private int id, idMacchina, idProdotto;
    private Date data;
    private String cliente;
    private double prezzo;

    public Transazione(){}

    public void setId(int id) {
        this.id = id;
    }

    public void setIdMacchina(int idMacchina) {
        this.idMacchina = idMacchina;
    }

    public void setIdProdotto(int idProdotto) {
        this.idProdotto = idProdotto;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public int getId() {
        return id;
    }

    public int getIdMacchina() {
        return idMacchina;
    }

    public int getIdProdotto() {
        return idProdotto;
    }

    public Date getData() {
        return data;
    }

    public String getCliente() {
        return cliente;
    }

    public double getPrezzo() {
        return prezzo;
    }
}
