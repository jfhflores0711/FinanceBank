<%-- 
    Document   : newjsp
    Created on : 11/02/2010, 04:41:24 PM
    Author     : ronald
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8" />
        <link rel="shortcut icon" href="./images/favicon.ico" />
        <title>FinanceBank</title>
        <meta name="keywords" content="FinanceBank" />
        <meta name="description" content="Sistema financiero" />
        <link href="default.css" rel="stylesheet" type="text/css" />
        <style type="text/css">
            /* ----- REQUIRED ----- */
            .req {
                font-size: 1em !important;
                color: #f90;
                font-weight: bold;
            }
        </style>
        <script type="text/javascript">
            <%@include file="/js/funciones.js" %>
        </script>
    </head>
    <body>

        <div id="logo">

            <h1><a href="#">&nbsp;&nbsp;&nbsp;&nbsp;</a>
                <i></i>
            </h1>

        </div>

        <div id="content">
            <div id="sidebar">
                
                <div id="login" class="boxed">
                    <h2 class="title">Client Account</h2>
                    <div class="content">
                        <form id="form1" method="post" action="index.htm" onsubmit="saveUsername(this);return validateForm(this)">
                            <fieldset>
                                <center>
                                    <font color="red" style="font-size:10px">
                                        <b>
                                            <c:forEach var="mensaje" items="${mensaje}">
                                                <c:out value="${mensaje}" />
                                            </c:forEach>
                                        </b>
                                    </font>
                                </center>
                                <br>
                                <legend>Iniciar Session</legend>
                                <label for="inputtext1"><fmt:message key="label.username"/> <span class="req">*</span></label>
                                <input id="inputtext1" type="text" name="login" value="" />
                                <label for="inputtext2"><fmt:message key="label.password"/> <span class="req">*</span></label>
                                <input id="inputtext2" type="password" name="contrasenia" value="" />
                                <input id="inputsubmit1" type="submit" name="inputsubmit1" value="<fmt:message key='button.login'/>" />
                                <p><fmt:message key="login.passwordHint"/></p>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
            <div id="main2">
                <div id="welcome" class="post">
                    <p><img src="images/img03.jpg" alt="" width="700" height="300" /></p>
                    <h2 class="title">Bienvenidos!</h2>
                </div>
            </div>
        </div>
    </body>
</html>
