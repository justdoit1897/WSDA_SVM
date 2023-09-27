<%@ page import="utils.CookieOps" %>
<%@ page import="it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model.Macchina" %>
<%@ page import="java.util.List" %>
<%@ page import="it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.dbControls.InterazioniDB" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean id="utente" type="it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model.Cliente"
             scope="session" />
<%
    Cookie subContent = CookieOps.getCookie(request, response, "subContent");
    if(subContent != null){
        subContent.setValue("connessioneMacchina.jsp");
        response.addCookie(subContent);
    }

    InterazioniDB idb = new InterazioniDB();
    List<Macchina> macchineLibere = idb.selectMacchineLibere();

%>
<% if(!macchineLibere.isEmpty()) { %>
    <form id="form-connessione-macchina" class="w-50 custom-form" method="post">
        <div class="row mb-3">
            <label class="form-label" for="listaMacchine">Seleziona la macchina a cui connettersi:</label>
            <select name="listaMacchine" id="listaMacchine" class="form-control">
                <%      for(Macchina macchina: macchineLibere){
                    request.setAttribute("macchina", macchina);
                %>
                <option value="${macchina.id}">${macchina.id} - ${macchina.indirizzo}</option>
                <% } %>
            </select>
        </div>
        <div class="row">
            <button type="submit" id="connettiti" class="btn btn-success w-100">Connettiti</button>
        </div>
    </form>
    <script type="text/javascript">
        $("#form-connessione-macchina").submit((ev) => {
            ev.preventDefault()
            ev.stopPropagation()

            $.ajax({
                type: 'POST',
                url: '${pageContext.request.contextPath}/ConnessioneMacchina',
                data: {
                    macchina: $("#listaMacchine").val()
                },
                success: function (data) {
                    let object = JSON.parse(data)

                    buildModaleCliente(object)
                }
            })
        })
    </script>
<% } else { %>
    <h1>Non ci sono macchine disponibili, torna più tardi.</h1>
<% } %>
