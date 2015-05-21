<%--
    Document   : newjsp
    Created on : 11/02/2010, 04:41:24 PM
    Author     : ronald
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="org.finance.bank.util.*, java.util.Date"%>
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
            .req {
                font-size: 1em !important;
                color: #f90;
                font-weight: bold;
            }
        </style>
        <script type="text/javascript">
            <%@include file="/js/funciones.js" %>
        </script>
        <script type="text/javascript">
            function real(){
                document.getElementById("mens").innerHTML="INICIALIZANDO SESIÓN!!!";
                document.getElementById("inputtext1").style.display='none';
                document.getElementById("inputtext2").style.display='none';
                document.getElementById("inputsubmit1").style.display='none';
                document.getElementById("cargando").innerHTML="Cargando los datos...<img alt='Cargando ...' src='images/ajax/ajax-loader1.gif'/>"
            }
        </script>
    </head>
    <body>
        <div id="logo">
            <h1><img src="images/logo04.png" width="129" height="100" alt="logo01"/></h1>
            <br><br><br>
        </div>
        <div id="content">
            <div id="sidebar">
                <div id="login" class="boxed">
                    <h2 class="title">Usuarios del sistema</h2>
                    <div class="content">
                        <form id="form1" method="post" action="index.htm" onsubmit="real();saveUsername(this);return validateForm(this);">
                            <center>
                                <font color="red" style="font-size:10px"><div id="mens"></div>
                                    <b>
                                        <c:forEach var="mensaje" items="${mensaje}">
                                            <c:out value="${mensaje}" />
                                        </c:forEach>
                                    </b>
                                </font>
                            </center>
                            <br/>
                            Iniciar Session
                            <br/>
                            <label for="inputtext1"><fmt:message key="label.username"/> <span class="req">*</span></label>
                            <input id="inputtext1" type="text" name="login" value="" />
                            <label for="inputtext2"><fmt:message key="label.password"/> <span class="req">*</span></label>
                            <input id="inputtext2" type="password" name="contrasenia" value="" />
                            <input id="inputsubmit1" type="submit" name="inputsubmit1" value="<fmt:message key='button.login'/>" />
                            <p><fmt:message key="login.passwordHint"/></p>
                            <div id="cargando"></div><img alt='Cargando ...' src='images/ajax/ajax-loader1.gif' style='display: none'/>
                        </form>
                    </div>
                </div>
            </div>
            <div id="main2">
                <div id="welcome" class="post">
                    <p><img src="images/img03.jpg" alt="" width="700" height="300" /></p>
                    <h2 class="title">Bienvenido a FinanceBank</h2>
                    <h3 class="date"><span class="month"><script type="text/javascript">
                        var fecha=new Date();
                        var fmtmonthnamesshort=new Array("Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre");
                        document.write(fmtmonthnamesshort[fecha.getMonth()]);
                            </script></span> <span class="day"><script type="text/javascript">
                                document.write(fecha.getDate());
                            </script></span><span class="year">, <script type="text/javascript">document.write(fecha.getFullYear());</script></span></h3>
                    <div class="meta">
                        <p><b>Casa de cambios Ventura</b></p>
                    </div>
                    <div class="story">
                        <p><strong>Características y Funciones del FinanceBank</strong> </p>
                        <p>
                            Es un sistema computacional desarrollado para la ayuda y fácil manejo de la administración de las casas de cambio (compra y venta de divisas) y
                            Control de Cuentas de Ahorros en Moneda Nacional e Internacional, registro de Depósitos y Retiros y Estados de Cuenta, etc.
                        </p>
                        <p>
                            El sistema ayuda a controlar la compra y venta de divisas y a registrar las operaciones realizadas con los distintos tipos de cambio. Proporciona un control completo sobre su negocio.
                        </p>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="/js/login.js" %>
        <div id="footer">
            <p id="legal">Copyright &copy; 2010-<%= DateUtil.getDateTime("yyyy", new Date())%> FinanceBank by VENTURA. All Rights Reserved.</p>
            <center> &#8476;</center>
            <center style="color:#ffffff" > &#8224;</center>
            <p id="links"><a href="#" onclick="window.alert('El Sistema FinanceBank ha sido diseñada para \n manejar operaciones especializadas del \ncliente, por el cual se exhorta no revelar sus \ncontraseñas y los datos personales.')">Políticas de privacidad</a> |
                <a href="#" onclick="window.alert('Los Usuarios del Sistema FinanceBank, deben reportar \ncualquier irrregularidad al Administrador para \nsubsanar y usar los datos de manera apropiadas,\n suprimiendo cualquier uso abusivo de \nla información de la empresa.')">Terminos de Uso</a></p>
        </div>
        <% session.invalidate();%>
    </body>
</html>
