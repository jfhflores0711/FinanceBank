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
import org.finance.bank.bean.TCobranza;
import org.finance.bank.bean.TOperacion;
import org.finance.bank.bean.TRegistroCompraVenta;
import org.finance.bank.bean.TRegistroDepositoRetiro;
import org.finance.bank.bean.TRegistroGiro;
import org.finance.bank.bean.TRegistroOtros;
import org.finance.bank.bean.TRegistroPrestamo;
import org.finance.bank.model.dao.DAOGeneral;
import org.finance.bank.util.CurrencyConverter;
import org.finance.bank.util.DateUtil;

public class SBuscarOp extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        try {
            String numOperacion = request.getParameter("numOP");
            String tipoOperacion = request.getParameter("tipoOperacion");
            DAOGeneral dao=new DAOGeneral();
            String hql = "";
            String tipo_rol = (String) session.getAttribute("USER_DESCRIPCION_ROLE");
            String cod_caja = (String) session.getAttribute("USER_CODCAJA");
            if (tipoOperacion.equals("TODO") && tipo_rol.equals("ADMINISTRADOR")) {
                hql = "from TOperacion op where op.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' order by op.fecha desc";
            } else if (tipoOperacion.equals("COMPRA") && tipo_rol.equals("ADMINISTRADOR")) {
                hql = "from TOperacion op where op.numeroOperacion like '" + numOperacion + "' AND op.TTipoOperacion.nombre='COMPRA' AND op.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' order by op.fecha desc";
            } else if (tipoOperacion.equals("VENTA") && tipo_rol.equals("ADMINISTRADOR")) {
                hql = "from TOperacion op where op.numeroOperacion like '" + numOperacion + "' AND op.TTipoOperacion.nombre='VENTA' AND op.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' order by op.fecha desc";
            } else if (tipoOperacion.equals("DEPOSITO O RETIRO") && tipo_rol.equals("ADMINISTRADOR")) {
                hql = "from TOperacion op where op.numeroOperacion like '" + numOperacion + "' AND (op.TTipoOperacion.nombre='DEPOSITO' OR op.TTipoOperacion.nombre='RETIRO') AND op.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' order by op.fecha desc";
            } else if (tipoOperacion.equals("GIRO") && tipo_rol.equals("ADMINISTRADOR")) {
                hql = "from TOperacion op where op.numeroOperacion like '" + numOperacion + "' AND op.TTipoOperacion.nombre='GIRO' AND op.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' order by op.fecha desc";
            } else if (tipoOperacion.equals("COBROGIRO") && tipo_rol.equals("ADMINISTRADOR")) {
                hql = "from TOperacion op where op.numeroOperacion like '" + numOperacion + "' AND op.TTipoOperacion.nombre='COBROGIRO' AND op.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' order by op.fecha desc";
            } else if (tipoOperacion.equals("PRESTAMO") && tipo_rol.equals("ADMINISTRADOR")) {
                hql = "from TOperacion op where op.numeroOperacion like '" + numOperacion + "' AND op.TTipoOperacion.nombre='PRESTAMO' AND op.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' order by op.fecha desc";
            } else if (tipoOperacion.equals("COBRANZA") && tipo_rol.equals("ADMINISTRADOR")) {
                hql = "from TOperacion op where op.numeroOperacion like '" + numOperacion + "' AND op.TTipoOperacion.nombre='COBRANZA' AND op.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' order by op.fecha desc";
            } else if (tipoOperacion.equals("RETIRO OTRO") && tipo_rol.equals("ADMINISTRADOR")) {
                hql = "from TOperacion op where op.numeroOperacion like '" + numOperacion + "' AND op.TTipoOperacion.nombre='RETIRO OTRO' AND op.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' order by op.fecha desc";
            }
            if (tipoOperacion.equals("TODO") && !(tipo_rol.equals("ADMINISTRADOR"))) {
                hql = "from TOperacion op where op.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' AND op.TPersonaCaja.TCaja.codCaja='" + cod_caja + "' order by op.fecha desc";
            } else if (tipoOperacion.equals("COMPRA") && !(tipo_rol.equals("ADMINISTRADOR"))) {
                hql = "from TOperacion op where op.numeroOperacion like '" + numOperacion + "' AND op.TTipoOperacion.nombre='COMPRA' AND op.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' AND op.TPersonaCaja.TCaja.codCaja='" + cod_caja + "' order by op.fecha desc";
            } else if (tipoOperacion.equals("VENTA") && !(tipo_rol.equals("ADMINISTRADOR"))) {
                hql = "from TOperacion op where op.numeroOperacion like '" + numOperacion + "' AND op.TTipoOperacion.nombre='VENTA' AND op.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' AND op.TPersonaCaja.TCaja.codCaja='" + cod_caja + "' order by op.fecha desc";
            } else if (tipoOperacion.equals("DEPOSITO O RETIRO") && !(tipo_rol.equals("ADMINISTRADOR"))) {
                hql = "from TOperacion op where op.numeroOperacion like '" + numOperacion + "' AND (op.TTipoOperacion.nombre='DEPOSITO' OR op.TTipoOperacion.nombre='RETIRO') AND op.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' AND op.TPersonaCaja.TCaja.codCaja='" + cod_caja + "' order by op.fecha desc";
            } else if (tipoOperacion.equals("GIRO") && !(tipo_rol.equals("ADMINISTRADOR"))) {
                hql = "from TOperacion op where op.numeroOperacion like '" + numOperacion + "' AND op.TTipoOperacion.nombre='GIRO' AND op.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' AND op.TPersonaCaja.TCaja.codCaja='" + cod_caja + "' order by op.fecha desc";
            } else if (tipoOperacion.equals("COBROGIRO") && !(tipo_rol.equals("ADMINISTRADOR"))) {
                hql = "from TOperacion op where op.numeroOperacion like '" + numOperacion + "' AND op.TTipoOperacion.nombre='COBROGIRO' AND op.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' AND op.TPersonaCaja.TCaja.codCaja='" + cod_caja + "' order by op.fecha desc";
            } else if (tipoOperacion.equals("PRESTAMO") && !(tipo_rol.equals("ADMINISTRADOR"))) {
                hql = "from TOperacion op where op.numeroOperacion like '" + numOperacion + "' AND op.TTipoOperacion.nombre='PRESTAMO' AND op.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' AND op.TPersonaCaja.TCaja.codCaja='" + cod_caja + "' order by op.fecha desc";
            } else if (tipoOperacion.equals("COBRANZA") && !(tipo_rol.equals("ADMINISTRADOR"))) {
                hql = "from TOperacion op where op.numeroOperacion like '" + numOperacion + "' AND op.TTipoOperacion.nombre='COBRANZA' AND op.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' AND op.TPersonaCaja.TCaja.codCaja='" + cod_caja + "' order by op.fecha desc";
            } else if (tipoOperacion.equals("RETIRO OTRO") && !(tipo_rol.equals("ADMINISTRADOR"))) {
                hql = "from TOperacion op where op.numeroOperacion like '" + numOperacion + "' AND op.TTipoOperacion.nombre='RETIRO OTRO' AND op.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' AND op.TPersonaCaja.TCaja.codCaja='" + cod_caja + "' order by op.fecha desc";
            }
            List result = dao.findAll(hql);
            Iterator it = result.iterator();
            String nombres = (String) session.getAttribute("USER_NAME");
            String codCaja = (String) session.getAttribute("USER_CODCAJA");
            String filial = (String) session.getAttribute("USER_FILIAL");
            filial = filial.replace("FILIAL", "OFICINA");
            DateFormat idfecha3 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            out.println("<table>");
            out.println("<tr>");
            out.println("<td>" + filial + "</td>");
            out.println("<td>CAJA:" + codCaja + "</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td>Fecha y Hora: </td>");
            out.println("<td>" + idfecha3.format(new Date()) + "</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td>Nombre:</td>");
            out.println("<td>" + nombres + "</td>");
            out.println("</tr>");
            out.println("</table>");
            out.println("<table id='tablaOperacion' class='tabla' border='0'>");
            out.println("<tr>");
            out.println("<th style='display:none'>-.-</th>");
            out.println("<th>Num. Operaci&oacute;n</th>");
            out.println("<th>Fecha de Registro</th>");
            out.println("<th>Tipo Operaci&oacute;n</th>");
            out.println("<th>Monto</th>");
            out.println("<th>Moneda</th>");
            out.println("<th>Descripci&oacute;n</th>");
            out.println("<th>Estado</th>");
            out.println("<th>Usuario</th>");
            out.println("<th>Fecha Extorno</th>");
            out.println("<th>Admin Extorno</th>");
            out.println("<th style='display:none'>-..-</th>");
            out.println("</tr>");
            int i = 0;
            BigDecimal total = BigDecimal.ZERO;
            while (it.hasNext()) {
                TOperacion oper = (TOperacion) it.next();
                String par = "";
                i = i + 1;
                if (i % 2 == 0) {
                    par = "modo2";
                } else {
                    par = "modo1";
                }
                BigDecimal Monto = BigDecimal.ZERO;
                if (oper.getTTipoOperacion().getNombre().equals("COMPRA")) {
                    TRegistroCompraVenta ComVent = (TRegistroCompraVenta) dao.findAll("from TRegistroCompraVenta where TOperacion='" + oper.getIdOperacion() + "'").get(0);
                    Monto = ComVent.getMontoRecibido();
                } else if (oper.getTTipoOperacion().getNombre().equals("VENTA")) {
                    TRegistroCompraVenta ComVent = (TRegistroCompraVenta) dao.findAll("from TRegistroCompraVenta where TOperacion='" + oper.getIdOperacion() + "'").get(0);
                    Monto = ComVent.getMontoEntregado();
                } else if (oper.getTTipoOperacion().getNombre().equals("DEPOSITO") || oper.getTTipoOperacion().getNombre().equals("RETIRO")) {
                    TRegistroDepositoRetiro DepRet = (TRegistroDepositoRetiro) dao.findAll("from TRegistroDepositoRetiro where TOperacion='" + oper.getIdOperacion() + "'").get(0);
                    Monto = DepRet.getImporte();
                } else if (oper.getTTipoOperacion().getNombre().equals("GIRO") || oper.getTTipoOperacion().getNombre().equals("COBROGIRO")) {
                    TRegistroGiro RegGiro = (TRegistroGiro) dao.findAll("from TRegistroGiro where TOperacion='" + oper.getIdOperacion() + "'").get(0);
                    Monto = RegGiro.getImporte();
                } else if (oper.getTTipoOperacion().getNombre().equals("PRESTAMO")) {
                    TRegistroPrestamo RegPres = (TRegistroPrestamo) dao.findAll("from TRegistroPrestamo where TOperacion='" + oper.getIdOperacion() + "'").get(0);
                    Monto = RegPres.getMonto();
                } else if (oper.getTTipoOperacion().getNombre().equals("PAGO")) {
                    TCobranza Cobranza = (TCobranza) dao.findAll("from TCobranza where TOperacion='" + oper.getIdOperacion() + "'").get(0);
                    Monto = Cobranza.getTDetallePrestamo().getMontoTotal();
                } else if (oper.getTTipoOperacion().getNombre().equals("RETIRO OTRO")) {
                    TRegistroOtros RegOtros = (TRegistroOtros) dao.findAll("from TRegistroOtros where TOperacion='" + oper.getIdOperacion() + "'").get(0);
                    Monto = RegOtros.getMonto();
                }
                total = total.add(Monto);
                out.println("<tr class=" + par + ">");
                out.println("<td id='r' style='display:none'>" + oper.getIdOperacion() + "</td>");
                out.println("<td>" + oper.getNumeroOperacion() + "</td>");
                out.println("<td>" + oper.getFecha() + "</td>");
                out.println("<td>" + oper.getTTipoOperacion().getNombre() + "</td>");
                out.println("<td>" + CurrencyConverter.formatToMoneyUS(Monto.doubleValue(), 2) + "</td>");
                out.println("<td>" + oper.getTMoneda().getNombre() + "</td>");
                out.println("<td>" + oper.getDescripcion() + "</td>");
                out.println("<td>" + oper.getEstado() + "</td>");
                out.println("<td>" + oper.getTPersonaCaja().getTPersona().getNombre() + " " + oper.getTPersonaCaja().getTPersona().getApellidos() + "</td>");
                String fechaextor = "";
                if (oper.getFechaExtornacion() != null) {
                    fechaextor = oper.getFechaExtornacion().substring(0, 19);
                }
                out.println("<td>" + fechaextor + "</td>");
                String idAdminExorno = "";
                if (oper.getIdAdminExtorno() != null) {
                    idAdminExorno = oper.getIdAdminExtorno();
                }
                out.println("<td>" + idAdminExorno + "</td>");
                out.println("</tr>");
            }
            out.println("<tr style='display:none'>");
            out.println("<td id='tdIdAdmin'>" + session.getAttribute("USER_ID") + "</td>");
            out.println("</tr>");
            out.println("</table>");
            out.println("<table>");
            out.println("<tr >");
            out.println("<td >Monto Total</td>");
            out.println("</tr>");
            out.println("</table>");
            out.println("<div id=´'ctrl'>");
            out.println("<center>");
            out.println("<br>");
            out.println("<input name='Submit' id='Submit' type='button' onClick=\"document.title=''; if (window.print)window.print();else alert('Su Navegador no dispone de esta opcion, Actualicelo con una version mas reciente');\" value='Imprimir'");
            out.println("<input name='btnRegresar' id='btnRegresar' type='button' onClick='regresarMenu();' value='Regresar'>");
            out.println("</center>");
            out.println("<form action='managercaja.htm' method='post' id='frmregresar' name='frmregresar'>");
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
