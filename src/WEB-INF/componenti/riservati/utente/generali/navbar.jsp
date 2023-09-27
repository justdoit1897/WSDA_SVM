<%@ page import="it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model.Connessione" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean id="utente" type="it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model.Cliente" scope="session" />

<%
    Connessione connessione = utente.getConnessione();

    int idMacchina = connessione.getIdMacchina();
%>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a id="home" class="navbar-brand" href="#">
            <img src="${pageContext.request.contextPath}/images/Logo.svg" alt="SVM" width="45" height="36">
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a id="dashboard" class="nav-link" href="#">Home</a>
                </li>
                <% if(idMacchina == 0) {%>
                    <li class="nav-item">
                        <a id="connettiti" class="nav-link" href="#">Connettiti</a>
                    </li>
                <% } else {%>
                    <li class="nav-item">
                        <a id="disconnettiti" class="nav-link" href="#">Disconnettiti</a>
                    </li>
                <% }%>
                <li class="nav-item">
                    <a id="aggiungiCarta"
                       class="nav-link <%if(idMacchina != 0){%> disabled<% }%>"
                       href="#">Aggiungi Carta</a>
                </li>
                <% if(!utente.getCarteList().isEmpty()){%>
                    <li class="nav-item">
                        <a id="rimuoviCarta"
                           class="nav-link <%if(idMacchina != 0){%> disabled<% }%>"
                           href="#">Rimuovi Carta</a>
                    </li>
                <% }%>
                <li class="nav-item">
                    <a id="logout"
                       class="nav-link <%if(idMacchina != 0){%> disabled<% }%>"
                       href="#">Esci</a>
                </li>
            </ul>
        </div>
    </div>
</nav>