package org.finance.bank.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.finance.bank.bean.TControlTipo;
import org.finance.bank.bean.TCuentaAcceso;
import org.finance.bank.model.dao.DAOGeneral;
import org.finance.bank.util.MD5;

/**
 *
 * @author ronald
 */
public class SAutorizarUsuario extends HttpServlet {

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
        String repuesta = "0";
        DAOGeneral dao = new DAOGeneral();
        HttpSession session = request.getSession(true);
        try {
            String l = request.getParameter("login");
            String c = request.getParameter("contrasenia");
            String keyring = MD5.encriptar(MD5.encriptar(c).toLowerCase() + l.toLowerCase());
            List result = dao.findAll("from TCuentaAcceso where keyring = '" + keyring + "' and estado='ACTIVO'");
            for (Iterator it = result.iterator(); it.hasNext();) {
                TCuentaAcceso dep = (TCuentaAcceso) it.next();
                TControlTipo ctp = (TControlTipo) dao.findAll("from TControlTipo where TPersona.idUserPk ='" + dep.getTPersona().getIdUserPk() + "' AND  estado ='ACTIVO' and TTipoPersona.descripcion='ADMINISTRADOR'").get(0);
//                if (ctp.getTTipoPersona().getDescripcion().equals("ADMINISTRADOR")) {
                    repuesta = "1";
                    session.setAttribute("ID_USER_ADMIN_AUTORIZER", ctp.getTPersona().getIdUserPk());
//                }
            }
            out.write(repuesta);
        } catch (Exception e) {
            out.write("0");
        } finally {
            dao.cerrarSession();
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
