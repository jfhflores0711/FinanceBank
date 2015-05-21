package org.finance.bank.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.finance.bank.bean.Sumasnashot;
import org.finance.bank.bean.TCaja;
import org.finance.bank.bean.TCategoriaPersona;
import org.finance.bank.bean.TControlTipo;
import org.finance.bank.bean.TCuentaAcceso;
import org.finance.bank.bean.TDenominacionMoneda;
import org.finance.bank.bean.TLogFinance;
import org.finance.bank.bean.TMoneda;
import org.finance.bank.bean.TPersona;
import org.finance.bank.bean.TPersonaCaja;
import org.finance.bank.bean.TTipoPersona;
import org.finance.bank.model.dao.DAOGeneral;
import org.finance.bank.util.ConvertUtil;
import org.finance.bank.util.DateUtil;
import org.finance.bank.util.MD5;

/**
 *
 * @author admin
 */
public class SAddUser extends HttpServlet {

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
        String idForInitNewestTable = DateUtil.convertDateId("ID", SAddUser.class.getSimpleName());
        try {
            String estado = "ACTIVO";
            boolean ret = false;
            String dni = "";
            if (request.getParameter("d") != null) {
                dni = request.getParameter("d").toString();
            } else {
                out.print("mensaje: Dni incorrecto.");
                ret = true;
            }
            String ruc = "";
            if (request.getParameter("r") != null) {
                ruc = request.getParameter("r").toString();
            }
            String nombres = "";
            if (request.getParameter("n") != null && !request.getParameter("n").equals("")) {
                nombres = request.getParameter("n").toString();
            } else {
                out.print("mensaje=Nombres incorrecto.");
                ret = true;
            }
            String apellidos = "";
            if (request.getParameter("a") != null && !request.getParameter("a").equals("")) {
                apellidos = request.getParameter("a").toString();
            } else {
                out.print("mensaje=Apellidos incorrecto.");
                ret = true;
            }
            String email = "";
            if (request.getParameter("e") != null && !request.getParameter("e").equals("")) {
                email = request.getParameter("e").toString();
            }
            String ubigeo = "050101";
            if (request.getParameter("u") != null && !request.getParameter("u").equals("")) {
                ubigeo = request.getParameter("u").toString();
            } else {
                out.print("mensaje=Ubigeo incorrecto.");
                ret = true;
            }
            String telefono = "";
            if (request.getParameter("t") != null && !request.getParameter("t").equals("")) {
                telefono = request.getParameter("t").toString();
            }
            String celular = "";
            if (request.getParameter("c") != null && !request.getParameter("c").equals("")) {
                celular = request.getParameter("c").toString();
            }
            String direccion = "";
            if (request.getParameter("di") != null && !request.getParameter("di").equals("")) {
                direccion = request.getParameter("di").toString();
            } else {
                out.print("mensaje=Direccion incorrecta.");
                ret = true;
            }
            String login = "";
            if (request.getParameter("l") != null && !request.getParameter("l").equals("")) {
                login = request.getParameter("l").toString();
            } else {
                out.print("mensaje=Login incorrecto.");
                ret = true;
            }
            String contrasenia = "";
            if (request.getParameter("co") != null && !request.getParameter("co").equals("")) {
                contrasenia = request.getParameter("co").toString();
            } else {
                out.print("mensaje=Contraseña incorrecta.");
                ret = true;
            }
            String ipacceso = "";
            if (request.getParameter("ip") != null && !request.getParameter("ip").equals("")) {
                ipacceso = request.getParameter("ip").toString();
            } else {
                out.print("mensaje=IP incorrecto.");
                ret = true;
            }
            String grupo = "";
            if (request.getParameter("gr") != null && !request.getParameter("gr").equals("")) {
                grupo = request.getParameter("gr").toString();
            } else {
                out.print("mensaje=Grupo incorrecto.");
                ret = true;
            }
            String caja = "";
            if (request.getParameter("caja") != null && !request.getParameter("caja").equals("")) {
                caja = request.getParameter("caja").toString();
            } else {
                out.print("mensaje=Caja incorrecta.");
                ret = true;
            }
            String categoria = "";
            if (request.getParameter("ca") != null && !request.getParameter("ca").equals("")) {
                categoria = request.getParameter("ca").toString();
            } else {
                out.print("mensaje=Categoria incorrecta.");
                ret = true;
            }

            String sql = "from TCuentaAcceso where login = '" + login + "'  and estado='ACTIVO'";
            List result = dao.findAll(sql);
            if (result.size() > 0) {
                out.print("ERROR: Ya Existe el LOGIN en USO!");
                ret = true;
            }

            sql = "from TPersonaCaja where TCaja.codCaja ='" + caja + "' AND  estado ='ACTIVO'";
            result = dao.findAll(sql);
            if (result.size() > 0) {
                out.print("ERROR: La caja Ya tien Un Usuario Activo!");
                ret = true;
            }

            if (ret) {
                dao.cerrarSession();
                out.close();
                return;
            }

            TPersona persona = new TPersona();
            persona.setIdUserPk(idForInitNewestTable);
            persona.setDocIdentidad(dni);
            persona.setNombre(nombres.toUpperCase());
            persona.setApellidos(apellidos.toUpperCase());
            persona.setEmail(email);
            persona.setUbigeo(ubigeo);
            persona.setTelefono(telefono);
            persona.setCelular(celular);
            persona.setUrlFoto("&nbsp;");
            persona.setUrlFirma("&nbsp;");
            persona.setDireccion(direccion.toUpperCase());
            persona.setEstado(estado);
            persona.setIdUser("ID");
            persona.setIpUser("IP");
            persona.setDateUser("DATE");
            TCategoriaPersona tcategoria = (TCategoriaPersona) dao.load(TCategoriaPersona.class, categoria);
            persona.setTCategoriaPersona(tcategoria);
            persona.setRuc(ruc);
            dao.persist(persona);
            TControlTipo control_grupo = new TControlTipo();
            control_grupo.setIdcontroltipo(idForInitNewestTable);
            control_grupo.setTPersona(persona);
            TTipoPersona ttipopersona = (TTipoPersona) dao.load(TTipoPersona.class, grupo);
            control_grupo.setTTipoPersona(ttipopersona);
            control_grupo.setEstado(estado);
            control_grupo.setIdUser("ID");
            control_grupo.setIpUser("IP");
            control_grupo.setDateUser("DATE");
            dao.persist(control_grupo);
            TCuentaAcceso cuenta = new TCuentaAcceso();
            cuenta.setIdAcceso(idForInitNewestTable);
            cuenta.setLogin(ConvertUtil.prepareStringParameter(login).toLowerCase());
            cuenta.setContrasenia(MD5.encriptar(contrasenia));
            cuenta.setEstado(estado);
            cuenta.setIdUser("ID");
            cuenta.setIpUser("IP");
            cuenta.setDateUser("DATE");
            cuenta.setTPersona(persona);
            cuenta.setIpAcceso(ipacceso);
            cuenta.setKeyring(MD5.encriptar(MD5.encriptar(contrasenia).toLowerCase() + login.toLowerCase()));
            dao.persist(cuenta);
            TPersonaCaja pcaja = new TPersonaCaja();
            pcaja.setIdpersonacaja(idForInitNewestTable);
            pcaja.setTPersona(persona);
            TCaja tcaja = (TCaja) dao.load(TCaja.class, caja);
            pcaja.setEstado(estado);
            pcaja.setTCaja(tcaja);
            pcaja.setIdUser("ID");
            pcaja.setIpUser("IP");
            pcaja.setDateUser("DATE");
            dao.persist(pcaja);
            List monedasList = dao.findAll("from TMoneda where estado='ACTIVO' order by codMoneda");
            if (monedasList.size() > 0) {
                Iterator iteraMoneda = monedasList.iterator();
                while (iteraMoneda.hasNext()) {
                    TMoneda moneda = (TMoneda) iteraMoneda.next();
                    TLogFinance logCaja = (TLogFinance) dao.load(TLogFinance.class, "LOG" + tcaja.getCodCaja() + moneda.getCodMoneda());
                    List listSumas = dao.findAll("from Sumasnashot where idsuma like 'SUM" + tcaja.getCodCaja() + moneda.getCodMoneda() + "%'");
                    if (logCaja == null) {
                        TLogFinance l0g = new TLogFinance("LOG" + tcaja.getCodCaja() + moneda.getCodMoneda(), "SNAPSHOT", "",
                                BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
                                "FECHA", tcaja.getCodCaja(), "ACTIVO", tcaja.getTFilial().getCodFilial(), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
                        dao.persist(l0g);
                        logCaja = l0g;
                    }
                    if (listSumas.isEmpty()) {
                        Set listaDetalleDen = moneda.getTDenominacionMonedas();
                        if (listaDetalleDen.size() > 0) {
                            Iterator iteraDen = listaDetalleDen.iterator();
                            while (iteraDen.hasNext()) {
                                TDenominacionMoneda denActual = (TDenominacionMoneda) iteraDen.next();
                                Sumasnashot suma = new Sumasnashot();
                                suma.setCajero(tcaja.getCodCaja());
                                suma.setCantidad(0);
                                suma.setEstado("ACTIVO" + denActual.getIddenominacionmoneda());
                                String tamanio = String.valueOf(Double.parseDouble(denActual.getMonto()) * 100);
                                suma.setIdsuma("SUM" + tcaja.getCodCaja() + moneda.getCodMoneda() + "000000000".substring(tamanio.length()) + tamanio);
                                suma.setMoneda(moneda.getCodMoneda());
                                suma.setMontodenominacio(denActual.getMonto());
                                dao.persist(suma);
                            }
                        }
                    }
                }
            }
            out.print("OK");
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
