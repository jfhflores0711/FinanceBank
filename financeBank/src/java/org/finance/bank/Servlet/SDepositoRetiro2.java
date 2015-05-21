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
import org.finance.bank.bean.TPersona;
import org.finance.bank.model.dao.DAOGeneral;

/**
 *
 * @author roger
 */
public class SDepositoRetiro2 extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        request.getSession(true);
        DAOGeneral dao = new DAOGeneral();
        try {
            String contenido = request.getParameter("a");
            if (contenido != null) {
                contenido = contenido.toUpperCase();
            } else {
                contenido = "ZEROBUSQUEDA";
            }
            String tipoBusqueda = request.getParameter("b");
            if (tipoBusqueda == null) {
                out.println("<center>");
                out.println("<fieldset style='border-width:3px'>");
                out.println("<legend><b>RESULTADO DE BUSQUEDA:</b></legend>");
                out.println("<table id='tablapersonas' class='tabla'>");
                out.println("<tr class='modo1'>");
                out.println("<td style='color:red;font-size: 15px'>NO SE ENCONTRO NINGUNA PERSONA, PRUEBE CON OTROS PARAMETROS</td>");
                out.println("</tr>");
                out.println("</table>");
                out.println("</fieldset>");
                out.println("</center>");
                dao.cerrarSession();
                out.close();
                return;
            }
            int i = 0;
            String par, sql = "";
            if (!"NUMERO DE CUENTA".equals(tipoBusqueda)) {
                if ("NOMBRE".equals(tipoBusqueda)) {
                    sql = "from TPersona where nombre like '%" + contenido + "%' AND (estado='ACTIVO') and NOT ruc='DISABLED'";
                } else if ("APELLIDO".equals(tipoBusqueda)) {
                    sql = "from TPersona where apellidos like '%" + contenido + "%' AND (estado='ACTIVO') and NOT ruc='DISABLED'";
                } else if (tipoBusqueda.equals("DNI")) {
                    sql = "from TPersona where docIdentidad like '" + contenido + "%' AND (estado='ACTIVO') and NOT ruc='DISABLED'";
                } else {
                    sql = "from TPersona where ruc like '" + contenido + "%' AND (estado='ACTIVO') AND NOT docIdentidad='DISABLED'";
                }
                List lisnombre = dao.findAll(sql);
                if (lisnombre.size() >= 1) {
                    out.println("<fieldset style='border-width:3px'>");
                    out.println("<legend><b>RESULTADO DE BUSQUEDA:</b></legend>");
                    out.println("<table border='0' id='tablapersonas' class='tabla'>");
                    out.println("<tr>");
                    out.println("<th>-.-</th>");
                    out.println("<th style='display:none'>idPersona</th>");
                    out.println("<th>Nombre</th>");
                    out.println("<th>Apellidos</th>");
                    out.println("<th>DNI</th>");
                    out.println("<th>RUC</th>");
                    out.println("<th>Ubigeo</th>");
                    out.println("<th>Celular</th>");
                    out.println("<th>Email</th>");
                    out.println("</tr>");
                    for (Iterator it = lisnombre.iterator(); it.hasNext();) {
                        TPersona persona = (TPersona) it.next();
                        if(persona.getTCuentaPersonas().isEmpty()){
                            continue;
                        }
                        par = "";
                        i = i + 1;
                        if (i % 2 == 0) {
                            par = "modo2";
                        } else {
                            par = "modo1";
                        }
                        out.println("<tr class='" + par + "'>");
                        out.println("<td ><input type='radio' id='rdPersona" + i + "' name='rdPersona' value='" + i + "' onclick='mostrarcuentadetalle(\"" + persona.getIdUserPk() + "\"," + i + ")'/></td>");
                        out.println("<td id='tdIdPersona" + i + "' style='display:none' >" + persona.getIdUserPk() + "</td>");
                        out.println("<td id='ttdN" + i + "'>" + persona.getNombre() + "</td>");
                        out.println("<td id='ttdA" + i + "'>" + persona.getApellidos() + " </td>");
                        out.println("<td id='ttdDNI" + i + "' >" + persona.getDocIdentidad() + "</td>");
                        out.println("<td id='ttdRUC" + i + "'>" + persona.getRuc() + "</td>");
                        out.println("<td>" + persona.getUbigeo() + "</td>");
                        out.println("<td>" + persona.getCelular() + "</td>");
                        out.println("<td>" + persona.getEmail() + "</td>");
                        out.println("</tr>");
                    }
                    out.println("</table>");
                    out.println("</fieldset>");
                } else {
                    out.println("<center>");
                    out.println("<fieldset style='border-width:3px'>");
                    out.println("<legend><b>RESULTADO DE BUSQUEDA:</b></legend>");
                    out.println("<table id='tablapersonas' class='tabla'>");
                    out.println("<tr class='modo1'>");
                    out.println("<td style='color:red;font-size: 15px'>NO SE ENCONTRO NINGUNA PERSONA CON CUENTA ACTIVA, PRUEBE CON OTROS PARAMETROS</td>");
                    out.println("</tr>");
                    out.println("</table>");
                    out.println("</fieldset>");
                    out.println("</center>");
                }
            } else if ("NUMERO DE CUENTA".equals(tipoBusqueda)) {
                contenido = contenido.replace("-", "");
                sql = "from TCuentaPersona where numCta like '%" + contenido + "' AND (estado='ACTIVO' OR estado='SOBREGIRO' OR estado='BLOQUEADO')";
                List lisnombre = dao.findAll(sql);
                if (lisnombre.size() >= 1) {
                    Iterator it = lisnombre.iterator();
                    out.println("<fieldset style='border-width:3px'>");
                    out.println("<legend><b>RESULTADO DE BUSQUEDA:</b></legend>");
                    out.println("<table border='0' id='tablapersonas' class='tabla'>");
                    out.println("<tr>");
                    out.println("<th >-.-</th>");
                    out.println("<th style='display:none' >idPersona</th>");
                    out.println("<th >N&uacute;mero Cuenta</th>");
                    out.println("<th >DNI</th>");
                    out.println("<th >RUC</th>");
                    out.println("<th >Nombre</th>");
                    out.println("<th >Apellidos</th>");
                    out.println("<th >Ubigeo</th>");
                    out.println("<th >Celular</th>");
                    out.println("<th >Email</th>");
                    out.println("</tr>");
                    while (it.hasNext()) {
                        TCuentaPersona cuenta = (TCuentaPersona) it.next();
                        par = "";
                        i = i + 1;
                        if (i % 2 == 0) {
                            par = "modo2";
                        } else {
                            par = "modo1";
                        }
                        out.println("<tr class='" + par + "'>");
                        out.println("<td ><input type='radio' id='rdPersona" + i + "' name='rdPersona' value='" + i + "' onclick='mostrarcuentadetalle(" + i + "," + i + ")'/></td>");
                        out.println("<td id='tdIdPersona" + i + "' style='display:none' >" + cuenta.getTPersona().getIdUserPk() + "</td>");
                        out.println("<td id='ttdNumCuenta" + i + "' >" + cuenta.getNumCta() + "</td>");
                        out.println("<td id='ttdDNI" + i + "' >" + cuenta.getTPersona().getDocIdentidad() + "</td>");
                        out.println("<td id='ttdRUC" + i + "'>" + cuenta.getTPersona().getRuc() + "</td>");
                        out.println("<td id='ttdN" + i + "'>" + cuenta.getTPersona().getNombre() + "</td>");
                        out.println("<td id='ttdA" + i + "'>" + cuenta.getTPersona().getApellidos() + " </td>");
                        out.println("<td >" + cuenta.getTPersona().getUbigeo() + "</td>");
                        out.println("<td >" + cuenta.getTPersona().getCelular() + "</td>");
                        out.println("<td >" + cuenta.getTPersona().getEmail() + "</td>");
                        out.println("</tr>");
                    }
                    out.println("</table>");
                    out.println("</fieldset>");
                } else {
                    out.println("<center>");
                    out.println("<fieldset style='border-width:3px'>");
                    out.println("<legend><b>RESULTADO DE BUSQUEDA:</b></legend>");
                    out.println("<table id='tablapersonas' class='tabla'>");
                    out.println("<tr class='modo1'>");
                    out.println("<td style='color:red;font-size: 15px'>NO SE ENCONTRO NINGUNA CUENTA CON ESTE N&Uacute;MERO</td>");
                    out.println("</tr>");
                    out.println("</table>");
                    out.println("</fieldset>");
                    out.println("</center>");
                }
            } else {
                out.println("<center>");
                out.println("<fieldset style='border-width:3px'>");
                out.println("<legend><b>RESULTADO DE BUSQUEDA:</b></legend>");
                out.println("<table id='tablapersonas' class='tabla'>");
                out.println("<tr class='modo1'>");
                out.println("<td style='color:red;font-size: 15px'>NO SE ENCONTRO NINGUNA PERSONA CON ESTE DATO</td>");
                out.println("</tr>");
                out.println("</table>");
                out.println("</fieldset>");
                out.println("</center>");
            }
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
