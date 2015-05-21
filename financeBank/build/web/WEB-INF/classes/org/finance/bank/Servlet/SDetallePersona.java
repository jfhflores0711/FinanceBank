/*
 * Admin
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
import org.finance.bank.bean.TCaja;
import org.finance.bank.bean.TCategoriaPersona;
import org.finance.bank.bean.TControlTipo;
import org.finance.bank.bean.TCuentaAcceso;
import org.finance.bank.bean.TFilial;
import org.finance.bank.bean.TPersona;
import org.finance.bank.bean.TPersonaCaja;
import org.finance.bank.bean.TTipoPersona;
import org.finance.bank.model.dao.DAOGeneral;

/**
 *
 * @author ronald
 */
public class SDetallePersona extends HttpServlet {

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
            String IDPERSONA = "";
            if (request.getParameter("i") != null) {
                IDPERSONA = request.getParameter("i").toString();
            }
            List result;
            TPersona persona = (TPersona) dao.load(TPersona.class, IDPERSONA);
            out.println("<table border='0' width='100%'><tr><td align='right'>");
            out.println("<font color='#385B88' style='font-size:12px'><b>Categoria&nbsp;</b></font>");
            out.println("</td><td align='left'>");
            result = dao.findAll("from TCategoriaPersona");
            Iterator it5 = result.iterator();
            int j = 0;
            String check = "";
            while (it5.hasNext()) {
                if (j == 0) {
                    check = "checked";
                }
                TCategoriaPersona cate = (TCategoriaPersona) it5.next();
                out.println(cate.getDescripcion() + "<input type='radio' name='categoria' value='" + cate.getIdcategoriapersona() + "' " + check + " /> &nbsp;");
                check = "";
                j++;
            }
            out.println("</td></tr>");
            out.println("<tr><td align='right'>");
            out.println("<font color='#385B88' style='font-size:12px'><b>D.N.I&nbsp;</b></font>");
            out.println("</td><td align='left'>");
            out.println("<input  value='" + persona.getDocIdentidad() + "' id='d' type='text' name='dni' onkeypress='return soloNumeroTelefonico(event);' maxlength='8'/><font color='red'>*</font>");
            out.println("</td></tr>");
            out.println("<tr><td align='right'>");
            out.println("<font color='#385B88' style='font-size:12px'><b>R.U.C&nbsp;</b></font>");
            out.println("</td><td align='left'>");
            out.println("<input id='r' type='text' name='ruc' value='" + persona.getRuc() + "'  onkeypress='return soloNumeroTelefonico(event);' maxlength='11'/>");
            out.println("</td></tr>");
            out.println("<tr><td align='right'>");
            out.println("<font color='#385B88' style='font-size:12px'><b>Nombres&nbsp;</b></font>");
            out.println("</td><td align='left'>");
            out.println("<input  value='" + persona.getNombre() + "' id='n' type='text' name='nombres' maxlength='50' /><font color='red'>*</font>");
            out.println("</td></tr>");
            out.println("<tr><td align='right'>");
            out.println("<font color='#385B88' style='font-size:12px'><b>Apellidos&nbsp;</b></font>");
            out.println("</td><td align='left'>");
            out.println("<input value='" + persona.getApellidos() + "' id='a' type='text' name='apellidos' maxlength='50' /><font color='red'>*</font>");
            out.println("</td></tr>");
            out.println("<tr><td align='right'>");
            out.println("<font color='#385B88' style='font-size:12px'><b>Ubigeo&nbsp;</b></font>");
            out.println("</td><td align='left'>");
            out.println("<input id='u' type='text' name='ubigeo' value='" + persona.getUbigeo() + "' onkeypress='return soloNumeroTelefonico(event);'  maxlength='10'/><font color='red'>*</font>");
            out.println("</td></tr>");
            out.println("<tr><td align='right'>");
            out.println("<font color='#385B88' style='font-size:12px'><b>Direccion&nbsp;</b></font>");
            out.println("</td><td align='left'>");
            out.println("<input id='di' type='text' name='direccion' value='" + persona.getDireccion() + "'  maxlength='100'/>");
            out.println("</td></tr>");
            out.println("<tr><td colspan='2' style='font-size: 8px;'><b>&nbsp; Datos Adicionales</b></td></tr>");
            out.println("<tr><td align='right'>");
            out.println("<font color='#385B88' style='font-size:12px'><b>Email&nbsp;</b></font>");
            out.println("</td><td align='left'>");
            out.println("<input id='e' type='text' name='email'  value='" + persona.getEmail() + "'  maxlength='50'/>");
            out.println("</td></tr>");
            out.println("<tr><td align='right'>");
            out.println("<font color='#385B88' style='font-size:12px'><b>Tel&eacute;fono&nbsp;</b></font>");
            out.println("</td><td align='left'>");
            out.println("<input id='t' type='text' name='telefono'  value='" + persona.getTelefono() + "' onkeypress='return soloNumeroTelefonico(event);' maxlength='10'  />");
            out.println("</td></tr>");
            out.println("<tr><td align='right'>");
            out.println("<font color='#385B88' style='font-size:12px'><b>Celular&nbsp;</b></font>");
            out.println("</td><td align='left'>");
            out.println("<input id='c' type='text' name='celular'  value='" + persona.getCelular() + "' onkeypress='return soloNumeroTelefonico(event);' maxlength='12' />");
            out.println("</td></tr>");
            out.println("</table>");
            out.println("<br><hr><br>");
            out.println("<table border='0' width='100%'><tr><td align='right'>");
            out.println("<font color='#385B88' style='font-size:12px'><b>Usuario&nbsp;</b></font>");
            out.println("</td><td align='left'>");
            List l1 = dao.findAll("from TCuentaAcceso where TPersona.idUserPk ='" + persona.getIdUserPk() + "'");
            int sl1 = l1.size();
            TCuentaAcceso cuenta;
            if (sl1 > 0) {
                cuenta = (TCuentaAcceso) l1.get(0);
                out.println("<input id='l' type='text' name='login' value='" + cuenta.getLogin() + "' maxlength='10' /><font color='red'>*</font>");
                out.println("</td></tr>");
                out.println("<tr><td align='right'>");
                out.println("<font color='#385B88' style='font-size:12px'><b>Contrase&ntilde;a&nbsp;</b></font>");
                out.println("</td><td align='left'>");
                out.println("<input id='co' type='password' name='contrasenia' value='" + cuenta.getContrasenia() + "' maxlength='10' /><font color='red'>*</font>");
                out.println("</td></tr>");
                out.println("<tr><td align='right'>");
                out.println("<font color='#385B88' style='font-size:12px'><b>IP Acceso</b></font>");
                out.println("</td><td align='left'>");
                out.println("<input id='ip' type='text' name='ipacceso' value='" + cuenta.getIpAcceso() + "'/><font color='red'>*</font>");
                out.println("</td></tr>");
                out.println("<tr><td align='right' colspan='2'>");
                out.println("<font color='#385B88' style='font-size:9px'><b>Ejem: 192.168.0.XX,*</b></font>");
                out.println("</td></tr>");
                out.println("<tr><td colspan='2'>");
            } else {
                out.println("<input id='l' type='text' name='login' value='' maxlength='10' /><font color='red'>*</font>"
                        + "</td></tr><tr><td align='right'><font color='#385B88' style='font-size:12px;'><b>Contrase&ntilde;a&nbsp;</b></font>"
                        + "</td><td align='left'><input id='co' type='password' name='contrasenia' value='' maxlength='10'/><font color='red'>*</font>"
                        + "</td></tr><tr><td align='right'><font color='#385B88' style='font-size:12px;'><b>IP Acceso</b></font>"
                        + "</td><td align='left'><input id='ip' type='text' name='ipacceso' value='*' /><font color='red'>*</font>"
                        + "</td></tr><tr><td align='right' colspan='2'><font color='#385B88' style='font-size:9px;'><b>Ejem: 192.168.0.XX,*,etc</b></font>"
                        + "</td> </tr><tr><td colspan='2'>");
            }
            out.println("<b>&nbsp;</b>");
            out.println("</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td align='right'>");
            out.println("<font color='#385B88' style='font-size:12px'><b>Grupo&nbsp;</b></font>");
            out.println("</td>");
            out.println("<td align='left'>");
            out.println("<select name='grupo' id='gr'>");
            List l2 = dao.findAll("from TControlTipo where TPersona.idUserPk ='" + persona.getIdUserPk() + "'");
            int sl2 = l2.size();
            if (sl2 > 0) {
                TControlTipo pgrupo = (TControlTipo) l2.get(0);
                result = dao.findAll("from TTipoPersona");
                Iterator it2 = result.iterator();
                while (it2.hasNext()) {
                    TTipoPersona grupo = (TTipoPersona) it2.next();//
                    out.println("<option value='" + grupo.getIdtipopersona() + "' ");
                    if (pgrupo.getTTipoPersona().getIdtipopersona().equals(grupo.getIdtipopersona())) {
                        out.println("selected");
                    }
                    out.println(">" + grupo.getDescripcion() + "</option>");
                }
            } else {
                result = dao.findAll("from TTipoPersona");
                Iterator it2 = result.iterator();
                while (it2.hasNext()) {
                    TTipoPersona grupo = (TTipoPersona) it2.next();
                    out.println("<option value='" + grupo.getIdtipopersona() + "' >" + grupo.getDescripcion() + "</option>");
                }
            }
            out.println("</select></td></tr>");
            out.print("<tr><td align='right'><font color='#385B88' style='font-size:12px'><b>Filial&nbsp;</b></font>");
            out.print("</td><td align='left'><select id='selmiFilial' name='filial' onclick='mostrarcaja();'>");
            String codfilial = "";
            result = dao.findAll("from TFilial where estado='ACTIVO'");
            Iterator it4 = result.iterator();
            List l3 = dao.findAll("from TPersonaCaja where TPersona.idUserPk ='" + persona.getIdUserPk() + "'");
            int sl3 = l3.size();
            if (sl3 > 0) {
                TPersonaCaja pcaja1 = (TPersonaCaja) l3.get(0);
                while (it4.hasNext()) {
                    TFilial fi = (TFilial) it4.next();
                    out.println("<option value='" + fi.getCodFilial() + "'");
                    if (pcaja1.getTCaja().getTFilial().getCodFilial().equals(fi.getCodFilial())) {
                        out.println(" selected");
                        codfilial = fi.getCodFilial();
                    }
                    out.print(" >" + fi.getNombre() + "</option>");
                }
            } else {
                result = dao.findAll("from TFilial where estado='ACTIVO'");
                Iterator ita = result.iterator();
                int ii = 0;
                while (ita.hasNext()) {
                    TFilial fi = (TFilial) ita.next();
                    out.println("<option value='" + fi.getCodFilial() + "' >" + fi.getNombre() + "</option>");
                    if (ii == 0) {
                        codfilial = fi.getCodFilial();
                    }
                    ii++;
                }
            }
            out.print("</select></td></tr>");
            out.println("<tr>");
            out.println("<td align='right'>");
            out.println("<font color='#385B88' style='font-size:12px'><b>Caja&nbsp;</b></font>");
            out.println("</td>");
            out.println("<td align='left'><div id='fiCaja'><select id='selcaja' name='caja'>");
            List xl = dao.findAll("from TPersonaCaja where TPersona.idUserPk ='" + persona.getIdUserPk() + "'");
            int sxl = xl.size();
            if (sxl > 0) {
                TPersonaCaja pcaja = (TPersonaCaja) xl.get(0);
                result = dao.findAll("from TCaja ca where ca.TFilial='" + codfilial + "' and ca.estado='ACTIVO' order by codCaja");
                Iterator it3 = result.iterator();
                if (result.size() > 0) {
                    while (it3.hasNext()) {
                        TCaja caja3 = (TCaja) it3.next();
                        out.println("<option value='" + caja3.getCodCaja() + "'");
                        if (pcaja.getTCaja().getCodCaja().equals(caja3.getCodCaja())) {
                            out.println("selected");
                        }
                        out.println(" >" + caja3.getNombreCaja() + "</option>");
                    }
                } else {
                    out.println("<option value='0'>(NO HAY FILIAL ACTIVA)</option>");
                }
            } else {
                result = dao.findAll("from TCaja ca where ca.TFilial='" + codfilial + "' and ca.estado='ACTIVO' order by codCaja");
                if (result.size() > 0) {
                    Iterator it3 = result.iterator();
                    while (it3.hasNext()) {
                        TCaja caja3 = (TCaja) it3.next();
                        out.println("<option value='" + caja3.getCodCaja() + "' >" + caja3.getNombreCaja() + "</option>");
                    }
                    out.println("<option value='0'>SIN ASIGNAR</option>");
                } else {
                    out.println("<option value='0'>(SIN ASIGNAR CAJA)</option>");
                }
            }
            out.println("</select>");
            out.println("</div></td>");
            out.println("</tr>");
            out.println("</table>");
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
