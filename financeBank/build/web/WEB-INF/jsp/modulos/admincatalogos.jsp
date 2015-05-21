<%--
    Document   : admincatalogos.jsp
    Created on : 22/03/2010, 03:43:07 AM
    Author     : ZAMORA
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8" />
        <link rel="shortcut icon" href="./images/favicon.ico">
        <title>FinanceBank</title>
        <meta name="keywords" content="FinanceBank" />
        <meta name="description" content="Sistema financiero" />
        <link href="default.css" rel="stylesheet" type="text/css" />
        <link href='css/tabla.css' rel='stylesheet' type='text/css' />
        <script type="text/javascript" src="js/scriptshow/scriptAdmCatalogo.js"></script>
        <script type="text/javascript" src="js/validacion.js"></script>
        <script type="text/javascript" language="javascript" src="js/colorPicker.js"></script>
        <link rel="stylesheet" href="css/colorPicker.css" type="text/css" />
        <link rel="stylesheet" href="css/admCatalogo.css" type="text/css" />
        <script type="text/javascript" src="js/calendar/popcalendar.js"></script>
        <%--****************************************************--%>
    </head>
    <body onload="document.getElementById('selcatalogo').focus();">
        <%@include file="../common/header.jsp" %>
        <%
                    request.setAttribute("idmenu", "20110228202911495761");
        %>
        <div id="content">
            <div id="sidebar">
                <div id="menu">
                    <%@include file="../common/menu.jsp" %>
                </div>
                <div id="login" class="boxed">
                    <h2 class="title">Client Account</h2>
                    <div class="content">
                        <form id="form1" method="post" action="principal.htm">
                            <fieldset>
                                <input id="inputsubmit1" type="submit" name="inputsubmit1" value="Salir" />
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
            <div id="main">
                <div id="welcome" class="post">
                    <h2 class="title">Modulo de Cat&aacute;logo!</h2>
                    <h3 class="date"><span class="month"><script type="text/javascript">
                        var fecha=new Date();
                        var fmtmonthnamesshort=new Array("Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre");
                        document.write(fmtmonthnamesshort[fecha.getMonth()]);
                            </script></span> <span class="day"><script type="text/javascript">
                                document.write(fecha.getDate());
                            </script></span><span class="year">, <script type="text/javascript">document.write(fecha.getFullYear());</script></span></h3>
                    <div class="story" style="width:800px;">
                        <table width="100%">
                            <tr>
                                <td colspan="3">
                                    <fieldset   style="border-width:3px">
                                        <legend><b>CATALOGOS:</b></legend>
                                        <select id="selcatalogo" name="selcatalogo" onchange="mostrarcatalogo();">
                                            <option value="0">(Seleccione un Catalogo)</option>
                                            <option value="CAJA">CAJA</option>
                                            <option value="FILIAL">FILIAL</option>
                                            <option value="PERSONA">PERSONAS</option>
                                            <option value="PERSC">CLIENTES</option>
                                            <option value="A">CREDITOS</option>
                                        </select>
                                    </fieldset>
                                </td>
                            </tr>
                            <tr>
                                <td valign="top">
                                    <div id="divCatalogoElements">&nbsp;</div>
                                </td>
                            </tr>
                            <tr>
                                <td><div style="display: none" id="divCatalogoDetalle">&nbsp;</div><div id="mostrarCuentas">&nbsp;</div><div id="divtemporal" style="display: none"></div></td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="../common/footer.jsp" %>
    </body>
</html>
