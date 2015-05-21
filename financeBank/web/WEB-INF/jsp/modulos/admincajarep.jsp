<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8" />
        <link rel="shortcut icon" href="./images/favicon.ico" />
        <title>FinanceBank - ReporteCajas</title>
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
        <script language="JavaScript" type="text/JavaScript" src="js/scriptshow/scriptAdmCajarep.js"></script>
        <style type="text/css" media="All">
            body{font-size:0.9em;}
            #content {width: 1100px;}
        </style>
        <style type="text/css" media="print">
            #logo{ display:none;}
            #ctrl{display:none;}
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
            <%
                        String cajaX = (String) request.getParameter("caja");
                        if (cajaX == null) {
                            cajaX = "ERROR";
                            out.print("ERROR DE PAR&Aacute;METROS!!");
                        }
            %>
            <div class="ticketpago" id="ticketpago">
                <%
                            if (cajaX.equals("ERROR")) {
                                return;
                            }
                            List cajas = d.findAll("from TCaja where TFilial.codFilial = '" + cajaX.substring(0, 4) + "' order by codCaja");
                            if (!cajas.isEmpty()) {
                                Iterator it = cajas.iterator();
                                while (it.hasNext()) {
                                    TCaja xca = (TCaja) it.next();
                                    List pc = d.findAll("from TPersonaCaja where TCaja.codCaja='" + xca.getCodCaja() + "' and estado = 'ACTIVO'");
                                    String cajero = "NO IDENTIFICADO";
                                    if (pc.size() > 0) {
                                        TPersonaCaja xpc = (TPersonaCaja) pc.iterator().next();
                                        cajero = xpc.getTPersona().getNombre() + " " + xpc.getTPersona().getApellidos();
                                    }
                                    out.print(xca.getNombreCaja() + ": " + cajero + "<br />");
                                    String reporte = "NO TIENE DATOS";
                                    //List xDetalles = d.findAll("from TDetalleCaja where TCaja.codCaja='" + xca.getCodCaja() + "' and estado = 'ACTIVO' and fecha_transaccion like '" + DateUtil.getDate(new Date()) + "%' order by TMoneda.codMoneda");
                                    List xDetalles = d.findAll("from TLogFinance where idLogFinance like 'LOG" + xca.getCodCaja() + "___'");
                                    if (xDetalles.size() > 0) {
                                        Iterator it2 = xDetalles.iterator();
                                        BigDecimal df = BigDecimal.ZERO;
                                        reporte = "<table><tr><td>";
                                        while (it2.hasNext()) {
                                            TLogFinance det = (TLogFinance) it2.next();
                                            TMoneda mo = (TMoneda) d.load(TMoneda.class, det.getIdlogfinance().substring(12));
                                            reporte += mo.getNombre() + " (" + mo.getSimbolo() + ") </td><td align='right'>";
                                            reporte += det.getMontoFinal().toString() + "</td></tr><tr><td>";
                                            if (!mo.getCodMoneda().equals("PEN")) {
                                                List lt = d.findAll("from TTasa where estado ='ACTIVO' AND TTipoCambio.TMoneda.codMoneda ='" + mo.getCodMoneda() + "' "
                                                        + " AND tipoTasa ='TASA MERCADO' AND TTipoCambio.codMonedaA ='PEN' ");
                                                TTasa xTa = (TTasa) lt.get(0);
                                                df = df.add(det.getMontoFinal().multiply(xTa.getFConversion()));
                                            } else {
                                                df = df.add(det.getMontoFinal());
                                            }
                                        }
                                        reporte += "</td></tr><tr><td>Total: </td><td>" + df.setScale(2, RoundingMode.HALF_UP);
                                        reporte += "</td></tr></table>";
                                    }
                                    out.print(reporte + "<br />");
                                    out.print("-----------------------<br />");
                                }
                            }
                            out.println("<div id='ctrl'>");
                            out.println("<center>");
                            out.println("<br>");
                            out.println("<input name='Submit' id='Submit' type='button' onClick=\"document.title=''; if (window.print)window.print();else alert('Su Navegador no dispone de esta opcion, Actualicelo con una version mas reciente');\" value='Imprimir'>");
                            out.println("<input type='button' value='Cerrar Ventana' onclick='window.close();'");
                            out.println("</center>");
                            out.println("</div>");
                %>
            </div>
        </div>
    </body>
</html>