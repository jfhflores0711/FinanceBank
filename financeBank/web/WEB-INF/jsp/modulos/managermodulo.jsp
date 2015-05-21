<%--
    Document   : newjsp
    Created on : 11/02/2010, 04:41:24 PM
    Author     : ronald
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8" />
        <link rel="shortcut icon" href="./images/favicon.ico" />
        <title>FinanceBank</title>
        <meta name="keywords" content="FinanceBank" />
        <meta name="description" content="Sistema financiero" />
        <link href="default.css" rel="stylesheet" type="text/css" />
        <link href='css/tabla.css' rel='stylesheet' type='text/css' />
        <script type="text/javascript" src="js/scriptshow/scriptMModulo.js"></script>
    </head>
    <body>
        <%@include file="../common/header.jsp" %>
        <%
                    request.setAttribute("idmenu", "20110228202911543603");
        %>
        <div id="content">
            <div id="sidebar">
                <div id="menu">
                    <%@include file="../common/menu.jsp" %>
                </div>
                <div id="login" class="boxed">
                    <h2 class="title">Client Account</h2>
                    <div class="content">
                        <form id="form1" method="post" action="principal.htm">
                            <fieldset>
                                <input id="inputsubmit1" type="submit" name="inputsubmit1" value="Salir" />
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
            <div id="main">
                <div id="welcome" class="post">
                    <h2 class="title">GRUPOS / MODULOS</h2>
                    <div class="story">
                        <table border="0" width="720px">
                            <tr>
                                <td width="300px" valign="top">
                                    <fieldset>
                                        <legend><b>GRUPOS</b></legend>
                                        <table width="100%">
                                            <tr>
                                                <td>
                                                    <div id="menu" style="overflow-y:auto; height:200px" >
                                                        <ul>
                                                            <%
                                                                        List result = d.findAll("from TTipoPersona");
                                                                        Iterator it = result.iterator();
                                                                        int i = 0;
                                                                        while (it.hasNext()) {
                                                                            TTipoPersona grupo = (TTipoPersona) it.next();

                                                                            out.println("<li id='li" + i + "'><a id='a" + i + "'  onclick=\"select('" + i + "');funcion('" + grupo.getIdtipopersona() + "')\" >" + grupo.getDescripcion() + "</a></li>");
                                                                            i++;
                                                                        }
                                                            %>

                                                        </ul>
                                                    </div>
                                                </td>
                                            </tr>
                                        </table>
                                        <br>
                                    </fieldset>
                                </td>
                                <td>
                                    <input id="asignar" type="button" name="asignar" value="ASIGNAR"  onclick="checkModulo();alert('¡ASIGNACION EXITOSA!')" />
                                </td>
                                <td width="340px" valign="top">
                                    <fieldset>
                                        <legend><b>MODULOS</b></legend>
                                        <table width="100%">
                                            <tr>
                                                <td>
                                                    <div id="menu" style="overflow-y:auto; height:200px;" >
                                                        <form id="fNodo" name="fNodo" action="">
                                                            <div id="Nodo"></div>
                                                        </form>
                                                    </div>
                                                </td>
                                            </tr>
                                        </table>
                                        <br>
                                    </fieldset>
                                </td>
                            </tr>
                        </table>
                        <br><br>
                        <form id="form1" method="post" action="addGrupo.htm">
                            <table  width="90%" cellpadding='5' cellspacing='5' border="0" class="tabla" style='border-bottom:1px solid #000;border-left:1px solid #000;border-right:1px solid #000;border-top:1px solid #000;'>
                                <tr>
                                    <td align="right" width="20%">
                                        NUEVO GRUPO :
                                    </td>
                                    <td align="left">
                                        <input id="grupo" type="text" name="grupo" value="" />
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <input id="agregar" type="submit" name="agregar" value="AGREGAR" />
                                    </td>
                                </tr>
                            </table>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="../common/footer.jsp" %>
    </body>
</html>
