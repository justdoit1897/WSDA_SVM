<%@ page import="it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model.Carta" %>
<%@ page import="utils.CookieOps" %>
<%@ page import="it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model.Transazione" %>
<%@ page import="java.util.List" %>
<%@ page import="it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.dbControls.InterazioniDB" %>
<%@ page import="it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model.Connessione" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean id="utente" type="it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model.Cliente" scope="session" />

<%
  Cookie subContent = CookieOps.getCookie(request, response, "subContent");

  if(!subContent.getValue().equals("dashboard.jsp")){
    subContent.setValue("dashboard.jsp");
    response.addCookie(subContent);
  }

  InterazioniDB idb = new InterazioniDB();
  List<Carta> listaCarte = idb.selectCarteUtente(utente.getCodiceFiscale());
  List<Transazione> listaTransazioni = idb.selectTransazioni(utente.getCodiceFiscale());
  Connessione connessione = utente.getConnessione();

  request.setAttribute("listaCarte", listaCarte);
  request.setAttribute("listaTransazioni", listaTransazioni);

%>

<h1>Benvenuto ${utente.nome} ${utente.cognome}</h1>
<div class="row mb-2 mx-0">
  <div class="col-sm-6">
    <% if(connessione.getIdMacchina() == 0) {%>
      <button id="connessioneMacchina" type="button" class="btn btn-primary w-100 mb-1">Connettiti a una macchinetta</button>
    <% } else {%>
      <button id="disconnessioneMacchina" type="button" class="btn btn-danger w-100 mb-1">Disconnettiti da una macchinetta</button>
    <% }%>
  </div>
  <div class="col-sm-6">
    <button id="aggiuntaCarta"
            type="button"
            class="btn btn-success w-100"
            <% if(connessione.getIdMacchina() != 0){%> disabled <% }%>>
      Aggiungi una Carta
    </button>
  </div>
</div>
<div class="row mb-2 mx-0">
  <div class="col-sm-6">
    <% if(!listaCarte.isEmpty()){%>
      <button id="rimozioneCarta"
              type="button"
              class="btn btn-danger w-100 mb-1"
              <% if (connessione.getIdMacchina() != 0){%> disabled<% }%>>
        Rimuovi Carta
      </button>
    <% }%>
  </div>
  <div class="col-sm-6">
    <button id="esci"
            type="button"
            class="btn btn-secondary w-100"
            <% if (connessione.getIdMacchina() != 0){%> disabled<% }%>>
      Esci
    </button>
  </div>
</div>
<jsp:include page="generali/tabellaCarteComponent.jsp" />

<jsp:include page="generali/tabellaTransazioniComponent.jsp" />

<script type="text/javascript">

$(document).ready(() => {
    $("#disconnessioneMacchina").click((event) => {
      callDisconnessioneLatoUtente(event)
    })

    $("#connessioneMacchina").click((event) => {
      customClick(event, $("#subPageContent"), "connessioneMacchina.jsp")
    })

    $("#aggiuntaCarta").click((event) => {
      customClick(event, $("#subPageContent"), "aggiungiCartaForm.jsp")
    })

    $("#rimozioneCarta").click((event) => {
      customClick(event, $("#subPageContent"), "rimuoviCartaForm.jsp")
    })

    $("#esci").click((event) => {
      callLogout(event)
    })

    setInterval(aggiornaStatoLatoUtente, 5*1000)


  })

</script>