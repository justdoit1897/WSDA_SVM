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

@WebServlet(name = "DisconnessioneMServlet", value = "/DisconnessioneMacchina")
public class DisconnessioneMServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InterazioniDB idb = new InterazioniDB();
        Cliente cliente = (Cliente) request.getSession(false).getAttribute("utente");
        String responseText;

        if(idb.resetConnessione(cliente.getConnessione().getIdMacchina())){

            Connessione connResetCliente = new Connessione();

            connResetCliente.setCodiceFiscaleCliente(cliente.getCodiceFiscale());
            connResetCliente.setIdMacchina(0);

            cliente.setConnessione(connResetCliente);

            responseText = "{" +
                    "\"esito\": \"successo\"," +
                    "\"titolo\": \"Disconnessione avvenuta\"," +
                    "\"corpo\": \"Ti sei disconnesso dalla macchina.\"" +
                    "}";

        } else {
            responseText = "{" +
                    "\"esito\": \"fallimento\"," +
                    "\"titolo\": \"Disconnessione fallita\"," +
                    "\"corpo\": \"È avvenuto un errore e non è stato possibile" +
                    "disconnetterti dalla macchina. Se il problema persiste contattare l'amministratore.\"" +
                    "}";
        }

        response.setContentType("text/plain");
        response.getWriter().write(responseText);
        response.getWriter().flush();

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
