<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean id="utente" type="it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model.Macchina" scope="session" />

<div class="mb-2">
    <div class="row mb-1">
        <div class="col-sm-5">
            <p>Inserisci il denaro:</p>
        </div>
        <div class="col-sm-7">
            <input type="text" id="ca" class="form-control" value="0.0" aria-describedby="amountFeedback" readonly>
            <input id="cifraAttuale" name="cifraAttuale" value="0.0" hidden>
            <div id="amountFeedback"></div>
        </div>
    </div>
    <div class="row">
        <div class="input-group col-sm-12">
            <span class="input-group-text">&euro;</span>
            <select id="monetine" name="monetine" class="form-control">
                <option value="0.1">0.10</option>
                <option value="0.2">0.20</option>
                <option value="0.5">0.50</option>
                <option value="1.00">1.00</option>
                <option value="2.00">2.00</option>
            </select>
            <button type="button" id="inserisci" class="btn btn-primary">OK</button>
        </div>
        <input value="${utente.connessione.codiceFiscaleCliente}" name="cliente" hidden>
    </div>
</div>

<script type="text/javascript">

    $("#inserisci").click((ev) => {

        let updatedValue = (parseFloat($("#cifraAttuale").val()) + parseFloat($("#monetine").val())).toFixed(2)

        $("#cifraAttuale").val(updatedValue)

        $("#ca").val($("#cifraAttuale").val())
    })

    $("#reset-button").click(() => {
        $("#cifraAttuale").val(0.0)

        $("#ca").html("0.0")
    })
</script>