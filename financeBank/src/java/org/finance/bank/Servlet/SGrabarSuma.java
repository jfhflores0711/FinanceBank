package org.finance.bank.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.finance.bank.bean.*;
import org.finance.bank.model.dao.DAOGeneral;
import org.finance.bank.model.iniDetalleCaja;
import org.finance.bank.util.DateUtil;

/**
 *
 * @author ronald
 */
public class SGrabarSuma extends HttpServlet {

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
        String idForInitNewestTable = DateUtil.convertDateId("NOUSER", SGrabarSuma.class.getSimpleName());
        String codcaja = session.getAttribute("USER_CODCAJA").toString();
        try {
            String cadena = "";
            if (request.getParameter("cadena") != null && !request.getParameter("cadena").equals("")) {
                cadena = request.getParameter("cadena").toString();
            }
            List lds = ListdItem(cadena, dao);
            TSumaMoneda sm = new TSumaMoneda();
            sm.setIdsumamoneda(idForInitNewestTable);
            sm.setEstado("" + codcaja + (((TDetalleSuma) lds.get(0)).getTDenominacionMoneda()).getTMoneda().getCodMoneda());
            sm.setIdoperacion("S" + (((TDetalleSuma) lds.get(0)).getTDenominacionMoneda()).getTMoneda().getSimbolo().substring(0, 1) + iniDetalleCaja.getIdSumaOrden(codcaja + (((TDetalleSuma) lds.get(0)).getTDenominacionMoneda()).getTMoneda().getCodMoneda()));
            dao.persist(sm);
            for (Iterator it = lds.iterator(); it.hasNext();) {
                TDetalleSuma object = (TDetalleSuma) it.next();
                TDetalleSuma s = new TDetalleSuma();
                s.setIddetallesuma(DateUtil.convertDateId("NOUSER", SGrabarSuma.class.getSimpleName()));
                s.setCantidad(object.getCantidad());
                s.setEstado("ACTIVO");
                s.setTDenominacionMoneda(object.getTDenominacionMoneda());
                s.setTSumaMoneda(sm);
                dao.persist(s);
            }
            session.setAttribute("CADENA_SUMA", session.getAttribute("CADENA_SUMA") + " " + sm.getIdsumamoneda());
            out.print("ok");
        } finally {
            dao.cerrarSession();
            out.close();
        }
    }

    private static List ListdItem(String str, DAOGeneral dao) {
        String[] array = str.split(" ");
        List LdItem = new ArrayList();
        for (int i = 0; i < array.length; i++) {
            String[] part = array[i].split("=");
            TDetalleSuma s = new TDetalleSuma();
            TDenominacionMoneda dm = (TDenominacionMoneda) dao.load(TDenominacionMoneda.class, part[0]);
            s.setTDenominacionMoneda(dm);
            s.setCantidad(Integer.parseInt(part[1]));
            LdItem.add(s);
        }
        return LdItem;
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
