package it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model;

import java.util.List;

public class Macchina extends Utente{
    private String indirizzo;
    private boolean stato;
    private List<Prodotto> listaProdotti;

    public Macchina(){}

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public void setStato(boolean stato) {
        this.stato = stato;
    }

    public void setListaProdotti(List<Prodotto> listaProdotti) {
        this.listaProdotti = listaProdotti;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public boolean getStato() {
        return stato;
    }

    public List<Prodotto> getListaProdotti() {
        return listaProdotti;
    }

}
