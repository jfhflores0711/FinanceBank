package org.finance.bank.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.finance.bank.bean.TMoneda;
import org.finance.bank.model.dao.DAOGeneral;

/**
 *
 * @author ubuntu
 */
public class SActivarMonedas extends HttpServlet {

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
        DAOGeneral dao = new DAOGeneral();
        request.getSession(true);
        try {
            String codigomoneda = request.getParameter("codigo");
            List result = dao.findAll("from TMoneda mon where mon.codMoneda='" + codigomoneda + "'");
            Iterator tasam = result.iterator();
            int tm = 0;
            while (tasam.hasNext()) {
                TMoneda mon = (TMoneda) tasam.next();
                if (mon.getCodMoneda().equals(codigomoneda)) {
                    TMoneda money = (TMoneda) dao.load(TMoneda.class, mon.getCodMoneda());
                    money.setEstado("ACTIVO");
                    dao.update();
                }
                tm++;
            }
            List resultx = dao.findAll("from TMoneda money where money.estado='ACTIVO' order by codParaNumCuenta");
            Iterator itx = resultx.iterator();
            out.println("<fieldset>");
            out.println("<legend style='font-size:12px'><b><font color='#2A7AA4'>ACTUALIZAR PRECIOS MONETARIOS</font></b></legend>");
            out.println("<br>");
            out.println("<table id='tablamoneda' border='1' cellspacing='0' bordercolor='#3E8992'>");
            out.println("<table id='tablamoneda' border='1' cellspacing='0'>");
            out.println("<tr>");
            out.println("<td style='display:none'><center><b>CODIGO MONEDA</b></center></td>");
            out.println("<td><center><b>MONEDA</b></center></td>");
            out.println("<td><center><b>COMPRA</b></center></td>");
            out.println("<td><center><b>VENTA</b></center></td>");
            out.println("<td><center><b>DESCRIPCI&Oacute;N</b></center></td>");
            out.println("<td><center><b>FECHA</b></center></td>");
            out.println("<td><center><b>HORA</b></center></td>");
            out.println("</tr>");
            int u = 0;
            while (itx.hasNext()) {
                TMoneda monx = (TMoneda) itx.next();
                if (!monx.getCodMoneda().equals("PEN")) {
                    out.println("<tr>");
                    out.println("<td style='display:none' id='codmoneda" + u + "'>" + monx.getCodMoneda() + "</td>");
                    out.println("<td>" + monx.getNombre() + "</td>");
                    out.println("<td><input id='compraM" + u + " 'name='compraM' type='text' value='' style='width:60px'/></td>");
                    out.println("<td><input id='ventaM" + u + "' name='ventaM' type='text' value='' style='width:60px'/></td>");
                    out.println("<td><select id='tipoT" + u + "' name='tipoT'>");
                    out.println("<option>TASA MERCADO</option>");
                    out.println("<option>TASA ESPECIAL</option>");
                    out.println("</select>");
                    out.println("</td>");
                    DateFormat fechas = new SimpleDateFormat("dd/MM/yyyy");
                    String unafecha = fechas.format(new Date()) + System.nanoTime();
                    unafecha = unafecha.substring(0, 10);
                    DateFormat horas = new SimpleDateFormat("HH:mm:ss");
                    String unahora = horas.format(new Date()) + System.nanoTime();
                    unahora = unahora.substring(0, 8);
                    out.println("<td>" + unafecha + "</td>");
                    out.println("<td>" + unahora + "</td>");
                    out.println("<td><input id='guardarT' type='button' value='GUARDAR' onclick='GuardarTasas('" + u + "')'/></td>");
                    out.println("<td><input type='submit' value='ELIMINAR' /></td>");
                    out.println("</tr>");
                    u++;
                }
            }
            out.println("</table>");
            out.println("</fieldset>");
            List resulta = dao.findAll("from TMoneda money where money.estado='INACTIVO' order by codParaNumCuenta");
            Iterator ita = resulta.iterator();
            out.println("<br>");
            out.println("<br>");
            out.println("<fieldset>");
            out.println("<legend style='font-size:12px' ><b><font color='#2A7AA4'>LISTA DE MONEDAS INABILITADAS</font></b></legend>");
            out.println("<table id='tablainferior' border='1' cellspacing='0'>");
            out.println("<tr>");
            out.println("<td style='display:none'><center><b>CODIGO MONEDA</b></center></td>");
            out.println("<td><center><b>MONEDA</b></center></td>");
            out.println("<td><center><b>ESTADO</b></center></td>");
            out.println("</tr>");
            int ut = 0;
            while (ita.hasNext()) {
                TMoneda mona = (TMoneda) ita.next();
                if (!mona.getCodMoneda().equals("PEN")) {
                    out.println("<tr>");
                    out.println("<td style='display:none' id='codmoneda" + u + "'>" + mona.getCodMoneda() + "></td>");
                    out.println("<td>" + mona.getNombre() + "</td>");
                    out.println("<td>" + mona.getEstado() + "</td>");
                    out.println("<td><input id='guardarT' type='button' value='ACTIVAR' onclick='ActivarMoneda('" + u + "')'/></td>");
                    out.println("</tr>");
                    ut++;
                }
            }
            out.println("</table>");
            out.println("</fieldset>");
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
