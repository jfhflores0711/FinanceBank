package org.finance.bank.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.finance.bank.bean.TRegistroPrestamo;
import org.finance.bank.model.service.GenericManager;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 *
 * @author oscar
 */
public class PrestamoController implements Controller {

    private final Log log = LogFactory.getLog(PrestamoController.class);
    private GenericManager<TRegistroPrestamo, String> prestamoManager = null;

    public void setPrestamoManager(GenericManager<TRegistroPrestamo, String> prestamoManager) {
        this.prestamoManager = prestamoManager;
    }

    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {
        log.debug("entering 'handleRequest' method...");
        return new ModelAndView().addObject(prestamoManager.getAll());
    }
}
