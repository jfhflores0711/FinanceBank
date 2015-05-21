<%-- 
    Document   : newjsp
    Created on : 11/02/2010, 04:41:24 PM
    Author     : Oscar
--%>
<%@page session="true" import="org.finance.bank.util.*,org.finance.bank.model.*,java.util.*, java.math.*, org.finance.bank.model.dao.DAOGeneral"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="shortcut icon" href="./images/favicon.ico" />
        <title><%=(DateUtil.getDateTime("yyyymmddhhmmssS", new Date()))%>|financeBank! </title>
        <meta name="keywords" content="FinanceBank" />
        <meta name="description" content="Sistema financiero" />
        <link href="default.css" rel="stylesheet" type="text/css" />
        <link href="css/francesGral.css" rel="stylesheet" type="text/css" media="All">
        <link href="css/francesScreen.css" rel="stylesheet" type="text/css" media="screen">
        <link href="css/francesPrint.css" rel="stylesheet" type="text/css" media="print">
        <script type="text/javascript">
            if (history.forward(1)){location.replace(history.forward(1))}
        </script>
        <script type="text/javascript" src="js/scriptConvertTest.js"></script>
        <script type="text/javascript" src="js/scriptshow/scriptFCobranzas.js"></script>
        <style type="text/css">
            #content{ width:1100px; }
            #sidebar{ width:200px; }
            #main{ width:900px; }
        </style>
    </head>
    <body>
        <%@include file="../common/header.jsp" %>
        <%
                    request.setAttribute("idmenu", "8");
        %>        
        <div id="content">
            <div id="sidebar">
                <div id="menu">
                    <%@include file="../common/menu.jsp" %>
                </div>
                <div id="login" class="boxed">
                    <h2 class="title">Client Account</h2>
                    <div class="content">
                        <form id="form1" method="post" action="principal.htm">
                            <fieldset><legend>&nbsp;</legend>
                                <input id="inputsubmit1" type="submit" name="inputsubmit1" value="Salir" />
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
            <div id="main">
                <div id="welcome" class="post">
                    <h2 class="title">Cuadro de Amortizacion financeBank</h2>
                    <h3 class="date"><span class="month"><script type="text/javascript">
                        var fecha=new Date();
                        var fmtmonthnamesshort=new Array("Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre");
                        document.write(fmtmonthnamesshort[fecha.getMonth()]);
                            </script></span> <span class="day"><script type="text/javascript">
                                document.write(fecha.getDate());
                            </script></span><span class="year">, <script type="text/javascript">document.write(fecha.getFullYear());</script></span></h3>
                    <div class="meta">
                        <p>Casa de cambios Ventura / INVERSIONES VENTURA</p>
                    </div>        <%
                                TContrato tcontrato = null;
                                /**Para responder reenvíos desde un refresh anterior*/
                                TMoneda moneda = null; /*Moneda, sólo con fines de validacion, no se necesita, ya que se carga a traves de la cuenta persona*/
                                String a = (String) request.getParameter("ax");
                                String myPC = session.getAttribute("USER_ID_PERSONA_CAJA").toString();
                                String myId = session.getAttribute("USER_ID").toString();
                                String myIp = session.getAttribute("USER_IP").toString();
                                String fpp = "";
                                String fup = "";
                                String idForInitNewestTable = DateUtil.convertDateId(myPC, "fechacobranzas");
                                TRegistroPrestamo b = (TRegistroPrestamo) d.load(TRegistroPrestamo.class, a);
                                if (b != null) {
                                    String s;
                                    double cxu = 0D;
                                    moneda = b.getTCuentaPersona().getTMoneda();
                                    if (b.getEstadoSolicitud() == 4) {
                                        s = "<h2>El Desembolso ya se había realizado<h2>";
                                        tcontrato = (TContrato) b.getTContratos().iterator().next();
                                    } else {
                                        b.setEstadoSolicitud(4);
                                        b.setFechaDembolsoAutorizacion(DateUtil.getNOW_S());
                                        b.setFechaDesembolso(DateUtil.getDate(new Date()));
                                        d.update();
                                        if (!b.getTDetallePrestamos().isEmpty()) {
                                            for (Iterator it = b.getTDetallePrestamos().iterator(); it.hasNext();) {
                                                TDetallePrestamo object = (TDetallePrestamo) it.next();
                                                d.delete(object);
                                            }
                                        }
                                        int c = b.getNumeroCuotas();
                                        double sa = 0D;
                                        double sad = 0D;
                                        double ia = 0D;
                                        double ca = 0D;
                                        double it = 0D;
                                        double ct = 0D;
                                        double cat = 0D;
                                        s = "<TABLE FRAME=VOID CELLSPACING=0 COLS=7 RULES=NONE BORDER=0>"
                                                //+ "<COLGROUP><COL WIDTH=86><COL WIDTH=86><COL WIDTH=86><COL WIDTH=86><COL WIDTH=86><COL WIDTH=86><COL WIDTH=86></COLGROUP>"
                                                + "<TBODY>"
                                                + "<TR style='height:14pt'>"
                                                + "<TD class='ee115'><B><FONT SIZE=1>NRO. CUOTA</FONT></B></TD>"
                                                + "<TD class='ee118'><B><FONT SIZE=1>TOTAL CUOTA</FONT></B></TD>"
                                                + "<TD class='ee115'><B><FONT SIZE=1>INTERESES</FONT></B></TD>"
                                                + "<TD class='ee115'><B><FONT SIZE=1>AMORTIZACIÓN</FONT></B></TD>"
                                                + "<TD class='ee115'><B><FONT SIZE=1>CAPITAL PENDIENTE</FONT></B></TD>"
                                                + "<TD class='ee115'><B><FONT SIZE=1>VENCIMIENTO</FONT></B></TD>"
                                                + "<TD class='ee100'><B><FONT SIZE=1>OTROS CARGOS</FONT></B></TD>"
                                                + "</TR>"
                                                + "<TR style='height:13pt'>"
                                                + "<TD class='ee131'>0</TD>"
                                                + "<TD class='ee120' colspan='3'></TD>"
                                                + "<TD class='ee126'>" + b.getMonto().negate() + "</TD>"
                                                + "<TD class='ee126'></TD>"
                                                + "<TD class='ee100'></TD>"
                                                + "</TR>";
                                        if (b.getModoCalculos() == 1) {
                                            String fa = b.getFechaDesembolso().substring(8, 10) + "/" + b.getFechaDesembolso().substring(5, 7) + "/" + b.getFechaDesembolso().substring(0, 4);
                                            //System.out.println("tasa = " + (Math.pow(1D + b.getTasaInteresPrestamo().doubleValue() / 100D, 1D / 360D) - 1D));
                                            Double cu = DateUtil.round(PrestamoUtil.pmt2(Math.pow(1D + b.getTasaInteresPrestamo().doubleValue() / 100D, 1D / 360D) - 1D, c, -b.getMonto().doubleValue(), b.getDiasEntreCuotas()), 2);
                                            //System.out.println("cu = " + cu);
                                            cxu = cu;
                                            sa = b.getMonto().doubleValue();
                                            for (int q = 1; q <= c; q++) {
                                                idForInitNewestTable = DateUtil.convertDateId(myPC, "fechacobranzas");
                                                ia = DateUtil.round(sa * (Math.pow(1D + b.getTasaInteresPrestamo().doubleValue() / 100D, b.getDiasEntreCuotas() / 360D) - 1D), 2);
                                                //System.out.println("ia = " + ia);
                                                it += ia;
                                                ca = DateUtil.round(cu - ia, 2);
                                                cat += ca;
                                                sad = sa;
                                                sa = DateUtil.round(sa - ca, 2);
                                                fa = DateUtil.addToDateFull(fa, b.getDiasEntreCuotas());
                                                //public TDetallePrestamo(String iddetalleprestamo, TRegistroPrestamo TRegistroPrestamo,
                                                //BigDecimal numeroCuota, BigDecimal montoCapital, BigDecimal montoTotal, String fechaPago,
                                                //BigDecimal interesPrestamo, BigDecimal interesMoratorio, String idUser, String ipUser,
                                                //String dateUser, BigDecimal capitalPendiente)
                                                TDetallePrestamo f = new TDetallePrestamo(idForInitNewestTable, b, new BigDecimal(q), new BigDecimal(ca), new BigDecimal(sa), "",
                                                        b.getTasaInteresPrestamo(), b.getTasaInteresMoratorio(), myId, myIp, DateUtil.getNOW_S(), new BigDecimal(sad));
                                                f.setCapitalPagado(BigDecimal.ZERO);
                                                f.setCargos(BigDecimal.ZERO);
                                                f.setCargosPagado(BigDecimal.ZERO);
                                                f.setComision(BigDecimal.ZERO);
                                                f.setCuota(new BigDecimal(cu));
                                                f.setDescuento(BigDecimal.ZERO);
                                                f.setEstado("ACTIVO");
                                                f.setEstadoPago(0);
                                                f.setFechaPagoOp(fa);
                                                f.setIntSuspendidoItf(BigDecimal.ZERO);
                                                f.setInteresComp(BigDecimal.ZERO);
                                                f.setInteresEfectivo(new BigDecimal(ia));
                                                f.setInteresPagado(BigDecimal.ZERO);
                                                f.setMora(BigDecimal.ZERO);
                                                f.setMoraPagada(BigDecimal.ZERO);
                                                f.setSeguro(BigDecimal.ZERO);
                                                f.setSeguroPagado(BigDecimal.ZERO);
                                                f.setTotalPago(BigDecimal.ZERO);
                                                fup = fa;
                                                s += "<TR style='height:13pt'>";
                                                if (q == 1) {
                                                    ct += cu;
                                                    fpp = fa;
                                                    s += "<TD class='ee131'>";
                                                    s += q + "";
                                                    s += "</TD>";
                                                    s += "<TD class='ee131'>" + DateUtil.padNmbOnlyDecFull(cu, 2, "0") + "</TD>"
                                                            + "<TD class='ee119'>" + DateUtil.padNmbOnlyDecFull(ia, 2, "0") + "</TD>"
                                                            + "<TD class='ee119'>" + DateUtil.padNmbOnlyDecFull(ca, 2, "0") + "</TD>"
                                                            + "<TD class='ee119'>" + DateUtil.padNmbOnlyDecFull(sad, 2, "0") + "</TD>"
                                                            + "<TD class='ee130'>" + fa + "</TD>"
                                                            + "<TD class='ee100'>0.00</TD>";
                                                } else if (q == c) {
                                                    if (sa != 0.0) {
                                                        ca = DateUtil.round(ca + sa, 2);
                                                        cat += DateUtil.round(sa, 2);
                                                        sa = 0;
                                                    }
                                                    out.print(q);
                                                    ct += ca + ia;
                                                    f.setMontoCapital(new BigDecimal(ca));
                                                    f.setCapitalPendiente(new BigDecimal(sa));
                                                    s += "<TD class='ee131'>";
                                                    s += q + "";
                                                    s += "</TD>";
                                                    s += "<TD class='ee131'>" + DateUtil.padNmbOnlyDecFull(DateUtil.round(ca + ia, 2), 2, "0") + "</TD>"
                                                            + "<TD class='ee119'>" + DateUtil.padNmbOnlyDecFull(ia, 2, "0") + "</TD>"
                                                            + "<TD class='ee119'>" + DateUtil.padNmbOnlyDecFull(ca, 2, "0") + "</TD>"
                                                            + "<TD class='ee119'>" + DateUtil.padNmbOnlyDecFull(sad, 2, "0") + "</TD>"
                                                            + "<TD class='ee130'>" + fa + "</TD>"
                                                            + "<TD class='ee100'>0.00</TD>";
                                                } else {
                                                    ct += cu;
                                                    s += "<TD class='ee131'>";
                                                    s += q + "";
                                                    s += "</TD>";
                                                    s += "<TD class='ee131'>" + DateUtil.padNmbOnlyDecFull(cu, 2, "0") + "</TD>"
                                                            + "<TD class='ee119'>" + DateUtil.padNmbOnlyDecFull(ia, 2, "0") + "</TD>"
                                                            + "<TD class='ee119'>" + DateUtil.padNmbOnlyDecFull(ca, 2, "0") + "</TD>"
                                                            + "<TD class='ee119'>" + DateUtil.padNmbOnlyDecFull(sad, 2, "0") + "</TD>"
                                                            + "<TD class='ee130'>" + fa + "</TD>"
                                                            + "<TD class='ee100'>0.00</TD>";
                                                }
                                                f.setMontoTotal(new BigDecimal(cu));
                                                d.persist(f);
                                                s += "</TR>";
                                            }
                                        } else {
                                            String ff = b.getFechaDesembolso().substring(8, 10) + "/" + b.getFechaDesembolso().substring(5, 7) + "/" + b.getFechaDesembolso().substring(0, 4);
                                            double de = 0.0D;
                                            String ff1 = ff;
                                            for (int sal = 1; sal <= c; sal++) {
                                                int dc = 0;
                                                ff = ff1;
                                                ff1 = DateUtil.sumaMesFull(ff, b.getDiasEntreCuotas() / 30);
                                                dc = DateUtil.difDiasFull(b.getFechaDesembolso().substring(8, 10) + "/" + b.getFechaDesembolso().substring(5, 7) + "/" + b.getFechaDesembolso().substring(0, 4), ff1);
                                                de = de + (Math.pow(1D / Math.pow(1D + b.getTasaInteresPrestamo().doubleValue() / 100D, 1D / 360D), dc));
                                            }
                                            ff1 = b.getFechaDesembolso().substring(8, 10) + "/" + b.getFechaDesembolso().substring(5, 7) + "/" + b.getFechaDesembolso().substring(0, 4);
                                            int dp = 0;
                                            if (de == 0.0D) {
                                                de = 1D;
                                            }
                                            double ce = DateUtil.round(b.getMonto().doubleValue() / de, 2);
                                            cxu = ce;
                                            sa = b.getMonto().doubleValue();
                                            for (int r = 1; r <= c; r++) {
                                                ff = ff1;
                                                ff1 = DateUtil.sumaMesFull(ff, b.getDiasEntreCuotas() / 30);
                                                dp = DateUtil.difDiasFull(ff, ff1);
                                                ia = DateUtil.round(sa * (Math.pow(1D + b.getTasaInteresPrestamo().doubleValue() / 100D, dp / 360D) - 1D), 2);
                                                it += ia;
                                                ca = DateUtil.round(ce - ia, 2);
                                                cat += ca;
                                                sad = sa;
                                                sa = DateUtil.round(sa - ca, 2);
                                                //public TDetallePrestamo(String iddetalleprestamo, TRegistroPrestamo TRegistroPrestamo,
                                                //BigDecimal numeroCuota, BigDecimal montoCapital, BigDecimal montoTotal, String fechaPago,
                                                //BigDecimal interesPrestamo, BigDecimal interesMoratorio, String idUser, String ipUser,
                                                //String dateUser, BigDecimal capitalPendiente)
                                                TDetallePrestamo f = new TDetallePrestamo(idForInitNewestTable, b, new BigDecimal(r), new BigDecimal(ca), new BigDecimal(sa), "",
                                                        new BigDecimal(ia), b.getTasaInteresMoratorio(), myId, myIp, DateUtil.getNOW_S(), new BigDecimal(sad));
                                                f.setCapitalPagado(BigDecimal.ZERO);
                                                f.setCargos(BigDecimal.ZERO);
                                                f.setCargosPagado(BigDecimal.ZERO);
                                                f.setComision(BigDecimal.ZERO);
                                                f.setCuota(new BigDecimal(ce));
                                                f.setDescuento(BigDecimal.ZERO);
                                                f.setEstado("ACTIVO");
                                                f.setEstadoPago(0);
                                                f.setFechaPagoOp(ff1);
                                                f.setIntSuspendidoItf(BigDecimal.ZERO);
                                                f.setInteresComp(BigDecimal.ZERO);
                                                f.setInteresEfectivo(BigDecimal.ZERO);
                                                f.setInteresPagado(BigDecimal.ZERO);
                                                f.setMora(BigDecimal.ZERO);
                                                f.setMoraPagada(BigDecimal.ZERO);
                                                f.setSeguro(BigDecimal.ZERO);
                                                f.setSeguroPagado(BigDecimal.ZERO);
                                                f.setTotalPago(BigDecimal.ZERO);
                                                fup = ff1;
                                                s += "<TR style='height:13pt'>";
                                                if (r == 1) {
                                                    ct += ce;
                                                    fpp = ff1;
                                                    s += "<TD class='ee131'>";
                                                    s += r + "";
                                                    s += "</TD>";
                                                    s += "<TD class='ee131'>" + DateUtil.padNmbOnlyDecFull(ce, 2, "0") + "</TD>"
                                                            + "<TD class='ee119'>" + DateUtil.padNmbOnlyDecFull(ia, 2, "0") + "</TD>"
                                                            + "<TD class='ee119'>" + DateUtil.padNmbOnlyDecFull(ca, 2, "0") + "</TD>"
                                                            + "<TD class='ee119'>" + DateUtil.padNmbOnlyDecFull(sad, 2, "0") + "</TD>"
                                                            + "<TD class='ee130'>" + ff1 + "</TD>"
                                                            + "<TD class='ee100'>0.00</TD>";
                                                } else if (r == c) {
                                                    if (sa != 0.0) {
                                                        ca = DateUtil.round(ca + sa, 2);
                                                        cat += DateUtil.round(sa, 2);
                                                        sa = 0;
                                                    }

                                                    ct += ca + ia;
                                                    f.setMontoCapital(new BigDecimal(ca));
                                                    f.setCapitalPendiente(new BigDecimal(sa));
                                                    s += "<TD class='ee131'>";
                                                    s += r + "";
                                                    s += "</TD>";
                                                    s += "<TD class='ee131'>" + DateUtil.padNmbOnlyDecFull(DateUtil.round(ca + ia, 2), 2, "0") + "</TD>"
                                                            + "<TD class='ee119'>" + DateUtil.padNmbOnlyDecFull(ia, 2, "0") + "</TD>"
                                                            + "<TD class='ee119'>" + DateUtil.padNmbOnlyDecFull(ca, 2, "0") + "</TD>"
                                                            + "<TD class='ee119'>" + DateUtil.padNmbOnlyDecFull(sad, 2, "0") + "</TD>"
                                                            + "<TD class='ee130'>" + ff1 + "</TD>"
                                                            + "<TD class='ee100'>0.00</TD>";
                                                } else {
                                                    ct += ce;
                                                    s += "<TD class='ee131'>";
                                                    s += r + "";
                                                    s += "</TD>";
                                                    s += "<TD class='ee131'>" + DateUtil.padNmbOnlyDecFull(ce, 2, "0") + "</TD>"
                                                            + "<TD class='ee119'>" + DateUtil.padNmbOnlyDecFull(ia, 2, "0") + "</TD>"
                                                            + "<TD class='ee119'>" + DateUtil.padNmbOnlyDecFull(ca, 2, "0") + "</TD>"
                                                            + "<TD class='ee119'>" + DateUtil.padNmbOnlyDecFull(sad, 2, "0") + "</TD>"
                                                            + "<TD class='ee130'>" + ff1 + "</TD>"
                                                            + "<TD class='ee100'>0.00</TD>";
                                                }
                                                f.setMontoTotal(new BigDecimal(ce));
                                                d.persist(f);
                                                s += "</TR>";
                                            }
                                        }
                                        s +=
                                                "<TR style='height:13pt'>"
                                                + "<TD class='ee103'><B><FONT SIZE=1>TOTAL:</FONT></B></TD>"
                                                + "<TD class='ee103'><B><FONT SIZE=3>" + DateUtil.round(ct, 2) + "</FONT></TD>"
                                                + "<TD class='ee103'><B><FONT SIZE=3>" + DateUtil.round(it, 2) + "</FONT></TD>"
                                                + "<TD class='ee103'><B><FONT SIZE=3>" + DateUtil.round(cat, 2) + "</FONT></TD>"
                                                + "<TD class='ee103'><B><FONT SIZE=3>------</FONT></B></TD>"
                                                + "<TD class='ee103'><B><FONT SIZE=3>------</FONT></TD>"
                                                + "<TD class='ee103'><B><FONT SIZE=1>&nbsp;</FONT></B></TD>"
                                                + "</TR>"
                                                + "</TBODY>"
                                                + "</TABLE>";

                                        tcontrato = new TContrato();
                                        tcontrato.setIdcontrato(idForInitNewestTable);
                                        tcontrato.setFecha(DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", new Date()));
                                        tcontrato.setNumContrato(PrestamoUtil.nextNumberContrato("0501"));
                                        tcontrato.setDniRuc(b.getTCuentaPersona().getTPersona().getDocIdentidad());
                                        tcontrato.setNombre(b.getTCuentaPersona().getTPersona().getNombre());
                                        tcontrato.setNombreRep(b.getTCuentaPersona().getTPersona().getApellidos());
                                        tcontrato.setDescripcion(new String("nonurl"));
                                        tcontrato.setEstado("ACTIVO");
                                        tcontrato.setTRegistroPrestamo(b);
                                        d.persist(tcontrato);
                                        try {
                                            //TDetalleCaja detaCaja = iniDetalleCaja.detalleActivaCaja((String) session.getAttribute("USER_CODCAJA"), mo.getCodMoneda(), DateUtil.getDate(new Date()));
                                            //TDetalleCaja detaCaja =(TDetalleCaja)d.load(TDetalleCaja.class, DateUtil.getDate(new Date()).replaceAll("/", "") + codcaja + mo.getCodParaNumCuenta() + "00");
                                            TLogFinance detaCaja = (TLogFinance) d.load(TLogFinance.class, "LOG" + codcaja + moneda.getCodMoneda());
                                            detaCaja.setMontoFinal(detaCaja.getMontoFinal().subtract(b.getMonto()));
                                            detaCaja.setMontoEntregado(detaCaja.getMontoEntregado().add(b.getMonto()));
                                            detaCaja.setActivoCajaybanco(detaCaja.getActivoCajaybanco().subtract(b.getMonto()));
                                            detaCaja.setActivoCuentaxcobrar(detaCaja.getActivoCuentaxcobrar().add(b.getMonto()));
                                            detaCaja.setMonto(detaCaja.getMonto().subtract(b.getMonto()));
                                            detaCaja.setPRespaldo(detaCaja.getPRespaldo().add(b.getMonto()));
                                            d.update();
                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                            out.write("<div class='error'>" + ex.getMessage() + "</div>");
                                        }
                                    }
                                    Map ticket = new HashMap();
                                    double te = b.getMonto().doubleValue();
                                    ticket.put("RUC", b.getTCuentaPersona().getTPersona().getRuc());
                                    ticket.put("DNI", b.getTCuentaPersona().getTPersona().getDocIdentidad());
                                    ticket.put("TIPOOPERACION", b.getTOperacion().getTTipoOperacion().getNombre());
                                    ticket.put("IDOPERACION", b.getTOperacion().getIdOperacion());
                                    ticket.put("NUMEROOPERACION", b.getTOperacion().getNumeroOperacion());
                                    ticket.put("NUMEROCRED", b.getTCuentaPersona().getNumCta().substring(7, 12));
                                    ticket.put("CODIGOCAJA", b.getTOperacion().getTPersonaCaja().getTCaja().getCodCaja());
                                    ticket.put("FILIAL", b.getTOperacion().getTPersonaCaja().getTCaja().getTFilial().getNombre());
                                    ticket.put("MONEDA", b.getTCuentaPersona().getTMoneda().getNombre());
                                    ticket.put("FECHA", b.getTOperacion().getFecha());
                                    ticket.put("NOMBRE", b.getTCuentaPersona().getTPersona().getNombre());
                                    ticket.put("APELLIDOS", b.getTCuentaPersona().getTPersona().getApellidos());
                                    String cx = b.getTCuentaPersona().getNumCta();
                                    ticket.put("NUMEROCUENTA", cx.substring(0, 5) + "-" + cx.substring(5, 6) + "-" + cx.substring(6));
                                    ticket.put("CODTIPOCUENTA", b.getTCuentaPersona().getTTipoCuenta().getCodigoCuenta());
                                    ticket.put("TIPOCUENTA", b.getTCuentaPersona().getTTipoCuenta().getDescripcion());
                                    ticket.put("MONTO", CurrencyConverter.formatToMoneyUS(b.getMonto().doubleValue(), 2));
                                    ticket.put("INTERES", CurrencyConverter.formatToMoneyUS(0D, 2));
                                    ticket.put("SALDO", CurrencyConverter.formatToMoneyUS(b.getTCuentaPersona().getSaldo().doubleValue(), 2));
                                    ticket.put("PP", fpp);
                                    ticket.put("TE", CurrencyConverter.formatToMoneyUS(te, 2));
                                    session.setAttribute("ticketp", ticket);

                    %>
                    <div class="story">
                        <form id="formc" name="formc" method="post" action=""><div style="display:none">
                                <input name="xl_sheet_no" id="xl_sheet_no" type="hidden" value="panel1" />
                                <input name="xl_postback" id="xl_postback" type="hidden" value="1" />
                            </div>
                            <div class="eebuttonbar_top">
                                <input class="eebuttons" type="button" value="Imprimir" name="xl_print_top" onclick="window.print();">
                            </div>
                            <div>
                                <table border="0">
                                    <tr>
                                        <td colspan="2" class="ee106">CRONOGRAMA DE PAGOS, CON PERIODO DE <%=b.getDiasEntreCuotas()%> DÍAS</td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" class="ee130">TASA DE INTERÉS COMPENSATORIA EFECTIVA ANUAL A 360 DÍAS: <%=b.getTasaInteresPrestamo()%>% </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" class="ee130">TASA DE INTERÉS MORATORIA ANUAL: <%=b.getTasaInteresMoratorio()%>%</td>
                                    </tr>
                                    <tr>
                                        <td class="ee130">NOMBRE: <%=(b.getTCuentaPersona().getTPersona().getApellidos() + "," + b.getTCuentaPersona().getTPersona().getNombre())%></td>
                                        <td class="ee130">DNI/RUC: <%=(b.getTCuentaPersona().getTPersona().getDocIdentidad() + (b.getTCuentaPersona().getTPersona().getTCategoriaPersona().getDescripcion() == "JURIDICA" ? "/" + b.getTCuentaPersona().getTPersona().getRuc() : ""))%></td>
                                    </tr>
                                    <tr>
                                        <td class="ee130">CUENTA: <%=(b.getTCuentaPersona().getNumCta())%></td>
                                        <td>DIVISION DE CUOTAS: MENSUAL</td>
                                    </tr>
                                    <tr>
                                        <td class="ee130" colspan="2">SON: <%=(NumberUtil.convertirLetrasDecimal(b.getMonto().doubleValue()).toUpperCase())%>  <%=("NUEVO SOL" == moneda.getNombre() ? " NUEVOS SOLES" : ("DOLAR" == moneda.getNombre() ? " DOLARES AMERICANOS" : ("EURO" == moneda.getNombre() ? " EUROS" : moneda.getNombre())))%></td>
                                    </tr>
                                </table>
                            </div>
                            <hr>
                            <div id="panel1" style='display:block;'>
                                <table style='border-collapse:collapse;' border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF">
                                    <col width="78.00" /><col width="130.00" /><col width="120.00" /><col width="115.00" /><col width="135.00" /><col width="244.00" /><col width="1.00" />
                                    <tr style='height:17pt'>
                                        <td class='ee100'>Desembolso:</td>
                                        <td class='ee106'><%=moneda.getSimbolo()%> <%=CurrencyConverter.formatToMoneyUS(b.getMonto().doubleValue(), 2).replaceAll(",", " ")%>
                                        </td>
                                        <td class='ee100'>
                                            Tasa Diario
                                        </td>
                                        <td class='ee106'>
                                            <%=DateUtil.eedisplayPercentND(Math.pow(1D + b.getTasaInteresPrestamo().doubleValue() / 100D, 1D / 360D) - 1D, 4)%>
                                        </td>
                                        <td class='ee100'>
                                            Fecha hoy:
                                        </td>
                                        <td class='ee106'>
                                            <%=b.getFechaDesembolso()%>
                                        </td>
                                        <td class='ee100'>
                                            &nbsp;
                                        </td>
                                    </tr>
                                    <tr style='height:13pt'>
                                        <td class='ee100'>
                                            Plazo :
                                        </td>
                                        <td class='ee109'><%=b.getNumeroCuotas()%> cuotas
                                        </td>
                                        <td class='ee100'>
                                            Pago cuota:
                                        </td>
                                        <td class='ee106'><%=moneda.getSimbolo()%>
                                            <%=CurrencyConverter.formatToMoneyUS(cxu, 2).replaceAll(",", " ")%>
                                        </td>
                                        <td class='ee100'>
                                            Fecha Primer pago:
                                        </td>
                                        <td class='ee111'>
                                            <%=fpp%>
                                        </td>
                                        <td class='ee100'>&nbsp;</td>
                                    </tr>
                                    <tr style='height:14pt'>
                                        <td class='ee100'>
                                            TEA:
                                        </td>
                                        <td class='ee114'><%=b.getTasaInteresPrestamo()%>
                                        </td>
                                        <td class='ee100'>
                                            Cuotas :
                                        </td>
                                        <td class='ee106'>
                                            <%=DateUtil.eedisplayFloat(b.getNumeroCuotas())%> cuotas
                                        </td>
                                        <td class='ee100'>
                                            VIGENCIA:
                                        </td>
                                        <td class='ee111'>
                                            <%=fup%>
                                        </td>
                                        <td class='ee100'>&nbsp;</td>
                                    </tr>
                                </table>
                                <%=s%>

                            </div><br><hr>
                            <div class="cl">
                                <p>AMIGO CLIENTE, UD. PUEDE CANCELAR SUS CUOTAS EN CUALQUIERA DE NUESTRAS OFICINAS, PARA LO CUAL DEBE TENER LA CONSIDERACIÓN DEL COBRO DE UNA COMISIÓN, QUE ES DE 1.5% DEL MONTO APROBADO AL MOMENTO DEL DESEMBOLSO.</p>
                                <p style="font-weight:bold">SI UD. TUVIERA ALGÚN RECLAMO Y/O DENUNCIA SOBRE NUESTROS SERVICIOS, REALÍCELO EN PRIMERA INSTANCIA A TRAVÉS DE NUESTRAS OFICINAS DE ATENCIÓN AL CLIENTE Y/O NUESTRA PÁGINA WEB. LA SBS E
                                    INDECOPI, REPRESENTAN LA SEGUNDA INSTANCIA DE RESOLUCIÓN DE RECLAMOS AL QUE PUEDA RECURRIR.</p></div>
                            <div class="eebuttonbar_bottom">
                                <input class="eebuttons" type="button" value="Imprimir" name="xl_print_bottom" onclick="window.print();">
                                <input class="eebuttons" type="button" value="Imprimir Comprobante" name="xl_bottom" onclick="printDes();">
                                <span style="margin-left:1px;"> Disfrute el crédito! </span>
                            </div>
                            <script language="javascript" type="text/javascript">
                                function reset_onclick(x){
                                    document.formc.reset();
                                    postcode();
                                }
                                function postcode(){};
                                function eequerystring(){
                                    var querystring=document.location.search;
                                    if(querystring.length>0){
                                        variables=(querystring.substring(1)).split("&");
                                        var variable;
                                        var key;
                                        var value;
                                        for(var ii=0;ii<variables.length;ii++){
                                            variable=variables[ii].split("=");
                                            key=unescape(variable[0]);
                                            value=unescape(variable[1]);
                                            if(document.formc[key]!=null){
                                                document.formc[key].value=value;
                                            }
                                        }
                                    }
                                }
                                function initial_update(){
                                    postcode('');
                                    eequerystring();
                                }
                            </script>
                        </form>
                    </div>
                </div>
                <div id="conContrato">
                    <blockquote>
                        <blockquote>
                            <blockquote>
                                <blockquote>
                                    <blockquote>
                                        <form name="form1" method="post" action="">
                                            <input type="checkbox" id="sinContrato" name="sinContrato" value="" onclick="contratos();" >
                                            <p id="habilitar">Habilitar contrato.</p><p id="inhabilitar" style="display:none;">Inhabilitar contrato.</p><br />
                                            <span>
                                                <input type="button" id="contrato" name="abrirContrato" value="Contrato" disabled onclick="abrirVentanac();">
                                                <input name="cerrarContrato" id="contrato2" type="button" disabled onClick="cerrarVentanac();this.disabled=true" value="Cerrar Contrato">
                                            </span>
                                            <input type="hidden" id="contratoGen" name="contratoGen" value="<%=tcontrato.getIdcontrato()%>">
                                        </form>
                                        <p>&nbsp;</p>
                                    </blockquote>
                                </blockquote>
                            </blockquote>
                        </blockquote>
                    </blockquote>
                    <p>&nbsp;</p>
                </div>
                <%
                            }
                %>
            </div>
        </div>
        <%@include file="../common/footer.jsp" %>
        <noscript>The browser does not support JavaScript. The calculations created using financeBank will not work. Please access the web page using another browser.<p></p></noscript>
    </body>
</html>
