package org.finance.bank.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.finance.bank.bean.*;
import org.finance.bank.model.dao.DAOGeneral;
import org.finance.bank.util.TextUtil;

/**
 *
 * @author ZAMORA
 */
public class SActualizarCatalogo extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        request.getSession(true);
        DAOGeneral dao = new DAOGeneral();
        try {
            String catalogo = request.getParameter("a");
            String ip_user = request.getRemoteAddr();
            if (catalogo.equals("FILIAL")) {
                String codigo = request.getParameter("b");
                if (codigo == null) {
                    out.println("<input id='txtRptaInsertar' name='txtRptaInsertar' type='hidden' value='NO'>");
                    out.print("No se puede Actualizar la filial");
                    out.close();
                    return;
                }
                String nombre = request.getParameter("c");
                if (nombre == null) {
                    nombre = "FILIAL" + codigo;
                }
                String direccion = request.getParameter("d");
                if (direccion == null) {
                    direccion = "N/A";
                }
                String estado = request.getParameter("e");
                if (estado == null) {
                    estado = "INACTIVO";
                }
                String telefono = request.getParameter("f");
                if (telefono == null) {
                    telefono = "S/n";
                }
                String distrito = request.getParameter("h");
                if (distrito == null) {
                    distrito = "";
                }
                TDistrito dist = (TDistrito) dao.load(TDistrito.class, distrito);
                if (dist == null) {
                    return;
                }
                TFilial filial = (TFilial) dao.load(TFilial.class, codigo);
                filial.setNombre(nombre.replace("SHRP", "#").replaceAll("AMPSD", "&").replaceAll("ASTRX", "*"));
                filial.setDireccion(direccion.replace("SHRP", "#").replaceAll("AMPSD", "&").replaceAll("ASTRX", "*"));
                filial.setEstado(estado);
                filial.setTelefono(TextUtil.trimMaxSize(telefono.replace("SHRP", "#").replaceAll("AMPSD", "&").replaceAll("ASTRX", "*"), 10));
                filial.setIpUser(ip_user);
                DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                filial.setDateUser(df.format(new Date()));
                filial.setTDistrito(dist);
                dao.update();
            } else if (catalogo.equals("CAJA")) {
                String codigo = request.getParameter("b");
                if (codigo == null) {
                    out.println("<input id='txtRptaInsertar' name='txtRptaInsertar' type='hidden' value='NO'>");
                    out.print("No se puede Actualizar a la CAJA");
                    out.close();
                    return;
                }
                String nombre = request.getParameter("c");
                if (nombre == null) {
                    nombre = "CAJA" + codigo;
                }
                String tipo = request.getParameter("i");
                if (tipo == null) {
                    tipo = "SECONDARY";
                }
                String estado = request.getParameter("e");
                if (estado == null) {
                    estado = "INACTIVO";
                }
                String codfilial = request.getParameter("j");
                if (codfilial == null) {
                    codfilial = "0501";
                }
//                Transaction tr = sess.beginTransaction();
                TFilial filial = (TFilial) dao.load(TFilial.class, codfilial);
                TCaja caja = (TCaja) dao.load(TCaja.class, codigo);
                caja.setNombreCaja(nombre.replace("SHRP", "#").replaceAll("AMPSD", "&").replaceAll("ASTRX", "*"));
                caja.setTipo(tipo);
                caja.setEstado(estado);
                caja.setIpUser(ip_user);
                DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                caja.setDateUser(df.format(new Date()));
                caja.setTFilial(filial);
                dao.update();
            } else if (catalogo.equals("A")) {
                String txtN = request.getParameter("l");
                String txtD = request.getParameter("b");
                String txtM = request.getParameter("c");
                String txtI = request.getParameter("d");
                String txtA = request.getParameter("e");
                String txtB = request.getParameter("f");
                String txtG = request.getParameter("g");
                String txtE = request.getParameter("h");
                String txtH = request.getParameter("i");
                String txtJ = request.getParameter("j");
                String selI = request.getParameter("m");
                String selJ = request.getParameter("k");
                txtN = txtN.replace("SHRP", "#").replaceAll("AMPSD", "&").replaceAll("ASTRX", "*").toUpperCase();
                txtD = txtD.replace("SHRP", "#").replaceAll("AMPSD", "&").replaceAll("ASTRX", "*").toUpperCase();
                String txtId = request.getParameter("z");
                if (txtI == null) {
                    out.println("<input id='txtRptaInsertar' name='txtRptaInsertar' type='hidden' value='NO'>");
                    out.print("No se puede Actualizar CRÉDITOS");
                    out.close();
                    return;
                }
                TTipoCredito o = (TTipoCredito) dao.load(TTipoCredito.class, txtId);
                if (o == null) {
                    out.println("<input id='txtRptaInsertar' name='txtRptaInsertar' type='hidden' value='NO'>");
                    out.print("No se puede Actualizar CRÉDITOS");
                    out.close();
                    return;
                } else {
                    o.setNombre(txtN);
                    o.setDescripcion(txtD);
                    o.setDiasEntreCuotas(Integer.parseInt(txtJ));
                    o.setFormaCalculoIntereses(Integer.parseInt(selI));
                    o.setMontoMaximo(new BigDecimal(txtM));
                    o.setTasaInteresCompensatorio(new BigDecimal(txtA));
                    o.setMontoMinimo(new BigDecimal(txtE));
                    o.setNumeroCuotasMax(Integer.parseInt(txtI));
                    o.setNumeroCuotasMin(Integer.parseInt(txtG));
                    o.setRequiereGarantia(Integer.parseInt(selJ));
                    o.setTasaInteresMax(new BigDecimal(txtA));
                    o.setTasaInteresMin(new BigDecimal(txtH));
                    o.setTasaInteresMoratoria(new BigDecimal(txtB));
                    dao.update();
                }
            } else if (catalogo.equals("PERSONA")) {
                String idpersona = request.getParameter("m");
                if (idpersona == null) {
                    out.println("<input id='txtRptaInsertar' name='txtRptaInsertar' type='hidden' value='NO'>");
                    out.print("No se puede Actualizar a la PERSONA");
                    out.close();
                    return;
                }
                String DNI = request.getParameter("k");
                if (DNI == null) {
                    DNI = "--";
                }
                String RUC = request.getParameter("l");
                if (RUC == null) {
                    RUC = "--";
                }
                String nombre = request.getParameter("c");
                if (nombre == null) {
                    nombre = "S/N";
                }
                String apellidos = request.getParameter("n");
                if (apellidos == null) {
                    apellidos = "--";
                }
                String email = request.getParameter("o");
                if (email == null) {
                    email = "--";
                }
                String ubigeo = request.getParameter("p");
                if (ubigeo == null) {
                    ubigeo = "--";
                }
                String telefono = request.getParameter("f");
                if (telefono == null) {
                    telefono = "S/n";
                }
                String celular = request.getParameter("q");
                if (celular == null) {
                    celular = "--";
                }
                String urlfoto = request.getParameter("r");
                if (urlfoto == null) {
                    urlfoto = "";
                }
                String urlfirma = request.getParameter("s");
                if (urlfirma == null) {
                    urlfirma = "";
                }
                String direccion = request.getParameter("d");
                if (direccion == null) {
                    direccion = "S/D";
                }
                String estado = request.getParameter("e");
                if (estado == null) {
                    estado = "INACTIVO";
                }
                String categoria = request.getParameter("u");
                if (categoria == null) {
                    categoria = "NATURAL";
                }
                TCategoriaPersona catper = (TCategoriaPersona) dao.load(TCategoriaPersona.class, categoria);
                TPersona persona = (TPersona) dao.load(TPersona.class, idpersona);
                persona.setDocIdentidad(DNI);
                persona.setRuc(RUC);
                persona.setNombre(nombre.replace("SHRP", "#").replaceAll("AMPSD", "&").replaceAll("ASTRX", "*"));
                persona.setApellidos(apellidos.replace("SHRP", "#").replaceAll("AMPSD", "&").replaceAll("ASTRX", "*"));
                persona.setEmail(email);
                persona.setUbigeo(ubigeo);
                persona.setTelefono(telefono.replace("SHRP", "#").replaceAll("AMPSD", "&").replaceAll("ASTRX", "*"));
                persona.setCelular(celular.replace("SHRP", "#").replaceAll("AMPSD", "&").replaceAll("ASTRX", "*"));
                persona.setUrlFoto(urlfoto);
                persona.setUrlFirma(urlfirma);
                persona.setDireccion(direccion.replace("SHRP", "#").replaceAll("AMPSD", "&").replaceAll("ASTRX", "*"));
                persona.setEstado(estado);
                persona.setTCategoriaPersona(catper);
                persona.setIpUser(ip_user);
                DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                persona.setDateUser(df.format(new Date()));
                dao.update();
            } else {
                out.println("<input id='txtRptaInsertar' name='txtRptaInsertar' type='hidden' value='NO'>");
                out.print("");
                out.close();
                return;
            }
            out.println("<input id='txtRptaInsertar' name='txtRptaInsertar' type='hidden' value='SI'>");
        } catch (Exception e) {
            out.println("<input id='txtRptaInsertar' name='txtRptaInsertar' type='hidden' value='NO'>");
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
