<%@ page import="utils.CookieOps" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Cookie aggiungiReport = CookieOps.getCookie(request, response, "aggiungiReport");
    String[] testiModale = new String[]{};

    if(aggiungiReport.getValue().equals("true")){
        testiModale = new String[] {"Carta aggiunta", "La carta è stata inserita correttamente.", "Chiudi"};
    } else if(aggiungiReport.getValue().equals("false")){
        testiModale = new String[] {"Carta non aggiunta", "È avvenuto un errore e non è stato possibile " +
                "aggiungere la carta. Se il problema persiste contattare l'amministratore.", "Chiudi"};
    }
    request.setAttribute("testiModale", testiModale);

%>

<div id="add-cards-modal" tabindex="-1" class="modal" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 id="modal-title" class="modal-title">${testiModale[0]}</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p id="modal-text">${testiModale[1]}</p>
            </div>
            <div class="modal-footer">
                <button type="button" id="modal-button"
                        class="btn btn-primary" data-dismiss="modal">${testiModale[2]}</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $("#add-cards-modal").modal("toggle")
</script>