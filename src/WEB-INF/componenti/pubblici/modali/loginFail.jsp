<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="login-fail" tabindex="-1" class="modal" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Accesso non riuscito</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p>Controlla i dati inseriti e riprova l'accesso. Se il problema dovesse persistere
                    contattare l'amministratore</p>
            </div>
            <div class="modal-footer">
                <button type="button" id="login-fail-close" class="btn btn-primary" data-dismiss="modal">Chiudi</button>
            </div>
        </div>
    </div>
    <script type="text/javascript">

        $("#login-fail").modal("toggle")

        $("#login-fail-close").click(() => {
            $(".modal-backdrop").remove()
            $("#login-fail").remove()
        })
    </script>
</div>
