package org.finance.bank.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.finance.bank.bean.*;
import org.finance.bank.model.dao.DAOGeneral;
import org.finance.bank.util.CurrencyConverter;
import org.finance.bank.util.PrestamoUtil;

/**
 *
 * @author roger
 */
public class SDepositoRetiro extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        DAOGeneral dao = new DAOGeneral();
        String idrol = (String) session.getAttribute("USER_ID_ROLE");
        try {
            DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String fechahoy = df.format(new Date());
            String contenido = request.getParameter("a");
            String tipoBusqueda = request.getParameter("b");
            Logger.getLogger(SDepositoRetiro.class.getName()).log(Level.INFO, "sess = " + session.getAttribute("USER_ID_ROLE"));
            if (idrol == null) {
                out.print("ERROR: Problema de la sesion, NO SE HACE NADA");
                out.close();
                return;
            }
            TTipoPersona tipopersona = (TTipoPersona) dao.load(TTipoPersona.class, idrol);//
            int i = 0;
            String par, sql = "";
            BigDecimal interes;
            String fechaplazofijo;
            if ("NUMERO DE CUENTA".equals(tipoBusqueda)) {
                sql = "from TCuentaPersona tcp where tcp.numCta like '" + contenido + "%' AND (tcp.estado='ACTIVO' OR tcp.estado='SOBREGIRO' OR estado='BLOQUEADO') order by tcp.fecha desc";
            } else if ("DNI".equals(tipoBusqueda) || "NOMBRE".equals(tipoBusqueda) || "APELLIDO".equals(tipoBusqueda)) {
                sql = "from TCuentaPersona tcp where tcp.TPersona.idUserPk = '" + contenido + "' AND (tcp.estado='ACTIVO' OR tcp.estado='SOBREGIRO' OR estado='BLOQUEADO') order by tcp.fecha desc";
            } else if ("RUC".equals(tipoBusqueda)) {
                sql = "from TCuentaPersona tcp where tcp.TPersona.idUserPk = '" + contenido + "' AND (tcp.estado='ACTIVO' OR tcp.estado='SOBREGIRO' OR estado='BLOQUEADO') order by tcp.fecha desc";
            } else {
                out.println("<center>"
                        + "<fieldset style='border-width:3px'>"
                        + "<legend><b>CUENTAS DEL USUARIO:</b></legend>"
                        + "<table id='tablamiscuentas' class='tabla'>"
                        + "<tr class='modo1'>"
                        + "<td style='color:red;font-size: 15px'><b>NO SE ENCONTRO NINGUNA CUENTA</b></td>"
                        + "</tr>"
                        + "</table>"
                        + "</fieldset>"
                        + "</center>");
                dao.cerrarSession();
                out.close();
                return;
            }
            List liscuenta = dao.findAll(sql);
            if (liscuenta.size() >= 1) {
                Iterator it = liscuenta.iterator();
                out.println("<fieldset style='border-width:3px'>"
                        + "<legend><b>CUENTAS DEL USUARIO:</b></legend>"
                        + "<table border='0' id='tablamiscuentas' class='tabla'>"
                        + "<tr>"
                        + "<th>-.-</th>"
                        + "<th>N&deg; de Cuenta</th>"
                        + "<th>Fecha</th>"
                        + "<th>Estado</th>"
                        + "<th>Tipo cuenta</th>");
                if (tipopersona.getDescripcion().equals("ADMINISTRADOR")) {
                    out.println("<th>SALDO</th><th>SALDO SIN INTERES</th>");
                }else{
                    out.println("<th style='display:none'>SALDO</th><th style='display:none'>SALDO SIN INTERES</th>");
                }
                out.println("<th>Tipo Moneda</th>"
                        + "<th>Observaciones</th>"
                        + "<th style='display:none'></th>"
                        + "<th style='display:none'></th>"
                        + "<th style='display:none'></th>"
                        + "<th style='display:none'></th>"
                        + "<th style='display:none'></th>"
                        + "<th style='display:none'></th>"
                        + "</tr>");
                while (it.hasNext()) {
                    TCuentaPersona cuenta = (TCuentaPersona) it.next();
                    if (cuenta.getTTipoCuenta().getDescripcion().equals("CUENTA A PLAZO FIJO")) {
                        TDetalleCuentaPersona cuenPerDetalle = (TDetalleCuentaPersona) dao.findAll("from TDetalleCuentaPersona detcuen where detcuen.TCuentaPersona='" + cuenta.getIdcuentapersona() + "'").get(0);
                        interes = cuenPerDetalle.getInteres();
                        fechaplazofijo = cuenPerDetalle.getFechaPlazo();
                    } else {
                        interes = cuenta.getInteres();
                        fechaplazofijo = "";
                    }
                    par = "";
                    i = i + 1;
                    if (i % 2 == 0) {
                        par = "modo2";
                    } else {
                        par = "modo1";
                    }
                    out.println("<tr class='" + par + "' style='color:" + cuenta.getTMoneda().getColor() + "'>");
                    out.println("<td ><input type='radio' id='rdCuenta" + i + "' name='rdCuenta' value='" + i + "' onclick='mostrarbotoncreate(" + i + ")'/></td>");
                    out.println("<td id='tdNumCuenta" + i + "' >" + cuenta.getNumCta() + "</td>");
                    out.println("<td id='tdCuenFecha" + i + "'>" + cuenta.getFecha() + " </td>");
                    out.println("<td id='tdCuenEstado" + i + "'>" + cuenta.getEstado() + "</td>");
                    out.println("<td id='tdtipocuenta" + i + "'>" + cuenta.getTTipoCuenta().getDescripcion() + "</td>");
                    if (tipopersona.getDescripcion().equals("ADMINISTRADOR")) {
                        out.println("<td id='tdCuenSaldo" + i + "'>" + CurrencyConverter.formatToMoneyUS(cuenta.getSaldo().doubleValue(), 2) + "</td>");
                        out.println("<td id='tdCuenSaldo1" + i + "'>" + CurrencyConverter.formatToMoneyUS(cuenta.getSaldoSinInteres().doubleValue(), 2) + "</td>");
                    }else{
                        out.println("<td id='tdCuenSaldo" + i + "' style='display:none'>" + CurrencyConverter.formatToMoneyUS(cuenta.getSaldo().doubleValue(), 2) + "</td>");
                        out.println("<td id='tdCuenSaldo1" + i + "' style='display:none'>" + CurrencyConverter.formatToMoneyUS(cuenta.getSaldoSinInteres().doubleValue(), 2) + "</td>");
                    }
                    out.println("<td id='tdCuenMoneda" + i + "'>" + cuenta.getTMoneda().getNombre() + "</td>");
                    out.println("<td id='tdCuenObservacion" + i + "'>" + cuenta.getObservaciones() + "</td>");
                    out.println("<td id='tdFechaHoy" + i + "' style='display:none'>" + fechahoy + "</td>");
                    out.println("<td id='tdInteres" + i + "' style='display:none'>" + interes + "</td>");
                    out.println("<td id='tdIdPersonas" + i + "' style='display:none'>" + cuenta.getTPersona().getIdUserPk() + "</td>");
                    out.println("<td id='tdIdMoney" + i + "' style='display:none'>" + cuenta.getTMoneda().getCodMoneda() + "</td>");
                    out.println("<td id='tdIdCuenPersona" + i + "' style='display:none'>" + cuenta.getIdcuentapersona() + "</td>");
                    out.println("<td id='tdObservacionCuen" + i + "' style='display:none'>" + cuenta.getObservaciones() + "</td>");
                    out.println("<td id='ttdN" + i + "' style='display:none'>" + cuenta.getTPersona().getNombre() + "</td>");
                    out.println("<td id='ttdA" + i + "' style='display:none'>" + cuenta.getTPersona().getApellidos() + "</td>");
                    out.println("<td id='ttdDNI" + i + "' style='display:none'>" + cuenta.getTPersona().getDocIdentidad() + "</td>");
                    out.println("<td id='ttdRUC" + i + "' style='display:none'>" + cuenta.getTPersona().getRuc() + "</td>");
                    if (cuenta.getTTipoCuenta().getCodigoCuenta().equals("CPF")) {
                        out.println("<td id='tdfechaplazoF" + i + "' style='display:none'>" + PrestamoUtil.eedatefmt(PrestamoUtil.fmtdate9, Double.parseDouble(fechaplazofijo)).replace(",", "") + "</td>");
                    } else {
                        out.println("<td id='tdfechaplazoF" + i + "' style='display:none'> </td>");
                    }
                    out.println("<td id='tdFechaCap" + i + "' style='display:none'>" + cuenta.getFechaCap() + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
                out.println("</fieldset>");
            } else {
                out.println("<center>");
                out.println("<fieldset style='border-width:3px'>");
                out.println("<legend><b>CUENTAS DEL USUARIO:</b></legend>");
                out.println("<table id='tablamiscuentas' class='tabla'>");
                out.println("<tr class='modo1'>");
                out.println("<td style='color:red;font-size: 15px'>NO SE ENCONTRO NINGUNA CUENTA</td>");
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
