package org.finance.bank.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.finance.bank.bean.TCuentaPersona;
import org.finance.bank.bean.TPersona;
import org.finance.bank.model.dao.DAOGeneral;

/**
 *
 * @author roger
 */
public class SMostrarPersona extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        request.getSession(true);
        DAOGeneral dao = new DAOGeneral();
        try {
            String tipoBusqueda = request.getParameter("b");
            String valor = request.getParameter("c");
            String catrus = request.getParameter("a");
            valor = valor.toUpperCase();
            if (tipoBusqueda == null) {
                out.print("&nbsp;");
                out.close();
                return;
            }
            if (catrus != null) {
                List resultN = dao.findAll("from TPersona pe where not(pe.ruc='DISABLED' or pe.docIdentidad='DISABLED') and estado='ACTIVO' and TCategoriaPersona.descripcion='NATURAL' order by pe.apellidos, pe.nombre ");
                List resultJ = dao.findAll("from TPersona pe where not(pe.ruc='DISABLED' or pe.docIdentidad='DISABLED') and estado='ACTIVO' and TCategoriaPersona.descripcion='JURIDICA' order by pe.nombre ");
                out.println("<div id='menu'>");
                out.println("<ul>");
                int i = 0;
                if (catrus.length() > 3) {
                    catrus = catrus.substring(1);
                }
                for (Iterator it1 = resultJ.iterator(); it1.hasNext();) {
                    TPersona persona = (TPersona) it1.next();
                    Set cp = persona.getTCuentaPersonas();
                    if (cp.size() > 0) {
                        Iterator ii = cp.iterator();
                        while (ii.hasNext()) {
                            TCuentaPersona cpx = (TCuentaPersona) ii.next();
                            if (!cpx.getEstado().equals("INACTIVO") && cpx.getNumCta().substring(2, 5).equals(catrus)) {
                                i = i + 1;
                                out.println("<li id='li" + i + "'><a id='a" + i + "' onclick=\"selected('" + i + "'); verDetalleCatalogo('" + persona.getIdUserPk() + "');\">" + persona.getNombre() + " " + persona.getApellidos());
                                out.println("</a></li>");
                                break;
                            }
                        }
                    }
                }
                for (Iterator it = resultN.iterator(); it.hasNext();) {
                    TPersona persona = (TPersona) it.next();
                    Set cp = persona.getTCuentaPersonas();
                    if (cp.size() > 0) {
                        Iterator ii = cp.iterator();
                        while (ii.hasNext()) {
                            TCuentaPersona cpx = (TCuentaPersona) ii.next();
                            if (!cpx.getEstado().equals("INACTIVO") && cpx.getNumCta().substring(2, 5).equals(catrus)) {
                                i = i + 1;
                                out.println("<li id='li" + i + "'><a id='a" + i + "' onclick=\"selected('" + i + "'); verDetalleCatalogo('" + persona.getIdUserPk() + "');\">" + persona.getApellidos() + ", " + persona.getNombre());
                                out.println("</a></li>");
                                break;
                            }
                        }
                    }
                }
                if (i == 0) {
                    out.println("<li id='li'>No se encontraron ning&uacute;n<br> cliente para esta filial</li>");
                }
                out.println("</ul>");
                out.println("</div>");
                out.close();
                return;
            }
            String hqlN = "from TPersona pe ";
            String hqlJ = "from TPersona pe ";
            if ("NOMBRE".equals(tipoBusqueda)) {
                hqlN += "where pe.nombre like '" + valor + "%'";
                hqlJ += "where pe.nombre like '" + valor + "%'";
            } else if ("DNI".equals(tipoBusqueda)) {
                hqlN += "where pe.docIdentidad like '" + valor + "%'";
                hqlJ += "where pe.docIdentidad like '" + valor + "%'";
            } else {
                hqlN += "where pe.apellidos like '" + valor + "%'";
                hqlJ += "where pe.apellidos like '" + valor + "%'";
            }
            List resultN = dao.findAll(hqlN + " and not(pe.ruc='DISABLED' or pe.docIdentidad='DISABLED') and pe.TCategoriaPersona.descripcion='NATURAL' order by pe.apellidos,pe.nombre ");
            List resultJ = dao.findAll(hqlJ + " and not(pe.ruc='DISABLED' or pe.docIdentidad='DISABLED') and pe.TCategoriaPersona.descripcion='JURIDICA' order by pe.nombre ");
            out.println("<div id='menu'>");
            out.println("<ul>");
            int i = 0;
            for (Iterator it1 = resultJ.iterator(); it1.hasNext();) {
                TPersona persona = (TPersona) it1.next();
                i = i + 1;
                out.println("<li id='li" + i + "'><a id='a" + i + "' onclick=\"selected('" + i + "'); verDetalleCatalogo('" + persona.getIdUserPk() + "');\">" + persona.getNombre() + ", RUC " + persona.getRuc());
                out.println("</a></li>");

            }
            for (Iterator it = resultN.iterator(); it.hasNext();) {
                TPersona persona = (TPersona) it.next();
                i = i + 1;
                out.println("<li id='li" + i + "'><a id='a" + i + "' onclick=\"selected('" + i + "'); verDetalleCatalogo('" + persona.getIdUserPk() + "');\">" + persona.getApellidos() + ", " + persona.getNombre());
                out.println("</a></li>");
            }
            if (i == 0) {
                out.println("<li id='li" + i + "'>SIN RESULTADOS</li>");
            }
            out.println("</ul>");
            out.println("</div>");
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
