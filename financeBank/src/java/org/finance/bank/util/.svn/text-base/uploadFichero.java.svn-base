package org.finance.bank.util;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.fileupload.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.finance.bank.bean.TPersona;
import org.finance.bank.model.dao.DAOGeneral;

/**
 *
 * @author roger
 */
public class uploadFichero extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<form id='frmfichero' method='POST' action='menu.htm'>");
        procesaFicheros(request, out);
        out.println("<br>");
        out.println("<input id='cmdregresar' type='button' value='Aceptar' onclick='window.history.back();'>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }

    void depura(String cadena) {
        Logger.getLogger(uploadFichero.class.getName()).log(Level.INFO, " " + cadena);
    }

    public boolean procesaFicheros(HttpServletRequest req, PrintWriter out) {
        try {
            DAOGeneral d = new DAOGeneral();
            HttpSession session = req.getSession(true);
            String id_user_PK = (String) session.getAttribute("id_user_PK");
            if (id_user_PK == null) {
                return false;
            }
            DiskFileUpload fu = new DiskFileUpload();
            fu.setSizeMax(1024 * 512); // 512 K
            depura("Ponemos el tamaño máximo");
            fu.setSizeThreshold(8192);
            fu.setRepositoryPath("/tmp");
            List fileItems = fu.parseRequest(req);
            if (fileItems == null) {
                depura("La lista es nula");
                return false;
            }
            out.print("<br>El número de ficheros subidos es: " + fileItems.size());
            if (fileItems.size() > 512) {
                throw new Exception("Error en el tamaño del archivo");
            }
            Iterator i = fileItems.iterator();
            FileItem actual = null;
            depura("estamos en la iteración");
            int k = 0;
            TPersona persona = (TPersona) d.findAll("from TPersona pe where pe.idUserPk='" + id_user_PK + "'").get(0);
            TPersona dep = (TPersona) d.load(TPersona.class, id_user_PK);
            String foto = (String) session.getAttribute("mfoto");
            String firma = (String) session.getAttribute("mfirma");
            String lasturlfoto = "";
            String lasturlfirma = "";
            if (foto.length() >= 1) {
                lasturlfoto = "fotos/foto";
            }
            if (firma.length() >= 1) {
                lasturlfirma = "firmas/firma";
            }
            while (i.hasNext()) {
                k = k + 1;
                actual = (FileItem) i.next();
                String fileName = actual.getName();
                File fichero = new File(fileName);
                depura("El nombre del fichero " + k + " es " + fichero.getName());
                String url = "";
                if (k == 1 && foto.length() >= 1) {
                    out.println("<br>se Subio el fichero " + k + " " + fileName);
                    if (!(fichero.getName().endsWith(".jpg") || fichero.getName().endsWith(".jpeg") || fichero.getName().endsWith(".png") || fichero.getName().endsWith(".gif") || fichero.getName().endsWith(".bmp"))) {
                        throw new Exception("El archivo " + k + " no es una imagen");
                    }
                    String dataPersona = lasturlfoto + persona.getDocIdentidad();
                    String nuevourl = dataPersona + fileName.substring((fileName).indexOf("."), fileName.length());
                    url = nuevourl;
                    fichero = new File(req.getRealPath("/") + "/" + url);
                    dep.setUrlFoto(url);
                    out.println("<img src='" + url + "' width='200px'>");
                    actual.write(fichero);
                }
                if (k == 2 && firma.length() >= 1) {
                    out.println("<br>se Subio el fichero " + k + " " + fileName);
                    if (!(fichero.getName().endsWith(".jpg") || fichero.getName().endsWith(".jpeg") || fichero.getName().endsWith(".png") || fichero.getName().endsWith(".gif") || fichero.getName().endsWith(".bmp"))) {
                        throw new Exception("El archivo " + k + " no es una imagen");
                    }
                    String dataPersona = lasturlfirma + persona.getDocIdentidad();
                    String nuevourl = dataPersona + fileName.substring((fileName).indexOf("."), fileName.length());
                    url = nuevourl;
                    fichero = new File(req.getRealPath("/") + "/" + url);
                    dep.setUrlFirma(url);
                    out.println("<img src='" + url + "' width='200px'>");
                    actual.write(fichero);
                }
            }
            d.cerrarSession();
        } catch (Exception e) {
            depura("Error de Aplicación " + e.getMessage());
            out.println("<h1>Error al subir el archivo</h1><p>" + e.getMessage() + "</p>");
            if ("the request was rejected".endsWith(e.getMessage())) {
                out.println("<h1>el archivo es muy grandde, no cabe</h1>");
            }
            return false;
        }
        return true;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
