package org.finance.bank.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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
public final class SAgregarMfilial extends HttpServlet {

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
        String myId = session.getAttribute("USER_ID").toString();
        String myIp = session.getAttribute("USER_IP").toString();
        String myPC = session.getAttribute("USER_ID_PERSONA_CAJA").toString();
        String myFi = session.getAttribute("USER_CODFILIAL").toString();
        String myCa = session.getAttribute("USER_CODCAJA").toString();
        String idForInitNewestTable = DateUtil.convertDateId(myPC, SAgregarMfilial.class.getSimpleName());
        String idForInitNewestTable2 = DateUtil.convertDateId(myPC, SAgregarMfilial.class.getSimpleName());
        try {
            String codfilial = "";
            if (request.getParameter("codfilial") != null && !request.getParameter("codfilial").equals("")) {
                codfilial = request.getParameter("codfilial").toString();
            } else {
                out.print("ERROR de filial");
                out.close();
                return;
            }
            String montoFilial = "";
            if (request.getParameter("montoFilial") != null && !request.getParameter("montoFilial").equals("")) {
                montoFilial = request.getParameter("montoFilial").toString();
            } else {
                out.print("ERROR de monto");
                out.close();
                return;
            }
            String codmoneda = "";
            if (request.getParameter("codmoneda") != null && !request.getParameter("codmoneda").equals("")) {
                codmoneda = request.getParameter("codmoneda").toString();
            } else {
                out.print("ERROR de moneda");
                out.close();
                return;
            }
            BigDecimal monto = new BigDecimal(montoFilial.replace(" ", ""));
            TMoneda xmo = (TMoneda) dao.load(TMoneda.class, codmoneda);
            TDetalleCaja detallCajap = (TDetalleCaja) dao.load(TDetalleCaja.class, DateUtil.getDate(new Date()).replaceAll("/", "") + codfilial + "CA001" + xmo.getCodMoneda() + "00");
            //detallCajap.setMontoFinal(detallCajap.getMontoFinal().add(monto));
            //detallCajap.setMontoRecibido(detallCajap.getMontoRecibido().add(monto));
            TLogFinance log = (TLogFinance) dao.load(TLogFinance.class, "LOG" + codfilial + "CA001" + xmo.getCodMoneda());
            log.setMontoFinal(log.getMontoFinal().add(monto));
            log.setMontoRecibido(log.getMontoRecibido().add(monto));
            log.setActivoCajaybanco(log.getActivoCajaybanco().add(monto));
            log.setMonto(log.getMonto().add(monto));
            log.setPatrimonio(log.getPatrimonio().add(monto));
            dao.update();
            TPersonaCaja pcaja = (TPersonaCaja) dao.load(TPersonaCaja.class, myPC);
            TOperacion operacion = new TOperacion();
            operacion.setIdOperacion(idForInitNewestTable);
            operacion.setDescripcion(DateUtil.getDate(new Date()) + " TRANSFERENCIA ENTRE FILIALES");
            operacion.setFecha(DateUtil.getNOW_S());
            TTipoOperacion tipoOper = (TTipoOperacion) dao.load(TTipoOperacion.class, "TIPC14");
            operacion.setTTipoOperacion(tipoOper);
            operacion.setEstado("ACTIVO");
            operacion.setNumeroOperacion(numeroOperacion.getNumber(myFi, myCa));
            TMoneda moneda = (TMoneda) dao.load(TMoneda.class, codmoneda);
            operacion.setTMoneda(moneda);
            operacion.setIdUser(myId + " TIPC14");
            operacion.setIpUser(myIp);
            operacion.setDateUser(DateUtil.getNOW_S());
            operacion.setTPersonaCaja(pcaja);
            operacion.setSaldofinal(monto);
            operacion.setSaldoFinalSinInteres(monto);
            operacion.setFd(new Date());
            dao.persist(operacion);
            TOperacion recep = new TOperacion();
            recep.setIdOperacion(idForInitNewestTable2);
            recep.setDescripcion(DateUtil.getDate(new Date()) + " RECEPCIÓN");
            recep.setFecha(DateUtil.getNOW_S());
            tipoOper = (TTipoOperacion) dao.load(TTipoOperacion.class, "TIPC12");
            recep.setTTipoOperacion(tipoOper);
            recep.setEstado("ACTIVO");
            recep.setNumeroOperacion(numeroOperacion.getNumber(codfilial, myCa));
            recep.setTMoneda(moneda);
            recep.setIdUser(myId + " TIPC14");
            recep.setIpUser(myIp);
            recep.setDateUser(DateUtil.getNOW_S());
            recep.setTPersonaCaja(pcaja);
            recep.setSaldofinal(monto);
            recep.setSaldoFinalSinInteres(monto);
            recep.setFd(new Date());
            dao.persist(recep);
            TCaja caja = (TCaja) dao.load(TCaja.class, myCa);
            TTransferenciaCaja nTransf = new TTransferenciaCaja();
            nTransf.setIdtransferenciacaja(idForInitNewestTable);
            nTransf.setFecha(DateUtil.getNOW_S());
            nTransf.setMonto(monto);
            nTransf.setIdOperacion(moneda.getCodMoneda());
            nTransf.setDescripcion("* CAPITAL TRANSF EN " + fecha());
            nTransf.setCodCajaDestino(detallCajap.getTCaja().getCodCaja());
            nTransf.setTCaja(caja);
            nTransf.setTipo("CAPITAL MOVIDO");
            nTransf.setTOperacion(operacion);
            nTransf.setIdUser(myId + " TIPC14");
            nTransf.setIpUser(myIp);
            nTransf.setDateUser(DateUtil.getNOW_S());
            nTransf.setIdope(recep.getIdOperacion());
            dao.persist(nTransf);
            //TDetalleCaja mica = (TDetalleCaja) dao.load(TDetalleCaja.class, DateUtil.getDate(new Date()).replaceAll("/", "") + myCa + xmo.getCodMoneda() + "00");
            //mica.setMontoEntregado(mica.getMontoEntregado().add(monto));
            //mica.setMontoFinal(mica.getMontoFinal().subtract(monto));
            TLogFinance logME = (TLogFinance) dao.load(TLogFinance.class, "LOG" + myCa + xmo.getCodMoneda());
            logME.setMontoFinal(logME.getMontoFinal().subtract(monto));
            logME.setMontoEntregado(logME.getMontoEntregado().add(monto));
            logME.setActivoCajaybanco(logME.getActivoCajaybanco().subtract(monto));
            logME.setMonto(logME.getMonto().subtract(monto));
            logME.setPatrimonio(log.getPatrimonio().subtract(monto));
            dao.update();
            out.print("OK");
        } finally {
            out.close();
        }
    }

    private String fecha() {
        SimpleDateFormat formateador = new SimpleDateFormat("dd 'de' MMMM 'del' yyyy", new Locale("ES"));
        Date fechaDate = new Date();
        String fecha = formateador.format(fechaDate);
        return fecha.toUpperCase();
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
