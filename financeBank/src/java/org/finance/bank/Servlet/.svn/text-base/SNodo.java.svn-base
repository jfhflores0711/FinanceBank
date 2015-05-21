/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.finance.bank.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.finance.bank.bean.TControlModulo;
import org.finance.bank.bean.TModulo;
import org.finance.bank.model.dao.DAOGeneral;

/**
 *
 * @author ronald
 */
public class SNodo extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        request.getSession(true);
        DAOGeneral dao = new DAOGeneral();
        try {
            String IDGRUPO = "";
            if (request.getParameter("idgrupo") != null) {
                IDGRUPO = request.getParameter("idgrupo").toString();
            }
            String sql = "from TControlModulo where TTipoPersona.idtipopersona ='" + IDGRUPO + "'";
            List result2 = dao.findAll(sql);
            Iterator it2 = result2.iterator();
            int i = 0;
            while (it2.hasNext()) {
                TControlModulo Cmodulo = (TControlModulo) it2.next();
                TModulo modulo = (TModulo) dao.load(TModulo.class, Cmodulo.getTModulo().getIdmodulo());
                String estado = "";
                if (Cmodulo.getEstado().equals("1")) {
                    estado = "checked";
                } else {
                    estado = "";
                }
                out.println("<li><a  >" + modulo.getDescripcion() + "</a>"
                        + "<input type='checkbox' name='checkboxModulo' "
                        + " id='checkboxModulo'"
                        + estado
                        + " value='" + Cmodulo.getIdcontrolmodulo() + "' />"
                        + "</li>");
                i++;
            }
            out.println("</ul>");
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
