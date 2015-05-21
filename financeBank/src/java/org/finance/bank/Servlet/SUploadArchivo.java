/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.finance.bank.Servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.finance.bank.bean.TDenominacionMoneda;
import org.finance.bank.model.dao.DAOGeneral;

/**
 *
 * @author ubuntu
 */
public class SUploadArchivo extends HttpServlet {

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
        out.println("<br>");
        out.println("<input id='cmdregresar' type='submit' value='VOLVER'>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }

    public boolean procesaFicheros(HttpServletRequest req, PrintWriter out) {
        try {
            HttpSession session = req.getSession(true);
            String iddenominacion = (String) session.getAttribute("iddenominacionmoneda");
            DiskFileUpload fu = new DiskFileUpload();
            fu.setSizeMax(1024 * 512); // 512 K
            fu.setSizeThreshold(8192);
            fu.setRepositoryPath("/tmp");
            List fileItems = fu.parseRequest(req);
            if (fileItems == null) {
                return false;
            }
            out.print("<br> NUMERO DE IMAGENES SUBIDOS : " + fileItems.size());
            if (fileItems.size() > 512) {
                throw new Exception("Error en el tamaño del archivo");
            }
            Iterator i = fileItems.iterator();
            FileItem actual = null;
            DAOGeneral dao = new DAOGeneral();
            TDenominacionMoneda dep = (TDenominacionMoneda) dao.load(TDenominacionMoneda.class, iddenominacion);
            String foto = (String) session.getAttribute("mfoto");
            String lasturlfoto = "";
            if (foto.length() >= 1) {
                lasturlfoto = "billetesmonedas/foto";
            }
            int k = 0;
            while (i.hasNext()) {
                k = k + 1;
                actual = (FileItem) i.next();
                String fileName = actual.getName();
                File fichero = new File(fileName);
                if (k == 1 && foto.length() >= 1) {
                    out.println("<br><br>NOMBRE DE LA IMAGEN : " + k + " ES   : " + fileName);
                    if (!(fichero.getName().endsWith(".jpg") || fichero.getName().endsWith(".jpeg") || fichero.getName().endsWith(".png") || fichero.getName().endsWith(".gif") || fichero.getName().endsWith(".bmp"))) {
                        throw new Exception("El archivo " + k + " no es una imagen");
                    }
                    String lasturlfotof = lasturlfoto + foto;
                    fichero = new File(req.getRealPath("/") + "/" + lasturlfotof);
                    dep.setImagen(lasturlfotof);
                    out.println("<img src='" + lasturlfotof + "'>");
                    actual.write(fichero);
                }
            }
            dao.update();
        } catch (Exception e) {
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
