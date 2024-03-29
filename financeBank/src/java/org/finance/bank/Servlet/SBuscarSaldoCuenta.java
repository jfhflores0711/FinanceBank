package org.finance.bank.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.finance.bank.bean.TCuentaPersona;
import org.finance.bank.model.dao.DAOGeneral;
import org.finance.bank.util.CurrencyConverter;

/**
 *
 * @author roger
 */
public class SBuscarSaldoCuenta extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        if (session.getAttributeNames().hasMoreElements()) {
            try {
                String numcuenta = request.getParameter("numcuentatick");
                DAOGeneral dao = new DAOGeneral();
                if (numcuenta != null) {
                    String tabla = "<table width='400px'><tr><td align='left'><font size='3'>SALDO DISPONIBLE: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
                    TCuentaPersona CuentaPersona = (TCuentaPersona) dao.findAll("from TCuentaPersona cuen where cuen.numCta='" + numcuenta + "'").get(0);
//                    if ("SOBREGIRO".equals(CuentaPersona.getEstado())) {
//                        Set sgs = CuentaPersona.getTSobregiros();
////                        int t = sgs.size();
////                        tabla += String.valueOf(t);
////                        Iterator its = sgs.iterator();
////                        while (its.hasNext()) {
////                            tabla += "sg=";
////                            TSobregiro sg = (TSobregiro) its.next();
////                            if ("ACTIVO".equals(sg.getEstado())) {
////                                tabla += "(" + sg.getMontoActual().toString() + ")";
////                            }
////                        }
//                    }
                    tabla += CuentaPersona.getTMoneda().getSimbolo() + "&nbsp;</font></td><td align='right'><font size='3'>";
                    tabla += CurrencyConverter.formatToMoneyUS(CuentaPersona.getSaldoSinInteres().doubleValue(), 2) + "</font></td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>";
                    BigDecimal saldointeres = CuentaPersona.getSaldo().subtract(CuentaPersona.getSaldoSinInteres());
                    if (CuentaPersona.getInteres().doubleValue() > 0.0D) {
                        tabla += "</tr><tr><td align='left'><font size='3'>INTERESES: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + CuentaPersona.getTMoneda().getSimbolo() + "&nbsp;</font></td><td align='right'><font size='3'>" + CurrencyConverter.formatToMoneyUS(saldointeres.doubleValue(), 2) + "</font></td>";
                        tabla += "<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr><tr>"
                                + "<td align='left'><font size='3'>SALDO TOTAL: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + CuentaPersona.getTMoneda().getSimbolo() + "&nbsp;</font></td><td align='right'><div><font size='3'>" + CurrencyConverter.formatToMoneyUS(CuentaPersona.getSaldo().doubleValue(), 2) + "</font></div></td>"
                                + "<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr></table>";
                    } else {
                        tabla += "</tr><tr><td>&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>"
                                + "<td>&nbsp;</td></tr></table>";
                    }
                    out.print(tabla);
                } else {
                    out.println("<table><tr><td></td><td></td></tr></table>");
                }
            } finally {
                out.close();
            }
        } else {
            out.println("ERROR...");
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
