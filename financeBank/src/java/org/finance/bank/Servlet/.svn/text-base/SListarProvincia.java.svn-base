
package org.finance.bank.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.finance.bank.bean.TProvincia;
import org.finance.bank.model.dao.DAOGeneral;

/**
 *
 * @author roger
 */
public class SListarProvincia extends HttpServlet {

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
        request.getSession(true);
        DAOGeneral dao = new DAOGeneral();
        try {
            String iddep = request.getParameter("iddep");
            List result = dao.findAll("from TProvincia pro where pro.TDepartamento='" + iddep + "'");
            Iterator it = result.iterator();
            if (result.size() > 0) {
                out.println("<select id='selprovincia' name='selprovincia' onchange='listarDistrito();'>");
                out.println("<option value='0'>(Seleccione Provincia)</option>");
                while (it.hasNext()) {
                    TProvincia provincia = (TProvincia) it.next();
                    out.println("<option value='" + provincia.getIdprovincia() + "'>" + provincia.getDescripcion() + "</option>");
                }
                out.println("</select>");
            } else {
                out.println("<select id='selprovincia' name='selprovincia' disabled>");
                out.println("<option value='0'>No hay Provincias</option>");
                out.println("</select>");
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
