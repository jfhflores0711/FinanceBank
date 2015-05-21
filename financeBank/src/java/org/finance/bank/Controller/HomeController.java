package org.finance.bank.Controller;

/**
 *
 * @author ronald
 */
import java.util.Calendar;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeController implements Controller {

    private String greeting;

    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {
        String s = greeting
                + ": the date and time is now "
                + Calendar.getInstance().getTime();
        return new ModelAndView("home", "message", s);
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }
}

