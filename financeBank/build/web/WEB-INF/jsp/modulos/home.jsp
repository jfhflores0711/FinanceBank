<%-- 
    Document   : home
    Created on : 12/02/2010, 05:15:33 PM
    Author     : ronald
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<html>
    <head>
        <title>FinanceBank </title>
        <link rel="shortcut icon" href="./images/favicon.ico" />
        <meta name="keywords" content="FinanceBank" />
        <meta name="description" content="Sistema financiero" />
    </head>
    <body>
        <% if (request.getParameter("mensaje").isEmpty()) {%>
        <h1>El módulo de pistas de contraseña están desactivadas!!!</h1>
        <form action="principal.htm" method="POST">
            Usuario:
            <input type="text" name="u" value="<%=request.getParameter("username")%>" />
            <input type="submit" value="Regresar" />
        </form>
        <%} else {
        %>
        <h1 style="font-size: 3em;color: red;font-weight: bolder;">
            <%=request.getParameter("mensaje")%>

        </h1>

        <%
                    }
        %>
    </body>
</html>
