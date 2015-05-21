package org.finance.bank.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.finance.bank.bean.TDetalleCaja;
import org.finance.bank.bean.TLogFinance;
import org.finance.bank.bean.TOperacion;
import org.finance.bank.bean.TPersonaCaja;
import org.finance.bank.bean.TTipoOperacion;
import org.finance.bank.bean.TTransferenciaCaja;
import org.finance.bank.model.dao.DAOGeneral;
import org.finance.bank.util.DateUtil;
import org.finance.bank.util.numeroOperacion;

/**
 *
 * @author linux
 */
public class SRecepcion extends HttpServlet {

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
        String myIdP = session.getAttribute("USER_ID_PERSONA_CAJA").toString();
        String idForInitNewesttable = DateUtil.convertDateId(myIdP, SRecepcion.class.getSimpleName());
        try {
            String idTransferencia = null;
            if (request.getParameter("idTransferencia") != null && !request.getParameter("idTransferencia").equals("")) {
                idTransferencia = request.getParameter("idTransferencia").toString().trim();
            }
            if (idTransferencia == null) {
                out.print("ERROR");
                out.close();
                return;
            }
            TTransferenciaCaja tfcaja = (TTransferenciaCaja) dao.load(TTransferenciaCaja.class, idTransferencia);
            if (tfcaja.getEstado().equals("ABORTED")) {
                tfcaja.setDateUser(DateUtil.getNOW_S());
                tfcaja.setEstado("ACTIVO");
                dao.update();
            }
            String myFi = session.getAttribute("USER_CODFILIAL").toString();
            String myId = session.getAttribute("USER_ID").toString();
            String myIp = session.getAttribute("USER_IP").toString();
//            String idDC = iniDetalleCaja.detalleActivaCaja(tfcaja.getCodCajaDestino(), tfcaja.getTOperacion().getTMoneda().getCodMoneda());
            //TDetalleCaja detallecajaD = (TDetalleCaja) dao.load(TDetalleCaja.class, DateUtil.getDate(tfcaja.getTOperacion().getFd()).replaceAll("/", "") + tfcaja.getCodCajaDestino() + tfcaja.getTOperacion().getTMoneda().getCodMoneda() + "00");
            TLogFinance log = (TLogFinance) dao.load(TLogFinance.class, "LOG" + tfcaja.getCodCajaDestino() + tfcaja.getTOperacion().getTMoneda().getCodMoneda());
            TPersonaCaja c = (TPersonaCaja) dao.load(TPersonaCaja.class, myIdP);
            TTipoOperacion tipoRecep = (TTipoOperacion) dao.load(TTipoOperacion.class, "TIPC12");
            TOperacion recepcion = new TOperacion(idForInitNewesttable,
                    tipoRecep, c, tfcaja.getTOperacion().getTMoneda(), numeroOperacion.getNumber(myFi, tfcaja.getCodCajaDestino()),
                    DateUtil.getNOW_S(), "ACTIVO", myId,
                    myIp, DateUtil.getNOW_S());
//            recepcion.setDescripcion(iniDetalleCaja.detalleActivaCaja(tfcaja.getCodCajaDestino(), tfcaja.getTOperacion().getTMoneda().getCodMoneda()) + " RECEPCION");
            recepcion.setDescripcion(DateUtil.getDate(tfcaja.getTOperacion().getFd()).replaceAll("/", "") + tfcaja.getCodCajaDestino() + tfcaja.getTOperacion().getTMoneda().getCodMoneda() + "00" + " RECEPCION");
            //recepcion.setSaldofinal(detallecajaD.getMontoFinal());
            recepcion.setSaldofinal(log.getMontoFinal());
            recepcion.setSaldoFinalSinInteres(log.getMontoFinal());
            recepcion.setFd(new Date());
            dao.persist(recepcion);
            tfcaja.setIdope(recepcion.getIdOperacion());
            dao.update();
        } finally {
            out.print("FIN");
            out.close();
            dao.cerrarSession();
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
