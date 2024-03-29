package org.finance.bank.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
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
import org.finance.bank.bean.TCaja;
import org.finance.bank.bean.TControlTipo;
import org.finance.bank.bean.TCuentaAcceso;
import org.finance.bank.bean.TCuentaPersona;
import org.finance.bank.bean.TDetalleCaja;
import org.finance.bank.bean.TFilial;
import org.finance.bank.bean.TPersona;
import org.finance.bank.bean.TPersonaCaja;
import org.finance.bank.bean.TRegistroGiro;
import org.finance.bank.bean.TTransferenciaCaja;
import org.finance.bank.model.dao.DAOGeneral;
import org.finance.bank.util.DateUtil;

/**
 *
 * @author roger
 */
public class SEliminarCatalogo extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        DAOGeneral dao = new DAOGeneral();
        try {
            String catalogo = request.getParameter("txtcatalogo");
            String codigo = request.getParameter("txtCodigo");
            if (codigo == null) {
                codigo = request.getParameter("txtId");
            }
            if (catalogo == null || codigo == null) {
                out.println("<input id='txtRptaInsertar' name='txtRptaInsertar' type='hidden' value='NO'><div id='r'>Error en la transaccion.</div>");
                out.close();
                return;
            }
            String reString = "";
            if (catalogo.equals("FILIAL")) {
                TFilial fil = (TFilial) dao.load(TFilial.class, codigo);
                if (fil != null) {
                    boolean dale = true;
                    Set cajitas = fil.getTCajas();
                    Iterator iCajitas = cajitas.iterator();
                    while (iCajitas.hasNext()) {
                        TCaja c = (TCaja) iCajitas.next();
                        if ("ACTIVO".equals(c.getEstado())) {
                            dale = false;
                            reString += "\n-" + c.getCodCaja() + " " + c.getNombreCaja();
                        }
                    }
                    if (dale) {
                        Iterator iCajita = cajitas.iterator();
                        while (iCajita.hasNext()) {
                            TCaja c = (TCaja) iCajita.next();
                            String idcaj = c.getCodCaja();
                            Set pCajitas = c.getTPersonaCajas();
                            Set dCajitas = c.getTDetalleCajas();
                            Set tCajitas = c.getTTransferenciaCajas();
                            Iterator ipCajitas = pCajitas.iterator();
                            Iterator idCajitas = dCajitas.iterator();
                            Iterator itCajitas = tCajitas.iterator();
                            while (itCajitas.hasNext()) {
                                TTransferenciaCaja ttca = (TTransferenciaCaja) idCajitas.next();
                                String idtca = ttca.getIdtransferenciacaja();
                                ttca = (TTransferenciaCaja) dao.load(TTransferenciaCaja.class, idtca);
                                if (ttca != null) {
                                    dao.delete(ttca);
                                }
                            }
                            while (ipCajitas.hasNext()) {
                                TPersonaCaja pCaja = (TPersonaCaja) ipCajitas.next();
                                Set operaciones = pCaja.getTOperacions();
                                if (!operaciones.isEmpty()) {
                                    out.println("<input id='txtRptaInsertar' name='txtRptaInsertar' type='hidden' value='NO'>");
                                    out.print("<div id='r'>La caja " + pCaja.getTCaja().getCodCaja() + " ya está en uso</div>");
                                    out.close();
                                    dao.cerrarSession();
                                    return;
                                }
                            }
                            while (idCajitas.hasNext()) {
                                TDetalleCaja tdca = (TDetalleCaja) idCajitas.next();
                                String iddca = tdca.getIddetallecaja();
                                tdca = (TDetalleCaja) dao.load(TDetalleCaja.class, iddca);
                                if (tdca != null) {
                                    dao.delete(tdca);
                                }
                            }
                            c = (TCaja) dao.load(TCaja.class, idcaj);
                            dao.delete(c);
                        }
                        fil = (TFilial) dao.load(TFilial.class, codigo);
                        dao.delete(fil);
                        out.println("<input id='txtRptaInsertar' name='txtRptaInsertar' type='hidden' value='SI'>");
                        out.close();
                        return;
                    } else {
                        out.println("<input id='txtRptaInsertar' name='txtRptaInsertar' type='hidden' value='NO'>");
                        out.print("<div id='r'>Borrar o desactivar las siguientes cajas" + reString + "</div>");
                        out.close();
                        return;
                    }
                }
            } else if (catalogo.equals("CAJA")) {
                TCaja caja = (TCaja) dao.load(TCaja.class, codigo);
                String idcaj = caja.getCodCaja();
                Set pCajitas = caja.getTPersonaCajas();
                Set dCajitas = caja.getTDetalleCajas();
                Set tCajitas = caja.getTTransferenciaCajas();
                Iterator ipCajitas = pCajitas.iterator();
                Iterator idCajitas = dCajitas.iterator();
                Iterator itCajitas = tCajitas.iterator();                
                while (ipCajitas.hasNext()) {
                    TPersonaCaja pCaja = (TPersonaCaja) ipCajitas.next();
                    if ("ACTIVO".equals(pCaja.getEstado())) {
                        out.print("Desactivar o eliminar el perfil del cajero actual:<br>" + pCaja.getTPersona().getNombre() + " " + pCaja.getTPersona().getApellidos());
                        out.println("<input id='txtRptaInsertar' name='txtRptaInsertar' type='hidden' value='NO'>");
                        out.print("<div id='r'>La Persona está usando aún la cuenta</div>");
                        out.close();
                        return;
                    }
                    Set operaciones = pCaja.getTOperacions();
                    if (operaciones.isEmpty()) {
                        dao.delete(pCaja);
                    } else {
                        out.println("<input id='txtRptaInsertar' name='txtRptaInsertar' type='hidden' value='NO'>");
                        out.print("<div id='r'>La Caja ya está en uso</div>");
                        out.close();
                        dao.cerrarSession();
                        return;
                    }
                }
                while (itCajitas.hasNext()) {
                    TTransferenciaCaja ttca = (TTransferenciaCaja) itCajitas.next();
                    String idtca = ttca.getIdtransferenciacaja();
                    ttca = (TTransferenciaCaja) dao.load(TTransferenciaCaja.class, idtca);
                    if (ttca != null) {
                        dao.delete(ttca);
                    }
                }
                while (idCajitas.hasNext()) {
                    TDetalleCaja tdca = (TDetalleCaja) idCajitas.next();
                    String iddca = tdca.getIddetallecaja();
                    tdca = (TDetalleCaja) dao.load(TDetalleCaja.class, iddca);
                    if (tdca != null) {
                        dao.delete(tdca);
                    }
                }
                caja = (TCaja) dao.load(TCaja.class, idcaj);
                dao.delete(caja);
            } else if (catalogo.equals("A")) {
                
                return;
            } else if (catalogo.equals("PERSONA")) {
                String idpersona = request.getParameter("txtId");
                if (idpersona == null) {
                    out.println("<input id='txtRptaInsertar' name='txtRptaInsertar' type='hidden' value='NO'> Error ...");
                    out.close();
                    dao.cerrarSession();
                    return;
                }
                if (idpersona.equals("20110228202911739108") || idpersona.equals("20110404094329213499")) {
                    out.println("<input id='txtRptaInsertar' name='txtRptaInsertar' type='hidden' value='NO'> USUARIO RESERVADO.");
                    out.close();
                    dao.cerrarSession();
                    return;
                }
                TPersona persona = (TPersona) dao.load(TPersona.class, idpersona);
                if (persona != null) {
                    boolean dale = true;
                    String cuentas = "";
                    List giros = dao.findAll("from TRegistroGiro where idUserPkDestino='" + persona.getIdUserPk() + "'");
                    if (giros.size() > 0) {
                        Iterator i = giros.iterator();
                        while (i.hasNext()) {
                            TRegistroGiro a = (TRegistroGiro) i.next();
                            if (a.getEstado().equals("ESPERA")) {
                                dale = false;
                                cuentas += "Existe un Giro a nombre de la persona por recibir de fecha " + a.getFecha() + "\n";
                            } else {
                                a.setTPersona((TPersona) dao.load(TPersona.class, "20110404094329213499"));
                                dao.update();
                            }
                        }
                    }
                    giros = dao.findAll("from TRegistroGiro where TPersona.idUserPk ='" + persona.getIdUserPk() + "'");
                    if (giros.size() > 0) {
                        Iterator i = giros.iterator();
                        while (i.hasNext()) {
                            TRegistroGiro a = (TRegistroGiro) i.next();
                            if (a.getEstado().equals("ESPERA")) {
                                dale = false;
                                cuentas += "Existe un Giro a nombre de la persona por entregar de fecha " + a.getFecha() + "\n";
                            } else {
                                a.setTPersona((TPersona) dao.load(TPersona.class, "20110404094329213499"));
                                dao.update();
                            }
                        }
                    }
                    Set cuentitas = persona.getTCuentaPersonas();
                    Iterator ipCuentitas = cuentitas.iterator();
                    while (ipCuentitas.hasNext()) {
                        cuentas += "Elimine las siguientes cuentas:";
                        TCuentaPersona tcpe = (TCuentaPersona) ipCuentitas.next();
                        if ("ACTIVO".equals(tcpe.getEstado())) {
                            dale = false;
                            cuentas += "<br>" + tcpe.getNumCta();
                        }
                    }
                    if (dale) {
                        Set cuentitaA = persona.getTCuentaAccesos();
                        Iterator iCuentitaA = cuentitaA.iterator();
                        while (iCuentitaA.hasNext()) {
                            TCuentaAcceso tcac = (TCuentaAcceso) iCuentitaA.next();
                            String idcac = tcac.getIdAcceso();
                            if ("ACTIVO".equals(tcac.getEstado())) {
                                tcac = (TCuentaAcceso) dao.load(TCuentaAcceso.class, idcac);
                                tcac.setLogin("DISABLED" + DateUtil.getDateTime("yyyyMMddHHmmss", new Date()));
                                tcac.setEstado("INACTIVO");
                                dao.update();
                            }
                        }
                        Set pCajita = persona.getTPersonaCajas();
                        Iterator ipCajita = pCajita.iterator();
                        while (ipCajita.hasNext()) {
                            TPersonaCaja tpca = (TPersonaCaja) ipCajita.next();
                            String idpca = tpca.getIdpersonacaja();
                            if ("ACTIVO".equals(tpca.getEstado())) {
                                tpca = (TPersonaCaja) dao.load(TPersonaCaja.class, idpca);
                                tpca.setEstado("INACTIVO");
                                dao.update();
                            }
                        }
                        Set giritos = persona.getTRegistroGiros();
                        Iterator iGiritos = giritos.iterator();
                        while (iGiritos.hasNext()) {
                            TRegistroGiro trgi = (TRegistroGiro) iGiritos.next();
                            String idrgi = trgi.getIdregistro();
                            if ("ACTIVO".equals(trgi.getEstado())) {
                                trgi = (TRegistroGiro) dao.load(TRegistroGiro.class, idrgi);
                                trgi.setEstado("INACTIVO");
                                dao.update();
                            }
                        }
                        Set tipitos = persona.getTControlTipos();
                        Iterator iTipitos = tipitos.iterator();
                        while (iTipitos.hasNext()) {
                            TControlTipo tcti = (TControlTipo) iTipitos.next();
                            String idcti = tcti.getIdcontroltipo();
                            if ("ACTIVO".equals(tcti.getEstado())) {
                                tcti = (TControlTipo) dao.load(TControlTipo.class, idcti);
                                tcti.setEstado("INACTIVO");
                                dao.update();
                            }
                        }
                        Set cuentitaP = persona.getTCuentaPersonas();
                        Iterator iCuentitaP = cuentitaP.iterator();
                        while (iCuentitaP.hasNext()) {
                            TCuentaPersona tcpe = (TCuentaPersona) iCuentitaP.next();
                            String idcpe = tcpe.getIdcuentapersona();
                            if ("ACTIVO".equals(tcpe.getEstado())) {
                                tcpe = (TCuentaPersona) dao.load(TCuentaPersona.class, idcpe);
                                tcpe.setEstado("INACTIVO");
                                dao.update();
                            }
                        }
                        persona = (TPersona) dao.load(TPersona.class, idpersona);
                        persona.setDocIdentidad("DISABLED");
                        persona.setRuc("DISABLED");
                        persona.setEstado("INACTIVO");
                        dao.update();
                    } else {
                        out.println("<input id='txtRptaInsertar' name='txtRptaInsertar' type='hidden' value='NO'>");
                        out.print("<div id='r'>FALTA ELIMINAR PRIMERO LAS CUENTAS</div>" + cuentas);
                        out.close();
                        return;
                    }
                } else {
                    out.println("<input id='txtRptaInsertar' name='txtRptaInsertar' type='hidden' value='NO'>");
                    out.print("<div id='r'>La Persona ya fue eliminada</div>");
                    out.close();
                    return;
                }
            }
            out.println("<input id='txtRptaInsertar' name='txtRptaInsertar' type='hidden' value='SI'>");
        } catch (Exception e) {
            Logger.getLogger(SEliminarCatalogo.class.getName()).log(Level.WARNING, e.getMessage());
            out.println("<input id='txtRptaInsertar' name='txtRptaInsertar' type='hidden' value='NO'>");
            out.print("<div id='r'>OCURRIÓ UN PROBLEMA</div>");
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
        return "Catalogos - DELETE";
    }// </editor-fold>
}
