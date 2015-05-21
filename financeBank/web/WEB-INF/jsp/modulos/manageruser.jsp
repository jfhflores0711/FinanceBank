<%--
 Document : newjsp
 Created on : 11/02/2010, 04:41:24 PM
 Author : ronald
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
        <script type="text/javascript" src="js/scriptshow/scriptMUser.js"></script>
        <script type="text/javascript" src="js/validacion.js">
        </script>
        <style type="text/css">
            #content {width: 1020px;}
            #main {width: 750px;}
        </style>
    </head>
    <body onload="document.getElementById('xli').focus();">
        <%
                    request.setAttribute("idmenu", "20110228202911478860");
                    String TBuscar = "sistema";
                    if (request.getParameter("buscar") != null) {
                        TBuscar = request.getParameter("buscar").toString();
                    }
                    String dni = "";
                    String ruc = "";
                    String nombres = "";
                    String apellidos = "";
                    String email = "";
                    String ubigeo = "050101";
                    String telefono = "";
                    String celular = "";
                    String direccion = "";
        %>
        <%@include file="../common/header.jsp" %>
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
                    <h2 class="title">ADMINISTRACIÓN DE USUARIOS</h2><div class="story">
                        <form id="f1" name="f1" method="post" action="">
                            <table width="100%" cellpadding="5" cellspacing="5" border="0" class="tabla" style="border-bottom:1px solid #000;border-left:1px solid #000;border-right:1px solid #000;border-top:1px solid #000;">
                                <tr>
                                    <td width="50%" >
                                        <font color="#385B88" style="font-size:14px"><b>Usuario de Sistema&nbsp;</b></font>
                                        <input id="xli" type="radio" name="buscar" value="sistema"
                                               <% if (TBuscar.equals("sistema")) {
                                                               out.println("checked");
                                                           }%>
                                               onclick="document.f1.submit();" />&nbsp;&nbsp;
                                        <font color="#385B88" style="font-size:14px"><b>Todos&nbsp;</b></font>
                                        <input type="radio" name="buscar" value="todo"
                                               <% if (TBuscar.equals("todo")) {
                                                               out.println("checked");
                                                           }%>
                                               onclick="document.f1.submit();"/>
                                    </td>
                                </tr>
                            </table>
                        </form>
                        <table border="0" width="100%">
                            <tr>
                                <td width="45%" valign="top">
                                    <fieldset>
                                        <legend><b>LISTA PERSONAS</b></legend>
                                        <table width="100%">
                                            <tr>
                                                <td>
                                                    <div id="menu" style="overflow-y:auto; height:500px" >
                                                        <ul>
                                                            <%
                                                                        List result = null;
                                                                        if (TBuscar.equals("todo")) {
                                                                            int ix = 0;
                                                                            Set h = new HashSet();
                                                                            h.add("p.TCategoriaPersona.descripcion='NATURAL' order by p.apellidos, p.nombre");
                                                                            h.add("p.TCategoriaPersona.descripcion='JURIDICA' order by p.nombre");
                                                                            for (Iterator k = h.iterator(); k.hasNext();) {
                                                                                String i = (String) k.next();
                                                                                List j = d.findAll("from TPersona p where p.estado='ACTIVO' and " + i);
                                                                                for (Iterator l = j.iterator(); l.hasNext();) {
                                                                                    TPersona m = (TPersona) l.next();
                                                                                    out.println("<li id='li" + i + "' value='" + m.getIdUserPk() + "' ><a id='a" + ix + "' onclick=\"select('" + ix + "');funcion('" + m.getIdUserPk() + "');\" >" + ("NATURAL".equals(m.getTCategoriaPersona().getDescripcion()) ? "" + m.getApellidos() + ", " + m.getNombre() : "" + m.getNombre()) + "</a></li>");
                                                                                    ix++;
                                                                                }
                                                                            }
                                                                        } else {
                                                                            String sql = "from TCuentaAcceso ca where ca.estado='ACTIVO' and not ( ca.login ='DISABLED') order by ca.idAcceso";
                                                                            result = d.findAll(sql);
                                                                            Iterator ite = result.iterator();
                                                                            int i = 0;
                                                                            while (ite.hasNext()) {
                                                                                TCuentaAcceso cuenta = (TCuentaAcceso) ite.next();
                                                                                TPersona persona = (TPersona) d.load(TPersona.class, cuenta.getTPersona().getIdUserPk());
                                                                                out.println("<li id='li" + i + "' value='" + persona.getIdUserPk() + "' ><a id='a" + i + "' onclick=\"select('" + i + "','" + persona.getIdUserPk() + "');funcion('" + persona.getIdUserPk() + "');\" >" + persona.getNombre() + " " + persona.getApellidos() + "</a></li>");
                                                                                i++;
                                                                            }
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
                                <td width="10%" align="center">
                                    <input id="nuevo" type="button" name="nuevo" value="NUEVO" onclick="nuevo();" /><br>
                                    <input id="actualizar" type="button" name="actualizar" value="ACTUALIZAR" onclick="Actualizar();" disabled="true"/><br>
                                    <input id="guardar" type="button" name="guardar" value="GUARDAR" onclick="eg();" disabled="true"/><br>
                                </td>
                                <td width="45%" valign="top">
                                    <fieldset>
                                        <legend><b>DETALLE</b></legend>
                                        <center>
                                            <font color="red" style="font-size:10px">
                                                <b>
                                                    <c:forEach var="mensaje" items="${mensaje}">
                                                        <c:out value="${mensaje}" />
                                                    </c:forEach>
                                                </b>
                                            </font>
                                        </center>
                                        <table width="100%">
                                            <tr>
                                                <td>
                                                    <div id="menu" style="overflow-y:auto; height:500px" >
                                                        <form id="fDetalle" name="fDetalle" method="post" action="">
                                                            <div id="Detalle">
                                                                <table border="0" width="100%">
                                                                    <tr><td align="right">
                                                                            <font color="#385B88" style="font-size:12px"><b>Categoria&nbsp;</b></font>
                                                                        </td>
                                                                        <td align="left">
                                                                            <%
                                                                                        //sess = sessFact.openSession();
                                                                                        result = d.findAll("from TCategoriaPersona");
                                                                                        //query = query.list();
                                                                                        Iterator it5 = result.iterator();
                                                                                        int j = 0;
                                                                                        String check = "";
                                                                                        while (it5.hasNext()) {
                                                                                            if (j == 0) {
                                                                                                check = "checked";
                                                                                            }
                                                                                            TCategoriaPersona cate = (TCategoriaPersona) it5.next();
                                                                                            out.println(cate.getDescripcion() + "<input type='radio' name='categoria' value='" + cate.getIdcategoriapersona() + "' " + check + " />&nbsp;");
                                                                                            check = "";
                                                                                            j++;
                                                                                        }
                                                                            %>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td align="right">
                                                                            <font color="#385B88" style="font-size:12px"><b>D.N.I&nbsp;</b></font>
                                                                        </td>
                                                                        <td align="left">
                                                                            <input value="<%=dni%>" id="d" type="text" name="dni" value="PRESIONE NUEVO" disabled onkeypress="return soloNumeroTelefonico(event);" maxlength="8"/><font color="red">*</font>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td align="right">
                                                                            <font color="#385B88"><b>R.U.C&nbsp;</b></font>
                                                                        </td>
                                                                        <td align="left">
                                                                            <input value="<%=ruc%>" id="r" type="text" name="ruc" value="" disabled onkeypress="return soloNumeroTelefonico(event);" maxlength="11"/>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td align="right">
                                                                            <font color="#385B88" style="font-size:12px"><b>Nombres&nbsp;</b></font>
                                                                        </td>
                                                                        <td align="left">
                                                                            <input value="<%=nombres%>" id="n" type="text" name="nombres" disabled maxlength="50" /><font color="red">*</font>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td align="right">
                                                                            <font color="#385B88" style="font-size:12px"><b>Apellidos&nbsp;</b></font>
                                                                        </td>
                                                                        <td align="left">
                                                                            <input value="<%=apellidos%>" id="a" type="text" name="apellidos" disabled maxlength="50" /><font color="red">*</font>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td align="right">
                                                                            <font color="#385B88" style="font-size:12px"><b>Ubigeo&nbsp;</b></font>
                                                                        </td>
                                                                        <td align="left">
                                                                            <input id="u" type="text" name="ubigeo" value="<%=ubigeo%>" disabled onkeypress="return soloNumeroTelefonico(event);" maxlength="10"/><font color="red">*</font>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td align="right">
                                                                            <font color="#385B88" style="font-size:12px"><b>Direccion&nbsp;</b></font>
                                                                        </td>
                                                                        <td align="left">
                                                                            <input id="di" type="text" name="direccion" value="<%=direccion%>" disabled maxlength="100"/><font color="red">*</font>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td colspan="2" style="font-size: 8px;">
                                                                            <b>&nbsp; Datos Adicionales</b>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td align="right">
                                                                            <font color="#385B88" style="font-size:12px;"><b>Email&nbsp;</b></font>
                                                                        </td>
                                                                        <td align="left">
                                                                            <input id="e" type="text" name="email" value="<%=email%>" maxlength="50"/>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td align="right">
                                                                            <font color="#385B88" style="font-size:12px;"><b>Tel&eacute;fono&nbsp;</b></font>
                                                                        </td>
                                                                        <td align="left">
                                                                            <input id="t" type="text" name="telefono" value="<%=telefono%>" onkeypress="return soloNumeroTelefonico(event);" maxlength="10" />
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td align="right">
                                                                            <font color="#385B88" style="font-size:12px;"><b>Celular&nbsp;</b></font>
                                                                        </td>
                                                                        <td align="left">
                                                                            <input id="c" type="text" name="celular" value="<%=celular%>" onkeypress="return soloNumeroTelefonico(event);" maxlength="12" />
                                                                        </td>
                                                                    </tr>
                                                                </table>
                                                                <br><hr><br>
                                                                <table border="0" width="100%">
                                                                    <tr>
                                                                        <td align="right">
                                                                            <font color="#385B88" style="font-size:12px;"><b>Usuario&nbsp;</b></font>
                                                                        </td>
                                                                        <td align="left">
                                                                            <input id="l" type="text" disabled name="login" value="" maxlength="10" /><font color="red">*</font>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td align="right">
                                                                            <font color="#385B88" style="font-size:12px;"><b>Contrase&ntilde;a&nbsp;</b></font>

                                                                        </td>
                                                                        <td align="left">
                                                                            <input id="co" type="password" name="contrasenia" value="" disabled maxlength="10" /><font color="red">*</font>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td align="right">
                                                                            <font color="#385B88" style="font-size:12px;"><b>IP Acceso</b></font>
                                                                        </td>
                                                                        <td align="left">
                                                                            <input id="ip" type="text" name="ipacceso" disabled value="*" /><font color="red">*</font>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td align="right" colspan="2">
                                                                            <font color="#385B88" style="font-size:9px;"><b>Ejem: 192.168.0.XX,*,etc</b></font>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td colspan="2">
                                                                            <b>&nbsp;</b>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td align="right">
                                                                            <font color="#385B88" style="font-size:12px;"><b>Grupo&nbsp;</b></font>
                                                                        </td>
                                                                        <td align="left">
                                                                            <select name="grupo" id="gr" disabled>
                                                                                <%
                                                                                            result = d.findAll("from TTipoPersona");
                                                                                            Iterator it2 = result.iterator();
                                                                                            while (it2.hasNext()) {
                                                                                                TTipoPersona grupo = (TTipoPersona) it2.next();
                                                                                                out.println("<option value='" + grupo.getIdtipopersona() + "' >" + grupo.getDescripcion() + "</option>");
                                                                                            }
                                                                                %>
                                                                            </select>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td align="right">
                                                                            <font color="#385B88" style="font-size:12px"><b>Filial&nbsp;</b></font>
                                                                        </td>
                                                                        <td align="left">
                                                                            <select id="fi" name="filial" onchange="mostrarcaja();" disabled>
                                                                                <%
                                                                                            String codfilial = "";
                                                                                            result = d.findAll("from TFilial where estado='ACTIVO'");
                                                                                            Iterator it4 = result.iterator();
                                                                                            int ii = 0;
                                                                                            while (it4.hasNext()) {
                                                                                                TFilial fi = (TFilial) it4.next();
                                                                                                out.println("<option value='" + fi.getCodFilial() + "' >" + fi.getNombre() + "</option>");
                                                                                                if (ii == 0) {
                                                                                                    codfilial = fi.getCodFilial();
                                                                                                }
                                                                                                ii++;
                                                                                            }
                                                                                %>
                                                                            </select>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td align="right">
                                                                            <font color="#385B88" style="font-size:12px"><b>Caja&nbsp;</b></font>
                                                                        </td>
                                                                        <td align="left"><div id="fiCaja">
                                                                                <select id="selcaja" name="caja" disabled>
                                                                                    <%
                                                                                                result = d.findAll("from TCaja ca where ca.TFilial='" + codfilial + "' and ca.estado='ACTIVO' order by codCaja");
                                                                                                if (result.size() > 0) {
                                                                                                    Iterator it3 = result.iterator();
                                                                                                    while (it3.hasNext()) {
                                                                                                        TCaja caja3 = (TCaja) it3.next();
                                                                                                        out.println("<option value='" + caja3.getCodCaja() + "' >" + caja3.getNombreCaja() + "</option>");
                                                                                                    }
                                                                                                } else {
                                                                                                    out.println("<option value='0'>(SIN ASIGNAR CAJA)</option>");
                                                                                                }
                                                                                    %>
                                                                                </select></div>
                                                                        </td>
                                                                    </tr>
                                                                </table>
                                                            </div>
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
                    </div><div style="visibility: hidden;" id="re"></div>
                </div>
            </div>
        </div>
        <%@include file="../common/footer.jsp" %>
    </body>
</html>
