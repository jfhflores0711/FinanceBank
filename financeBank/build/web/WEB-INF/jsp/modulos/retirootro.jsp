<%-- 
    Document   : newjsp
    Created on : 11/02/2010, 04:41:24 PM
    Author     : Admin
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
        <script type="text/javascript" src="js/validacion.js"></script>
        <script type="text/javascript" src="js/scriptConvertTest.js"></script>
        <script type="text/javascript" src="js/scriptshow/scriptRetiroOtro.js"></script>
    </head>
    <body onload="document.getElementById('monto').focus();">
        <%
                    request.setAttribute("idmenu", "20110228202911555566");
        %>
        <%@include file="../common/header.jsp" %>
        <div id="content">
            <div id="sidebar">
                <div id="menu">
                    <%@include file="../common/menu.jsp" %>
                </div>
                <div id="login" class="boxed">
                    <h2 class="title">Cuenta del Cliente</h2>
                    <div class="content">
                        <form id="form1" method="post" action="principal.htm">
                            <fieldset>
                                <input id="inputsubmit1" type="submit" name="inputsubmit1" value="Salir" />
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
            <%
                        String codigo = request.getParameter("moneda");
                        List result = d.findAll("from TMoneda where estado ='ACTIVO'");
                        Iterator itMONEDA = result.iterator();
                        String sel = "";
                        TLogFinance tdetallecaja = null;
                        //TPatrimonioTransit tpatrimonio = new TPatrimonioTransit();
                        String det = "";
                        while (itMONEDA.hasNext()) {
                            TMoneda Moneda = (TMoneda) itMONEDA.next();
                            if (codigo == null) {
                                if (Moneda.getCodMoneda().equals("PEN")) {
                                    sel = "selected";
                                    codigo = "PEN";
                                    //String hql = "from TPatrimonioTransit where estado ='ACTIVO" + session.getAttribute("USER_CODFILIAL").toString() + "'"
                                    //      + " AND TBalancexmoneda.TMoneda.codMoneda ='" + codigo + "' ";
                                    //System.out.println("HQL>" + hql);
                                    //tpatrimonio = (TPatrimonioTransit) d.findAll(hql).get(0);
                                    tdetallecaja = (TLogFinance) d.load(TLogFinance.class, "LOG" + session.getAttribute("USER_CODCAJA").toString() + codigo);
                                } else {
                                    sel = "";
                                }
                            } else {
                                if (Moneda.getCodMoneda().equals(codigo)) {
                                    sel = "selected";
                                    //String hql = "from TPatrimonioTransit where estado ='ACTIVO" + session.getAttribute("USER_CODFILIAL").toString() + "'"
                                    //        + " AND TBalancexmoneda.TMoneda.codMoneda ='" + codigo + "' ";
                                    //System.out.println("HQL>" + hql);
                                    //tpatrimonio = (TPatrimonioTransit) d.findAll(hql).get(0);
                                    tdetallecaja = (TLogFinance) d.load(TLogFinance.class, "LOG" + codcaja + codigo);
                                } else {
                                    sel = "";
                                }
                            }
                            det += "<option label='" + Moneda.getSimbolo() + "'  value='" + Moneda.getCodMoneda() + "' " + sel + ">" + Moneda.getSimbolo() + "</option>";
                        }
                        //adminCapital ak = new adminCapital(codigo, session.getAttribute("USER_ID").toString(), session.getAttribute("USER_IP").toString());
%>
            <div id="main">
                <div id="welcome" class="post">
                    <h2 class="title">RETIROS EXTRAS</h2>
                    <div class="story">
                        <form id="f1" name="f1" method="post" action="">
                            <fieldset>
                                <legend><b>Datos del Retiro</b></legend>
                                <table  border="0"  cellpadding="10" cellspacing="10" width="100%">
                                    <tr>
                                        <td colspan="5" align="center">Utilidad total:
                                            <input type="text" readonly value="<%=fm.formatMoneda(adminCapital.xCalcularUtilidad(codcaja))%>" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <th align="center" style="font-size:10px;color:#ffffff" bgcolor="#ECB784" >
                                            Moneda
                                        </th>
                                        <th align="center" style="font-size:10px;color:#ffffff" bgcolor="#ECB784">
                                            Lugar de retiro
                                        </th>
                                        <th align="center" style="font-size:10px;color:#ffffff" bgcolor="#ECB784">
                                            Disponible
                                        </th>
                                        <th align="center" style="font-size:10px;color:#ffffff" bgcolor="#ECB784">
                                            Disponible en caja
                                        </th>
                                        <th align="center" style="font-size:10px;color:#ffffff" bgcolor="#ECB784">
                                            Monto a Retirar
                                        </th>
                                    </tr>
                                    <tr>
                                        <td>
                                            <select name="moneda" style="font-size:14px"  onchange="document.f1.submit()" >
                                                <%=det%>
                                            </select>
                                        </td>
                                        <td>
                                            <select name="lugarR" style="font-size:14px" onchange="document.f1.submit()">
                                                <%
                                                            String sel1 = "";
                                                            String sel2 = "";
                                                            Double Disponible = 0.00;
                                                            String lugarR = request.getParameter("lugarR");
                                                            if (request.getParameter("lugarR") == null) {
                                                                sel1 = "selected";
                                                                Disponible = adminCapital.utilidad(codigo, codcaja);
                                                            } else {
                                                                if (lugarR.equals("UTILIDAD")) {
                                                                    Disponible = adminCapital.utilidad(codigo, codcaja);
                                                                    sel1 = "selected";
                                                                } else {
                                                                    //Falta Incluir a todos los cajas del filial
                                                                    Disponible = tdetallecaja.getPatrimonio().doubleValue();
                                                                    sel2 = "selected";
                                                                }
                                                            }
                                                            out.println("<option value='UTILIDAD' " + sel1 + "> UTILIDAD </option>");
                                                            out.println("<option value='PATRIMONIO' " + sel2 + "> PATRIMONIO </option>");
                                                %>
                                            </select>
                                        </td>
                                        <td>
                                            <input id="disponible" type="text" style="font-size:14px;width:140px;color:#018d00;background:transparent;text-align:right" readonly='true' name="giro_neto"
                                                   value="<%=fm.formatMoneda(Disponible)%>" />
                                        </td>
                                        <td>
                                            <input id="diaponibleCaja" type="text" style="font-size:14px;width:140px;color:#018d00;background:transparent;text-align:right" readonly='true' name="giro_neto"
                                                   value="<%=fm.formatMoneda(tdetallecaja.getMontoFinal().doubleValue())%>" />
                                        </td>
                                        <td>
                                            <input id="monto" type="text" style="font-size:14px;width:140px;text-align:right" name="monto" value="0.00"  onKeyPress="return(currencyFormat(this,',','.',event))"  onkeyup="val_monto()" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="5" align="center">
                                            <table>
                                                <tr>
                                                    <td valign="top">
                                                        <font color="#385B88" style="font-size:12px">MOTIVO DE RETIRO</font>
                                                    </td>
                                                    <td>
                                                        <textarea id="motivo" name="motivo" rows="3" cols="40"
                                                                  onkeydown="if(this.value.length >= 100){ window.alert('Has superado el tama&ntilde;o m&aacute;ximo permitido');this.value=this.value.substring(0,99); return false; }"
                                                                  ></textarea>
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="5" align="center">
                                            <input id="inputsubmit1" type="button" name="inputsubmit1" onclick="procesarRetiroOtro();" value="PROCESAR" />
                                        </td>
                                    </tr>
                                </table>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="../common/footer.jsp" %>
    </body>
</html>
