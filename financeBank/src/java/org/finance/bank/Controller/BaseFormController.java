package org.finance.bank.Controller;

/**
 *
 * @author oscar
 */
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.finance.bank.bean.TCuentaAcceso;
import org.finance.bank.util.Constants;
import org.finance.bank.model.service.MailEngine;
import org.finance.bank.model.service.UserManager;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 * Implementation of <strong>SimpleFormController</strong> that contains
 * convenience methods for subclasses.  For example, getting the current
 * user and saving messages/errors. This class is intended to
 * be a base class for all Form controllers.
 *
 */
public class BaseFormController extends SimpleFormController {

    protected final transient Log a = LogFactory.getLog(getClass());
    public static final String MESSAGES_KEY = "successMessages";
    private UserManager c = null;
    protected MailEngine mailEngine = null;
    protected SimpleMailMessage message = null;
    protected String templateName = null;
    protected String cancelView;

    public void setUserManager(UserManager userManager) {
        this.c = userManager;
    }

    public UserManager getUserManager() {
        return this.c;
    }

    @SuppressWarnings("unchecked")
    public void saveError(HttpServletRequest request, String error) {
        List errors = (List) request.getSession().getAttribute("errors");
        if (errors == null) {
            errors = new ArrayList();
        }
        errors.add(error);
        request.getSession().setAttribute("errors", errors);
    }

    @SuppressWarnings("unchecked")
    public void saveMessage(HttpServletRequest request, String msg) {
        List messages = (List) request.getSession().getAttribute(MESSAGES_KEY);
        if (messages == null) {
            messages = new ArrayList();
        }
        messages.add(msg);
        request.getSession().setAttribute(MESSAGES_KEY, messages);
    }

    /**
     * Convenience method for getting a i18n key's value.  Calling
     * getMessageSourceAccessor() is used because the RequestContext variable
     * is not set in unit tests b/c there's no DispatchServlet Request.
     *
     * @param msgKey
     * @param locale the current locale
     * @return
     */
    public String getText(String msgKey, Locale locale) {
        return getMessageSourceAccessor().getMessage(msgKey, locale);
    }

    /**
     * Convenient method for getting a i18n key's value with a single
     * string argument.
     *
     * @param msgKey
     * @param arg
     * @param locale the current locale
     * @return
     */
    public String getText(String msgKey, String arg, Locale locale) {
        return getText(msgKey, new Object[]{arg}, locale);
    }

    /**
     * Convenience method for getting a i18n key's value with arguments.
     *
     * @param msgKey
     * @param args
     * @param locale the current locale
     * @return
     */
    public String getText(String msgKey, Object[] args, Locale locale) {
        return getMessageSourceAccessor().getMessage(msgKey, args, locale);
    }

    /**
     * Convenience method to get the Configuration HashMap
     * from the servlet context.
     *
     * @return the user's populated form from the session
     */
    public Map getConfiguration() {
        Map config = (HashMap) getServletContext().getAttribute(Constants.CONFIG);
        if (config == null) {
            return new HashMap();
        }
        return config;
    }

    /**
     * Set up a custom property editor for converting form inputs to real objects
     */
    @Override
    protected void initBinder(HttpServletRequest request,
            ServletRequestDataBinder binder) {
        binder.registerCustomEditor(Integer.class, null,
                new CustomNumberEditor(Integer.class, null, true));
        binder.registerCustomEditor(Long.class, null,
                new CustomNumberEditor(Long.class, null, true));
        binder.registerCustomEditor(byte[].class,
                new ByteArrayMultipartFileEditor());
        SimpleDateFormat dateFormat =
                new SimpleDateFormat(getText("date.format", request.getLocale()));
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null,
                new CustomDateEditor(dateFormat, true));
    }

    /**
     * Convenience message to send messages to users, includes app URL as footer.
     * @param user
     * @param msg
     * @param url
     */
    protected void sendUserMessage(TCuentaAcceso user, String msg, String url) {
        if (a.isDebugEnabled()) {
            a.debug("sending e-mail to user [" + user.getTPersona().getEmail() + "]...");
        }
        message.setTo(user.getTPersona().getNombre() + "<" + user.getTPersona().getEmail() + ">");
        Map<String, Serializable> model = new HashMap<String, Serializable>();
        model.put("user", user);
        model.put("message", msg);
        model.put("applicationURL", url);
        mailEngine.sendMessage(message, templateName, model);
    }

    public void setMailEngine(MailEngine mailEngine) {
        this.mailEngine = mailEngine;
    }

    public void setMessage(SimpleMailMessage message) {
        this.message = message;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    /**
     * Indicates what view to use when the cancel button has been pressed.
     */
    public final void setCancelView(String cancelView) {
        this.cancelView = cancelView;
    }

    public final String getCancelView() {
        if (this.cancelView == null || this.cancelView.length() == 0) {
            return getSuccessView();
        }
        return this.cancelView;
    }
}
