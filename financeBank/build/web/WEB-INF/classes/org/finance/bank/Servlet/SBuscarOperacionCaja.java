package org.finance.bank.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.finance.bank.bean.TOperacion;
import org.finance.bank.model.dao.DAOGeneral;

/**
 *
 * @author roger
 */
public class SBuscarOperacionCaja extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        DAOGeneral dao = new DAOGeneral();
        try {
            String numOperacion = request.getParameter("numOP");
            Logger.getLogger(SBuscarOperacionCaja.class.getName()).log(Level.INFO, "numOperacion:" + numOperacion);
            String hql = "from TOperacion op where op.numeroOperacion like '%" + numOperacion + "'";
            List result = dao.findAll(hql);
            Iterator it = result.iterator();
            Logger.getLogger(SBuscarOperacionCaja.class.getName()).log(Level.INFO, "id  Descripcion");
            out.println("<table id='tablaOperacion' class='tabla' border='0'>");
            out.println("<tr>");
            out.println("<th style='display:none'>-.-</th>");
            out.println("<th>Num. Operaci&oacute;n</th>");
            out.println("<th>Fecha de Registro</th>");
            out.println("<th>Tipo Operaci&oacute;n</th>");
            out.println("<th>Moneda</th>");
            out.println("<th>Descripci&oacute;n</th>");
            out.println("<th>Estado</th>");
            out.println("<th>Usuario</th>");
            out.println("<th>Fecha Extorno</th>");
            out.println("<th>Admin Extorno</th>");
            out.println("</tr>");
            int i = 0;
            Logger.getLogger(SBuscarOperacionCaja.class.getName()).log(Level.INFO, "antes de iterador");
            while (it.hasNext()) {
                Logger.getLogger(SBuscarOperacionCaja.class.getName()).log(Level.INFO, "despues de iterador");
                TOperacion oper = (TOperacion) it.next();
                String par = "";
                i = i + 1;
                if (i % 2 == 0) {
                    par = "modo2";
                } else {
                    par = "modo1";
                }
                out.println("<tr class=" + par + ">");
                out.println("<td id='r'" + i + " style='display:none'>" + oper.getIdOperacion() + "</td>");
                out.println("<td>" + oper.getNumeroOperacion() + "</td>");
                out.println("<td>" + oper.getFecha() + "</td>");
                out.println("<td>" + oper.getTTipoOperacion().getNombre() + "</td>");
                out.println("<td>" + oper.getTMoneda().getNombre() + "</td>");
                out.println("<td>" + oper.getDescripcion() + "</td>");
                out.println("<td>" + oper.getEstado() + "</td>");
                out.println("<td>" + oper.getTPersonaCaja().getTPersona().getNombre() + " " + oper.getTPersonaCaja().getTPersona().getApellidos() + "</td>");
                out.println("<td>" + (oper.getFechaExtornacion() == null ? "" : oper.getFechaExtornacion()) + "</td>");
                out.println("<td>" + (oper.getIdAdminExtorno() == null ? "" : oper.getIdAdminExtorno()) + "</td>");
                out.println("</tr>");
            }
            out.println("<tr>");
            out.println("<td id='tdIdAdmin'>" + session.getAttribute("USER_ID") + "</td>");
            out.println("</tr>");
            out.println("</table>");
            Logger.getLogger(SBuscarOperacionCaja.class.getName()).log(Level.INFO, "cerrando table");
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
