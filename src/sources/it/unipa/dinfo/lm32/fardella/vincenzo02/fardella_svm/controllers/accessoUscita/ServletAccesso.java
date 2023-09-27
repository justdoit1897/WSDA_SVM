package it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.controllers.accessoUscita;

import it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.dbControls.InterazioniDB;
import it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model.Utente;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import utils.CookieOps;
import utils.SHA256;

import java.io.IOException;

@WebServlet(name = "ServletAccesso", value = "/login")
public class ServletAccesso extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.sendRedirect(request.getContextPath()+"/index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("indirizzo"),
               password = request.getParameter("password");

        InterazioniDB idb = new InterazioniDB();
        Cookie loginReport;

        Utente utente = idb.accedi(email, SHA256.SHA256Hash(password));

        if(utente != null){
            HttpSession session = request.getSession();
            Cookie actualPage = CookieOps.getCookie(request, response, "actualPage");

            loginReport = new Cookie("login", "ok");

            session.setAttribute("utente", utente);
            session.setAttribute("tipoUtente", utente.getTipoUtente());

            if(utente.getTipoUtente().equals("cliente")){
                actualPage.setValue("homeCliente.jsp");
            } else if (utente.getTipoUtente().equals("macchina")){
                actualPage.setValue("homeMacchina.jsp");
            }

            response.addCookie(actualPage);
        } else {
            loginReport = new Cookie("login", "fail");
        }
        loginReport.setMaxAge(1);

        response.addCookie(loginReport);
        response.sendRedirect(request.getContextPath()+"/index.jsp");
    }
}
