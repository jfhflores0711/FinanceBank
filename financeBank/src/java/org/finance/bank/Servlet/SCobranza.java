package org.finance.bank.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.finance.bank.bean.*;
import org.finance.bank.model.dao.DAOGeneral;
import org.finance.bank.util.CurrencyConverter;
import org.finance.bank.util.DateUtil;
import org.finance.bank.util.PrestamoUtil;

/**
 * @author admin
 * 08/05/2014
 */
public class SCobranza extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String o = (String) request.getParameter("o");
        HttpSession session = request.getSession(true);
        DAOGeneral dao = new DAOGeneral();
        String fi = (String) session.getAttribute("USER_CODFILIAL");
        String myCaja = session.getAttribute("USER_CODCAJA").toString();
        String myId = session.getAttribute("USER_ID").toString();
        String myIp = session.getAttribute("USER_IP").toString();
        String myPC = session.getAttribute("USER_ID_PERSONA_CAJA").toString();
        String idForInitNewestTable = DateUtil.convertDateId(myPC, SCobranza.class.getSimpleName());
        try {
            if ("b".equals(o)) {
                //buscar
                String contenido = request.getParameter("a");
                if (contenido != null) {
                    contenido = contenido.toUpperCase();
                } else {
                    contenido = "ZEROBUSQUEDA";
                }
                String tipoBusqueda = request.getParameter("b");
                if (tipoBusqueda == null) {
                    out.println("<center><fieldset style='border-width:3px'>");
                    out.println("<legend><b>RESULTADO DE BÚSQUEDA:</b></legend>");
                    out.println("<table id='tablapersonas' class='tabla'>");
                    out.println("<tr class='modo1'>");
                    out.println("<td style='color:red;font-size: 15px'>NO SE ENCONTRÓ NINGUNA PERSONA</td>");
                    out.println("</tr></table></fieldset></center>");
                    dao.cerrarSession();
                    out.close();
                    return;
                }
                int i = 0;
                String par, sql = "from TRegistroPrestamo";
                if ("nom".equals(tipoBusqueda)) {
                    sql += " where TCuentaPersona.TPersona.nombre like '%" + contenido + "%'";
                } else if ("ape".equals(tipoBusqueda)) {
                    sql += " where TCuentaPersona.TPersona.apellidos like '%" + contenido + "%'";
                } else if (tipoBusqueda.equals("dni")) {
                    sql += " where TCuentaPersona.TPersona.docIdentidad like '%" + contenido + "%'";
                } else if (tipoBusqueda.equals("ruc")) {
                    sql += " where TCuentaPersona.TPersona.ruc like '%" + contenido + "%'";
                } else {
                    if (!contenido.contains("-")) {
                        contenido = contenido + "-" + DateUtil.getDateTime("yyyy", new Date());
                    }
                    sql = "from TRegistroPrestamo where TCuentaPersona.numCta like '______%" + contenido + "'";
                }
                List lisnombre = dao.findAll(sql + " and TCuentaPersona.estado='PRESTAMO' and estadoSolicitud=4");
                if (lisnombre.size() >= 1) {
                    Iterator it = lisnombre.iterator();
                    out.println("<fieldset style='border-width:3px'>");
                    out.println("<legend><b>RESULTADO DE BUSQUEDA:</b></legend>");
                    out.println("<table id='tablapersonas' class='tabla' width='100%'>");
                    out.println("<tr><th>-.-</th><th style='display:none'>id</th>");
                    out.println("<th>Nro. Cred.</th><th>Nombres/R.S.</th><th>Moneda</th>");
                    out.println("<th>Monto Créd.</th><th>Estado</th>");
                    out.println("<th>Nro. Cuotas</th><th>Fecha D.</th></tr>");
                    while (it.hasNext()) {
                        TRegistroPrestamo px = (TRegistroPrestamo) it.next();
                        par = "modo1";
                        i = i + 1;
                        if (i % 2 == 0) {
                            par = "modo2";
                        }
                        out.println("<tr class='" + par + "'>");
                        out.println("<td ><input type='radio' id='rd" + i + "' name='rd' value='" + i + "' onclick='mostrarcuentadetallec(\"" + i + "\")'/></td>");
                        out.println("<td id='tdId" + i + "' style='display:none' >" + px.getIdprestamo() + "</td>");
                        out.println("<td id='tdN" + i + "'>" + px.getTCuentaPersona().getNumCta().substring(7) + "</td>");
                        out.println("<td id='tdA" + i + "'>" + px.getTCuentaPersona().getTPersona().getNombre() + " " + px.getTCuentaPersona().getTPersona().getApellidos() + " </td>");
                        out.println("<td id='tdM" + i + "' >" + px.getTCuentaPersona().getTMoneda().getSimbolo() + "</td>");
                        out.println("<td id='tdMT" + i + "'>" + px.getMonto() + "</td>");
                        out.println("<td>" + px.getEstado() + "</td>");
                        out.println("<td>" + px.getNumeroCuotas() + "</td>");
                        out.println("<td>" + px.getFechaDesembolso() + "</td>");
                        out.println("</tr>");
                    }
                    out.println("</table>");
                    out.println("</fieldset>");
                } else {
                    out.println("<center>");
                    out.println("<fieldset style='border-width:3px'>");
                    out.println("<legend><b>RESULTADO DE BUSQUEDA:</b></legend>");
                    out.println("<table id='tablapersonas' class='tabla'>");
                    out.println("<tr class='modo1'>");
                    out.println("<td style='color:red;font-size: 15px'>NO SE ENCONTRO NINGUNA PERSONA/CRÉDITO, PRUEBE CON OTROS PARAMETROS, SÓLO SE MUESTRAN CRÉDITOS PENDIENTES.</td>");
                    out.println("</tr></table>");
                    out.println("</fieldset>");
                    out.println("</center>");
                }
            } else if ("s".equals(o)) {
                String contenido = request.getParameter("a");
                if (contenido != null) {
                    contenido = contenido.toUpperCase();
                } else {
                    out.print("ERROR");
                    out.close();
                    return;
                }
                TRegistroPrestamo c = (TRegistroPrestamo) dao.load(TRegistroPrestamo.class, contenido);
                if (c != null) {
                    String moneda = "";
                    out.print("<fieldset style='border-width:3px'>"
                            + "<legend><b>DETALLE DEL CRÉDITO</b></legend>"
                            + "<table width='800' border='0' class='cobranza'>"
                            + "<tbody><tr>"
                            + "<td><input type='hidden' id='w_' value='" + c.getIdprestamo() + "'>"
                            + "<strong>Nro. Crédito</strong>"
                            + "</td><td>" + c.getTCuentaPersona().getNumCta().substring(7)
                            + "</td><td><strong>Moneda</strong></td>"
                            + "<td>" + c.getTCuentaPersona().getTMoneda().getNombre() + "</td>"
                            + "</tr><tr>"
                            + "<td><strong>NOMBRE/R.S.</strong></td><td>" + c.getTCuentaPersona().getTPersona().getNombre() + " "
                            + c.getTCuentaPersona().getTPersona().getApellidos()
                            + "</td><td><strong>Estado</strong></td><td>"
                            + (c.getSituacionCredito() == 1 ? "NORMAL" : "REF/REP") + "</td>"
                            + "</tr><tr><td>Nro. Cuotas</td>"
                            + "<td id='ctx'>" + c.getNumeroCuotas()
                            + "</td><td>Desembolso</td><td>" + c.getFechaDesembolso() + "</td>"
                            + "</tr><tr><td colspan='4'>");
                    out.print("<fieldset style='border-width:2px'><table><tr><td>Tipo de cobro:</td>"
                            + "<td><input type='radio' name='radio1' id='rn' value='rn' checked><a onclick='document.getElementById(\"rn\").click();' style='cursor: default;'>Normal&nbsp;(Fecha contable)</a></td>"
                            + "<td><input type='radio' name='radio1' id='rf' value='rf'><a onclick='document.getElementById(\"rf\").click();' style='cursor: default;'>Fecha valor (Pagaré)&nbsp;</a></td>"
                            + "<td><input type='radio' name='radio1' id='rv' value='rv'><a onclick='document.getElementById(\"rv\").click();' style='cursor: default;'>Voucher/Cheque&nbsp;</a></td>"
                            + "</tr><tr><td><input id='rc0' type='hidden' value='0'>CANCELACIÓN:</td>"
                            + "<td><input type='radio' name='radio2' id='ro' value='ro' onclick='rco();' checked><a onclick='document.getElementById(\"ro\").click();' style='cursor: default;'>Normal&nbsp;(por Operación)</a></td>"
                            + "<td colspan='2'><input type='radio' name='radio2' id='ra' value='ra' onclick='rca();'><a onclick='document.getElementById(\"ra\").click();' style='cursor: default;'>Fecha Adelantada(Con reprogramación)&nbsp;</a></td>"
                            + "</tr><tr><td colspan='4'><div id='cr'>");
                    moneda = c.getTCuentaPersona().getTMoneda().getSimbolo() + " ";
                    List dx = dao.findAll("from TDetallePrestamo where TRegistroPrestamo.idprestamo='" + c.getIdprestamo() + "' and estado='ACTIVO' order by numeroCuota");
                    Map ticket = new HashMap();
                    ticket.put("RUC", c.getTCuentaPersona().getTPersona().getRuc());
                    ticket.put("DNI", c.getTCuentaPersona().getTPersona().getDocIdentidad());
                    ticket.put("MONEDA", c.getTCuentaPersona().getTMoneda().getNombre());
                    ticket.put("NOMBRE", c.getTCuentaPersona().getTPersona().getNombre());
                    ticket.put("APELLIDOS", c.getTCuentaPersona().getTPersona().getApellidos());
                    ticket.put("NUMEROCUENTA", c.getTCuentaPersona().getNumCta().substring(7));
                    ticket.put("CODTIPOCUENTA", c.getTCuentaPersona().getTTipoCuenta().getCodigoCuenta());
                    ticket.put("TIPOCUENTA", c.getTCuentaPersona().getTTipoCuenta().getDescripcion());
                    ticket.put("IDPRESTAMO", c.getIdprestamo());
                    session.setAttribute("ticketc", ticket);
                    boolean estado = false;
                    out.print("<fieldset style='border-width:2px'>");
                    if (!dx.isEmpty()) {
                        out.print("<table width='800' border='1'><tr>"
                                + "<td colspan='7'><div align='center'><strong>CRONOGRAMA DE PAGOS</strong></div></td>"
                                + "</tr><tr>"
                                + "<td width='50'><div align='center'><strong>Numero de Cuota </strong></div></td>"
                                + "<td width='100'><div align='center'><strong>Fecha Vencto. </strong></div></td>"
                                + "<td width='120'><div align='center'><strong>Capital</strong></div></td>"
                                + "<td width='120'><div align='center'><strong>Inter&eacute;s</strong></div></td>"
                                + "<td width='120'><div align='center'><strong>Mora/Cargos</strong></div></td>"
                                + "<td width='120'><div align='center'><strong>Monto Total </strong></div></td>"
                                + "<td width='170'><div align='center'><strong>PAGAR</strong></div></td>"
                                + "</tr>");
                        for (Iterator it = dx.iterator(); it.hasNext();) {
                            TDetallePrestamo xprestamo = (TDetallePrestamo) it.next();
                            out.print("<tr><td><div align='center'>" + xprestamo.getNumeroCuota().toString() + "<input type='hidden' value='" + xprestamo.getIddetalleprestamo() + "' id='_" + xprestamo.getNumeroCuota() + "'></div></td>");
                            double hoy = DateUtil.today();
                            double tim = xprestamo.getTRegistroPrestamo().getTasaInteresMoratorio().doubleValue();
                            long fechap = DateUtil.convertStringToDate("dd/MM/yyyy", xprestamo.getFechaPagoOp()).getTime();
                            int dc = DateUtil.difDiasFullS(xprestamo.getFechaPagoOp(), DateUtil.getDate(new Date()), "dd/MM/yyyy", "yyyy/MM/dd");
                            if (xprestamo.getEstadoPago() == 0) {
                                if (fechap <= (new Date()).getTime()) {
                                    xprestamo.setMora(BigDecimal.valueOf(PrestamoUtil.calculoMora(dc, hoy, xprestamo.getMontoCapital().doubleValue(), tim)));
                                } else {
                                    xprestamo.setMora(BigDecimal.ZERO);
                                    dc = 0;
                                }
                                xprestamo.setMontoTotal(xprestamo.getMontoCapital().add(xprestamo.getInteresEfectivo()).add(xprestamo.getMora()));
                                dao.update();
                                out.print("<td align='center'>" + xprestamo.getFechaPagoOp() + "</td>"
                                        + "<td align='center'><div align='right'>" + (moneda + " " + CurrencyConverter.formatToMoneyUS(xprestamo.getMontoCapital().doubleValue(), 2)) + "</div>"
                                        + " <input type='hidden' name='a1" + xprestamo.getNumeroCuota() + "' value='" + xprestamo.getMontoCapital().doubleValue() + "' id='a1" + xprestamo.getNumeroCuota() + "'></td>"
                                        + "<td align='center'><div align='center'>" + moneda + " " + CurrencyConverter.formatToMoneyUS(xprestamo.getInteresEfectivo().doubleValue(), 2) + "</div>"
                                        + " <input type='hidden' name='a2" + xprestamo.getNumeroCuota() + "' value='" + xprestamo.getInteresEfectivo().doubleValue() + "' id='a2" + xprestamo.getNumeroCuota() + "'></td>"
                                        + "<td align='center'><div align='center' id='di4" + xprestamo.getNumeroCuota() + "'>" + (moneda + " " + CurrencyConverter.formatToMoneyUS(xprestamo.getInteresMoratorio().doubleValue() + xprestamo.getComision().doubleValue(), 2)) + (dc == 0 ? "" : " (Mora=" + dc + "d)") + "</div>"
                                        + "<input type='hidden' name='a3" + xprestamo.getNumeroCuota() + "' value='" + CurrencyConverter.formatToMoneyUS(xprestamo.getInteresMoratorio().doubleValue() + xprestamo.getComision().doubleValue(), 2).replace(",", "")
                                        + "' id='a3" + xprestamo.getNumeroCuota() + "'></td>"
                                        + "<td align='center'><div align='right' id='dv4" + xprestamo.getNumeroCuota() + "'>" + (moneda + " " + CurrencyConverter.formatToMoneyUS(xprestamo.getMontoTotal().doubleValue(), 2)) + "</div>"
                                        + "<input type='hidden' name='a4" + xprestamo.getNumeroCuota()
                                        + "' value='" + CurrencyConverter.formatToMoneyUS(xprestamo.getMontoTotal().doubleValue(), 2).replace(",", "") + "' id='a4" + xprestamo.getNumeroCuota() + "'></td>");
                                out.print("<td align='center'>"
                                        + "<input type='checkbox' name='aa5" + xprestamo.getNumeroCuota() + "' " + (estado ? "disabled" : "") + " id='aa5" + xprestamo.getNumeroCuota()
                                        + "' onclick='evalTF(this);' value='" + xprestamo.getNumeroCuota().toString() + "'>Cobrar</div>"
                                        + "<div id='du" + xprestamo.getNumeroCuota() + "' style='display:none;'><a onclick='ha(\"ad" + xprestamo.getNumeroCuota() + "\");'>Descontar</a>"
                                        + "<input type='text' id='ad" + xprestamo.getNumeroCuota() + "' style='display:none;' size='10' value='0.00' onkeyup='aM(\"" + xprestamo.getNumeroCuota() + "\");' onkeypress='return(currencyFormat(this,\",\",\".\",event));'>"
                                        + " &nbsp;<a onclick='ha(\"au" + xprestamo.getNumeroCuota() + "\");'>Recargar</a>"
                                        + "<input type='text' id='au" + xprestamo.getNumeroCuota() + "' style='display:none;' size='10' value='0.00' onkeyup='aM(\"" + xprestamo.getNumeroCuota() + "\");' onkeypress='return(currencyFormat(this,\",\",\".\",event));'></div>"
                                        + "</td></tr>");
                                estado = true;
                            } else {
                                out.print("<td align='center'>" + xprestamo.getFechaPagoOp() + "</td>"
                                        + "<td><div align='right'>" + moneda + " " + CurrencyConverter.formatToMoneyUS(xprestamo.getMontoCapital().doubleValue(), 2) + "</div></td>"
                                        + "<td><div align='center'>" + moneda + " " + CurrencyConverter.formatToMoneyUS(xprestamo.getInteresPrestamo().doubleValue() - xprestamo.getMontoCapital().doubleValue(), 2) + "</div></td>"
                                        + "<td><div align='center'>" + moneda + " " + CurrencyConverter.formatToMoneyUS(xprestamo.getInteresMoratorio().doubleValue() + xprestamo.getComision().doubleValue(), 2) + "</div></td>"
                                        + "<td><div align='right'>" + moneda + " " + CurrencyConverter.formatToMoneyUS(xprestamo.getMontoTotal().doubleValue(), 2) + "</div></td>");
                                out.print("<td>PAGADO</td>"
                                        + "</tr>");
                            }
                        }
                        out.print("<tr><td colspan='3'>&nbsp;</td>"
                                + "<td colspan='2'><span class='Estilo3'>Total a cobrar</span></td>"
                                + "<td colspan='2'><input type='hidden' id='mi' value='" + moneda + "'>" + moneda
                                + "<input type='text' name='z' id='z' value='0.00'></td>"
                                + "</tr></table></fieldset>"
                                + "<p style='text-align:center;'><input type='button' id='grabarC' name='grabarC' value='COBRAR' onclick='validarc();'>"
                                + "</p>");
                    }
                    out.print("<div></td>"
                            + "</tr></table>"
                            + "</td>"
                            + "</tr>"
                            + "</tbody>"
                            + "</table>"
                            + "</fieldset>");
                } else {
                    out.println("<center><input type='hidden' id='ix' value='ERROR'>");
                    out.println("<p style='color:red;font-size: 15px'>ERROR, EL CRÉDITO NO EXISTE.</p>");
                    out.println("</center>");
                }
            } else if ("g".equals(o)) {
                boolean r = false;
                String a = "", b = "", c = "", f = "", t = "", z = "";
                int g = 0;
                try {
                    a = (String) request.getParameter("a");
                    b = (String) request.getParameter("b");
                    f = (String) request.getParameter("f");
                    c = (String) request.getParameter("c");
                    t = (String) request.getParameter("t");
                    z = (String) request.getParameter("z");
                    BigDecimal ba = new BigDecimal(z);
                    g = Integer.parseInt(f);
                    TRegistroPrestamo rx = (TRegistroPrestamo) dao.load(TRegistroPrestamo.class, t);
                    if (g == 1) {
                        String[] dc = c.split(";");
                        BigDecimal bc = new BigDecimal(dc[1]);
                        BigDecimal p = BigDecimal.ZERO;
                        //Cantidato a reprogramacion cuando la próxima fecha de pago es mayor en tiempo al período de pago
                        List la = dao.findAll("from TDetallePrestamo where idprestamo='" + t + "' and numeroCuota='" + dc[0] + "'");
                        TDetallePrestamo dx;
                        if (la.isEmpty()) {
                            out.print("<h2>No se ha Podido procesar la acción</h2>");
                            out.close();
                            return;
                        } else {
                            dx = (TDetallePrestamo) la.iterator().next();
                            /**
                             * BigDecimal.compareTo(BigDecimal val)
                             * result 1 si BD > val
                             * 0 si BD = val
                             * -1 si BD < val
                             */
                            dx.setEstadoPago(1);
                            if (bc.compareTo(dx.getCuota()) >= 0) {
                                dx.setFechaPago(DateUtil.getNOW_S());
                                dx.setTotalPago(bc);
                                dx.setSeguroPagado(bc.subtract(dx.getSeguro()));
                                dx.setCargosPagado(bc.subtract(dx.getCargos()));
                                dx.setMoraPagada(bc.subtract(dx.getMora()));
                                dx.setInteresPagado(bc.subtract(dx.getInteresEfectivo()));
                                dx.setCapitalPagado(bc.subtract(dx.getMontoCapital()));
                                dx.setDescuento(bc);
                            } else {
                                //Posible cancelación parcial
                                dx.setFechaPago(DateUtil.getNOW_S());
                                dx.setTotalPago(bc);
                                dx.setSeguroPagado(bc.subtract(dx.getSeguro()));
                                dx.setCargosPagado(bc.subtract(dx.getCargos()));
                                dx.setMoraPagada(bc.subtract(dx.getMora()));
                                dx.setInteresPagado(bc.subtract(dx.getInteresEfectivo()));
                                dx.setCapitalPagado(bc.subtract(dx.getMontoCapital()));
                                dx.setDescuento(bc);
                                dx.setEstadoPago(0);
                            }
                            dao.update();
                        }
                        if ("ro".equals(b)) {//Evaluar La candidatura a la reprogramación (si cumple condición)
                        } else { //reprogramar automáticamente (si cumple condición...) por confirmación
                            //TDetallePrestamo ad = (TDetallePrestamo) dao.load(TDetallePrestamo.class, z);
                        }
                    } else if (g > 1) {
                        String[] dec = c.split("q");

                        if (dec.length > 1) {
                            //Cantidato a reprogramacion
                            if ("ra".equals(b)) {
                                //reprogramar automáticamente
                                //Set rxa = rx.getTDetallePrestamos();
                            } else {
                                //Evaluar La candidatura a la reprogramación
                            }
                        }
                        for (int i = 0; i < dec.length; i++) {
                            String s = dec[i];
                            String[] tx = s.split(";");
                            int ix = tx.length;
                            //String iddetalleprestamo, TRegistroPrestamo TRegistroPrestamo, BigDecimal numeroCuota,
                            //BigDecimal montoCapital, BigDecimal montoTotal, String fechaPago, BigDecimal interesPrestamo,
                            //BigDecimal interesMoratorio, String idUser, String ipUser, String dateUser,
                            //BigDecimal capitalPendiente
                            //TDetallePrestamo d = new TDetallePrestamo(idForInitNewestTable, rx, new BigDecimal(tx[0]),
                            //        new BigDecimal(tx[1]), new BigDecimal(tx[1]), "yyyy/MM/dd", new BigDecimal(0),
                            //        new BigDecimal(0), "iu", "ip", "du", new BigDecimal(0));
                            //BeanUtil fX = new BeanUtil(d);
                            //System.out.println("fX = " + fX.toString());
                        }
                    } else {
                        out.print("ERROR");
                    }
                } catch (NumberFormatException ex) {
                    r = false;
                } catch (Exception x) {
                    r = false;
                }
                /*if (r) {
                TPersona ap = (TPersona) dao.load(TPersona.class, z);
                if (ap == null) {
                out.print("NO SE HA SELECCIONADO UNA PERSONA");
                }
                TTipoCuenta ac = (TTipoCuenta) dao.load(TTipoCuenta.class, "CCO");
                TMoneda am = (TMoneda) dao.load(TMoneda.class, g);
                if (am == null) {
                out.print("Error al seleccionar el tipo de moneda");
                }
                String numCuenta = "51";
                String monedas = (String) dao.findAll("select money.codParaNumCuenta from TMoneda money where money.codMoneda ='" + g + "'").get(0);
                boolean ok = false;
                while (!ok) {
                List al = dao.findAll("select t.numCta from TCuentaPersona t where t.estado='PRESTAMO' and t.numCta like '%-" + idForInitNewestTable.substring(0, 4) + "' order by t.numCta desc");
                String codigox = "00001-" + idForInitNewestTable.substring(0, 4);
                if (!al.isEmpty()) {
                String ag = (String) al.get(0);
                int ai = Integer.parseInt(ag.substring(7, 12));
                ai++;
                codigox = "00000".substring(String.valueOf(ai).length()) + String.valueOf(ai) + "-" + idForInitNewestTable.substring(0, 4);
                }
                if (fi.length() > 3) {
                fi = fi.substring(fi.length() - 3);
                }
                numCuenta += fi;
                numCuenta += monedas;
                numCuenta += "3";
                numCuenta += codigox;
                List l = dao.findAll("select t.numCta from TCuentaPersona t where t.numCta ='" + numCuenta + "'");
                if (l.isEmpty() || l.size() < 1) {
                ok = true;
                } else {
                ok = false;
                }
                }
                //public TCuentaPersona(String idcuentapersona, TTipoCuenta TTipoCuenta, TPersona TPersona, TMoneda TMoneda, String numCta, String fecha, String estado, String ipUser, String idUser, String dateUser)
                TCuentaPersona ab = new TCuentaPersona(idForInitNewestTable, ac, ap, am, numCuenta, DateUtil.getNOW_S(), "PRESTAMO", myId, myIp, DateUtil.getNOW_S());
                ab.setObservaciones("");
                ab.setInteres(BigDecimal.ZERO);
                ab.setInteres(BigDecimal.ZERO);
                ab.setSaldo(BigDecimal.ZERO);
                ab.setSaldoSinInteres(BigDecimal.ZERO);
                ab.setFechaActualizacion(DateUtil.getNOW_S());
                ab.setFechaCap(DateUtil.getDate(new Date()));
                dao.persist(ab);
                TTipoCredito at = (TTipoCredito) dao.load(TTipoCredito.class, a);
                TTipoOperacion ar = (TTipoOperacion) dao.load(TTipoOperacion.class, "TIPC6");
                TPersonaCaja ad = (TPersonaCaja) dao.load(TPersonaCaja.class, myPC);
                //public TOperacion(String idOperacion, TTipoOperacion TTipoOperacion, TPersonaCaja TPersonaCaja, TMoneda TMoneda, String numeroOperacion, String fecha, String estado, String idUser, String ipUser, String dateUser)
                TOperacion ao = new TOperacion(idForInitNewestTable, ar, ad, am, numeroOperacion.getNumber(fi, myCaja), DateUtil.getNOW_S(), "ACTIVO", myId, myIp, DateUtil.getNOW_S());
                ao.setDescripcion("REGISTRO PRESTAMO");
                ao.setFd(new Date());
                ao.setSaldoFinalSinInteres(BigDecimal.ZERO);
                ao.setSaldofinal(BigDecimal.ZERO);
                dao.persist(ao);
                //TRegistroPrestamo(String idprestamo, TCuentaPersona TCuentaPersona, TTipoCredito TTipoCredito, TOperacion TOperacion, BigDecimal monto, String fecha, int numeroCuotas, String idUser, String ipUser, String dateUser, String estado, BigDecimal tasaInteresPrestamo, int numeroReprogramacion)
                TRegistroPrestamo ax = new TRegistroPrestamo(idForInitNewestTable, ab, at, ao, new BigDecimal(imp), DateUtil.getNOW_S(), nc, myId, myIp, DateUtil.getNOW_S(), "ACTIVO", new BigDecimal(intas), 0);
                ax.setTea(i);
                ax.setSegDesg("0.00");
                ax.setPeriodo("" + dec);
                ax.setTasaInteresMoratorio(at.getTasaInteresMoratoria());
                ax.setItSuspenso(BigDecimal.ZERO);
                ax.setFechaAprobacion(DateUtil.getDate(fa));
                ax.setNumeroRefinanciacion(0);
                ax.setTipoCuota(0);
                ax.setDiaInicialPp(dec);
                ax.setDiasEntreCuotas(dec);
                ax.setAplicaSeguroDesgravamen(0);
                ax.setEstadoSolicitud(0);
                ax.setFechaDesembolso(DateUtil.getDate(fa));
                ax.setModoCalculos(e.equals("ff") ? 2 : 1);
                ax.setCargos(BigDecimal.ZERO);
                ax.setMontoInteresComp(BigDecimal.ZERO);
                ax.setFechaJudicial("");
                ax.setSituacionCredito(1);
                ax.setFechaSituacion(DateUtil.getNOW_S());
                ax.setFechaDembolsoAutorizacion("");
                dao.persist(ax);
                }
                List xa = dao.findAll("from TRegistroPrestamo where estadoSolicitud=0");
                if (!xa.isEmpty()) {
                out.print("<fieldset style='border-width:3px'>"
                + "<legend><b>Préstamos Guardados</b></legend>"
                + "<table>"
                + "<tr><td>"
                + "N°"
                + "</td><td>"
                + "Nombre"
                + "</td><td>"
                + "Moneda y Monto"
                + "</td><td>"
                + "Fecha Solicitud"
                + "</td><td>"
                + "</td><td>"
                + "</td>"
                + "</tr>");
                for (Iterator it = xa.iterator(); it.hasNext();) {
                TRegistroPrestamo at = (TRegistroPrestamo) it.next();
                out.print("<tr><td>"
                + at.getTCuentaPersona().getNumCta().substring(7, 12)
                + "</td><td>"
                + at.getTCuentaPersona().getTPersona().getNombre() + " " + at.getTCuentaPersona().getTPersona().getApellidos()
                + "</td><td>"
                + at.getTCuentaPersona().getTMoneda().getSimbolo() + " " + at.getMonto()
                + "</td><td>"
                + at.getFecha()
                + "</td><td>"
                + "<input type='button' name='des" + at.getIdprestamo() + "' onclick='despacho(\"" + at.getIdprestamo() + "\");' value='Desembolsar' />"
                + "</td><td>"
                + "<input type='button' name='eli" + at.getIdprestamo() + "' onclick='borrar(\"" + at.getIdprestamo() + "\")' value='Eliminar' />"
                + "</td></tr>");
                }
                out.print(""
                + "</table></fieldset>");
                }*/
                out.print("OK");
            } else if ("e".equals(o)) {
                out.print("OK");
            } else if ("d".equals(o)) {
                out.print("OK");
            } else if ("r0".equals(o)) {
                String a = (String) request.getParameter("a");
                TRegistroPrestamo c = (TRegistroPrestamo) dao.load(TRegistroPrestamo.class, a);
                if (c != null) {
                    String moneda = "";
                    moneda = c.getTCuentaPersona().getTMoneda().getSimbolo() + " ";
                    List dx = dao.findAll("from TDetallePrestamo where TRegistroPrestamo.idprestamo='" + c.getIdprestamo() + "' and estado='ACTIVO' order by numeroCuota");
                    boolean estado = false;
                    out.print("<fieldset style='border-width:2px'>");
                    if (!dx.isEmpty()) {
                        out.print("<table width='800' border='1'><tr>"
                                + "<td colspan='7'><div align='center'><strong>CRONOGRAMA DE PAGOS</strong></div></td>"
                                + "</tr><tr>"
                                + "<td width='50'><div align='center'><strong>Numero de Cuota </strong></div></td>"
                                + "<td width='100'><div align='center'><strong>Fecha Vencto. </strong></div></td>"
                                + "<td width='120'><div align='center'><strong>Capital</strong></div></td>"
                                + "<td width='120'><div align='center'><strong>Inter&eacute;s</strong></div></td>"
                                + "<td width='120'><div align='center'><strong>Mora/Cargos</strong></div></td>"
                                + "<td width='120'><div align='center'><strong>Monto Total </strong></div></td>"
                                + "<td width='170'><div align='center'><strong>PAGAR</strong></div></td>"
                                + "</tr>");
                        for (Iterator it = dx.iterator(); it.hasNext();) {
                            TDetallePrestamo xprestamo = (TDetallePrestamo) it.next();
                            out.print("<tr><td><div align='center'>" + xprestamo.getNumeroCuota().toString() + "<input type='hidden' value='" + xprestamo.getIddetalleprestamo() + "' id='_" + xprestamo.getNumeroCuota() + "'></div></td>");
                            double hoy = DateUtil.today();
                            double tim = xprestamo.getTRegistroPrestamo().getTasaInteresMoratorio().doubleValue();
                            long fechap = DateUtil.convertStringToDate("dd/MM/yyyy", xprestamo.getFechaPagoOp()).getTime();
                            int dc = DateUtil.difDiasFullS(xprestamo.getFechaPagoOp(), DateUtil.getDate(new Date()), "dd/MM/yyyy", "yyyy/MM/dd");
                            if (xprestamo.getEstadoPago() == 0) {
                                if (fechap <= (new Date()).getTime()) {
                                    xprestamo.setInteresMoratorio(BigDecimal.valueOf(PrestamoUtil.calculoMora(dc, hoy, xprestamo.getMontoCapital().doubleValue(), tim)));
                                } else {
                                    xprestamo.setInteresMoratorio(BigDecimal.ZERO);
                                    dc = 0;
                                }
                                xprestamo.setMontoTotal(xprestamo.getMontoCapital().add(xprestamo.getInteresEfectivo()).add(xprestamo.getInteresMoratorio()));
                                dao.update();
                                out.print("<td align='center'>" + xprestamo.getFechaPagoOp() + "</td>"
                                        + "<td align='center'><div align='right'>" + (moneda + " " + CurrencyConverter.formatToMoneyUS(xprestamo.getMontoCapital().doubleValue(), 2)) + "</div>"
                                        + " <input type='hidden' name='a1" + xprestamo.getNumeroCuota() + "' value='" + xprestamo.getMontoCapital().doubleValue() + "' id='a1" + xprestamo.getNumeroCuota() + "'></td>"
                                        + "<td align='center'><div align='center'>" + moneda + " " + CurrencyConverter.formatToMoneyUS(xprestamo.getInteresEfectivo().doubleValue(), 2) + "</div>"
                                        + " <input type='hidden' name='a2" + xprestamo.getNumeroCuota() + "' value='" + xprestamo.getInteresEfectivo().doubleValue() + "' id='a2" + xprestamo.getNumeroCuota() + "'></td>"
                                        + "<td align='center'><div align='center' id='di4" + xprestamo.getNumeroCuota() + "'>" + (moneda + " " + CurrencyConverter.formatToMoneyUS(xprestamo.getInteresMoratorio().doubleValue() + xprestamo.getComision().doubleValue(), 2)) + (dc == 0 ? "" : " (Mora=" + dc + "d)") + "</div>"
                                        + "<input type='hidden' name='a3" + xprestamo.getNumeroCuota() + "' value='" + CurrencyConverter.formatToMoneyUS(xprestamo.getInteresMoratorio().doubleValue() + xprestamo.getComision().doubleValue(), 2).replace(",", "")
                                        + "' id='a3" + xprestamo.getNumeroCuota() + "'></td>"
                                        + "<td align='center'><div align='right' id='dv4" + xprestamo.getNumeroCuota() + "'>" + (moneda + " " + CurrencyConverter.formatToMoneyUS(xprestamo.getMontoTotal().doubleValue(), 2)) + "</div>"
                                        + "<input type='hidden' name='a4" + xprestamo.getNumeroCuota()
                                        + "' value='" + CurrencyConverter.formatToMoneyUS(xprestamo.getMontoTotal().doubleValue(), 2).replace(",", "") + "' id='a4" + xprestamo.getNumeroCuota() + "'></td>");
                                out.print("<td align='center'>"
                                        + "<input type='checkbox' name='aa5" + xprestamo.getNumeroCuota() + "' " + (estado ? "disabled" : "") + " id='aa5" + xprestamo.getNumeroCuota()
                                        + "' onclick='evalTF(this);' value='" + xprestamo.getNumeroCuota().toString() + "'>Cobrar</div>"
                                        + "<div id='du" + xprestamo.getNumeroCuota() + "' style='display:none;'><a onclick='ha(\"ad" + xprestamo.getNumeroCuota() + "\");'>Descontar</a>"
                                        + "<input type='text' id='ad" + xprestamo.getNumeroCuota() + "' style='display:none;' size='10' value='0.00' onkeyup='aM(\"" + xprestamo.getNumeroCuota() + "\");' onkeypress='return(currencyFormat(this,\",\",\".\",event));'>"
                                        + " &nbsp;<a onclick='ha(\"au" + xprestamo.getNumeroCuota() + "\");'>Recargar</a>"
                                        + "<input type='text' id='au" + xprestamo.getNumeroCuota() + "' style='display:none;' size='10' value='0.00' onkeyup='aM(\"" + xprestamo.getNumeroCuota() + "\");' onkeypress='return(currencyFormat(this,\",\",\".\",event));'></div>"
                                        + "</td></tr>");
                                estado = true;
                            } else {
                                out.print("<td align='center'>" + xprestamo.getFechaPagoOp() + "</td>"
                                        + "<td><div align='right'>" + moneda + " " + CurrencyConverter.formatToMoneyUS(xprestamo.getMontoCapital().doubleValue(), 2) + "</div></td>"
                                        + "<td><div align='center'>" + moneda + " " + CurrencyConverter.formatToMoneyUS(xprestamo.getInteresPrestamo().doubleValue() - xprestamo.getMontoCapital().doubleValue(), 2) + "</div></td>"
                                        + "<td><div align='center'>" + moneda + " " + CurrencyConverter.formatToMoneyUS(xprestamo.getInteresMoratorio().doubleValue() + xprestamo.getComision().doubleValue(), 2) + "</div></td>"
                                        + "<td><div align='right'>" + moneda + " " + CurrencyConverter.formatToMoneyUS(xprestamo.getMontoTotal().doubleValue(), 2) + "</div></td>");
                                out.print("<td>PAGADO</td>"
                                        + "</tr>");
                            }
                        }
                        out.print("<tr><td colspan='3'>&nbsp;</td>"
                                + "<td colspan='2'><span class='Estilo3'>Total a cobrar</span></td>"
                                + "<td colspan='2'><input type='hidden' id='mi' value='" + moneda + "'>" + moneda
                                + "<input type='text' name='z' id='z' value='0.00'></td>"
                                + "</tr></table></fieldset>"
                                + "<p style='text-align:center;'><input type='button' id='grabarC' name='grabarC' value='COBRAR' onclick='validarc();'>"
                                + "</p>");
                    }
                } else {
                    out.println("<center><input type='hidden' id='ix' value='ERROR'>");
                    out.println("<p style='color:red;font-size: 15px'>ERROR AL CARGAR LOS CAMPOS.</p>");
                    out.println("</center>");
                }
            } else if ("r1".equals(o)) {
                String a = (String) request.getParameter("a");
                TRegistroPrestamo c = (TRegistroPrestamo) dao.load(TRegistroPrestamo.class, a);
                if (c != null) {
                    String moneda = "";
                    moneda = c.getTCuentaPersona().getTMoneda().getSimbolo() + " ";
                    List dx = dao.findAll("from TDetallePrestamo where TRegistroPrestamo.idprestamo='" + c.getIdprestamo() + "' and estado='ACTIVO' order by numeroCuota");
                    boolean estado = false;
                    out.print("<fieldset style='border-width:2px'>");
                    if (!dx.isEmpty()) {
                        out.print("<table width='800' border='1'><tr>"
                                + "<td colspan='7'><div align='center'><strong>CRONOGRAMA DE PAGOS</strong></div></td>"
                                + "</tr><tr>"
                                + "<td width='50'><div align='center'><strong>Numero de Cuota </strong></div></td>"
                                + "<td width='100'><div align='center'><strong>Fecha Vencto. </strong></div></td>"
                                + "<td width='120'><div align='center'><strong>Capital</strong></div></td>"
                                + "<td width='120'><div align='center'><strong>Inter&eacute;s</strong></div></td>"
                                + "<td width='120'><div align='center'><strong>Mora/Cargos</strong></div></td>"
                                + "<td width='120'><div align='center'><strong>Monto Total </strong></div></td>"
                                + "<td width='170'><div align='center'><strong>PAGAR</strong></div></td>"
                                + "</tr>");
                        Date fAnt = DateUtil.convertStringToDate("yyyy/MM/ddd", c.getFechaDesembolso());
                        for (Iterator it = dx.iterator(); it.hasNext();) {
                            TDetallePrestamo xprestamo = (TDetallePrestamo) it.next();
                            out.print("<tr><td><div align='center'>" + xprestamo.getNumeroCuota().toString() + "<input type='hidden' value='" + xprestamo.getIddetalleprestamo() + "' id='_" + xprestamo.getNumeroCuota() + "'></div></td>");
                            double hoy = DateUtil.today();
                            double tim = xprestamo.getTRegistroPrestamo().getTasaInteresMoratorio().doubleValue();
                            Date fechap = DateUtil.convertStringToDate("dd/MM/yyyy", xprestamo.getFechaPagoOp());
                            int dc = DateUtil.difDiasFullS(xprestamo.getFechaPagoOp(), DateUtil.getDate(new Date()), "dd/MM/yyyy", "yyyy/MM/dd");
                            int periodo = DateUtil.daysBetween(fAnt, fechap);
                            int momentum=periodo-DateUtil.daysBetween(fAnt, new Date());
                            fAnt = fechap;
                            if (xprestamo.getEstadoPago() == 0) {
                                if (fechap.getTime() < (new Date()).getTime()) {
                                    xprestamo.setMora(BigDecimal.valueOf(PrestamoUtil.calculoMora(dc, hoy, xprestamo.getMontoCapital().doubleValue(), tim)));
                                } else {
                                    xprestamo.setMora(BigDecimal.ZERO);
                                    dc = 0;
                                }
                                xprestamo.setMontoTotal(xprestamo.getMontoCapital().add(xprestamo.getInteresEfectivo()).add(xprestamo.getMora()));
                                dao.update();
                                out.print("<td align='center'>" + xprestamo.getFechaPagoOp() + "</td>"
                                        + "<td align='center'><div align='right'>" + (moneda + " " + CurrencyConverter.formatToMoneyUS(xprestamo.getMontoCapital().doubleValue(), 2)) + "</div>"
                                        + " <input type='hidden' name='a1" + xprestamo.getNumeroCuota() + "' value='" + xprestamo.getMontoCapital().doubleValue() + "' id='a1" + xprestamo.getNumeroCuota() + "'></td>"
                                        + "<td align='center'><div align='center'>" + moneda + " " + CurrencyConverter.formatToMoneyUS(xprestamo.getInteresEfectivo().doubleValue(), 2) + "</div>"
                                        + " <input type='hidden' name='a2" + xprestamo.getNumeroCuota() + "' value='" + xprestamo.getInteresEfectivo().doubleValue() + "' id='a2" + xprestamo.getNumeroCuota() + "'></td>"
                                        + "<td align='center'><div align='center' id='di4" + xprestamo.getNumeroCuota() + "'>" + (moneda + " " + CurrencyConverter.formatToMoneyUS(xprestamo.getMora().doubleValue() + xprestamo.getComision().doubleValue(), 2)) + (dc == 0 ? "" : " (Mora=" + dc + "d)") + "</div>"
                                        + "<input type='hidden' name='a3" + xprestamo.getNumeroCuota() + "' value='" + CurrencyConverter.formatToMoneyUS(xprestamo.getMora().doubleValue() + xprestamo.getComision().doubleValue(), 2).replace(",", "")
                                        + "' id='a3" + xprestamo.getNumeroCuota() + "'></td>"
                                        + "<td align='center'><div align='right' id='dv4" + xprestamo.getNumeroCuota() + "'>" + (moneda + " " + CurrencyConverter.formatToMoneyUS(xprestamo.getMontoTotal().doubleValue(), 2)) + "</div>"
                                        + "<input type='hidden' name='a4" + xprestamo.getNumeroCuota()
                                        + "' value='" + CurrencyConverter.formatToMoneyUS(xprestamo.getMontoTotal().doubleValue(), 2).replace(",", "") + "' id='a4" + xprestamo.getNumeroCuota() + "'></td>");
                                out.print("<td align='center'>"
                                        + "<input type='checkbox' name='aa5" + xprestamo.getNumeroCuota() + "' " + (estado ? "disabled" : "") + " id='aa5" + xprestamo.getNumeroCuota()
                                        + "' onclick='evalTF(this);' value='" + xprestamo.getNumeroCuota().toString() + "'>Cobrar</div>"
                                        + "<div id='du" + xprestamo.getNumeroCuota() + "' style='display:none;'><a onclick='ha(\"ad" + xprestamo.getNumeroCuota() + "\");'>Descontar</a>"
                                        + "<input type='text' id='ad" + xprestamo.getNumeroCuota() + "' style='display:none;' size='10' value='0.00' onkeyup='aM(\"" + xprestamo.getNumeroCuota() + "\");' onkeypress='return(currencyFormat(this,\",\",\".\",event));'>"
                                        + " &nbsp;<a onclick='ha(\"au" + xprestamo.getNumeroCuota() + "\");'>Recargar</a>"
                                        + "<input type='text' id='au" + xprestamo.getNumeroCuota() + "' style='display:none;' size='10' value='0.00' onkeyup='aM(\"" + xprestamo.getNumeroCuota() + "\");' onkeypress='return(currencyFormat(this,\",\",\".\",event));'></div>"
                                        + "</td></tr>");
                                estado = true;
                            } else {
                                out.print("<td align='center'>" + xprestamo.getFechaPago() + "</td>"
                                        + "<td><div align='right'>" + moneda + " " + CurrencyConverter.formatToMoneyUS(xprestamo.getCapitalPagado().doubleValue(), 2) + "</div></td>"
                                        + "<td><div align='center'>" + moneda + " " + CurrencyConverter.formatToMoneyUS(xprestamo.getInteresPagado().doubleValue(), 2) + "</div></td>"
                                        + "<td><div align='center'>" + moneda + " " + CurrencyConverter.formatToMoneyUS(xprestamo.getMoraPagada().doubleValue() + xprestamo.getComision().doubleValue(), 2) + "</div></td>"
                                        + "<td><div align='right'>" + moneda + " " + CurrencyConverter.formatToMoneyUS(xprestamo.getTotalPago().doubleValue(), 2) + "</div></td>");
                                out.print("<td>PAGADO</td>"
                                        + "</tr>");
                            }
                        }
                        out.print("<tr><td colspan='3'>&nbsp;</td>"
                                + "<td colspan='2'><span class='Estilo3'>Total a cobrar</span></td>"
                                + "<td colspan='2'><input type='hidden' id='mi' value='" + moneda + "'>" + moneda
                                + "<input type='text' name='z' id='z' readonly value='0.00'></td>"
                                + "</tr></table></fieldset>"
                                + "<p style='text-align:center;'><input type='button' id='grabarC' name='grabarC' value='COBRAR' onclick='validarc();'>"
                                + "</p>");
                    }
                } else {
                    out.println("<center><input type='hidden' id='ix' value='ERROR'>");
                    out.println("<p style='color:red;font-size: 15px'>ERROR AL CARGAR LOS CAMPOS.</p>");
                    out.println("</center>");
                }
            }
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);


    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);


    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";

    }// </editor-fold>
}
