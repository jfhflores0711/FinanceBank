/*
 * PARA MANTENER LA AUTOEXTORNACIÓN
 */
package org.finance.bank.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.finance.bank.bean.*;
import org.finance.bank.model.dao.DAOGeneral;
import org.finance.bank.util.DateUtil;

/**
 *
 * @author ronald
 */
public class SExtornoOp extends HttpServlet {

    /** ADMIN EXTORNO
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
        String idAdminExtorno = (String) session.getAttribute("ID_USER_ADMIN_AUTORIZER");
        if (idAdminExtorno == null || idAdminExtorno.equals("")) {
            out.print("ERROR: NO HAY AUTORIZACIÓN!!!<input id='txtExtorno' name='txtExtorno' type='text' value='NO'>");
            out.close();
            return;
        }
        String caja1 = session.getAttribute("USER_CODCAJA").toString();
        String idForInitNewestTable;
        DAOGeneral dao = new DAOGeneral();
        String relleno = "";
        try {
            idForInitNewestTable = (String) request.getParameter("id");
            TOperacion op = (TOperacion) dao.load(TOperacion.class, idForInitNewestTable);
            if (op == null) {
                out.print("ERROR: NO HAY NADA QUE EXTORNAR!!!<input id='txtExtorno' name='txtExtorno' type='hidden' value='NO'>");
                out.close();
                return;
            }
            int chain = (int) Integer.parseInt(op.getTTipoOperacion().getCodigoTipoOperacion().substring(4));
            if (chain >= 11) {
                out.println("<input id='txtExtorno' name='txtExtorno' type='text' value='NO'>");
                out.close();
                dao.cerrarSession();
                return;
            }
            op.setEstado("EXTORNADO");
            op.setFechaExtornacion(DateUtil.getNOW_S());
            op.setIdAdminExtorno(idAdminExtorno);
            dao.update();
            boolean estado = false;
            switch (chain) {
                case 1:
                    TRegistroCompraVenta compra = (TRegistroCompraVenta) dao.load(TRegistroCompraVenta.class, idForInitNewestTable);
                    if (compra.getEstado().equals("EXTORNADO")) {
                        out.println("<input id='txtExtorno' name='txtExtorno' type='text' value='SI'>");
                        out.close();
                        dao.cerrarSession();
                        return;
                    }
                    compra.setEstado("EXTORNADO");
                    dao.update();
                    TLogFinance logcompra = (TLogFinance) dao.load(TLogFinance.class, "LOG" + caja1 + op.getTMoneda().getCodMoneda());
                    logcompra.setMontoFinal(logcompra.getMontoFinal().subtract(compra.getMontoRecibido()));
                    logcompra.setMontoRecibido(logcompra.getMontoRecibido().subtract(compra.getMontoRecibido()));
                    logcompra.setActivoCajaybanco(logcompra.getActivoCajaybanco().subtract(compra.getMontoRecibido()));
                    logcompra.setMonto(logcompra.getMonto().subtract(compra.getMontoRecibido()));
                    dao.update();
                    TLogFinance logcompra2 = (TLogFinance) dao.load(TLogFinance.class, "LOG" + caja1 + ((TMoneda) dao.load(TMoneda.class, "PEN")).getCodMoneda());
                    logcompra2.setMontoFinal(logcompra2.getMontoFinal().add(compra.getMontoEntregado()));
                    logcompra2.setMontoEntregado(logcompra2.getMontoEntregado().subtract(compra.getMontoEntregado()));
                    logcompra2.setActivoCajaybanco(logcompra2.getActivoCajaybanco().add(compra.getMontoEntregado()));
                    logcompra2.setMonto(logcompra2.getMonto().add(compra.getMontoEntregado()));
                    dao.update();
                    estado = true;
                    relleno = "COMPRA EXTORNADA CORRECTAMENTE!!";
                    break;
                case 2:
                    TRegistroCompraVenta venta = (TRegistroCompraVenta) dao.load(TRegistroCompraVenta.class, idForInitNewestTable);
                    if (venta.getEstado().equals("EXTORNADO")) {
                        out.println("<input id='txtExtorno' name='txtExtorno' type='text' value='SI'>");
                        dao.cerrarSession();
                        out.close();
                        return;
                    }
                    venta.setEstado("EXTORNADO");
                    dao.update();
                    TLogFinance logcajav = (TLogFinance) dao.load(TLogFinance.class, "LOG" + caja1 + op.getTMoneda().getCodMoneda());
                    //logcajav.setMontoFinal(new BigDecimal(logcajav.getMontoFinal().doubleValue() + venta.getMontoEntregado().doubleValue()));
                    logcajav.setMontoFinal(logcajav.getMontoFinal().add(venta.getMontoEntregado()));
                    logcajav.setMontoEntregado(logcajav.getMontoEntregado().subtract(venta.getMontoEntregado()));
                    logcajav.setActivoCajaybanco(logcajav.getActivoCajaybanco().add(venta.getMontoEntregado()));
                    logcajav.setMonto(logcajav.getMonto().add(venta.getMontoEntregado()));
                    dao.update();
                    TLogFinance logVenta2 = (TLogFinance) dao.load(TLogFinance.class, "LOG" + caja1 + ((TMoneda) dao.load(TMoneda.class, "PEN")).getCodMoneda());
                    //logVenta2.setMontoFinal(new BigDecimal(logVenta2.getMontoFinal().doubleValue() - venta.getMontoRecibido().doubleValue()));
                    logVenta2.setMontoFinal(logVenta2.getMontoFinal().subtract(venta.getMontoRecibido()));
                    logVenta2.setMontoRecibido(logVenta2.getMontoRecibido().subtract(venta.getMontoRecibido()));
                    logVenta2.setActivoCajaybanco(logVenta2.getActivoCajaybanco().subtract(venta.getMontoRecibido()));
                    logVenta2.setMonto(logVenta2.getMonto().subtract(venta.getMontoRecibido()));
                    dao.update();
                    estado = true;
                    relleno = "VENTA EXTORNADA CORRECTAMENTE!!";
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    TRegistroGiro giro = (TRegistroGiro) dao.load(TRegistroGiro.class, idForInitNewestTable);
                    if (giro.getEstado().equals("EXTORNADO")) {
                        out.println("<input id='txtExtorno' name='txtExtorno' type='text' value='SI'>");
                        out.close();
                        dao.cerrarSession();
                        return;
                    }
                    giro.setEstado("EXTORNADO");
                    dao.update();
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    try {
                        TRegistroGiro giro_cobro = (TRegistroGiro) dao.findAll("TRegistroGiro where idoperacioncobro='" + idForInitNewestTable + "'").get(0);
                        giro_cobro.setEstado("ESPERA");
                        dao.update();
                    } catch (Exception ex) {
                        out.print("FALLO DEL OBJETO!!!");
                    }
                    break;
                case 9:
                    TTransferenciaCaja ttc = (TTransferenciaCaja) dao.load(TTransferenciaCaja.class, idForInitNewestTable);
                    if (ttc.getEstado().equals("ACTIVO")) {
                        ttc.setEstado("EXTORNADO");
                        ttc.setFecha(DateUtil.getNOW_S());
                        dao.update();
                        TLogFinance log1 = (TLogFinance) dao.load(TLogFinance.class, "LOG" + ttc.getTCaja().getCodCaja() + ttc.getTOperacion().getTMoneda().getCodMoneda());
                        log1.setMontoFinal(log1.getMontoFinal().add(ttc.getMonto()));
                        log1.setMontoEntregado(log1.getMontoEntregado().subtract(ttc.getMonto()));
                        log1.setActivoCajaybanco(log1.getActivoCajaybanco().add(ttc.getMonto()));
                        log1.setMonto(log1.getMonto().add(ttc.getMonto()));
                        dao.update();
                        if (ttc.getIdope() != null) {
                            TOperacion op2 = (TOperacion) dao.load(TOperacion.class, ttc.getIdope());
                            op2.setEstado("EXTORNADO");
                            op2.setIdAdminExtorno(idAdminExtorno);
                            op2.setFechaExtornacion(DateUtil.getNOW_S());
                            dao.update();
                            //TDetalleCaja desca = (TDetalleCaja) dao.load(TDetalleCaja.class, DateUtil.getDate(op.getFd()).replaceAll("/", "") + ttc.getCodCajaDestino() + ttc.getTOperacion().getTMoneda().getCodMoneda() + "00");
                            TLogFinance logDes = (TLogFinance) dao.load(TLogFinance.class, "LOG" + ttc.getCodCajaDestino() + ttc.getTOperacion().getTMoneda().getCodMoneda());
                            if (ttc.getTipo().equals("911")) {
                                //desca.setMontoInicial(desca.getMontoInicial().subtract(ttc.getMonto()));
                                logDes.setMontoInicial(logDes.getMontoInicial().subtract(ttc.getMonto()));
                            } else {
                                logDes.setMontoRecibido(logDes.getMontoRecibido().subtract(ttc.getMonto()));
                            }
                            logDes.setMontoFinal(logDes.getMontoFinal().add(ttc.getMonto()));
                            logDes.setActivoCajaybanco(logDes.getActivoCajaybanco().subtract(ttc.getMonto()));
                            logDes.setMonto(logDes.getMonto().subtract(ttc.getMonto()));
                            dao.update();
                        }
                        out.println("<input id='txtExtorno' name='txtExtorno' type='text' value='SI'>");
                        dao.cerrarSession();
                        out.close();
                        return;
                    }
                    if ("CAPITAL SOCIAL".equals(ttc.getTipo())) {
                        out.println("ERROR:<input id='txtExtorno' name='txtExtorno' type='text' value='NO'>");
                        out.close();
                        dao.cerrarSession();
                        return;
                    }
                    if (ttc.getEstado().equals("EXTORNADO")) {
                        out.println("<input id='txtExtorno' name='txtExtorno' type='text' value='SI'>");
                        dao.cerrarSession();
                        out.close();
                        return;
                    }
                    relleno += "<input id='txtExtorno' name='txtExtorno' type='text' value='SI'>";
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
                    relleno = "ERROR: NO hay NADA que extornar!!!";
            }
        } finally {
            dao.cerrarSession();
            out.println("<input id='txtExtorno' name='txtExtorno' type='text' value='SI'>"
                    + relleno);
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
