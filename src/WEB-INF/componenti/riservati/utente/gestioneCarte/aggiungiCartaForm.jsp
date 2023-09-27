<%@ page import="utils.CookieOps" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%
Cookie subContent = CookieOps.getCookie(request, response, "subContent");
Cookie aggiungiReport = CookieOps.getCookie(request, response, "aggiungiReport");

if(subContent != null){
    subContent.setValue("aggiungiCartaForm.jsp");
    response.addCookie(subContent);
}

%>

<jsp:useBean id="utente" type="it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model.Cliente" scope="session" />

<form method="post" action="${pageContext.request.contextPath}/AggiungiCarta">
    <h2>Aggiungi Carta</h2>
    <input type="text" name="codiceFiscale" id="codiceFiscale"
           class="form-control mb-3" value="${utente.codiceFiscale}" hidden required>
    <div class="row mb-1">
        <div class="col-md-12">
            <input type="text" name="numeroCarta" id="numeroCarta" class="form-control mb-3"
                   pattern="^[1-9]{16}$" placeholder="Numero" required>
        </div>
    </div>
    <div class="row mb-1">
        <div class="col-md-12">
            <input type="month" name="dataDiScadenza" id="dataDiScadenza" class="form-control mb-3" min="2022-10" required>
        </div>
    </div>
    <div class="row mb-1">
        <div class="col-md-12">
            <input type="text" name="cvv" id="cvv" class="form-control mb-3"
                   pattern="[1-9]{3}" placeholder="CVV" required>
        </div>
    </div>
    <div class="row mb-1">
        <div class="col-md-6">
            <button type="submit" class="btn btn-primary btn-lg btn-block w-100 mb-1">Aggiungi</button>
        </div>
        <div class="col-md-6">
            <button type="reset" class="btn btn-secondary btn-lg btn-block w-100">Annulla</button>
        </div>
    </div>
</form>

<% if(aggiungiReport!=null){%>
    <jsp:include page="modali/modaleAggiungiCarta.jsp" />
<%}%>
