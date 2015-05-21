/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.servlet.http.HttpSession;
import org.finance.bank.bean.TDenominacionMoneda;
import org.finance.bank.bean.TMoneda;
import org.finance.bank.model.dao.DAOGeneral;
import org.finance.bank.util.DateUtil;

/**
 *
 * @author ubuntu
 */
public class SDenominacionMonedas extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        DAOGeneral dao = new DAOGeneral();
        try {
            String codigo = request.getParameter("codigo");
            String monto = request.getParameter("monto");
            String tipo = request.getParameter("tipo");
            TDenominacionMoneda denominacion = new TDenominacionMoneda();
            denominacion.setIdUser((String) session.getAttribute("USER_ID"));          // Atributo 05
            String unaid = DateUtil.convertDateId(denominacion.getIdUser(),SDenominacionMonedas.class.getSimpleName());
            denominacion.setIddenominacionmoneda(unaid);                                // Atributo 01
            session.setAttribute("iddenominacionmoneda", unaid);
            denominacion.setTipo(tipo);                                                // Atributo 02
            denominacion.setMonto(monto);                                              // Atributo 03
            denominacion.setImagen("");                                                // Atributo 04
            denominacion.setIpUser((String) session.getAttribute("USER_IP"));          // Atributo 06
            DateFormat idfecha = new SimpleDateFormat("yyyyMMddHHmmss");
            String idfechapersona = idfecha.format(new Date()) + System.nanoTime();
            idfechapersona = idfechapersona.substring(0, 20);
            denominacion.setDateUser(idfechapersona);                                   // Atributo 07
            TMoneda objeto = (TMoneda) dao.load(TMoneda.class, codigo);
            denominacion.setTMoneda(objeto);                                            // Atributo 08
            List resulta = dao.findAll("from TDenominacionMoneda");
            Iterator ita = resulta.iterator();
            int contador = 0;
            int inicial = 10;
            while (ita.hasNext()) {
                TDenominacionMoneda TDmona = (TDenominacionMoneda) ita.next();
                TMoneda lista = (TMoneda) dao.load(TMoneda.class, codigo);
                if (TDmona.getTMoneda().equals(lista)) {
                    contador = contador + 1;
                }
            }
            if (contador < 1) {
                denominacion.setOrden(inicial);                                           // Atribute 09
            } else {
                String sql = "select max(demon.orden) from TDenominacionMoneda demon where demon.TMoneda='" + codigo + "'";
                Integer valor = (Integer) dao.findAll(sql).get(0);
                Integer max = valor + 10;
                denominacion.setOrden(max);                                       // Atribute 09
            }
            dao.persist(denominacion);
            //REDIBUJANDO TABLA
/*           Query queryx = sess.createQuery("from TMoneda money where money.estado='ACTIVO' order by codParaNumCuenta");
            List resultx = queryx.list();
            Iterator itx = resultx.iterator();
            out.println("<div id='divdos'>");
            out.println("<fieldset>");
            out.println("<legend style='font-size:12px'><b><font color='#487388'>ACTUALIZAR PRECIOS MONETARIOS</font></b> </legend>");
            out.println("<br>");
            out.println("<table id='tablamoneda' border='1' cellspacing='0' bordercolor='#3E8992' >");
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
            while (itx.hasNext())
            {
            TMoneda monx = (TMoneda) itx.next();
            if (!monx.getCodMoneda().equals("PEN"))
            {
            out.println("<tr>");
            out.println("<td style='display:none' id='codmoneda"+u+"'>"+monx.getCodMoneda()+"</td>");
            //////////////////////////////////////////////
            if(monx.getCodMoneda().equals("EUR"))
            {
            out.println("<td><font color='#7007F9'><b>"+monx.getNombre()+"</b></font></td>");
            }
            else
            {
            if(monx.getCodMoneda().equals("USD"))
            {
            out.println("<td><font color='#27D314'><b>"+monx.getNombre()+"</b></font></td>");
            }
            else
            {
            out.println("<td><b>"+monx.getNombre()+"</b></td>");
            }
            }
            out.println("<td><input id='compraM"+u+"' name='compraM' type='text' value='' style='width:60px'/></td>");
            out.println("<td><input id='ventaM"+u+"' name='ventaM' type='text' value='' style='width:60px'/></td>");
            out.println("<td><select id='tipoT"+u+"' name='tipoT'>");
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
            out.println("<td>"+unafecha+"</td>");
            out.println("<td>"+unahora +"</td>");
            out.println("<td><input id='guardarT' type='button' value='GUARDAR' onclick='GuardarTasas('"+u+"')'/></td>");
            out.println("<td><input type='submit' value='ELIMINAR' onclick='EliminarMoneda('"+u+"')' /></td>");
            out.println("</tr>");
            u++;
            }
            }
            out.println("</table>");
            out.println("</fieldset>");
            out.println("</div>");*/
            /*
            Query querya = sess.createQuery("from TMoneda money where money.estado='INACTIVO' order by codParaNumCuenta");
            List resulta = querya.list();
            Iterator ita = resulta.iterator();
            //out.println("<td><font color='RED'> TABLA 02 DESDE SERVLET</font></td>");
            out.println("<br>");
            out.println("<br>");
            out.println("<div id='divdos'>");
            out.println("<fieldset>");
            out.println("<legend style='font-size:12px'><b><font color='#2A7AA4'>LISTA DE MONEDAS INABILITADAS</font></b></legend>");
            out.println("<table id='tablatodas' border='1' cellspacing='0'>");
            out.println("<tr>");
            out.println("<td style='display:none'><center><b>CODIGO MONEDA</b></center></td>");
            out.println("<td><center><b><font color='#568FA5'>MONEDA</font></b></center></td>");
            out.println("<td><center><b><font color='#568FA5'>ESTADO</font></b></center></td>");
            out.println("</tr>");
            int ut = 0;
            while (ita.hasNext())
            {
            TMoneda mona = (TMoneda) ita.next();
            if (!mona.getCodMoneda().equals("PEN"))
            {
            out.println("<tr>");
            out.println("<td style='display:none' id='codmoneda"+ut+"'>"+mona.getCodMoneda()+"</td>");
            if(mona.getCodMoneda().equals("EUR"))
            {
            out.println("<td><font color='#7007F9'><b>"+mona.getNombre()+"</b></font></td>");
            }
            else
            {
            if(mona.getCodMoneda().equals("USD"))
            {
            out.println("<td><font color='#27D314'><b>"+mona.getNombre()+"</b></font></td>");
            }
            else
            {
            out.println("<td><b>"+mona.getNombre()+"</b></td>");
            }
            }
            out.println("<td>"+mona.getEstado()+"</td>");
            out.println("<td><input id='guardarT' type='button' value='ACTIVAR' onclick='ActivarMoneda('"+ut+"')'/></td>");
            out.println("</tr>");
            ut++;
            }
            }
            out.println("</table>");
            out.println("</div>");*/
            out.println("<br><br>");
            out.println("<br><br><br><br><br>");
            out.println("<br><br><br><br><br>");
            out.println("<br><br><br><br><br>");
            out.println("<br><br><br><br>");
            out.println("<div id='divsubirfichero'>");
            out.println("<form id='frmsubirfile' name='frmsubirfile' method='POST' action='SUploadArchivo' enctype='multipart/form-data'>");
            out.println("<fieldset   style='border-width:3px'>");
            out.println("<legend><font color='#487388'><b>INGRESE LA IMAGEN DE LA MONEDA Y/O BILLETE</b></font></legend>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<td colspan='2'>");
            out.println("</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td><font color='#1E551D' style='font-size: 12px;'>BUSCAR IMAGEN </font></td>");
            out.println("<td><input type='file' id='ExaminarFoto' name='ExaminarFoto' value='' maxlength='200' onfocus='enviarsessionimagen();' accept='image/jpg;image/jpeg;image/gif;image/png;image/bmp'/></td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td colspan='2'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
            out.println("<input id='cmdsubir' type='submit' value='Subir' disabled><input type='button' id='cmdLimpiar' value='Limpiar' onclick='limpiarcelda();'disabled></td>");
            out.println("</tr>");
            out.println("</table>");
            out.println("</fieldset>");
            out.println("</form>");
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
