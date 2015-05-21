package org.finance.bank.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.finance.bank.bean.TCategoriaPersona;
import org.finance.bank.bean.TControlTipo;
import org.finance.bank.bean.TPersona;
import org.finance.bank.bean.TRegistroGiro;
import org.finance.bank.bean.TTipoPersona;
import org.finance.bank.model.dao.DAOGeneral;

/**
 *
 * @author ronald
 */
public class Sbuscarpersona extends HttpServlet {

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
        DAOGeneral dao = new DAOGeneral();
        try {
            String dni = "";
            if (request.getParameter("dni") != null) {
                dni = request.getParameter("dni").toString();
            }
            String ruc = "";
            if (request.getParameter("ruc") != null) {
                ruc = request.getParameter("ruc").toString();
            }
            String categoria = "";
            if (request.getParameter("categoria") != null && !request.getParameter("categoria").equals("")) {
                categoria = request.getParameter("categoria").toString();
            }
            String er = "";
            if (request.getParameter("er") != null && !request.getParameter("er").equals("")) {
                er = request.getParameter("er").toString();
            }
            String nombres = "";
            if (request.getParameter("nom") != null && !request.getParameter("nom").equals("")) {
                nombres = request.getParameter("nom").toString();
            }
            String apellidos = "";
            if (request.getParameter("ap") != null && !request.getParameter("ap").equals("")) {
                apellidos = request.getParameter("ap").toString();
            }
            String tipoB = "F";
            if (er.equals("EH") || er.equals("RH")) {
                tipoB = "H";
                if (er.equals("EH")) {
                    er = "E";
                } else {
                    er = "R";
                }
            } else {
                if (er.equals("EF")) {
                    er = "E";
                } else {
                    er = "R";
                }
            }
            TCategoriaPersona cat = (TCategoriaPersona) dao.load(TCategoriaPersona.class, categoria);
            if (cat == null) {
                cat = (TCategoriaPersona) dao.load(TCategoriaPersona.class, "20110228202911410395");
            }
            TPersona personabuscada = new TPersona();
            personabuscada.setIdUserPk("0");
            personabuscada.setRuc("");
            personabuscada.setNombre("");
            personabuscada.setApellidos("");
            personabuscada.setEmail("");
            personabuscada.setUbigeo("");
            personabuscada.setTelefono("");
            personabuscada.setCelular("");
            personabuscada.setUrlFirma("&nbsp;");
            personabuscada.setUrlFoto("&nbsp;");
            personabuscada.setDireccion("");
            personabuscada.setTCategoriaPersona(cat);
            String cruc = "";
            String cgrupo = "-grupo=CLIENTE";
            if (tipoB.equals("F")) {
                if (cat.getDescripcion().equals("NATURAL")) {
                    String hql = "from TPersona where docIdentidad like '%" + dni + "%'";
                    List l = dao.findAll(hql);
                    if (l.size() > 0) {
                        personabuscada = (TPersona) l.get(0);
                        cruc = "-" + er + "ruc=" + (personabuscada.getRuc() == null ? "." : personabuscada.getRuc());
                    } else {
                        if (personabuscada.getIdUserPk().equals("0") || dni.equals("")) {
                            cruc = er + "0";
                            out.print(cruc);
                            out.close();
                            return;
                        }
                    }
                } else {
                    String hql = "from TPersona where ruc like '%" + ruc + "'%";
                    List l = dao.findAll(hql);
                    if (l.size() > 0) {
                        personabuscada = (TPersona) l.get(0);
                        cruc = "";
                    } else {
                        if (personabuscada.getIdUserPk().equals("0")) {
                            cruc = er + "0";
                            out.print(cruc);
                            out.close();
                            return;
                        }
                        cruc = "";
                    }
                }
            } else {
                if (cat.getDescripcion().equals("NATURAL")) {
                    String hql = "from TPersona where nombre like '%" + nombres + "%' and apellidos='" + apellidos + "'";
                    List l = dao.findAll(hql);
                    if (l.size() > 0) {
                        personabuscada = (TPersona) l.get(0);
                        if (personabuscada.getDocIdentidad().equals("DISABLED") || personabuscada.getEstado().equals("INACTIVO")) {
                            personabuscada.setDocIdentidad("");
                            personabuscada.setEstado("ACTIVO");
                            personabuscada.setRuc("");
                            dao.update();
                        }
                        cruc = "-" + er + "ruc=" + (personabuscada.getRuc() == null ? "." : personabuscada.getRuc());
                    } else {
                        if (personabuscada.getIdUserPk().equals("0") || dni.equals("")) {
                            cruc = er + "0";
                            out.print(cruc);
                            out.close();
                            return;
                        }
                    }
                } else {
                    String hql = "from TPersona where nombre like '%" + nombres + "'%";
                    List l = dao.findAll(hql);
                    if (l.size() > 0) {
                        personabuscada = (TPersona) l.get(0);
                        cruc = "";
                        if (personabuscada.getDocIdentidad().equals("DISABLED") || personabuscada.getEstado().equals("INACTIVO")) {
                            personabuscada.setDocIdentidad("");
                            personabuscada.setEstado("ACTIVO");
                            personabuscada.setRuc("");
                            dao.update();
                        }
                    } else {
                        if (personabuscada.getIdUserPk().equals("0")) {
                            cruc = er + "0";
                            out.print(cruc);
                            out.close();
                            return;
                        }
                        cruc = "";
                    }
                }
            }
            String idregistro = (String) request.getParameter("idregistro");
            if (idregistro != null) {
                Logger.getLogger(Sbuscarpersona.class.getName()).log(Level.INFO, "idregistro = " + idregistro);
                TRegistroGiro trg = (TRegistroGiro) dao.load(TRegistroGiro.class, idregistro);
                if (trg != null) {
                    if (er.equals("E")) {
                        personabuscada = trg.getTPersona();
                    } else {
                        personabuscada = (TPersona) dao.load(TPersona.class, trg.getIdUserPkDestino());
                    }
                }
            }
            try {
                if (er.equals("E")) {
                    TControlTipo control_grupo = (TControlTipo) dao.findAll("from TControlTipo where TPersona.idUserPk ='" + personabuscada.getIdUserPk() + "'").get(0);
                    Logger.getLogger(Sbuscarpersona.class.getName()).log(Level.INFO, "control_grupo = " + control_grupo.getIdcontroltipo());
                    TTipoPersona ttipopersona = (TTipoPersona) dao.load(TTipoPersona.class, control_grupo.getTTipoPersona().getIdtipopersona());
                    cgrupo = "-grupo=" + ttipopersona.getDescripcion();
                } else {
                    cgrupo = "";
                }
            } catch (IndexOutOfBoundsException ex) {
                cruc = er + "0";
                out.print(cruc);
                out.close();
                return;
            }
            String str = er + "idpersona=" + personabuscada.getIdUserPk()
                    + cruc
                    + "-" + er + "nombres=" + personabuscada.getNombre()
                    + "-" + er + "apellidos=" + (personabuscada.getApellidos() == null ? "" : personabuscada.getApellidos())
                    + "-" + er + "email=" + (personabuscada.getEmail() == null ? "" : personabuscada.getEmail())
                    + "-" + er + "ubigeo=" + (personabuscada.getUbigeo() == null ? "" : personabuscada.getUbigeo())
                    + "-" + er + "telefono=" + (personabuscada.getTelefono() == null ? "" : personabuscada.getTelefono())
                    + "-" + er + "celular=" + (personabuscada.getCelular() == null ? "" : personabuscada.getCelular())
                    + "-" + er + "direccion=" + (personabuscada.getDireccion() == null ? "" : personabuscada.getDireccion())
                    + cgrupo;
            out.println(str);
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
