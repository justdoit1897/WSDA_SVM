package it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model;

public class Credenziali {
    private String email, password;

    public Credenziali(){}

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
