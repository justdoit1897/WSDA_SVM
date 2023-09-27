package it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model;

import java.util.Date;
import java.util.List;

public class Cliente extends Utente{
    private String nome, cognome, codiceFiscale;
    private Date dataDiNascita;
    private List<Carta> carteList;

    public Cliente(){
        super();
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setDataDiNascita(Date dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public void setCarteList(List<Carta> carteList) {
        this.carteList = carteList;
    }

    public String getNome() {
        return this.nome;
    }

    public String getCognome() {
        return this.cognome;
    }

    public Date getDataDiNascita() {
        return this.dataDiNascita;
    }

    public String getCodiceFiscale() {
        return this.codiceFiscale;
    }


    public List<Carta> getCarteList() {
        return carteList;
    }
}
