<%--
    Document   : ticket
    Created on : 19-mar-2010, 10:01:09
    Author     : roger
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8" />
        <link rel="shortcut icon" href="./images/favicon.ico" />
        <title>FinanceBank - Reporte Cuenta</title>
        <meta name="keywords" content="FinanceBank" />
        <meta name="description" content="Sistema financiero" />
        <link href="default.css" rel="stylesheet" type="text/css" />
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
        </script>
        <script type="text/javascript" src="js/scriptshow/scriptReporteCuenta.js"></script>
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
                    request.setAttribute("idmenu", "10");
        %>
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
        <div id="content">
            <%
                        String idpersona = request.getParameter("txidPersona");
                        TPersona persona = (TPersona) d.load(TPersona.class, idpersona);
                        String nombreTitular = persona.getNombre();
                        String apellidosTitular = persona.getApellidos();
                        String dniTitular = persona.getDocIdentidad();
                        String rucTitular = persona.getRuc();
                        String tipocuenta = request.getParameter("txTipoCuenta");
                        String nombreMoneda = request.getParameter("txtNombreMoneda");
                        String numcuenta = request.getParameter("txtNumCuentaDepo");
                        String c = numcuenta;
                        String interes = request.getParameter("txtInteres");
                        String codCaja = (String) session.getAttribute("USER_CODCAJA");
            %>
            <div class="ticketpago" id="ticketpago">
                <form name="fcambio" method="post" action="certificadoplazofijo.htm">
                    <table>
                        <tr>
                            <td align="center" valign="top">
                                <table width="520px" border="0" >
                                    <tr>
                                        <td colspan="2">&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" align="center" style="font-size: medium;font-weight: bold"><font size='3'>REPORTE DE CUENTA&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font></td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <%String CodFilial = (String) session.getAttribute("USER_CODFILIAL");
                                                    TFilial Tfilial = (TFilial) d.load(TFilial.class, CodFilial);
                                                    String mifilial = Tfilial.getNombre().replace("FILIAL", "OFICINA");
                                                    DateFormat idfecha = new SimpleDateFormat("yyyy/MM/dd");
                                                    String FechaHoy = idfecha.format(new Date());
                                                    DateFormat hora = new SimpleDateFormat("HH:mm:ss");
                                                    String HoraHoy = hora.format(new Date());
                                        %>
                                        <td colspan=""><font size='3'>CODCAJA: <%=codCaja%> | FILIAL: <%=mifilial%></font></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2"><font size='3'>FECHA: <%=FechaHoy%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;HORA: <%=HoraHoy%> </font></td>
                                    </tr>
                                    <tr>
                                        <%
                                                    if (persona.getTCategoriaPersona().getDescripcion().equals("NATURAL")) {%>
                                        <td colspan="2"><font size='3'>DNI: <%=dniTitular%></font></td>
                                        <%} else if (persona.getTCategoriaPersona().getDescripcion().equals("JURIDICA")) {%>
                                        <td colspan="2"><font size='3'>RUC: <%=rucTitular%></font></td>
                                        <%}
                                        %>
                                    </tr>
                                    <tr>
                                        <td colspan="2"><font size='3'>TITULAR: <%=nombreTitular%>&nbsp;<%=apellidosTitular%> </font></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2"><font size='3'><%=tipocuenta%> N&deg;: <%=(c.substring(0, 5) + "-" + c.substring(5, 6) + "-" + c.substring(6))%></font></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2"><font size='3'>TIPO DE MONEDA: <%=nombreMoneda%></font></td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr id="trInteres1" style="display: none">
                                        <td colspan="2"><font size='3'>INTERES : <%=interes%> %</font></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">
                                            <div id="divSaldos" style="display: none">
                                            </div>
                                        </td>
                                    </tr>
                                    <tr id="trnumCuenta" style="display:none">
                                        <td colspan="2" id="tdnumcuenta"><%=numcuenta%></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" align="center">&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" align="center"><font size='3'>___________________________________</font></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" align="center"><font size='3'><%=persona.getNombre()%>&nbsp;<%=persona.getApellidos()%></font></td>
                                    </tr>
                                    <tr>
                                        <% if (persona.getTCategoriaPersona().getDescripcion().equals("NATURAL")) {%>
                                        <td colspan="2" align="center"><font size='3'>DNI: <%=persona.getDocIdentidad()%></font></td>
                                        <%} else if (persona.getTCategoriaPersona().getDescripcion().equals("JURIDICA")) {%>
                                        <td colspan="2" align="center"><font size='3'>RUC: <%=persona.getRuc()%></font></td>
                                        <%}
                                        %>
                                    </tr>
                                </table>
                            </td>
                            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                            <td align="center" valign="top">
                                <table width="520px" border="0" >
                                    <tr>
                                        <td colspan="2">&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" align="center" style="font-size: medium;font-weight: bold"><font size='3'>REPORTE DE CUENTA&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font></td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td colspan="2"><font size='3'> CODCAJA: <%=codCaja%> | FILIAL: <%=mifilial%> </font></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2"><font size='3'>FECHA: <%=FechaHoy%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;HORA: <%=HoraHoy%> </font></td>
                                    </tr>
                                    <tr>
                                        <%
                                                    //TPersona persona = (TPersona) d.load(TPersona.class, idpersona);
                                                    if (persona.getTCategoriaPersona().getDescripcion().equals("NATURAL")) {%>
                                        <td colspan="2"><font size='3'>DNI: <%=dniTitular%></font></td>
                                        <%} else if (persona.getTCategoriaPersona().getDescripcion().equals("JURIDICA")) {%>
                                        <td colspan="2"><font size='3'>RUC: <%=rucTitular%></font></td>
                                        <%}
                                        %>
                                    </tr>
                                    <tr>
                                        <td colspan="2"><font size='3'>TITULAR: <%=nombreTitular%>&nbsp;<%=apellidosTitular%> </font></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2"><font size='3'>CUENTA: <%=tipocuenta%> N&deg;: <%=numcuenta%></font></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2"><font size='3'>TIPO MONEDA: <%=nombreMoneda%></font></td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr id="trInteres2" style="display: none">
                                        <td colspan="2"><font size='3'>INTERES :  <%=interes%> %</font></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">
                                            <div id="divSaldos2" style="display: none">
                                            </div>
                                        </td>
                                    </tr>
                                    <tr id="trnumCuenta" style="display:none">
                                        <td colspan="2" id="tdnumcuenta"><%=numcuenta%></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">&nbsp;</td>
                                    </tr>                                    
                                    <tr>
                                        <td>&nbsp;</td>
                                        <td align="center"><font size='3'>CLIENTE</font></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">&nbsp;</td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                    <br><br>
                    <div id="divExtorno" style="display:none">
                        <input id="txtExtorno" name="txtExtorno" type="text" value="NO">
                    </div>
                    <div id="ctrl">
                        <center>
                            <blockquote>
                                <blockquote>
                                    <blockquote>
                                        <input name="Submit" id="Submit" type="button" onClick="document.title=''; if (window.print)window.print();else alert('Su Navegador no dispone de esta opcion, Actualicelo con una version mas reciente');" value="Imprimir">
                                        <input name="regresar" id="regresar" type="button" onclick="activarsubmit('regresar');" value="Regresar" />
                                        <input name="chkVerSaldo" id="chkVerSaldo" type="checkbox" value="aa" onclick="ventanaNueva();">Incluir Saldo del Cliente en el ticket
                                        &nbsp;&nbsp;&nbsp;&nbsp; <input type="checkbox" name="chkVerInteres" id="chkVerInteres" onclick="mostrarInteres();"/>Incluir Inter&eacute;s
                                        <p>&nbsp;</p>
                                    </blockquote>
                                </blockquote>
                            </blockquote>
                        </center>
                    </div>
                </form>
                <form id="frmFin" name="frmFin" method="post" action="menu.htm">
                </form>
            </div>
        </div>
    </body>
</html>
