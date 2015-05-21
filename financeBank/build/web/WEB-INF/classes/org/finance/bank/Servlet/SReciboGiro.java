package org.finance.bank.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.finance.bank.bean.TCuentaPersona;
import org.finance.bank.bean.TPersona;
import org.finance.bank.bean.TRegistroGiro;
import org.finance.bank.model.dao.DAOGeneral;
import org.finance.bank.util.formatMoneda;

/**
 *
 * @author ronald
 */
public class SReciboGiro extends HttpServlet {

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
        HttpSession session = request.getSession(true);
        DAOGeneral dao = new DAOGeneral();
        try {
            String h = (String) session.getAttribute("h");
            String gx = (String) session.getAttribute("ID_REGISTRO_GIRO");
            if (gx == null) {
                out.close();
                return;
            }
            TRegistroGiro rg = (TRegistroGiro) dao.load(TRegistroGiro.class, gx);
            boolean borrado = false;
            TPersona p = (TPersona) dao.load(TPersona.class, rg.getIdUserPkDestino());
            if (p == null) {
                borrado = true;
                p = (TPersona) dao.load(TPersona.class, "20110228202911739108");
            }
            boolean sinUserr = p.getIdUserPk().equals("20110228202911739108");
            boolean sinUsere = rg.getTPersona().getIdUserPk().equals("20110228202911739108");
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
                    String tipoOp = " EN EFECTIVO";
                    if (!rg.getIdcuentapersona().equals("0")) {
                        tipoOp = " POR CARGO A CUENTA";
                    }
                    out.println("<td colspan='3'><font size='2'><b> OPERACION :" + rg.getTOperacion().getTTipoOperacion().getNombre() + " " + tipoOp + " </b></font> </td>");
                    out.println("</tr>");
                    if (!rg.getIdcuentapersona().equals("0")) {
                        TCuentaPersona cue = (TCuentaPersona) dao.load(TCuentaPersona.class, rg.getIdcuentapersona());
                        out.println("<tr>");
                        out.println("<td colspan='3' ><font size='1'>CUENTA Nº:" + cue.getNumCta() + " </font></td>");
                        out.println("</tr>");
                    }
                    out.println("<tr>");
                    out.println("<td colspan='3' ><font size='1'>N&deg; OP :" + rg.getTOperacion().getNumeroOperacion().substring(rg.getTOperacion().getNumeroOperacion().length() - 4, rg.getTOperacion().getNumeroOperacion().length()) + " | CODCAJA : " + rg.getTOperacion().getTPersonaCaja().getTCaja().getCodCaja() + " | FI: " + rg.getTOperacion().getTPersonaCaja().getTCaja().getTFilial().getNombre() + " </font></td>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println("<td><font size='1'>FECHA: " + rg.getTOperacion().getFecha().substring(8, 10) + "/" + rg.getTOperacion().getFecha().substring(5, 8) + rg.getTOperacion().getFecha().substring(0, 4) + "</font></td>");
                    out.println("<td><font size='1'>HORA:  " + rg.getTOperacion().getFecha().substring(11) + "</font></td>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println("<td colspan='3' ><font size='1'>MONEDA: " + rg.getTOperacion().getTMoneda().getNombre() + " </font></td>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println("<td colspan='3' ><font size='1'>ORDENANTE: " + (sinUsere ? "" + rg.getGirador().substring(0, rg.getGirador().indexOf("|")) : rg.getTPersona().getNombre()) + " " + (sinUsere ? "" + rg.getGirador().substring(rg.getGirador().indexOf("|") + 1, rg.getGirador().length()) : rg.getTPersona().getApellidos()) + " </font></td>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println("<td colspan='3' ><font size='1'>BENEFICIARIO: " + (sinUserr ? "" + rg.getRecibidor().substring(0, rg.getRecibidor().indexOf("|")) : p.getNombre()) + " " + (sinUserr ? "" + rg.getRecibidor().substring(rg.getRecibidor().indexOf("|") + 1, rg.getRecibidor().length()) : p.getApellidos()) + (borrado ? rg.getIdUserPkDestino().substring(0, 8) : "") + " </font></td>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println("<td colspan='3' ><font size='1'>FILIAL DESTINO: " + rg.getTFilial().getNombre() + " </font></td>");
                    out.println("</tr>");
                    Double monto = 0.00;
                    if (rg.getComision().doubleValue() == 0.0) {
                        monto = rg.getImporte().doubleValue();
                    } else if (rg.getFpagoComision().equals("DESCONTAR")) {
                        monto = rg.getImporte().doubleValue() - rg.getComision().doubleValue();
                    } else {
                        monto = rg.getImporte().doubleValue();
                    }
                    out.println("<tr>");
                    out.println("<td colspan='3' aling='left'>");
                    if (rg.getPagadorComision().equals("PRECEPTOR")) {
                        out.println("<font size='1'>MONTO: " + rg.getTOperacion().getTMoneda().getSimbolo() + " " + formatMoneda.formatMoneda(monto) + "</font>");
                    } else {
                        if (rg.getComision().doubleValue() == 0.0) {
                            out.println("<font size='1'>MONTO: " + rg.getTOperacion().getTMoneda().getSimbolo() + " " + formatMoneda.formatMoneda(monto) + "</font>");
                        } else {
                            out.println("<table border='0' cellpadding='5' cellspacing='5' >");
                            out.println("<tr>");
                            out.println("<td><font size='1' align ='left'>MONTO: " + rg.getTOperacion().getTMoneda().getSimbolo() + " " + formatMoneda.formatMoneda(monto) + "</font></td>");
                            out.println("<td><font size='1' style='font-weight: bold'>COMISION: " + rg.getTOperacion().getTMoneda().getSimbolo() + " " + formatMoneda.formatMoneda(rg.getComision().doubleValue()) + "</font></td>");
                            out.println("<td><font size='1' style='font-weight: bold'>TOTAL: " + rg.getTOperacion().getTMoneda().getSimbolo() + " " + formatMoneda.formatMoneda(monto + rg.getComision().doubleValue()) + "</font></td>");
                            out.println("</tr>");
                            out.println("</table>");
                        }
                    }
                    out.println("</td>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println("<td colspan='3' align='right'>");
                    if (i == 0) {
                        out.print("<br><br>");
                        if (" POR CARGO A CUENTA".equals(tipoOp)) {
                            out.print("_________________<br>");
                            out.print("<font size='1'>DNI:" + (sinUserr ? ".................." : p.getDocIdentidad()) + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font>");
                        } else {
                            out.print("<br>");
                        }
                        out.print("<br><font size='1'>CONTROL ADMINISTRATIVO</font>");
                    } else {
                        out.print("<br><br><br><br><font size='1'>USUARIO</font>");
                    }
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
            if (h == null) {
                out.println("<td align='center' valign='top'>");
                out.println("<form name='fcambiomu' method='post' action='giros.htm'>");
                out.println("<input type='submit' value='VOLVER' />");
                out.println("</form>");
                out.println("</td>");
            } else {
                out.println("<td align='center' valign='top'>");
                out.println("<form name='fcambiomu' method='post' action=''>");
                out.println("<input type='button' value='CERRAR' onclick='window.close();' />");
                out.println("</form>");
                out.println("</td>");
            }
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
