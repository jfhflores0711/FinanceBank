package org.finance.bank.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.finance.bank.bean.TBalancexmoneda;
import org.finance.bank.bean.TCaja;
import org.finance.bank.bean.TCobranza;
import org.finance.bank.bean.TMoneda;
import org.finance.bank.bean.TOperacion;
import org.finance.bank.bean.TPatrimonioTransit;
import org.finance.bank.bean.TRegistroCompraVenta;
import org.finance.bank.bean.TRegistroDepositoRetiro;
import org.finance.bank.bean.TRegistroGiro;
import org.finance.bank.bean.TRegistroOtros;
import org.finance.bank.bean.TRegistroPrestamo;
import org.finance.bank.bean.TTransferenciaCaja;
import org.finance.bank.model.dao.DAOGeneral;
import org.finance.bank.model.iniPatrimonio;
import org.finance.bank.util.DateUtil;

/**
 *
 * @author admin
 */
public class SReportAdmin extends HttpServlet {

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
        out.println("<html>");
        out.println("<head>");
        out.println("<title>REPORTE DE ADMINISTRACI&Oacute;N</title>");
        HttpSession session = request.getSession(true);
        String type = request.getParameter("type");
        if (type == null) {
            out.print("<body>HA OCURRIDO UN ERROR DE INICIALIZAR VARIABLES!!!<br><input type='button' value='Cerrar' onclick='window.close();'></body></html>");
            out.close();
            return;
        }
        DAOGeneral dao = new DAOGeneral();
        StringBuffer sb = new StringBuffer();
        String hql = "";
        Date f1 = null;
        Date f2 = null;
        sb.append("<table border='0' cellpadding='0' cellspacing='0' >");
        sb.append("<tr>");
        sb.append("<td valign='top'>");
        String nc = request.getParameter("conf");
        String ff2 = request.getParameter("hasta");
        boolean resetedTransit = true;
        int c = 0;
        if (nc != null) {
            if (nc.equals("con")) {
                c = 1;
            } else {
                c = 2;
            }
        }
        if (c == 1) {
            //confirm the monts of day, without of now.
            out.print("<body>ACTUALIZADO!!!<br><input type='button' value='Cerrar' onclick='window.close();'></body></html>");
            out.close();
            dao.cerrarSession();
            return;
        } else {
            if (ff2 == null) {
                ff2 = DateUtil.getDateTime("dd/MM/yyyy", new Date());
            }
            resetedTransit = resetNowChanges(ff2, dao);
            Logger.getLogger(SReportAdmin.class.getName()).log(Level.INFO, "c = " + resetedTransit);
        }
        if ("now".equals(type)) {
            f1 = new Date();
            hql = "from TOperacion where fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", f1) + "%' order by fecha";
            List lnow = dao.findAll(hql);
            if (lnow.isEmpty()) {
                sb.append("<font size='1'>NO HAY OPERACIONES EN EL SISTEMA QUE HAYAN MODIFICADO B&Oacute;VEDA INICIAL</font>");
            } else {
                sb.append("<table border='1' cellpadding='0' cellspacing='0.5' style='font-size:9pt'>");
                sb.append("<thead><tr>"
                        + "<th>CAJA</th>"
                        + "<th>FECHA Y HORA</th>"
                        + "<th>NUM. OPER.</th>"
                        + "<th>MON. ENTREGADO</th>"
                        + "<th>MON. RECIBIDO</th>"
                        + "<th>TIPO OPER.</th>"
                        + "<th>REFERENCIA</th>"
                        + "<th>CAJAS</th>"
                        + "<th>CtaxCOBRAR</th>"
                        + "<th>CAPITAL</th>"
                        + "<th>CtaxPAGAR</th>"
                        + "<th>SOBREGs.</th>"
                        + "<th>PRESTs.</th>"
                        + "</tr>");
                sb.append("</thead><tbody>");
                Iterator inow = lnow.iterator();
                while (inow.hasNext()) {
                    TOperacion to = (TOperacion) inow.next();
                    if (to == null) {
                        sb.append("<tr><td>ERROR:</td><td colspan='13'>NO se pudo leer los datos de la operacion " + "</td></tr>");
                        continue;
                    }
                    String n = taxRecover(to, dao, resetedTransit);
                    sb.append("<tr><td>" + to.getTPersonaCaja().getTCaja().getCodCaja()
                            + "</td><td>" + to.getFecha()
                            + "</td><td>" + to.getNumeroOperacion().substring(10) + "</td><td>");
                    sb.append(n);
                    sb.append("</td></tr>");
                }
                sb.append("</tbody></table>");
                sb.append("<TABLE border='1'><thead><tr>"
                        + "<th>FECHA Y HORA</th>"
                        + "<th>PATRIMONIO T.</th>"
                        + "<th>CAJA Y BCOS.</th>"
                        + "<th>Ctas.xCOBRAR</th>"
                        + "<th>CAPITAL TOTAL</th>"
                        + "<th>Ctas.xPAGAR</th>"
                        + "<th>SOBREGIROS</th>"
                        + "<th>PRESTAMOS</th>"
                        + "</tr></THEAD><tbody>");
                List mons = dao.findAll("from TMoneda where estado='ACTIVO'");
                if (!mons.isEmpty()) {
                    Iterator iMons = mons.iterator();
                    while (iMons.hasNext()) {
                        TMoneda m = (TMoneda) iMons.next();
                        List l = dao.findAll("from TCaja where estado = 'ACTIVO'");
                        Iterator f = l.iterator();
                        while (f.hasNext()) {
                            TCaja cx = (TCaja) f.next();
                            TBalancexmoneda bi = (TBalancexmoneda) dao.load(TBalancexmoneda.class, DateUtil.getDateAnt(f1).replaceAll("/", "") + cx.getCodCaja() + m.getCodParaNumCuenta() + "00");
                            TBalancexmoneda bf = (TBalancexmoneda) dao.load(TBalancexmoneda.class, DateUtil.getDate(f1).replaceAll("/", "") + cx.getCodCaja() + m.getCodParaNumCuenta() + "00");
                            if (bi != null && bf != null) {
                                sb.append("<tr><td colspan='8'>");
                                sb.append("MONEDA:INICIAL / FINAL: " + m.getNombre() + "</td></tr><tr><td>");
                                sb.append("" + bi.getDateUser() + "</td><td>"
                                        + getPatTotal(bi) + "</td><td>"
                                        + DateUtil.eedisplayFloatNDTh(bi.getActivoCajaybanco().doubleValue(), 4) + "</td><td>"
                                        + DateUtil.eedisplayFloatNDTh(bi.getActivoCuentaxcobrar().doubleValue(), 4) + "</td><td>"
                                        + getKapTotal(bi) + "</td><td>"
                                        + DateUtil.eedisplayFloatNDTh(bi.getPasivo().doubleValue(), 4) + "</td><td>"
                                        + DateUtil.eedisplayFloatNDTh(bi.getEncaje().doubleValue(), 4) + "</td><td>"
                                        + DateUtil.eedisplayFloatNDTh(bi.getPRespaldo().doubleValue(), 4) + "</td></tr><tr><td>");
                                sb.append("" + bf.getDateUser() + "</td><td>"
                                        + getPatTotal(bf) + "</td><td>"
                                        + DateUtil.eedisplayFloatNDTh(bf.getActivoCajaybanco().doubleValue(), 4) + "</td><td>"
                                        + DateUtil.eedisplayFloatNDTh(bf.getActivoCuentaxcobrar().doubleValue(), 4) + "</td><td>"
                                        + getKapTotal(bf) + "</td><td>"
                                        + DateUtil.eedisplayFloatNDTh(bf.getPasivo().doubleValue(), 4) + "</td><td>"
                                        + DateUtil.eedisplayFloatNDTh(bf.getEncaje().doubleValue(), 4) + "</td><td>"
                                        + DateUtil.eedisplayFloatNDTh(bf.getPRespaldo().doubleValue(), 4) + "</td></tr>");
                            } else {
                                sb.append("<tr><td>NO se pueden mostrar los montos del balance</td></tr>");
                            }
                        }
                    }
                    sb.append("</tbody></table>");
                }
            }
        } else {
            if ("lease".equals(type)) {
                String ff1 = "27/04/2011";
                if (ff2 == null) {
                    out.print("<body>HA OCURRIDO UN ERROR DE INICIALIZAR VARIABLES!!!</body></html>");
                    out.close();
                    return;
                }
                f1 = DateUtil.convertStringToDate("dd/MM/yyyy", ff1);
                f2 = DateUtil.convertStringToDate("dd/MM/yyyy", ff2);
                int days = DateUtil.daysDifBetween(f1, f2);
                if (days > 0) {
                    if (f1.after(f2)) {
                        sb.append("ERROR:</td><td colspan='12'>NO se puede reportar ninguna operación, ya que no existen..." + "</td></tr>");
                    } else {
                        hql = "from TOperacion where fecha like '" + DateUtil.getDateTime("yyyy/MM/dd", f2) + "%' order by fecha";
                        List lnow = dao.findAll(hql);
                        if (lnow.isEmpty()) {
                            sb.append("<font size='1'>NO HAY OPERACIONES EN EL SISTEMA QUE HAYAN MODIFICADO B&Oacute;VEDA INICIAL</font>");
                        } else {
                            sb.append("<table border='1' cellpadding='0' cellspacing='0.5' style='font-size:9pt'>");
                            sb.append("<thead><tr>"
                                    + "<th>CAJA</th>"
                                    + "<th>FECHA Y HORA</th>"
                                    + "<th>NUM. OPER.</th>"
                                    + "<th>MON. ENTREGADO</th>"
                                    + "<th>MON. RECIBIDO</th>"
                                    + "<th>TIPO OPER.</th>"
                                    + "<th>REFERENCIA</th>"
                                    + "<th>CAJAS</th>"
                                    + "<th>CtaxCOBRAR</th>"
                                    + "<th>CAPITAL</th>"
                                    + "<th>CtaxPAGAR</th>"
                                    + "<th>SOBREGs.</th>"
                                    + "<th>PRESTs.</th>"
                                    + "</tr>");
                            sb.append("</thead><tbody>");
                            Iterator inow = lnow.iterator();
                            while (inow.hasNext()) {
                                TOperacion to = (TOperacion) inow.next();
                                if (to == null) {
                                    sb.append("<tr><td>ERROR:</td><td colspan='13'>NO se pudo leer los datos de la operacion " + "</td></tr>");
                                    continue;
                                }
                                String n = taxRecover(to, dao, resetedTransit);
                                sb.append("<tr><td>" + to.getTPersonaCaja().getTCaja().getCodCaja()
                                        + "</td><td>" + to.getFecha()
                                        + "</td><td>" + to.getNumeroOperacion().substring(10) + "</td><td>");
                                sb.append(n);
                                sb.append("</td></tr>");
                            }
                            sb.append("</tbody></table>");
                            sb.append("<TABLE border='1'><thead><tr>"
                                    + "<th>FECHA Y HORA</th>"
                                    + "<th>PATRIMONIO T.</th>"
                                    + "<th>CAJA Y BCOS.</th>"
                                    + "<th>Ctas.xCOBRAR</th>"
                                    + "<th>CAPITAL TOTAL</th>"
                                    + "<th>Ctas.xPAGAR</th>"
                                    + "<th>SOBREGIROS</th>"
                                    + "<th>PRESTAMOS</th>"
                                    + "</tr></THEAD><tbody>");
                            List mons = dao.findAll("from TMoneda where estado='ACTIVO'");
                            if (!mons.isEmpty()) {
                                Iterator iMons = mons.iterator();
                                while (iMons.hasNext()) {
                                    TMoneda m = (TMoneda) iMons.next();
                                    List l = dao.findAll("from TCaja where estado = 'ACTIVO'");
                                    Iterator f = l.iterator();
                                    while (f.hasNext()) {
                                        TCaja cx = (TCaja) f.next();
                                        TBalancexmoneda bi = (TBalancexmoneda) dao.load(TBalancexmoneda.class, DateUtil.getDateAnt(f2).replaceAll("/", "") + cx.getCodCaja() + m.getCodParaNumCuenta() + "00");
                                        TBalancexmoneda bf = (TBalancexmoneda) dao.load(TBalancexmoneda.class, DateUtil.getDate(f2).replaceAll("/", "") + cx.getCodCaja() + m.getCodParaNumCuenta() + "00");
                                        if (bi != null && bf != null) {
                                            sb.append("<tr><td colspan='8'>");
                                            sb.append("MONEDA:INICIAL / FINAL: " + m.getNombre() + "</td></tr><tr><td>");
                                            sb.append("" + bi.getDateUser() + "</td><td>"
                                                    + getPatTotal(bi) + "</td><td>"
                                                    + DateUtil.eedisplayFloatNDTh(bi.getActivoCajaybanco().doubleValue(), 4) + "</td><td>"
                                                    + DateUtil.eedisplayFloatNDTh(bi.getActivoCuentaxcobrar().doubleValue(), 4) + "</td><td>"
                                                    + getKapTotal(bi) + "</td><td>"
                                                    + DateUtil.eedisplayFloatNDTh(bi.getPasivo().doubleValue(), 4) + "</td><td>"
                                                    + DateUtil.eedisplayFloatNDTh(bi.getEncaje().doubleValue(), 4) + "</td><td>"
                                                    + DateUtil.eedisplayFloatNDTh(bi.getPRespaldo().doubleValue(), 4) + "</td></tr><tr><td>");
                                            sb.append("" + bf.getDateUser() + "</td><td>"
                                                    + getPatTotal(bf) + "</td><td>"
                                                    + DateUtil.eedisplayFloatNDTh(bf.getActivoCajaybanco().doubleValue(), 4) + "</td><td>"
                                                    + DateUtil.eedisplayFloatNDTh(bf.getActivoCuentaxcobrar().doubleValue(), 4) + "</td><td>"
                                                    + getKapTotal(bf) + "</td><td>"
                                                    + DateUtil.eedisplayFloatNDTh(bf.getPasivo().doubleValue(), 4) + "</td><td>"
                                                    + DateUtil.eedisplayFloatNDTh(bf.getEncaje().doubleValue(), 4) + "</td><td>"
                                                    + DateUtil.eedisplayFloatNDTh(bf.getPRespaldo().doubleValue(), 4) + "</td></tr>");
                                        } else {
                                            sb.append("<tr><td>NO se pueden mostrar los montos del balance</td></tr>");
                                        }
                                    }
                                }
                                sb.append("</tbody></table>");
                            }
                        }
                    }
                } else {
                    sb.append("ERROR:</td><td colspan='12'>NO se puede reportar ninguna operación, ya que no existen..." + "</td></tr>");
                }
            }
        }
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("</table");
        try {
            out.println("<style type='text/css' media='print'> .botones{display:none;}</style>");
            out.println("</head><body>");
            out.print(sb.toString());
            out.println("<br><br><table width='50%'><tr class='botones'><td align='center' valign='top' >");
            out.println("<input type='submit' value='IMPRIMIR' onclick=\"document.title=''; if (window.print)window.print();else alert('SU NAVEGADOR NO SOPORTA ESTA FUNCIÓN');\"/>");
            out.println("</td><td align='center' valign='top'><form name='fmc' method='post' action='managercapital.htm'>");
            out.println("<input type='button' value='Cerrar' onclick='window.close();'/></form></td></tr></table></body></html>");
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

    private String taxRecover(TOperacion to, DAOGeneral dao, boolean resetedTransit) {
        TBalancexmoneda bx = null;
        TBalancexmoneda bx1 = null;
        TPatrimonioTransit pa = null;
        bx = (TBalancexmoneda) dao.load(TBalancexmoneda.class, DateUtil.getDate(to.getFd()).replaceAll("/", "") + to.getTPersonaCaja().getTCaja().getCodCaja() + to.getTMoneda().getCodParaNumCuenta() + "00");
        iniPatrimonio.patActual(to.getTPersonaCaja().getTCaja().getCodCaja(), to.getTMoneda().getCodMoneda());
        pa = (TPatrimonioTransit) dao.findAll("from TPatrimonio where TBalancexmoneda='" + bx.getIdbalance()
                + "' and estado='ACTIVO" + to.getTPersonaCaja().getTCaja().getTFilial().getCodFilial() + "'").get(0);
        String idForInitNewestTable = to.getIdOperacion();
        int chain = (int) Integer.parseInt(to.getTTipoOperacion().getCodigoTipoOperacion().substring(4));
        String s = "";
        switch (chain) {
            case 1:
                List compra = dao.findAll("from TRegistroCompraVenta where TOperacion.idOperacion='" + idForInitNewestTable + "'");
                if (compra.size() > 0) {
                    TRegistroCompraVenta c = (TRegistroCompraVenta) compra.iterator().next();
                    s += "S/. " + c.getMontoEntregado() + "</td><td>";
                    s += to.getTMoneda().getSimbolo() + " " + c.getMontoRecibido() + "</td><td>";
                    s += to.getTTipoOperacion().getNombre() + "</td><td>";
                    s += to.getDescripcion() + "</td><td>";
//                    bx = (TBalancexmoneda) dao.load(TBalancexmoneda.class, DateUtil.getDate(to.getFd()).replaceAll("/", "") + "0" + to.getTMoneda().getCodParaNumCuenta() + "0000000000");
                    bx1 = (TBalancexmoneda) dao.load(TBalancexmoneda.class, DateUtil.getDate(to.getFd()).replaceAll("/", "") + to.getTPersonaCaja().getTCaja().getCodCaja() + "100");
//                    pa = (TPatrimonio) dao.findAll("from TPatrimonio where TBalancexmoneda='" + bx.getIdbalance()
//                            + "' and estado='ACTIVO" + to.getTPersonaCaja().getTCaja().getTFilial().getCodFilial() + "'").get(0);
                    TPatrimonioTransit pa1 = (TPatrimonioTransit) dao.findAll("from TPatrimonio where TBalancexmoneda='" + bx1.getIdbalance()
                            + "' and estado='ACTIVO" + to.getTPersonaCaja().getTCaja().getTFilial().getCodFilial() + "'").get(0);
                    if (resetedTransit && bx != null) {
                        bx.setActivoCajaybanco(bx.getActivoCajaybanco().add(c.getMontoRecibido()));
                        bx1.setActivoCajaybanco(bx1.getActivoCajaybanco().subtract(c.getMontoEntregado()));
                        dao.update();
                        pa.setPatrimonioActual(pa.getPatrimonioActual().add(c.getMontoRecibido()));
                        pa1.setPatrimonioActual(pa1.getPatrimonioActual().subtract(c.getMontoEntregado()));
                        dao.update();
                    }
                    s += to.getTMoneda().getCodMoneda() + " " + DateUtil.eedisplayFloatNDTh(bx.getActivoCajaybanco().doubleValue(), 4) + "<br>S/. "
                            + DateUtil.eedisplayFloatNDTh(bx1.getActivoCajaybanco().doubleValue(), 4) + "</td><td>"
                            + "</td><td>";
                    s += to.getTMoneda().getCodMoneda() + " " + DateUtil.eedisplayFloatNDTh(pa.getPatrimonioActual().doubleValue(), 4) + "<br>S/. "
                            + DateUtil.eedisplayFloatNDTh(pa1.getPatrimonioActual().doubleValue(), 4) + "</td><td>"
                            + ".</td><td>.</td><td>.";
                } else {
                    dao.delete(to);
                }
                break;
            case 2:
                List venta = dao.findAll("from TRegistroCompraVenta where TOperacion.idOperacion='" + idForInitNewestTable + "'");
                if (venta.size() > 0) {
                    TRegistroCompraVenta v = (TRegistroCompraVenta) venta.iterator().next();
                    s += to.getTMoneda().getSimbolo() + " " + v.getMontoEntregado() + "</td><td>";
                    s += "S/. " + v.getMontoRecibido() + "</td><td>";
                    s += to.getTTipoOperacion().getNombre() + "</td><td>";
                    s += to.getDescripcion() + "</td><td>";
//                    bx = (TBalancexmoneda) dao.load(TBalancexmoneda.class, DateUtil.getDate(to.getFd()).replaceAll("/", "") + "0" + to.getTMoneda().getCodParaNumCuenta() + "0000000000");
                    bx1 = (TBalancexmoneda) dao.load(TBalancexmoneda.class, DateUtil.getDate(to.getFd()).replaceAll("/", "") + to.getTPersonaCaja().getTCaja().getCodCaja() + "100");
//                    pa = (TPatrimonio) dao.findAll("from TPatrimonio where TBalancexmoneda='" + bx.getIdbalance()
//                            + "' and estado='ACTIVO" + to.getTPersonaCaja().getTCaja().getTFilial().getCodFilial() + "'").get(0);
                    TPatrimonioTransit pa1 = (TPatrimonioTransit) dao.findAll("from TPatrimonio where TBalancexmoneda='" + bx1.getIdbalance()
                            + "' and estado='ACTIVO" + to.getTPersonaCaja().getTCaja().getTFilial().getCodFilial() + "'").get(0);
                    if (resetedTransit && bx != null) {
                        bx.setActivoCajaybanco(bx.getActivoCajaybanco().subtract(v.getMontoEntregado()));
                        bx1.setActivoCajaybanco(bx1.getActivoCajaybanco().add(v.getMontoRecibido()));
                        dao.update();
                        pa.setPatrimonioActual(pa.getPatrimonioActual().subtract(v.getMontoEntregado()));
                        pa1.setPatrimonioActual(pa1.getPatrimonioActual().add(v.getMontoRecibido()));
                        dao.update();
                    }
                    s += to.getTMoneda().getCodMoneda() + " " + DateUtil.eedisplayFloatNDTh(bx.getActivoCajaybanco().doubleValue(), 4) + "<br>S/. "
                            + DateUtil.eedisplayFloatNDTh(bx1.getActivoCajaybanco().doubleValue(), 4) + "</td><td>"
                            + "</td><td>";
                    s += to.getTMoneda().getCodMoneda() + " " + DateUtil.eedisplayFloatNDTh(pa.getPatrimonioActual().doubleValue(), 4) + "<br>S/. "
                            + DateUtil.eedisplayFloatNDTh(pa1.getPatrimonioActual().doubleValue(), 4) + "</td><td>"
                            + ".</td><td>.</td><td>.";
                } else {
                    dao.delete(to);
                }
                break;
            case 3:
                List deposito = dao.findAll("from TRegistroDepositoRetiro where TOperacion.idOperacion='" + idForInitNewestTable + "'");
                if (deposito.size() > 0) {
                    TRegistroDepositoRetiro d = (TRegistroDepositoRetiro) deposito.iterator().next();
                    s += ".</td><td>";
                    s += to.getTMoneda().getSimbolo() + " " + d.getImporte() + "</td><td>";
                    s += to.getTTipoOperacion().getNombre() + "</td><td>";
                    s += to.getDescripcion() + "</td><td>";
//                    bx = (TBalancexmoneda) dao.load(TBalancexmoneda.class, DateUtil.getDate(to.getFd()).replaceAll("/", "") + "0" + to.getTMoneda().getCodParaNumCuenta() + "0000000000");
//                    pa = (TPatrimonio) dao.findAll("from TPatrimonio where TBalancexmoneda='" + bx.getIdbalance()
//                            + "' and estado='ACTIVO" + to.getTPersonaCaja().getTCaja().getTFilial().getCodFilial() + "'").get(0);
                    if (resetedTransit && bx != null) {
                        bx.setActivoCajaybanco(bx.getActivoCajaybanco().add(d.getImporte()));
                        Double x = d.getTCuentaPersona().getSaldoSinInteres().doubleValue() - d.getImporte().doubleValue();
                        if (x < 0.0D) {
                            Double x1 = d.getImporte().doubleValue() - d.getTCuentaPersona().getSaldoSinInteres().doubleValue();
                            if (x1 > d.getImporte().doubleValue()) {
                                //A=0
                                bx.setActivoCuentaxcobrar(bx.getActivoCuentaxcobrar().subtract(d.getImporte()));
                                bx.setEncaje(bx.getEncaje().subtract(d.getImporte()));
                                pa.setPatrimonioActual(pa.getPatrimonioActual().add(d.getImporte()));
                            } else {
                                //Importe=A+B
                                bx.setPasivo(bx.getPasivo().add(d.getTCuentaPersona().getSaldoSinInteres()));
                                bx.setActivoCuentaxcobrar(bx.getActivoCuentaxcobrar().subtract(new BigDecimal(x1)));
                                bx.setEncaje(bx.getEncaje().subtract(new BigDecimal(x1)));
                                pa.setPatrimonioActual(pa.getPatrimonioActual().add(new BigDecimal(x1)));
                            }
                        } else {
                            //B=0
                            bx.setPasivo(bx.getPasivo().add(d.getImporte()));
                        }
                        dao.update();
                    }
                    s += to.getTMoneda().getSimbolo() + " " + DateUtil.eedisplayFloatNDTh(bx.getActivoCajaybanco().doubleValue(), 4) + "</td><td>";
                    s += to.getTMoneda().getSimbolo() + " " + DateUtil.eedisplayFloatNDTh(bx.getActivoCuentaxcobrar().doubleValue(), 4) + "</td><td>";
                    s += to.getTMoneda().getSimbolo() + " " + DateUtil.eedisplayFloatNDTh(pa.getPatrimonioActual().doubleValue(), 4) + "</td><td>";
                    s += to.getTMoneda().getSimbolo() + " " + DateUtil.eedisplayFloatNDTh(bx.getPasivo().doubleValue(), 4) + "</td><td>";
                    s += to.getTMoneda().getSimbolo() + " " + DateUtil.eedisplayFloatNDTh(bx.getEncaje().doubleValue(), 4) + "</td><td>.";
                } else {
                    dao.delete(to);
                }
                break;
            case 4:
                List retiro = dao.findAll("from TRegistroDepositoRetiro where TOperacion.idOperacion='" + idForInitNewestTable + "'");
                if (retiro.size() > 0) {
                    TRegistroDepositoRetiro r = (TRegistroDepositoRetiro) retiro.iterator().next();
                    s += to.getTMoneda().getSimbolo() + " " + r.getImporte() + "</td><td>";
                    s += ".</td><td>";
                    s += to.getTTipoOperacion().getNombre() + "</td><td>";
                    s += to.getDescripcion() + "</td><td>";
                    if (resetedTransit && bx != null) {
                        bx.setActivoCajaybanco(bx.getActivoCajaybanco().subtract(r.getImporte()));
//                        Double x = r.getTCuentaPersona().getSaldoSinInteres().doubleValue() + r.getImporte().doubleValue();
                        if (r.getTCuentaPersona().getSaldoSinInteres().doubleValue() < 0.0D) {
                            Double x1 = r.getImporte().doubleValue() + r.getTCuentaPersona().getSaldoSinInteres().doubleValue();
                            if (x1 < 0.0D) {
                                //A=0
                                bx.setActivoCuentaxcobrar(bx.getActivoCuentaxcobrar().add(r.getImporte()));
                                bx.setEncaje(bx.getEncaje().add(r.getImporte()));
                                pa.setPatrimonioActual(pa.getPatrimonioActual().subtract(r.getImporte()));
                            } else {
                                //Importe=A+B
                                bx.setPasivo(bx.getPasivo().subtract(new BigDecimal(x1)));
                                bx.setActivoCuentaxcobrar(bx.getActivoCuentaxcobrar().add(r.getTCuentaPersona().getSaldoSinInteres()));
                                bx.setEncaje(bx.getEncaje().add(r.getTCuentaPersona().getSaldoSinInteres()));
                                pa.setPatrimonioActual(pa.getPatrimonioActual().subtract(r.getTCuentaPersona().getSaldoSinInteres()));
                            }
                        } else {
                            //B=0
                            bx.setPasivo(bx.getPasivo().subtract(r.getImporte()));
                        }
                        dao.update();
                    }
                    s += to.getTMoneda().getSimbolo() + " " + DateUtil.eedisplayFloatNDTh(bx.getActivoCajaybanco().doubleValue(), 4) + "</td><td>";
                    s += to.getTMoneda().getSimbolo() + " " + DateUtil.eedisplayFloatNDTh(bx.getActivoCuentaxcobrar().doubleValue(), 4) + "</td><td>";
                    s += to.getTMoneda().getSimbolo() + " " + DateUtil.eedisplayFloatNDTh(pa.getPatrimonioActual().doubleValue(), 4) + "</td><td>";
                    s += to.getTMoneda().getSimbolo() + " " + DateUtil.eedisplayFloatNDTh(bx.getPasivo().doubleValue(), 4) + "</td><td>";
                    s += to.getTMoneda().getSimbolo() + " " + DateUtil.eedisplayFloatNDTh(bx.getEncaje().doubleValue(), 4) + "</td><td>.";
                } else {
                    dao.delete(to);
                }
                break;
            case 5:
                List cgiro = dao.findAll("from TRegistroGiro where TOperacion.idOperacion='" + idForInitNewestTable + "'");
                if (cgiro.size() > 0) {
                    TRegistroGiro g = (TRegistroGiro) cgiro.iterator().next();
                    s += ".</td><td>.</td><td>";
                    s += to.getTTipoOperacion().getNombre() + "</td><td>";
                    s += to.getDescripcion() + " " + to.getTMoneda().getSimbolo() + " " + g.getImporte() + "</td><td>.</td><td>.</td><td>.</td><td>.</td><td>.</td><td>.";
                } else {
                    dao.delete(to);
                }
                break;
            case 6:
                List rpr = dao.findAll("from TRegistroPrestamo where TOperacion.idOperacion='" + idForInitNewestTable + "'");
                if (rpr.size() > 0) {
                    TRegistroPrestamo rp = (TRegistroPrestamo) rpr.iterator().next();
                    s += ".</td><td>" + to.getTMoneda().getSimbolo() + " " + rp.getMonto() + "</td><td>";
                    s += to.getTTipoOperacion().getNombre() + "</td><td>";
                    s += to.getDescripcion() + "</td><td>";

                    if (resetedTransit && bx != null) {
                        bx.setActivoCajaybanco(bx.getActivoCajaybanco().subtract(rp.getMonto()));
                        bx.setActivoCuentaxcobrar(bx.getActivoCuentaxcobrar().add(rp.getMonto()));
                        pa.setPatrimonioActual(pa.getPatrimonioActual().subtract(rp.getMonto()));
                        bx.setPRespaldo(bx.getPRespaldo().add(rp.getMonto()));
                        dao.update();
                    }
                    s += to.getTMoneda().getSimbolo() + " " + DateUtil.eedisplayFloatNDTh(bx.getActivoCajaybanco().doubleValue(), 4) + "</td><td>";
                    s += to.getTMoneda().getSimbolo() + " " + DateUtil.eedisplayFloatNDTh(bx.getActivoCuentaxcobrar().doubleValue(), 4) + "</td><td>";
                    s += to.getTMoneda().getSimbolo() + " " + DateUtil.eedisplayFloatNDTh(pa.getPatrimonioActual().doubleValue(), 4) + "</td><td>.</td><td>.</td><td>.";
                    s += to.getTMoneda().getSimbolo() + " " + DateUtil.eedisplayFloatNDTh(bx.getPRespaldo().doubleValue(), 4);
                } else {
                    dao.delete(to);
                }
                break;
            case 7:
                List c = dao.findAll("from TCobranza where TOperacion.idOperacion='" + idForInitNewestTable + "'");
                if (c.size() > 0) {
                    TCobranza xc = (TCobranza) c.iterator().next();
                    s += to.getTMoneda().getSimbolo() + " " + xc.getTDetallePrestamo().getMontoTotal() + "</td><td>.</td><td>";
                    s += to.getTTipoOperacion().getNombre() + "</td><td>";
                    s += to.getDescripcion() + "</td><td>";
                    if (resetedTransit && bx != null) {
                        bx.setActivoCajaybanco(bx.getActivoCajaybanco().add(xc.getTDetallePrestamo().getMontoTotal()));
                        bx.setActivoCuentaxcobrar(bx.getActivoCuentaxcobrar().subtract(xc.getTDetallePrestamo().getMontoCapital()));
                        pa.setPatrimonioActual(pa.getPatrimonioActual().subtract(xc.getTDetallePrestamo().getMontoTotal()));
                        bx.setPRespaldo(bx.getPRespaldo().add(xc.getTDetallePrestamo().getMontoCapital()));
                        dao.update();
                    }
                    s += to.getTMoneda().getSimbolo() + " " + DateUtil.eedisplayFloatNDTh(bx.getActivoCajaybanco().doubleValue(), 4) + "</td><td>";
                    s += to.getTMoneda().getSimbolo() + " " + DateUtil.eedisplayFloatNDTh(bx.getActivoCuentaxcobrar().doubleValue(), 4) + "</td><td>";
                    s += to.getTMoneda().getSimbolo() + " " + DateUtil.eedisplayFloatNDTh(pa.getPatrimonioActual().doubleValue(), 4) + "</td><td>.</td><td>.</td><td>.";
                    s += to.getTMoneda().getSimbolo() + " " + DateUtil.eedisplayFloatNDTh(bx.getPRespaldo().doubleValue(), 4);
                } else {
                    dao.delete(to);
                }
                break;
            case 8:
                List giro_cobro = dao.findAll("from TRegistroGiro where idoperacioncobro='" + idForInitNewestTable + "'");
                if (giro_cobro.size() > 0) {
                    TRegistroGiro xrg = (TRegistroGiro) giro_cobro.iterator().next();
                    s += ".</td><td>.</td><td>";
                    s += to.getTTipoOperacion().getNombre() + "</td><td>";
                    s += to.getDescripcion() + " " + to.getTMoneda().getSimbolo() + " " + xrg.getImporte() + "</td><td>.</td><td>.</td><td>.</td><td>.</td><td>.</td><td>.";
                } else {
                    dao.delete(to);
                }
                break;
            case 9:
                List ttc = dao.findAll("from TTransferenciaCaja where TOperacion.idOperacion='" + idForInitNewestTable + "'");
                if (ttc.size() > 0) {
                    TTransferenciaCaja xt = (TTransferenciaCaja) ttc.iterator().next();
                    s += to.getTMoneda().getSimbolo() + " " + xt.getMonto() + "</td><td>.</td><td>";
                    s += to.getTTipoOperacion().getNombre() + "</td><td>";
                    s += to.getDescripcion() + " " + "</td><td>.</td><td>.</td><td>.</td><td>.</td><td>.</td><td>.";
                } else {
                    dao.delete(to);
                }
                break;
            case 10:
                List ro = dao.findAll("from TRegistroOtros where TOperacion.idOperacion='" + idForInitNewestTable + "'");
                if (ro.size() > 0) {
                    TRegistroOtros xro = (TRegistroOtros) ro.iterator().next();
                    s += ".</td><td>.</td><td>";
                    s += to.getTTipoOperacion().getNombre() + "</td><td>";
                    s += to.getDescripcion() + "</td><td>";
                    if (resetedTransit && bx != null) {
                        bx.setActivoCajaybanco(bx.getActivoCajaybanco().subtract(xro.getMonto()));
                        pa.setPatrimonioActual(pa.getPatrimonioActual().subtract(xro.getMonto()));
                        dao.update();
                    }
                    s += to.getTMoneda().getSimbolo() + " " + DateUtil.eedisplayFloatNDTh(bx.getActivoCajaybanco().doubleValue(), 4) + "</td><td>.</td><td>";
                    s += to.getTMoneda().getSimbolo() + " " + DateUtil.eedisplayFloatNDTh(pa.getPatrimonioActual().doubleValue(), 4) + "</td><td>.</td><td>.</td><td>.";
                } else {
                    dao.delete(to);
                }
                break;
            case 11:
                List tro = dao.findAll("from TRegistroDepositoRetiro where TOperacion.idOperacion='" + idForInitNewestTable + "'");
                if (tro.size() > 0) {
                    TRegistroDepositoRetiro xr = (TRegistroDepositoRetiro) tro.iterator().next();
                    s += ".</td><td>.</td><td>";
                    s += to.getTTipoOperacion().getNombre() + "</td><td>";
                    s += to.getDescripcion() + "</td><td>";
                    if (resetedTransit && bx != null) {
                        bx.setPasivo(bx.getPasivo().add(xr.getImporte()));
                        pa.setPatrimonioActual(pa.getPatrimonioActual().subtract(xr.getImporte()));
                        dao.update();
                    }
                    s += "</td><td>.</td><td>";
                    s += to.getTMoneda().getSimbolo() + " " + DateUtil.eedisplayFloatNDTh(pa.getPatrimonioActual().doubleValue(), 4) + "</td><td>" + to.getTMoneda().getSimbolo() + " " + DateUtil.eedisplayFloatNDTh(bx.getPasivo().doubleValue(), 4)
                            + "</td><td>.</td><td>.";
                } else {
                    dao.delete(to);
                }
                break;
            case 12:
                List tr = dao.findAll("from TTransferenciaCaja where TOperacion.idOperacion='" + idForInitNewestTable + "'");
                if (tr.size() > 0) {
                    TTransferenciaCaja xre = (TTransferenciaCaja) tr.iterator().next();
                    s += ".</td><td>.</td><td>";
                    s += to.getTTipoOperacion().getNombre() + "</td><td>";
                    s += to.getDescripcion() + " " + to.getTMoneda().getSimbolo() + " " + xre.getMonto() + "</td><td>.</td><td>.</td><td>.</td><td>.</td><td>.</td><td>.";
                } else {
                    dao.delete(to);
                }
                break;
            case 13:
                List rac = dao.findAll("from TTransferenciaCaja where TOperacion.idOperacion='" + idForInitNewestTable + "'");
                if (rac.size() > 0) {
                    TTransferenciaCaja ac = (TTransferenciaCaja) rac.iterator().next();
                    s += ".</td><td>" + to.getTMoneda().getSimbolo() + " " + ac.getMonto() + "</td><td>";
                    s += to.getTTipoOperacion().getNombre() + "</td><td>";
                    s += to.getDescripcion() + "</td><td>";
                    if (resetedTransit && bx != null) {
                        bx.setActivoCajaybanco(bx.getActivoCajaybanco().add(ac.getMonto()));
                        pa.setPatrimonioActual(pa.getPatrimonioActual().add(ac.getMonto()));
                        dao.update();
                    }
                    s += to.getTMoneda().getSimbolo() + " " + DateUtil.eedisplayFloatNDTh(bx.getActivoCajaybanco().doubleValue(), 4) + "</td><td>.</td><td>";
                    s += to.getTMoneda().getSimbolo() + " " + DateUtil.eedisplayFloatNDTh(pa.getPatrimonioActual().doubleValue(), 4) + "</td><td>.</td><td>.</td><td>.";
                } else {
                    dao.delete(to);
                }
                break;
            case 14:
                List rtf = dao.findAll("from TTransferenciaCaja where TOperacion.idOperacion='" + idForInitNewestTable + "'");
                if (rtf.size() > 0) {
                    TTransferenciaCaja tf = (TTransferenciaCaja) rtf.iterator().next();
                    s += to.getTMoneda().getSimbolo() + " " + tf.getMonto() + "</td><td>.</td><td>";
                    s += to.getTTipoOperacion().getNombre() + "</td><td>";
                    s += to.getDescripcion() + "</td><td>";
                    TPatrimonioTransit pa1 = (TPatrimonioTransit) dao.findAll("from TPatrimonio where TBalancexmoneda='" + bx1.getIdbalance()
                            + "' and estado='ACTIVO" + tf.getCodCajaDestino().substring(0, 4) + "'").get(0);
                    if (resetedTransit && bx != null) {
                        pa.setPatrimonioActual(pa.getPatrimonioActual().subtract(tf.getMonto()));
                        pa1.setPatrimonioActual(pa1.getPatrimonioActual().add(tf.getMonto()));
                        dao.update();
                    }
                    s += "</td><td>.</td><td>" + to.getTMoneda().getSimbolo() + " " + DateUtil.eedisplayFloatNDTh(pa.getPatrimonioActual().doubleValue(), 4) + "<br>";
                    s += to.getTMoneda().getSimbolo() + " " + DateUtil.eedisplayFloatNDTh(pa1.getPatrimonioActual().doubleValue(), 4) + "</td><td>.</td><td>.</td><td>.";
                } else {
                    dao.delete(to);
                }
                break;
            default:
                s = ("ERROR: NO hay NADA que hacer!!!");
        }
        return s;
    }

    private String getPatTotal(TBalancexmoneda bi) {
        Set pat = bi.getTPatrimonioTransits();
        BigDecimal b = BigDecimal.ZERO;
        if (!pat.isEmpty()) {
            Iterator i = pat.iterator();
            while (i.hasNext()) {
                TPatrimonioTransit p = (TPatrimonioTransit) i.next();
                b = b.add(p.getPatrimonio());
            }
        }
        return DateUtil.eedisplayFloatNDTh(b.doubleValue(), 4);
    }

    private String getKapTotal(TBalancexmoneda bi) {
        Set pat = bi.getTPatrimonioTransits();
        BigDecimal b = BigDecimal.ZERO;
        if (!pat.isEmpty()) {
            Iterator i = pat.iterator();
            while (i.hasNext()) {
                TPatrimonioTransit p = (TPatrimonioTransit) i.next();
                b = b.add(p.getPatrimonioActual());
            }
        }
        return DateUtil.eedisplayFloatNDTh(b.doubleValue(), 4);
    }

    //falta Kapital
    private boolean resetNowChanges(String ff2, DAOGeneral dao) {
        List mons = dao.findAll("from TMoneda where estado='ACTIVO'");
        Date f1 = DateUtil.convertStringToDate("dd/MM/yyyy", ff2);
        if (!mons.isEmpty()) {
            Iterator iMons = mons.iterator();
            while (iMons.hasNext()) {
                TMoneda m = (TMoneda) iMons.next();
                List l = dao.findAll("from TCaja where estado = 'ACTIVO'");
                Iterator f = l.iterator();
                while (f.hasNext()) {
                    TCaja cx = (TCaja) f.next();
                    TBalancexmoneda bi = (TBalancexmoneda) dao.load(TBalancexmoneda.class, DateUtil.getDateAnt(f1).replaceAll("/", "") + cx.getCodCaja() + m.getCodParaNumCuenta() + "00");
                    TBalancexmoneda bf = (TBalancexmoneda) dao.load(TBalancexmoneda.class, DateUtil.getDate(f1).replaceAll("/", "") + cx.getCodCaja() + m.getCodParaNumCuenta() + "00");
                    if (bi != null && bf != null) {
                        if (bf.getEstado().equals("TRANSIT")) {
                            bf.setActivoCajaybanco(bi.getActivoCajaybanco());
                            bf.setActivoCuentaxcobrar(bi.getActivoCuentaxcobrar());
                            bf.setPasivo(bi.getPasivo());
                            bf.setEncaje(bi.getEncaje());
                            bf.setPRespaldo(bi.getPRespaldo());
                            dao.update();
                            return true;
                        }
                    } else {
                        Logger.getLogger(SReportAdmin.class.getName()).log(Level.INFO, "nulos");
                    }
                }
            }
        }
        return false;
    }
}
