<%@ page import="utils.CookieOps" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
Cookie loginFail = CookieOps.getCookie(request, response, "login");
Cookie actualPage = CookieOps.getCookie(request, response, "actualPage");

if(!actualPage.getValue().equals("loginForm.jsp")){
    CookieOps.updateCookie(response, actualPage, "loginForm.jsp");
}
%>

<form class="form-signin" method="post" action="${pageContext.request.contextPath}/login">
    <img src="${pageContext.request.contextPath}/images/Logo.svg" class="mb-4" width="96" alt="company-logo"/>
    <h2>Accedi</h2>
    <div class="row mb-1">
        <div class="col-md-12">
            <input type="email" name="indirizzo" id="email-address"
                   class="form-control mb-3" pattern="^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$"
                   placeholder="E-mail" required>
        </div>
    </div>
    <div class="row mb-1">
        <div class="col-md-12">
            <input type="password" name="password" id="password"
                   placeholder="Password" class="form-control mb-3" required>
        </div>
    </div>
    <div class="row mb-1">
        <div class="col-md-6">
            <button type="submit" class="btn btn-primary btn-lg btn-block w-100 mb-1">Accedi</button>
        </div>
        <div class="col-md-6">
            <button type="reset" class="btn btn-secondary btn-lg btn-block w-100">Annulla</button>
        </div>
    </div>
    <p>Non sei iscritto? <a id="link" class="text-decoration-none" href="#">Registrati</a></p>
</form>
<% if(loginFail != null && loginFail.getValue().equals("fail")){%>
    <jsp:include page="/WEB-INF/componenti/pubblici/modali/loginFail.jsp" />
<%}%>

<script type="text/javascript">
    $(document).ready(() => {

        $("#link").click((event) => {
            customClick(event, $("#pageContent"), "subscribeForm.jsp")
        })

    })
</script>


