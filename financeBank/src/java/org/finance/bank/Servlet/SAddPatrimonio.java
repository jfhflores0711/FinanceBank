package org.finance.bank.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.finance.bank.bean.*;
import org.finance.bank.model.dao.DAOGeneral;
import org.finance.bank.util.*;

/**
 *
 * @author ronald
 */
public class SAddPatrimonio extends HttpServlet {

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
        String fi = (String) session.getAttribute("USER_CODFILIAL");
        String myCaja = session.getAttribute("USER_CODCAJA").toString();
        String myId = session.getAttribute("USER_ID").toString();
        String myIp = session.getAttribute("USER_IP").toString();
        String myPC = session.getAttribute("USER_ID_PERSONA_CAJA").toString();
        DAOGeneral dg = new DAOGeneral();
        String idForInitNewestTable = DateUtil.convertDateId(myPC, SAddPatrimonio.class.getSimpleName());
        try {
            String addpatrimonio = "";
            if (request.getParameter("addpatrimonio") != null && !request.getParameter("addpatrimonio").equals("")) {
                addpatrimonio = request.getParameter("addpatrimonio").toString();
            }
            String codmoneda = "";
            if (request.getParameter("codmoneda") != null && !request.getParameter("codmoneda").equals("")) {
                codmoneda = request.getParameter("codmoneda").toString();
            }
            if (codmoneda == null) {
                return;
            }
            BigDecimal monto = new BigDecimal(addpatrimonio.replace(" ", ""));
            TPersonaCaja pcaja = (TPersonaCaja) dg.load(TPersonaCaja.class, myPC);
            TOperacion operacion = new TOperacion();
            operacion.setIdOperacion(idForInitNewestTable);
            operacion.setDescripcion(DateUtil.getNOW_S() + " PATRIMONIO AUMENTADO");
            operacion.setFecha(formatFecha.get());
            TTipoOperacion tipoOper = (TTipoOperacion) dg.load(TTipoOperacion.class, "TIPC13");
            operacion.setTTipoOperacion(tipoOper);
            operacion.setEstado("ACTIVO");
            operacion.setNumeroOperacion(numeroOperacion.getNumber(fi, myCaja));
            TMoneda moneda = (TMoneda) dg.load(TMoneda.class, codmoneda);
            operacion.setTMoneda(moneda);
            operacion.setIdUser(myId + " SAddPatrimonio");
            operacion.setIpUser(myIp);
            operacion.setDateUser(formatFecha.get());
            operacion.setTPersonaCaja(pcaja);
            operacion.setSaldofinal(monto);
            operacion.setSaldoFinalSinInteres(monto);
            operacion.setFd(new Date());
            dg.persist(operacion);
            TCaja caja = (TCaja) dg.load(TCaja.class, myCaja);
            TTransferenciaCaja nTransf = new TTransferenciaCaja();
            nTransf.setIdtransferenciacaja(idForInitNewestTable);
            nTransf.setFecha(DateUtil.getNOW_S());
            nTransf.setMonto(monto);
            nTransf.setIdOperacion(codmoneda);
            nTransf.setDescripcion("* CAPITAL AGREGADO " + DateUtil.getNOW_S());
            nTransf.setCodCajaDestino(caja.getCodCaja());
            nTransf.setTCaja(caja);
            nTransf.setTipo("CAPITAL SOCIAL");
            nTransf.setTOperacion((TOperacion) dg.load(TOperacion.class, operacion.getIdOperacion()));
            nTransf.setIdUser(myId + " Patrimonio");
            nTransf.setIpUser(myIp);
            nTransf.setEstado("ACTIVO");
            nTransf.setDateUser(DateUtil.getNOW_S());
            nTransf.setIdope(idForInitNewestTable);
            dg.persist(nTransf);
            //TDetalleCaja xd = iniDetalleCaja.detalleActivaCaja(myCaja, codmoneda, DateUtil.getDate(new Date()));
            TLogFinance xd = (TLogFinance) dg.load(TLogFinance.class, "LOG" + myCaja + moneda.getCodMoneda());
            xd.setMontoRecibido(xd.getMontoRecibido().add(monto));
            xd.setMontoFinal(xd.getMontoFinal().add(monto));
            xd.setActivoCajaybanco(xd.getActivoCajaybanco().add(monto));
            xd.setMonto(xd.getMonto().add(monto));
            xd.setPatrimonio(xd.getPatrimonio().add(monto));
            dg.update();
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
        Logger.getLogger(SAddPatrimonio.class.getName()).log(Level.INFO, "response = Get");
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
        Logger.getLogger(SAddPatrimonio.class.getName()).log(Level.INFO, "response = post");
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Agregar Patrimonio";
    }// </editor-fold>
}
