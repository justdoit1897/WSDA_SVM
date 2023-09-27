package it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.controllers.cliente;

import it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.dbControls.InterazioniDB;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "ServletIscrizione", value = "/ServletIscrizione")
public class ServletIscrizione extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath()+"/index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Raccolta parametri form iscrizione
        String nome=request.getParameter("nome"),
               cognome=request.getParameter("cognome"),
               dataDiNascita=request.getParameter("dataDiNascita"),
               codiceFiscale=request.getParameter("codiceFiscale"),
               email=request.getParameter("indirizzoEmail"),
               password=request.getParameter("password");

        //Validazione Server Side speculare a quella client side
        Pattern patternCodiceFiscale = Pattern.compile("^[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]$"),
                patterEmail = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$");
        Matcher codiceFiscaleMatcher = patternCodiceFiscale.matcher(codiceFiscale),
                emailMatcher = patterEmail.matcher(email);
        Boolean isValidCodiceFiscale = codiceFiscaleMatcher.find(),
                isValidEmail = emailMatcher.find();

        InterazioniDB idb = new InterazioniDB();
        Cookie iscrizioneReport;
        Cookie actualPage = new Cookie("actualPage", "subscribeForm.jsp");

        if(isValidCodiceFiscale && isValidEmail &&
                idb.iscriviCliente(nome, cognome, dataDiNascita, codiceFiscale, email, password)) {

            iscrizioneReport = new Cookie("iscrizione", "true");
            iscrizioneReport.setMaxAge(1);
        } else {
            iscrizioneReport = new Cookie("iscrizione", "false");
            iscrizioneReport.setMaxAge(1);
        }
        response.addCookie(actualPage);
        response.addCookie(iscrizioneReport);
        response.sendRedirect(request.getContextPath()+"/index.jsp");
    }
}
