package org.finance.bank.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.finance.bank.bean.TPersona;
import org.finance.bank.model.service.GenericManager;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 *
 * @author oscar
 */
public class TPersonaController implements Controller {

    private final Log log = LogFactory.getLog(TPersona.class);
    private GenericManager<TPersona, String> personaManager = null;

    public void setPrestamoManager(GenericManager<TPersona, String> prestamoManager) {
        this.personaManager = prestamoManager;
    }

    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {
        log.debug("entering 'handleRequest' method...");
        return new ModelAndView().addObject(personaManager.getAll());
    }
}
