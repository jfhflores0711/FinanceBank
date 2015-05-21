<%--
    Document   : newjsp
    Created on : 11/02/2010, 04:41:24 PM
    Author     : eloy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8" />
        <link rel="shortcut icon" href="./images/favicon.ico" />
        <title>FinanceBank Cambio de Moneda User</title>
        <meta name="keywords" content="FinanceBank" />
        <meta name="description" content="Sistema financiero" />
        <link href="default.css" rel="stylesheet" type="text/css" />
        <link href='css/tabla.css' rel='stylesheet' type='text/css' />
        <link rel="stylesheet" href="css/form.css" media="screen" />
        <script type="text/javascript" src="js/custom-form-elements.js"></script>
        <script type="text/javascript" src="js/prototype.js"> </script>
        <script type="text/javascript" src="js/effects.js"> </script>
        <script type="text/javascript" src="js/window.js"> </script>
        <script type="text/javascript" src="js/debug.js"> </script>
        <link href="css/themes/default.css" rel="stylesheet" type="text/css"/>
        <link href="css/themes/alert.css" rel="stylesheet" type="text/css"/>
        <link href="css/themes/alphacube.css" rel="stylesheet" type="text/css"/>
        <script type="text/javascript" src="js/scriptMath.js"></script>
        <script type="text/javascript" src="js/scriptConvertTest.js"></script>
        <script type="text/javascript" src="js/scriptshow/scriptCambioUser.js"></script>
        <script type="text/javascript" src="js/validacion.js"></script>
    </head>
    <body>
        <%@include file="../common/header.jsp" %>
        <%
                    session.setAttribute("CADENA_SUMA", "");
                    request.setAttribute("idmenu", "20110228202911490823");
                    String idpc = (String) session.getAttribute("USER_ID_PERSONA_CAJA");
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
                    <h2 class="title">MODULO DE CAMBIO DE MONEDA</h2>
                    <div class="story">
                        <form name="fcambio" action="">
                            <br>
                            <center>
                                <table border='0' cellpadding='0' cellspacing='0' width="100%">
                                    <tr>
                                        <td valign="top" align="center">
                                            <table border='0' cellpadding='4' cellspacing='4' class='tabla' bgcolor="#f1ffed">
                                                <tr>
                                                    <td align="right">
                                                        Tasa Especial <input type="checkbox" name="tasaEspecial" id="tasaEspecial" value="ON" onchange="ventanaNueva();" />
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <select name="persona" id="pe" onchange="f_razonSocial();">
                                                            <option value="n">Natural</option>
                                                            <option value="j">Jurídica</option>
                                                        </select>
                                                        <div id="na">Nombre: <input type="text" name="nom" id="nom" />
                                                            &nbsp;&nbsp;<br>DNI: <input type="text" name="doc" id="doc"/>&nbsp;&nbsp;</div>
                                                        <div id="ju" style="display: none">
                                                        Razon Social: <input type="text" name="razonSocial" id="razonSocial" />
                                                        &nbsp;&nbsp;<br>RUC. <input type="text" name="txtRazonSocial" id="txtRazonSocial"/>&nbsp;&nbsp;</div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td valign="top" width="100%">
                                                        <fieldset>
                                                            <legend style="font-size:12px"><b>CAMBIOS DE DINERO</b> </legend>
                                                            <table border='0' cellpadding='5' cellspacing='5' class='tabla' width='100%'>
                                                                <tr>
                                                                    <td>
                                                                        <table border="0" width='100%'>
                                                                            <tr>
                                                                                <td colspan="2">
                                                                                    <fieldset>
                                                                                        <table bgcolor="#ffffff" border="0" width="100%">
                                                                                            <tr>
                                                                                                <td >
                                                                                                    <input type="radio" size="20" onclick="compraVenta();" name="cambio" value="COMPRA">
                                                                                                </td>
                                                                                                <td style="font-size:20px;font-weight:bold;color:#385B88">
                                                                                                    COMPRA
                                                                                                </td>
                                                                                                <td>
                                                                                                    &nbsp;&nbsp;&nbsp;&nbsp
                                                                                                </td>
                                                                                                <td>
                                                                                                    <input type="radio" onclick="compraVenta();" name="cambio" value="VENTA" >
                                                                                                </td>
                                                                                                <td style="font-size:20px;font-weight:bold;color:#385B88">
                                                                                                    VENTA
                                                                                                </td>
                                                                                            </tr>
                                                                                        </table>
                                                                                    </fieldset>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td align="right">
                                                                                    <select name="moneda" style="font-size:20px" onchange="document.fcambio.submit()" >
                                                                                        <%
                                                                                                    String codigo = request.getParameter("moneda");
                                                                                                    List Presult = d.findAll("from TMoneda where estado ='ACTIVO'");
                                                                                                    Iterator itPmoneda = Presult.iterator();
                                                                                                    String sel = "";
                                                                                                    String nombreM = "";
                                                                                                    String ColorM = "";
                                                                                                    while (itPmoneda.hasNext()) {
                                                                                                        TMoneda Moneda = (TMoneda) itPmoneda.next();
                                                                                                        if (request.getParameter("moneda") == null) {
                                                                                                            if (Moneda.getCodMoneda().equals("USD")) {
                                                                                                                sel = "selected";
                                                                                                                codigo = "USD";
                                                                                                                nombreM = Moneda.getNombre();
                                                                                                                ColorM = Moneda.getColor();
                                                                                                            } else {
                                                                                                                sel = "";
                                                                                                            }
                                                                                                        } else {
                                                                                                            if (Moneda.getCodMoneda().equals(codigo)) {
                                                                                                                sel = "selected";
                                                                                                                nombreM = Moneda.getNombre();
                                                                                                                ColorM = Moneda.getColor();
                                                                                                            } else {
                                                                                                                sel = "";
                                                                                                            }
                                                                                                        }
                                                                                                        if (!Moneda.getCodMoneda().equals("PEN")) {
                                                                                                            out.println("<option label='" + Moneda.getCodMoneda() + "(" + Moneda.getSimbolo() + ")' value='" + Moneda.getCodMoneda() + "' " + sel + ">" + Moneda.getSimbolo() + "</option>");
                                                                                                        }
                                                                                                    }
                                                                                        %>
                                                                                    </select>
                                                                                    <%
                                                                                                out.println("<script language='JavaScript'> document.getElementById('tasaEspecial').focus();</script>");
                                                                                    %>
                                                                                </td>
                                                                                <td style="font-size:20px;font-weight:bold;color:<%=ColorM%>" align="left">
                                                                                    <%=nombreM%>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td colspan="2" align="center">
                                                                                    <fieldset>
                                                                                        <table>
                                                                                            <tr>
                                                                                                <td id="factor" style="font-size:20px;font-weight:bold;color:<%=ColorM%>" colspan="5" align="center">
                                                                                                </td>
                                                                                            </tr>
                                                                                            <tr>
                                                                                                <td>
                                                                                                    <font color="#385B88" style="font-size:12px">Tasa Especial
                                                                                                        <input id="Tasa_Especial" type="text" style="font-size:13px;width:100px;text-align:right;" name="Tasa_Especial" value="0.000" onKeyPress="return(currencyFormat_decimal(this,',','.',event,3));" onkeyup="calculo();" onfocus="this.select();" disabled />
                                                                                                    </font>
                                                                                                </td>
                                                                                            </tr>
                                                                                            <tr>
                                                                                                <td>
                                                                                                    <font color="#385B88" style="font-size:12px">Descuento por deterioro
                                                                                                        <input id="descuento_deterioro" type="text" style="font-size:12px;width:80px;text-align:right" name="descuento_deterioro" value="0.0" onKeyPress="return(currencyFormat_decimal(this,',','.',event,1))" onkeyup="f_deterioro(this.value)" disabled/>
                                                                                                    </font>
                                                                                                </td>
                                                                                            </tr>
                                                                                        </table>
                                                                                    </fieldset>
                                                                                </td>
                                                                            </tr>
                                                                        </table>
                                                                    </td>
                                                                <tr>
                                                                    <td colspan="2" align="center">
                                                                        <fieldset>
                                                                            <table border="0" cellspacing="10" cellpadding="10" >
                                                                                <tr>
                                                                                    <td style="font-size:20px">
                                                                                        <font color="#385B88" style="font-size:12px">
                                                                                            <b>
                                                                                                <div id="tipo1">
                                                                                                </div>
                                                                                            </b>
                                                                                        </font>
                                                                                    </td>
                                                                                    <td style="font-size:20px;font-weight:bold;color:<%=ColorM%>" id="SinMonedaINI">
                                                                                    </td>
                                                                                    <td align="center" >
                                                                                        <input id="monto1" type="text" style="font-size:20px;width:140px;text-align:right" name="monto1" value="0.00" onKeyPress="return validar(event);" onblur="currencyFormatF(this,',','.');calculo();" onfocus="this.select();" disabled/>
                                                                                        <input id="monto1button" type="button" style="font-size:7px;" value="&#8721;" onclick="sumarCalcular(this.label,1)" />
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td style="font-size:20px">
                                                                                        <font color="#385B88" style="font-size:12px">
                                                                                            <b>
                                                                                                <div id="tipo2">
                                                                                                </div>
                                                                                            </b>
                                                                                        </font>
                                                                                    </td>
                                                                                    <td style="font-size:20px;font-weight:bold;color:<%=ColorM%>" id="SinMonedaFIN">
                                                                                    </td>
                                                                                    <td align="center" >
                                                                                        <input id="monto2" style="font-size:20px;width:140px;text-align:right;color:<%=ColorM%>" name="monto2" value="0.00" onKeyPress="return(currencyFormat(this,',','.',event))" onkeyup="calculo2();" disabled/>
                                                                                        <input id="monto2button" type="button" style="font-size:7px;" value="&#8721;" onclick="sumarCalcular(this.label,2)" />
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td colspan="4" align="center">
                                                                                        <input id="inputsubmitx" size="1em" type="button" name="registrar" value="        EFECTUAR OPERACION          " onclick="RegistrarCambio();"/>
                                                                                    </td>
                                                                                </tr>
                                                                            </table>
                                                                        </fieldset>
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                            <div id="div_sumas"></div>
                                                        </fieldset>
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td valign="top" align="center">
                                            <fieldset>
                                                <div id="div_calcular">
                                                </div>
                                            </fieldset>
                                        </td>
                                    </tr>
                                </table>
                            </center>
                            <br>
                            <br>
                            <fieldset>
                                <table border="0" class='tabla' width="100%" cellpadding="5" cellspacing="5">
                                    <tr>
                                        <th width="17%" align="center" style="font-size:10px;color:#000000">Fecha</th>
                                        <th width="22%" align="center" style="font-size:10px;color:#000000">N&deg; OP</th>
                                        <th width="16%" align="center" style="font-size:10px;color:#000000">Operacion</th>
                                        <th width="16%" align="center" style="font-size:10px;color:#000000">Monto</th>
                                        <th width="16%" align="center" style="font-size:10px;color:#000000">Moneda</th>
                                        <th width="19%" align="center" style="font-size:10px;color:#000000">&nbsp;</th>
                                        <th width="1%" align="center" style="font-size:10px;color:#000000">&nbsp;</th>
                                    </tr>
                                </table>
                                <div id='extorno' style='overflow-y:auto; height:100px;width:100%' >
                                    <table border="0" class='tabla' width="100%" cellpadding="2" cellspacing="2">
                                        <%
                                                    String hql = "from TRegistroCompraVenta where TOperacion.TPersonaCaja.idpersonacaja='" + idpc + "' "
                                                            + " AND TOperacion.TPersonaCaja.TCaja.codCaja='" + codcaja + "'"
                                                            + " AND (TOperacion.TTipoOperacion.codigoTipoOperacion ='TIPC1' OR TOperacion.TTipoOperacion.codigoTipoOperacion ='TIPC2') AND estado='ACTIVO' AND TOperacion.estado='ACTIVO' AND fecha like '" + formatFecha.get().substring(0, 10) + "%' order by fecha desc";
                                                    List resultOp = d.findAll(hql);
                                                    Iterator itOp = resultOp.iterator();
                                                    Double Monto = 0.00;
                                                    String Tipo = "";
                                                    int k = 0;
                                                    while (itOp.hasNext()) {
                                                        TRegistroCompraVenta opreg = (TRegistroCompraVenta) itOp.next();
                                                        if (opreg.getTOperacion().getTTipoOperacion().getCodigoTipoOperacion().equals("TIPC1")) {
                                                            Monto = opreg.getMontoRecibido().doubleValue();
                                                            Tipo = "TIPC1";
                                                        } else {
                                                            Monto = opreg.getMontoEntregado().doubleValue();
                                                            Tipo = "TIPC2";
                                                        }
                                                        if (k % 2 == 0) {
                                                            out.println("<tr class='modo1'>");
                                                        } else {
                                                            out.println("<tr class='modo2'>");
                                                        }
                                        %>
                                        <td width="20%" style="font-size:10px;"><%=opreg.getFecha()%></td>
                                        <td width="20%" style="font-size:10px;"><%=opreg.getTOperacion().getNumeroOperacion()%></td>
                                        <td width="20%" style="font-size:10px;"><%
                                                                                                out.println(opreg.getTOperacion().getTTipoOperacion().getNombre() + " ");
                                                                                                List lSumaM = d.findAll("from TSumaMoneda where idoperacion='" + opreg.getTOperacion().getIdOperacion() + "' ");
                                                                                                Iterator itSumaM = lSumaM.iterator();
                                                                                                int is = 0;
                                                                                                while (itSumaM.hasNext()) {
                                                                                                    TSumaMoneda sum = (TSumaMoneda) itSumaM.next();
                                                                                                    out.println("<input id='sum" + is + "' type='button' style='font-size:7px;' value='S-" + is + "' onclick=\"RecargarSumarCalcular('" + sum.getIdsumamoneda() + "')\" />");
                                                                                                    is++;
                                                                                                }
                                            %></td>
                                        <td width="20%" style="font-size:10px;"><%=fm.formatMoneda(Monto)%></td>
                                        <td width="20%" style="font-size:10px;"><%=opreg.getTOperacion().getTMoneda().getNombre()%></td>
                                        <td width="19%" style="font-size:10px; text-align: right;"><input type="button" value="Extornar" onclick="extornar('<%=opreg.getIdregistrocompraventa().toString()%>','<%=Tipo%>')" /></td>
                                            <% out.println("</tr>");
                                                            k++;
                                                        }%>
                                    </table>
                                </div>
                            </fieldset>
                        </form>
                        <form id="frmimprimir" name="frmimprimir" method="post" action="vercambiomoneda.htm">
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="../common/footer.jsp" %>
    </body>
</html>
