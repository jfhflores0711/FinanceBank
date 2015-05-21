<%--
    Document   : calculadora
    Created on : 11/02/2013, 04:41:24 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8" />
        <link rel="shortcut icon" href="./images/favicon.ico" />
        <title>FinanceBank calculadora</title>
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
        <script type="text/javascript" src="js/validacion.js"> </script>
        <link href="css/themes/default.css" rel="stylesheet" type="text/css"/>
        <link href="css/themes/alert.css" rel="stylesheet" type="text/css"/>
        <link href="css/themes/alphacube.css" rel="stylesheet" type="text/css"/>
        <script type="text/javascript" src="js/scriptMath.js"></script>
        <script type="text/javascript" src="js/scriptConvertTest.js"></script>
        <script type="text/javascript" src="js/scriptshow/scriptCalc.js"></script>
    </head>
    <body>
        <%@include file="../common/header.jsp" %>
        <%
                    session.setAttribute("CADENA_SUMA", "");
                    request.setAttribute("idmenu", "20121029195228421011");
        %>
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
            <div id="main">
                <div id="welcome" class="post">
                    <h2 class="title">MODULO DE CALCULADORA</h2>
                    <div class="story">
                        <form name="fcambio" method="POST" action="">
                            <br>
                            <center>
                                <table border='0' cellpadding='0' cellspacing='0' width="100%">
                                    <tr>
                                        <td valign="top" align="center">
                                            <table border='0' cellpadding='4' cellspacing='4' class='tabla' bgcolor="#f1ffed">
                                                <tr>
                                                    <td valign="top" width="100%">
                                                        <fieldset>
                                                            <legend style="font-size:12px"><b>SELECCIONE LA MONEDA</b> </legend>
                                                            <table border='0' cellpadding='5' cellspacing='5' class='tabla' width='100%'>
                                                                <tr>
                                                                    <td>
                                                                        <table border="0" width='100%'>
                                                                            <tr>
                                                                                <td align="right">
                                                                                    <select name="moneda" id="moneda" style="font-size:20px" onchange="document.fcambio.submit()" >
                                                                                        <%
                                                                                                    String codigo = request.getParameter("moneda");
                                                                                                    List Presult = d.findAll("from TMoneda where estado ='ACTIVO'");
                                                                                                    Iterator itPmoneda = Presult.iterator();
                                                                                                    String sel = "";
                                                                                                    String nombreM = "";
                                                                                                    String ColorM = "";
                                                                                                    while (itPmoneda.hasNext()) {
                                                                                                        sel = "";
                                                                                                        TMoneda Moneda = (TMoneda) itPmoneda.next();
                                                                                                        if (codigo == null) {
                                                                                                            if (Moneda.getCodMoneda().equals("PEN")) {
                                                                                                                sel = "selected";
                                                                                                                codigo = "PEN";
                                                                                                                nombreM = Moneda.getNombre();
                                                                                                                ColorM = Moneda.getColor();
                                                                                                            }
                                                                                                        } else {
                                                                                                            if (Moneda.getCodMoneda().equals(codigo)) {
                                                                                                                sel = "selected";
                                                                                                                nombreM = Moneda.getNombre();
                                                                                                                ColorM = Moneda.getColor();
                                                                                                            }
                                                                                                        }
                                                                                                        out.println("<option label='" + Moneda.getCodMoneda() + "(" + Moneda.getSimbolo() + ")' style='color:" + Moneda.getColor() + ";' value='" + Moneda.getCodMoneda() + "' " + sel + ">.:" + Moneda.getSimbolo() + ":.</option>");
                                                                                                    }
                                                                                        %>
                                                                                    </select>
                                                                                    <%
                                                                                                out.println("<script language='JavaScript'> document.getElementById('moneda').focus();</script>");
                                                                                    %>
                                                                                </td>
                                                                                <td style="font-size:20px;font-weight:bold;color:<%=ColorM%>" align="left">
                                                                                    <%=nombreM%>
                                                                                </td>
                                                                            </tr>
                                                                        </table>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td colspan="2" align="center">
                                                                        <%
                                                                                    out.println("<div id='oscar' style='overflow-y:auto; height:250px;width:500px' >");
                                                                                    out.println("<fieldset>");
                                                                                    out.println("<table border='0' width='100%'>");
                                                                                    String hql_denom = "from TDenominacionMoneda where TMoneda.codMoneda='" + codigo + "' order by orden desc ";
                                                                                    List lDenomin = d.findAll(hql_denom);
                                                                                    Iterator itDenomin = lDenomin.iterator();
                                                                                    int i = 0;
                                                                                    while (itDenomin.hasNext()) {
                                                                                        TDenominacionMoneda denom = (TDenominacionMoneda) itDenomin.next();
                                                                                        out.println("<tr>");
                                                                                        out.println("<td align='center' valign='center'>");
                                                                                        out.println("<img src='billetesmonedas/" + denom.getImagen() + "' width='131' height='60' alt='" + denom.getMonto().toString() + " " + denom.getTMoneda().getNombre() + "'/>");
                                                                                        out.println("</td><td><label id='montoMoneda" + i + "' style='font-size:12px;width:50px;text-align:right;background:transparent' title='" + denom.getIddenominacionmoneda() + "' >" + denom.getMonto() + "</label>" + " " + denom.getTMoneda().getSimbolo());
                                                                                        out.println("</td>");
                                                                                        out.println("<td>");
                                                                                        out.println("<input id='totalDinero" + i + "' type='text' style='font-size:12px;width:50px;text-align:right' name='monto" + i + "' value='0.00' onFocus='this.select();' onKeyPress='return solodecimales(event);'  onblur='calTotal(" + i + ");' />");
                                                                                        out.println("</td>");
                                                                                        out.println("<td>");
                                                                                        out.println("<label id='Cantidad" + i + "' style='font-size:12px;width:70px;text-align:right;color:red;font-weight:bold'>0</label>");
                                                                                        out.println("</td>");
                                                                                        out.println("</tr>");
                                                                                        i++;
                                                                                    }
                                                                                    out.println("</table>");
                                                                                    out.println("</fieldset>");
                                                                                    out.println("</div>");
                                                                                    out.println("<fieldset>");
                                                                                    out.println("<table width='100%'>");
                                                                                    out.println("<tr>");
                                                                                    out.println("<td align='right' width='50%'><input id='total' type='hidden' value='" + i + "' />");
                                                                                    out.println("TOTAL");
                                                                                    out.println("</td>");
                                                                                    out.println("<td align='left'> ");
                                                                                    out.println("<input id='sumaTotal' name='sumatotal' type='text' style='font-size:20px;width:150px;text-align:right' value='0.00' readonly='true' />");
                                                                                    out.println("</td>");
                                                                                    out.println("</tr>");
                                                                                    out.println("<tr>");
                                                                                    out.println("<td align='center'> ");
                                                                                    out.println("<input type='button' name='Grabar' value='Grabar' onclick='grabar()' />");
                                                                                    out.println("</td>");
                                                                                    out.println("<td align='center'> ");
                                                                                    out.println("<input type='button' name='print' value='Imprimir' onclick='printM()' />");
                                                                                    out.println("</td>");
                                                                                    out.println("</tr>");
                                                                                    out.println("</table>");
                                                                                    out.println("</fieldset>");
                                                                        %>
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
                                        <th width="27%" align="center" style="font-size:10px;color:#000000">Caja</th>
                                        <th width="30%" align="center" style="font-size:10px;color:#000000">Monto</th>
                                        <th width="24%" align="center" style="font-size:10px;color:#000000">Codigo</th>
                                        <th width="19%" align="center" style="font-size:10px;color:#000000">&nbsp;</th>
                                    </tr>
                                </table>
                                <div id='calcSumas' style='overflow-y:auto; height:100px;width:100%;' >
                                    <table border="0" class='tabla' width="100%" cellpadding="2" cellspacing="2">
                                        <%
                                                    String hql = "from TSumaMoneda where estado='" + codcaja + codigo + "'"
                                                            + " AND idoperacion like 'S%' AND idsumamoneda like '" + DateUtil.getDate(new Date()).replace("/", "") + "%' order by idsumamoneda asc";
                                                    List resultOp = d.findAll(hql);
                                                    Iterator itOp = resultOp.iterator();
                                                    int k = 0;
                                                    while (itOp.hasNext()) {
                                                        TSumaMoneda opreg = (TSumaMoneda) itOp.next();
                                                        List lSumaM = d.findAll("from TDetalleSuma where TSumaMoneda.idsumamoneda='" + opreg.getIdsumamoneda() + "' ");
                                                        Iterator itSumaM = lSumaM.iterator();
                                                        Double montoT = 0.0;
                                                        String den = "";
                                                        while (itSumaM.hasNext()) {
                                                            TDetalleSuma dsum = (TDetalleSuma) itSumaM.next();
                                                            TDenominacionMoneda denom = dsum.getTDenominacionMoneda();
                                                            den = denom.getTMoneda().getSimbolo() + " ";
                                                            Double monto = Double.parseDouble(denom.getMonto()) * dsum.getCantidad();
                                                            montoT += monto;
                                                        }
                                                        if (k % 2 == 0) {
                                                            out.println("<tr class='modo1'>");
                                                        } else {
                                                            out.println("<tr class='modo2'>");
                                                        }
                                        %>
                                        <td width="27%" style="font-size:10px;"><%=opreg.getEstado()%></td>
                                        <td width="30%" style="font-size:10px;"><%=(den + fm.formatMoneda(montoT))%></td>
                                        <td width="24%" style="font-size:10px;"><%=opreg.getIdoperacion()%></td>
                                        <td width="19%" style="font-size:10px; text-align: right;"><input type="button" value="Visualizar" onclick="RecargarSumarCalcular('<%=opreg.getIdsumamoneda()%>')" /></td>
                                            <% out.println("</tr>");
                                                            k++;
                                                        }%>
                                    </table>
                                </div>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="../common/footer.jsp" %>
    </body>
</html>
