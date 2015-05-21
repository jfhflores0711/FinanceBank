<%-- 
Document : Cobranzas.jsp
Created on : 11/02/2010, 04:41:24 PM
Author : Oscar
--%>
<%@page session="true"%>
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
        <script language="JavaScript" type="text/JavaScript">
            <!--
            function MM_reloadPage(init) {//reloads the window if Nav4 resized
                if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
                        document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
                else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
            }
            MM_reloadPage(true);
            //-->
        </script>
        <script type="text/javascript">
            <%@include file="/js/funciones.js" %>
        </script>
        <script type="text/javascript" src="js/jquery.min1.102.js"></script>
        <script type="text/javascript" src="js/jquery.maskMoney.js"></script>
        <script type="text/javascript" src="js/validacion.js"></script>
        <script type="text/javascript" src="js/scriptMath.js"></script>
        <script type="text/javascript" src="js/scriptConvertTest.js"></script>
        <script type="text/javascript" src="js/scriptshow/scriptCobranzas.js"></script>
        <STYLE type="text/css">
            <!--
            #content {width: 1040px;}
            #main {width: 800px;}
            .Estilo1 {font-size: 18px; font-weight: bold;}
            .Estilo3 {font-size: 18px}
            .Estilo4 {color: #FF0000; font-weight: bold; font-family: Arial, Helvetica, sans-serif; }
            .cobranza{color: #125010; font-size:1.4em; font-family: Arial, Helvetica, sans-serif; }
            -->
        </STYLE>
        <link href='css/tabla.css' rel='stylesheet' type='text/css' />
    </head>
    <body onload="document.getElementById('rbPersona').focus();">
        <%
                    request.setAttribute("idmenu", "20110228202911502983");
        %>
        <%@include file="../common/header.jsp" %>        
        <div id="content">
            <div id="sidebar">
                <div id="menu">
                    <%@include file="../common/menu.jsp" %>
                </div>
                <div id="login" class="boxed">
                    <h2 class="title">Credencial personal</h2>
                    <div class="content">
                        <form id="form1" method="post" action="principal.htm">
                            <fieldset>
                                <legend>
                                    <input id="inputsubmit1" type="submit" name="inputsubmit1" value="Salir" />
                                </legend>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
            <div id="main"><div id="welcome" class="post">
                    <h2 class="title">Préstamos y cobranzas --> Créditos!</h2>
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
                                <td width="307" valign="top"><blockquote>
                                        <p><strong><a href="prestamos.htm" title="REALIZAR PRÉSTAMOS">Ir a PRÉSTAMOS</a></strong>&nbsp;</p>
                                    </blockquote></td>
                                <td width="588"><span class="Estilo1" style="background:url(images/bg_glossaryButton.png) repeat-x">COBRANZA DE CUOTAS</span></td>
                            </tr>
                        </table>
                        <div class="story">
                            <form name="formCobranza" method="POST" action="#">
                                <input type="hidden" name="xl_postback" value="1">
                                <fieldset style="border-width:3px">
                                    <legend><b>BUSCAR POR:</b></legend>
                                    <table width="100%">
                                        <tr>
                                            <td><input type="text" id="txtTempSelTipB" name="txtTempSelTipB" value="" style="display:none">
                                                <input type="text" id="txtTempdataB" name="txtTempdataB" value="" style="display:none">
                                                <input type="text" id="txtbanz" name="txtbanz" value="" style="display:none">
                                                <input type="text" id="txtestados" name="txtestados" value="0" style="display:none">
                                                <select id="selTipoBus" name="selTipoBus" onchange="validartxtBusquedac();" >
                                                    <option selected value="0">(Seleccione Tipo Busqueda)</option>
                                                    <option value="dni">DNI</option>
                                                    <option value="ruc">RUC</option>
                                                    <option value="nom">NOMBRE / R.S.</option>
                                                    <option value="ape">APELLIDOS</option>
                                                    <option value="cre">Nro. CREDITO</option>
                                                </select>
                                            </td>
                                            <td><div id="divtxtBuscar"><input id="txtBuscar" disabled type="text" name="txtBuscar" value=""/></div></td>
                                            <td><input id="btnBuscarPers" type="button" value="Buscar Persona" onclick="verpc();" /></td>
                                        </tr>
                                        <tr>
                                            <td colspan="3"><div id="ajaximgo" style="display:none"><img alt="Cargando.." src="images/ajax/ajax-loader1.gif"></div><div id="divlistPers">
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                </fieldset>
                                <div id="tableMisSels"><input type="hidden" value="" id="rus">
                                </div><div id="dg"><input type="hidden" value="" id="im_"></div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="../common/footer.jsp" %>
    </body>
</html>
