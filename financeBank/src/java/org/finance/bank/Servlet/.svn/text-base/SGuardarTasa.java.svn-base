package org.finance.bank.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.finance.bank.bean.TTasa;
import org.finance.bank.bean.TTipoCambio;
import org.finance.bank.model.dao.DAOGeneral;
import org.finance.bank.util.DateUtil;

/**
 *
 * @author ubuntu
 *
 */
public class SGuardarTasa extends HttpServlet {

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
        HttpSession session = request.getSession(true);
        String myId = (String) session.getAttribute("USER_ID");
        String idForInitNewestTable = DateUtil.convertDateId(myId, SGuardarTasa.class.getSimpleName());
        String idForInitNewestTable2 = DateUtil.convertDateId(myId, SGuardarTasa.class.getSimpleName());
        String myIp = (String) session.getAttribute("USER_IP");
        try {
            String compraMoneda = request.getParameter("tasacompra");
            String ventaMoneda = request.getParameter("tasaventa");
            String tipoperacion = request.getParameter("tipotasa");
            String codigomon = request.getParameter("codigomoneda");
            if (!compraMoneda.equals("")) {
                String hql = "from TTipoCambio tipcam where tipcam.TMoneda='" + codigomon + "' AND tipcam.codMonedaA='PEN'";
                TTipoCambio idtipocambiocompra = (TTipoCambio) dao.findAll(hql).get(0);
                TTasa tasa = new TTasa();
                tasa.setIdtasa(idForInitNewestTable);                                        //ATRIBUTO 01
                if (tipoperacion.equals("TASA MERCADO")) {
                    List result = dao.findAll("from TTasa tas where tas.TTipoCambio='" + idtipocambiocompra.getIdtipocambio() + "'");
                    Iterator tasam = result.iterator();
                    int tm = 0;
                    while (tasam.hasNext()) {
                        TTasa tas2 = (TTasa) tasam.next();
                        if (tas2.getTipoTasa().equals("TASA MERCADO") && tas2.getEstado().equals("ACTIVO")) {
                            TTasa tas = (TTasa) dao.load(TTasa.class, tas2.getIdtasa());
                            tas.setEstado("INACTIVO");
                            dao.update();
                        }
                        tm++;
                    }
                }
                if (tipoperacion.equals("TASA ESPECIAL")) {
                    List result = dao.findAll("from TTasa tas where tas.TTipoCambio='" + idtipocambiocompra.getIdtipocambio() + "'");
                    Iterator tasae = result.iterator();
                    int te = 0;
                    while (tasae.hasNext()) {
                        TTasa tas2 = (TTasa) tasae.next();
                        if (tas2.getTipoTasa().equals("TASA ESPECIAL") && tas2.getEstado().equals("ACTIVO")) {
                            TTasa tas = (TTasa) dao.load(TTasa.class, tas2.getIdtasa());
                            tas.setEstado("INACTIVO");
                            dao.update();
                        }
                        te++;
                    }
                }
                BigDecimal conversion = new BigDecimal(compraMoneda);
                tasa.setFConversion(conversion);                                       //ATRIBUTO 02
                tasa.setFecha(DateUtil.getNOW_S());                                         //ATRIBUTO 03
                tasa.setEstado("ACTIVO");                                              //ATRIBUTO 04
                tasa.setIdUser(myId);              //ATRIBUTO 05
                tasa.setIpUser(myIp);              //ATRIBUTO 06
                tasa.setDateUser(idForInitNewestTable);                                      //ATRIBUTO 07
                tasa.setTTipoCambio(idtipocambiocompra);                               //ATRIBUTO 08
                tasa.setTipoTasa(tipoperacion);                                        //ATRIBUTO 09                                      //ATRIBUTO 09
                dao.persist(tasa);
            }
            if (!ventaMoneda.equals("")) {
                String hql = "from TTipoCambio tipcam where tipcam.TMoneda='PEN' AND tipcam.codMonedaA='" + codigomon + "'";
                TTipoCambio idtipocambioventa = (TTipoCambio) dao.findAll(hql).get(0);
                TTasa tasa = new TTasa();
                tasa.setIdtasa(idForInitNewestTable2);//ATRIBUTO 01
                if (tipoperacion.equals("TASA MERCADO")) {
                    List result = dao.findAll("from TTasa tas where tas.TTipoCambio='" + idtipocambioventa.getIdtipocambio() + "'");
                    Iterator tasam = result.iterator();
                    int tm = 0;
                    while (tasam.hasNext()) {
                        TTasa tas2 = (TTasa) tasam.next();
                        if (tas2.getTipoTasa().equals("TASA MERCADO") && tas2.getEstado().equals("ACTIVO")) {
                            TTasa tas = (TTasa) dao.load(TTasa.class, tas2.getIdtasa());
                            tas.setEstado("INACTIVO");
                            dao.update();
                        }
                        tm++;
                    }
                }
                if (tipoperacion.equals("TASA ESPECIAL")) {
                    List result = dao.findAll("from TTasa tas where tas.TTipoCambio='" + idtipocambioventa.getIdtipocambio() + "'");
                    Iterator tasae = result.iterator();
                    int te = 0;
                    while (tasae.hasNext()) {
                        TTasa tas2 = (TTasa) tasae.next();
                        if (tas2.getTipoTasa().equals("TASA ESPECIAL") && tas2.getEstado().equals("ACTIVO")) {
                            TTasa tas = (TTasa) dao.load(TTasa.class, tas2.getIdtasa());
                            tas.setEstado("INACTIVO");
                            dao.update();
                        }
                        te++;
                    }
                }
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
