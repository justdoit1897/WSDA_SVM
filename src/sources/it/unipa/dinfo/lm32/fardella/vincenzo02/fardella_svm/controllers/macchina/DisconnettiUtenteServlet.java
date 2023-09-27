package it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.controllers.macchina;

import it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.dbControls.InterazioniDB;
import it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model.Cliente;
import it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model.Connessione;
import it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model.Macchina;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import utils.CookieOps;

import java.io.IOException;

@WebServlet(name = "DisconnettiUtenteServlet", value = "/DisconnettiUtente")
public class DisconnettiUtenteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InterazioniDB idb = new InterazioniDB();
        Macchina macchina = (Macchina) request.getSession(false).getAttribute("utente");

        String responseString;

        if(idb.resetConnessione(macchina.getId())){
            Connessione connResetMacchina = new Connessione();

            connResetMacchina.setCodiceFiscaleCliente("");
            connResetMacchina.setIdMacchina(macchina.getId());

            macchina.setConnessione(connResetMacchina);

            responseString = "{" +
                    "\"titolo\": \"Disconnessione avvenuta\"," +
                    "\"corpo\": \"Adesso è possibile la connessione per un altro utente.\"" +
                    "}";

        } else {
            responseString = "{" +
                    "\"titolo\": \"Impossibile disconnettersi \"," +
                    "\"corpo\": \"È successo qualcosa di imprevisto." +
                    " Se il problema persiste, contattare l'amministratore.\"" +
                    "}";
        }

        response.setContentType("text/plain");
        response.getWriter().write(responseString);
        response.getWriter().flush();

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
