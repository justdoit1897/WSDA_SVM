<%@ page import="it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model.Carta" %>
<%@ page import="java.util.List" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="utente" type="it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model.Cliente" scope="session" />

<%
    List<Carta> listaCarte  = (List<Carta>) request.getAttribute("listaCarte");
    if(!listaCarte.isEmpty()){ %>
        <h2>Le tue carte </h2>
        <div class="table-responsive-md">
            <table class="w-98 text-center my-3 mx-auto p-2">
                <tbody>
                <tr>
                    <th>Numero Carta</th>
                    <th>Data di scadenza</th>
                    <th>CVV</th>
                    <th>Saldo</th>
                </tr>
                <% for(Carta carta: listaCarte){
                    request.setAttribute("carta", carta);
                %>
                <tr>
                    <td>${carta.numero}</td>
                    <td>${carta.dataScadenza}</td>
                    <td>${carta.cvv}</td>
                    <td>&euro; ${carta.saldo}</td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
<%  } %>