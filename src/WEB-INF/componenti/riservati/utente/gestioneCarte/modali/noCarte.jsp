<%@ page contentType="text/html;charset=UTF-8" %>

<div id="no-cards-modal" tabindex="-1" class="modal" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 id="modal-title" class="modal-title">Nessuna carta trovata</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <p id="modal-text">Spiacente, nessuna carta risulta a lei attribuita.
                Se pensa che sia un errore, contattare l'amministratore.</p>
            </div>
            <div class="modal-footer">
                <button type="button" id="modal-button"
                        class="btn btn-primary" data-dismiss="modal">OK</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $("#no-cards-modal").modal("toggle")

    $("#modal-button").click((event) => {
        event.preventDefault()

        $.ajax({
            type: 'GET',
            url: '${pageContext.request.contextPath}/RequestContentServlet',
            data: {
                content: "dashboard.jsp"
            },
            success: function (data) {
                $("#subPageContent").html(data)
            }
        })
    })
</script>
