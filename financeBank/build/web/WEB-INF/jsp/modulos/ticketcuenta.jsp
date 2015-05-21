<%--
    Document   : ticketcuenta
    Created on : 30/12/2010, 10:14:07 AM
    Author     : Oscar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" import="java.net.*,org.finance.bank.util.*,org.finance.bank.model.*,java.util.*, java.math.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8" />
        <link rel="shortcut icon" href="./images/favicon.ico" />
        <title>FinanceBank CAJA</title>
        <meta name="keywords" content="FinanceBank" />
        <meta name="description" content="Sistema financiero" />
        <link href="default.css" rel="stylesheet" type="text/css" />
        <link href='css/tabla.css' rel='stylesheet' type='text/css' />
        <style type="text/css">
            #inputsubmit1{
                display: none;
            }
            #main {
                float:left;
                margin-top:0;
                width:1200px;}
            #content {
                margin:0 auto;
                width:1200px;
            }
        </style>
        <style type="text/css" media="print">
            #logo{
                display: none;
            }
            #frmOperaciones{
                display: none;
            }
            #main{
                width:1200px;
            }
            #Submit{
                display: none;
            }
            #btnRegresar{
                display: none;
            }
            #footer{
                display: none;
            }
            #divOperacionf{
                height:auto;
            }
        </style>
    </head>
    <body>
        <%@include file="../common/header.jsp" %>
        <div id="content">            
            <div id="main">
                <div id="welcome" class="post">
                    <h2 class="title">ADMINISTRACION DE CAJA</h2>
                    <div class="story divOperacionf">
                        <%
                                    //String tipoOperacion = request.getParameter("tipoOperacion");
                                    String hql = "";
                                    //String tipo_rol = (String) session.getAttribute("USER_DESCRIPCION_ROLE");
                                    //String cod_caja = (String) codcaja;
                                    //if (tipoOperacion.equals("TODO") && tipo_rol.equals("ADMINISTRADOR")) {
                                    //hql = "from TOperacion op where (op.estado='ACTIVO' OR op.estado='EXTORNADO') AND op.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' order by op.fecha desc";
                                    //} else {
                                    hql = "from TOperacion op where (op.estado='ACTIVO' OR op.estado='EXTORNADO') AND op.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' AND op.TPersonaCaja.TCaja.codCaja='" + codcaja + "' order by op.fecha desc";
                                    //}
                                    String nombres = (String) session.getAttribute("USER_NAME");
                                    //String codCaja = (String) session.getAttribute("USER_CODCAJA");
                                    filial = (String) session.getAttribute("USER_FILIAL");
                                    filial = filial.replace("FILIAL", "OFICINA");
                                    DateFormat idfecha3 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                    out.println("<table>");
                                    out.println("<tr>");
                                    out.println("<td>" + filial + "</td>");
                                    out.println("<td>CAJA:" + codcaja + "</td>");
                                    out.println("</tr>");
                                    out.println("<tr>");
                                    out.println("<td>Fecha y Hora: </td>");
                                    out.println("<td>" + idfecha3.format(new Date()) + "</td>");
                                    out.println("</tr>");
                                    out.println("<tr>");
                                    out.println("<td>Nombre:</td>");
                                    out.println("<td>" + nombres + "</td>");
                                    out.println("</tr>");
                                    out.println("</table>");
                                    out.println("<table id='tablaOperacion' class='tabla' border='0'>");
                                    out.println("<tr>");
                                    out.println("<th style='display:none'>-.-</th>");
                                    out.println("<th>Num. Operaci&oacute;n</th>");
                                    out.println("<th>Fecha de Registro</th>");
                                    out.println("<th>Tipo Operaci&oacute;n</th>");
                                    out.println("<th>Monto</th>");
                                    out.println("<th>Moneda</th>");
                                    out.println("<th>Descripci&oacute;n</th>");
                                    out.println("<th>Estado</th>");
                                    out.println("<th>Usuario</th>");
                                    out.println("<th>Fecha Extorno</th>");
                                    out.println("<th>Admin Extorno</th>");
                                    out.println("<th style='display:none'>-..-</th>");
                                    out.println("</tr>");
                                    int i = 0;
                                    List lm = d.findAll("from TMoneda where estado='ACTIVO' order by codMoneda");
                                    Vector iniciados = new Vector();
                                    Vector recibidos = new Vector();
                                    Vector entregados = new Vector();
                                    //TDetalleCaja tdc;
                                    TLogFinance tdc;
                                    TRegistroOtros tro;
                                    TOperacion to;
                                    //TRegistroGiro trg;
                                    TTransferenciaCaja ttc;///Operar para cajas secundarias
                                    TRegistroCompraVenta trcv;
                                    TRegistroDepositoRetiro trdr;
                                    TRegistroPrestamo trp;
                                    TCobranza tc;
                                    Set data = new HashSet();
                                    TMoneda tm = null;
                                    List resultm = lm;
                                    Iterator itm = resultm.iterator();
                                    int tcpsizem = resultm.size();
                                    while (itm.hasNext()) {
                                        tm = (TMoneda) itm.next();
                                        data.add(tm);
                                        iniciados.add(new Double(0.0D));
                                        recibidos.add(new Double(0.0D));
                                        entregados.add(new Double(0.0D));
                                    }
                                    if (tm != null && tcpsizem > 0) {
                                        List result = d.findAll(hql);
                                        Iterator it = result.iterator();
                                        int tosize = result.size();
                                        String nombreO = "";
                                        if (tosize > 0) {
                                            while (it.hasNext()) {
                                                to = (TOperacion) it.next();
                                                nombreO = to.getTTipoOperacion().getNombre();
                                                //COMPRA DE M.E.
                                                if ("TIPC1".equals(to.getTTipoOperacion().getCodigoTipoOperacion()) && "ACTIVO".equals(to.getEstado())) {
                                                    Set xTRCV = to.getTRegistroCompraVentas();
                                                    if (xTRCV.size() >= 1) {
                                                        Iterator itTRC = xTRCV.iterator();
                                                        while (itTRC.hasNext()) {
                                                            trcv = (TRegistroCompraVenta) itTRC.next();
                                                            Vector interchanger = new Vector();
                                                            Vector interchanger2 = new Vector();
                                                            String moneda = to.getTMoneda().getCodMoneda();
                                                            BigDecimal mont = trcv.getMontoEntregado();
                                                            BigDecimal montR = trcv.getMontoRecibido();
                                                            if (trcv != null) {
                                                                Iterator iteraMoney1 = data.iterator();
                                                                Iterator iteraMont = recibidos.iterator();
                                                                while (iteraMoney1.hasNext() && iteraMont.hasNext()) {
                                                                    TMoneda mo = (TMoneda) iteraMoney1.next();
                                                                    Double montNow = (Double) iteraMont.next();
                                                                    if (moneda.equals(mo.getCodMoneda())) {
                                                                        montNow += montR.doubleValue();
                                                                        System.out.println("R1montNow = " + montNow + " " + to.getNumeroOperacion());
                                                                    }
                                                                    interchanger.add(montNow);
                                                                }
                                                                recibidos = interchanger;
                                                                Iterator iteraMoney2 = data.iterator();
                                                                Iterator iteraMontR = entregados.iterator();
                                                                TMoneda tMonedaP = (TMoneda) d.load(TMoneda.class, "PEN");
                                                                String moR = tMonedaP.getCodMoneda();
                                                                while (iteraMoney2.hasNext() && iteraMontR.hasNext()) {
                                                                    TMoneda mo = (TMoneda) iteraMoney2.next();
                                                                    Double montNow = (Double) iteraMontR.next();
                                                                    if (moR.equals(mo.getCodMoneda())) {
                                                                        montNow += mont.doubleValue();
                                                                        System.out.println("E1montNow = " + montNow + " " + to.getNumeroOperacion());
                                                                    }
                                                                    interchanger2.add(montNow);
                                                                }
                                                                entregados = interchanger2;
                                                            }
                                                        }
                                                    }
                                                }
                                                //VENTA DE M.E.
                                                if ("TIPC2".equals(to.getTTipoOperacion().getCodigoTipoOperacion()) && "ACTIVO".equals(to.getEstado())) {
                                                    Set xTRCV = to.getTRegistroCompraVentas();
                                                    if (xTRCV.size() >= 1) {
                                                        Iterator itTRC = xTRCV.iterator();
                                                        while (itTRC.hasNext()) {
                                                            trcv = (TRegistroCompraVenta) itTRC.next();
                                                            Vector interchanger = new Vector();
                                                            Vector interchanger2 = new Vector();
                                                            String moneda = to.getTMoneda().getCodMoneda();
                                                            BigDecimal mont = trcv.getMontoEntregado();
                                                            BigDecimal montR = trcv.getMontoRecibido();
                                                            if (trcv != null) {
                                                                Iterator iteraMoney1 = data.iterator();
                                                                Iterator iteraMont = entregados.iterator();
                                                                while (iteraMoney1.hasNext() && iteraMont.hasNext()) {
                                                                    TMoneda mo = (TMoneda) iteraMoney1.next();
                                                                    Double montNow = (Double) iteraMont.next();
                                                                    if (moneda.equals(mo.getCodMoneda())) {
                                                                        montNow += mont.doubleValue();
                                                                        System.out.println("E2montNow = " + montNow + " " + to.getNumeroOperacion());
                                                                    }
                                                                    interchanger.add(montNow);
                                                                }
                                                                entregados = interchanger;
                                                                Iterator iteraMoney2 = data.iterator();
                                                                Iterator iteraMontR = recibidos.iterator();
                                                                TMoneda tMonedaP = (TMoneda) d.load(TMoneda.class, "PEN");
                                                                String moR = tMonedaP.getCodMoneda();
                                                                while (iteraMoney2.hasNext() && iteraMontR.hasNext()) {
                                                                    TMoneda mo = (TMoneda) iteraMoney2.next();
                                                                    Double montNow = (Double) iteraMontR.next();
                                                                    if (moR.equals(mo.getCodMoneda())) {
                                                                        montNow += montR.doubleValue();
                                                                        System.out.println("R2montNow = " + montNow + " " + to.getNumeroOperacion());
                                                                    }
                                                                    interchanger2.add(montNow);
                                                                }
                                                                recibidos = interchanger2;
                                                            }
                                                        }
                                                    }
                                                }
                                                //DEPOSITOS
                                                if ("TIPC3".equals(to.getTTipoOperacion().getCodigoTipoOperacion()) && "ACTIVO".equals(to.getEstado())) {
                                                    Set xTRDR = to.getTRegistroDepositoRetiros();
                                                    if (xTRDR.size() >= 1) {
                                                        Iterator itTRD = xTRDR.iterator();
                                                        while (itTRD.hasNext()) {
                                                            trdr = (TRegistroDepositoRetiro) itTRD.next();
                                                            Vector interchanger = new Vector();
                                                            String moneda = to.getTMoneda().getCodMoneda();
                                                            BigDecimal mont = trdr.getImporte();
                                                            if (trdr != null) {
                                                                Iterator iteraMoney = data.iterator();
                                                                Iterator iteraMontR = recibidos.iterator();
                                                                while (iteraMoney.hasNext() && iteraMontR.hasNext()) {
                                                                    TMoneda mo = (TMoneda) iteraMoney.next();
                                                                    Double montNow = (Double) iteraMontR.next();
                                                                    if (moneda.equals(mo.getCodMoneda())) {
                                                                        montNow += mont.doubleValue();
                                                                        System.out.println("R3montNow = " + montNow + " " + to.getNumeroOperacion());
                                                                    }
                                                                    interchanger.add(montNow);
                                                                }
                                                                recibidos = interchanger;
                                                            }
                                                        }
                                                    }
                                                }
                                                //RETIROS
                                                if ("TIPC4".equals(to.getTTipoOperacion().getCodigoTipoOperacion()) && "ACTIVO".equals(to.getEstado())) {
                                                    Set xTRDR = to.getTRegistroDepositoRetiros();
                                                    if (xTRDR.size() >= 1) {
                                                        Iterator itTRR = xTRDR.iterator();
                                                        while (itTRR.hasNext()) {
                                                            trdr = (TRegistroDepositoRetiro) itTRR.next();
                                                            Vector interchanger = new Vector();
                                                            String moneda = to.getTMoneda().getCodMoneda();
                                                            BigDecimal mont = trdr.getImporte();
                                                            if (trdr != null) {
                                                                Iterator iteraMoney = data.iterator();
                                                                Iterator iteraMont = entregados.iterator();
                                                                while (iteraMoney.hasNext() && iteraMont.hasNext()) {
                                                                    TMoneda mo = (TMoneda) iteraMoney.next();
                                                                    Double montNow = (Double) iteraMont.next();
                                                                    if (moneda.equals(mo.getCodMoneda())) {
                                                                        montNow += mont.doubleValue();
                                                                        System.out.println("E4montNow = " + montNow + " " + to.getNumeroOperacion());
                                                                    }
                                                                    interchanger.add(montNow);
                                                                }
                                                                entregados = interchanger;
                                                            }
                                                        }
                                                    }
                                                }
                                                //GIROS
//                        if ("TIPC5".equals(to.getTTipoOperacion().getCodigoTipoOperacion()) && "ACTIVO".equals(to.getEstado())) {
//                            Set xTRG = to.getTRegistroGiros();
//                            if (xTRG.size() >= 1) {
//                                Iterator itTRG = xTRG.iterator();
//                                while (itTRG.hasNext()) {
//                                    trg = (TRegistroGiro) itTRG.next();
//                                    Vector interchanger = new Vector();
//                                    String moneda = to.getTMoneda().getCodMoneda();
//                                    BigDecimal mont = trg.getImporte();
//                                    if (trg != null) {
//                                        Iterator iteraMoney = data.iterator();
//                                        Iterator iteraMont = recibidos.iterator();
//                                        while (iteraMoney.hasNext() && iteraMont.hasNext()) {
//                                            TMoneda mo = (TMoneda) iteraMoney.next();
//                                            Double montNow = (Double) iteraMont.next();
//                                            if (moneda.equals(mo.getCodMoneda())) {
//                                                montNow += mont.doubleValue();
//                                                System.out.println("R5montNow = " + montNow + " " + to.getNumeroOperacion());
//                                            }
//                                            interchanger.add(montNow);
//                                        }
//                                        recibidos = interchanger;
//                                    }
//                                }
//                            }
//                        }
                                                //PRESTAMOS
                                                if ("TIPC6".equals(to.getTTipoOperacion().getCodigoTipoOperacion()) && "ACTIVO".equals(to.getEstado())) {
                                                    System.out.println("nombreO = " + to.getIdOperacion());
                                                    Set xTRP = to.getTRegistroPrestamos();
                                                    int s = xTRP.size();
                                                    System.out.println("s = " + s);
                                                    if (s >= 1) {
                                                        Iterator itTRP = xTRP.iterator();
                                                        while (itTRP.hasNext()) {
                                                            trp = (TRegistroPrestamo) itTRP.next();
                                                            Vector interchanger = new Vector();
                                                            String moneda = to.getTMoneda().getCodMoneda();
                                                            BigDecimal mont = trp.getMonto();
                                                            if (trp != null) {
                                                                Iterator iteraMoney = data.iterator();
                                                                Iterator iteraMont = entregados.iterator();
                                                                while (iteraMoney.hasNext() && iteraMont.hasNext()) {
                                                                    TMoneda mo = (TMoneda) iteraMoney.next();
                                                                    Double montNow = (Double) iteraMont.next();
                                                                    if (moneda.equals(mo.getCodMoneda())) {
                                                                        montNow += mont.doubleValue();
                                                                        System.out.println("E6montNow = " + montNow + " " + to.getNumeroOperacion());
                                                                    }
                                                                    interchanger.add(montNow);
                                                                }
                                                                entregados = interchanger;
                                                            }
                                                        }
                                                    }
                                                }
                                                //COBRANZAS
                                                if ("TIPC7".equals(to.getTTipoOperacion().getCodigoTipoOperacion()) && "ACTIVO".equals(to.getEstado())) {
                                                    Set xTC = to.getTCobranzas();
                                                    if (xTC.size() >= 1) {
                                                        Iterator itTC = xTC.iterator();
                                                        while (itTC.hasNext()) {
                                                            tc = (TCobranza) itTC.next();
                                                            Vector interchanger = new Vector();
                                                            String moneda = to.getTMoneda().getCodMoneda();
                                                            BigDecimal mont = tc.getTDetallePrestamo().getMontoTotal();
                                                            if (tc != null) {
                                                                Iterator iteraMoney = data.iterator();
                                                                Iterator iteraMont = recibidos.iterator();
                                                                while (iteraMoney.hasNext() && iteraMont.hasNext()) {
                                                                    TMoneda mo = (TMoneda) iteraMoney.next();
                                                                    Double montNow = (Double) iteraMont.next();
                                                                    if (moneda.equals(mo.getCodMoneda())) {
                                                                        montNow += mont.doubleValue();
                                                                        System.out.println("R7montNow = " + montNow + " " + to.getNumeroOperacion());
                                                                    }
                                                                    interchanger.add(montNow);
                                                                }
                                                                recibidos = interchanger;
                                                            }
                                                        }
                                                    }
                                                }
                                                //ENTREGA DE GIROS
//                        if ("TIPC8".equals(to.getTTipoOperacion().getCodigoTipoOperacion()) && "ACTIVO".equals(to.getEstado())) {
////                            tr = sess.beginTransaction();
//                            List l = dao.findAll("from TRegistroGiro rg where rg.idoperacioncobro='" + to.getNumeroOperacion() + "'");
//                            Iterator itCG = l.iterator();
//                            BigDecimal mont = BigDecimal.ZERO;
//                            trg = null;
//                            while (itCG.hasNext()) {
//                                trg = (TRegistroGiro) itCG.next();
//                                mont = mont.add(trg.getImporte());
//                            }
//                            Vector interchanger = new Vector();
//                            String moneda = to.getTMoneda().getCodMoneda();
//                            if (trg != null) {
//                                Iterator iteraMoney = data.iterator();
//                                Iterator iteraMont = entregados.iterator();
//                                while (iteraMoney.hasNext() && iteraMont.hasNext()) {
//                                    TMoneda mo = (TMoneda) iteraMoney.next();
//                                    Double montNow = (Double) iteraMont.next();
//                                    if (moneda.equals(mo.getCodMoneda())) {
//                                        montNow += mont.doubleValue();
//                                        System.out.println("E8montNow = " + montNow + " " + to.getNumeroOperacion());
//                                    }
//                                    interchanger.add(montNow);
//                                }
//                                entregados = interchanger;
//                            }
//                        }
                                                //TRANSFERENCIAS ENTRE CAJAS
                                                if ("TIPC9".equals(to.getTTipoOperacion().getCodigoTipoOperacion()) && "ACTIVO".equals(to.getEstado())) {
                                                    Set xTTC = to.getTTransferenciaCajas();
                                                    if (xTTC.size() >= 1) {
                                                        Iterator itTTC = xTTC.iterator();
                                                        while (itTTC.hasNext()) {
                                                            ttc = (TTransferenciaCaja) itTTC.next();
                                                            Vector interchanger = new Vector();
                                                            String moneda = to.getTMoneda().getCodMoneda();
                                                            BigDecimal mont = ttc.getMonto();
                                                            if (ttc != null) {
                                                                Iterator iteraMoney = data.iterator();
                                                                Iterator iteraMont = entregados.iterator();
                                                                while (iteraMoney.hasNext() && iteraMont.hasNext()) {
                                                                    TMoneda mo = (TMoneda) iteraMoney.next();
                                                                    Double montNow = (Double) iteraMont.next();
                                                                    if (moneda.equals(mo.getCodMoneda())) {
                                                                        montNow += mont.doubleValue();
                                                                        System.out.println("E9montNow = " + montNow + " " + to.getNumeroOperacion());
                                                                    }
                                                                    interchanger.add(montNow);
                                                                }
                                                                entregados = interchanger;
                                                            }
                                                        }
                                                    }
                                                }
                                                //RETIRO UTILIDAD
                                                if ("TIPC10".equals(to.getTTipoOperacion().getCodigoTipoOperacion()) && "ACTIVO".equals(to.getEstado())) {
                                                    Set xTRO = to.getTRegistroOtroses();
                                                    if (xTRO.size() >= 1) {
                                                        Iterator itTRO = xTRO.iterator();
                                                        while (itTRO.hasNext()) {
                                                            tro = (TRegistroOtros) itTRO.next();
                                                            Vector interchanger = new Vector();
                                                            String moneda = to.getTMoneda().getCodMoneda();
                                                            BigDecimal mont = tro.getMonto();
                                                            if (tro != null) {
                                                                Iterator iteraMoney = data.iterator();
                                                                Iterator iteraMont = entregados.iterator();
                                                                while (iteraMoney.hasNext() && iteraMont.hasNext()) {
                                                                    TMoneda mo = (TMoneda) iteraMoney.next();
                                                                    Double montNow = (Double) iteraMont.next();
                                                                    if (moneda.equals(mo.getCodMoneda())) {
                                                                        montNow += mont.doubleValue();
                                                                        System.out.println("E10montNow = " + montNow + " " + to.getNumeroOperacion());
                                                                    }
                                                                    interchanger.add(montNow);
                                                                }
                                                                entregados = interchanger;
                                                            }
                                                        }
                                                    }
                                                }
                                                String par = "";
                                                i++;
                                                if (i % 2 == 0) {
                                                    par = "modo2";
                                                } else {
                                                    par = "modo1";
                                                }
                                                BigDecimal Monto = BigDecimal.ZERO;
                                                if (nombreO.equals("COMPRA")) {
                                                    TRegistroCompraVenta ComVent = (TRegistroCompraVenta) d.findAll("from TRegistroCompraVenta where TOperacion='" + to.getIdOperacion() + "'").get(0);
                                                    Monto = ComVent.getMontoRecibido();
                                                } else if (nombreO.equals("VENTA")) {
                                                    TRegistroCompraVenta ComVent = (TRegistroCompraVenta) d.findAll("from TRegistroCompraVenta where TOperacion='" + to.getIdOperacion() + "'").get(0);
                                                    Monto = ComVent.getMontoEntregado();
                                                } else if (nombreO.equals("DEPOSITO") || nombreO.equals("RETIRO")) {
                                                    TRegistroDepositoRetiro DepRet = (TRegistroDepositoRetiro) d.findAll("from TRegistroDepositoRetiro where TOperacion='" + to.getIdOperacion() + "'").get(0);
                                                    Monto = DepRet.getImporte();
                                                    /*} else if (nombreO.equals("GIRO")) {
                                                    //System.out.println("giro = " + to.getNumeroOperacion());
                                                    TRegistroGiro RegGiro = (TRegistroGiro) dao.findAll("from TRegistroGiro where TOperacion='" + to.getIdOperacion() + "'").get(0);
                                                    Monto = RegGiro.getImporte();
                                                    } else if (nombreO.equals("COBROGIRO")) {
                                                    //System.out.println("giro = " + to.getNumeroOperacion());
                                                    TRegistroGiro RegGiro = (TRegistroGiro) dao.findAll("from TRegistroGiro where idoperacioncobro='" + to.getIdOperacion() + "'").get(0);
                                                    Monto = RegGiro.getImporte();*/
                                                } else if (nombreO.equals("PRESTAMO")) {
                                                    TRegistroPrestamo RegPres = (TRegistroPrestamo) d.findAll("from TRegistroPrestamo where TOperacion='" + to.getIdOperacion() + "'").get(0);
                                                    Monto = RegPres.getMonto();
                                                } else if (nombreO.equals("PAGO")) {
                                                    TCobranza Cobranza = (TCobranza) d.findAll("from TCobranza where TOperacion='" + to.getIdOperacion() + "'").get(0);
                                                    Monto = Cobranza.getTDetallePrestamo().getMontoTotal();
                                                } else if (nombreO.equals("RETIRO OTRO")) {
                                                    TRegistroOtros RegOtros = (TRegistroOtros) d.findAll("from TRegistroOtros where TOperacion='" + to.getIdOperacion() + "'").get(0);
                                                    Monto = RegOtros.getMonto();
                                                } else if (nombreO.equals("TRANSFERENCIA")) {
                                                    TTransferenciaCaja t = (TTransferenciaCaja) d.findAll("from TTransferenciaCaja where TOperacion='" + to.getIdOperacion() + "'").get(0);
                                                    Monto = t.getMonto();
                                                }
                                                out.println("<tr class=" + par + ">");
                                                out.println("<td id='r' style='display:none'>" + to.getIdOperacion() + "</td>");
                                                out.println("<td>" + to.getNumeroOperacion() + "</td>");
                                                out.println("<td>" + to.getFecha() + "</td>");
                                                out.println("<td>" + to.getTTipoOperacion().getNombre() + "</td>");
                                                out.println("<td>" + CurrencyConverter.formatToMoneyUS(Monto.doubleValue(), 2) + "</td>");
                                                out.println("<td>" + to.getTMoneda().getNombre() + "</td>");
                                                out.println("<td>" + to.getDescripcion() + "</td>");
                                                out.println("<td>" + to.getEstado() + "</td>");
                                                out.println("<td>" + to.getTPersonaCaja().getTPersona().getNombre() + "</td>");
                                                String fechaextor = "";
                                                if (to.getFechaExtornacion() != null) {
                                                    fechaextor = to.getFechaExtornacion().substring(0, 19);
                                                }
                                                out.println("<td>" + fechaextor + "</td>");
                                                String idAdminExorno = "";
                                                if (to.getIdAdminExtorno() != null) {
                                                    TPersona xA = (TPersona) d.load(TPersona.class, to.getIdAdminExtorno());
                                                    if (xA == null) {
                                                        idAdminExorno = to.getIdAdminExtorno();
                                                    } else {
                                                        idAdminExorno = xA.getNombre() + "<br>" + xA.getApellidos();
                                                    }
                                                }
                                                out.println("<td>" + idAdminExorno + "</td>");
                                                out.println("</tr>");
                                            }
                                        }
                                        hql = "from TTransferenciaCaja o where (o.TOperacion.estado='ACTIVO' OR o.TOperacion.estado='EXTORNADO') AND o.TOperacion.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' AND o.codCajaDestino='" + codcaja + "' order by o.TOperacion.fecha desc";
                                        List resultX = d.findAll(hql);
                                        int tosizeX = resultX.size();
                                        //String nombreO = "";
                                        if (tosizeX > 0) {
                                            Iterator itX = resultX.iterator();
                                            while (itX.hasNext()) {
                                                TTransferenciaCaja opX = (TTransferenciaCaja) itX.next();
                                                BigDecimal Monto = opX.getMonto();
                                                i++;
                                                out.println("<tr class=" + (i % 2 == 0 ? "modo2" : "modo1") + ">");
                                                out.println("<td id='r' style='display:none'>" + opX.getTOperacion().getIdOperacion() + "</td>");
                                                out.println("<td>" + opX.getTOperacion().getNumeroOperacion() + "</td>");
                                                out.println("<td>" + opX.getTOperacion().getFecha() + "</td>");
                                                out.println("<td>RECEP. DE TRANSF.</td>");
                                                out.println("<td style='font-weight: bold'>" + CurrencyConverter.formatToMoneyUS(Monto.doubleValue(), 2) + "</td>");
                                                out.println("<td>" + opX.getTOperacion().getTMoneda().getNombre() + "</td>");
                                                out.println("<td>" + opX.getTOperacion().getDescripcion() + "</td>");
                                                out.println("<td>" + opX.getTOperacion().getEstado() + "</td>");
                                                out.println("<td>" + opX.getTOperacion().getTPersonaCaja().getTPersona().getNombre() + "</td>");
                                                String fechaextor = "";
                                                if (opX.getTOperacion().getFechaExtornacion() != null) {
                                                    fechaextor = opX.getTOperacion().getFechaExtornacion().substring(0, 19);
                                                }
                                                out.println("<td>" + fechaextor + "</td>");
                                                String idAdminExorno = "";
                                                if (opX.getTOperacion().getIdAdminExtorno() != null) {
                                                    TPersona xA = (TPersona) d.load(TPersona.class, opX.getTOperacion().getIdAdminExtorno());
                                                    if (xA == null) {
                                                        idAdminExorno = opX.getTOperacion().getIdAdminExtorno();
                                                    } else {
                                                        idAdminExorno = xA.getNombre() + "<br>" + xA.getApellidos();
                                                    }
                                                }
                                                out.println("<td>" + idAdminExorno + "</td>");
                                                out.println("</tr>");
                                            }
                                        }
//                List liIx = dao.findAll("from TTransferenciaCaja tc where tc.tipo='DEVENGADO' and tc.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' and tc.codCajaDestino='" + cod_caja + "'");
//                int ssIx = liIx.size();
//                if (ssIx > 0) {
//                    Iterator itI = liIx.iterator();
//                    while (itI.hasNext()) {
//                        ttc = (TTransferenciaCaja) itI.next();
//                        Vector interchanger = new Vector();
//                        String moneda = ttc.getTOperacion().getTMoneda().getCodMoneda();
//                        if (ttc != null) {
//                            BigDecimal mont = ttc.getMonto();
//                            Iterator iteraMoney = data.iterator();
//                            Iterator iteraMont = iniciados.iterator();
//                            while (iteraMoney.hasNext() && iteraMont.hasNext()) {
//                                TMoneda mo = (TMoneda) iteraMoney.next();
//                                Double montNow = (Double) iteraMont.next();
//                                if (moneda.equals(mo.getCodMoneda())) {
//                                    montNow += mont.doubleValue();
//                                    System.out.println("IttmontNow = " + montNow +" "+ttc.getTOperacion().getNumeroOperacion());
//                                }
//                                interchanger.add(montNow);
//                            }
//                            iniciados = interchanger;
//                        }
//                    }
//                }
                                        /*
                                        from TTransferenciaCaja where fecha like '2011/08/13%' and TCaja.codCaja='0501CA001' and codCajaDestino = '0501CA004' order by fecha
                                        from TTransferenciaCaja where fecha like '2011/08/13%' and TCaja.codCaja='0501CA001' and codCajaDestino = '0501CA004' and TOperacion.TMoneda.codMoneda='PEN' order by fecha
                                         *                                         */
                                        //List liI = d.findAll("from TDetalleCaja tc where TCaja.codCaja='" + cod_caja + "' and TMoneda.estado='ACTIVO' and estado='ACTIVO' and fechaTransaccion like '" + DateUtil.getDate(new Date()) + "%'");
                                        List liI = d.findAll("from TLogFinance where idLogFinance like 'LOG" + codcaja + "___'");
                                        int ssI = liI.size();
                                        if (ssI > 0) {
                                            Iterator itI = liI.iterator();
                                            while (itI.hasNext()) {
                                                //tdc = (TDetalleCaja) itI.next();
                                                tdc = (TLogFinance) itI.next();
                                                Vector interchanger = new Vector();
                                                String moneda = tdc.getIdlogfinance().substring(12);
                                                if (tdc != null) {
                                                    BigDecimal mont = tdc.getMontoFinal();
                                                    Iterator iteraMoney = data.iterator();
                                                    Iterator iteraMont = iniciados.iterator();
                                                    while (iteraMoney.hasNext() && iteraMont.hasNext()) {
                                                        TMoneda mo = (TMoneda) iteraMoney.next();
                                                        Double montNow = (Double) iteraMont.next();
                                                        if (moneda.equals(mo.getCodMoneda())) {
                                                            montNow += mont.doubleValue();
                                                            //System.out.println("IttmontNow = " + montNow + " " + tdc.getMontoInicial());
                                                        }
                                                        interchanger.add(montNow);
                                                    }
                                                    iniciados = interchanger;
                                                }
                                            }
                                        }
                                        out.println("<tr style='display:none'>");
                                        out.println("<td id='tdIdAdmin'>" + session.getAttribute("USER_ID") + "</td>");
                                        out.println("</tr>");
                                        out.println("</table>");
                                        out.println("<table>");
                                        out.println("<tr>");
                                        out.println("<td>RESUMEN:</td>");
                                        Iterator itM = data.iterator();
                                        /*Iterator itII = iniciados.iterator();
                                        Iterator itE = entregados.iterator();
                                        Iterator itR = recibidos.iterator();*/
                                        String mm = "";
                                        String ii = "";
                                        String e = "";
                                        String r = "";
                                        String f = "";
                                        while (itM.hasNext()) {
                                            TMoneda xmm = (TMoneda) itM.next();
                                            //TDetalleCaja detA = iniDetalleCaja.detalleActivaCaja(codCaja, xmm.getCodMoneda(), DateUtil.getDate(new Date()));
                                            TLogFinance detA = (TLogFinance) d.load(TLogFinance.class, "LOG" + codcaja + xmm.getCodMoneda());
                                            if (detA == null) {
                                                mm += "<td>NULO</td>";
                                                ii += "<td>NULO</td>";
                                                r += "<td>NULO</td>";
                                                e += "<td>NULO</td>";
                                                f += "<td>NULO</td>";
                                            } else {
                                                mm += "<td  width='180px'>" + xmm.getNombre() + " &nbsp;</td>";
                                                ii += "<td style='font-weight: bold'>" + DateUtil.eedatefmt(DateUtil.fmtdate17, detA.getMontoFinal().doubleValue()) + "</td>";
                                                r += "<td style='font-weight: bold'>" + DateUtil.eedatefmt(DateUtil.fmtdate17, detA.getMontoRecibido().doubleValue()) + "</td>";
                                                e += "<td style='font-weight: bold'>" + DateUtil.eedatefmt(DateUtil.fmtdate17, detA.getMontoEntregado().doubleValue()) + "</td>";
                                                f += "<td style='font-weight: bold'>" + DateUtil.eedatefmt(DateUtil.fmtdate17, detA.getMontoFinal().doubleValue()) + "</td>";
                                            }
                                            /*Double xii = (Double) itII.next();
                                            Double ee = (Double) itE.next();
                                            Double rr = (Double) itR.next();
                                            BigDecimal mxi = BigDecimal.ZERO;
                                            if (!codCaja.endsWith("001")) {
                                            List l = d.findAll("from TTransferenciaCaja where fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%'"
                                            + " and TCaja.codCaja='" + filial + "CA001' and codCajaDestino = '" + codCaja + "'"
                                            + " and tipo='PAGADO'"
                                            + " and TOperacion.TMoneda.codMoneda='" + xmm.getCodMoneda() + "' order by fecha");
                                            int h = l.size();
                                            int first = 0;
                                            if (h > 0) {
                                            while (!(mxi.doubleValue() > 0)) {
                                            if (!(first > h)) {
                                            first++;
                                            TTransferenciaCaja txi = (TTransferenciaCaja) l.iterator().next();
                                            mxi = txi.getMonto();
                                            }
                                            }
                                            }
                                            mm += "<td  width='180px'>" + xmm.getNombre() + " &nbsp;</td>";
                                            ii += "<td>" + DateUtil.eedatefmt(DateUtil.fmtdate17, mxi.doubleValue()) + "</td>";
                                            r += "<td>" + DateUtil.eedatefmt(DateUtil.fmtdate17, xii + ee - mxi.doubleValue()) + "</td>";
                                            e += "<td>" + DateUtil.eedatefmt(DateUtil.fmtdate17, ee) + "</td>";
                                            f += "<td>" + DateUtil.eedatefmt(DateUtil.fmtdate17, xii) + "</td>";
                                            } else {
                                            mm += "<td  width='180px'>" + xmm.getNombre() + " &nbsp;</td>";
                                            ii += "<td>" + DateUtil.eedatefmt(DateUtil.fmtdate17, xii + ee - rr) + "</td>";
                                            r += "<td>" + DateUtil.eedatefmt(DateUtil.fmtdate17, rr) + "</td>";
                                            e += "<td>" + DateUtil.eedatefmt(DateUtil.fmtdate17, ee) + "</td>";
                                            f += "<td>" + DateUtil.eedatefmt(DateUtil.fmtdate17, xii) + "</td>";
                                            }*/
                                        }
                                        out.println("" + mm);
                                        out.println("</tr>");
                                        out.println("<tr>");
                                        out.println("<td>MONTO INICIAL:</td>");
                                        out.println("" + ii);
                                        out.println("</tr>");
                                        out.println("<tr>");
                                        out.println("<td>MONTO RECIBIDO:</td>");
                                        out.println("" + r);
                                        out.println("</tr>");
                                        out.println("<tr>");
                                        out.println("<td>MONTO TRANSFERIDO:</td>");
                                        out.println("" + e);
                                        out.println("</tr>");
                                        out.println("<tr>");
                                        out.println("<td>MONTO FINAL:</td>");
                                        out.println("" + f);
                                        out.println("</tr>");
                                        out.println("</table>");
                                        out.println("<div id='ctrl'>");
                                        out.println("<center>");
                                        out.println("<br>");
                                        out.println("<input name='Submit' id='Submit' type='button' onClick=\"document.title=''; if (window.print)window.print();else alert('Su Navegador no dispone de esta opcion, Actualicelo con una version mas reciente');\" value='Imprimir'>");
                                        out.println("<input type='button' value='Cerrar Ventana' onclick='window.close();'");
                                        out.println("</center>");
                                        //out.println("<form action='managercaja.htm' method='post' id='frmregresar' name='frmregresar'>");
                                        out.println("</div>");
                                    }
                        %>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="../common/footer.jsp" %>
    </body>
</html>