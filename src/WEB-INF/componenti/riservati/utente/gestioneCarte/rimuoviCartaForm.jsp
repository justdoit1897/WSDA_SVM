<%@ page import="it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model.Carta" %>
<%@ page import="utils.CookieOps" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="utente" type="it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model.Cliente" scope="session" />

<%
    Cookie subContent = CookieOps.getCookie(request, response, "subContent");
    Cookie rimuoviReport = CookieOps.getCookie(request, response, "rimuoviReport");

    if(subContent != null){
        subContent.setValue("rimuoviCartaForm.jsp");
        response.addCookie(subContent);
    }

%>

<% if(!utente.getCarteList().isEmpty()) { %>
    <form method="post" action="${pageContext.request.contextPath}/RimuoviCarta">
        <h2>Rimuovi Carta</h2>
        <input type="text" name="codiceFiscale" id="codiceFiscale"
               class="form-control mb-3" value="${utente.codiceFiscale}" hidden required>
        <div class="row mb-1">
            <div class="col-md-12">
                <div>
                    <label class="form-label">Seleziona la carta:</label>
                    <select name="carte" id="carte" class="form-control">
                        <%      for(Carta carta: utente.getCarteList()){
                            request.setAttribute("carta", carta);
                        %>
                        <option value="${carta.numero}">${carta.numero}</option>
                        <% } %>
                    </select>
                </div>
            </div>
        </div>
        <div class="row mb-1">
            <div class="col-md-12">
                <div>
                    <label for="password" class="form-label">Inserisci la password per sicurezza:</label>
                    <input type="password" name="password" id="password" class="form-control mb-3" placeholder="Password" required>
                </div>
            </div>
        </div>
        <div class="row mb-1">
            <div class="col-md-6">
                <button type="submit" class="btn btn-primary btn-lg btn-block w-100 mb-1">Rimuovi</button>
            </div>
            <div class="col-md-6">
                <button type="reset" class="btn btn-secondary btn-lg btn-block w-100">Annulla</button>
            </div>
        </div>
    </form>
    <% if(rimuoviReport != null) {%>
        <jsp:include page="modali/modaleRimuoviCarta.jsp" />
    <% }%>

<% } else { %>
    <jsp:include page="modali/noCarte.jsp" />
<% } %>

