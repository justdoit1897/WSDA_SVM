<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model.Prodotto" %>
<jsp:useBean id="utente" type="it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model.Macchina" scope="session" />

<form id="form-ordine" class="w-100 mx-0 my-1 p-0 custom-form">
    <% if(!utente.getListaProdotti().isEmpty()){ %>
        <table class="w-98 text-center my-3 mx-auto p-2">
        <tbody>
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Prezzo</th>
            <th></th>
        </tr>
        <% for(Prodotto prodotto: utente.getListaProdotti()){
            request.setAttribute("prodotto", prodotto); %>
        <tr>
            <td>${prodotto.id}</td>
            <td>${prodotto.nome}</td>
            <td>&euro; ${prodotto.prezzo}</td>
            <td>
                <input type="radio" value="${prodotto.id}" id="${prodotto.id}"
                       class="form-check-input custom-check" name="prodotto" required>
                <input type="text" value="${prodotto.prezzo}" name="${prodotto.id}" hidden>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <% } %>
    <% if(!utente.getStato()){ %>
        <div class="row p-2">
            <div class="col-md-6">
                <p class="h3">Paga con:</p>
                <div class="form-check">
                    <input type="radio" name="metodoPagamento" id="contanti" value="contanti" class="form-check-input" required>
                    <label class="form-check-label" for="contanti">Contanti</label>
                </div>
                <div class="form-check">
                    <input type="radio" name="metodoPagamento" id="carta" value="carta" class="form-check-input" required>
                    <label class="form-check-label" for="carta">Carta</label>
                </div>
            </div>
            <div id="payment-method" class="col-md-6"></div>
        </div>

        <div class="row text-center p-2">
            <div class="col-md-6">
                <button type="submit" id="ordina" class="btn btn-success btn-lg w-100 mb-1">Ordina</button>
            </div>
            <div class="col-md-6">
                <button type="reset" id="reset-button" class="btn btn-secondary btn-lg w-100">Annulla</button>
            </div>
        </div>
    <% } %>
</form>

<script type="text/javascript">
    $("input[name=metodoPagamento]").change(()=>{
        if($("#contanti").is(":checked")){
            requestContent($("#payment-method"), "pagamentoContanti.jsp")
        } else if($("#carta").is(":checked")){
            requestContent($("#payment-method"), "pagamentoCarta.jsp")
        }
    })

    $("#form-ordine").submit((ev) => {
        ev.preventDefault()
        ev.stopPropagation()

        let productID = $("input[type=radio][name=prodotto]:checked").val().toString()
        let today = new Date()
        let date = today.getFullYear()+"-"+(today.getMonth()+1)+"-"+today.getDate()

        if($("#contanti").is(":checked")){
            // Ulteriore validazione client side solo per le transazioni in contanti

            if($("input[hidden][name="+productID+"]").val() <= $("#cifraAttuale").val()){

                if($("#ca").hasClass("is-invalid")){
                    $("#ca").toggleClass("is-invalid")
                }
                $("#ca").toggleClass("is-valid")

                if($("#amountFeedback").hasClass("invalid-feedback")){
                    $("#amountFeedback").toggleClass("invalid-feedback")
                    $("#amountFeedback").toggleClass("valid-feedback")
                    $("#amountFeedback").text("OK")
                }

                $.ajax({
                    type: 'POST',
                    url: '${pageContext.request.contextPath}/Pagamento',
                    data: {
                        metodo: $("input[type=radio][name='metodoPagamento']:checked").val(),
                        cliente: $("input[name='cliente']").val(),
                        prodotto: $("input[name='prodotto']:checked").attr("id"),
                        prezzo: $("input[hidden][name="+$("input[name='prodotto']:checked").attr("id")+"]").val(),
                        cifraInserita: $("#cifraAttuale").val(),
                        dataAcquisto: date
                    },
                    success: function (data) {
                        let object = JSON.parse(data)

                        $("#ca").toggleClass("is-valid")
                        $("#form-ordine").get(0).reset()

                        $("#modal-title").html(object["titolo"])
                        $("#modal-text").html(object["corpo"])
                        $("#modal-button").html("OK")

                        $("#machine-modal").modal("toggle")
                    },
                })

            } else {

                $("#ca").toggleClass("is-invalid")

                $("#amountFeedback").toggleClass("invalid-feedback")
                $("#amountFeedback").text("Inserire altro credito")
            }
        } else if($("#carta").is(":checked")){

            $.ajax({
                type: 'POST',
                url: '${pageContext.request.contextPath}/Pagamento',
                data: {
                    metodo: $("input[type=radio][name='metodoPagamento']:checked").val(),
                    cliente: $("input[name='cliente']").val(),
                    carta: $("select[name='carte']").val(),
                    prodotto: $("input[name='prodotto']:checked").attr("id"),
                    prezzo: $("input[hidden][name="+$("input[name='prodotto']:checked").attr("id")+"]").val(),
                    dataAcquisto: date

                },
                success: function (data) {
                    let object = JSON.parse(data)

                    $("#form-ordine").get(0).reset()

                    $("#modal-title").html(object["titolo"])
                    $("#modal-text").html(object["corpo"])
                    $("#modal-button").html("OK")

                    $("#machine-modal").modal("toggle")
                }
            })
        }
    })
</script>