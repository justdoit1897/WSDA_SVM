<%@ page import="utils.CookieOps" %><%
    Cookie rimuoviReport = CookieOps.getCookie(request, response, "rimuoviReport");
    String[] testiModale = new String[]{};

    if(rimuoviReport.getValue().equals("true")){
        testiModale = new String[] {"Carta Rimossa", "La carta &egrave; stata rimossa correttamente.", "Chiudi"};
    } else if(rimuoviReport.getValue().equals("false")){
        testiModale = new String[] {"Carta non rimossa", "&Egrave; avvenuto un errore e non &egrave; stato possibile " +
                "rimuovere la carta. Se il problema persiste contattare l'amministratore.", "Chiudi"};
    }
    request.setAttribute("testiModale", testiModale);
%>

<div id="remove-cards-modal" tabindex="-1" class="modal" role="dialog">
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
    $("#remove-cards-modal").modal("toggle")
</script>
