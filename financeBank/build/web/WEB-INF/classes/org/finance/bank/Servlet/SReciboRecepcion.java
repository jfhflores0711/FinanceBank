package org.finance.bank.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.finance.bank.bean.TCaja;
import org.finance.bank.bean.TTransferenciaCaja;
import org.finance.bank.model.dao.DAOGeneral;
import org.finance.bank.util.DateUtil;
import org.finance.bank.util.formatMoneda;

/**
 *
 * @author linux
 */
public class SReciboRecepcion extends HttpServlet {

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
            String idTransferencia = null;
            if (request.getParameter("idTransferencia") != null && !request.getParameter("idTransferencia").equals("")) {
                idTransferencia = request.getParameter("idTransferencia").toString().trim();
            }
            if (idTransferencia == null) {
                out.print("ERROR");
                out.close();
                return;
            }
            String date = DateUtil.getNOW_S();
            String verfecha = date.substring(0, 10);
            String verhora = date.substring(11);
            TTransferenciaCaja trnscj = (TTransferenciaCaja) dao.load(TTransferenciaCaja.class, idTransferencia);
            out.println("<style type='text/css' media='print'>");
            out.println(".botones{");
            out.println("display:none;");
            out.println("}");
            out.println("</style>");
            out.println("<body>");
            out.println("<table border='0' cellpadding='0' cellspacing='0' >");
            out.println("<tr>");
            for (int i = 0; i < 3; i++) {
                if (i == 0 || i == 2) {
                    out.println("<td valign='top'>");
                    out.println("<table  width ='400'border = '0' cellpadding = '0' cellspacing = '5' >");
                    out.println("<tr>");
                    out.println("<td>");
                    out.println(" &nbsp;&nbsp;&nbsp;");
                    out.println("</td>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println("<td colspan='3'><font size='2'><b> OPERACION : RECEPCIÓN </b></font> </td>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println("<td><font size='1'>FECHA: " + verfecha + "</font></td>");
                    out.println("<td><font size='1'>HORA:  " + verhora + "</font></td>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println("<td colspan='3' ><font size='1'>MONEDA: " + trnscj.getTOperacion().getTMoneda().getNombre() + " </font></td>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println("<td colspan='3' ><font size='1'>CAJA ORIGEN: " + trnscj.getTCaja().getNombreCaja() + " </font></td>");
                    out.println("</tr>");
                    TCaja cds = (TCaja) dao.load(TCaja.class, trnscj.getCodCajaDestino());
                    out.println("<tr>");
                    out.println("<td colspan='3' ><font size='1'>CAJA DESTINO: " + cds.getNombreCaja() + " </font></td>");
                    out.println("</tr>");
                    Double monto = 0.00;
                    if (trnscj.getMonto().doubleValue() != 0.0) {
                        monto = trnscj.getMonto().doubleValue();
                    }
                    out.println("<tr>");
                    out.println("<td colspan='3' aling='left'>");
                    out.println("<font size='1' style='font-weight: bold'>MONTO TRANSFERIDO: " + trnscj.getTOperacion().getTMoneda().getSimbolo() + " " + formatMoneda.formatMoneda(monto) + "</font>");
                    out.println("</td>");
                    out.println("</tr>");
                    out.println("</table>");
                    out.println("</td>");
                } else {
                    out.println("<td valign='top'  width ='20px' style='font-size:5px'> ");
                    out.println("´`´`´`´`´`´`´`´`´`´`´`´`´`´`´`´`´`´`´`´`´`´`´`´`");
                    out.println("</td>");
                }
            }
            out.println("</tr>");
            out.println("</table");
            out.println("<br>");
            out.println("<br>");
            out.println("<table width ='50%' >");
            out.println("<tr class='botones'>");
            out.println("<td align='center' valign='top' >");
            out.println("<input type='submit' value='IMPRIMIR' onclick=\"document.title=''; if (window.print)window.print();else alert('SU NAVEGADOR NO SOPORTA ESTA FUNCIÓN');\"/>");
            out.println("</td>");
            out.println("<td align='center' valign='top'>");
            out.println("<form name='fcambiomu' method='post' action='managercaja.htm'>");
            out.println("<input type='submit' value='VOLVER' />");
            out.println("</form>");
            out.println("</td>");
            out.println("</tr>");
            out.println("</table>");
            out.println("</body>");
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
