package org.finance.bank.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.finance.bank.bean.TOperacion;
import org.finance.bank.bean.TRegistroGiro;
import org.finance.bank.model.dao.DAOGeneral;
import org.finance.bank.util.DateUtil;

public class SBuscarOperacion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        DAOGeneral dao = new DAOGeneral();
        try {
            String numOperacion = request.getParameter("numOP");
            String tipoOperacion = request.getParameter("tipoOperacion");
            String tipo_rol = (String) session.getAttribute("USER_DESCRIPCION_ROLE");
            String cod_caja = (String) session.getAttribute("USER_CODCAJA");
            if (tipo_rol == null || cod_caja == null) {
                out.print("NO SE PUEDE HACER CONSULTAS");
                out.close();
                return;
            }
            String hql = "";
            if (tipo_rol.equals("ADMINISTRADOR")) {
                if (tipoOperacion.equals("TODO")) {
                    hql = "from TOperacion op where op.numeroOperacion like '%" + numOperacion.trim() + "' AND op.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' AND op.estado='ACTIVO' order by op.fecha desc";
                } else if (tipoOperacion.equals("CAJA")) {
                    hql = "from TOperacion op where op.TPersonaCaja.TCaja.codCaja ='" + session.getAttribute("COD_CAJA") + "' AND op.TTipoOperacion.nombre='COMPRA' AND op.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' AND op.estado='ACTIVO' order by op.fecha desc";
                } else if (tipoOperacion.equals("COMPRA")) {
                    hql = "from TOperacion op where op.numeroOperacion like '%" + numOperacion.trim() + "' AND op.TTipoOperacion.nombre='COMPRA' AND op.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' AND op.estado='ACTIVO' order by op.fecha desc";
                } else if (tipoOperacion.equals("VENTA")) {
                    hql = "from TOperacion op where op.numeroOperacion like '%" + numOperacion.trim() + "' AND op.TTipoOperacion.nombre='VENTA' AND op.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' AND op.estado='ACTIVO' order by op.fecha desc";
                } else if (tipoOperacion.equals("DEPOSITO O RETIRO")) {
                    hql = "from TOperacion op where op.numeroOperacion like '%" + numOperacion.trim() + "' AND (op.TTipoOperacion.nombre='DEPOSITO' OR op.TTipoOperacion.nombre='RETIRO') AND op.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' AND op.estado='ACTIVO' order by op.fecha desc";
                } else if (tipoOperacion.equals("GIRO")) {
                    hql = "from TOperacion op where op.numeroOperacion like '%" + numOperacion.trim() + "' AND op.TTipoOperacion.nombre='GIRO' AND op.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' AND op.estado='ACTIVO' order by op.fecha desc";
                } else if (tipoOperacion.equals("COBROGIRO")) {
                    hql = "from TOperacion op where op.numeroOperacion like '%" + numOperacion.trim() + "' AND op.TTipoOperacion.nombre='COBROGIRO' AND op.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' AND op.estado='ACTIVO' order by op.fecha desc";
                } else if (tipoOperacion.equals("PRESTAMO")) {
                    hql = "from TOperacion op where op.numeroOperacion like '%" + numOperacion.trim() + "' AND op.TTipoOperacion.nombre='PRESTAMO' AND op.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' AND op.estado='ACTIVO' order by op.fecha desc";
                } else if (tipoOperacion.equals("COBRANZA")) {
                    hql = "from TOperacion op where op.numeroOperacion like '%" + numOperacion.trim() + "' AND op.TTipoOperacion.nombre='COBRANZA' AND op.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' AND op.estado='ACTIVO' order by op.fecha desc";
                } else if (tipoOperacion.equals("RETIRO OTRO")) {
                    hql = "from TOperacion op where op.numeroOperacion like '%" + numOperacion.trim() + "' AND op.TTipoOperacion.nombre='RETIRO OTRO' AND op.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' AND op.estado='ACTIVO' order by op.fecha desc";
                } else if (tipoOperacion.equals("TRANSFERENCIAS")) {
                    hql = "from TOperacion op where op.numeroOperacion like '%" + numOperacion.trim() + "' AND op.TTipoOperacion.nombre='TRANSFERENCIA' AND op.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' AND op.estado='ACTIVO' order by op.fecha desc";
                }
            } else {
                if (tipoOperacion.equals("TODO")) {
                    hql = "from TOperacion op where op.numeroOperacion like '%" + numOperacion.trim() + "%' AND op.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' AND op.estado='ACTIVO' AND op.TPersonaCaja.TCaja.codCaja='" + cod_caja + "' order by op.fecha desc";
                } else if (tipoOperacion.equals("CAJA")) {
                    hql = "from TOperacion op where op.TPersonaCaja.TCaja.codCaja ='" + session.getAttribute("COD_CAJA") + "' AND op.TTipoOperacion.nombre='COMPRA' AND op.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' AND op.estado='ACTIVO' AND op.TPersonaCaja.TCaja.codCaja='" + cod_caja + "' order by op.fecha desc";
                } else if (tipoOperacion.equals("COMPRA")) {
                    hql = "from TOperacion op where op.numeroOperacion like '%" + numOperacion.trim() + "%' AND op.TTipoOperacion.nombre='COMPRA' AND op.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' AND op.estado='ACTIVO' AND op.TPersonaCaja.TCaja.codCaja='" + cod_caja + "' order by op.fecha desc";
                } else if (tipoOperacion.equals("VENTA")) {
                    hql = "from TOperacion op where op.numeroOperacion like '%" + numOperacion.trim() + "%' AND op.TTipoOperacion.nombre='VENTA' AND op.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' AND op.estado='ACTIVO' AND op.TPersonaCaja.TCaja.codCaja='" + cod_caja + "' order by op.fecha desc";
                } else if (tipoOperacion.equals("DEPOSITO O RETIRO")) {
                    hql = "from TOperacion op where op.numeroOperacion like '%" + numOperacion.trim() + "%' AND (op.TTipoOperacion.nombre='DEPOSITO' OR op.TTipoOperacion.nombre='RETIRO') AND op.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' AND op.estado='ACTIVO' AND op.TPersonaCaja.TCaja.codCaja='" + cod_caja + "' order by op.fecha desc";
                } else if (tipoOperacion.equals("GIRO")) {
                    hql = "from TOperacion op where op.numeroOperacion like '%" + numOperacion.trim() + "%' AND op.TTipoOperacion.nombre='GIRO' AND op.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' AND op.estado='ACTIVO' AND op.TPersonaCaja.TCaja.codCaja='" + cod_caja + "' order by op.fecha desc";
                } else if (tipoOperacion.equals("COBROGIRO")) {
                    hql = "from TOperacion op where op.numeroOperacion like '%" + numOperacion.trim() + "%' AND op.TTipoOperacion.nombre='COBROGIRO' AND op.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' AND op.estado='ACTIVO' AND op.TPersonaCaja.TCaja.codCaja='" + cod_caja + "' order by op.fecha desc";
                } else if (tipoOperacion.equals("PRESTAMO")) {
                    hql = "from TOperacion op where op.numeroOperacion like '%" + numOperacion.trim() + "%' AND op.TTipoOperacion.nombre='PRESTAMO' AND op.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' AND op.estado='ACTIVO' AND op.TPersonaCaja.TCaja.codCaja='" + cod_caja + "' order by op.fecha desc";
                } else if (tipoOperacion.equals("COBRANZA")) {
                    hql = "from TOperacion op where op.numeroOperacion like '%" + numOperacion.trim() + "%' AND op.TTipoOperacion.nombre='COBRANZA' AND op.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' AND op.estado='ACTIVO' AND op.TPersonaCaja.TCaja.codCaja='" + cod_caja + "' order by op.fecha desc";
                } else if (tipoOperacion.equals("RETIRO OTRO")) {
                    hql = "from TOperacion op where op.numeroOperacion like '%" + numOperacion.trim() + "%' AND op.TTipoOperacion.nombre='RETIRO OTRO' AND op.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' AND op.estado='ACTIVO' AND op.TPersonaCaja.TCaja.codCaja='" + cod_caja + "' order by op.fecha desc";
                } else if (tipoOperacion.equals("RETIRO OTRO")) {
                    hql = "from TOperacion op where op.numeroOperacion like '%" + numOperacion.trim() + "%' AND op.TTipoOperacion.nombre='TRANSFERENCIA' AND op.fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", new Date()) + "%' AND op.estado='ACTIVO' AND op.TPersonaCaja.TCaja.codCaja='" + cod_caja + "' order by op.fecha desc";
                }
            }
            List result = dao.findAll(hql);
            Iterator it = result.iterator();
            out.println("<table id='tablaOperacion' class='tabla' border='0'>");
            out.println("<tr>");
            out.println("<th style='display:none'>-.-</th>");
            out.println("<th>Num. Operaci&oacute;n, Nombre</th>");
            out.println("<th>Fecha de Registro</th>");
            out.println("<th>Tipo Operaci&oacute;n</th>");
            out.println("<th>Moneda</th>");
            out.println("<th>-.-</th>");
            out.println("</tr>");
            int i = 0;
            while (it.hasNext()) {
                TOperacion oper = (TOperacion) it.next();
                if (oper.getTTipoOperacion().getCodigoTipoOperacion().equals("TIPC5")) {
                    Set rgiros = oper.getTRegistroGiros();
                    if (rgiros.isEmpty()) {
                        continue;
                    } else {
                        TRegistroGiro rg = (TRegistroGiro) rgiros.iterator().next();
                        if (rg.getEstado().equals("COBRADO")) {
                            continue;
                        }
                    }
                }
                String par = "";
                i = i + 1;
                if (i % 2 == 0) {
                    par = "modo2";
                } else {
                    par = "modo1";
                }
                out.println("<tr class=" + par + ">");
                out.println("<td id='r' style='display:none'>" + oper.getIdOperacion() + "</td>");
                out.println("<td width='700px'>" + oper.getNumeroOperacion() + "<br>"
                        + oper.getTPersonaCaja().getTPersona().getNombre() + " " + oper.getTPersonaCaja().getTPersona().getApellidos() + "</td>");
                out.println("<td>" + oper.getFecha() + "</td>");
                out.println("<td>" + oper.getTTipoOperacion().getNombre() + "</td>");
                out.println("<td>" + oper.getTMoneda().getNombre() + "</td>");
                if (!oper.getTTipoOperacion().getCodigoTipoOperacion().equals("TIPC11") && !oper.getTTipoOperacion().getCodigoTipoOperacion().equals("TIPC12") && !oper.getTTipoOperacion().getCodigoTipoOperacion().equals("TIP13") && !oper.getTTipoOperacion().getCodigoTipoOperacion().equals("TIPC14")) {
                    out.println("<td><input type='button' id='btnExtornar" + i + "' value='Extornar' onclick=\"ventanaNueva2('" + oper.getIdOperacion() + "');\"></td>");
                } else {
                    out.println("<td>NO EXTORNABLE</td>");
                }
                out.println("</tr>");
            }
            out.println("<tr style='display:none'>");
            out.println("<td id='tdIdAdmin'>" + session.getAttribute("USER_LOGIN") + "</td>");
            out.println("<td id='tdRole'>" + tipo_rol + "</td>");
            out.println("</tr>");
            out.println("</table>");
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
