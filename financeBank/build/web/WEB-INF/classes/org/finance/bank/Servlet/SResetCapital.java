package org.finance.bank.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.finance.bank.bean.TPersona;
import org.finance.bank.bean.TRegistroGiro;
import org.finance.bank.model.dao.DAOGeneral;

/**
 *
 * @author rvb
 */
public class SResetCapital extends HttpServlet {

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
        String idregistro = request.getParameter("idregistro");
        if (idregistro == null) {
            out.println("ok, no se autoriza tal petición");
            out.close();
            return;
        } else {
            TPersona personabuscada = null;
            DAOGeneral dao = new DAOGeneral();
            String cruc = "";
            String cgrupo = "-grupo=CLIENTE";
            String er = "";
            if (request.getParameter("er") != null && !request.getParameter("er").equals("")) {
                er = request.getParameter("er").toString();
            }
            if (er.equals("EH") || er.equals("RH")) {
                if (er.equals("EH")) {
                    er = "E";
                } else {
                    er = "R";
                }
            } else {
                if (er.equals("EF")) {
                    er = "E";
                } else {
                    er = "R";
                }
            }
            TRegistroGiro trg = (TRegistroGiro) dao.load(TRegistroGiro.class, idregistro);
            if (trg != null) {
                if (er.equals("E")) {
                    personabuscada = trg.getTPersona();
                } else {
                    personabuscada = (TPersona) dao.load(TPersona.class, trg.getIdUserPkDestino());
                }
            }
            boolean sinUser = personabuscada.getIdUserPk().equals("20110228202911739108");
            Logger.getLogger(SResetCapital.class.getName()).log(Level.INFO, er);
            try {
                    cgrupo = "-grupo=CLIENTE";
            } catch (IndexOutOfBoundsException ex) {
                Logger.getLogger(SResetCapital.class.getName()).log(Level.INFO, ""+ex.getMessage());
                cruc = er + "0";
                out.print(cruc);
                out.close();
                return;
            }
            String str = er + "idpersona=" + personabuscada.getIdUserPk()
                    + cruc
                    + "-" + er + "nombres=" + (sinUser ? (er.equals("E") ? "" + trg.getGirador().substring(0, trg.getGirador().indexOf("|")) : trg.getRecibidor().substring(0, trg.getRecibidor().indexOf("|"))) : personabuscada.getNombre())
                    + "-" + er + "apellidos=" + (sinUser ? (er.equals("E") ? "" + trg.getGirador().substring(trg.getGirador().indexOf("|") + 1, trg.getGirador().length()) : trg.getRecibidor().substring(trg.getRecibidor().indexOf("|") + 1, trg.getRecibidor().length())) : (personabuscada.getApellidos() == null ? "" : personabuscada.getApellidos()))
                    + "-" + er + "email=" + (personabuscada.getEmail() == null ? "" : personabuscada.getEmail())
                    + "-" + er + "ubigeo=" + (personabuscada.getUbigeo() == null ? "" : personabuscada.getUbigeo())
                    + "-" + er + "telefono=" + (personabuscada.getTelefono() == null ? "" : personabuscada.getTelefono())
                    + "-" + er + "celular=" + (personabuscada.getCelular() == null ? "" : personabuscada.getCelular())
                    + "-" + er + "direccion=" + (personabuscada.getDireccion() == null ? "" : personabuscada.getDireccion())
                    + cgrupo;
            out.println(str);
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
