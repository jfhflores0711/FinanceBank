<%--
    Document   : newjsp
    Created on : 11/02/2010, 04:41:24 PM
    Author     : roger
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8" />
        <link rel="shortcut icon" href="./images/favicon.ico" />
        <title>FinanceBank - Nueva Cuenta</title>
        <meta name="keywords" content="FinanceBank" />
        <meta name="description" content="Sistema financiero" />
        <link href="default.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="js/validacion.js">
        </script>
        <script type="text/javascript" src="js/scriptMath.js"></script>
        <script type="text/javascript" src="js/scriptConvertTest.js"></script>
        <script type="text/javascript" src="js/scriptshow/scriptNuevaCuenta.js"></script>
        <script type="text/javascript" src="js/calendar/popcalendar.js">
        </script>
    </head>
    <body onload="document.getElementById('txtcategoria').focus();">
        <%@include file="../common/header.jsp" %>
        <%
                    request.setAttribute("idmenu", "20110228202911514866");
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
                    <h2 class="title">Modulo Nueva Cuenta!</h2>
                    <h3 class="date"><span class="month"><script type="text/javascript">
                        var fecha=new Date();
                        var fmtmonthnamesshort=new Array("Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre");
                        document.write(fmtmonthnamesshort[fecha.getMonth()]);
                            </script></span> <span class="day"><script type="text/javascript">
                                document.write(fecha.getDate());
                            </script></span><span class="year">, <script type="text/javascript">document.write(fecha.getFullYear());</script></span></h3>
                            <%
                                        String DNI = "";
                                        if (request.getParameter("txtDNI") != null) {
                                            DNI = request.getParameter("txtDNI");
                                        }
                                        String RUC = "";
                                        if (request.getParameter("txtRUC") != null) {
                                            RUC = request.getParameter("txtRUC");
                                        }
                                        String Nombre = "";
                                        if (request.getParameter("txtNombre") != null) {
                                            Nombre = request.getParameter("txtNombre");
                                        }
                                        String Apellidos = "";
                                        if (request.getParameter("txtApellidos") != null) {
                                            Apellidos = request.getParameter("txtApellidos");
                                        }
                                        String Email = "";
                                        if (request.getParameter("txtEmail") != null) {
                                            Email = request.getParameter("txtEmail");
                                        }
                                        String Ubigeo = "";
                                        if (request.getParameter("txtUbigeo") != null) {
                                            Ubigeo = request.getParameter("txtUbigeo");
                                        }
                                        String Telefono = "";
                                        if (request.getParameter("txtTelefono") != null) {
                                            Telefono = request.getParameter("txtTelefono");
                                        }
                                        String Celular = "";
                                        if (request.getParameter("txtCelular") != null) {
                                            Celular = request.getParameter("txtCelular");
                                        }
                                        String Direccion = "";
                                        if (request.getParameter("txtDireccion") != null) {
                                            Direccion = request.getParameter("txtDireccion");
                                        }
                                        String MontoInicial = "0.00";
                                        if (request.getParameter("txtMontoInicial") != null) {
                                            MontoInicial = request.getParameter("txtMontoInicial");
                                        }
                                        String Tasa = "0.00";
                                        if (request.getParameter("txtTasa") != null) {
                                            Tasa = request.getParameter("txtTasa");
                                        }
                                        String observacionn = "";
                                        if (request.getParameter("observacion") != null) {
                                            observacionn = request.getParameter("observacion");
                                        }
                                        String DNIapoderado = "";
                                        if (request.getParameter("txtDNIapoderado") != null) {
                                            DNIapoderado = request.getParameter("txtDNIapoderado");
                                        }
                                        String NombreApoderado = "";
                                        if (request.getParameter("txtNombreApoderado") != null) {
                                            NombreApoderado = request.getParameter("txtNombreApoderado");
                                        }
                                        String ApellidoApoderado = "";
                                        if (request.getParameter("txtApellidoApoderado") != null) {
                                            ApellidoApoderado = request.getParameter("txtApellidoApoderado");
                                        }
                                        String FechaNacApoderado = "";
                                        if (request.getParameter("txtFechaNacApoderado") != null) {
                                            FechaNacApoderado = request.getParameter("txtFechaNacApoderado");
                                        }
                                        /*String TiempoPF = "1";
                                        if (request.getParameter("txtTiempoPF") != null) {
                                        TiempoPF = request.getParameter("txtTiempoPF");
                                        }*/
                                        String banfichero = "";
                                        if (request.getParameter("bansubirfichero") == null) {
                                            banfichero = "0";
                                        } else {
                                            banfichero = "1";
                                        }
                                        if (banfichero.equals("1")) {
                                            String idper = (String) session.getAttribute("id_user_PK");
                                            TPersona pers = (TPersona) d.findAll("from TPersona pe where pe.idUserPk='" + idper + "'").get(0);
                            %>
                    <br>
                    <br>
                    <div id="divVerTicket">
                        <form id="frmiraticket" name="frmiraticket" method="post" action="ticket.htm">
                            <center>NUMERO DE CUENTA: <%=request.getParameter("numcuent")%><br><input id="txtVerTicket" name="txtVerTicket" type="button" value="Ticket" onclick="activarTicket();"></center>
                        </form>
                    </div>
                    <div id="divsubirfichero">
                        <form id="frmsubirfile" name="frmsubirfile" method="POST" action="uploadFichero" enctype='multipart/form-data'>
                            <fieldset   style="border-width:3px">
                                <legend><b>Si Dispone de una foto o firma digital del Usuario puede subirla:</b></legend>
                                <table>
                                    <tr>
                                        <td colspan="2">
                                        </td>
                                    </tr>
                                    <%
                                                                                if (!pers.getUrlFoto().equals(".")) {
                                    %>
                                    <tr>
                                        <td colspan="2">&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td colspan="2"><font color="red" style="font-size: 12px;"><b><%=pers.getNombre()%> <%=pers.getApellidos()%> ya posee FOTO</b></font></td>
                                    </tr>
                                    <%
                                                                                }
                                    %>
                                    <tr>
                                        <td><font color="#385b88" style="font-size: 12px;"><b>BUSCAR FOTO:</b></font></td>
                                        <td><input type="file" id="ExaminarFoto" name="ExaminarFoto" value="" maxlength="200" onfocus="enviarsession();" accept="image/jpg;image/jpeg;image/gif;image/png;image/bmp"/></td>
                                    </tr>
                                    <%
                                                                                if (!pers.getUrlFirma().equals(".")) {
                                    %>
                                    <tr>
                                        <td colspan="2">&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td colspan="2"><font color="red" style="font-size: 12px;"><b><%=pers.getNombre()%> <%=pers.getApellidos()%> ya posee FIRMA</b></font></td>
                                    </tr>
                                    <%}%>
                                    <tr>
                                        <td><font color="#385b88" style="font-size: 12px;"><b>BUSCAR FIRMA:</b></font></td>
                                        <td><input type="file" id="ExaminarFirma" name="ExaminarFirma" value="" onfocus="enviarsession();" maxlength="200"/></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            <input id="cmdsubir" type="submit" value="Subir" disabled <%--onclick="activarformfichero()"--%> >
                                            <input type="button" id="cmdLimpiar" value="Limpiar" onclick="limpiar();" disabled>
                                        </td>
                                    </tr>
                                </table>
                            </fieldset>
                        </form>
                    </div>
                    <%}%>
                    <div class="story">
                        <form id="formulario" name="formulario" action="SGenerarNuevaCuenta" method="post">
                            <table >
                                <tr>
                                    <td colspan="2"></td>
                                </tr>
                                <tr>
                                    <td>
                                        <div id="divtemporal" style="display:none"></div>
                                        <div id="divDatosPersonales">
                                            <fieldset   style="border-width:3px">
                                                <legend><b>DATOS PERSONALES</b></legend>
                                                <table id="tablaDatosPersonales" border="0">
                                                    <tr>
                                                        <td colspan="2" align="center"><input type="text" id="txtPersonaExiste" name="txtPersonaExiste" value="NO EXISTE" style="display:none"><input id="txtidpersona" type='hidden' value="" /></td>
                                                    </tr>
                                                    <tr>
                                                        <td><font color="#385B88" style="font-size:12px"><b>Categoria&nbsp;</b></font></td>
                                                        <td align='left'>
                                                            <%
                                                                        String er = "txt";
                                                                        //sess = sessFact.openSession();
                                                                        List result = d.findAll("from TCategoriaPersona");
                                                                        //Query query = query.list();
                                                                        Iterator it4 = result.iterator();
                                                                        int j = 0;
                                                                        String check = "onclick=\"juridicax('" + er + "')\"";
                                                                        while (it4.hasNext()) {
                                                                            if (j == 0) {
                                                                                check = "checked onclick=\"naturalx('" + er + "')\"";
                                                                            } else {
                                                                                check = "onclick=\"juridicax('" + er + "')\"";
                                                                            }
                                                                            TCategoriaPersona cate = (TCategoriaPersona) it4.next();
                                                                            out.println(cate.getDescripcion() + "<input type='radio' name='txtcategoria' id='txtcategoria' value='" + cate.getIdcategoriapersona() + "' " + check + "  />");
                                                                            j++;
                                                                        }
                                                            %>
                                                        </td>
                                                    </tr>
                                                    <tr id="txttr_dni">
                                                        <td><font color="#385b88" style="font-size: 12px;"><b>DNI N&deg;:</b></font></td>
                                                        <td><table><tr><td><input type="text" id="txtDNI" name="txtDNI" value="<%=DNI%>" onkeypress="return solonumeros(event);" maxlength="8" onblur="buscarcuenta(document.getElementById('txtDNI').value,'DNI');" /></td><td style="color: red">*</td></tr></table></td>
                                                    </tr>
                                                    <tr id="txttr_ruc">
                                                        <td><font color="#385b88" style="font-size: 12px;"><b>RUC:</b></font></td>
                                                        <td><input type="text" disabled="true" id="txtRUC" name="txtRUC" value="<%=RUC%>" onkeypress="return solonumeros(event);" maxlength="15" onblur="buscarcuenta(document.getElementById('txtRUC').value,'RUC');" /></td>
                                                    </tr>
                                                    <tr id="txttr_nombres">
                                                        <td align='left'><table><tr>
                                                                    <td align='left' id="txttd_nombres"> <font color="#385b88" style="font-size: 12px;"><b>Nombre:</b></font></td>
                                                                    <td align='left' id="txttd_razonsocial"  style='display:none'>
                                                                        <font color="#385B88" style="font-size:12px"><b>Razon Social:</b></font>
                                                                    </td></tr></table></td>
                                                        <td><input type="text" id="txtNombre" name="txtNombre" value="<%=Nombre%>" maxlength="60" /><font color='red'>*</font></td>
                                                    </tr>
                                                    <tr id="txttr_apellidos">
                                                        <td><font color="#385b88" style="font-size: 12px;"><b>Apellidos:</b></font></td>
                                                        <td><table><tr><td><input type="text" id="txtApellidos" name="txtApellidos" value="<%=Apellidos%>" maxlength="60"/></td><td style="color:red">*</td></tr></table></td>
                                                    </tr>
                                                    <tr id="txttr_email">
                                                        <td><font color="#385b88" style="font-size: 12px;"><b>E-mail:</b></font></td>
                                                        <td><input type="text" id="txtEmail" name="txtEmail" value="<%=Email%>" maxlength="50" /></td>
                                                    </tr>
                                                    <tr id="txttr_ubigeo">
                                                        <td><font color="#385b88" style="font-size: 12px;"><b>Ubigeo</b></font></td>
                                                        <td><input type="text" id="txtUbigeo" name="txtUbigeo" value="<%=Ubigeo%>" onkeypress="return solonumeros(event);" maxlength="6"/></td>
                                                    </tr>
                                                    <tr id="txttr_telefono">
                                                        <td><font color="#385b88" style="font-size: 12px;"><b>Telefono:</b></font></td>
                                                        <td><input type="text" id="txtTelefono" name="txtTelefono" value="<%=Telefono%>" onkeypress="return soloNumeroTelefonico(event);" maxlength="100"/></td>
                                                    </tr>
                                                    <tr id="txttr_celular">
                                                        <td><font color="#385b88" style="font-size: 12px;"><b>Celular:</b></font></td>
                                                        <td><input type="text" id="txtCelular" name="txtCelular" value="<%=Celular%>" maxlength="100"/></td>
                                                    </tr>
                                                    <tr id="txttr_direccion">
                                                        <td><font color="#385b88" style="font-size: 12px;"><b>Direccion:</b></font></td>
                                                        <td><input type="text" id="txtDireccion" name="txtDireccion" value="<%=Direccion%>" maxlength="100" /></td>
                                                    </tr>
                                                    <tr>
                                                        <td><input type="text" id="txtdatapersona" name="txtdatapersona" value="" maxlength="200" style="display:none"/></td>
                                                    </tr>
                                                </table>
                                            </fieldset>
                                        </div>
                                    </td>
                                    <td>
                                        <fieldset style="border-width:3px">
                                            <legend><b>DATOS DE LA CUENTA</b></legend>
                                            <table id="tablaCuentaPersona" border="0">
                                                <tr>
                                                    <td colspan="2">&nbsp;</td>
                                                </tr>
                                                <tr>
                                                    <td><font color="#385b88" style="font-size: 12px;"><b>Tipo de cuenta:</b></font></td>
                                                    <td >
                                                        <select id="selTipoCuenta" name="selTipoCuenta" onChange="mostrarDataAPF()">
                                                            <%
                                                                        List result2 = d.findAll("from TTipoCuenta");
                                                                        Iterator iter = result2.iterator();
                                                                        while (iter.hasNext()) {
                                                                            TTipoCuenta tipocuenta = (TTipoCuenta) iter.next();
                                                                            out.println("<option value='" + tipocuenta.getCodigoCuenta() + "'>" + tipocuenta.getDescripcion() + "</option>");
                                                                        }
                                                            %>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td><font color="#385b88" style="font-size: 12px;"><b>Moneda:</b></font></td>
                                                    <td>
                                                        <select id="lstMoneda" name="lstMoneda">
                                                            <%
                                                                        List result3 = d.findAll("from TMoneda where estado='ACTIVO'");
                                                                        Iterator itera = result3.iterator();
                                                                        while (itera.hasNext()) {
                                                                            TMoneda moneda = (TMoneda) itera.next();
                                                                            out.println("<option value='" + moneda.getCodMoneda() + "'>" + moneda.getNombre() + "</option>");
                                                                        }
                                                            %>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td><font color="#385b88" style="font-size: 12px;"><b>Monto Inicial:</b></font></td>
                                                    <td><input type="text" id="txtMontoInicial" name="txtMontoInicial" value="<%=MontoInicial%>" onfocus="this.select();" onblur="this.value=eedisplayFloatNDTh(eeparseFloatTh(this.value),2);" onkeypress="return validar(event);" maxlength="23"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td ><input type="button" value="Limpiar monto" onclick="document.getElementById('txtMontoInicial').value=''; document.getElementById('txtMontoInicial').focus();" ></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td><input type="checkbox" id="txtradius"  value="SIN INTERES" onclick="if(document.getElementById('txtradius').checked==true){document.getElementById('txtTasa').disabled=true; document.getElementById('txtTasa').value='0.00';} else{ document.getElementById('txtTasa').disabled=false;}" > Sin Tasa de Interes:</td>
                                                </tr>
                                                <tr>
                                                    <td><font color="#385b88" style="font-size: 12px;"><b>Tasa Inter&eacute;s</b></font></td>
                                                    <td><input type="text" id="txtTasa" name="txtTasa" onkeyup="tean();" value="<%=Tasa%>" maxlength="12" onfocus="this.select()" onkeypress="return validar(event);" onchange="range = /^\d{1,3}(\.\d{1,3})?$/;
                                                        hundred = /^1000$/;
                                                        if(!(range.test(this.value) || hundred.test(this.value))){alert('La tasa de interés es inválida.'); document.getElementById('txtTasa').focus();};"/><font color="#385b88" style="font-size: 12px;"><b>% ANUAL</b></font>
                                                        <br/>
                                                        EFECTIVO (TEA/TAE):<span id="tea">0.00</span>%
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td colspan="2"><div id="divTitulo"></div></td>
                                                </tr>
                                                <tr>
                                                    <td><div id="divNumDia"></div></td>
                                                    <td><div id="divNumeroDias"></div></td>
                                                </tr>
                                                <tr>
                                                    <td><div id="divTiempo"></div></td>
                                                    <td><div id="divTipoTiempo"></div></td>
                                                </tr>
                                                <tr>
                                                    <td><font color="#385b88" style="font-size: 12px;"><b>Observaciones:</b></font></td>
                                                    <td><textarea id="observacion" name="observacion" rows="2" cols="26" maxlength="500"><%=observacionn%></textarea></td>
                                                </tr>
                                                <tr>
                                                    <td>Int. mensual:</td>
                                                    <td><span id="im">0.00</span>% Mensual</td>
                                                </tr>
                                                <tr>
                                                    <td>Int. DIARIO: </td>
                                                    <td><span id="id">0.00</span>% DIARIO</td>
                                                </tr>
                                            </table>
                                        </fieldset>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <fieldset style="border-width:3px">
                                            <legend><b>EN CASO DE URGENCIA:</b></legend>
                                            <table border="0">
                                                <tr>
                                                    <td colspan="2">&nbsp;</td>
                                                </tr>
                                                <tr>
                                                    <td colspan="2"><b>APODERADO</b></td>
                                                </tr>
                                                <tr>
                                                    <td><font color="#385b88" style="font-size: 12px;"><b>DNI N&deg;</b></font></td>
                                                    <td><input type="text" id="txtDNIapoderado" name="txtDNIapoderado" value="<%=DNIapoderado%>" onkeypress="return solonumeros(event);"/></td>
                                                </tr>
                                                <tr>
                                                    <td><font color="#385b88" style="font-size: 12px;"><b>Nombre:</b></font></td>
                                                    <td><input type="text" id="txtNombreApoderado" name="txtNombreApoderado" value="<%=NombreApoderado%>" /></td>
                                                </tr>
                                                <tr>
                                                    <td><font color="#385b88" style="font-size: 12px;"><b>Apellido</b></font></td>
                                                    <td><input type="text" id="txtApellidoApoderado" name="txtApellidoApoderado" value="<%=ApellidoApoderado%>" /></td>
                                                </tr>
                                                <tr>
                                                    <td><font color="#385b88" style="font-size: 12px;"><b>Fecha Nacimiento:</b></font></td>
                                                    <td><input type="text" id="txtFechaNacApoderado" name="txtFechaNacApoderado" value="<%=FechaNacApoderado%>" onclick="popUpCalendar(this, formulario.txtFechaNacApoderado , 'dd/mm/yyyy');" /></td>
                                                </tr>
                                            </table>
                                        </fieldset>
                                    </td>
                                </tr>
                            </table>
                            <center>
                                <table>
                                    <tr>
                                        <td><input type="hidden" value="1" name="save"><center><input id="cmdGuardar" type="button" onclick="activarform();" value="Guardar Registro" <%if (banfichero.equals("1")) {%>disabled<%}%>></center></td>
                                    </tr>
                                </table>
                            </center>
                            <input type="hidden" value="" id="bansubirfichero" name="bansubirfichero">
                        </form>
                        <div id="divf"></div>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="../common/footer.jsp" %>
    </body>
</html>
