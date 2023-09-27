package it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "RequestContentServlet", value = "/RequestContentServlet")
public class RequestContentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String baseURL = "/WEB-INF/componenti",
                urlRiservataClienti = baseURL+"/riservati/utente/",
                urlRiservataMacchine = baseURL+"/riservati/macchina/";
        String content = request.getParameter("content");

        switch(content){
            case "pagamentoCarta.jsp":
            case "pagamentoContanti.jsp":
                request.getRequestDispatcher(urlRiservataMacchine+"metodiPagamento/"+content)
                        .forward(request, response);
                break;
            case "connessioneMacchina.jsp":
                request.getRequestDispatcher(urlRiservataClienti+"/connessioneMacchine/"+content)
                        .forward(request, response);
                break;
            case "rimuoviCartaForm.jsp":
            case "aggiungiCartaForm.jsp":
                request.getRequestDispatcher(urlRiservataClienti+"gestioneCarte/"+content)
                        .forward(request, response);
                break;
            case "dashboard.jsp":
            case "homeCliente.jsp":
                request.getRequestDispatcher(urlRiservataClienti+content).forward(request, response);
                break;
            case "homeMacchina.jsp":
                request.getRequestDispatcher(urlRiservataMacchine+"homeMacchina.jsp").forward(request, response);
                break;
            case "loginForm.jsp":
            case "subscribeForm.jsp":
                request.getRequestDispatcher(baseURL+"/pubblici/"+content).forward(request,response);
                break;

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
