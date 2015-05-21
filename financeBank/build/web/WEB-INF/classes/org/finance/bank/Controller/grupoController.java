package org.finance.bank.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.finance.bank.bean.TControlModulo;
import org.finance.bank.bean.TModulo;
import org.finance.bank.bean.TTipoPersona;
import org.finance.bank.model.dao.DAOGeneral;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class grupoController implements Controller {

    private String formView;
    private String successView;

    protected ModelAndView onSubmit(Object command) throws Exception {
        return new ModelAndView(this.getFormView(), "mensaje", "Ingrese un usuario y password correcto.");
    }

    @SuppressWarnings("static-access")
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DAOGeneral dao = new DAOGeneral();
        if (request.getParameter("grupo") != null && !request.getParameter("grupo").toString().equals("")) {
            String descripcionGrupo = request.getParameter("grupo");
            TTipoPersona grupo = new TTipoPersona();
            grupo.setDescripcion(descripcionGrupo.toUpperCase());
            grupo.setIdtipopersona(generateId());
            grupo.setIdUser("ID");
            grupo.setIpUser("IP");
            grupo.setDateUser("DATE");
            dao.persist(grupo);
            List result = dao.findAll("from TModulo");
            Iterator it = result.iterator();
            while (it.hasNext()) {
                TModulo modulo = (TModulo) it.next();
                TControlModulo grupo_modulo = new TControlModulo();
                grupo_modulo.setEstado("0");
                grupo_modulo.setIdcontrolmodulo(generateId());
                grupo_modulo.setTModulo(modulo);
                grupo_modulo.setTTipoPersona(grupo);
                grupo_modulo.setIdUser("ID");
                grupo_modulo.setIpUser("IP");
                grupo_modulo.setDateUser("DATE");
                dao.persist(grupo_modulo);
            }
        }
        return new ModelAndView(this.getSuccessView());
    }

    private static String generateId() {
        java.util.Date dt = new java.util.Date();
        Date dt2 = new java.util.Date();
        String ms = dt2.getTime() + "";
        ms = ms.substring(7, 13);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
        String id = sdf2.format(dt) + ms;
        return id;
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
