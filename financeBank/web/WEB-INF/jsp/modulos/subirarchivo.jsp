<%-- 
    Document   : subirarchivo
    Created on : 15/03/2010, 03:13:21 PM
    Author     : roger
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" href="./images/favicon.ico" />
        <title>FinanceBank</title>
        <meta name="keywords" content="FinanceBank" />
        <meta name="description" content="Sistema financiero" />
    </head>
    <body>
        <center>
            <form method="POST" enctype='multipart/form-data' action="/financeBank/uploadFichero">
                Por favor, seleccione el trayecto del fichero a cargar
                <br><input type="file" name="fichero"> 
                <input type="submit">
            </form>
        </center>
    </body>
</html>
