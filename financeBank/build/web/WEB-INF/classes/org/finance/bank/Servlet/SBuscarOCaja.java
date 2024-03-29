package org.finance.bank.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.finance.bank.bean.TCobranza;
import org.finance.bank.bean.TDetalleCaja;
import org.finance.bank.bean.TLogFinance;
import org.finance.bank.bean.TMoneda;
import org.finance.bank.bean.TOperacion;
import org.finance.bank.bean.TPersona;
import org.finance.bank.bean.TRegistroCompraVenta;
import org.finance.bank.bean.TRegistroDepositoRetiro;
import org.finance.bank.bean.TRegistroOtros;
import org.finance.bank.bean.TRegistroPrestamo;
import org.finance.bank.bean.TTransferenciaCaja;
import org.finance.bank.model.dao.DAOGeneral;
import org.finance.bank.util.CurrencyConverter;
import org.finance.bank.util.DateUtil;

/**
 *
 * @author admin
 */
public class SBuscarOCaja extends HttpServlet {

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
        HttpSession session = request.getSession(true);
        DAOGeneral dao = new DAOGeneral();
        try {
            String hql = "";
            String cod_caja = (String) session.getAttribute("USER_CODCAJA");
            hql = "from TOperacion op where (op.estado='ACTIVO' OR op.estado='EXTORNADO') AND op.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' AND op.TPersonaCaja.TCaja.codCaja='" + cod_caja + "' order by op.fecha desc";
            String nombres = (String) session.getAttribute("USER_NAME");
            String codCaja = (String) session.getAttribute("USER_CODCAJA");
            String filial = (String) session.getAttribute("USER_FILIAL");
            filial = filial.replace("FILIAL", "OFICINA");
            DateFormat idfecha3 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            out.println("<table>");
            out.println("<tr>");
            out.println("<td>" + filial + "</td>");
            out.println("<td>CAJA:" + codCaja + "</td>");
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
            List lm = dao.findAll("from TMoneda where estado='ACTIVO' order by codMoneda");
            Vector iniciados = new Vector();
            Vector recibidos = new Vector();
            Vector entregados = new Vector();
            TDetalleCaja tdc;
            TRegistroOtros tro;
            TOperacion to;
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
                List result = dao.findAll(hql);
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
                                                Logger.getLogger(SBuscarOCaja.class.getName()).log(Level.INFO, "R1montNow = " + montNow + " " + to.getNumeroOperacion());
                                            }
                                            interchanger.add(montNow);
                                        }
                                        recibidos = interchanger;
                                        Iterator iteraMoney2 = data.iterator();
                                        Iterator iteraMontR = entregados.iterator();
                                        TMoneda tMonedaP = (TMoneda) dao.load(TMoneda.class, "PEN");
                                        String moR = tMonedaP.getCodMoneda();
                                        while (iteraMoney2.hasNext() && iteraMontR.hasNext()) {
                                            TMoneda mo = (TMoneda) iteraMoney2.next();
                                            Double montNow = (Double) iteraMontR.next();
                                            if (moR.equals(mo.getCodMoneda())) {
                                                montNow += mont.doubleValue();
                                                Logger.getLogger(SBuscarOCaja.class.getName()).log(Level.INFO, "E1montNow = " + montNow + " " + to.getNumeroOperacion());
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
                                                Logger.getLogger(SBuscarOCaja.class.getName()).log(Level.INFO, "E2montNow = " + montNow + " " + to.getNumeroOperacion());
                                            }
                                            interchanger.add(montNow);
                                        }
                                        entregados = interchanger;
                                        Iterator iteraMoney2 = data.iterator();
                                        Iterator iteraMontR = recibidos.iterator();
                                        TMoneda tMonedaP = (TMoneda) dao.load(TMoneda.class, "PEN");
                                        String moR = tMonedaP.getCodMoneda();
                                        while (iteraMoney2.hasNext() && iteraMontR.hasNext()) {
                                            TMoneda mo = (TMoneda) iteraMoney2.next();
                                            Double montNow = (Double) iteraMontR.next();
                                            if (moR.equals(mo.getCodMoneda())) {
                                                montNow += montR.doubleValue();
                                                Logger.getLogger(SBuscarOCaja.class.getName()).log(Level.INFO, "R2montNow = " + montNow + " " + to.getNumeroOperacion());
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
                                                Logger.getLogger(SBuscarOCaja.class.getName()).log(Level.INFO, "R3montNow = " + montNow + " " + to.getNumeroOperacion());
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
                                                Logger.getLogger(SBuscarOCaja.class.getName()).log(Level.INFO, "E4montNow = " + montNow + " " + to.getNumeroOperacion());
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
//                                                Logger.getLogger(SBuscarOCaja.class.getName()).log(Level.INFO, "R5montNow = " + montNow + " " + to.getNumeroOperacion());
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
                            Logger.getLogger(SBuscarOCaja.class.getName()).log(Level.INFO, "nombreO = " + to.getIdOperacion());
                            Set xTRP = to.getTRegistroPrestamos();
                            int s = xTRP.size();
                            Logger.getLogger(SBuscarOCaja.class.getName()).log(Level.INFO, "s = " + s);
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
                                                Logger.getLogger(SBuscarOCaja.class.getName()).log(Level.INFO, "E6montNow = " + montNow + " " + to.getNumeroOperacion());
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
                                                Logger.getLogger(SBuscarOCaja.class.getName()).log(Level.INFO, "R7montNow = " + montNow + " " + to.getNumeroOperacion());
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
//                                        Logger.getLogger(SBuscarOCaja.class.getName()).log(Level.INFO, "E8montNow = " + montNow + " " + to.getNumeroOperacion());
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
                                                Logger.getLogger(SBuscarOCaja.class.getName()).log(Level.INFO, "E9montNow = " + montNow + " " + to.getNumeroOperacion());
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
                                                Logger.getLogger(SBuscarOCaja.class.getName()).log(Level.INFO, "E10montNow = " + montNow + " " + to.getNumeroOperacion());
                                            }
                                            interchanger.add(montNow);
                                        }
                                        entregados = interchanger;
                                    }
                                }
                            }
                        }
                        String par = "";
                        i = i + 1;
                        if (i % 2 == 0) {
                            par = "modo2";
                        } else {
                            par = "modo1";
                        }
                        BigDecimal Monto = BigDecimal.ZERO;
                        if (nombreO.equals("COMPRA")) {
                            TRegistroCompraVenta ComVent = (TRegistroCompraVenta) dao.findAll("from TRegistroCompraVenta where TOperacion='" + to.getIdOperacion() + "'").get(0);
                            Monto = ComVent.getMontoRecibido();
                        } else if (nombreO.equals("VENTA")) {
                            TRegistroCompraVenta ComVent = (TRegistroCompraVenta) dao.findAll("from TRegistroCompraVenta where TOperacion='" + to.getIdOperacion() + "'").get(0);
                            Monto = ComVent.getMontoEntregado();
                        } else if (nombreO.equals("DEPOSITO") || nombreO.equals("RETIRO")) {
                            TRegistroDepositoRetiro DepRet = (TRegistroDepositoRetiro) dao.findAll("from TRegistroDepositoRetiro where TOperacion='" + to.getIdOperacion() + "'").get(0);
                            Monto = DepRet.getImporte();
                            /*} else if (nombreO.equals("GIRO")) {
                            //Logger.getLogger(SBuscarOCaja.class.getName()).log(Level.INFO, "giro = " + to.getNumeroOperacion());
                            TRegistroGiro RegGiro = (TRegistroGiro) dao.findAll("from TRegistroGiro where TOperacion='" + to.getIdOperacion() + "'").get(0);
                            Monto = RegGiro.getImporte();
                            } else if (nombreO.equals("COBROGIRO")) {
                            //Logger.getLogger(SBuscarOCaja.class.getName()).log(Level.INFO, "giro = " + to.getNumeroOperacion());
                            TRegistroGiro RegGiro = (TRegistroGiro) dao.findAll("from TRegistroGiro where idoperacioncobro='" + to.getIdOperacion() + "'").get(0);
                            Monto = RegGiro.getImporte();*/
                        } else if (nombreO.equals("PRESTAMO")) {
                            TRegistroPrestamo RegPres = (TRegistroPrestamo) dao.findAll("from TRegistroPrestamo where TOperacion='" + to.getIdOperacion() + "'").get(0);
                            Monto = RegPres.getMonto();
                        } else if (nombreO.equals("PAGO")) {
                            TCobranza Cobranza = (TCobranza) dao.findAll("from TCobranza where TOperacion='" + to.getIdOperacion() + "'").get(0);
                            Monto = Cobranza.getTDetallePrestamo().getMontoTotal();
                        } else if (nombreO.equals("RETIRO OTRO")) {
                            TRegistroOtros RegOtros = (TRegistroOtros) dao.findAll("from TRegistroOtros where TOperacion='" + to.getIdOperacion() + "'").get(0);
                            Monto = RegOtros.getMonto();
                        } else if (nombreO.equals("TRANSFERENCIA")) {
                            TTransferenciaCaja t = (TTransferenciaCaja) dao.findAll("from TTransferenciaCaja where TOperacion='" + to.getIdOperacion() + "'").get(0);
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
                            TPersona xA = (TPersona) dao.load(TPersona.class, to.getIdAdminExtorno());
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
                 String par = "modo1";
                if (i % 2 == 0) {
                            par = "modo2";
                        }
            hql = "from TTransferenciaCaja o where (o.TOperacion.estado='ACTIVO' OR o.TOperacion.estado='EXTORNADO') AND o.TOperacion.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' AND o.codCajaDestino='" + cod_caja + "' order by o.TOperacion.fecha desc";
            List resultX = dao.findAll(hql);
                int tosizeX = resultX.size();
                //String nombreO = "";
                if (tosizeX > 0) {
                Iterator itX = resultX.iterator();
                    while(itX.hasNext()){
                        TTransferenciaCaja opX=(TTransferenciaCaja) itX.next();
                        BigDecimal Monto = BigDecimal.ZERO;
                        out.println("<tr class=" + par + ">");
                        out.println("<td id='r' style='display:none'>" + opX.getTOperacion().getIdOperacion() + "</td>");
                        out.println("<td>" + opX.getTOperacion().getNumeroOperacion() + "</td>");
                        out.println("<td>" + opX.getTOperacion().getFecha() + "</td>");
                        out.println("<td>RECEP. DE TRANSF.</td>");
                        out.println("<td>" + CurrencyConverter.formatToMoneyUS(Monto.doubleValue(), 2) + "</td>");
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
                            TPersona xA = (TPersona) dao.load(TPersona.class, opX.getTOperacion().getIdAdminExtorno());
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
//                                    Logger.getLogger(SBuscarOCaja.class.getName()).log(Level.INFO, "IttmontNow = " + montNow +" "+ttc.getTOperacion().getNumeroOperacion());
//                                }
//                                interchanger.add(montNow);
//                            }
//                            iniciados = interchanger;
//                        }
//                    }
//                }
                //List liI = dao.findAll("from TDetalleCaja tc where TCaja.codCaja='" + cod_caja + "' and TMoneda.estado='ACTIVO' and estado='ACTIVO'");
                List liI=dao.findAll("from TLogFinance where idLogFinance like 'LOG"+codCaja+"___'");
                int ssI = liI.size();
                if (ssI > 0) {
                    Iterator itI = liI.iterator();
                    while (itI.hasNext()) {
                        //tdc = (TDetalleCaja) itI.next();
                        TLogFinance logtdc=(TLogFinance)itI.next();
                        Vector interchanger = new Vector();
                        String moneda = logtdc.getIdlogfinance().substring(12);
                        if (logtdc != null) {
                            BigDecimal mont = logtdc.getMontoFinal();
                            Iterator iteraMoney = data.iterator();
                            Iterator iteraMont = iniciados.iterator();
                            while (iteraMoney.hasNext() && iteraMont.hasNext()) {
                                TMoneda mo = (TMoneda) iteraMoney.next();
                                Double montNow = (Double) iteraMont.next();
                                if (moneda.equals(mo.getCodMoneda())) {
                                    montNow += mont.doubleValue();
                                    Logger.getLogger(SBuscarOCaja.class.getName()).log(Level.INFO, "IttmontNow = " + montNow + " " + logtdc.getMontoInicial());
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
                Iterator itII = iniciados.iterator();
                Iterator itE = entregados.iterator();
                Iterator itR = recibidos.iterator();
                String mm = "";
                String ii = "";
                String e = "";
                String r = "";
                String f = "";
                while (itM.hasNext()) {
                    TMoneda xmm = (TMoneda) itM.next();
                    Double xii = (Double) itII.next();
                    Double ee = (Double) itE.next();
                    Double rr = (Double) itR.next();
                    mm += "<td  width='180px'>" + xmm.getNombre() + " &nbsp;</td>";
                    ii += "<td>" + DateUtil.eedatefmt(DateUtil.fmtdate17, xii + ee - rr) + "</td>";
                    r += "<td>" + DateUtil.eedatefmt(DateUtil.fmtdate17, rr) + "</td>";
                    e += "<td>" + DateUtil.eedatefmt(DateUtil.fmtdate17, ee) + "</td>";
                    f += "<td>" + DateUtil.eedatefmt(DateUtil.fmtdate17, xii) + "</td>";
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
                out.println("<input name='Submit' id='Submit' type='button' onClick=\"document.title=''; if (window.open)window.open('ticketcuenta.htm?tipoOperacion=TODO','operacion','width=800,height=840,menubar=yes,resizable=yes,scrollbars=yes,status=yes');else alert('Su Navegador no dispone de esta opcion, Actualicelo con una version mas reciente');\" value='Imprimir'");
                out.println("<input type='hidden' id='rows' value='" + i + "'");
                out.println("</center>");
                out.println("</div>");
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
