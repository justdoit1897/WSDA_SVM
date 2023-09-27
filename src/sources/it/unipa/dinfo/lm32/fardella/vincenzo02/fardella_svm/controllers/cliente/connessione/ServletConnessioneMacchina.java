package it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.controllers.cliente.connessione;

import it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.dbControls.InterazioniDB;
import it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model.Cliente;
import it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model.Connessione;
import it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model.Macchina;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import utils.CookieOps;

import java.io.IOException;

@WebServlet(name = "ServletConnessioneMacchina", value = "/ConnessioneMacchina")
public class ServletConnessioneMacchina extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InterazioniDB idb = new InterazioniDB();

        // Recupero l'ID della macchina dal form
        int idMacchina = Integer.parseInt(request.getParameter("macchina"));

        // Il cliente che ha lanciato la richiesta è nelle variabili di sessione
        Cliente cliente = (Cliente) request.getSession(false).getAttribute("utente");

        String responseText;

        if(idb.setConnessione(idMacchina, cliente.getCodiceFiscale())){
            // La transazione ha avuto successo, cambio lo stato dei due oggetti
            Connessione connessione = new Connessione();

            connessione.setIdMacchina(idMacchina);
            connessione.setCodiceFiscaleCliente(cliente.getCodiceFiscale());

            cliente.setConnessione(connessione);

            responseText = "{" +
                    "\"esito\": \"successo\"," +
                    "\"titolo\": \"Connessione Riuscita\"," +
                    "\"corpo\": \"Ti sei connesso alla macchina scelta.\"" +
                    "}";

            CookieOps.updateCookie(response, CookieOps.getCookie(request, response, "subContent"),
                    "dashboard.jsp");


        } else {
            responseText = "{" +
                    "\"esito\": \"fallimento\"," +
                    "\"titolo\": \"Connessione non riuscita\"," +
                    "\"corpo\": \"È avvenuto un errore e non è stato possibile " +
                    "connettersi alla macchina scelta. Se il problema persiste contattare l'amministratore.\"" +
                    "}";
        }

        response.setContentType("text/plain");
        response.getWriter().write(responseText);
        response.getWriter().flush();

    }
}
