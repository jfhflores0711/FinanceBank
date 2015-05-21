package org.finance.bank.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.finance.bank.bean.TDetalleSuma;
import org.finance.bank.bean.TSumaMoneda;
import org.finance.bank.model.dao.DAOGeneral;

/**
 *
 * @author admin
 */
public class SSumarCalcular extends HttpServlet {

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
            String cadena = "";
            if (request.getParameter("cadena") != null && !request.getParameter("cadena").equals("")) {
                cadena = request.getParameter("cadena").toString();
            }
            String codSumaMoneda = "";
            if (request.getParameter("m") != null && !request.getParameter("m").equals("")) {
                codSumaMoneda = request.getParameter("m").toString();
            }
            TSumaMoneda sm = (TSumaMoneda) dao.load(TSumaMoneda.class, codSumaMoneda);
            if (sm == null || cadena.equals("")) {
                out.print("No se Puede Actualizar ...");
                dao.cerrarSession();
                out.close();
                return;
            }
            List<TDetalleSuma> lds = ListdItem(cadena, dao, codSumaMoneda);
            System.out.println("lds = " + lds.size());
            out.print("ok");
        } finally {
            out.close();
        }
    }

    private static List<TDetalleSuma> ListdItem(String str, DAOGeneral dao, String idSumaMoneda) {
        String[] array = str.split(" ");
        List<TDetalleSuma> LdItem = new ArrayList<TDetalleSuma>();
        for (int i = 0; i < array.length; i++) {
            String[] part = array[i].split("=");
            TDetalleSuma s = (TDetalleSuma) dao.findAll("from TDetalleSuma where TSumaMoneda.idsumamoneda='" + idSumaMoneda + "' and TDenominacionMoneda.iddenominacionmoneda='" + part[0] + "'").get(0);
            s.setCantidad(Integer.parseInt(part[1]));
            dao.update();
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
