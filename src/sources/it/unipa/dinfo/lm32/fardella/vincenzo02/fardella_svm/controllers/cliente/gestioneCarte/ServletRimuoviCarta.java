package it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.controllers.cliente.gestioneCarte;

import it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.dbControls.InterazioniDB;
import it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model.Carta;
import it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model.Cliente;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import utils.SHA256;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ServletRimuoviCarta", value = "/RimuoviCarta")
public class ServletRimuoviCarta extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath()+"/index.html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cliente cliente = (Cliente) request.getSession(false).getAttribute("utente");
        List<Carta> list = cliente.getCarteList();
        InterazioniDB idb = new InterazioniDB();

        Cookie rimuoviReport;

        if(SHA256.SHA256Hash(request.getParameter("password")).equals(cliente.getCredenziali().getPassword()) &&
                idb.rimuoviCarta(request.getParameter("codiceFiscale"), request.getParameter("carte"))){
            rimuoviReport = new Cookie("rimuoviReport", "true");

            for(Carta carta: list){
                if(carta.getNumero().equals(request.getParameter("carte"))){
                    list.remove(carta);
                }
            }
        } else {
            rimuoviReport = new Cookie("rimuoviReport", "false");
        }
        rimuoviReport.setMaxAge(1);
        response.addCookie(rimuoviReport);

        response.sendRedirect(request.getContextPath()+"/index.jsp");

    }
}
