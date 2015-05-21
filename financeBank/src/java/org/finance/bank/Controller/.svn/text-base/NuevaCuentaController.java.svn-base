package org.finance.bank.Controller;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.finance.bank.bean.TCuentaPersona;
import org.springframework.web.servlet.mvc.Controller;

/**
 *
 * @author roger
 */
public class NuevaCuentaController implements Controller {

    private String successView;
    private String formView;
    private String greeting;
    private String nueva;
    private String mensaje;

    protected Object formBackinObject(ServletRequest request) throws ServletException {
        TCuentaPersona cuentaP = new TCuentaPersona();
        return cuentaP;
    }

    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {
        response.setContentType("text/html");
        return new ModelAndView(getGreeting());
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @return the nueva
     */
    public String getNueva() {
        return nueva;
    }

    /**
     * @param nueva the nueva to set
     */
    public void setNueva(String nueva) {
        this.nueva = nueva;
    }

    /**
     * @return the greeting
     */
    public String getGreeting() {
        return greeting;
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
}
