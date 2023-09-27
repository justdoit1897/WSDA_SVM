package it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.controllers.macchina;

import it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.dbControls.InterazioniDB;
import it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model.Connessione;
import it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model.Macchina;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "CheckStatusServlet", value = "/CheckStatus")
public class CheckStatusServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Macchina richiedente = (Macchina) session.getAttribute("utente");
        InterazioniDB idb = new InterazioniDB();
        boolean stato = idb.selectStatoMacchina(richiedente.getId());
        Connessione connessione = idb.selectConnessione(richiedente.getId());

        richiedente.setStato(stato);
        richiedente.setConnessione(connessione);

        response.setContentType("text/html");
        if(!stato) {
            response.getWriter().write("Non disponibile");
        } else {
            response.getWriter().write("Disponibile");
        }
        response.getWriter().flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
