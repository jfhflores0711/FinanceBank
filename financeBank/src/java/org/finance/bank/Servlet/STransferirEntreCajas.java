package org.finance.bank.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.finance.bank.bean.TCaja;
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
public class STransferirEntreCajas extends HttpServlet {

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
        String caja1 = (String) session.getAttribute("USER_CODCAJA");
        if (caja1 == null) {
            out.print("ERROR DE SESSION");
            out.close();
            return;
        }
        String myFi = session.getAttribute("USER_CODFILIAL").toString();
        String myIdP = session.getAttribute("USER_ID_PERSONA_CAJA").toString();
        String myId = session.getAttribute("USER_ID").toString();
        String myIp = session.getAttribute("USER_IP").toString();
        String idForInitNewestTable = DateUtil.convertDateId(myIdP, STransferirEntreCajas.class.getSimpleName());
        String idForInitNewestTable2 = DateUtil.convertDateId(myIdP, STransferirEntreCajas.class.getSimpleName());
        DAOGeneral dao = new DAOGeneral();
        try {
            String CodigoMoneda = null;
            if (request.getParameter("CodigoMoneda") != null && !request.getParameter("CodigoMoneda").equals("")) {
                CodigoMoneda = request.getParameter("CodigoMoneda").toString().trim();
            } else {
                out.print("Error de moneda");
                out.close();
                return;
            }
            String MontoTransferir = null;
            if (request.getParameter("MontoTransferir") != null && !request.getParameter("MontoTransferir").equals("")) {
                MontoTransferir = request.getParameter("MontoTransferir").toString();
            } else {
                out.print("Error de Montos");
                out.close();
                return;
            }
            String cajaDestino = null;
            if (request.getParameter("CajaDestino") != null && !request.getParameter("CajaDestino").equals("")) {
                cajaDestino = request.getParameter("CajaDestino").toString();
            } else {
                out.print("Error de CAJAS");
                out.close();
                return;
            }
            Double mimontot = Double.parseDouble(MontoTransferir);
            TMoneda moneda = (TMoneda) dao.load(TMoneda.class, CodigoMoneda);
            //TDetalleCaja actualD = (TDetalleCaja) dao.load(TDetalleCaja.class, DateUtil.getDate(new Date()).replaceAll("/", "") + cajaDestino + moneda.getCodMoneda() + "00");
            TCaja caja = (TCaja) dao.load(TCaja.class, caja1);
            String xTipo = "921";
            String relleno = "TRANSFERENCIA DE MONTOS ADICIONALES ";
            TLogFinance actualD = (TLogFinance) dao.load(TLogFinance.class, "LOG" + cajaDestino + moneda.getCodMoneda());
            if (actualD == null) {
                session.setAttribute("ID_TRANSFERENCIA", null);
                out.println("dep = " + null);
                return;
            }
            if (caja.getCodCaja().endsWith("CA001")) {
                actualD.setMontoInicial(new BigDecimal(actualD.getMontoInicial().doubleValue() + mimontot));
                xTipo = "911";
                relleno = "ENTREGA DE MONTO INICIAL ";
            } else {
                actualD.setMontoRecibido(actualD.getMontoRecibido().add(new BigDecimal(mimontot)));
            }
            actualD.setMontoFinal(new BigDecimal(actualD.getMontoFinal().doubleValue() + mimontot));
            actualD.setActivoCajaybanco(actualD.getActivoCajaybanco().add(new BigDecimal(mimontot)));
            actualD.setMonto(actualD.getMonto().add(new BigDecimal(mimontot)));
            dao.update();
            TPersonaCaja pcaja = (TPersonaCaja) dao.load(TPersonaCaja.class, myIdP);
            TTipoOperacion tipoOper = (TTipoOperacion) dao.load(TTipoOperacion.class, "TIPC9");
            TOperacion operacion = new TOperacion(idForInitNewestTable,
                    tipoOper, pcaja, moneda, numeroOperacion.getNumber(myFi, caja1),
                    DateUtil.getNOW_S(), "ACTIVO", myId + " STransferirEntreCajas",
                    myIp, DateUtil.getNOW_S());
            operacion.setDescripcion(actualD.getIdlogfinance() + " TRANSFERENCIA");
            operacion.setSaldofinal(BigDecimal.ZERO);
            operacion.setSaldoFinalSinInteres(BigDecimal.ZERO);
            operacion.setFd(new Date());
            dao.persist(operacion);
            //TDetalleCaja actual = (TDetalleCaja) dao.load(TDetalleCaja.class, DateUtil.getDate(new Date()).replaceAll("/", "") + caja.getCodCaja() + moneda.getCodMoneda() + "00");
            TLogFinance actual = (TLogFinance) dao.load(TLogFinance.class, "LOG" + caja.getCodCaja() + moneda.getCodMoneda());
            actual.setMontoFinal(new BigDecimal(actual.getMontoFinal().doubleValue() - mimontot));
            actual.setMontoEntregado(actual.getMontoEntregado().add(new BigDecimal(mimontot)));
            actual.setActivoCajaybanco(actual.getActivoCajaybanco().subtract(new BigDecimal(mimontot)));
            actual.setMonto(actual.getMonto().subtract(new BigDecimal(mimontot)));
            dao.update();
            BigDecimal monto = new BigDecimal(mimontot);
            if (cajaDestino.endsWith("CA001")) {
                xTipo = "931";
                relleno = "TRANSFERENCIA DE MONTOS EXCEDENTES ";
            }
            TTransferenciaCaja ntranf = new TTransferenciaCaja(idForInitNewestTable, caja, operacion,
                    DateUtil.getNOW_S(), monto, xTipo, cajaDestino, myIp,
                    myId, DateUtil.getNOW_S(), CodigoMoneda);
            ntranf.setDescripcion(relleno + DateUtil.getNOW_S());
            ntranf.setEstado("ACTIVO");
            dao.persist(ntranf);
            TTipoOperacion tipoRecep = (TTipoOperacion) dao.load(TTipoOperacion.class, "TIPC12");
            List cualquiera = dao.findAll("from TPersonaCaja where TCaja.codCaja='" + cajaDestino + "' and estado = 'ACTIVO'");
            try {
                if (!cualquiera.isEmpty()) {
                    TOperacion recepcion = new TOperacion(idForInitNewestTable2,
                            tipoRecep, (TPersonaCaja) cualquiera.get(0), moneda, numeroOperacion.getNumber(myFi, cajaDestino),
                            DateUtil.getNOW_S(), "ACTIVO", myId,
                            myIp, DateUtil.getNOW_S());
                    recepcion.setDescripcion(cajaDestino + CodigoMoneda + " RECEPCION");
                    recepcion.setSaldofinal(actualD.getMontoFinal());
                    recepcion.setSaldoFinalSinInteres(actualD.getMontoFinal());
                    recepcion.setFd(new Date());
                    dao.persist(recepcion);
                    ntranf.setIdope(recepcion.getIdOperacion());
                    dao.update();
                } else {
                    ntranf.setEstado("ABORTED");
                    dao.update();
                }
            } catch (Exception ex) {
                Logger.getLogger(STransferirEntreCajas.class.getName()).log(Level.INFO, "ABORTADO = " + ex.getMessage());
                ntranf.setEstado("ABORTED");
                dao.update();
            }
            session.setAttribute("ID_TRANSFERENCIA", ntranf.getIdtransferenciacaja());
            out.println("dep = " + ntranf.getIdtransferenciacaja());
        } finally {
            dao.cerrarSession();
            out.close();
        }
    }

    /**
     * --UPDATE "public".t_transferencia_caja SET "tipo" = '921' WHERE estado='ACTIVO';
     * --UPDATE "public".t_transferencia_caja SET "estado" = 'ACTIVO' WHERE estado is null;
     * --UPDATE "public".t_transferencia_caja SET "tipo" = '921' WHERE estado='ACTIVO';
     * --UPDATE "public".t_transferencia_caja SET "tipo" = '911' WHERE cod_caja='0501CA001';
     * --UPDATE "public".t_transferencia_caja SET "tipo" = '931' WHERE cod_caja_destino='0501CA001';
     */
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
