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
import org.finance.bank.bean.TCuentaPersona;
import org.finance.bank.bean.TDetalleIntereses;
import org.finance.bank.model.dao.DAOGeneral;
import org.finance.bank.util.DateUtil;

/**
 *
 * @author admin
 */
public class SUpdateMDisp extends HttpServlet {

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
            String gg = request.getParameter("idcuenta");
            if (gg == null) {
                out.print("NO");
                out.close();
                return;
            }
            TCuentaPersona tc = (TCuentaPersona) dao.load(TCuentaPersona.class, gg);
            if (tc == null) {
                out.print("NO");
                out.close();
                return;
            }
            String tiny = request.getParameter("tiny");
            boolean bol = false;
            if (tiny != null) {
                bol = true;
                //Set t = tc.getTDetalleIntereseses();
                List ls = dao.findAll("from TDetalleIntereses where TCuentaPersona.idcuentapersona ='" + gg + "' order by Fecha desc");
                if (ls.isEmpty()) {
                    out.print("NO HAY DETALLES");
                    out.close();
                    return;
                }
                String s = "<fieldset id='fielInts' style='border-width:3px'><legend id='legVer'><b>DETALLES ANTERIORES</b></legend><table>";
                Iterator i = ls.iterator();
                while (i.hasNext()) {
                    TDetalleIntereses d = (TDetalleIntereses) i.next();
                    if (d.getTipo().equals("ANT") && tiny.equals("a")) {
                        s += "<tr><td>FECHA: " + d.getFecha() + "</td><td>BASE: " + d.getMontoBase() + "</td><td>INTERES: " + d.getMontoint()
                                + "</td><td>FINAL: " + d.getMontoTotal() + "</td><td>Tasa: " + d.getTasaInt() + "</td></tr>";
                    } else {
                        s += "<tr><td>FECHA: " + d.getFecha() + "</td><td>BASE: " + d.getMontoBase() + "</td><td>INTERES: " + d.getMontoint()
                                + "</td><td>FINAL: " + d.getMontoTotal() + "</td><td>Tasa: " + d.getTasaInt() + "</td></tr>";
                    }
                }
                s += "<table></fieldset>";
                out.print(s);
                out.close();
                return;
            }
            if (bol) {
                out.close();
                return;
            }
            BigDecimal bm = BigDecimal.ZERO;
            String mm = request.getParameter("nm");
            if (mm == null) {
                out.print("NO");
                out.close();
                return;
            } else {
                bm = new BigDecimal(mm.replaceAll(",", ""));
            }
            BigDecimal ant = tc.getSaldo();
            tc.setSaldo(bm);
            tc.setFechaActualizacion(DateUtil.getNOW_S());
            dao.update();
            TDetalleIntereses newd = new TDetalleIntereses();
            newd.setIddetalleintereses(DateUtil.convertDateId("NOUSER", SUpdateMDisp.class.getSimpleName()));
            newd.setFecha(DateUtil.getNOW_S());
            newd.setMontoBase(tc.getSaldoSinInteres());
            newd.setMontoFinal(bm);
            newd.setMontoTotal(ant);
            newd.setMontoint(new BigDecimal(bm.doubleValue() - ant.doubleValue()));
            newd.setNumOrden("100");
            newd.setTCuentaPersona(tc);
            newd.setTasaInt(tc.getInteres());
            newd.setTipo("PRE");
            dao.persist(newd);
            out.print("OK");
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
