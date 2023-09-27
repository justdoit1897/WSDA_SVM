package it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.filters;

import it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.filters.macchina.IsLegitMacchinaRequestFilter;
import it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.filters.user.IsLegitUserRequestFilter;
import it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model.Utente;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utils.CookieOps;

import java.io.IOException;

@WebFilter(filterName = "UserLoggedFilter", urlPatterns = "/*",
dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.INCLUDE, DispatcherType.FORWARD})
public class UserLoggedFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpSession session = ((HttpServletRequest) request).getSession(false);

        if(session == null || session.getAttribute("utente") == null){
            // Non c'è una sessione, manda il controllo a un altro filtro per vedere se la richiesta è legittima

            IsLegitVisitorRequestFilter visitorRequestFilter = new IsLegitVisitorRequestFilter();

            visitorRequestFilter.doFilter(request, response, chain);

        } else {
            /* C'è una sessione, manda il controllo a un altro filtro specifico per il tipo di utente
            per vedere se la richiesta è legittima*/

            Utente utente = (Utente) session.getAttribute("utente");

            request.setAttribute("actualPage", CookieOps.getCookie((HttpServletRequest) request,
                    (HttpServletResponse) response, "actualPage"));

            if(utente.getTipoUtente().equals("macchina")){
                IsLegitMacchinaRequestFilter macchinaRequestFilter = new IsLegitMacchinaRequestFilter();

                macchinaRequestFilter.doFilter(request, response, chain);

            } else if(utente.getTipoUtente().equals("cliente")) {
                IsLegitUserRequestFilter userRequestFilter = new IsLegitUserRequestFilter();

                userRequestFilter.doFilter(request, response, chain);

            }


        }
    }
}
