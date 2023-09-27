<%@ page import="utils.CookieOps" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%
Cookie iscrizione = CookieOps.getCookie(request, response, "iscrizione");

if(iscrizione != null){

    String[] testiModale = new String[]{};

    if(iscrizione.getValue().equals("true")){
        testiModale = new String[] {"Iscrizione avvenuta", "Congratulazioni! Adesso puoi accedere alla tua area personale",
        "Chiudi"};
    } else if (iscrizione.getValue().equals("false")){
        testiModale = new String[] {"Iscrizione non avvenuta", "Controllare i dati inseriti prima di inviare il modulo." +
                " Se il problema persiste, rivolgersi all'amministratore.", "Chiudi"};
    }
    request.setAttribute("testiModale", testiModale);
}

%>

<div id="subscription-modal" tabindex="-1" class="modal" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 id="subscription-modal-title" class="modal-title">${testiModale[0]}</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p id="subscription-modal-text">${testiModale[1]}</p>
            </div>
            <div class="modal-footer">
                <button type="button" id="subscription-modal-button"
                        class="btn btn-primary" data-dismiss="modal">${testiModale[2]}</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $("#subscription-modal").modal("toggle")
    $("#subscription-modal-button").click(() => {
        $(".modal-backdrop").remove()
        $("#subscription-modal").remove()
    })
</script>
