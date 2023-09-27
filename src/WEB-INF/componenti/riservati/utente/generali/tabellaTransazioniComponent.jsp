<%@ page import="it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model.Transazione" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:useBean id="utente" type="it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model.Cliente" scope="session" />

<%
    List<Transazione> listaTransazioni = (List<Transazione>) request.getAttribute("listaTransazioni");
    if(!listaTransazioni.isEmpty()){%>
        <h2>Ecco le tue ultime transazioni</h2>
        <div class="table-responsive-md">
            <table class="w-98 text-center my-3 mx-auto p-2">
                <tbody>
                <tr>
                    <th>ID</th>
                    <th>Data</th>
                    <th>Macchinetta</th>
                    <th>Prodotto</th>
                    <th>Spesa</th>
                </tr>
                <%  for(Transazione transazione: listaTransazioni){
                    request.setAttribute("transazione", transazione);
                %>
                <tr>
                    <td>${transazione.id}</td>
                    <td>${transazione.data}</td>
                    <td>#${transazione.idMacchina}</td>
                    <td>#${transazione.idProdotto}</td>
                    <td>&euro; ${transazione.prezzo}</td>
                </tr>
                <% }%>
                </tbody>
            </table>
        </div>
<%  }%>
