<%@ page import="it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model.Carta" %>
<%@ page import="it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.dbControls.InterazioniDB" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="utente" type="it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model.Macchina" scope="session" />

<%
InterazioniDB idb = new InterazioniDB();
List<Carta> listaCarteUtente = idb.selectCarteUtente(utente.getConnessione().getCodiceFiscaleCliente());
if(!(listaCarteUtente == null || listaCarteUtente.isEmpty())) { %>
    <div class="mb-3">
        <label class="form-label" for="carte">Seleziona la carta:</label>
        <select name="carte" id="carte" class="form-control">
            <%      for(Carta carta: listaCarteUtente){
                request.setAttribute("carta", carta);
            %>
            <option value="${carta.numero}">${carta.numero}</option>
            <% } %>
        </select>
        <input value="${utente.connessione.codiceFiscaleCliente}" name="cliente" hidden>
    </div>
<% } else { %>
    <p>Non disponibile, si prega di cambiare metodo di pagamento</p>
    <script type="text/javascript">
        $("button[type=submit]").attr("disabled", "disabled")
    </script>
<% } %>
