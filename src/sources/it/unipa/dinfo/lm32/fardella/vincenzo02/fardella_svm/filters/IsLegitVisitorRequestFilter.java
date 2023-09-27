package it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.CookieOps;

import java.io.IOException;

@WebFilter(filterName = "IsLegitVisitorRequestFilter",
        dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.INCLUDE, DispatcherType.FORWARD})
public class IsLegitVisitorRequestFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {


        if (!CookieOps.areThereCookies((HttpServletRequest) request)) {
            CookieOps.addCookie((HttpServletResponse) response, "actualPage",
                    "loginForm.jsp", 60 * 60 * 2);

            ((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getContextPath()+"/index.jsp");
            return;
        }

        Cookie actualPage = CookieOps.getCookie((HttpServletRequest) request,
                (HttpServletResponse) response, "actualPage");

        if(!(actualPage.getValue().equals("loginForm.jsp") || actualPage.getValue().equals("subscribeForm.jsp"))){
            // Richiesta illegale per un visitatore, rinvio al login

            CookieOps.updateCookie((HttpServletResponse) response, actualPage, "loginForm.jsp");

            ((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getContextPath()+"/index.jsp");

            return;
        } else {
            // Richiesta legittima per un visitatore

            chain.doFilter(request, response);
        }
    }
}
