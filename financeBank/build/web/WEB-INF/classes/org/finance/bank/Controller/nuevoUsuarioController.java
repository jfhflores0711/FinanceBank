package org.finance.bank.Controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.finance.bank.bean.TCaja;
import org.finance.bank.bean.TCategoriaPersona;
import org.finance.bank.bean.TControlTipo;
import org.finance.bank.bean.TCuentaAcceso;
import org.finance.bank.bean.TPersona;
import org.finance.bank.bean.TPersonaCaja;
import org.finance.bank.bean.TTipoPersona;
import org.finance.bank.model.dao.DAOGeneral;
import org.finance.bank.util.DateUtil;
import org.finance.bank.util.MD5;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class nuevoUsuarioController implements Controller {

    private String formView;
    private String successView;

    protected ModelAndView onSubmit(Object command) throws Exception {
        return new ModelAndView(this.getFormView(), "mensaje", "Ingrese un usuario y password correcto.");
    }

    @SuppressWarnings("static-access")
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DAOGeneral dao = new DAOGeneral();
        Map mensaje = new HashMap();
        String idForInitNewestTable = DateUtil.convertDateId("NOUSER", nuevoUsuarioController.class.getSimpleName());
        String estado = "";
        if (request.getParameter("estado") != null) {
            estado = request.getParameter("estado").toString();
        }
        String dni = "";
        if (request.getParameter("dni") != null) {
            dni = request.getParameter("dni").toString();
        } else {
            mensaje.put("mensaje", "Dni incorrecto.");
            return new ModelAndView(this.getSuccessView(), mensaje);
        }
        String ruc = "";
        if (request.getParameter("ruc") != null) {
            ruc = request.getParameter("ruc").toString();
        }
        String nombres = "";
        if (request.getParameter("nombres") != null && !request.getParameter("nombres").equals("")) {
            nombres = request.getParameter("nombres").toString();
        } else {
            mensaje.put("mensaje", "Nombres incorrecto.");
            return new ModelAndView(this.getSuccessView(), mensaje);
        }
        String apellidos = "";
        if (request.getParameter("apellidos") != null && !request.getParameter("apellidos").equals("")) {
            apellidos = request.getParameter("apellidos").toString();
        } else {
            mensaje.put("mensaje", "Apellidos incorrecto.");
            return new ModelAndView(this.getSuccessView(), mensaje);
        }
        String email = "";
        if (request.getParameter("email") != null && !request.getParameter("email").equals("")) {
            email = request.getParameter("email").toString();
        }
        String ubigeo = "";
        if (request.getParameter("ubigeo") != null && !request.getParameter("ubigeo").equals("")) {
            ubigeo = request.getParameter("ubigeo").toString();
        } else {
            mensaje.put("mensaje", "Ubigeo incorrecto.");
            return new ModelAndView(this.getSuccessView(), mensaje);
        }
        String telefono = "";
        if (request.getParameter("telefono") != null && !request.getParameter("telefono").equals("")) {
            telefono = request.getParameter("telefono").toString();
        }
        String celular = "";
        if (request.getParameter("celular") != null && !request.getParameter("celular").equals("")) {
            celular = request.getParameter("celular").toString();
        }
        String direccion = "";
        if (request.getParameter("direccion") != null && !request.getParameter("direccion").equals("")) {
            direccion = request.getParameter("direccion").toString();
        } else {
            mensaje.put("mensaje", "Direccion incorrecta.");
            return new ModelAndView(this.getSuccessView(), mensaje);
        }
        String login = "";
        if (request.getParameter("login") != null && !request.getParameter("login").equals("")) {
            login = request.getParameter("login").toString();
        } else {
            mensaje.put("mensaje", "Login incorrecto.");
            return new ModelAndView(this.getSuccessView(), mensaje);
        }
        String contrasenia = "";
        if (request.getParameter("contrasenia") != null && !request.getParameter("contrasenia").equals("")) {
            contrasenia = request.getParameter("contrasenia").toString();
        } else {
            mensaje.put("mensaje", "Contraseña incorrecta.");
            return new ModelAndView(this.getSuccessView(), mensaje);
        }
        String ipacceso = "";
        if (request.getParameter("ipacceso") != null && !request.getParameter("ipacceso").equals("")) {
            ipacceso = request.getParameter("ipacceso").toString();
        } else {
            mensaje.put("mensaje", "IP incorrecto.");
            return new ModelAndView(this.getFormView(), mensaje);
        }
        String grupo = "";
        if (request.getParameter("grupo") != null && !request.getParameter("grupo").equals("")) {
            grupo = request.getParameter("grupo").toString();
        } else {
            mensaje.put("mensaje", "Grupo incorrecto.");
            return new ModelAndView(this.getSuccessView(), mensaje);
        }
        String caja = "";
        if (request.getParameter("caja") != null && !request.getParameter("caja").equals("")) {
            caja = request.getParameter("caja").toString();
        } else {
            mensaje.put("mensaje", "Caja incorrecta.");
            return new ModelAndView(this.getSuccessView(), mensaje);
        }
        String categoria = "";
        if (request.getParameter("categoria") != null && !request.getParameter("categoria").equals("")) {
            categoria = request.getParameter("categoria").toString();
        } else {
            mensaje.put("mensaje", "Categoria incorrecta.");
            return new ModelAndView(this.getFormView(), mensaje);
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
        cuenta.setLogin(login);
        MD5 md5 = new MD5();
        cuenta.setContrasenia(md5.encriptar(contrasenia));
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
        return new ModelAndView(this.getSuccessView());
    }

    /**
     * @return the formView
     */
    public String getFormView() {
        return formView;
    }

    /**
     * @param formView the formView to set
     */
    public void setFormView(String formView) {
        this.formView = formView;
    }

    /**
     * @return the successView
     */
    public String getSuccessView() {
        return successView;
    }

    /**
     * @param successView the successView to set
     */
    public void setSuccessView(String successView) {
        this.successView = successView;
    }
}
