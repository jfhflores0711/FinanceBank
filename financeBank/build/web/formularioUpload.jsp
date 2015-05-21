<%-- 
    Document   : formularioUpload
    Created on : 15/03/2010, 08:28:40 AM
    Author     : roger
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD><link rel="shortcut icon" href="./images/favicon.ico" />
<TITLE></TITLE>
</HEAD>
<BODY>
<center>
<form method="POST" enctype='multipart/form-data' action="/servlet/uploadFichero">
Por favor, seleccione el trayecto del fichero a cargar
<br><input type="file" name="fichero">
<input type="submit">
</form>
</center>
</BODY>
</HTML>
