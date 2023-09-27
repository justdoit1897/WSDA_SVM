package it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model;

public class Utente {
    private int id;
    private String tipoUtente;
    private Credenziali credenziali;
    private Connessione connessione;

    public Utente(){}

    public void setId(int i){
        this.id = i;
    }

    public void setTipoUtente(String t){
        this.tipoUtente = t;
    }

    public void setCredenziali(Credenziali credenziali){
        this.credenziali = credenziali;
    }

    public void setConnessione(Connessione connessione) {
        this.connessione = connessione;
    }

    public int getId() {
        return this.id;
    }

    public String getTipoUtente() {
        return this.tipoUtente;
    }

    public Credenziali getCredenziali() {
        return credenziali;
    }

    public Connessione getConnessione() {
        return connessione;
    }
}
