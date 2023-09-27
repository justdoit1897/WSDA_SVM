package it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.filters.user;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.CookieOps;

import java.io.IOException;

@WebFilter(filterName = "isLegitUserRequestFilter")
public class IsLegitUserRequestFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        Cookie actualPage = (Cookie) request.getAttribute("actualPage");

        if(actualPage == null){
            CookieOps.addCookie((HttpServletResponse) response, "actualPage",
                    "homeCliente.jsp", 60*60*2);

            ((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getContextPath()+"/index.jsp");
            return;
        }

        if(!actualPage.getValue().equals("homeCliente.jsp")){
            // Richiesta illegittima cliente, rimando alla sua home

            CookieOps.updateCookie((HttpServletResponse) response, actualPage, "homeCliente.jsp");

            ((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getContextPath()+"/index.jsp");
            return;
        } else {
            // Richiesta legittima, procedo a filtrare le richieste del sotto-contenuto
            IsLegitSubRequestFilter subRequestFilter = new IsLegitSubRequestFilter();

            request.setAttribute("subContent", CookieOps.getCookie((HttpServletRequest) request,
                    (HttpServletResponse) response, "subContent"));

            subRequestFilter.doFilter(request, response, chain);
        }
    }
}
