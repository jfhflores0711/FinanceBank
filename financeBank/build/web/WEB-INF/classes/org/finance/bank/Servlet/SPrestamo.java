package org.finance.bank.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.finance.bank.bean.*;
import org.finance.bank.model.dao.DAOGeneral;
import org.finance.bank.util.DateUtil;
import org.finance.bank.util.numeroOperacion;

/**
 *
 * @author admin
 */
public class SPrestamo extends HttpServlet {

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
        String o = (String) request.getParameter("o");
        HttpSession session = request.getSession(true);
        DAOGeneral dao = new DAOGeneral();
        String fi = (String) session.getAttribute("USER_CODFILIAL");
        String myCaja = session.getAttribute("USER_CODCAJA").toString();
        String myId = session.getAttribute("USER_ID").toString();
        String myIp = session.getAttribute("USER_IP").toString();
        String myPC = session.getAttribute("USER_ID_PERSONA_CAJA").toString();
        String idForInitNewestTable = DateUtil.convertDateId(myPC, SPrestamo.class.getSimpleName());
        try {
            if ("b".equals(o)) {
                //buscar
                String contenido = request.getParameter("a");
                if (contenido != null) {
                    contenido = contenido.toUpperCase();
                } else {
                    contenido = "ZEROBUSQUEDA";
                }
                String tipoBusqueda = request.getParameter("b");
                if (tipoBusqueda == null) {
                    out.println("<center><fieldset style='border-width:3px'>");
                    out.println("<legend><b>RESULTADO DE BUSQUEDA:</b></legend>");
                    out.println("<table id='tablapersonas' class='tabla'>");
                    out.println("<tr class='modo1'>");
                    out.println("<td style='color:red;font-size: 15px'>NO SE ENCONTRO NINGUNA PERSONA</td>");
                    out.println("</tr></table></fieldset></center>");
                    dao.cerrarSession();
                    out.close();
                    return;
                }
                int i = 0;
                String par, sql = "";
                if ("nom".equals(tipoBusqueda)) {
                    sql = "from TPersona where nombre like '%" + contenido + "%' AND (estado='ACTIVO') and NOT ruc='DISABLED'";
                } else if ("ape".equals(tipoBusqueda)) {
                    sql = "from TPersona where apellidos like '%" + contenido + "%' AND (estado='ACTIVO') and NOT ruc='DISABLED'";
                } else if (tipoBusqueda.equals("dni")) {
                    sql = "from TPersona where docIdentidad like '" + contenido + "%' AND (estado='ACTIVO') and NOT ruc='DISABLED'";
                } else {
                    sql = "from TPersona where ruc like '" + contenido + "%' AND (estado='ACTIVO') AND NOT docIdentidad='DISABLED'";
                }
                List lisnombre = dao.findAll(sql);
                if (lisnombre.size() >= 1) {
                    Iterator it = lisnombre.iterator();
                    out.println("<fieldset style='border-width:3px'>");
                    out.println("<legend><b>RESULTADO DE BUSQUEDA:</b></legend>");
                    out.println("<table border='0' id='tablapersonas' class='tabla' width='100%'>");
                    out.println("<tr>");
                    out.println("<th>-.-</th>");
                    out.println("<th style='display:none'>idPersona</th>");
                    out.println("<th>Nombre</th>");
                    out.println("<th>Apellidos</th>");
                    out.println("<th>DNI</th>");
                    out.println("<th>RUC</th>");
                    out.println("<th>Ubigeo</th>");
                    out.println("<th>Celular</th>");
                    out.println("<th>Email</th>");
                    out.println("</tr>");
                    while (it.hasNext()) {
                        TPersona persona = (TPersona) it.next();
                        par = "";
                        i = i + 1;
                        if (i % 2 == 0) {
                            par = "modo2";
                        } else {
                            par = "modo1";
                        }
                        out.println("<tr class='" + par + "'>");
                        out.println("<td ><input type='radio' id='rdPersona" + i + "' name='rdPersona' value='" + i + "' onclick='mostrarcuentadetallep(\"" + i + "\")'/></td>");
                        out.println("<td id='tdIdPersona" + i + "' style='display:none' >" + persona.getIdUserPk() + "</td>");
                        out.println("<td id='ttdN" + i + "'>" + persona.getNombre() + "</td>");
                        out.println("<td id='ttdA" + i + "'>" + persona.getApellidos() + " </td>");
                        out.println("<td id='ttdDNI" + i + "' >" + persona.getDocIdentidad() + "</td>");
                        out.println("<td id='ttdRUC" + i + "'>" + persona.getRuc() + "</td>");
                        out.println("<td>" + persona.getUbigeo() + "</td>");
                        out.println("<td>" + persona.getCelular() + "</td>");
                        out.println("<td>" + persona.getEmail() + "</td>");
                        out.println("</tr>");
                    }
                    out.println("</table>");
                    out.println("</fieldset>");
                } else {
                    out.println("<center>");
                    out.println("<fieldset style='border-width:3px'>");
                    out.println("<legend><b>RESULTADO DE BUSQUEDA:</b></legend>");
                    out.println("<table id='tablapersonas' class='tabla'>");
                    out.println("<tr class='modo1'>");
                    out.println("<td style='color:red;font-size: 15px'>NO SE ENCONTRO NINGUNA PERSONA, PRUEBE CON OTROS PARAMETROS</td>");
                    out.println("</tr>");
                    out.println("</table>");
                    out.println("</fieldset>");
                    out.println("</center>");
                }
            } else if ("s".equals(o)) {
                String contenido = request.getParameter("a");
                if (contenido != null) {
                    contenido = contenido.toUpperCase();
                } else {
                    out.print("ERROR");
                    out.close();
                    return;
                }
                TTipoCredito c = (TTipoCredito) dao.load(TTipoCredito.class, contenido);
                if (c != null) {
                    out.print("" + c.getDescripcion());
                    out.println("<input type='hidden' id='ix' value='" + c.getIdtipocredito() + "'>");
                    out.println("<input type='hidden' id='ncx' value='" + c.getNumeroCuotasMax() + "'>");
                    out.println("<input type='hidden' id='tix' value='" + c.getTasaInteresMax() + "'>");
                    out.println("<input type='hidden' id='tic' value='" + c.getTasaInteresCompensatorio() + "'>");
                    out.println("<input type='hidden' id='tim' value='" + c.getTasaInteresMoratoria() + "'>");
                    out.println("<input type='hidden' id='fci' value='" + c.getFormaCalculoIntereses() + "'>");
                    out.println("<input type='hidden' id='nci' value='" + c.getNumeroCuotasMin() + "'>");
                    out.println("<input type='hidden' id='mx' value='" + c.getMontoMaximo() + "'>");
                    out.println("<input type='hidden' id='mi' value='" + c.getMontoMinimo() + "'>");
                    out.println("<input type='hidden' id='tii' value='" + c.getTasaInteresMin() + "'>");
                    out.println("<input type='hidden' id='dec' value='" + c.getDiasEntreCuotas() + "'>");
                    out.println("<input type='hidden' id='rg' value='" + c.getRequiereGarantia() + "'>");
                } else {
                    out.println("<center><input type='hidden' id='ix' value='ERROR'>");
                    out.println("<p style='color:red;font-size: 15px'>ERROR, EL TIPO DE CREDITO NO EXISTE.</p>");
                    out.println("</center>");
                }
            } else if ("g".equals(o)) {
                boolean r = true;
                String a = "", b, c, d, e = "", f, g = "", h = "", i = "", j = "", z = "";
                int dec = 30, nc = 1;
                double imp = 0D, intas = 0D;
                Date fs, fa = null;
                try {
                    a = (String) request.getParameter("a");
                    b = (String) request.getParameter("b");
                    fs = DateUtil.convertStringToDate("dd/MM/yyyy", b);
                    c = (String) request.getParameter("c");
                    fa = DateUtil.convertStringToDate("dd/MM/yyyy", c);
                    d = (String) request.getParameter("d");
                    e = (String) request.getParameter("e");
                    f = (String) request.getParameter("f");
                    dec = Integer.parseInt(f.replace(" ", ""));
                    System.out.println("dec = " + dec);
                    g = (String) request.getParameter("g");
                    h = (String) request.getParameter("h");
                    imp = Double.parseDouble(h);
                    i = (String) request.getParameter("i");
                    intas = Double.parseDouble(i);
                    j = (String) request.getParameter("j");
                    nc = Integer.parseInt(j.replace(" ", ""));
                    z = (String) request.getParameter("z");
                } catch (NumberFormatException ex) {
                    r = false;
                } catch (Exception x) {
                    r = false;
                }
                if (r) {
                    TPersona ap = (TPersona) dao.load(TPersona.class, z);
                    if (ap == null) {
                        out.print("NO SE HA SELECCIONADO UNA PERSONA");
                    }
                    TTipoCuenta ac = (TTipoCuenta) dao.load(TTipoCuenta.class, "CCO");
                    TMoneda am = (TMoneda) dao.load(TMoneda.class, g);
                    if (am == null) {
                        out.print("Error al seleccionar el tipo de moneda");
                    }
                    String numCuenta = "51";
                    String monedas = (String) dao.findAll("select money.codParaNumCuenta from TMoneda money where money.codMoneda ='" + g + "'").get(0);
                    boolean ok = false;
                    while (!ok) {
                        List al = dao.findAll("select t.numCta from TCuentaPersona t where t.estado='PRESTAMO' and t.numCta like '%-" + idForInitNewestTable.substring(0, 4) + "' order by t.numCta desc");
                        String codigox = "00001-" + idForInitNewestTable.substring(0, 4);
                        if (!al.isEmpty()) {
                            String ag = (String) al.get(0);
                            int ai = Integer.parseInt(ag.substring(7, 12));
                            ai++;
                            codigox = "00000".substring(String.valueOf(ai).length()) + String.valueOf(ai) + "-" + idForInitNewestTable.substring(0, 4);
                        }
                        if (fi.length() > 3) {
                            fi = fi.substring(fi.length() - 3);
                        }
                        numCuenta += fi;
                        numCuenta += monedas;
                        numCuenta += "3";
                        numCuenta += codigox;
                        List l = dao.findAll("select t.numCta from TCuentaPersona t where t.numCta ='" + numCuenta + "'");
                        if (l.isEmpty() || l.size() < 1) {
                            ok = true;
                        } else {
                            ok = false;
                        }
                    }
//public TCuentaPersona(String idcuentapersona, TTipoCuenta TTipoCuenta, TPersona TPersona, TMoneda TMoneda, String numCta, String fecha, String estado, String ipUser, String idUser, String dateUser)
                    TCuentaPersona ab = new TCuentaPersona(idForInitNewestTable, ac, ap, am, numCuenta, DateUtil.getNOW_S(), "PRESTAMO", myId, myIp, DateUtil.getNOW_S());
                    ab.setObservaciones("");
                    ab.setInteres(BigDecimal.ZERO);
                    ab.setInteres(BigDecimal.ZERO);
                    ab.setSaldo(BigDecimal.ZERO);
                    ab.setSaldoSinInteres(BigDecimal.ZERO);
                    ab.setFechaActualizacion(DateUtil.getNOW_S());
                    ab.setFechaCap(DateUtil.getDate(new Date()));
                    dao.persist(ab);
                    TTipoCredito at = (TTipoCredito) dao.load(TTipoCredito.class, a);
                    TTipoOperacion ar = (TTipoOperacion) dao.load(TTipoOperacion.class, "TIPC6");
                    TPersonaCaja ad = (TPersonaCaja) dao.load(TPersonaCaja.class, myPC);
                    //public TOperacion(String idOperacion, TTipoOperacion TTipoOperacion, TPersonaCaja TPersonaCaja, TMoneda TMoneda, String numeroOperacion, String fecha, String estado, String idUser, String ipUser, String dateUser)
                    TOperacion ao = new TOperacion(idForInitNewestTable, ar, ad, am, numeroOperacion.getNumber(fi, myCaja), DateUtil.getNOW_S(), "ACTIVO", myId, myIp, DateUtil.getNOW_S());
                    ao.setDescripcion("REGISTRO PRESTAMO");
                    ao.setFd(new Date());
                    ao.setSaldoFinalSinInteres(BigDecimal.ZERO);
                    ao.setSaldofinal(BigDecimal.ZERO);
                    dao.persist(ao);
//TRegistroPrestamo(String idprestamo, TCuentaPersona TCuentaPersona, TTipoCredito TTipoCredito, TOperacion TOperacion, BigDecimal monto, String fecha, int numeroCuotas, String idUser, String ipUser, String dateUser, String estado, BigDecimal tasaInteresPrestamo, int numeroReprogramacion)
                    TRegistroPrestamo ax = new TRegistroPrestamo(idForInitNewestTable, ab, at, ao, new BigDecimal(imp), DateUtil.getNOW_S(), nc, myId, myIp, DateUtil.getNOW_S(), "ACTIVO", new BigDecimal(intas), 0);
                    ax.setTea(i);
                    ax.setSegDesg("0.00");
                    ax.setPeriodo(""+dec);
                    ax.setTasaInteresMoratorio(at.getTasaInteresMoratoria());
                    ax.setItSuspenso(BigDecimal.ZERO);
                    ax.setFechaAprobacion(DateUtil.getDate(fa));
                    ax.setNumeroRefinanciacion(0);
                    ax.setTipoCuota(0);
                    ax.setDiaInicialPp(dec);
                    ax.setDiasEntreCuotas(dec);
                    ax.setAplicaSeguroDesgravamen(0);
                    ax.setEstadoSolicitud(0);
                    ax.setFechaDesembolso(DateUtil.getDate(fa));
                    ax.setModoCalculos(e.equals("ff") ? 2 : 1);
                    ax.setCargos(BigDecimal.ZERO);
                    ax.setMontoInteresComp(BigDecimal.ZERO);
                    ax.setFechaJudicial("");
                    ax.setSituacionCredito(1);
                    ax.setFechaSituacion(DateUtil.getNOW_S());
                    ax.setFechaDembolsoAutorizacion("");
                    dao.persist(ax);
                }
                List xa = dao.findAll("from TRegistroPrestamo where estadoSolicitud=0");
                if (!xa.isEmpty()) {
                    out.print("<fieldset style='border-width:3px'>"
                            + "<legend><b>Préstamos Guardados</b></legend>"
                            + "<table>"
                            + "<tr><td colspan='6' align='right'><input type='submit' value='Refrescar'></td></tr>"
                            + "<tr><td>"
                            + "N°"
                            + "</td><td>"
                            + "Nombre"
                            + "</td><td>"
                            + "Moneda y Monto"
                            + "</td><td>"
                            + "Fecha Solicitud"
                            + "</td><td>"
                            + "</td><td>"
                            + "</td>"
                            + "</tr>");
                    for (Iterator it = xa.iterator(); it.hasNext();) {
                        TRegistroPrestamo at = (TRegistroPrestamo) it.next();
                        out.print("<tr><td>"
                                + at.getTCuentaPersona().getNumCta().substring(7, 12)
                                + "</td><td>"
                                + at.getTCuentaPersona().getTPersona().getNombre() + " " + at.getTCuentaPersona().getTPersona().getApellidos()
                                + "</td><td>"
                                + at.getTCuentaPersona().getTMoneda().getSimbolo() + " " + at.getMonto()
                                + "</td><td>"
                                + at.getFecha()
                                + "</td><td><input type='hidden' name='ax" + at.getIdprestamo() + "' value='" + at.getIdprestamo() + "' />"
                                + "<input type='button' name='des" + at.getIdprestamo() + "' onclick='despacho(\"" + at.getIdprestamo() + "\");' value='Desembolsar' />"
                                + "</td><td>"
                                + "<input type='button' name='eli" + at.getIdprestamo() + "' onclick='borrar(\"" + at.getIdprestamo() + "\")' value='Eliminar' />"
                                + "</td></tr>");
                    }
                    out.print(""
                            + "</table></fieldset>");
                }
            } else if ("e".equals(o)) {
                String a = (String) request.getParameter("a");
                TRegistroPrestamo b = (TRegistroPrestamo) dao.load(TRegistroPrestamo.class, a);
                b.setEstado("ELIMINADO");
                b.setEstadoSolicitud(99);
                b.setSituacionCredito(0);
                dao.update();
                List xa = dao.findAll("from TRegistroPrestamo where estadoSolicitud=0");
                if (!xa.isEmpty()) {
                    out.print("<fieldset style='border-width:3px'>"
                            + "<legend><b>Préstamos Guardados</b></legend>"
                            + "<table>"
                            + "<tr><td colspan='6' align='right'><input type='submit' value='Refrescar'></td></tr>"
                            + "<tr><td>"
                            + "N°"
                            + "</td><td>"
                            + "Nombre"
                            + "</td><td>"
                            + "Moneda y Monto"
                            + "</td><td>"
                            + "Fecha Solicitud"
                            + "</td><td>"
                            + "</td><td>"
                            + "</td>"
                            + "</tr>");
                    for (Iterator it = xa.iterator(); it.hasNext();) {
                        TRegistroPrestamo at = (TRegistroPrestamo) it.next();
                        out.print("<tr><td>"
                                + at.getTCuentaPersona().getNumCta().substring(7, 12)
                                + "</td><td>"
                                + at.getTCuentaPersona().getTPersona().getNombre() + " " + at.getTCuentaPersona().getTPersona().getApellidos()
                                + "</td><td>"
                                + at.getTCuentaPersona().getTMoneda().getSimbolo() + " " + at.getMonto()
                                + "</td><td>"
                                + at.getFecha()
                                + "</td><td><input type='hidden' name='ax" + at.getIdprestamo() + "' value='" + at.getIdprestamo() + "' />"
                                + "<input type='button' name='des" + at.getIdprestamo() + "' onclick='despacho(\"" + at.getIdprestamo() + "\");' value='Desembolsar' />"
                                + "</td><td>"
                                + "<input type='button' name='eli" + at.getIdprestamo() + "' onclick='borrar(\"" + at.getIdprestamo() + "\")' value='Eliminar' />"
                                + "</td></tr>");
                    }
                    out.print(""
                            + "</table></fieldset>");
                }
            } else if ("d".equals(o)) {
                out.print("OK");
//                }
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
