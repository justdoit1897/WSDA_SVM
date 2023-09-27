package it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.filters.user;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.CookieOps;

import java.io.IOException;

@WebFilter(filterName = "IsLegitSubRequestFilter")
public class IsLegitSubRequestFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        Cookie subContent = (Cookie) request.getAttribute("subContent");

        if(subContent == null){
            CookieOps.addCookie((HttpServletResponse) response, "subContent",
                    "dashboard.jsp", 60*60*2);

            ((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getContextPath()+"/index.jsp");

            return;
        }

        if(!isPossibleSub(subContent.getValue())){
            // Richiesta di elementi non accettabili, ricarica la pagina di riepilogo
            CookieOps.updateCookie((HttpServletResponse) response, subContent, "dashboard.jsp");

            ((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getContextPath()+"/index.jsp");

            return;
        }
        // Richiesta di elementi ammissibili, può procedere

        chain.doFilter(request, response);
    }

    public boolean isPossibleSub(String candidate){
        String[] possibleSubs = new String[]{"dashboard.jsp", "aggiungiCartaForm.jsp",
                "rimuoviCartaForm.jsp", "connessioneMacchina.jsp"};
        for(String sub: possibleSubs){
            if(candidate.equals(sub)){
                return true;
            }
        }
        return false;
    }
}
