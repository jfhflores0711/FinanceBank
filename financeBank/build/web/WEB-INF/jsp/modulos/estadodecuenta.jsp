<%--
    Document   : ticket
    Created on : 19-mar-2010, 10:01:09
    Author     : roger
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.io.IOException,java.io.PrintWriter,java.sql.Connection,
        org.finance.bank.ds.*,
        org.finance.bank.model.BOperacion,
        org.finance.bank.model.dao.DAOoperacion,
        java.util.ArrayList" %>

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
        <script type="text/javascript" src="js/scriptshow/scriptEstadoCuenta.js"></script>
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
                    request.setAttribute("idmenu", "1");
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
            <form action="" method="post" id="frmTicketEstadoCuenta" name="frmTicketEstadoCuenta">
                <br><br>
                <center>
                    <%
                                String fechaselected = request.getParameter("fecha");
                                String hasta = request.getParameter("hasta");
                                String idcuenta = request.getParameter("idcuenta");
                                String userlogin = (String) session.getAttribute("USER_LOGIN");
                                Date f1 = DateUtil.convertStringToDate(fechaselected);
                                Date f2 = DateUtil.convertStringToDate(hasta);
                                //hasta = DateUtil.getDate(DateUtil.getDateUpDay(f2));
                                int i = f1.compareTo(f2);
                                String f = (i == -1) ? " (fecha >= '" + fechaselected + "' and fecha <= '" + DateUtil.getDate(DateUtil.getDateUpDay(f2)) + "') " : ((i == 0) ? " fecha like '" + fechaselected + "%' " : " (fecha >= '" + hasta + "' and fecha <= '" + DateUtil.getDate(DateUtil.getDateUpDay(f1)) + "') ");
                                /*******este codigo no es necesario es solo para asegurar que cuando por
                                 * algun motivo no se pudieron eliminar la tabla Transaccion des pues de
                                 * consultar un estado de cuenta me elimina la tabla transaccion  para
                                 * no mezclar datos la nueva consulta de estado de cuentas con la anterior
                                 *********************************************************/
                                String code = "ACTIVO";
                                TCuentaPersona cuenta = (TCuentaPersona) d.load(TCuentaPersona.class, idcuenta);
                                String hql = "from TRegistroDepositoRetiro where TCuentaPersona='" + idcuenta + "' AND " + f + " and not estado='EXTORNADO' order by fecha desc";
                                List result = d.findAll(hql);
                                Iterator it = result.iterator();
                                if (result.size() > 0) {
                                    String sqlRun = "from TRegistroDepositoRetiro where numCta='" + cuenta.getNumCta() + "' and (not estado='EXTORNADO') and fecha >='" + fechaselected + "%' order by fecha";
                                    List resultR = d.findAll(sqlRun);
                                    Iterator itR = resultR.iterator();
                                    boolean first = true;
                                    BigDecimal rentail = BigDecimal.ZERO;
                                    BigDecimal rentailF = BigDecimal.ZERO;
                                    while (itR.hasNext()) {
                                        TRegistroDepositoRetiro depret = (TRegistroDepositoRetiro) itR.next();
                                        if (depret.getEstado().equals("EXTORNADO")) {
                                            continue;
                                        }
                                        if (first) {
                                            rentail = depret.getTOperacion().getSaldoFinalSinInteres()==null?depret.getTOperacion().getSaldofinal():depret.getTOperacion().getSaldoFinalSinInteres();
                                            System.out.println("rentail = " + rentail);
                                            rentailF = depret.getTOperacion().getSaldofinal()==null?BigDecimal.ZERO:(rentail.add(depret.getTCuentaPersona().getSaldo().subtract(depret.getTCuentaPersona().getSaldoSinInteres())));
                                            System.out.println("rentailF = " + rentailF);
                                            first = false;
                                            continue;
                                        }
                                        if (depret.getTOperacion().getTTipoOperacion().getCodigoTipoOperacion().equals("TIPC3")||depret.getTOperacion().getTTipoOperacion().getCodigoTipoOperacion().equals("TIPC11")) {
                                            rentail = rentail.add(depret.getImporte());
                                            rentailF = rentailF.add(depret.getImporte());
                                        } else {
                                            rentail = rentail.subtract(depret.getImporte());
                                            rentailF = rentailF.subtract(depret.getImporte());
                                        }
                                        TOperacion o = (TOperacion) d.load(TOperacion.class, depret.getTOperacion().getIdOperacion());
                                        o.setSaldoFinalSinInteres(rentail);
                                        o.setSaldofinal(rentailF);
                                        d.update();
                                        TCuentaPersona c = (TCuentaPersona) d.load(TCuentaPersona.class, depret.getTCuentaPersona().getIdcuentapersona());
                                        c.setSaldo(rentailF);
                                        c.setSaldoSinInteres(rentail);
                                        d.update();
                                        //Logger.getLogger("estadocuenta.jsp").log(Level.INFO, "->" + depret.getFecha() + " s=" + rentail);
                                    }
                                }
                                while (it.hasNext()) {
                                    TRegistroDepositoRetiro depret = (TRegistroDepositoRetiro) it.next();
                                    TTransaccion trans = new TTransaccion();
                                    trans.setIdtransaccion(DateUtil.convertDateId(idcuenta, "estadocuenta"));
                                    trans.setTipoOperacion(depret.getTOperacion().getTTipoOperacion().getNombre());
                                    trans.setMonto(depret.getImporte());
                                    trans.setFecha(depret.getFecha());
                                    trans.setIdcuenta(depret.getTCuentaPersona().getIdcuentapersona());
                                    trans.setUserLogin(userlogin);
                                    trans.setNombreReferencia(depret.getNombreRepresentante());
                                    trans.setApellidoReferencia(depret.getApellidosRepresentante());
                                    trans.setSaldo(depret.getTOperacion().getSaldoFinalSinInteres());
                                    trans.setEstado(code);
                                    d.persist(trans);
                                }
                                hql = "from TRegistroPrestamo where TCuentaPersona='" + idcuenta + "' AND " + f + " and not estado='EXTORNADO' order by fecha desc";
                                result = d.findAll(hql);
                                it = result.iterator();
                                while (it.hasNext()) {
                                    TRegistroPrestamo trp = (TRegistroPrestamo) it.next();
                                    log(trp.getIdprestamo());
                                    TTransaccion trans = new TTransaccion();
                                    trans.setIdtransaccion(DateUtil.convertDateId());
                                    trans.setTipoOperacion(trp.getTOperacion().getTTipoOperacion().getNombre());
                                    trans.setMonto(trp.getMonto());
                                    trans.setFecha(trp.getFecha());
                                    trans.setIdcuenta(trp.getTCuentaPersona().getIdcuentapersona());
                                    trans.setUserLogin(userlogin);
                                    trans.setNombreReferencia("");
                                    trans.setApellidoReferencia("");
                                    trans.setSaldo(trp.getTOperacion().getSaldoFinalSinInteres());
                                    trans.setEstado(code);
                                    d.persist(trans);
                                }
                                hql = "from TCobranza where TDetallePrestamo.TRegistroPrestamo.TCuentaPersona='" + idcuenta + "' AND " + f + " and not estado='EXTORNADO' order by fecha desc";
                                result = d.findAll(hql);
                                it = result.iterator();
                                while (it.hasNext()) {
                                    TCobranza tcob = (TCobranza) it.next();
                                    TTransaccion trans = new TTransaccion();
                                    trans.setIdtransaccion(DateUtil.convertDateId());
                                    trans.setTipoOperacion(tcob.getTOperacion().getTTipoOperacion().getNombre());
                                    trans.setMonto(tcob.getTDetallePrestamo().getMontoTotal());
                                    trans.setFecha(tcob.getFecha());
                                    trans.setIdcuenta(tcob.getTDetallePrestamo().getTRegistroPrestamo().getTCuentaPersona().getIdcuentapersona());
                                    trans.setUserLogin(userlogin);
                                    trans.setNombreReferencia("");
                                    trans.setApellidoReferencia("");
                                    trans.setSaldo(tcob.getTOperacion().getSaldoFinalSinInteres());
                                    trans.setEstado(code);
                                    d.persist(trans);
                                }
                                hql = "from TRegistroGiro where idcuentapersona='" + idcuenta + "' AND " + f + " and not estado='EXTORNADO' order by fecha desc";
                                result = d.findAll(hql);
                                it = result.iterator();
                                while (it.hasNext()) {
                                    TRegistroGiro trg = (TRegistroGiro) it.next();
                                    TTransaccion trans = new TTransaccion();
                                    trans.setIdtransaccion(DateUtil.convertDateId());
                                    trans.setTipoOperacion(trg.getTOperacion().getTTipoOperacion().getNombre());
                                    trans.setMonto(trg.getImporte());
                                    if ("CUENTA".equals(trg.getFpagoImporte())) {
                                        trans.setMonto(trans.getMonto().add(trg.getComision()));
                                    }
                                    trans.setFecha(trg.getFecha());
                                    trans.setIdcuenta(trg.getIdcuentapersona());
                                    trans.setUserLogin(userlogin);
                                    TPersona Persona = (TPersona) d.load(TPersona.class, trg.getIdUserPkDestino());
                                    trans.setNombreReferencia(Persona.getNombre());
                                    trans.setApellidoReferencia(Persona.getApellidos());
                                    trans.setSaldo(trg.getTOperacion().getSaldoFinalSinInteres());
                                    trans.setEstado(code);
                                    d.persist(trans);
                                }
                                hql = "from TTransaccion trans where trans.idcuenta='" + idcuenta + "' AND trans.userLogin='" + userlogin + "' order by trans.fecha";
                                result = d.findAll(hql);
                                it = result.iterator();
                                String fechaHoy = DateUtil.getDateTime("dd/MM/yyyy", new Date());
                                String HoraNow = DateUtil.getDateTime("HH:mm:ss", new Date());
                    %>
                    <table>
                        <tr>
                            <td colspan="2" align="center">******* ESTADO DE CUENTA *******</td>
                        </tr>
                        <tr>
                            <td colspan="2">&nbsp;</td>
                        </tr>
                        <tr>
                            <td>Titular</td><td>: <%=cuenta.getTPersona().getNombre()%> <%=cuenta.getTPersona().getApellidos()%></td>
                        </tr>
                        <tr>
                            <td><%if (cuenta.getTPersona().getTCategoriaPersona().getDescripcion().equals("NATURAL")) {%>DNI<%} else {%>RUC<%}%></td><td><%if (cuenta.getTPersona().getTCategoriaPersona().getDescripcion().equals("NATURAL")) {%>: <%=cuenta.getTPersona().getDocIdentidad()%><%} else {%>: <%= cuenta.getTPersona().getRuc()%><%}%></td>
                        </tr>
                        <tr>
                            <td>FECHA: <%=fechaHoy%></td><td>&nbsp;&nbsp;HORA: <%=HoraNow%></td>
                        </tr>
                        <tr>
                            <td colspan="2">&nbsp;</td>
                        </tr>
                        <tr>
                            <td colspan="2">- - - - - ULTIMOS MOVIMIENTOS - - - - - </td>
                        </tr>
                        <tr>
                            <td colspan="2">&nbsp;</td>
                        </tr>
                        <tr>
                            <td colspan="2"><%=cuenta.getTTipoCuenta().getDescripcion()%> <%=cuenta.getTMoneda().getSimbolo()%>: <%=cuenta.getNumCta()%></td>
                        </tr>
                        <tr>
                            <%
                                        String cadena = "";
                                        if (cuenta.getTMoneda().getCodMoneda().equals("PEN")) {
                                            cadena = "NUEVOS SOLES";
                                        } else if (cuenta.getTMoneda().getCodMoneda().equals("USD")) {
                                            cadena = "DOLARES AMERICANOS";
                                        } else {
                                            cadena = "EUROS";
                                        }
                            %>
                            <td colspan="2">Moneda: <%=cadena%></td>
                        </tr>
                        <tr>
                            <td colspan="2">&nbsp;</td>
                        </tr>
                    </table>
                </center>
                <center>
                    <table border="1">
                        <tr style="font-weight: bold">
                            <td>FECHA Y HORA</td>
                            <td>OPERACION</td>
                            <td align="center">REFERENCIA</td>
                            <td>MONTO</td>
                            <td>SALDO <br> DISPONIBLE</td>
                        </tr>
                        <%
                                    int j = 0;
                                    BigDecimal finalImprimible= BigDecimal.ZERO;                                    
                                    if (result.size() > 0) {
                                        while (it.hasNext()) {
                                            TTransaccion trg = (TTransaccion) it.next();
                                            j = j + 1;
                                            out.println("<tr>");
                                            String mifecha = "";
                                            if (trg.getFecha() != null) {
                                                mifecha = trg.getFecha();
                                                String[] c = mifecha.split(" ");
                                                String[] cs = c[0].split("/");
                                                mifecha = cs[2] + "/" + cs[1] + "/" + cs[0] + " " + c[1];
                                            }
                                            out.println("<td>" + mifecha + "</td>"
                                                    + "<td>" + trg.getTipoOperacion() + "</td>"
                                                    + "<td>");
                                            if (trg.getTipoOperacion().equals("RETIRO") || trg.getTipoOperacion().equals("DEPOSITO") || trg.getTipoOperacion().equals("GIRO")) {
                                                if (trg.getNombreReferencia() != null) {
                                                    out.print(trg.getNombreReferencia() + " ");
                                                }
                                                if (trg.getApellidoReferencia() != null) {
                                                    out.print(trg.getApellidoReferencia());
                                                }
                                            }
                                            out.print("</td><td align='right'>");
                                            if (trg.getTipoOperacion().equals("RETIRO") || trg.getTipoOperacion().equals("PRESTAMO") || trg.getTipoOperacion().equals("GIRO")) {
                                                out.print("&nbsp;&nbsp;-&nbsp;&nbsp; ");
                                            } else {
                                                out.print("&nbsp;");
                                            }
                                            out.print(CurrencyConverter.formatToMoneyUS(trg.getMonto().doubleValue(), 2) + "</td><td align='right'>");
                                            if (trg.getSaldo() == null) {
                                                out.print(CurrencyConverter.formatToMoneyUS(0D, 2) + "</td></tr>");
                                                finalImprimible=BigDecimal.ZERO;
                                            } else {
                                                out.print(CurrencyConverter.formatToMoneyUS(trg.getSaldo().doubleValue(), 2) + "</td></tr>");
                                                finalImprimible=trg.getSaldo();
                                            }
                                        }
                                        d.executeUpdate("delete from TTransaccion where idcuenta='" + idcuenta + "' and userLogin='" + userlogin + "'");
                                    } else {
                                        out.print("<tr><td colspan='7'>ESTA CUENTA NO POSEE TRANSACCIONES A PARTIR DEL " + fechaselected + "</td> </tr>");
                                    }
                        %>
                    </table>
                    <table>
                        <%
                                    fechaHoy = DateUtil.getDateTime("yyyy/MM/dd", new Date());
                                   int fDifNOw = DateUtil.convertStringToDate(fechaHoy).compareTo(f2);
                                   System.out.println("fDifNow = " + fDifNOw);
                                   if(fDifNOw==0){
                                    BigDecimal saldoSinInteresBig = cuenta.getSaldoSinInteres();
                                    BigDecimal saldoBig = cuenta.getSaldo();
                                    BigDecimal saldodeinteresBig = saldoBig.subtract(saldoSinInteresBig);
                                    String saldosininteres = CurrencyConverter.formatToMoneyUS(saldoSinInteresBig.doubleValue(), 2);
                                    String saldo = CurrencyConverter.formatToMoneyUS(saldoBig.doubleValue(), 2);
                                    String saldodeinteres = CurrencyConverter.formatToMoneyUS(saldodeinteresBig.doubleValue(), 2);
                        %>
                        <tr>
                            <td style="font-weight: bold;">SALDO DISPONIBLE: &nbsp;</td><td><%=cuenta.getTMoneda().getSimbolo()%> <%=saldosininteres%></td>
                        </tr>
                        <tr id="tri" style="visibility: hidden;font-weight: bold;">
                            <td>INTERESES: </td><td><%=cuenta.getTMoneda().getSimbolo()%> <%=saldodeinteres%></td>
                        </tr>
                        <tr id="trt" style="visibility: hidden;font-weight: bold;">
                            <td>SALDO TOTAL: </td><td><%=cuenta.getTMoneda().getSimbolo()%> <%=saldo%></td>
                        </tr>
                        <%
                                   } else{                                       
                       
                       %>
                         <tr>
                            <td style="font-weight: bold;">SALDO DISPONIBLE: &nbsp;</td><td><%=cuenta.getTMoneda().getSimbolo()%> <%=CurrencyConverter.formatToMoneyUS(finalImprimible.doubleValue(), 2) %></td>
                        </tr>
                        <tr id="tri" style="visibility: hidden;font-weight: bold;">
                            <td></td>
                        </tr>
                        <tr id="trt" style="visibility: hidden;font-weight: bold;">
                            <td></td>
                        </tr>
                        <%
                        
                                    }
                        %>
                    </table>
                    <blockquote>
                        <blockquote>
                            <blockquote>
                                <div id="ctrl">
                                    <center>
                                        <br>
                                        <input name="Submit" id="Submit" type="button" onClick="document.title=''; if (window.print)window.print();else alert('Su Navegador no dispone de esta opcion, Actualicelo con una version mas reciente');" value="Imprimir">
                                        <input name="btnRegresar" id="btnRegresar" type="button" onClick="regresarMenu();" value="Regresar">
                                        <b>Ver Intereses: &nbsp; <input id="ifup" type="checkbox" onclick="ifto();" </b>
                                    </center>
                                </div>
                            </blockquote>
                        </blockquote>
                    </blockquote>
                </center>
            </form>
        </div>
    </body>
</html>
