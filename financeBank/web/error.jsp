<%-- 
    Document   : error
    Created on : 02-mar-2010, 11:49:04
    Author     : oscar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ page isErrorPage="true" %>

<html>
    <head>
        <title>Inversiones VENTURA</title><link rel="shortcut icon" href="./images/favicon.ico" />
        <meta name="author" content="Casa de cambios Ventura">
        <meta name="organization" content="financeBank">
        <meta name="locality" content="Peru">
        <meta name="lang" content="es">
        <meta name="description" content="financebank">
        <meta name="keywords" content="financeBank">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="Pragma" content="no-cache">
        <meta http-equiv="Expires" content="-1">
    </head>
    <body bgcolor="#FFFF9D">
        <font color="#000080" FACE="Arial,Helvetica,Times" SIZE=2 />
        <center><H3>ERROR</H3></center>
        <HR>
        <h3>Se ha producido un error</h3>
        <p>Mensaje: <%= exception.getMessage()%></p>
        <%
        if (exception !=  null) {
        %>
        <p>Mensaje General: <%= exception%></p>
        <%
        }
        %>
    </body>
</html>
