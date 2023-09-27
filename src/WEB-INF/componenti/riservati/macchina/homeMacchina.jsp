<%@ page import="utils.CookieOps" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%
    Cookie actualPage = CookieOps.getCookie(request, response, "actualPage");

    if(actualPage == null) {
        CookieOps.addCookie(response, "actualPage", "homeMacchina.jsp", 60*60*2);
    } else if(!actualPage.getValue().equals("homeMacchina.jsp")){
        CookieOps.updateCookie(response, actualPage, "homeMacchina.jsp");
    }

%>

<jsp:useBean id="utente"
             type="it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model.Macchina"
             scope="session" />

<div class="container-md mx-auto px-2">
    <h1>Macchina No. ${utente.id}</h1>
    <h2 class="p">In ${utente.indirizzo}</h2>

    <p>Stato: <span id="stato" <% if(utente.getStato()){ %>
                class="green-text">Disponibile<% } else { %>class="red-text">Non disponibile<% } %></span></p>

    <jsp:include page="formOrdine.jsp" />


    <div class="row text-center p-2">
        <% if(utente.getStato()){%>
            <div class="col-md-12">
                <button type="button" id="logout1" class="btn btn-danger btn-lg w-100">Esci</button>
            </div>
        <%} else{%>
            <div class="col-md-6">
                <button type="button" id="logout2" class="btn btn-danger btn-lg w-100 mb-1">Esci</button>
            </div>
            <div class="col-md-6">
                <button type="button" id="disconnettiUtente" class="btn btn-warning btn-lg w-100">Disconnetti</button>
            </div>
        <% }%>
    </div>
</div>

<div id="machine-modal" tabindex="-1" class="modal" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 id="modal-title" class="modal-title"></h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p id="modal-text"></p>
            </div>
            <div class="modal-footer">
                <button type="button" id="modal-button"
                        class="btn btn-primary" data-bs-dismiss="modal">OK</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">

    $("button[id^='logout']").click((event) => {
        callLogout(event)
    })

    $("#disconnettiUtente").click(() => {
        disconnettiUtente()
    })

    setInterval(aggiornaStatoLatoMacchina, 5*1000, $("#stato").text())

</script>
