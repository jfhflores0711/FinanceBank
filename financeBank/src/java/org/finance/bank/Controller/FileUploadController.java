package org.finance.bank.Controller;

/**
 *
 * @author oscar
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.finance.bank.util.Constants;
import org.springframework.validation.BindException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller class to upload Files.
 *
 */
public class FileUploadController extends BaseFormController {

    public FileUploadController() {
        setCommandName("fileUpload");
        setCommandClass(FileUpload.class);
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
            HttpServletResponse response, Object command,
            BindException errors)
            throws Exception {
        FileUpload fileUpload = (FileUpload) command;
        if (fileUpload.getFile().length == 0) {
            Object[] args = new Object[]{getText("uploadForm.file", request.getLocale())};
            errors.rejectValue("file", "errors.required", args, "File");
            return showForm(request, response, errors);
        }
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");
        String uploadDir = getServletContext().getRealPath("/resources") + "/" + request.getRemoteUser() + "/";
        File dirPath = new File(uploadDir);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
        InputStream stream = file.getInputStream();
        OutputStream bos = new FileOutputStream(uploadDir + file.getOriginalFilename());
        int bytesRead;
        byte[] buffer = new byte[8192];
        while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
            bos.write(buffer, 0, bytesRead);
        }
        bos.close();
        stream.close();
        request.setAttribute("friendlyName", fileUpload.getName());
        request.setAttribute("fileName", file.getOriginalFilename());
        request.setAttribute("contentType", file.getContentType());
        request.setAttribute("size", file.getSize() + " bytes");
        request.setAttribute("location", dirPath.getAbsolutePath() + Constants.FILE_SEP + file.getOriginalFilename());
        String link = request.getContextPath() + "/resources" + "/" + request.getRemoteUser() + "/";
        request.setAttribute("link", link + file.getOriginalFilename());
        return new ModelAndView(getSuccessView());
    }
}
