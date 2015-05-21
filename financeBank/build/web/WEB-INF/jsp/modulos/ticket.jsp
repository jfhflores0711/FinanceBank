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
        <title>FinanceBank - Ticket</title>
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
        <script type="text/javascript" src="js/scriptshow/scriptTicket.js"></script>
        <style type="text/css" media="All">
            body{
                font-size:0.9em;
            }
            #content {
                width: 1100px;
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
            <% //System.out.println("antes de invocar");
                        Map ticket = (Map) session.getAttribute("ticket");
                        if (ticket == null) {
                            ticket = new HashMap();
                            ticket.put("NOMBRE", "_");
                            ticket.put("MONEDA", "PEN");
                        }
                        ticket.get("NOMBRE");
                        String mn = "";
                        mn = ticket.get("MONEDA").toString();
            %>
            <div class="ticketpago" id="ticketpago">
                <form name="fcambio" method="post" action="certificadoplazofijo.htm">
                    <table>
                        <tr>
                            <td valign="top">
                                <table width="525" border="0" >
                                    <tr>
                                        <td>&nbsp;</td><td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td><td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td><td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" align="center" style="font-size: medium;font-weight: bold"><font size='3'><%=ticket.get("TIPOOPERACION")%> <%=ticket.get("TIPOCUENTA")%>&nbsp;<%=("NUEVOS SOLES".equals(mn) ? "M.N." : "M.E.")%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font><br/>&nbsp;</td>
                                    </tr>
                                    <!--tr>
                                        <td>&nbsp;</td><td>&nbsp;</td>
                                    </tr-->
                                    <tr>
                                        <td colspan="2"><font size='3'>N&deg; OP.:<%=ticket.get("NUMEROOPERACION").toString().substring(ticket.get("NUMEROOPERACION").toString().length() - 4, ticket.get("NUMEROOPERACION").toString().length())%> | CAJA : <%=ticket.get("CODIGOCAJA")%> | OF:<%=ticket.get("FILIAL")%> </font></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2"><font size='3'>FECHA: <%=ticket.get("FECHA")%> &nbsp;&nbsp;&nbsp;&nbsp;HORA: <%=ticket.get("HORA")%> </font></td>
                                    </tr>
                                    <tr>
                                        <% if (ticket.get("TIPOPERSONA").equals("NATURAL")) {%>
                                        <td colspan="2"><font size='3'>DNI N&deg;: <%=ticket.get("DNI")%></font></td>
                                        <%} else if (ticket.get("TIPOPERSONA").equals("JURIDICA")) {%>
                                        <td colspan="2"><font size='3'>RUC N&deg;: <%=ticket.get("RUC")%></font></td>
                                        <%}
                                        %>
                                    </tr>
                                    <tr>
                                        <td colspan="2"><font size='3'><%if (ticket.get("TIPOOPERACION").equals("DEPOSITO") || ticket.get("TIPOOPERACION").equals("RETIRO")) {%>N&deg; CUENTA: <%=ticket.get("NUMEROCUENTA")%> <% } else {%>C&Oacute;DIGO: <% }%> </font></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2"><font size='3'>TITULAR: <b> <%=ticket.get("NOMBRE")%>&nbsp;<%=ticket.get("APELLIDOS")%> </b></font></td>
                                    </tr>
                                    <!--tr>
                                        <td colspan="2" ><font size='3'>TIPO MONEDA: <%=ticket.get("MONEDA")%>(<%=("NUEVOS SOLES".equals(mn) ? "M.N." : "M.E.")%>: <%=(ticket.get("MONEDA").toString().length() > 12 ? ticket.get("MONEDA").toString().substring(0, 12) : ticket.get("MONEDA").toString())%>)</font></td>
                                    </tr-->
                                    <tr style="display:none">
                                        <td colspan="2" id="tdIdOperacion"><%=ticket.get("IDOPERACION")%></td>
                                    </tr>
                                    <% if ((ticket.get("CODTIPOCUENTA").equals("CCO") || ticket.get("CODTIPOCUENTA").equals("CAH")) && (ticket.get("TIPOOPERACION").equals("DEPOSITO") || ticket.get("TIPOOPERACION").equals("RETIRO")) && (ticket.get("NOMBREREPRESENTANTE") != null || ticket.get("APELLIDOSPRESENTANTE") != null)) {
                                    %>
                                    <tr>
                                        <td colspan="2"><font size='3'>REF.: <% if (ticket.get("NOMBREREPRESENTANTE") == null) {
                                                 out.println(" ***** ");
                                             } else {
                                                 out.print(ticket.get("NOMBREREPRESENTANTE").toString().toUpperCase());
                                             }%><% if (ticket.get("APELLIDOSPRESENTANTE") == null) {
                                                      out.println(" ***** ");
                                                  } else {
                                                      out.println(" " + ticket.get("APELLIDOSPRESENTANTE").toString().toUpperCase());
                                                  }%>
                                            </font></td>
                                    </tr>
                                    <%}%>
                                    <!--tr>
                                        <td colspan="2">&nbsp;</td>
                                    </tr-->
                                    <tr id="trInteres1" style="display: none">                                        
                                        <td colspan="2">
                                            <table width="400px">
                                                <tr><td align="left"><font size='3'>INTERES :</font></td>
                                                    <td align="right"><font size='3' style="font-weight: bold"> <%=ticket.get("INTERES")%> %</font></td>
                                                    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr><td colspan="2"><br/>
                                            <table width="400px">
                                                <tr><td align="left">
                                                        <font size='3'>IMPORTE <%
                                                                    if (ticket.get("TIPOOPERACION").equals("DEPOSITO")) {
                                                            %>RECIBIDO:<%} else if (ticket.get("TIPOOPERACION").equals("RETIRO")) {
                                                            %>PAGADO:&nbsp;&nbsp;<%}
                                                            %> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=ticket.get("MON")%>&nbsp;&nbsp;&nbsp;</font>
                                                    </td>
                                                    <td align="right" style="font-weight: bold"> <%=ticket.get("MONTO")%></td>
                                                    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>
                                            </table></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">
                                            <div id="divSaldos" style="display: none">
                                                <table width='400px'><tr><td align='left'><font size='3'>SALDO DISPONIBLE: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                <%= ticket.get("MON")%>&nbsp;</font></td><td align='right'><font size='3'>
                                                                <%=ticket.get("SALDO1")%></font></td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                                                <% if (!ticket.get("INT").equals("0.00")) {%>
                                                    </tr><tr><td align='left'><font size='3'>INTERESES: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%= ticket.get("MON")%>&nbsp;</font></td><td align='right'><font size='3'><%= ticket.get("INT")%></font></td>
                                                        <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr><tr>
                                                        <td align='left'><font size='3'>SALDO TOTAL: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%= ticket.get("MON")%>&nbsp;</font></td><td align='right'><div><font size='3'><%= ticket.get("SALDO")%></font></div></td>
                                                        <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                            <% } else {%>
                                                    </tr><tr><td>&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                                        <td>&nbsp;
                                                            <% }%></td></tr></table>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr id="trnumCuenta" style="display:none">
                                        <td colspan="2" id="tdnumcuenta"><%=ticket.get("NUMEROCUENTA")%></td>
                                    </tr>
                                    <% if (ticket.get("TIPOOPERACION").equals("RETIRO")) {
                                    %>
                                    <tr>
                                        <td colspan="2" align="center">&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" align="center"><font size='3'>___________________________________</font></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" align="center"><font size='3'><%=ticket.get("NOMBRE")%>&nbsp;<%=ticket.get("APELLIDOS")%></font><br/><!--/td>
                                    </tr>
                                    <tr-->
                                            <% if (ticket.get("TIPOPERSONA").equals("NATURAL")) {%>
                                            <!--td colspan="2" align="center"--><font style="font-weight: bold;" size='3'>DNI: <%=ticket.get("DNI")%></font><!--/td-->
                                            <%} else if (ticket.get("TIPOPERSONA").equals("JURIDICA")) {%>
                                            <!--td colspan="2" align="center"--><font style="font-weight: bold;" size='3'>RUC: <%=ticket.get("RUC")%></font></td>
                                            <% }
                                            %>
                                    </tr>
                                    <%} else {
                                    %>
                                    <tr>
                                        <td colspan="2" align="center">&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" align="center">&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" align="center">&nbsp;</td>
                                    </tr>
                                    <!--tr>
                                        <td colspan="2" align="center">&nbsp;</td>
                                    </tr-->
                                    <%}
                                    %>
                                    <tr>
                                        <!--td>&nbsp;</td-->
                                        <td colspan="2" align="center"><font size='1'>CONTROL ADMINISTRATIVO</font></td>
                                    </tr>
                                </table>
                            </td>
                            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                            <td valign="top">
                                <table width="525" border="0" >
                                    <tr>
                                        <td colspan="2">&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td><td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td><td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" align="center" style="font-size: medium;font-weight: bold"><font size='3'><%=ticket.get("TIPOOPERACION")%> <%=ticket.get("TIPOCUENTA")%>&nbsp;<%=("NUEVOS SOLES".equals(mn) ? "M.N." : "M.E.")%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font><br/></td>
                                    </tr>
                                    <!--tr>
                                        <td>&nbsp;</td><td>&nbsp;</td>
                                    </tr-->
                                    <tr>
                                        <td colspan="2"><font size='3'>N&deg; OP.:<%=ticket.get("NUMEROOPERACION").toString().substring(ticket.get("NUMEROOPERACION").toString().length() - 4, ticket.get("NUMEROOPERACION").toString().length())%> | CAJA : <%=ticket.get("CODIGOCAJA")%> | OF:<%=ticket.get("FILIAL")%> </font></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2"><font size='3'>FECHA: <%=ticket.get("FECHA")%> &nbsp;&nbsp;&nbsp;&nbsp;HORA: <%=ticket.get("HORA")%> </font></td>
                                    </tr>
                                    <tr>
                                        <% if (ticket.get("TIPOPERSONA").equals("NATURAL")) {%>
                                        <td colspan="2"><font size='3'>DNI N&deg;: <%=ticket.get("DNI")%></font></td>
                                        <%} else if (ticket.get("TIPOPERSONA").equals("JURIDICA")) {%>
                                        <td colspan="2"><font size='3'>RUC N&deg;: <%=ticket.get("RUC")%></font></td>
                                        <%}
                                        %>
                                    </tr>
                                    <tr>
                                        <td colspan="2"><font size='3'><%if (ticket.get("TIPOOPERACION").equals("DEPOSITO") || ticket.get("TIPOOPERACION").equals("RETIRO")) {%>N&deg; CUENTA: <%=ticket.get("NUMEROCUENTA")%> <% } else {%>C&Oacute;DIGO: <% }%> (<%=("NUEVOS SOLES".equals(mn) ? "M.N." : "M.E.")%>: <%=(ticket.get("MONEDA").toString().length() > 12 ? ticket.get("MONEDA").toString().substring(0, 12) : ticket.get("MONEDA").toString())%>)</font></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2"><font size='3'>TITULAR: <b> <%=ticket.get("NOMBRE")%>&nbsp;<%=ticket.get("APELLIDOS")%> </b></font></td>
                                    </tr>
                                    <!--tr>
                                        <td colspan="2" ><font size='3'>TIPO MONEDA: <%=ticket.get("MONEDA")%></font></td>
                                    </tr-->
                                    <tr style="display:none">
                                        <td colspan="2" id="tdIdOperacion"><%=ticket.get("IDOPERACION")%></td>
                                    </tr>
                                    <% if ((ticket.get("CODTIPOCUENTA").equals("CCO") || ticket.get("CODTIPOCUENTA").equals("CAH") || ticket.get("CODTIPOCUENTA").equals("CPF")) && (ticket.get("TIPOOPERACION").equals("DEPOSITO") || ticket.get("TIPOOPERACION").equals("RETIRO")) && (ticket.get("NOMBREREPRESENTANTE") != null || ticket.get("APELLIDOSPRESENTANTE") != null)) {
                                    %>
                                    <tr>
                                        <td colspan="2"><font size='3'>REF.: <% if (ticket.get("NOMBREREPRESENTANTE") == null) {
                                                 out.println(" ***** ");
                                             } else {
                                                 out.print(ticket.get("NOMBREREPRESENTANTE").toString().toUpperCase());
                                             }%><% if (ticket.get("APELLIDOSPRESENTANTE") == null) {
                                                      out.println(" ***** ");
                                                  } else {
                                                      out.println(" " + ticket.get("APELLIDOSPRESENTANTE").toString().toUpperCase());
                                                  }%>
                                            </font></td>
                                    </tr>
                                    <%}%>
                                    <!--tr>
                                        <td colspan="2">&nbsp;</td>
                                    </tr-->
                                    <tr id="trInteres2" style="display: none">
                                        <td colspan="2">
                                            <table width="400px">
                                                <tr><td align="left"><font size='3'>INTERES :</font></td>
                                                    <td align="right"><font size='3' style="font-weight: bold"> <%=ticket.get("INTERES")%> %</font></td>
                                                    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr><td colspan="2"><br/>
                                            <table width="400px">
                                                <tr><td align="left">
                                                        <font size='3'>IMPORTE <%
                                                                    if (ticket.get("TIPOOPERACION").equals("DEPOSITO")) {
                                                            %>RECIBIDO:<%} else if (ticket.get("TIPOOPERACION").equals("RETIRO")) {
                                                            %>PAGADO:&nbsp;&nbsp;<%}
                                                            %> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=ticket.get("MON")%>&nbsp;&nbsp;&nbsp;</font>
                                                    </td>
                                                    <td align="right" style="font-weight: bold"> <%=ticket.get("MONTO")%></td>
                                                    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>
                                            </table></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">
                                            <div id="divSaldos2" style="display: none">
                                                <table width='400px'><tr><td align='left'><font size='3'>SALDO DISPONIBLE: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                <%= ticket.get("MON")%>&nbsp;</font></td><td align='right'><font size='3'>
                                                                <%=ticket.get("SALDO1")%></font></td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                                                <% if (!ticket.get("INT").equals("0.00")) {%>
                                                    </tr><tr><td align='left'><font size='3'>INTERESES: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%= ticket.get("MON")%>&nbsp;</font></td><td align='right'><font size='3'><%= ticket.get("INT")%></font></td>
                                                        <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr><tr>
                                                        <td align='left'><font size='3'>SALDO TOTAL: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%= ticket.get("MON")%>&nbsp;</font></td><td align='right'><div><font size='3'><%= ticket.get("SALDO")%></font></div></td>
                                                        <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                            <% } else {%>
                                                    </tr><tr><td>&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                                        <td>&nbsp;
                                                            <% }%></td></tr></table>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr id="trnumCuenta" style="display:none">
                                        <td colspan="2" id="tdnumcuenta"><%=ticket.get("NUMEROCUENTA")%></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" align="center">&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" align="center">&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" align="center">&nbsp;</td>
                                    </tr>
                                    <!--tr>
                                        <td colspan="2" align="center">&nbsp;</td>
                                    </tr-->
                                    <tr>
                                        <!--td>&nbsp;</td-->
                                        <td colspan="2" align="center"><font size='1'>USUARIO</font></td>
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
                                        <%
                                                    if (ticket.get("h") == null) {
                                                        if (ticket.get("CODTIPOCUENTA").equals("CPF")) {%>
                                        <input type="button" id="txtCertificadoPF" name="txtCertificadoPF" value="Ver Certificado" onclick="abrirVentana();">
                                        <input type="button" id="txtAnexoPF" name="txtAnexoPF" value="Ver Anexo" onclick="abrirVentanaAnexo();">
                                        <input type="hidden" id="certificadoplazofijoGen" name="certificadoplazofijoGen" value="<%=ticket.get("IDCERTIFICADO")%>">
                                        <input type="hidden" id="anexoplazofijoGen" name="anexoplazofijoGen" value="<%=ticket.get("IDANEXO")%>">
                                        <%                                                                                    }%>
                                        <input name="cmdExtornar" id="cmdExtornar" type="button" value="Extornar" onclick="ventanaNueva2();">
                                        <input name="regresar" id="regresar" type="button" onclick="activarsubmit('regresar');" value="Regresar" />
                                        <%} else {%>
                                        <input name="cerrar" id="cerrar" type="button" onclick="window.close();" value="CERRAR" />
                                        <%                                                    }%>
                                        <input name="chkVerSaldo" id="chkVerSaldo" type="checkbox" value="aa" onclick="ventanaNueva();">Incluir Saldo
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
