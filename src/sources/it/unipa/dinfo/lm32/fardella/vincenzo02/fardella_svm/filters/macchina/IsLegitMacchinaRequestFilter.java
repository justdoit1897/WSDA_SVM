package it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.filters.macchina;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.CookieOps;

import java.io.IOException;

@WebFilter(filterName = "IsLegitMacchinaRequestFilter",
        dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.INCLUDE, DispatcherType.FORWARD})
public class IsLegitMacchinaRequestFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        Cookie actualPage = (Cookie) request.getAttribute("actualPage");

        if(!actualPage.getValue().equals("homeMacchina.jsp")){
            // Richiesta illegittima macchina, rimando alla sua home

            CookieOps.updateCookie((HttpServletResponse) response, actualPage, "homeMacchina.jsp");

            ((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getContextPath()+"/index.jsp");
        } else {
            // Richiesta legittima macchina, procedi

            chain.doFilter(request, response);
        }
    }
}
