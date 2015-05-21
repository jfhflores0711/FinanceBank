<%-- 
    Document   : Prestamos.jsp
    Created on : 11/02/2010, 04:41:24 PM
    Author     : Oscar
--%>
<%@page session="true" import="java.util.Iterator,java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8" />
        <link rel="shortcut icon" href="./images/favicon.ico" />
        <title><%=(DateUtil.getDateTime("yyyyMMddHHmmssS", new Date()))%>|financeBank! Prestamos</title>
        <meta name="keywords" content="FinanceBank" />
        <meta name="description" content="Sistema financiero" />
        <link href="default.css" rel="stylesheet" type="text/css" />
        <link href='css/tabla.css' rel='stylesheet' type='text/css' />
        <link href="css/themes/default.css" rel="stylesheet" type="text/css"/>
        <link href="css/themes/alert.css" rel="stylesheet" type="text/css"/>
        <style type="text/css">
            .Estilo1 {font-size: 18px;font-weight: bold;}
            .Estilo4 {color: #FF0000;font-weight: bold;font-family: Arial, Helvetica, sans-serif;}
            .post .meta {font-size: 12px;}
        </style>
        <style type="text/css" media="print">
            *{visibility:hidden;}
        </style>
        <link href="css/themes/alphacube.css" rel="stylesheet" type="text/css"/>
        <script language="JavaScript" type="text/JavaScript">
            <!--
            function MM_reloadPage(init) {  //reloads the window if Nav4 resized
                if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
                        document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
                else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
            }
            MM_reloadPage(true);            
            //-->
        </script>
        <script type="text/javascript" src="js/jquery.min1.102.js"></script>
        <script type="text/javascript" src="js/jquery.maskMoney.js"></script>
        <script type="text/javascript" src="js/funciones.js"> </script>
        <script type="text/javascript" src="js/validacion.js"> </script>
        <script type="text/JavaScript" src="js/numpad.js"></script>
        <script type="text/javascript" src="js/scriptMath.js"></script>
        <script type="text/javascript" src="js/scriptConvertTest.js"></script>
        <script type="text/javascript" src="js/scriptshow/scriptPrestamo.js"></script>
        <script type="text/javascript" src="js/calendar/popcalendar.js"> </script>
    </head>
    <body>
        <%
                    request.setAttribute("idmenu", "20110228202911550727");
        %>
        <%@include file="../common/header.jsp" %>
        <div id="content">
            <div id="sidebar">
                <div id="menu">
                    <%@include file="../common/menu.jsp" %>
                </div>
                <div id="loginx" class="boxed">
                    <h2 class="title">SALIR DE ESTA CUENTA</h2>
                    <div class="content">
                        <form id="form1" method="post" action="principal.htm">
                            <fieldset><legend>
                                    <input id="inputsubmit2" type="submit" name="inputsubmit2" value="Salir" />
                                </legend>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
            <div id="main">
                <div id="welcome" class="post">
                    <h2 class="title">Préstamos y cobranzas -- Créditos!</h2>
                    <h3 class="date"><span class="month"><script type="text/javascript">
                        var fecha=new Date();
                        var fmtmonthnamesshort=new Array("Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre");
                        document.write(fmtmonthnamesshort[fecha.getMonth()]);
                            </script></span> <span class="day"><script type="text/javascript">
                                document.write(fecha.getDate());
                            </script></span><span class="year">, <script type="text/javascript">document.write(fecha.getFullYear());</script></span></h3>
                    <div class="meta">
                        <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td valign="top"><blockquote>
                                        <p><span class="Estilo1" style="background:url(images/bg_glossaryButton.png) repeat-x">PR&Eacute;STAMOS! </span></p>
                                    </blockquote></td>
                                <td>&nbsp;<strong><a href="cobranzas.htm" title="COBRANZA DE CUOTAS">Ir a COBRANZA DE CUOTAS</a></strong></td><td></td>
                            </tr>
                        </table>
                        <div class="story">
                            <form id="frmd" name="frmd" method="post" action="prestamos.htm">
                                <a onclick="document.getElementById('simular').style.display='';" onmouseover="this.style.cursor='default'" style="cursor:default;">SIMULAR.</a>
                                <div id="simular" style="display:none;">
                                    <fieldset style="border-width:3px">
                                        <legend><b>SIMULAR (TODOS CON CUOTAS FIJAS):</b></legend>
                                        <center>
                                            <table>
                                                <tr>
                                                    <td>
                                                        <p>Fecha de inicio</p>
                                                    </td>
                                                    <td>
                                                        &nbsp;<input type='text' id='txtf' name='txtf' size="10" value="<%=DateUtil.getDateTime("dd/MM/yyyy", new Date())%>" onclick="popUpCalendar(this, frmd.txtf, 'dd/mm/yyyy');" />
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <p>Monto del Préstamo (Capital):</p>
                                                    </td>
                                                    <td>
                                                        &nbsp;<input type="text" name="ncs" id="ncs" value="0.00"  style="font-size:20px;width:140px;text-align:right;" onKeyPress="return validar(event);" onblur="currencyFormatF(this,'','.');" onfocus="this.select();" />
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <p>Número de cuotas (Num de Amortiz.):</p>
                                                    </td>
                                                    <td>
                                                        &nbsp;<input type="text" id="numcuotas" name="numcuotas" value="12" onfocus="this.select();" style="width:140px;text-align:right;" onKeyPress='return soloNumeroTelefonico(event);' />
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <p>Tasa de Inter&eacute;s Anual(TEA) AÑO=360 Días</p>
                                                    </td>
                                                    <td>
                                                        <p>&nbsp;<input type="text" name="tasaIntSim" id="tasaIntSim" value="0.00" size="10" style="width:140px;text-align:right;" onkeypress="return validar(event);" onkeyup="tea();" onfocus="this.select();" />%</p>
                                                        <p style="font-weight: bold;">TEM:<span id="tem">0.00</span>% TED:<span id="ted">0.00</span>%</p>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <p>Días entre cuotas ((ACT)/ACT):</p>
                                                    </td>
                                                    <td>
                                                        &nbsp;<input type="text" name="diacuotas" id="diacuotas" value="30" onfocus="this.select();" style="width:140px;text-align:right;" onKeyPress='return soloNumeroTelefonico(event);' />
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <p>Modalidad</p>
                                                    </td>
                                                    <td>
                                                        &nbsp;
                                                        <table border="0">
                                                            <tbody>
                                                                <tr>
                                                                    <td>Dias Fijos (Días entre cuotas)</td>
                                                                    <td><input type="radio" name="gs" id="gs1" value="AlMonto" checked="checked" /></td>
                                                                </tr>
                                                                <tr>
                                                                    <td>Fechas fijas (A Rebatir: 30(E)/360)</td>
                                                                    <td><input type="radio" name="gs" id="gs2" value="AlRebatir" /></td>
                                                                </tr>
                                                            </tbody>
                                                        </table>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        &nbsp;
                                                    </td>
                                                    <td>
                                                        <input id="simula" type="button" value="CALCULAR!" name="simula" onclick="sim();" />
                                                    </td>
                                                </tr>
                                            </table>
                                        </center>
                                    </fieldset>
                                </div>
                                <div id="resultSim"></div>
                                <div id="solicitar" style="width:720px">
                                    <fieldset style="border-width:3px">
                                        <legend><b>INGRESAR SOLICITUD:</b></legend>
                                        <fieldset   style="border-width:3px">
                                            <legend><b>BUSCAR POR:</b></legend>
                                            <table width="100%">
                                                <tr>
                                                    <td><input type="text" id="txtTempSelTipB" name="txtTempSelTipB" value="" style="display:none">
                                                        <input type="text" id="txtTempdataB" name="txtTempdataB" value="" style="display:none">
                                                        <input type="text" id="txtbanz" name="txtbanz" value="" style="display:none">
                                                        <input type="text" id="txtestados" name="txtestados" value="0" style="display:none">
                                                        <select id="selTipoBus" name="selTipoBus" onchange="validartxtBusquedap();" >
                                                            <option selected value="0">(Seleccione Tipo Busqueda)</option>
                                                            <option value="dni">DNI</option>
                                                            <option value="ruc">RUC</option>
                                                            <option value="nom">NOMBRE / R.S.</option>
                                                            <option value="ape">APELLIDOS</option>
                                                        </select>
                                                    </td>
                                                    <td><div id="divtxtBuscar"><input id="txtBuscar" disabled type="text" name="txtBuscar" value=""/></div></td>
                                                    <td><input id="btnBuscarPers" type="button" value="Buscar Persona" onclick="verpp();" /></td>
                                                </tr>
                                                <tr>
                                                    <td colspan="3"><div id="divlistPers">
                                                        </div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td colspan="3"><div id="tableMisSels"><input type='hidden' value='' id='rus' />
                                                        </div>
                                                    </td>
                                                </tr>
                                            </table>
                                        </fieldset>
                                        <center>
                                            <fieldset style="border-width:3px">
                                                <legend><b>Datos de Nueva Solicitud:</b></legend>
                                                <table>
                                                    <tr>
                                                        <td>Producto</td>
                                                        <td><select name="productos" id="pr" onchange="selec();">
                                                                <option value="0">Seleccione...</option>
                                                                <%
                                                                            List ca = d.findAll("from TTipoCredito order by nombre");
                                                                            String da = "";
                                                                            if (!ca.isEmpty()) {
                                                                                for (Iterator it = ca.iterator(); it.hasNext();) {
                                                                                    TTipoCredito elem = (TTipoCredito) it.next();
                                                                                    da += "<option value='" + elem.getIdtipocredito() + "'>" + elem.getNombre() + "</option>";
                                                                                }
                                                                            }
                                                                            out.print("" + da);
                                                                %>
                                                            </select>
                                                        </td>
                                                        <td colspan="2"><div id="dcar"><input type='hidden' id='ix' value='ERROR'>&nbsp;</div>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            Fecha de Solicitud:
                                                        </td>
                                                        <td>
                                                            <input type="text" id="fs" name="fs" value="<%=DateUtil.getDateTime("dd/MM/yyyy", new Date())%>" onclick="popUpCalendar(this, frmd.fs, 'dd/mm/yyyy');" />
                                                        </td>
                                                        <td>
                                                            Fecha Prob. Aprobación:
                                                        </td>
                                                        <td>
                                                            <input type="text" id="fa" name="fa" value="<%=DateUtil.getDateTime("dd/MM/yyyy", new Date())%>" onclick="popUpCalendar(this, frmd.fa, 'dd/mm/yyyy');" />
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            Tipo de cuota:
                                                        </td>
                                                        <td>
                                                            <input type="radio" name="at" value="Fija" checked="checked" />Fija
                                                        </td>
                                                        <td>
                                                            Modo Cálculo:
                                                        </td>
                                                        <td>
                                                            Dias Fijos<input type="radio" name="ac" id="acd" value="df" checked="checked" />&nbsp;
                                                            Fechas Fijas<input type="radio" name="ac" id="acf" value="ff" />
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>Requiere garantía</td>
                                                        <td><select id='selG' name='selG'><option value='1'>1 SI </option><option value='2' selected>2 NO </option></select></td>
                                                        <td>
                                                            Días entre cuotas
                                                        </td>
                                                        <td>
                                                            <input type="text" name="decx" id="decx" onfocus="this.select();" value="30" onKeyPress='return soloNumeroTelefonico(event);'/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>Moneda:</td>
                                                        <td>
                                                            <select name="mon" id="mon">
                                                                <option value="PEN">NUEVO SOL</option>
                                                                <option value="USD">DOLAR USD</option>
                                                            </select>
                                                        </td>
                                                        <td>Monto(Importe):</td>
                                                        <td><input type="text" name="imp" id="imp" value="0.00" />
                                                            <script type="text/javascript">
                                                                $("#imp").maskMoney();
                                                            </script>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>Numero de Cuotas</td>
                                                        <td>
                                                            <input type="text" name="nc" id="nc" value="12" onfocus="this.select();" onKeyPress='return soloNumeroTelefonico(event);'/>
                                                        </td>
                                                        <td>Interes TEA (Anual %)</td>
                                                        <td><p><input type="text" name="intas" id="intas" value="0.00" onkeypress="return validar(event);" onkeyup="teap();" onfocus="this.select();" />%</p>
                                                            <p style="font-weight: bold;">TEM:<span id="temp">0.00</span>% TED:<span id="tedp">0.00</span>%</p></td>
                                                    </tr>
                                                    <tr>
                                                        <td colspan="4"><center>
                                                                <input type="button" id="gp" name="gp" value="Grabar Solicitud" onclick="grabarp();">
                                                            </center></td>
                                                    </tr>
                                                </table></fieldset>
                                        </center>
                                    </fieldset>
                                </div>
                                <table><tr>
                                        <td>
                                            <div id="divprestamos">
                                                <%
                                                            List xa = d.findAll("from TRegistroPrestamo where estadoSolicitud=0");
                                                            if (!xa.isEmpty()) {
                                                                out.print("<fieldset style='border-width:3px'>"
                                                                        + "<legend><b>Préstamos Guardados</b></legend>"
                                                                        + "<table>"
                                                                        + "<tr><td colspan='6' align='right'><input type='submit' value='Refrescar'></td></tr>"
                                                                        + "<tr><td>"
                                                                        + "N°"
                                                                        + "</td><td>"
                                                                        + "Nombre"
                                                                        + "</td><td>"
                                                                        + "Moneda y Monto"
                                                                        + "</td><td>"
                                                                        + "Fecha Solicitud"
                                                                        + "</td><td>"
                                                                        + "</td><td>"
                                                                        + "</td>"
                                                                        + "</tr>");
                                                                for (Iterator it = xa.iterator(); it.hasNext();) {
                                                                    TRegistroPrestamo at = (TRegistroPrestamo) it.next();
                                                                    out.print("<tr><td>"
                                                                            + at.getTCuentaPersona().getNumCta().substring(7, 12)
                                                                            + "</td><td>"
                                                                            + at.getTCuentaPersona().getTPersona().getNombre() + " " + at.getTCuentaPersona().getTPersona().getApellidos()
                                                                            + "</td><td>"
                                                                            + at.getTCuentaPersona().getTMoneda().getSimbolo() + " " + at.getMonto()
                                                                            + "</td><td>"
                                                                            + at.getFecha()
                                                                            + "</td><td><input type='hidden' name='ax" + at.getIdprestamo() + "' value='" + at.getIdprestamo() + "' />"
                                                                            + "<input type='button' name='des" + at.getIdprestamo() + "' onclick='despacho(\"" + at.getIdprestamo() + "\");' value='Desembolsar' />"
                                                                            + "</td><td>"
                                                                            + "<input type='button' name='eli" + at.getIdprestamo() + "' onclick='borrar(\"" + at.getIdprestamo() + "\")' value='Eliminar' />"
                                                                            + "</td></tr>");
                                                                }
                                                                out.print("</table></fieldset>");
                                                            }
                                                %>
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                                <input type="hidden" id="txtind" name="txtind" value=""/>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script type="javascript"> document.getElementById('ncs').focus();</script>
        <%@include file="../common/footer.jsp" %>
    </body>
</html>
