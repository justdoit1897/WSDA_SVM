package it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.controllers.cliente;

import it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.dbControls.InterazioniDB;
import it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model.Cliente;
import it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model.Connessione;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "CheckConnessioniServlet", value = "/CheckConnessioni")
public class CheckConnessioniServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Cliente richiedente = (Cliente) session.getAttribute("utente");
        InterazioniDB idb = new InterazioniDB();
        Connessione connessioneAggiornata = idb.selectConnessione(richiedente.getCodiceFiscale());
        String responseText;

        if(richiedente.getConnessione().getIdMacchina() != connessioneAggiornata.getIdMacchina()){
            richiedente.setConnessione(connessioneAggiornata);
            responseText = "{" +
                    "\"azione\": \"ricarica\"" +
                    "}";
        } else {
            responseText = "{" +
                    "\"azione\": \"niente\"" +
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
