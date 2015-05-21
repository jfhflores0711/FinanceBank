package org.finance.bank.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.finance.bank.bean.TControlModulo;
import org.finance.bank.model.dao.DAOGeneral;

/**
 *
 * @author ronald
 */
public class SActualizarModulos extends HttpServlet {

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
        try {
            DAOGeneral dao = new DAOGeneral();
            String CADENANODO = "";
            if (request.getParameter("todo") != null) {
                CADENANODO = request.getParameter("todo").toString();
            }
            List result = ListdItem(CADENANODO);
            Iterator it = result.iterator();
            while (it.hasNext()) {
                TControlModulo cm = (TControlModulo) it.next();
                TControlModulo ControlModulo = (TControlModulo) dao.load(TControlModulo.class, cm.getIdcontrolmodulo());
                ControlModulo.setEstado(cm.getEstado());
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

    private static List ListdItem(String str) {
        String[] array = str.split(" ");
        List LdItem = new ArrayList();
        for (int i = 0; i < array.length; i++) {
            String[] part = array[i].split("=");
            TControlModulo cmodulo = new TControlModulo();
            cmodulo.setIdcontrolmodulo(part[0]);
            cmodulo.setEstado(part[1]);
            LdItem.add(cmodulo);
        }
        return LdItem;
    }
}
