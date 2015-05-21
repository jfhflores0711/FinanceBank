package org.finance.bank.Controller;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.*;
import org.finance.bank.bean.*;
import org.finance.bank.model.*;
import org.finance.bank.model.dao.DAOGeneral;
import org.finance.bank.util.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class LoginFormController implements Controller {

    private String formView;
    private String successView;

    protected ModelAndView onSubmit(Object command) throws Exception {
        return new ModelAndView(this.getFormView(), "mensaje", "Ingrese un usuario y password correctos.");
    }

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map datosError = new HashMap();
        HttpSession session = request.getSession(true);
        TCuentaAcceso cuenta = new TCuentaAcceso();
        Date now = new Date();
        String FECHAB = DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", now);
        String add = request.getRemoteAddr();
        cuenta.setLogin(ConvertUtil.prepareStringParameter(request.getParameter("login")).toLowerCase());
//        cuenta.setContrasenia(request.getParameter("contrasenia"));
        String keyring = MD5.encriptar(MD5.encriptar(request.getParameter("contrasenia")).toLowerCase() + request.getParameter("login").toLowerCase());
        DAOGeneral dao = new DAOGeneral();
        String sql = "from TCuentaAcceso where keyring = '" + keyring + "' and estado='ACTIVO'";
        List result = dao.findAll(sql);
        if (result.size() > 0) {
            Iterator it = result.iterator();
            if (it.hasNext()) {
                TCuentaAcceso d = (TCuentaAcceso) it.next();
                boolean f = (boolean) HibernateUtil.firstLogin(now, FECHAB);
                session.setAttribute("SESSION", "true");
                session.setAttribute("USER_LOGIN", request.getParameter("login"));
                TPersona persona = (TPersona) d.getTPersona();
                session.setAttribute("USER_ID", persona.getIdUserPk());
                session.setAttribute("USER_IP", add);
                if (f) {
                    boolean estadoBD = (boolean) HibernateUtil.dbBlocked(FECHAB);
                    if (estadoBD) {
                        Logger.getLogger(LoginFormController.class.getName()).log(Level.INFO, "estadoBD = " + estadoBD);
                        adminCapital.initChecksDatabaseForWork();
                        CheckCapital.capitalizar(now);
                        //Logger.getLogger(LoginFormController.class.getName()).log(Level.INFO, "iniPat = " + iniPatrimonio.comprobarIni());
                        dao.executeUpdate("delete from TTransaccion");
                        HibernateUtil.truncarBloqueo();
                        HibernateUtil.dbUnBlocker();
                    } else {
                        session.setAttribute("SESSION", "false");
                        session.invalidate();
                        datosError.put("mensaje", "PROBLEMA SERIO CON LA BASE DE DATOS... INTENTE INICIAR SESION NUEVAMENTE");
                        dao.cerrarSession();
                        return new ModelAndView(this.getFormView(), datosError);
                    }
                }
                if (persona.getEstado().equals("FULL")) {
                    persona.setEstado("ACTIVO");
                }
                if (!persona.getEstado().equals("ACTIVO")) {
                    session.setAttribute("SESSION", "false");
                    session.invalidate();
                    datosError.put("mensaje", "Usuario deshabilitado por el Administrador");
                    dao.cerrarSession();
                    return new ModelAndView(this.getFormView(), datosError);
                }
                TTransaccionC blockeado = (TTransaccionC) dao.load(TTransaccionC.class, "2011050217183907447300");
                if (blockeado != null) {
                    session.setAttribute("SESSION", "false");
                    session.invalidate();
                    datosError.put("mensaje", "LA BASE DE DATOS ESTA BLOQUEADA DEBIDO A TRABAJOS INTERNOS, VUELVA A INICIAR SESIÓN");
                    dao.cerrarSession();
                    return new ModelAndView(this.getFormView(), datosError);
                }
                List lctp = dao.findAll("from TControlTipo where TPersona.idUserPk ='" + persona.getIdUserPk() + "' AND  estado ='ACTIVO'");
                if (lctp.isEmpty()) {
                    session.setAttribute("SESSION", "false");
                    session.invalidate();
                    datosError.put("mensaje", "ERROR: COMUNIQUESE CON EL ADMINISTRADOR DEL SISTEMA.");
                    return new ModelAndView(this.getFormView(), datosError);
                }
                TControlTipo ctp = (TControlTipo) lctp.get(0);
                if (ctp.getEstado().equals("ACTIVO")) {
                    TTipoPersona tipop = (TTipoPersona) dao.load(TTipoPersona.class, ctp.getTTipoPersona().getIdtipopersona());
                    if (ip_acceso(d.getIpAcceso(), add)) {
                        session.setAttribute("FECHA", fecha());
                        session.setAttribute("FECHAB", FECHAB);
                        String hql = "from TPersonaCaja where TPersona.idUserPk ='" + persona.getIdUserPk() + "' AND  estado ='ACTIVO'";
                        List lpcaja = dao.findAll(hql);
                        if (lpcaja.isEmpty()) {
                            session.setAttribute("SESSION", "false");
                            session.invalidate();
                            datosError.put("mensaje", "ERROR DE CAJA.");
                            dao.cerrarSession();
                            return new ModelAndView(this.getFormView(), datosError);
                        }
                        TPersonaCaja pcaja = (TPersonaCaja) lpcaja.get(0);
                        TCaja caja = (TCaja) pcaja.getTCaja();
                        TFilial filial = (TFilial) caja.getTFilial();
                        session.setAttribute("USER_NAME", persona.getNombre() + " " + persona.getApellidos());
                        session.setAttribute("USER_ID_ROLE", tipop.getIdtipopersona());
                        session.setAttribute("USER_DESCRIPCION_ROLE", tipop.getDescripcion());
                        session.setAttribute("USER_CODFILIAL", filial.getCodFilial());
                        session.setAttribute("USER_CODCAJA", caja.getCodCaja());
                        session.setAttribute("USER_TIPOCAJA", caja.getTipo());
                        session.setAttribute("USER_FILIAL", filial.getNombre());
                        session.setAttribute("USER_CAJA", caja.getNombreCaja());
                        session.setAttribute("USER_ID_PERSONA_CAJA", pcaja.getIdpersonacaja());
                        session.setAttribute("CADENA_SUMA", "");
                        String hid = MD5.encriptar(d.getLogin() + d.getContrasenia() + persona.getIdUserPk() + add).toUpperCase();
                        List x = dao.findAll("from TSesion where fecha like '" + DateUtil.getDate(now) + "%' and idUser='" + hid + "'");
                        String m = "";
                        if (x.isEmpty()) {
                            TSesion tses = new TSesion(DateUtil.convertDateId(pcaja.getIdpersonacaja(), LoginFormController.class.getSimpleName()),
                                    d, FECHAB, "LOGIN NUMERO 1", hid, add, FECHAB);
                            //public TSesion(String idsession, TCuentaAcceso TCuentaAcceso, String fecha, String accion, String idUser, String ipUser, String dateUser)
                            dao.persist(tses);
                        } else {
                            Iterator y = x.iterator();
                            m += "Otras Sesiones anteriores desde: ";
                            while (y.hasNext()) {
                                TSesion z = (TSesion) y.next();
                                if (z.getIdUser().equals(hid)) {
                                    int w = 0;
                                    try {
                                        w = (int) Integer.parseInt(z.getAccion().replace("LOGIN NUMERO ", ""));
                                    } catch (Exception e) {
                                        Logger.getLogger(LoginFormController.class.getName()).log(Level.WARNING, "NO SE PUDO NUMERAR LA SESIÓN POR " + e.getMessage());
                                    }
                                    w++;
                                    z.setAccion("LOGIN NUMERO " + String.valueOf(w));
                                    dao.update();
                                    m += "Desde esta máquina\n";
                                } else {
                                    m += "" + z.getIpUser() + " a horas " + z.getFecha() + "\n";
                                }
                            }
                        }
                        datosError.put("mensaje", m);
                        session.setAttribute("hid", hid);
                        return new ModelAndView(this.getSuccessView(), datosError);
                    }
                    session.setAttribute("SESSION", "false");
                    session.invalidate();
                    datosError.put("mensaje", "Maquina incorrecta");
                    return new ModelAndView(this.getFormView(), datosError);
                }
                session.setAttribute("SESSION", "false");
                session.invalidate();
                datosError.put("mensaje", "Usuario deshabilitado");
                return new ModelAndView(this.getFormView(), datosError);
            }
        } else {
            sql = "from TCuentaAcceso where login = '" + cuenta.getLogin() + "'  and estado='ACTIVO'";
            result = dao.findAll(sql);
            if (result.size() > 0) {
                session.setAttribute("SESSION", "false");
                session.invalidate();
                datosError.put("mensaje", "Password incorrecto.");
                dao.cerrarSession();
                return new ModelAndView(this.getFormView(), datosError);
            }
        }
        session.setAttribute("SESSION", "false");
        session.invalidate();
        datosError.put("mensaje", "Ingrese un Usuario y Password correcto.");
        dao.cerrarSession();
        return new ModelAndView(this.getFormView(), datosError);
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

    public static boolean ip_acceso(String cadena, String ip) {
        boolean correcto = false;
        String[] array = cadena.split(",");
        for (int i = 0; i < array.length; i++) {
            if (ip.equals(array[i]) || array[i].equals("*")) {
                correcto = true;
            }
        }
        return correcto;
    }

    private String fecha() {
        SimpleDateFormat formateador = new SimpleDateFormat(
                "'AYACUCHO' dd 'de' MMMM 'del' yyyy", new Locale("ES"));
        Date fechaDate = new Date();
        String fecha = formateador.format(fechaDate);
        return fecha.toUpperCase();
    }
}
