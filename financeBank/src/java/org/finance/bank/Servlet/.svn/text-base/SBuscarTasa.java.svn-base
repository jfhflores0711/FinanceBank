package org.finance.bank.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.finance.bank.bean.TMoneda;
import org.finance.bank.bean.TTasa;
import org.finance.bank.bean.TTipoCambio;
import org.finance.bank.model.dao.DAOGeneral;
import org.finance.bank.util.DateUtil;
import org.finance.bank.util.HibernateUtil;

/**
 *
 * @author ronald
 */
public class SBuscarTasa extends HttpServlet {

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
        String id = (session.getAttribute("USER_ID") == null ? "ERROR" : session.getAttribute("USER_ID").toString());
        String idForInitNewestTable = DateUtil.convertDateId(id, SBuscarTasa.class.getSimpleName());
        try {
            String mIni = "";
            if (request.getParameter("mIni") != null && !request.getParameter("mIni").equals("")) {
                mIni = request.getParameter("mIni").toString();
            }
            String mFin = "";
            if (request.getParameter("mFin") != null && !request.getParameter("mFin").equals("")) {
                mFin = request.getParameter("mFin").toString();
            }
            String especial = "";
            if (request.getParameter("especial") != null && !request.getParameter("especial").equals("")) {
                especial = request.getParameter("especial").toString();
            }
            String tipoTasa = "";
            String tE = "TASA ESPECIAL";
            String tM = "TASA MERCADO";
            if (especial.equals("true")) {
                tipoTasa = tE;
            } else {
                tipoTasa = tM;
            }
            String hql = "from TTasa where estado ='ACTIVO'"
                    + " AND TTipoCambio.TMoneda.codMoneda ='" + mIni + "' "
                    + " AND tipoTasa ='" + tipoTasa + "' "
                    + " AND TTipoCambio.codMonedaA ='" + mFin + "' ";
            List lt = dao.findAll(hql);
            TTasa xTa;
            if (lt.isEmpty()) {
                TMoneda xxM0 = (TMoneda) dao.load(TMoneda.class, mIni);
                TTipoCambio xTC;
                if (xxM0.getTTipoCambios().isEmpty()) {
                    TTipoCambio newTC = new TTipoCambio();
                    newTC.setIdtipocambio(idForInitNewestTable);
                    newTC.setCodMonedaA(mFin);
                    newTC.setDateUser(DateUtil.getNOW_S());
                    newTC.setDescripcion("CONVERTIR DE " + mIni + " A " + mFin);
                    newTC.setEstado("ACTIVO");
                    newTC.setFecha(DateUtil.getNOW_S());
                    newTC.setIdUser(id);
                    newTC.setIpUser(request.getRemoteAddr());
                    newTC.setTMoneda(xxM0);
                    dao.persist(newTC);
                    xTC = newTC;
                } else {
                    xTC = (TTipoCambio) xxM0.getTTipoCambios().iterator().next();
                }
                TTasa xT = new TTasa();
                xT.setIdtasa(idForInitNewestTable);
                xT.setDateUser(DateUtil.getNOW_S());
                xT.setEstado("ACTIVO");
                xT.setFConversion(BigDecimal.ONE);
                xT.setFecha(DateUtil.getNOW_S());
                xT.setIdUser(id);
                xT.setIpUser(request.getRemoteAddr());
                xT.setTTipoCambio(xTC);
                xT.setTipoTasa(tipoTasa);
                dao.persist(xT);
                xTa = xT;
            } else {
                xTa = (TTasa) lt.get(0);
            }
            TTipoCambio tipoCambio = xTa.getTTipoCambio();
            session.setAttribute("ID_TIPOCAMBIO", tipoCambio.getIdtipocambio());
            out.println(xTa.getFConversion().toString());
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
