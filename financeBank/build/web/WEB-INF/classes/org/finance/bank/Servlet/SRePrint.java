package org.finance.bank.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.finance.bank.bean.TCertificado;
import org.finance.bank.bean.TDetalleCuentaPersona;
import org.finance.bank.bean.TOperacion;
import org.finance.bank.bean.TRegistroCompraVenta;
import org.finance.bank.bean.TRegistroDepositoRetiro;
import org.finance.bank.bean.TRegistroGiro;
import org.finance.bank.bean.TTransferenciaCaja;
import org.finance.bank.model.dao.DAOGeneral;
import org.finance.bank.util.CurrencyConverter;
import org.finance.bank.util.DateUtil;
import org.finance.bank.util.formatMoneda;

/**
 *
 * @author admin
 */
public class SRePrint extends HttpServlet {

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
            Map ticket = new HashMap();
            String idOperacion = request.getParameter("id");
            if (idOperacion == null) {
                dao.cerrarSession();
                out.close();
                return;
            }
            TOperacion o = (TOperacion) dao.load(TOperacion.class, idOperacion);
            if (o == null) {
                out.print("<fieldset><legend style='font-size:9px'><b>DATOS DE LA OPERACION</b></legend>"
                        + "<table border='0' cellpadding='5' cellspacing='5' id='h1' class='tabla' width='100%'>"
                        + "<tr><td valign='top'>NO RESULTS.</td></tr></table></fieldset>");
            } else {
                int chain = (int) Integer.parseInt(o.getTTipoOperacion().getCodigoTipoOperacion().substring(4));
                switch (chain) {
                    case 1:
                        TRegistroCompraVenta compra = (TRegistroCompraVenta) o.getTRegistroCompraVentas().iterator().next();
                        ticket.put("FECHA", o.getFecha().substring(8, 10) + "/" + o.getFecha().substring(5, 8) + o.getFecha().substring(0, 4));
                        ticket.put("HORA", o.getFecha().substring(11));
                        ticket.put("RZS", o.getDescripcion());
                        ticket.put("TASA", formatTasa(compra.getTipoCambio()));
                        ticket.put("RECIBIDO", formatMoneda.formatMoneda(compra.getMontoRecibido().doubleValue()));
                        ticket.put("ENTREGADO", formatMoneda.formatMoneda(compra.getMontoEntregado().doubleValue()));
                        ticket.put("TIPO", o.getTTipoOperacion().getNombre());
                        ticket.put("MON", o.getTMoneda().getSimbolo());
                        ticket.put("MONEDA", o.getTMoneda().getNombre());
                        ticket.put("NOP", o.getNumeroOperacion());
                        ticket.put("SIMBOLO", o.getTMoneda().getSimbolo());
                        ticket.put("COD_OP", o.getTMoneda().getCodMoneda());
                        ticket.put("D_DETERIORO", compra.getDescuentoDeterioro());
                        ticket.put("h", "true");
                        session.setAttribute("ticket", ticket);
                        response.sendRedirect("vercambiomoneda.htm");
                        break;
                    case 2:
                        TRegistroCompraVenta venta = (TRegistroCompraVenta) o.getTRegistroCompraVentas().iterator().next();
                        ticket.put("FECHA", o.getFecha().substring(8, 10) + "/" + o.getFecha().substring(5, 8) + o.getFecha().substring(0, 4));
                        ticket.put("HORA", o.getFecha().substring(11));
                        ticket.put("RZS", o.getDescripcion());
                        ticket.put("TASA", formatTasa(venta.getTipoCambio()));
                        ticket.put("RECIBIDO", formatMoneda.formatMoneda(venta.getMontoRecibido().doubleValue()));
                        ticket.put("ENTREGADO", formatMoneda.formatMoneda(venta.getMontoEntregado().doubleValue()));
                        ticket.put("TIPO", o.getTTipoOperacion().getNombre());
                        ticket.put("MON", o.getTMoneda().getSimbolo());
                        ticket.put("MONEDA", o.getTMoneda().getNombre());
                        ticket.put("NOP", o.getNumeroOperacion());
                        ticket.put("SIMBOLO", o.getTMoneda().getSimbolo());
                        ticket.put("COD_OP", o.getTMoneda().getCodMoneda());
                        ticket.put("D_DETERIORO", venta.getDescuentoDeterioro());
                        ticket.put("h", "true");
                        session.setAttribute("ticket", ticket);
                        response.sendRedirect("vercambiomoneda.htm");
                        break;
                    case 3:
                        TRegistroDepositoRetiro regDepRet = (TRegistroDepositoRetiro) o.getTRegistroDepositoRetiros().iterator().next();
                        ticket.put("RUC", regDepRet.getTCuentaPersona().getTPersona().getRuc());
                        ticket.put("DNI", regDepRet.getTCuentaPersona().getTPersona().getDocIdentidad());
                        ticket.put("TIPOPERSONA", regDepRet.getTCuentaPersona().getTPersona().getTCategoriaPersona().getDescripcion());
                        ticket.put("TIPOOPERACION", regDepRet.getTOperacion().getTTipoOperacion().getNombre());
                        ticket.put("IDOPERACION", regDepRet.getTOperacion().getIdOperacion());
                        ticket.put("NUMEROOPERACION", regDepRet.getTOperacion().getNumeroOperacion());
                        ticket.put("CODIGOCAJA", regDepRet.getTOperacion().getTPersonaCaja().getTCaja().getCodCaja());
                        String Filial = regDepRet.getTOperacion().getTPersonaCaja().getTCaja().getTFilial().getNombre();
                        Filial = Filial.replace("FILIAL", "OFICINA");
                        ticket.put("FILIAL", Filial);
                        ticket.put("MONEDA", regDepRet.getTCuentaPersona().getTMoneda().getNombre());
                        ticket.put("FECHA", o.getFecha().substring(8, 10) + "/" + o.getFecha().substring(5, 8) + o.getFecha().substring(0, 4));
                        ticket.put("HORA", o.getFecha().substring(11));
                        ticket.put("NOMBRE", regDepRet.getTCuentaPersona().getTPersona().getNombre());
                        ticket.put("APELLIDOS", regDepRet.getTCuentaPersona().getTPersona().getApellidos());
                        ticket.put("NOMBREREPRESENTANTE", regDepRet.getNombreRepresentante());
                        ticket.put("APELLIDOSPRESENTANTE", regDepRet.getApellidosRepresentante());
                        String c = regDepRet.getTCuentaPersona().getNumCta();
                        ticket.put("NUMEROCUENTA", c.substring(0, 5) + "-" + c.substring(5, 6) + "-" + c.substring(6));
                        ticket.put("CODTIPOCUENTA", regDepRet.getTCuentaPersona().getTTipoCuenta().getCodigoCuenta());
                        ticket.put("TIPOCUENTA", regDepRet.getTCuentaPersona().getTTipoCuenta().getDescripcion());
                        ticket.put("MON", regDepRet.getTCuentaPersona().getTMoneda().getSimbolo());
                        ticket.put("MONTO", CurrencyConverter.formatToMoneyUS(regDepRet.getImporte().doubleValue(), 2));
                        ticket.put("IMPORTE", CurrencyConverter.formatToMoneyUS(regDepRet.getImporte().doubleValue(), 2));
                        ticket.put("CATEGORIAPERSONA", regDepRet.getTCuentaPersona().getTPersona().getTCategoriaPersona().getDescripcion());
                        if (regDepRet.getTCertificados().isEmpty()) {
                            ticket.put("IDCERTIFICADO", null);
                            ticket.put("NUMEROCERTIFICADO", null);
                            ticket.put("IDANEXO", null);
                        } else {
                            Iterator i = regDepRet.getTCertificados().iterator();
                            while (i.hasNext()) {
                                TCertificado ce = (TCertificado) i.next();
                                if (ce.getTipo().equals("CERTIFICADO")) {
                                    ticket.put("IDCERTIFICADO", ce.getIdcertificado());
                                    ticket.put("NUMEROCERTIFICADO", ce.getNumCertificado());
                                } else {
                                    ticket.put("IDANEXO", ce.getIdcertificado());
                                }
                            }
                        }
                        if (regDepRet.getTCuentaPersona().getTTipoCuenta().getCodigoCuenta().equals("CPF")) {
                            TDetalleCuentaPersona detalleCuentPers = (TDetalleCuentaPersona) dao.findAll("from TDetalleCuentaPersona detcuenper where detcuenper.TCuentaPersona='" + regDepRet.getTCuentaPersona().getIdcuentapersona() + "'").get(0);
                            ticket.put("INTERES", detalleCuentPers.getInteres());
                            ticket.put("NUMERODIASPF", detalleCuentPers.getNumDias());
                            ticket.put("FECHAPF", DateUtil.eedatefmt(DateUtil.fmtdate24, Double.parseDouble(detalleCuentPers.getFechaPlazo())).replace(",", ""));
                        } else {
                            ticket.put("INTERES", regDepRet.getTCuentaPersona().getInteres());
                        }
                        ticket.put("SALDO", CurrencyConverter.formatToMoneyUS(regDepRet.getTOperacion().getSaldofinal().doubleValue(), 2));
                        ticket.put("SALDO1", CurrencyConverter.formatToMoneyUS(regDepRet.getTOperacion().getSaldoFinalSinInteres().doubleValue(), 2));
                        ticket.put("INT",CurrencyConverter.formatToMoneyUS(regDepRet.getTOperacion().getSaldofinal().doubleValue()-regDepRet.getTOperacion().getSaldoFinalSinInteres().doubleValue(), 2));
                        ticket.put("h", "true");
                        session.setAttribute("ticket", ticket);
                        response.sendRedirect("ticket.htm");
                        break;
                    case 4:
                        TRegistroDepositoRetiro regDepRet2 = (TRegistroDepositoRetiro) o.getTRegistroDepositoRetiros().iterator().next();
                        ticket.put("RUC", regDepRet2.getTCuentaPersona().getTPersona().getRuc());
                        ticket.put("DNI", regDepRet2.getTCuentaPersona().getTPersona().getDocIdentidad());
                        ticket.put("TIPOPERSONA", regDepRet2.getTCuentaPersona().getTPersona().getTCategoriaPersona().getDescripcion());
                        ticket.put("TIPOOPERACION", regDepRet2.getTOperacion().getTTipoOperacion().getNombre());
                        ticket.put("IDOPERACION", regDepRet2.getTOperacion().getIdOperacion());
                        ticket.put("NUMEROOPERACION", regDepRet2.getTOperacion().getNumeroOperacion());
                        ticket.put("CODIGOCAJA", regDepRet2.getTOperacion().getTPersonaCaja().getTCaja().getCodCaja());
                        String Filial2 = regDepRet2.getTOperacion().getTPersonaCaja().getTCaja().getTFilial().getNombre();
                        Filial2 = Filial2.replace("FILIAL", "OFICINA");
                        ticket.put("FILIAL", Filial2);
                        ticket.put("MONEDA", regDepRet2.getTCuentaPersona().getTMoneda().getNombre());
                        ticket.put("FECHA", o.getFecha().substring(8, 10) + "/" + o.getFecha().substring(5, 8) + o.getFecha().substring(0, 4));
                        ticket.put("HORA", o.getFecha().substring(11));
                        ticket.put("NOMBRE", regDepRet2.getTCuentaPersona().getTPersona().getNombre());
                        ticket.put("APELLIDOS", regDepRet2.getTCuentaPersona().getTPersona().getApellidos());
                        ticket.put("NOMBREREPRESENTANTE", regDepRet2.getNombreRepresentante());
                        ticket.put("APELLIDOSPRESENTANTE", regDepRet2.getApellidosRepresentante());
                        String c2 = regDepRet2.getTCuentaPersona().getNumCta();
                        ticket.put("NUMEROCUENTA", c2.substring(0, 5) + "-" + c2.substring(5, 6) + "-" + c2.substring(6));
                        ticket.put("CODTIPOCUENTA", regDepRet2.getTCuentaPersona().getTTipoCuenta().getCodigoCuenta());
                        ticket.put("TIPOCUENTA", regDepRet2.getTCuentaPersona().getTTipoCuenta().getDescripcion());
                        ticket.put("MON", regDepRet2.getTCuentaPersona().getTMoneda().getSimbolo());
                        ticket.put("MONTO", CurrencyConverter.formatToMoneyUS(regDepRet2.getImporte().doubleValue(), 2));
                        ticket.put("IMPORTE", CurrencyConverter.formatToMoneyUS(regDepRet2.getImporte().doubleValue(), 2));
                        ticket.put("CATEGORIAPERSONA", regDepRet2.getTCuentaPersona().getTPersona().getTCategoriaPersona().getDescripcion());
                        if (regDepRet2.getTCertificados().isEmpty()) {
                            ticket.put("IDCERTIFICADO", null);
                            ticket.put("NUMEROCERTIFICADO", null);
                            ticket.put("IDANEXO", null);
                        } else {
                            Iterator i = regDepRet2.getTCertificados().iterator();
                            while (i.hasNext()) {
                                TCertificado ce = (TCertificado) i.next();
                                if (ce.getTipo().equals("CERTIFICADO")) {
                                    ticket.put("IDCERTIFICADO", ce.getIdcertificado());
                                    ticket.put("NUMEROCERTIFICADO", ce.getNumCertificado());
                                } else {
                                    ticket.put("IDANEXO", ce.getIdcertificado());
                                }
                            }
                        }
                        if (regDepRet2.getTCuentaPersona().getTTipoCuenta().getCodigoCuenta().equals("CPF")) {
                            TDetalleCuentaPersona detalleCuentPers = (TDetalleCuentaPersona) dao.findAll("from TDetalleCuentaPersona detcuenper where detcuenper.TCuentaPersona='" + regDepRet2.getTCuentaPersona().getIdcuentapersona() + "'").get(0);
                            ticket.put("INTERES", detalleCuentPers.getInteres());
                            ticket.put("NUMERODIASPF", detalleCuentPers.getNumDias());
                            ticket.put("FECHAPF", DateUtil.eedatefmt(DateUtil.fmtdate24, Double.parseDouble(detalleCuentPers.getFechaPlazo())).replace(",", ""));
                        } else {
                            ticket.put("INTERES", regDepRet2.getTCuentaPersona().getInteres());
                        }
                        ticket.put("SALDO", CurrencyConverter.formatToMoneyUS(regDepRet2.getTOperacion().getSaldofinal().doubleValue(), 2));
                        ticket.put("SALDO1", CurrencyConverter.formatToMoneyUS(regDepRet2.getTOperacion().getSaldoFinalSinInteres().doubleValue(), 2));
                        ticket.put("INT",CurrencyConverter.formatToMoneyUS(regDepRet2.getTOperacion().getSaldofinal().doubleValue()-regDepRet2.getTOperacion().getSaldoFinalSinInteres().doubleValue(), 2));
                        ticket.put("h", "true");
                        session.setAttribute("ticket", ticket);
                        response.sendRedirect("ticket.htm");
                        break;
                    case 5:
                        TRegistroGiro giro = (TRegistroGiro) o.getTRegistroGiros().iterator().next();
                        if (giro.getEstado().equals("EXTORNADO")) {
                            out.println("<fieldset><legend style='font-size:9px'><b>DATOS DE LA OPERACION</b></legend>"
                                    + "<table border='0' cellpadding='5' cellspacing='5' id='h1' class='tabla' width='100%'>"
                                    + "<tr><td valign='top'>Esta operación a sido extornado el " + o.getFechaExtornacion()
                                    + "</td></tr></table></fieldset>");
                            dao.cerrarSession();
                            out.close();
                            dao.cerrarSession();
                            return;
                        }
                        session.setAttribute("h", "true");
                        session.setAttribute("ID_REGISTRO_GIRO", giro.getIdregistro());
                        response.sendRedirect("SReciboGiro");
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        TRegistroGiro giro_cobro = null;
                        try {
                            giro_cobro = (TRegistroGiro) dao.findAll("from TRegistroGiro where idoperacioncobro='" + o.getIdOperacion() + "'").get(0);
                        } catch (Exception ex) {
                            out.print("FALLO AL CARGAR EL DATO!!! " + ex.getMessage());
                            dao.cerrarSession();
                            out.close();
                            return;
                        }
                        session.setAttribute("h", "true");
                        session.setAttribute("ID_REGISTRO_GIRO", giro_cobro.getIdregistro());
                        response.sendRedirect("SReciboCobroGiro");
                        break;
                    case 9:
                        TTransferenciaCaja ttc = (TTransferenciaCaja) o.getTTransferenciaCajas().iterator().next();
                        if (ttc.getEstado().equals("EXTORNADO")) {
                            out.println("<fieldset><legend style='font-size:9px'><b>DATOS DE LA OPERACION</b></legend>"
                                    + "<table border='0' cellpadding='5' cellspacing='5' id='h1' class='tabla' width='100%'>"
                                    + "<tr><td valign='top'>Esta operación a sido extornado el " + o.getFechaExtornacion()
                                    + "</td></tr></table></fieldset>");
                            dao.cerrarSession();
                            out.close();
                            return;
                        }
                        break;
                    case 10:
                        break;
                    case 11:
                        break;
                    case 12:
                        break;
                    case 13:
                        break;
                    case 14:
                        break;
                    default:
                        out.print("<fieldset><legend style='font-size:9px'><b>DATOS DE LA OPERACION</b></legend>"
                                + "<table border='0' cellpadding='5' cellspacing='5' id='h1' class='tabla' width='100%'>"
                                + "<tr><td valign='top'>NO hay nada que mostrar.</td></tr></table></fieldset>");
                }
            }            
        } finally {
            dao.cerrarSession();
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

    private static String formatTasa(String t) {
        String tasa = t;
        tasa = tasa.replace(".", "-");
        String[] array = tasa.split("-");
        if (array[1].length() < 3) {
            t = t + "&nbsp;&nbsp;";
        }
        return t;
    }
}
