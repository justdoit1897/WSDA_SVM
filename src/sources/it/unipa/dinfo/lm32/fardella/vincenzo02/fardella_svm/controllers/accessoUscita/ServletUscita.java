package it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.controllers.accessoUscita;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import utils.CookieOps;

import java.io.IOException;

@WebServlet(name = "ServletUscita", value = "/LogoutServlet")
public class ServletUscita extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        session.invalidate();

        response.sendRedirect(request.getContextPath()+"/index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
