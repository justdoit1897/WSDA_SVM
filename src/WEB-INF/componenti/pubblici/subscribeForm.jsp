<%@ page import="utils.CookieOps" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%

Cookie reportIscrizione = CookieOps.getCookie(request, response, "iscrizione");
Cookie actualPage = CookieOps.getCookie(request, response, "actualPage");

if(actualPage != null){
    actualPage.setValue("subscribeForm.jsp");
    response.addCookie(actualPage);
}

%>

<form class="form-subscribe" method="post" action="${pageContext.request.contextPath}/ServletIscrizione">
    <img src="${pageContext.request.contextPath}/images/Logo.svg" class="mb-3" width="96" alt="company-logo" />
    <h2>Iscriviti</h2>
    <div class="row mb-1">
        <div class="col-md-12">
            <input type="text" name="nome" id="nome" class="form-control mb-3" placeholder="Nome" required>
        </div>
    </div>
    <div class="row mb-1">
        <div class="col-md-12">
            <input type="text" name="cognome" id="cognome" class="form-control mb-3" placeholder="Cognome" required>
        </div>
    </div>
    <div class="row mb-1">
        <div class="col-md-12">
            <input type="date" name="dataDiNascita" id="dataDiNascita" class="form-control mb-3" required>
        </div>
    </div>
    <div class="row mb-1">
        <div class="col-md-12">
            <input type="text" name="codiceFiscale" id="codiceFiscale" class="form-control mb-3"
                   minlength="16" maxlength="16"
                   pattern="^[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]$"
                   placeholder="Codice Fiscale" required>
        </div>
    </div>
    <div class="row mb-1">
        <div class="col-md-12">
            <input type="email" name="indirizzoEmail" id="indirizzoEmail" class="form-control mb-3"
                   pattern="^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$"
                   placeholder="E-mail" required>
        </div>
    </div>
    <div class="row mb-1">
        <div class="col-md-12">
            <input type="password" name="password" id="password" class="form-control mb-3"
                   placeholder="Password" required>
        </div>
    </div>
    <div class="row mb-1">
        <div class="col-md-6">
            <button type="submit" class="btn btn-primary btn-lg btn-block w-100 mb-1">Iscriviti</button>
        </div>
        <div class="col-md-6">
            <button type="reset" class="btn btn-secondary btn-lg btn-block w-100">Annulla</button>
        </div>
    </div>
    <p>Sei già iscritto? <a id="link" href="#" class="text-decoration-none">Accedi</a></p>
</form>
<% if(reportIscrizione != null){%>
    <jsp:include page="/WEB-INF/componenti/pubblici/modali/modaleIscrizione.jsp" />
<%}%>
<script type="text/javascript">

    $(document).ready(() => {
        let today = new Date()
        let todayMinusEighteenYears = new Date(today.setFullYear(today.getFullYear() - 18))

        $("#dataDiNascita").attr("max", todayMinusEighteenYears.toISOString().slice(0, 10))

        $("#link").click((event) => {
            customClick(event, $("#pageContent"), "loginForm.jsp")
        })
    })
</script>
