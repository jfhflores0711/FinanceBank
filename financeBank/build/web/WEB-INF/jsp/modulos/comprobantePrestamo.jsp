<%-- 
    Document   : comprobantePrestamo
    Created on : 15-abr-2010, 9:46:18
    Author     : oscar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8" />
        <link rel="shortcut icon" href="./images/favicon.ico" />
        <title><%=(DateUtil.getDateTime("yyyymmddhhmmssS", new Date()))%>|financeBank! TicketDesembolso</title>
        <meta name="keywords" content="FinanceBank" />
        <meta name="description" content="Sistema financiero" />
        <link href="default.css" rel="stylesheet" type="text/css" />
        <!-- WIN PROTOTYPE -->
        <script type="text/javascript" src="js/prototype.js"> </script>
        <script type="text/javascript" src="js/effects.js"> </script>
        <script type="text/javascript" src="js/window.js"> </script>
        <script type="text/javascript" src="js/debug.js"> </script>
        <link href="css/themes/default.css" rel="stylesheet" type="text/css"/>
        <link href="css/themes/alert.css" rel="stylesheet" type="text/css"/>
        <link href="css/themes/alphacube.css" rel="stylesheet" type="text/css"/>
        <script language="JavaScript" type="text/JavaScript">
            <!--
            function MM_reloadPage(init) {  //reloads the window if Nav4 resized
                if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
                        document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
                else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
            }
            MM_reloadPage(true);
            //-->
        </script>
        <script type="text/javascript" src="js/scriptConvertTest.js"></script>
        <script type="text/javascript" src="js/scriptshow/scriptCComprobante.js"></script>
        <style type="text/css" media="All">
            body{
                font-size:1em;
            }
        </style>
        <style type="text/css" media="print">
            #logo{
                display:none;
            }
            #ctrl{
                display:none;
            }
        </style>
    </head>
    <body>
        <%@include file="../common/header.jsp" %>
        <%
                    request.setAttribute("idmenu", "15");
        %>
        <div id="login" style="display:none">
            <p><span id='login_error_msg' class="login_error" style="display:none;font-size:15px;">&nbsp;</span></p>
            <div style="clear:both"></div>
            <p><span class="login_label">Usuario</span><span class="login_input"><input type="text" id="user"/></span></p>
            <div style="clear:both"></div>
            <p><span class="login_label">Contrase&ntilde;a</span><span class="login_input"><input type="password" id="passwd"/></span></p>
            <div style="clear:both"></div>
            <div id="res" style="display:none"></div>
        </div>
        <div id="content">
            <%
                        Map ticket = (Map) session.getAttribute("ticketp");
                        ticket.get("NOMBRE");
            %>
            <div class="ticketpago" id="ticketpago">
                <form name="fcambio" method="post" action="certificadoplazofijo.htm">
                    <table border="0" style="font-size:small;"><tr>
                            <td align="left" valign="top">
                                <table width="525" border="0" >
                                    <tr>
                                        <td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td colspan="3" style="font:bolder;font-size:large">** DESEMBOLSO EN EFECTIVO - CRED<%=ticket.get("NUMEROCRED")%> **</td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td colspan="3">NUM. OP.: <%=ticket.get("NUMEROOPERACION").toString().substring(17)%>  / CAJA: <%=ticket.get("CODIGOCAJA")%> / <%=ticket.get("FILIAL")%></td>
                                    </tr>
                                    <tr>
                                        <td colspan="3">FECHA: <%=ticket.get("FECHA")%> </td>
                                    </tr>
                                    <tr>
                                        <td colspan="3"><%=(ticket.get("RUC") == null ? "" : ("RUC: " + ticket.get("RUC") + "/"))%>DNI: <%=ticket.get("DNI")%></td>
                                    </tr>
                                    <tr>
                                        <td colspan="3">CLIENTE: <font style="font:bolder;font-size:16px"><%=ticket.get("APELLIDOS")%> , <%=ticket.get("NOMBRE")%></font> </td>
                                    </tr>
                                    <tr>
                                        <td colspan="3">OPERACION EN EFECTIVO:<font style="font:bolder;font-size:16px"> <%=ticket.get("TIPOOPERACION")%></font></td>
                                    </tr>
                                    <tr style="display:none">
                                        <td colspan="3" id="tdIdOperacion"><%=ticket.get("IDOPERACION")%></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">-----------------------DESEMBOLSO--------------</td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>MONTO(<%=("NUEVO SOL" == ticket.get("MONEDA").toString() ? " NUEVOS SOLES" : ("DOLAR" == ticket.get("MONEDA").toString() ? " DOLARES AMERICANOS" : ("EURO" == ticket.get("MONEDA").toString() ? " EUROS" : ticket.get("MONEDA"))))%>): </td>
                                        <td align="right" style="font-weight: bold"><%=ticket.get("MONTO")%> </td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>COMISION Y OTROS:</td><td align="right" style="font-weight: bold"> 0.00</td> <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td colspan="3">--------------------------------------------------------</td>
                                    </tr>
                                    <tr id="trSaldo">
                                        <td id="tdvacio">&nbsp;</td>
                                        <td id="tdSaldo"><div id="divSaldo">TOTAL ENTREGA: <%=ticket.get("TE")%></div></td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td colspan="3" align="center">&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td colspan="3" align="center">&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td colspan="3" align="center">--------------------</td>
                                    </tr>
                                    <tr>
                                        <td colspan="3" align="center"><%=ticket.get("APELLIDOS")%> , <%=ticket.get("NOMBRE")%></td>
                                    </tr>
                                    <tr>
                                        <td colspan="3" align="center"><%=(ticket.get("RUC") == null ? "" : ("RUC: " + ticket.get("RUC") + "/"))%>DNI: <%=ticket.get("DNI")%></td>
                                    </tr>
                                    <tr id="trnumCuenta" style="display:none">
                                        <td colspan="2" id="tdnumcuenta"><%=ticket.get("NUMEROCUENTA")%></td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                    </tr>
                                </table></td><td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                            <td align="right" valign="top">
                                <table width="525" border="0" >
                                    <tr>
                                        <td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td colspan="3" style="font:bolder;font-size:large">** DESEMBOLSO EN EFECTIVO -CRED<%=ticket.get("NUMEROCRED")%> **</td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td colspan="3">NUM. OP.: <%=ticket.get("NUMEROOPERACION").toString().substring(17)%>  / CAJA: <%=ticket.get("CODIGOCAJA")%> / <%=ticket.get("FILIAL")%></td>
                                    </tr>
                                    <tr>
                                        <td colspan="3">FECHA: <%=ticket.get("FECHA")%> </td>
                                    </tr>
                                    <tr>
                                        <td colspan="3"><%=(ticket.get("RUC") == null ? "" : ("RUC: " + ticket.get("RUC") + "/"))%>DNI: <%=ticket.get("DNI")%></td>
                                    </tr>
                                    <tr>
                                        <td colspan="3">CLIENTE: <font style="font:bolder;font-size:16px"><%=ticket.get("APELLIDOS")%> , <%=ticket.get("NOMBRE")%></font> </td>
                                    </tr>
                                    <tr>
                                        <td colspan="3">OPERACION EN EFECTIVO:<font style="font:bolder;font-size:16px"> <%=ticket.get("TIPOOPERACION")%></font></td>
                                    </tr>
                                    <tr style="display:none">
                                        <td colspan="3" id="tdIdOperacion"><%=ticket.get("IDOPERACION")%></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">-----------------------DESEMBOLSO--------------</td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>MONTO(<%=("NUEVO SOL" == ticket.get("MONEDA").toString() ? " NUEVOS SOLES" : ("DOLAR" == ticket.get("MONEDA").toString() ? " DOLARES AMERICANOS" : ("EURO" == ticket.get("MONEDA").toString() ? " EUROS" : ticket.get("MONEDA"))))%>): </td>
                                        <td align="right" style="font-weight: bold"><%=ticket.get("MONTO")%> </td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>COMISION Y OTROS:</td><td align="right" style="font-weight: bold"> 0.00</td> <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td colspan="3">--------------------------------------------------------</td>
                                    </tr>
                                    <tr id="trSaldo">
                                        <td id="tdvacio">&nbsp;</td>
                                        <td id="tdSaldo"><div id="divSaldo">TOTAL ENTREGA: <%=ticket.get("TE")%></div></td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td colspan="3" align="center">Primera fecha de pago: <%=ticket.get("PP")%></td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td style="font-weight:bolder">Cliente</td>
                                    </tr>
                                    <tr id="trnumCuenta" style="display:none">
                                        <td colspan="2" id="tdnumcuenta"><%=ticket.get("NUMEROCUENTA")%></td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                    </tr>
                                </table></td>
                        </tr></table>
                    <div id="divExtorno" style="display:none">
                        <input id="txtExtorno" name="txtExtorno" type="text" value="NO">
                    </div>
                    <div id="ctrl"><blockquote>
                            <blockquote>
                                <blockquote>
                                    <input name="Submit" id="Submit" type="button" onClick="document.title=''; if (window.print) window.print();elsewindow.alert('Su Navegador no dispone de esta opcion, Actualicelo con una version mas reciente');" value="Imprimir">
                                    <input name="cmdExtornar" id="cmdExtornar" type="button" value="Extornar" onclick="ventanaNueva2();this.disabled=true;">
                                    <input name="Cerrar" id="Cerrar" type="button" onclick="window.close();" value="Cerrar" />
                                    <%--input name="chkVerSaldo" id="chkVerSaldo" type="checkbox" value="aa" onclick="ventanaNuevap();" disabled>Cierre esta ventana después de terminar de imprimir--%>
                                    <p>&nbsp;</p>
                                </blockquote>
                            </blockquote>
                        </blockquote>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
