package org.finance.bank.Controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author roger
 */
public class nController implements Controller {

    private String successView;
    private String formView;
    private String greeting;
    private String nueva;
    private String mensaje;

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Logger.getLogger(nController.class.getName()).log(Level.INFO, "******** nController ***************");
        response.setContentType("text/html");
        request.getSession(true);
        return new ModelAndView(getSuccessView());
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

    /**
     * @return the greeting
     */
    public String getGreeting() {
        return greeting;
    }

    /**
     * @param greeting the greeting to set
     */
    public void setGreeting(String greeting) {
        this.greeting = greeting;
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
}
