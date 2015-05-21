<%--
   Document   : managercaja.jsp
   Created on : 11/02/2010, 04:41:24 PM
   Author     : oscar
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
        <style type="text/css" media="print">
            #sidebar{display: none;}
            #logo{display: none;}
            .story{display:none;}
            #frmOperaciones{display: none;}
            #main{width:940px;}
            #Submit{display: none;}
            #btnRegresar{display: none;}
            #footer{display: none;}
            #divOperacionf{height:auto;overflow: visible;position: absolute;top: 0;}
        </style>
        <script type="text/javascript" src="js/scriptConvertTest.js"></script>
        <script type="text/javascript" src="js/scriptshow/scriptMCaja.js"></script>
    </head>
    <body onload="document.getElementById('montoTranferir0').focus();">
        <%@include file="../common/header.jsp" %>
        <%
                    request.setAttribute("idmenu", "20110228202911483821");
        %>
        <div id="content">
            <div id="sidebar">
                <div id="menu">
                    <%@include file="../common/menu.jsp" %>
                </div>
                <div id="xlogin" class="boxed">
                    <h2 class="title">Cuenta del Cliente</h2>
                    <div class="content">
                        <form id="form1" method="post" action="logout.htm">
                            <fieldset>
                                <input id="inputsubmit1" type="submit" name="inputsubmit1" value="Salir" />
                            </fieldset>
                        </form>
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
            </div>
            <div id="main">
                <div id="welcome" class="post">
                    <h2 class="title">ADMINISTRACION DE CAJA</h2>
                    <div class="story">
                        <form id="f1" name="f1" method="post" action=""></form>
                        <fieldset>
                            <legend style="font-size:8px"><b>Recepción de Transferencia (MONTOS RECIBIDOS)</b></legend>
                            <table border="0" class='tabla' width="100%" cellpadding="5" cellspacing="5">
                                <tr>
                                    <th width="20%" align="center" style="font-size:10px;color:#000000">Fecha</th>
                                    <th width="20%" align="center" style="font-size:10px;color:#000000">Operacion</th>
                                    <th width="20%" align="center" style="font-size:10px;color:#000000">Monto</th>
                                    <th width="13%" align="center" style="font-size:10px;color:#000000">Moneda</th>
                                    <th width="15%" align="center" style="font-size:10px;color:#000000">&nbsp;</th>
                                    <th width="12%" align="center" style="font-size:10px;color:#000000">caja</th>
                                </tr>
                            </table>
                            <div style='overflow-y:auto; height:50px;width:100%' >
                                <table border="0" class='tabla' width="100%" cellpadding="2" cellspacing="2">
                                    <%
                                                if (codcaja == null) {
                                                    out.print("<td>ERROR EN LA SESION</td></table></div>");
                                                    out.close();
                                                }
                                                String hql1 = "from TTransferenciaCaja where codCajaDestino='" + codcaja + "' "
                                                        + "and fecha like '" + DateUtil.getDate(new Date()) + "%'";
                                                List resultTrans = d.findAll(hql1);
                                                Iterator itTrans = resultTrans.iterator();
                                                Double Monto1 = 0.00;
                                                int k1 = 0;
                                                while (itTrans.hasNext()) {
                                                    TTransferenciaCaja trans = (TTransferenciaCaja) itTrans.next();
                                                    Monto1 = trans.getMonto().doubleValue();
                                                    if (k1 % 2 == 0) {
                                                        out.println("<tr class='modo1'>");
                                                    } else {
                                                        out.println("<tr class='modo2'>");
                                                    }
                                    %>
                                    <td width="20%" style="font-size:10px;"><%=trans.getFecha()%></td>
                                    <td width="20%" style="font-size:10px;">RECEPCIÓN</td>
                                    <td width="20%" style="font-size:10px;"><%=fm.formatMoneda(Monto1)%></td>
                                    <td width="13%" style="font-size:10px;"><%=trans.getTOperacion().getTMoneda().getNombre()%></td>
                                    <td width="15%" style="font-size:10px; text-align: right;">
                                        <%if (trans.getIdope() == null) {%>
                                        <input type="button" value="Recepcionar" onclick="transferir('<%=trans.getIdtransferenciacaja().toString()%>')" /></td>
                                        <%} else {
                                                                                                out.print("RECEPCIONADO");
                                                                                            }%>
                                    <td width="12%" style="font-size:10px;"> <%=trans.getTCaja().getCodCaja()%></td>
                                    <%  out.println("</tr>");
                                                    k1++;
                                                }%>
                                </table>
                            </div>
                            <form action="" id="fup" name="fup">
                                <input type="button" value="REFRESCAR" onclick="document.fup.submit();" /></form>
                        </fieldset>
                        <form id="foper" name="foper" method="post" action="">
                            <fieldset>
                                <legend style="font-size:8px"><b>Operaciones Entre Cajas</b></legend>
                                <table border='0' cellpadding='5' cellspacing='5' class='tabla'  width='100%'>
                                    <tr>
                                        <th style='display:none'>&nbsp;</th><th>MONEDA</th>
                                        <th>SIMBOLO</th>
                                        <th>MONTO ACTUAL</th>
                                        <th>MONTO</th>
                                        <th>SALDO</th>
                                        <th>CAJA</th>
                                        <th>&nbsp;</th>
                                    </tr>
                                    <%
                                                //iniDetalleCaja.comprobarIni(session.getAttribute("USER_CODCAJA").toString(), session);
                                                /*String hqlm = "from TDetalleCaja where estado ='ACTIVO' "
                                                + " AND TCaja.codCaja ='" + session.getAttribute("USER_CODCAJA").toString() + "' "
                                                + " AND TMoneda.estado ='ACTIVO' and fechaTransaccion like '" + DateUtil.getDate(new Date()) + "%'";*/
                                                List result = d.findAll("from TLogFinance where idLogFinance like 'LOG" + codcaja + "___' order by idLogFinance DESC");
                                                Iterator MONE_DC = result.iterator();
                                                int j2 = 0;
                                                String cajasx = "";
                                                List res = d.findAll("from TCaja where estado ='ACTIVO' "
                                                        + " AND TFilial.codFilial ='" + codFilial + "' order by codCaja");
                                                Iterator in = res.iterator();
                                                while (in.hasNext()) {
                                                    TCaja caja3 = (TCaja) in.next();
                                                    if (!codcaja.equals(caja3.getCodCaja())) {
                                                        cajasx += "<option value='" + caja3.getCodCaja() + "' >" + caja3.getNombreCaja() + "</option>";
                                                    }
                                                }
                                                while (MONE_DC.hasNext()) {
                                                    //TDetalleCaja detCaja = (TDetalleCaja) MONE_DC.next();
                                                    TLogFinance detCaja = (TLogFinance) MONE_DC.next();
                                                    TMoneda Moneda = (TMoneda) d.load(TMoneda.class, detCaja.getIdlogfinance().substring(12));
                                                    if (j2 % 2 == 0) {
                                    %>
                                    <tr class='modo1'>
                                        <%} else {%>
                                    <tr class='modo2'>
                                        <%}%>
                                        <td id="CodigoMoneda<%=j2%>" style='display:none' >
                                            <%= Moneda.getCodMoneda()%>
                                        </td>
                                        <td>
                                            <%= Moneda.getNombre()%>
                                        </td>
                                        <td style='font-size:20px' >
                                            <center>
                                                <%= Moneda.getSimbolo()%>
                                            </center>
                                        </td>
                                        <td  id="montoActual<%=j2%>" style='text-align:right;'>
                                            <%=detCaja.getMontoFinal()%>
                                        </td>
                                        <td>
                                            <input id="montoTranferir<%=j2%>" type="text" name="" value='0.00'
                                                   onKeyPress="return(currencyFormat(this,',','.',event));"
                                                   style='font-size:20px;width:150px;text-align:right;'
                                                   onkeyup="actualizarM('<%=j2%>')" />
                                        </td>
                                        <td id="montoSaldo<%=j2%>" style='text-align:right;' >
                                            <%=detCaja.getMontoFinal()%>
                                        </td>
                                        <td>
                                            <select id="Caja<%=j2%>" name="Caja<%=j2%>">
                                                <%=cajasx%>
                                            </select>
                                        </td>
                                        <td>
                                            <input type="button" value="TRANSFERIR" onclick="TransferirMonto(<%=j2%>)"/>
                                        </td>
                                        <%
                                                        j2++;
                                                    }
                                                    out.println("<script language='JavaScript'>");
                                                    out.print("formaterMontos(" + j2 + ")");
                                                    out.println("</script>");
                                        %>
                                    </tr>
                                    <tr class='modo2'>
                                        <td colspan="7" align="center">
                                            <input type="button" value="CERRAR CAJA"  onclick="CerrarCaja('<%=session.getAttribute("USER_TIPOCAJA").toString()%>');"/>
                                        </td>
                                    </tr>
                                </table>
                            </fieldset>
                        </form>
                        <%  if (session.getAttribute("USER_TIPOCAJA").toString().equals("PRIMARY")) {%>
                        <br>
                        <br>
                        <%
                              int contador = 0;
                              result = d.findAll("from TCaja where tipo='SECONDARY' "
                                      + " AND TFilial.codFilial ='" + codFilial + "' and estado='ACTIVO' order by codCaja");
                              Iterator itCaja = result.iterator();
                              while (itCaja.hasNext()) {
                                  TCaja tcaja = (TCaja) itCaja.next();
                                  //iniDetalleCaja.comprobarIni(tcaja.getCodCaja(), session);
                                  out.println("<div id='caja" + contador + "'style='display:none' >" + tcaja.getCodCaja() + "</div>");
                        %>
                        <fieldset id="marco<%=contador%>"  >
                            <legend style="font-size:8px"><b>APERTURA DE CAJA</b></legend>
                            <table  border='0' cellpadding='5' cellspacing='5' width="100%">
                                <tr>
                                    <td>
                                        <font color="#385B88" style="font-size:12px"><b>Montos iniciales a asignar a la caja  :</b></font>
                                        <font color="#E08934" style="font-size:15px"><b><%=tcaja.getNombreCaja()%></b></font>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <table border='0' cellpadding='0' cellspacing='0' class='tabla'  width='100%'>
                                            <tr>
                                                <th style='display:none'>&nbsp;</th>
                                                <th align="center" style="font-size:10px;color:#000000">MONEDA</th>
                                                <th align="center" style="font-size:10px;color:#000000">SIMBOLO</th>
                                                <th align="center" style="font-size:10px;color:#000000">MONTO APERTURA</th>
                                                <th align="center" style="font-size:10px;color:#000000">ULTIMO CIERRE <br> DE CAJA</th>
                                                <th align="center" style="font-size:10px;color:#000000">MONTO ACTUAL</th>
                                                <th>&nbsp;</th>
                                            </tr>
                                            <%
                                                                              String deshabilitado = "";
                                                                              result = d.findAll("from TMoneda where estado ='ACTIVO'");
                                                                              Iterator itx = result.iterator();
                                                                              int k = 0;
                                                                              while (itx.hasNext()) {
                                                                                  deshabilitado = "";
                                                                                  TMoneda Moneda = (TMoneda) itx.next();
                                                                                  String fch = DateUtil.getDate(new Date());
                                                                                  String hql = "from TDetalleCaja where estado='ACTIVO' "
                                                                                          + " AND TMoneda.codMoneda='" + Moneda.getCodMoneda() + "'"
                                                                                          + " AND TCaja.codCaja='" + tcaja.getCodCaja() + "' "
                                                                                          + " AND EXISTS(from TTransferenciaCaja where TOperacion.TMoneda.codMoneda='" + Moneda.getCodMoneda() + "'"
                                                                                          + " AND codCajaDestino='" + tcaja.getCodCaja() + "'"
                                                                                          + " AND tipo='INICIAL'"
                                                                                          + " AND fecha LIKE '" + fch + "%')";
                                                                                  List resultW = d.findAll(hql);
                                                                                  //iniDetalleCaja.comprobarIni(tcaja.getCodCaja(), session);
                                                                                  TDetalleCaja tdc = (TDetalleCaja) d.load(TDetalleCaja.class, DateUtil.getDate(new Date()).replaceAll("/", "") + tcaja.getCodCaja() + Moneda.getCodMoneda() + "00");
                                                                                  if (tdc != null) {
                                                                                      int stdc = resultW.size();
                                                                                      if (stdc > 0) {
                                                                                          deshabilitado = "disabled";
                                                                                      }
                                                                                      if (k % 2 == 0) {
                                                                                          out.println("<tr class='modo1'>");
                                                                                      } else {
                                                                                          out.println("<tr class='modo2'>");
                                                                                      }
                                                                                      TTransferenciaCaja ttcaja = new TTransferenciaCaja();
                                                                                      String sqlTrans = "from TTransferenciaCaja where TCaja.codCaja='" + tdc.getTCaja().getCodCaja() + "' AND tipo='END' AND TOperacion.TMoneda.codMoneda='" + Moneda.getCodMoneda() + "'  order by  fecha desc ";
                                                                                      List lttcaja = d.findAll(sqlTrans);
                                                                                      int sl = lttcaja.size();
                                                                                      if (sl > 0) {
                                                                                          ttcaja = (TTransferenciaCaja) lttcaja.get(0);
                                                                                      } else {
                                                                                          ttcaja.setMonto(new BigDecimal("0"));
                                                                                      }
                                                                                      out.println("<td style='display:none' >" + Moneda.getCodMoneda() + "</td>");
                                                                                      out.println("<td >" + Moneda.getNombre() + "</td>");
                                                                                      out.println("<td >" + Moneda.getSimbolo() + "</td>");
                                                                                      out.println("<td ><input style='text-align:right;' id='monto" + Moneda.getCodMoneda() + contador + "' type='text' name='cuenta' disabled='true' "
                                                                                              + " onKeyPress=\"return(currencyFormat(this,',','.',event))\" "
                                                                                              + " onkeyup=\"actSaldo('" + Moneda.getCodMoneda() + "','" + k + "')\" "
                                                                                              + " value='" + fm.formatMoneda(ttcaja.getMonto().doubleValue()) + "' /></td>");
                                                                                      out.println("<td style='text-align:right;'>" + fm.formatMoneda(ttcaja.getMonto().doubleValue()) + "</td>");
                                                                                      out.println("<td style='text-align:right;'>" + fm.formatMoneda(tdc.getMontoFinal().doubleValue()) + "</td>");
                                                                                      out.println("<td><input id='cambiarMonto' " + deshabilitado + " type='button' name='cambiarMonto' value='CAMBIAR' onclick=\"abilitarTxt('monto" + Moneda.getCodMoneda() + contador + "')\" /></td>");
                                                                                      out.println("</tr>");
                                                                                      k++;
                                                                                  }
                                                                              }
                                            %>
                                        </table>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center">
                                        <input id="btn_tranferir<%=contador%>"  <%=deshabilitado%> type="button" name="btn_tranferir" onclick="Transferirx(<%=contador%>);" value="TRANSFERIR" />
                                    </td>
                                </tr>
                            </table>
                        </fieldset>
                        <%
                                            contador++;
                                        }//DEL WHILE
                                        out.println("<script language='JavaScript'>");
                                        out.print("numeroTcajas(" + contador + ");");
                                        out.println("</script>");
                                    }
                        %>
                    </div>
                    <div id="misoper">
                        <form id="misOperaciones" method="post" action="">
                            <table id="xxoper">
                                <tr>
                                    <td>
                                        <!--input type="button" onclick="full();" value="Ver las operaciones del día"-->
                                        <input type="button" onclick="if (window.open)window.open('ticketcuenta.htm?tipoOperacion=TODO','operacion','width=800,height=840,menubar=yes,resizable=yes,scrollbars=yes,status=yes');else alert('Su Navegador no dispone de esta opcion, Actualicelo con una version mas reciente');" value="REPORTAR OPERACIONES..." />
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <div id="divOperacionf">
                                            <table id="tablaOperacionf" class="tabla" border="0" style="display:none">
                                                <tr>
                                                    <th>&nbsp;&nbsp;&nbsp;</th>
                                                    <th>Num. Operaci&oacute;n</th>
                                                    <th>Fecha de Registro</th>
                                                    <th>Tipo Operaci&oacute;n</th>
                                                    <th>Moneda</th>
                                                    <th>Descripci&oacute;n</th>
                                                    <th>Estado</th>
                                                    <th>Usuario</th>
                                                    <th>Fecha Extorno</th>
                                                    <th>Admin Extorno</th>
                                                    <th>&nbsp;&nbsp;&nbsp;&nbsp;</th>
                                                </tr>
                                                <% int i = 0;

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
                                                    <td id="r" style="display:none"></td><td>a</td>
                                                    <td>a</td><td>a</td><td>a</td><td>a</td><td>a</td>
                                                    <td>a</td><td>a</td><td>a</td>
                                                    <td><input type="button" id="btnExtornarf<%=i%>" value="Extornar"></td>
                                                </tr>
                                                <% }%>
                                            </table>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </form>
                    </div>
                    <div>
                        <form id="frmOperaciones" method="post" action="">
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
                                                            <option>CAJA</option>
                                                            <option>COMPRA</option>
                                                            <option>VENTA</option>
                                                            <option>DEPOSITO O RETIRO</option>
                                                            <option>GIRO</option>
                                                            <option>COBROGIRO</option>
                                                            <option>PRESTAMO</option>
                                                            <option>COBRANZA</option>
                                                            <option>RETIRO OTRO</option>
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
                                                            i = 0;
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
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div id="divExtorno" style="display: none"></div>
        <%@include file="../common/footer.jsp" %>
    </body>
</html>
