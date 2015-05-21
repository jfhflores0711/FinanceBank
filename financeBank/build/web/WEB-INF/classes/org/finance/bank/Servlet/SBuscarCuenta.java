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
import org.finance.bank.bean.TCuentaPersona;
import org.finance.bank.model.dao.DAOGeneral;

/**
 *
 * @author ronald
 */
public class SBuscarCuenta extends HttpServlet {

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
            String idpersona_e = "";
            if (request.getParameter("idpersona_e") != null && !request.getParameter("idpersona_e").equals("")) {
                idpersona_e = request.getParameter("idpersona_e").toString();
            }
            String cod_moneda = "";
            if (request.getParameter("cod_moneda") != null && !request.getParameter("cod_moneda").equals("")) {
                cod_moneda = request.getParameter("cod_moneda").toString();
            }
            String hql = "from TCuentaPersona where TPersona.idUserPk='" + idpersona_e + "' AND TMoneda.codMoneda='" + cod_moneda + "' AND saldo > 0 AND estado='ACTIVO'";
            List result = dao.findAll(hql);
            Iterator it = result.iterator();
            out.println("<table border='0' cellpadding='0' cellspacing='0' class='tabla'  width='100%'>");
            out.println("<tr>");
            out.println("<th >&nbsp;");
            out.println("</th>");
            out.println("<th align='left' >Nº CUENTA");
            out.println("</th>");
            out.println("<th align='left'>TIPO");
            out.println("</th>");
            out.println("<th align='left'>ESTADO");
            out.println("</th>");
            out.println("</tr>");
            int j = 0;
            while (it.hasNext()) {
                if (j % 2 == 0) {
                    out.println("<tr class='modo1'>");
                } else {
                    out.println("<tr class='modo2'>");
                }
                TCuentaPersona cuenta = (TCuentaPersona) it.next();
                out.println("<td ><input type='radio' name='cuenta' value='" + cuenta.getIdcuentapersona() + "' onclick=\"cuentaSaldo('" + cuenta.getSaldo() + "')\" /></td>");
                out.println("<td >" + cuenta.getNumCta() + "</td>");
                out.println("<td>" + cuenta.getTMoneda().getNombre() + "</td>");
                out.println("<td>" + cuenta.getEstado() + "</td>");
                out.println("</tr>");
                j++;
            }
            out.println("</table>");
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
