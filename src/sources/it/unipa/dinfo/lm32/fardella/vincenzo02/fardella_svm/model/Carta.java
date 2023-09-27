package it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model;

import java.util.Date;

public class Carta {
    private String codiceFiscale, numero, cvv;
    private Date dataScadenza;
    private double saldo;

    public Carta(){}

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setDataScadenza(Date dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public String getNumero() {
        return numero;
    }

    public Date getDataScadenza() {
        return dataScadenza;
    }

    public String getCvv() {
        return cvv;
    }

    public double getSaldo() {
        return saldo;
    }
}
