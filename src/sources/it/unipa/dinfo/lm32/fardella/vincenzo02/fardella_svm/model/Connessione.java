package it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model;

public class Connessione {
    private int idMacchina;
    private String codiceFiscaleCliente;

    public Connessione(){}

    public void setIdMacchina(int idMacchina) {
        this.idMacchina = idMacchina;
    }

    public void setCodiceFiscaleCliente(String codiceFiscaleCliente) {
        this.codiceFiscaleCliente = codiceFiscaleCliente;
    }

    public int getIdMacchina() {
        return idMacchina;
    }

    public String getCodiceFiscaleCliente() {
        return codiceFiscaleCliente;
    }
}
