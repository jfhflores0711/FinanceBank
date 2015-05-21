package org.finance.bank.Controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.finance.bank.util.Constants;
import org.finance.bank.util.DateUtil;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author roger
 */
public class PruebaController extends BaseFormController {

    public PruebaController() {
    }

    @Override
    public ModelAndView processFormSubmission(HttpServletRequest request,
            HttpServletResponse response,
            Object command,
            BindException errors)
            throws Exception {
        if (request.getParameter("cancel") != null) {
            return new ModelAndView(getCancelView());
        }
        return super.processFormSubmission(request, response, command, errors);
    }

    @Override
    public ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response,
            Object command,
            BindException errors)
            throws Exception {
        String fileDir = getServletContext().getRealPath("/resources") + "/" + request.getRemoteUser() + "/";
        File dirPath = new File(fileDir);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
        InputStream stream = null;
        String f = DateUtil.getDateTimePattern().replace("/", "").replace(":", "");
        OutputStream bos = new FileOutputStream(fileDir + f);
        int bytesRead;
        byte[] buffer = new byte[8192];
        while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
            bos.write(buffer, 0, bytesRead);
        }
        bos.close();
        stream.close();
        request.setAttribute("location", dirPath.getPath() + Constants.FILE_SEP + f);
        String link = request.getContextPath() + "/resources" + "/" + request.getRemoteUser() + "/";
        request.setAttribute("link", link + f);
        return new ModelAndView(getSuccessView());
    }
}
