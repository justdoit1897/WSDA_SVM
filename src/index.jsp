<%@ page import="utils.CookieOps" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%

    if (!CookieOps.areThereCookies(request)) {
        CookieOps.addCookie(response, "actualPage", "loginForm.jsp", 60 * 60 * 2);
    } else {
        Cookie actualPage = CookieOps.getCookie(request, response, "actualPage");

        if (actualPage != null) {
            response.addCookie(actualPage);
        } else {
            CookieOps.addCookie(response, "actualPage", "loginForm.jsp", 60 * 60 * 2);
        }
    }
%>

<!DOCTYPE html>
<html lang="it">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/favicon.png" />
        <title>Smart Vending Machines</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT" crossorigin="anonymous">
        <!-- Custom CSS -->
        <link rel="stylesheet" href="styles.css">
        <!-- Bootstrap JS -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js" integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-u1OknCvxWvY5kfmNBILK2hRnQC3Pr17a+RTT6rIHI7NnikvbZlHgTPOOmMi466C8" crossorigin="anonymous"></script>
        <!-- Custom JS -->
        <script src="scripts.js" type="text/javascript"></script>
    </head>
    <body>
        <main id="pageContent">
            <script type="text/javascript">
                $(document).ready(() => {
                    $.ajax({
                        type: "GET",
                        url: "./RequestContentServlet",
                        data: {
                            content: getCookie("actualPage")
                        },
                        success: function (data) {
                            $("#pageContent").html(data)
                        }
                    })

                })
            </script>
        </main>
        <jsp:include page="WEB-INF/componenti/footer.jsp" />
    </body>
</html>