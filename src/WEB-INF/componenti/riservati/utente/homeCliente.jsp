<%@ page import="utils.CookieOps" %>
<%@ page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="utente" type="it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model.Cliente" scope="session" />

<%
    Cookie actualPage = CookieOps.getCookie(request, response, "actualPage");
    Cookie subContent = CookieOps.getCookie(request, response, "subContent");

    if(actualPage == null && session.getAttribute("utente") != null) {
        CookieOps.addCookie(response, "actualPage", "homeCliente.jsp", 60*60*2);
    } else if(!actualPage.getValue().equals("homeCliente.jsp")){
        CookieOps.updateCookie(response, CookieOps.getCookie(request, response, "actualPage"), "homeCliente.jsp");
    }

    if(subContent == null){
        CookieOps.addCookie(response, "subContent", "dashboard.jsp", 60*60*2);
    }

%>

<jsp:include page="generali/navbar.jsp" />

<div id="subPageContent" class="container-md w-100 p-2"></div>

<div id="customer-modal" tabindex="-1" class="modal" role="dialog">
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
                        class="btn btn-primary" data-bs-dismiss="modal">Chiudi</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(() => {

        $("#home").click((ev) => {
            customClick(ev, $("#subPageContent"), "dashboard.jsp")
        })

        $("#dashboard").click((ev) => {
            customClick(ev, $("#subPageContent"), "dashboard.jsp")
        })

        $("#aggiungiCarta").click((ev) => {
            customClick(ev, $("#subPageContent"), "aggiungiCartaForm.jsp")
        })

        $("#rimuoviCarta").click((ev) => {
            customClick(ev, $("#subPageContent"), "rimuoviCartaForm.jsp")
        })

        $("#connettiti").click((ev) => {
            customClick(ev, $("#subPageContent"), "connessioneMacchina.jsp")
        })

        $("#disconnettiti").click((ev) => {
            callDisconnessioneLatoUtente(ev)
        })

        $("#logout").click((ev) => {
            callLogout(ev)
        })

        $.ajax({
            type: 'GET',
            url: './RequestContentServlet',
            data: {
                content: getCookie("subContent")
            },
            success: function (data){
                $("#subPageContent").html(data)
            }
        })

    })
</script>
