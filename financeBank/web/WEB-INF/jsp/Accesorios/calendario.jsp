<%-- 
    Document   : calendario
    Created on : 12/02/2010, 11:19:49 AM
    Author     : ubuntu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <%@page import="java.util.*" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script type="text/javascript" language='javascript' src="<%=request.getContextPath() %>/js/calendar/popcalendar.js"></script>
    </head>
    <body>
          <center>
                 <form name="form1" method="post" action="">
           Fecha: <input name="nombre_de_la_caja" type="text" id="dateArrival" onClick="popUpCalendar(this, form1.dateArrival, 'dd/mm/yyyy');" size="10">
           (Click para escoger en el calendario)
        </form>
		  </center>
    <span><%=new Date() %></span>
    </body>
</html>
