package org.finance.bank.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.finance.bank.bean.TCaja;
import org.finance.bank.bean.TDetalleCaja;
import org.finance.bank.bean.TLogFinance;
import org.finance.bank.bean.TMoneda;
import org.finance.bank.bean.TOperacion;
import org.finance.bank.bean.TPersonaCaja;
import org.finance.bank.bean.TTipoOperacion;
import org.finance.bank.bean.TTransferenciaCaja;
import org.finance.bank.model.dao.DAOGeneral;
import org.finance.bank.util.DateUtil;
import org.finance.bank.util.numeroOperacion;

/**
 *
 * @author ronald
 */
public class SCerrarFilial extends HttpServlet {

    /**
     * Manejo de codigo 12, transferencia de cajas
     * Transferencia de cajas entre filiales de patrimonios
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
        String myId = session.getAttribute("USER_ID").toString();
        String myIp = session.getAttribute("USER_IP").toString();
        String myIDP = session.getAttribute("USER_ID_PERSONA_CAJA").toString();
        try {
            String codfilial = "";
            if (request.getParameter("codfilial") != null || !request.getParameter("codfilial").equals("")) {
                codfilial = request.getParameter("codfilial").toString();
            } else {
                out.print("ERROR EN TRANSACION 1");
                out.close();
                dao.cerrarSession();
                return;
            }

            if (codfilial.equals("0501")) {
                out.print("NO se puede cerrar al filial principal!!");
                out.close();
                dao.cerrarSession();
                return;
            }
            List result = dao.findAll("from TMoneda where estado ='ACTIVO'");
            if (result.isEmpty()) {
                out.print("ERROR: no se pudo leer la bases de datos!!");
                out.close();
                dao.cerrarSession();
                return;
            }
            List listCajas = dao.findAll("TCajas where estado = 'ACTIVO' TFilial.codFilial='" + codfilial + "' order by codCaja");
            if (listCajas.isEmpty()) {
                out.print("Error en la lectura de la Bases de datos!!");
                out.close();
                dao.cerrarSession();
                return;
            } else {
                Iterator iCajas = listCajas.iterator();
                while (iCajas.hasNext()) {
                    TCaja caja = (TCaja) iCajas.next();
                    if (!caja.getCodCaja().endsWith("CA001")) {
                        List lMo = dao.findAll("from TMoneda where estado = 'ACTIVO'");
                        if (!lMo.isEmpty()) {
                            Iterator iMo = lMo.iterator();
                            while (iMo.hasNext()) {
                                TMoneda xmo = (TMoneda) iMo.next();
                                /*List lDetC = dao.findAll("from TDetalleCaja where TCaja.codCaja='" + caja.getCodCaja()
                                + "' and TMoneda.codMoneda='" + xmo.getCodMoneda()
                                + "' and estado='ACTIVO' order by fechaTransaccion desc");*/
                                TLogFinance logFor = (TLogFinance) dao.load(TLogFinance.class, "LOG" + caja.getCodCaja() + xmo.getCodMoneda());
                                if (logFor != null) {
                                    if (logFor.getMontoFinal().doubleValue() != 0.00D) {
                                        out.print("Existe caja secundaria " + caja.getNombreCaja() + " con monto " + logFor.getMontoFinal() + "!!");
                                        dao.cerrarSession();
                                        out.close();
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if(true)return;
            Iterator it = result.iterator();
            while (it.hasNext()) {
                TMoneda xMoney = (TMoneda) it.next();
                String idForInitNewestTable = DateUtil.convertDateId(myIDP, SCerrarFilial.class.getSimpleName());
                /*TDetalleCaja detallCajap = iniDetalleCaja.detalleActivaCaja(codfilial + "CA001", xMoney.getCodMoneda(), DateUtil.getDate(new Date()));*/
                TDetalleCaja detallCajap = (TDetalleCaja) dao.load(TDetalleCaja.class, DateUtil.getDate(new Date()).replaceAll("/", "") + codfilial + "CA001" + xMoney.getCodMoneda() + "00");
                TLogFinance log = (TLogFinance) dao.load(TLogFinance.class, "LOG" + codfilial + "CA001" + xMoney.getCodMoneda());
                if (log.getMontoFinal().doubleValue() < 0D) {
                    out.print("NO se puede cerrar, pues el monto es Menor que cero!!");
                    dao.cerrarSession();
                    out.close();
                    return;
                }
                //TDetalleCaja central = (TDetalleCaja) dao.load(TDetalleCaja.class, DateUtil.getDate(new Date()).replaceAll("/", "") + "0501CA001" + xMoney.getCodMoneda() + "00");
                //central.setMontoFinal(central.getMontoFinal().add(detallCajap.getMontoFinal()));
                //central.setMontoRecibido(central.getMontoRecibido().add(detallCajap.getMontoFinal()));
                //central.setDateUser(DateUtil.getNOW_S());
                TLogFinance logCentral = (TLogFinance) dao.load(TLogFinance.class, "LOG0501CA001" + xMoney.getCodMoneda());
                logCentral.setMontoFinal(logCentral.getMontoFinal().add(log.getMontoFinal()));
                logCentral.setMontoRecibido(logCentral.getMontoRecibido().add(log.getMontoFinal()));
                dao.update();
                TPersonaCaja pcaja = (TPersonaCaja) dao.load(TPersonaCaja.class, myIDP);
                TOperacion operacion = new TOperacion();
                operacion.setIdOperacion(idForInitNewestTable);
                operacion.setDescripcion(DateUtil.getDate(new Date()) + " TRANSFERENCIA INTERNA");
                operacion.setFecha(DateUtil.getNOW_S());
                TTipoOperacion tipoOper = (TTipoOperacion) dao.load(TTipoOperacion.class, "TIPC14");
                operacion.setTTipoOperacion(tipoOper);
                operacion.setEstado("ACTIVO");
                operacion.setNumeroOperacion(numeroOperacion.getNumber(codfilial, codfilial + "CA001"));
                operacion.setTMoneda(xMoney);
                operacion.setIdUser(myId + " 932");
                operacion.setIpUser(myIp);
                operacion.setDateUser(DateUtil.getNOW_S());
                operacion.setTPersonaCaja(pcaja);
                operacion.setSaldofinal(BigDecimal.ZERO);
                operacion.setSaldoFinalSinInteres(BigDecimal.ZERO);
                operacion.setFd(new Date());
                dao.persist(operacion);
                TOperacion recepcion = new TOperacion();
                recepcion.setIdOperacion(idForInitNewestTable);
                recepcion.setDescripcion(DateUtil.getDate(new Date()) + " TRANSFERENCIA POR CIERRE DE FILIAL");
                recepcion.setFecha(DateUtil.getNOW_S());
                TTipoOperacion tipoOper2 = (TTipoOperacion) dao.load(TTipoOperacion.class, "TIPC12");
                recepcion.setTTipoOperacion(tipoOper2);
                recepcion.setEstado("ACTIVO");
                recepcion.setNumeroOperacion(numeroOperacion.getNumber("0501", "0501CA001"));
                recepcion.setTMoneda(xMoney);
                recepcion.setIdUser(myId + " 932");
                recepcion.setIpUser(myIp);
                recepcion.setDateUser(DateUtil.getNOW_S());
                recepcion.setTPersonaCaja(pcaja);
                recepcion.setSaldofinal(BigDecimal.ZERO);
                recepcion.setSaldoFinalSinInteres(BigDecimal.ZERO);
                recepcion.setFd(new Date());
                dao.persist(recepcion);
                TTransferenciaCaja nTransf = new TTransferenciaCaja();
                nTransf.setIdtransferenciacaja(idForInitNewestTable);
                nTransf.setFecha(DateUtil.getNOW_S());
                nTransf.setMonto(log.getMontoFinal());
                nTransf.setDescripcion("* CERRADO DE FILIAL " + codfilial);
                nTransf.setCodCajaDestino("0501CA001");
                nTransf.setTCaja(detallCajap.getTCaja());
                nTransf.setTipo("932");
                nTransf.setIdUser(myId + " 932");
                nTransf.setIpUser(myIp);
                nTransf.setDateUser(DateUtil.getNOW_S());
                nTransf.setEstado("ACTIVO");
                nTransf.setTOperacion(operacion);
                nTransf.setIdope(recepcion.getIdOperacion());
                dao.persist(nTransf);
                //TDetalleCaja tUp = (TDetalleCaja) dao.load(TDetalleCaja.class, detallCajap.getIddetallecaja());
                //tUp.setMontoFinal(BigDecimal.ZERO);
                //tUp.setDateUser(DateUtil.getNOW_S());
                log.setMontoEntregado(log.getMontoEntregado().add(log.getMontoFinal()));
                log.setMontoFinal(BigDecimal.ZERO);
                dao.update();
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
