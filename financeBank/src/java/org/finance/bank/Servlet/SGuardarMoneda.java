package org.finance.bank.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
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
import org.finance.bank.bean.TMoneda;
import org.finance.bank.bean.TTasa;
import org.finance.bank.bean.TTipoCambio;
import org.finance.bank.model.dao.DAOGeneral;
import org.finance.bank.util.DateUtil;

/**
 *
 * @author ubuntu
 */
public class SGuardarMoneda extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @SuppressWarnings("empty-statement")
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        String myId=(String) session.getAttribute("USER_ID");
        String myIp=(String) session.getAttribute("USER_IP");
        DAOGeneral dao = new DAOGeneral();
        String idForInitNewestTable = DateUtil.convertDateId(myId, SGuardarMoneda.class.getSimpleName());
        String idForInitNewestTable2=DateUtil.convertDateId(myId, SGuardarMoneda.class.getSimpleName());
        try {
            String nombre = request.getParameter("nombre");
            String codigo = request.getParameter("codigo");
            String simbolo = request.getParameter("simbolo");
            String color = request.getParameter("color");
            String band = "";
            List result = dao.findAll("from TMoneda");
            Iterator bucle = result.iterator();
            int te = 0;
            while (bucle.hasNext()) {
                TMoneda tas2 = (TMoneda) bucle.next();
                if (!tas2.getCodMoneda().equals(codigo)) {
                    band = "V";
                } else {
                    band = "F";
                    out.println("<fieldset>");
                    out.println("<legend style='font-size:12px'><b><font color='#2A7AA4'>ACTUALIZAR PRECIOS MONETARIOS</font></b> </legend>");
                    out.println("<br>");
                    out.println("<td><font color='RED'>OPERACI&Oacute;N FALLIDA: CODIGO DE LA MONEDA YA EXISTE</font></td>");
                    out.println("<br>");
                    out.println("</fieldset>");
                }
                te++;
            }
            if (band.equals("V")) {
                TMoneda moneda = new TMoneda();                
                moneda.setCodMoneda(codigo.toUpperCase());                                 // Atributo 01
                moneda.setNombre(nombre.toUpperCase());                                    // Atributo 02
                String simbolofinal = "&#" + simbolo;
                moneda.setSimbolo(simbolofinal.toUpperCase());                             // Atributo 03
                moneda.setIdUser(myId );                // Atributo 04
                moneda.setIpUser(myIp);                // Atributo 05
                moneda.setDateUser(idForInitNewestTable);                          // Atributo 06
                String sql = "select max(mon.codParaNumCuenta) from TMoneda mon";
                List result2 = dao.findAll(sql);
                String max = (String) result2.get(0);
                int maxint = Integer.parseInt(max) + 10;
                String valor = String.valueOf(maxint);
                moneda.setCodParaNumCuenta(valor);                     // Atributo 07
                String colorfinal = "#" + color;
                moneda.setColor(colorfinal);                           // Atributo 08
                moneda.setEstado("INACTIVO");                          // Atributo 09
                dao.persist(moneda);
                TTipoCambio tablatipocambio = new TTipoCambio();
                tablatipocambio.setIdtipocambio(idForInitNewestTable);        //Atributo 01
                tablatipocambio.setFecha(DateUtil.getNOW_S());                            //Atributo 02
                tablatipocambio.setCodMonedaA("PEN");                                //Atributo 03
                tablatipocambio.setIdUser(myId);  // Atributo 04
                tablatipocambio.setIpUser(myIp);  // Atributo 05
                tablatipocambio.setDateUser(idForInitNewestTable);                              //Atributo  06
                String hql = "from TMoneda codigomon where codigomon.codMoneda='" + codigo + "'";
                TMoneda codmon = (TMoneda) dao.findAll(hql).get(0);
                tablatipocambio.setTMoneda(codmon);
                tablatipocambio.setDescripcion("COMPRA DE MONEDA " + nombre);
                dao.persist(tablatipocambio);
                tablatipocambio = new TTipoCambio();
                tablatipocambio.setIdtipocambio(idForInitNewestTable2);        //Atributo 01
                tablatipocambio.setFecha(DateUtil.getNOW_S());                            //Atributo 02
                tablatipocambio.setCodMonedaA(codigo);                                 //Atributo 03
                tablatipocambio.setIdUser(myId);   // Atributo 04
                tablatipocambio.setIpUser(myIp);   // Atributo 05
                tablatipocambio.setDateUser(idForInitNewestTable);                              //Atributo  06
                String hqlv = "from TMoneda codigomon where codigomon.codMoneda='PEN'";
                TMoneda codmonv = (TMoneda) dao.findAll(hqlv).get(0);
                tablatipocambio.setTMoneda(codmonv);
                tablatipocambio.setDescripcion("VENTA DE MONEDA " + nombre);
                dao.persist(tablatipocambio);
                String compraMoneda = "1";
                String ventaMoneda = "1";
                String tipoperacion = "TASA MERCADO";
                if (!compraMoneda.equals("")) {
                    String hqlc = "from TTipoCambio tipcam where tipcam.TMoneda='" + codigo + "' AND tipcam.codMonedaA='PEN'";
                    TTipoCambio idtipocambiocompra = (TTipoCambio) dao.findAll(hqlc).get(0);
                    TTasa tasa = new TTasa();
                    tasa.setIdtasa(idForInitNewestTable);                           //ATRIBUTO 01
                    BigDecimal conversionc = new BigDecimal(compraMoneda);
                    tasa.setFConversion(conversionc);                                       //ATRIBUTO 02
                    tasa.setFecha(DateUtil.getNOW_S());                                        //ATRIBUTO 03
                    tasa.setEstado("ACTIVO");                                              //ATRIBUTO 04
                    tasa.setIdUser(myId);              //ATRIBUTO 05
                    tasa.setIpUser(myIp);              //ATRIBUTO 06
                    tasa.setDateUser(idForInitNewestTable);                                     //ATRIBUTO 07
                    tasa.setTTipoCambio(idtipocambiocompra);                               //ATRIBUTO 08
                    tasa.setTipoTasa(tipoperacion);                                        //ATRIBUTO 09                                      //ATRIBUTO 09
                    dao.persist(tasa);
                }
                if (!ventaMoneda.equals("")) {
                    String hqlventa = "from TTipoCambio tipcam where tipcam.TMoneda='PEN' AND tipcam.codMonedaA='" + codigo + "'";
                    TTipoCambio idtipocambioventa = (TTipoCambio) dao.findAll(hqlventa).get(0);
                    TTasa tasa = new TTasa();
                    tasa.setIdtasa(idForInitNewestTable2);
                    BigDecimal conversion = new BigDecimal(ventaMoneda);
                    tasa.setFConversion(conversion);                                       //ATRIBUTO 02
                    tasa.setFecha(DateUtil.getNOW_S());//ATRIBUTO 03
                    tasa.setEstado("ACTIVO");                                              //ATRIBUTO 04
                    tasa.setIdUser(myId);              //ATRIBUTO 05
                    tasa.setIpUser(myIp);              //ATRIBUTO 06
                    tasa.setDateUser(idForInitNewestTable);                                     //ATRIBUTO 07
                    tasa.setTTipoCambio(idtipocambioventa);                                //ATRIBUTO 08
                    tasa.setTipoTasa(tipoperacion);                                        //ATRIBUTO 09
                    dao.persist(tasa);
                }
                List resultx = dao.findAll("from TMoneda money where money.estado='ACTIVO' order by codParaNumCuenta");
                Iterator itx = resultx.iterator();
                out.println("<div id='divdos'>");
                out.println("<fieldset>");
                out.println("<legend style='font-size:12px'><b><font color='#2A7AA4'>ACTUALIZAR PRECIOS MONETARIOS</font></b> </legend>");
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
                while (itx.hasNext()) {
                    TMoneda monx = (TMoneda) itx.next();
                    if (!monx.getCodMoneda().equals("PEN")) {
                        out.println("<tr>");
                        out.println("<td style='display:none' id='codmoneda" + u + "'>" + monx.getCodMoneda() + "</td>");
                        if (monx.getCodMoneda().equals("EUR")) {
                            out.println("<td><font color='#7007F9'><b>" + monx.getNombre() + "</b></font></td>");
                        } else {
                            if (monx.getCodMoneda().equals("USD")) {
                                out.println("<td><font color='#27D314'><b>" + monx.getNombre() + "</b></font></td>");
                            } else {
                                out.println("<td><font color=" + colorfinal + "><b>" + monx.getNombre() + "</b></font></td>");
                            }
                        }
                        out.println("<td><input id='compraM" + u + "' name='compraM' type='text' value='' style='width:60px'/></td>");
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
                        out.println("<td><input type='submit' value='ELIMINAR' onclick='EliminarMoneda('" + u + "')' /></td>");
                        out.println("</tr>");
                        u++;
                    }
                }
                out.println("</table>");
                out.println("</fieldset>");
                out.println("</div>");
                List resulta = dao.findAll("from TMoneda money where money.estado='INACTIVO' order by codParaNumCuenta");
                Iterator ita = resulta.iterator();
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
                while (ita.hasNext()) {
                    TMoneda mona = (TMoneda) ita.next();
                    if (!mona.getCodMoneda().equals("PEN")) {
                        out.println("<tr>");
                        out.println("<td style='display:none' id='codmoneda" + ut + "'>" + mona.getCodMoneda() + "</td>");
                        if (mona.getCodMoneda().equals("EUR")) {
                            out.println("<td><font color='#7007F9'><b>" + mona.getNombre() + "</b></font></td>");
                        } else {
                            if (mona.getCodMoneda().equals("USD")) {
                                out.println("<td><font color='#27D314'><b>" + mona.getNombre() + "</b></font></td>");
                            } else {
                                out.println("<td><font color=" + colorfinal + "><b>" + mona.getNombre() + "</b></font></td>");
                            }
                        }
                        out.println("<td>" + mona.getEstado() + "</td>");
                        out.println("<td><input id='guardarT' type='button' value='ACTIVAR' onclick='ActivarMoneda('" + ut + "')'/></td>");
                        out.println("</tr>");
                        ut++;
                    }
                }
                out.println("</table>");
                out.println("</div>");
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
