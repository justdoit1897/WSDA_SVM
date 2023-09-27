package it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.controllers.cliente.gestioneCarte;

import it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.dbControls.InterazioniDB;
import it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model.Carta;
import it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model.Cliente;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "ServletAggiungiCarta", value = "/AggiungiCarta")
public class ServletAggiungiCarta extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codiceFiscale=request.getParameter("codiceFiscale"),
                numero=request.getParameter("numeroCarta"),
                dataString = request.getParameter("dataDiScadenza"),
                cvv=request.getParameter("cvv");
        DecimalFormat df = new DecimalFormat("##,##");
        df.setRoundingMode(RoundingMode.CEILING);
        double saldo = Double.parseDouble(df.format(Math.random()*100));

        //Validazione dell'input Server-Side
        Pattern patternNumeroCarta = Pattern.compile("^[1-9]{16}$"),
                patterCVV = Pattern.compile("^[1-9]{3}$");
        Matcher numeroCartaMatcher = patternNumeroCarta.matcher(numero),
                cvvMatcher = patterCVV.matcher(cvv);
        Boolean isValidNumeroCarta = numeroCartaMatcher.find(),
                isValidCVV = cvvMatcher.find(),
                isValidInput = isValidNumeroCarta && isValidCVV;

        InterazioniDB idb = new InterazioniDB();

        Cookie aggiungiReport;

        if(isValidInput && idb.aggiungiCarta(saldo, codiceFiscale, numero, dataString, cvv)){
            aggiungiReport = new Cookie("aggiungiReport", "true");

            Cliente cliente = (Cliente) request.getSession(false).getAttribute("utente");

            Carta nuovaCarta = new Carta();
            nuovaCarta.setCodiceFiscale(codiceFiscale);
            nuovaCarta.setNumero(numero);
            try {
                nuovaCarta.setDataScadenza(idb.stringa2SQLDate(dataString+"-01"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            nuovaCarta.setCvv(cvv);
            nuovaCarta.setSaldo(saldo);

            cliente.getCarteList().add(nuovaCarta);
        } else {
            aggiungiReport = new Cookie("aggiungiReport", "false");
        }
        aggiungiReport.setMaxAge(1);
        response.addCookie(aggiungiReport);

        response.sendRedirect(request.getContextPath()+"/index.jsp");

    }
}
