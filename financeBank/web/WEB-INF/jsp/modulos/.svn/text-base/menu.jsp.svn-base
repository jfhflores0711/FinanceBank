<%-- 
    Document   : newjsp
    Created on : 11/02/2010, 04:41:24 PM
    Author     : ronald
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8" import="java.net.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8" />
        <link rel="shortcut icon" href="./images/favicon.ico" />
        <title>FinanceBank</title>
        <meta name="keywords" content="FinanceBank" />
        <meta name="description" content="Sistema financiero" />
        <link href="default.css" rel="stylesheet" type="text/css" />
        <style type="text/css" media="print">
            *{
                display:none;
            }
        </style>
    </head>
    <body>
        <%@include file="../common/header.jsp" %>
        <div id="content">
            <div id="sidebar">
                <div id="menu">
                    <%@include file="../common/menu.jsp" %>
                </div>
                <div id="login" class="boxed">
                    <h2 class="title">Cuenta Usuario</h2>
                    <div class="content">
                        <form id="form1" method="post" action="logout.htm">
                            <input id="inputsubmit1" type="submit" name="inputsubmit1" value="Salir" />
                        </form>
                    </div>
                </div>
            </div>
            <div id="main">
                <div id="welcome" class="post">
                    <h3 class="date"><span class="month"><script type="text/javascript">
                        var fecha=new Date();
                        var fmtmonthnamesshort=new Array("Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre");
                        document.write(fmtmonthnamesshort[fecha.getMonth()]);
                            </script></span> <span class="day"><script type="text/javascript">
                                document.write(fecha.getDate());
                            </script></span><span class="year">, <script type="text/javascript">document.write(fecha.getFullYear());</script></span></h3>
                    <div class="story">
                        <div id="mens"><center>
                                <font color="red" style="font-size:10px">
                                    <b>
                                        <c:forEach var="mensaje" items="${mensaje}">
                                            <c:out value="${mensaje}" />
                                        </c:forEach>
                                    </b>
                                </font>
                            </center></div>
                        <p><strong>Características y Funciones del FinanceBank</strong> </p>
                        <p>
                            Es un sistema computacional desarrollado para la ayuda y fácil manejo de la administración de las casas de cambio (compra y venta de divisas) y
                            Control de Cuentas de Ahorros en Moneda Nacional e Internacional, registro de Depósitos y Retiros y Estados de Cuenta, etc.
                        </p>
                        <p>
                            El sistema ayuda a controlar la compra y venta de divisas y a registrar las operaciones realizadas con los distintos tipos de cambio. Proporciona un control completo sobre su negocio.
                        </p>
                        <ul>
                            <li>Registro de Compra/Venta de Dólares y Divisas en Ventanilla</li>
                            <li>Catálogo de Divisas y Productos plenamente configurable (Dólar Billete, Euro,Monedas, etc.)</li>
                            <li>Catálogo de Clientes</li>
                            <li>Posibilidad de indicar operaciones con el Público en General o con Identificación de Cliente</li>
                            <li>Registro de Entradas/Salidas directas de Caja (Gastos, Sobrantes, Faltantes, etc.), tanto en Moneda Nacional como en Dólares y Divisas</li>
                            <li>Cambio de Cheques Nacionales o en Dólares con Comisión</li>
                            <li>Cálculo interno de Utilidades y Evaluación de Existencias</li>
                            <li>Control de Cuentas de Ahorros en Moneda Nacional y Dólares, registro de Depósitos y Retiros y Estados de Cuenta</li>
                            <li>Cargos y Abonos a Clientes Diversos y Estados de Cuenta de Clientes</li>
                            <li>Listados de Operaciones de Compra/Venta del Día, por Cliente, por Empleado, Totales, etc.</li>
                            <li>Listado de Entradas/Salidas y Depósitos/Retiros (Gastos, Faltantes, Sobrantes, Transferencias a/desde Bancos, etc.)</li>
                            <li>Detalle general de operaciones</li>
                            <li>Cálculo del Capital Inicial y Existencias del siguiente día laboral</li>
                            <li>Dotaciones y Retiros a Cajeros</li>
                            <li>Restricciones a Cajeros</li>
                            <li>Funcionamiento en Red</li>
                        </ul>
                    </div><div class="content">Firma de Ingreso:
                        <c:forEach var="hid" items="${hid}">
                            <c:out value="${hid}" />
                        </c:forEach>
                        <input id="hid" name="hid" value="<%=hid%>" type="hidden"/>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="../common/footer.jsp" %>
    </body>
</html>
