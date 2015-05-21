<%--
    Document   : extornar
    Created on : 13/04/2010, 05:54:01 PM
    Author     : roger
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
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
        <link href='css/tabla.css' rel='stylesheet' type='text/css' />
        <script type="text/javascript" src="js/prototype.js"> </script>
        <script type="text/javascript" src="js/effects.js"> </script>
        <script type="text/javascript" src="js/window.js"> </script>
        <script type="text/javascript" src="js/debug.js"> </script>
        <link href="css/themes/default.css" rel="stylesheet" type="text/css"/>
        <link href="css/themes/alert.css" rel="stylesheet" type="text/css"/>
        <link href="css/themes/alphacube.css" rel="stylesheet" type="text/css"/>
        <script type="text/javascript" src="js/validacion.js"></script>
        <script type="text/javascript" src="js/scriptMath.js"></script>
        <script type="text/javascript" src="js/scriptConvertTest.js"></script>
        <script type="text/javascript" src="js/scriptshow/scriptExtornar.js"></script>
    </head>
    <body onload="document.getElementById('selTipoOperacion').focus();">
        <%@include file="../common/header.jsp" %>
        <%
                    request.setAttribute("idmenu", "20110228202911531353");
        %>
        <div id="content">
            <div id="sidebar">
                <div id="menu">
                    <%@include file="../common/menu.jsp" %>
                </div>
                <div id="xlogin" class="boxed">
                    <h2 class="title">Client Account</h2>
                    <div class="content">
                        <form id="form1" method="post" action="principal.htm">
                            <input id="inputsubmit1" type="submit" name="inputsubmit1" value="Salir" />
                        </form>
                    </div>
                </div>
            </div>
            <div id="login" style="display:none">
                <p>
                    <span id='login_error_msg' class="login_error" style="display:none;font-size:15px;">&nbsp;</span>
                </p>
                <div style="clear:both">
                </div>
                <p>
                    <span class="login_label">Usuario</span>
                    <span class="login_input">
                        <input type="text" id="user"/>
                    </span>
                </p>
                <div style="clear:both"></div>
                <p>
                    <span class="login_label">Contrase&ntilde;a</span>
                    <span class="login_input">
                        <input type="password" id="passwd"/>
                    </span>
                </p>
                <div style="clear:both">
                </div>
                <div id="res" style="display:none">
                </div>
            </div>
            <div id="main">
                <div id="welcome" class="post">
                    <h2 class="title">Modulo Extornar!</h2>
                    <h3 class="date"><span class="month"><script type="text/javascript">
                        var fecha=new Date();
                        var fmtmonthnamesshort=new Array("Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre");
                        document.write(fmtmonthnamesshort[fecha.getMonth()]);
                            </script></span> <span class="day"><script type="text/javascript">
                                document.write(fecha.getDate());
                            </script></span><span class="year">, <script type="text/javascript">document.write(fecha.getFullYear());</script></span></h3>
                    <div class="story">
                        <table id="">
                            <tr>
                                <td><fieldset style="border-width: 3px;">
                                        <legend>Buscar Por: </legend>
                                        <table>
                                            <tr>
                                                <td></td>
                                                <td>Escriba N&uacute;mero de Operaci&oacute;n:</td>
                                                <td></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td><select id="selTipoOperacion">
                                                        <option>TODO</option>
                                                        <option>COMPRA</option>
                                                        <option>VENTA</option>
                                                        <option>DEPOSITO O RETIRO</option>
                                                        <option>GIRO</option>
                                                        <option>COBROGIRO</option>
                                                        <option>PRESTAMO</option>
                                                        <option>COBRANZA</option>
                                                        <option>RETIRO OTRO</option>
                                                        <option>TRANFERENCIAS</option>
                                                    </select>
                                                </td>
                                                <td><input type="text" id="txtNumOperacion" name="txtNumOperacion" onkeypress="return solonumeros(event);" value=""></td>
                                                <td><input type="button" id="btnuscar" onclick="buscarOperacione();" value="Buscar Operaci&oacute;n"></td>
                                                <td></td>
                                            </tr>
                                        </table>
                                    </fieldset>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div id="divOperacion">
                                        <table id="tablaOperacion" class="tabla" border="0" style="display:none">
                                            <tr>
                                                <th style="display:none">-.-</th>
                                                <th>Num. Operaci&oacute;n</th>
                                                <th>Fecha de Registro</th>
                                                <th>Tipo Operaci&oacute;n</th>
                                                <th>Moneda</th>
                                                <th>Descripci&oacute;n</th>
                                                <th>Estado</th>
                                                <th>Usuario</th>
                                                <th>Fecha Extorno</th>
                                                <th>Admin Extorno</th>
                                                <th style="display:none">-..-</th>
                                            </tr>
                                            <%
                                                        int i = 0;
                                                        for (i = 0; i < 4; i++) {
                                                            String par = "";
                                                            i = i + 1;
                                                            if (i % 2 == 0) {
                                                                par = "modo2";
                                                            } else {
                                                                par = "modo1";
                                                            }
                                                            i = i - 1;%>
                                            <tr class="<%=par%>">
                                                <td id="r" style="display:none"></td>
                                                <td>a</td>
                                                <td>a</td>
                                                <td>a</td>
                                                <td>a</td>
                                                <td>a</td>
                                                <td>a</td>
                                                <td>a</td>
                                                <td>a</td>
                                                <td>a</td>
                                                <td><input type="button" id="btnExtornar<%=i%>" value="Extornar"></td>
                                            </tr>
                                            <% }%>
                                        </table>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <input type="button" id="btnActualizarK" name="btnActualizarK" onclick="actualizarK();" value="Actualizar Cuenta">
        <div id="divExtorno" style="display:none">
            <input id="txtExtorno" name="txtExtorno" type="text" value="NO">
        </div> <div id="res" style="display:none">
        </div>    
        <form id="formextor" name="formextor" action="extornar.htm"></form>
        <%@include file="../common/footer.jsp" %>
    </body>
</html>