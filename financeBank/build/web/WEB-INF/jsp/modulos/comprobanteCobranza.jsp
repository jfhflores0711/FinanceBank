<%-- 
    Document   : comprobanteCobranza
    Created on : 16-abr-2010, 10:10:44
    Author     : oscar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<%@page import="org.finance.bank.model.dao.DAOGeneral" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8" />
        <link rel="shortcut icon" href="./images/favicon.ico" />
        <title><%=(DateUtil.getDateTime("yyyymmddhhmmssS", new Date()))%>|financeBank! TicketCobranzas</title>
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
        <script type="text/javascript" src="js/scriptshow/scriptCCobranzas.js"></script>
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
                    request.setAttribute("idmenu", "20110228202911502983");
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
                        System.out.println("Antes de invocar");
                        String a = (String) request.getParameter("a");
                        String mensaje = "";
                        Map ticket = (Map) session.getAttribute("ticketc");
                        ticket.put("CUOTA", "");
                        String idprest = (String) ticket.get("IDPRESTAMO");
                        out.print("<script>alert('Aún no se ha procesado la trasanción con id "+a+" ');</script>");
                        /*List li = d.findAll("from TRegistroPrestamo where idprestamo='" + idprest + "'");
                        TRegistroPrestamo xtrp = (TRegistroPrestamo) li.get(0);
                        Set detalles = xtrp.getTDetallePrestamos();
                        int cdeP = detalles.size();
                        boolean smoke = true;
                        Iterator iDet = detalles.iterator();
                        if (cdeP > 0) {
                            Vector val = new Vector();
                            double monto = 0.0D;
                            double interes = 0.0D;
                            double itf = 0.0D;
                            double mora = 0.0D;
                            double otros = 0.0D;
                            double te = 0.0D;
                            while (iDet.hasNext()) {
                                TDetallePrestamo xtdp = (TDetallePrestamo) iDet.next();
                                String name = xtdp.getIddetalleprestamo();
                                String value1 = request.getParameter("x" + name);
                                String value2 = request.getParameter("z" + name);
                                if (null == value1 && null == value2) {
                                    if ("PENDIENTE".equals(xtdp.getEstado())) {
                                        smoke = false;
                                    }
                                    continue;
                                }
                                String pp = "---------";
                                String cx = "";
                                if ("1".equals(value1)) {
                                    if ("1".equals(value2)) {
                                        monto += xtdp.getMontoCapital().doubleValue();
                                        interes += xtdp.getInteresPrestamo().doubleValue();
                                        mora += xtdp.getInteresMoratorio().doubleValue();
                                        itf += xtdp.getIntSuspendidoItf().doubleValue();
                                        otros += xtdp.getInteresComp().doubleValue() + DateUtil.round(PrestamoUtil.calCom(xtdp.getMontoCapital().doubleValue(), xtdp.getComision().doubleValue()), 2);
                                        cx = xtdp.getNumeroCuota().toString();
                                        val.add(xtdp);
                                    } else {
                                        TDetallePrestamo tdpx = (TDetallePrestamo) d.load(TDetallePrestamo.class, xtdp.getIddetalleprestamo());
                                        tdpx.setMontoTotal(tdpx.getMontoCapital());
                                        tdpx.setComision(BigDecimal.ZERO);
                                        tdpx.setIntSuspendidoItf(BigDecimal.ZERO);
                                        tdpx.setInteresPrestamo(BigDecimal.ZERO);
                                        tdpx.setInteresMoratorio(BigDecimal.ZERO);
                                        tdpx.setInteresComp(BigDecimal.ZERO);
                                        d.update();
                                        xtdp = tdpx;
                                        cx = xtdp.getNumeroCuota().toString();
                                        monto += xtdp.getMontoCapital().doubleValue();
                                        val.add(xtdp);
                                    }
                                } else {
                                    smoke = false;
                                }
                                te = monto + interes + mora + itf + otros;
                                ticket.put("MONTO", CurrencyConverter.formatToMoneyUS(monto, 2));
                                ticket.put("ITF", CurrencyConverter.formatToMoneyUS(itf, 2));
                                ticket.put("INTERES", CurrencyConverter.formatToMoneyUS(interes, 2));
                                ticket.put("MORA", CurrencyConverter.formatToMoneyUS(mora, 2));
                                ticket.put("OTROS", CurrencyConverter.formatToMoneyUS(otros, 2));
                                ticket.put("TE", CurrencyConverter.formatToMoneyUS(te, 2));
                                ticket.put("PP", pp);
                                ticket.put("CUOTA", ("".equals(((String) ticket.get("CUOTA"))) ? cx : (String) ticket.get("CUOTA") + "," + cx));
                            }
                            Iterator ob = val.iterator();
                            boolean yesno = false;
                            TPersonaCaja tpCaja = (TPersonaCaja) d.load(TPersonaCaja.class, session.getAttribute("USER_ID_PERSONA_CAJA").toString());
                            TTipoOperacion ttOperacion = (TTipoOperacion) d.load(TTipoOperacion.class, "TIPC7");
                            String idInitForNewestTable = DateUtil.convertDateId();
                            BigDecimal mf = xtrp.getTCuentaPersona().getSaldo().add(new BigDecimal(monto));*/
                            /** Por cada Operación sólo una cobranza...**/
                           /* int cc = 0;
                            while (ob.hasNext()) {
                                cc += 1;
                                TOperacion op = new TOperacion();
                                op.setIdOperacion(idInitForNewestTable);
                                String idprn = PrestamoUtil.nextNumberPrestamo("0501");
                                op.setDescripcion(idprn);
                                op.setFecha(DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", new Date()));
                                op.setTTipoOperacion(ttOperacion);
                                op.setTMoneda(xtrp.getTCuentaPersona().getTMoneda());
                                op.setEstado("ACTIVO");
                                op.setIdUser((String) session.getAttribute("USER_ID"));
                                op.setIpUser((String) session.getAttribute("USER_IP"));
                                op.setDateUser(DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", new Date()));
                                op.setTPersonaCaja(tpCaja);
                                op.setNumeroOperacion(numeroOperacion.getNumber((String) session.getAttribute("USER_CODFILIAL"), (String) session.getAttribute("USER_CODCAJA")));
                                op.setSaldofinal(mf);
                                d.persist(op);
                                if (!yesno) {
                                    yesno = true;
                                    ticket.put("TIPOOPERACION", op.getTTipoOperacion().getNombre());
                                    ticket.put("IDOPERACION", op.getIdOperacion());
                                    ticket.put("NUMEROOPERACION", op.getNumeroOperacion());
                                    ticket.put("NUMEROCRED", op.getDescripcion());
                                    ticket.put("CODIGOCAJA", op.getTPersonaCaja().getTCaja().getCodCaja());
                                    ticket.put("FILIAL", op.getTPersonaCaja().getTCaja().getTFilial().getNombre());
                                    ticket.put("PP", DateUtil.eedatefmt(DateUtil.fmtdate13, (DateUtil.today() + 30 * cc)));
                                    ticket.put("FECHA", DateUtil.getDateTime("dd/MM/yyyy HH:mm:ss", new Date()));
                                }
                                TDetallePrestamo tdp = (TDetallePrestamo) d.load(TDetallePrestamo.class, ((TDetallePrestamo) ob.next()).getIddetalleprestamo());
                                tdp.setEstado("CANCELADO");
                                d.update();
                                TCobranza tc = new TCobranza();
                                tc.setIdcobranza(idInitForNewestTable);
                                tc.setTDetallePrestamo(tdp);
                                tc.setEstado("ACTIVO");
                                tc.setTOperacion(op);
                                tc.setDateUser(DateUtil.getDateTime("yyyy/MM/dd hh:mm:ss.S", new Date()));
                                tc.setFecha(DateUtil.getDateTime("yyyy/MM/dd hh:mm:ss.S", new Date()));
                                tc.setIdUser((String) session.getAttribute("USER_ID"));
                                tc.setIpUser((String) session.getAttribute("USER_IP"));
                                d.persist(tc);
                                TCuentaPersona c = (TCuentaPersona) d.load(TCuentaPersona.class, xtrp.getTCuentaPersona().getIdcuentapersona());
                                c.setSaldo(c.getSaldo().add(new BigDecimal(monto)));
                                c.setSaldoSinInteres(c.getSaldo().add(new BigDecimal(monto)));
                                d.update();
                            }
                            //TDetalleCaja detaCaja = iniDetalleCaja.detalleActivaCaja((String) session.getAttribute("USER_CODCAJA"), xtrp.getTCuentaPersona().getTMoneda().getCodMoneda(), DateUtil.getDate(new Date()));
                            //TDetalleCaja detaCaja = (TDetalleCaja) d.load(TDetalleCaja.class, DateUtil.getDate(new Date()).replaceAll("/", "") + codcaja + xtrp.getTCuentaPersona().getTMoneda().getCodParaNumCuenta() + "00");
                            TLogFinance detaCaja = (TLogFinance) d.load(TDetalleCaja.class, "LOG" + codcaja + xtrp.getTCuentaPersona().getTMoneda().getCodParaNumCuenta());
                            detaCaja.setMontoFinal(detaCaja.getMontoFinal().add(new BigDecimal(te)));
                            detaCaja.setMontoInicial(detaCaja.getMontoRecibido().add(new BigDecimal(te)));
                            detaCaja.setActivoCajaybanco(detaCaja.getActivoCajaybanco().add(new BigDecimal(te)));
                            detaCaja.setActivoCuentaxcobrar(detaCaja.getActivoCuentaxcobrar().subtract(new BigDecimal(monto)));
                            detaCaja.setMonto(detaCaja.getMonto().add(new BigDecimal(te)));
                            detaCaja.setPRespaldo(detaCaja.getPRespaldo().subtract(new BigDecimal(monto)));
                            d.update();
                            if (smoke) {
                                TRegistroPrestamo trpx = (TRegistroPrestamo) d.load(TRegistroPrestamo.class, xtrp.getIdprestamo());
                                trpx.setEstado("CANCELADO");
                                ticket.put("PP", "--------------");
                                d.update();
                                TCuentaPersona st = (TCuentaPersona) d.load(TCuentaPersona.class, xtrp.getTCuentaPersona().getIdcuentapersona());
                                st.setEstado("ACTIVO");
                                d.update();
                            }
                            String c = (String) ticket.get("CUOTA");
                            if (!"".equals(c)) {
                                String[] cr = c.split(",");
                                int k = 0;
                                int g[] = new int[cr.length];
                                for (int j = 0; j < cr.length; j++) {
                                    if (!"".equals(cr[j].toString())) {
                                        g[k++] = Integer.parseInt(cr[j]);
                                    }
                                }
                                Arrays.sort(g);
                                String s = "";
                                for (int j = 0; j < g.length; j++) {
                                    s += (("".equals(s)) ? String.valueOf(g[j]) : ("," + String.valueOf(g[j])));
                                }
                                while (s.startsWith("0,")) {
                                    s = s.replaceFirst("0,", "");
                                }
                                ticket.put("CUOTA", s);
                                ticket.put("PP", DateUtil.eedatefmt(DateUtil.fmtdate13, (DateUtil.today() + 30 * (g.length))));
                            }*/
            %>
            <div class="ticketpago" id="ticketpago">
                <%--form name="fcambio" method="post" action="certificadoplazofijo.htm">
                    <table style="font-size:small;" border="0"><tr>
                            <td align="left" valign="top"><table width="526" border="0" >
                                    <tr>
                                        <td colspan="3" style="font:bolder;font-size:large">** COBRANZAS **</td>
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
                                        <td colspan="3">CLIENTE: <div style="font-weight:bold;font-size:medium"><%=ticket.get("APELLIDOS")%> , <%=ticket.get("NOMBRE")%></div> </td>
                                    </tr>
                                    <tr>
                                        <td colspan="3">OPERACION EN EFECTIVO:<div style="font-weight:bold;font-size:medium"> PAGO DE <%=ticket.get("TIPOOPERACION")%></div></td>
                                    </tr>
                                    <tr style="display:none">
                                        <td colspan="3" id="tdIdOp"><%=ticket.get("IDOPERACION")%></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">MONEDA: <%=("NUEVO SOL" == ticket.get("MONEDA").toString() ? " NUEVOS SOLES" : ("DOLAR" == ticket.get("MONEDA").toString() ? " DOLARES AMERICANOS" : ("EURO" == ticket.get("MONEDA").toString() ? " EUROS" : ticket.get("MONEDA"))))%></td><td>COUTA:<%=ticket.get("CUOTA")%></td>
                                    </tr>
                                    <tr>
                                        <td colspan="3">
                                            <%
                                                                        if (ticket.get("TIPOOPERACION").equals("COBRANZA")) {
                                                                            out.write("NUM CRÉDITO (PRÉSTAMO):" + ticket.get("NUMEROCRED"));
                                                                        }
                                            %></td>
                                    </tr>
                                    <tr>
                                        <td colspan="3">-------------------- PAGOS EN EFECTIVO ------------------</td>
                                    </tr>
                                    <tr>
                                        <td>MONTO: </td><td align="right" style="font-weight: bold"><%=ticket.get("MONTO")%> </td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>INTERESES: </td><td align="right" style="font-weight: bold"> <%=ticket.get("INTERES")%></td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>I.T.F.: </td><td align="right"> <%=ticket.get("ITF")%></td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>MORA:</td><td align="right" style="font-weight: bold"> <%=ticket.get("MORA")%></td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                    </tr><tr>
                                        <td>COMISION Y OTROS:</td><td align="right"> 0.00</td> <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td colspan="3">--------------------------------------------------------</td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                        <td><div>TOTAL PAGADO: <%=ticket.get("TE")%></div></td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td colspan="3" align="center">Próxima fecha de pago: <%=ticket.get("PP")%></td>
                                    </tr>
                                    <tr id="trSaldo">
                                        <td id="tdvacio">&nbsp;</td></tr>
                                    <tr><td id="tdvacio" colspan="3">&nbsp;</td></tr>
                                    <tr><td id="tdvacio" colspan="3">&nbsp;</td></tr>
                                    <tr><td id="tdSaldo" style="text-align:center" colspan="3"><div id="divSaldo">_________________</div></td></tr>
                                    <tr><td id="tdSaldo" style="text-align:center" colspan="3"><div id="divSaldo">CLIENTE</div></td>
                                    </tr>
                                    <tr id="trnumCuenta" style="display:none">
                                        <td colspan="2" id="tdnumcuenta"><%=ticket.get("NUMEROCUENTA")%></td>
                                    </tr>
                                </table></td><td align="right" valign="top"><table width="525" border="0" >
                                    <tr>
                                        <td colspan="3" style="font:bolder;font-size:large">** COBRANZAS **</td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td><td>&nbsp;</td>
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
                                        <td colspan="3">CLIENTE: <div style="font-weight:bold;font-size:medium"><%=ticket.get("APELLIDOS")%> , <%=ticket.get("NOMBRE")%></div> </td>
                                    </tr>
                                    <tr>
                                        <td colspan="3">OPERACION EN EFECTIVO:<div style="font-weight:bold;font-size:medium"> PAGO DE <%=ticket.get("TIPOOPERACION")%></div></td>
                                    </tr>
                                    <tr style="display:none">
                                        <td colspan="3" id="tdIdOp"><%=ticket.get("IDOPERACION")%></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">MONEDA: <%=("NUEVO SOL" == ticket.get("MONEDA").toString() ? " NUEVOS SOLES" : ("DOLAR" == ticket.get("MONEDA").toString() ? " DOLARES AMERICANOS" : ("EURO" == ticket.get("MONEDA").toString() ? " EUROS" : ticket.get("MONEDA"))))%></td><td>COUTA:<%=ticket.get("CUOTA")%></td>
                                    </tr>
                                    <tr>
                                        <td colspan="3">
                                            <%
                                                                        if (ticket.get("TIPOOPERACION").equals("COBRANZA")) {
                                                                            out.write("NUM CRÉDITO (PRÉSTAMO):" + ticket.get("NUMEROCRED"));
                                                                        }
                                            %></td>
                                    </tr>
                                    <tr>
                                        <td colspan="3">----------------------PAGOS EN EFECTIVO---------------------</td>
                                    </tr>
                                    <tr>
                                        <td>MONTO: </td><td align="right" style="font-weight: bold"><%=ticket.get("MONTO")%> </td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>INTERESES: </td><td align="right" style="font-weight: bold"> <%=ticket.get("INTERES")%></td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>I.T.F.: </td><td align="right"> <%=ticket.get("ITF")%></td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>MORA:</td><td align="right" style="font-weight: bold"> <%=ticket.get("MORA")%></td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                    </tr><tr>
                                        <td>COMISION Y OTROS:</td><td align="right" style="font-weight: bold"> 0.00</td> <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td colspan="3">--------------------------------------------------------</td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                        <td><div>TOTAL PAGADO: <%=ticket.get("TE")%></div></td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td colspan="3" align="center">Próxima fecha de pago: <%=ticket.get("PP")%></td>
                                    </tr>
                                    <tr id="trnumCuenta2" style="display:none">
                                        <td colspan="2" id="tdnumcuenta2"><%=ticket.get("NUMEROCUENTA")%></td>
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
                                    <input name="cmdExtornar" id="cmdExtornar" type="button" value="Extornar" onclick="ventanaNueva2();">
                                    <input name="Regresar" id="Cerrar" type="button" onclick="window.location='cobranzas.htm';" value="Cerrar" />

                                </blockquote>
                            </blockquote>
                        </blockquote>
                    </div>
                </form--%>
            </div>
            <%
                       /* }
                        out.write(mensaje);*/
            %>
        </div>
    </body>
</html>
