package org.finance.bank.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.finance.bank.bean.*;
import org.finance.bank.model.dao.DAOGeneral;
import org.finance.bank.util.*;

/**
 *
 * @author ronald
 */
public class SCobroGiro extends HttpServlet {

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
        String idcaja = session.getAttribute("USER_ID_PERSONA_CAJA").toString();
        DAOGeneral dao = new DAOGeneral();
        String idForInitNewestTable = DateUtil.convertDateId(idcaja, SCobroGiro.class.getSimpleName());
        try {
            String idregistro_giro = "*";
            if (request.getParameter("idregistro_giro") != null && !request.getParameter("idregistro_giro").equals("")) {
                idregistro_giro = request.getParameter("idregistro_giro").toString();
            }
            TRegistroGiro rgiro = (TRegistroGiro) dao.load(TRegistroGiro.class, idregistro_giro);
            TPersonaCaja pcaja = (TPersonaCaja) dao.load(TPersonaCaja.class, idcaja);
            TOperacion operacion = new TOperacion();
            operacion.setIdOperacion(idForInitNewestTable);
            operacion.setDescripcion(DateUtil.getDate(new Date()) + " * COBRO GIRO");
            operacion.setFecha(formatFecha.get());
            TTipoOperacion tipoOper = (TTipoOperacion) dao.load(TTipoOperacion.class, "TIPC8");
            operacion.setTTipoOperacion(tipoOper);
            operacion.setEstado("ACTIVO");
            operacion.setNumeroOperacion(numeroOperacion.getNumber(session.getAttribute("USER_CODFILIAL").toString(), session.getAttribute("USER_CODCAJA").toString()));
            TMoneda monedaObj = (TMoneda) dao.load(TMoneda.class, rgiro.getTOperacion().getTMoneda().getCodMoneda());
            operacion.setTMoneda(monedaObj);
            operacion.setIdUser(session.getAttribute("USER_ID").toString());
            operacion.setIpUser(session.getAttribute("USER_IP").toString());
            operacion.setDateUser(formatFecha.get());
            operacion.setTPersonaCaja(pcaja);
            operacion.setSaldofinal(BigDecimal.ZERO);
            operacion.setSaldoFinalSinInteres(BigDecimal.ZERO);
            operacion.setFd(new Date());
            dao.persist(operacion);
//            tr.commit();
//            DAOGeneral dao = new DAOGeneral();
//            adminCapital capital = new adminCapital(rgiro.getTOperacion().getTMoneda().getCodMoneda(), session.getAttribute("USER_ID").toString(), session.getAttribute("USER_IP").toString());
//            if (rgiro.getPagadorComision().equals("PRECEPTOR")) {
//                TRegistroGiro xGiro = (TRegistroGiro) dao.load(TRegistroGiro.class, rgiro.getIdregistro());
//                xGiro.setFpagoComision(fpago_comision);
//                dao.update();
////                tr = sess.beginTransaction();
//                String hql = "from TDetalleCaja where estado ='ACTIVO'"
//                        + " AND TCaja.codCaja ='" + session.getAttribute("USER_CODCAJA").toString() + "' "
//                        + " AND TMoneda.codMoneda ='" + rgiro.getTOperacion().getTMoneda().getCodMoneda() + "' ";
//                TDetalleCaja detallecaja = (TDetalleCaja) dao.findAll(hql).get(0);
//                if (rgiro.getFpagoComision().equals("DESCONTAR")) {
//                    TDetalleCaja xCaja = (TDetalleCaja) dao.load(TDetalleCaja.class, detallecaja.getIddetallecaja());
//                    xCaja.setMontoFinal(new BigDecimal(detallecaja.getMontoFinal().doubleValue() - rgiro.getImporte().doubleValue() + rgiro.getComision().doubleValue()));
//                    dao.update();
//                    TPatrimonio p = capital.ponerAsiento();
//                    TBalancexmoneda xbm = (TBalancexmoneda) dao.load(TBalancexmoneda.class, p.getTBalancexmoneda().getIdbalance());
//                    xbm.setActivoCajaybanco(new BigDecimal(xbm.getActivoCajaybanco().doubleValue() - detallecaja.getMontoFinal().doubleValue()));
//                    xbm.setPasivo(new BigDecimal(xbm.getPasivo().doubleValue() - detallecaja.getMontoFinal().doubleValue() + rgiro.getComision().doubleValue()));
//                    dao.update();
//                    TPatrimonio xp = (TPatrimonio) dao.load(TPatrimonio.class, p.getIdpatrimonio());
//                    xp.setPatrimonioActual(new BigDecimal(xp.getPatrimonioActual().doubleValue() + rgiro.getComision().doubleValue()));
//                    dao.update();
//                } else {
//                    TDetalleCaja xCaja = (TDetalleCaja) dao.load(TDetalleCaja.class, detallecaja.getIddetallecaja());
//                    xCaja.setMontoFinal(new BigDecimal(detallecaja.getMontoFinal().doubleValue() - rgiro.getImporte().doubleValue() + rgiro.getComision().doubleValue()));
//                    dao.update();
//                    TPatrimonio p = capital.ponerAsiento();
//                    TBalancexmoneda xbm = (TBalancexmoneda) dao.load(TBalancexmoneda.class, p.getTBalancexmoneda().getIdbalance());
//                    xbm.setActivoCajaybanco(new BigDecimal(xbm.getActivoCajaybanco().doubleValue() - detallecaja.getMontoFinal().doubleValue()));
//                    xbm.setPasivo(new BigDecimal(xbm.getPasivo().doubleValue() - detallecaja.getMontoFinal().doubleValue()));
//                    dao.update();
//                }
//            } else {
////                tr = sess.beginTransaction();
//                String hql = "from TDetalleCaja where estado ='ACTIVO'"
//                        + " AND TCaja.codCaja ='" + session.getAttribute("USER_CODCAJA").toString() + "' "
//                        + " AND TMoneda.codMoneda ='" + rgiro.getTOperacion().getTMoneda().getCodMoneda() + "' ";
//                TDetalleCaja detallecaja = (TDetalleCaja) dao.findAll(hql).get(0);
//                if (rgiro.getFpagoComision().equals("DESCONTAR")) {
//                    TDetalleCaja xCaja = (TDetalleCaja) dao.load(TDetalleCaja.class, detallecaja.getIddetallecaja());
//                    xCaja.setMontoFinal(new BigDecimal(detallecaja.getMontoFinal().doubleValue() - rgiro.getImporte().doubleValue() + rgiro.getComision().doubleValue()));
//                    dao.update();
//                    TPatrimonio p = capital.ponerAsiento();
//                    TBalancexmoneda xbm = (TBalancexmoneda) dao.load(TBalancexmoneda.class, p.getTBalancexmoneda().getIdbalance());
//                    xbm.setActivoCajaybanco(new BigDecimal(xbm.getActivoCajaybanco().doubleValue() - detallecaja.getMontoFinal().doubleValue()));
//                    xbm.setPasivo(new BigDecimal(xbm.getPasivo().doubleValue() - detallecaja.getMontoFinal().doubleValue() + rgiro.getComision().doubleValue()));
//                    dao.update();
//                    TPatrimonio xp = (TPatrimonio) dao.load(TPatrimonio.class, p.getIdpatrimonio());
//                    xp.setPatrimonioActual(new BigDecimal(xp.getPatrimonioActual().doubleValue() + rgiro.getComision().doubleValue()));
//                    dao.update();
//                } else {
//                    TDetalleCaja xCaja = (TDetalleCaja) dao.load(TDetalleCaja.class, detallecaja.getIddetallecaja());
//                    xCaja.setMontoFinal(new BigDecimal(detallecaja.getMontoFinal().doubleValue() - rgiro.getImporte().doubleValue()));
//                    dao.update();
//                    TPatrimonio p = capital.ponerAsiento();
//                    TBalancexmoneda xbm = (TBalancexmoneda) dao.load(TBalancexmoneda.class, p.getTBalancexmoneda().getIdbalance());
//                    xbm.setActivoCajaybanco(new BigDecimal(xbm.getActivoCajaybanco().doubleValue() - detallecaja.getMontoFinal().doubleValue()));
//                    xbm.setPasivo(new BigDecimal(xbm.getPasivo().doubleValue() - detallecaja.getMontoFinal().doubleValue()));
//                    dao.update();
//                }
//            }
            TRegistroGiro xGiro = (TRegistroGiro) dao.load(TRegistroGiro.class, rgiro.getIdregistro());
            xGiro.setIdoperacioncobro(operacion.getIdOperacion());
            xGiro.setEstado("COBRADO");
            xGiro.setFechaEntrega(formatFecha.get());
            dao.update();
            session.setAttribute("ID_REGISTRO_GIRO", rgiro.getIdregistro());
//            adminCapital ak = new adminCapital(monedaObj.getCodMoneda(), session.getAttribute("USER_ID").toString(), session.getAttribute("USER_IP").toString());
//            TPatrimonio p = ak.ponerAsiento();
//            TLogFinance newLog = new TLogFinance();
//            newLog.setIdlogfinance(idForInitNewestTable);
//            newLog.setActivoCajaybanco(p.getTBalancexmoneda().getActivoCajaybanco());
//            newLog.setActivoCuentaxcobrar(p.getTBalancexmoneda().getActivoCuentaxcobrar());
//            String sID = iniDetalleCaja.detalleActivaCaja(session.getAttribute("USER_CODCAJA").toString(), monedaObj.getCodMoneda(), DateUtil.getDate(new Date()));
//            System.out.println("sid" + sID);
//            TDetalleCaja mica = (TDetalleCaja) dao.load(TDetalleCaja.class, sID);
//            newLog.setCajero(mica.getMontoFinal().toString());
//            newLog.setEncaje(p.getTBalancexmoneda().getEncaje());
//            newLog.setFecha(DateUtil.getNOW_S());
//            newLog.setFilial(mica.getTCaja().getTFilial().getCodFilial());
//            newLog.setMonto(new BigDecimal(0.00));
//            newLog.setObservacion(xGiro.getComision().toString());
//            newLog.setPRespaldo(p.getTBalancexmoneda().getPRespaldo());
//            newLog.setPasivo(p.getTBalancexmoneda().getPasivo());
//            newLog.setPatrimonio(p.getPatrimonioActual());
//            newLog.setTipoOperacion(idForInitNewestTable);
//            newLog.setReferencia(p.getPatrimonio().toString());
//            dao.persist(newLog);
            out.print("OK");
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

