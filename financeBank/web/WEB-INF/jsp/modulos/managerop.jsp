<%-- 
    Document   : managercapital.jsp
    Created on : 11/02/2010, 04:41:24 PM
    Author     : oscar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8" />
        <link rel="shortcut icon" href="./images/favicon.ico" />
        <title>FinanceBank Admin Operaciones!</title>
        <meta name="keywords" content="FinanceBank" />
        <meta name="description" content="Sistema financiero" />
        <link href="default.css" rel="stylesheet" type="text/css" />
        <link href='css/tabla.css' rel='stylesheet' type='text/css' />
        <script type="text/javascript" src="js/validacion.js">
        </script>
        <style type="text/css" media="print">
            .eebuttonbar_top {display:none;}
            .eebuttonbar_bottom {display:none;}
            #dh{display:none;}
            #footer{display: none;}
            #sidebar {display:none;width:0px;}
            #logo {display:none;}
            #h1{display: none;}
            #h2{display: none;}
            .conf{display:block;}
            #main{width: 950px;margin: -20px;font-size: 7pt;}
            #panel1{font-size: 7pt;}
            .tabla{font-size: 7pt;}
            .ee115{font-size: 7pt;}
            .modo1{font-size: 7pt;}
            .modo2{font-size: 7pt;}
        </style>
        <script type="text/javascript" src="js/scriptConvertTest.js"></script>
        <script type="text/javascript" src="js/scriptshow/scriptMOp.js"></script>
        <script type="text/javascript" src="js/calendar/popcalendar.js"> </script>
    </head>
    <body>
        <%@include file="../common/header.jsp" %>
        <%
                    request.setAttribute("idmenu", "20110804142911505556");
        %>
        <div id="content">
            <div id="sidebar">
                <div id="menu">
                    <%@include file="../common/menu.jsp" %>
                </div>
                <div id="login" class="boxed">
                    <h2 class="title">Client Account</h2>
                    <div class="content">
                        <form id="form1" method="post" action="logout.htm">
                            <fieldset>
                                <input id="inputsubmit1" type="submit" name="inputsubmit1" value="Salir" />
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
            <div id="main">
                <div id="welcome" class="post">
                    <h2 class="title">ADMINISTRADOR DE OPERACIONES</h2>
                    <div class="story">
                        <form id="fbus" name="fbus" method="post" action="">
                            <fieldset>
                                <legend style="font-size:8px"><b>FORMULARIO DE BÚSQUEDA</b></legend>
                                <table border='0' cellpadding='5' cellspacing='5' class='tabla' width='100%'>
                                    <tr>
                                        <td>
                                            <font color="#385B88" style="font-size:12px"><b>BUSCAR POR :&nbsp;&nbsp;&nbsp;</b</font>
                                            <select name="busca" id="busca" style="font-size:13px" onchange="rechargeInput();" >
                                                <option value='MONTOX1'>NÚMERO DE OPERACIÓN</option>
                                                <option value='MONTOX2'>REFERENCIA (Sólo Dep. /Ret.)</option>
                                                <option value='MONTOX3'>Núm. de Cuenta (Sólo Dep./Ret.)</option>
                                                <option value='MONTOX4'>Horas del Día (HH:MM)</option>
                                                <option value='MONTOX5'>Tipo de Operación (Hoy)</option>
                                                <option value='MONTOX6'>MONTO DEL DÍA (por Moneda)</option>
                                            </select>
                                            MÁS DATOS: <input type="checkbox" id="WITHMORE" value="OTROS DATOS"  onclick="if(document.getElementById('WITHMORE').checked){document.getElementById('mas').style.display='block';}else{document.getElementById('mas').style.display='none';}">
                                        </td>
                                        <td><div id="mas" style="display:none"><input type="radio" name="more" value="Listado" id="more1" checked> Listado<input id="more2" type="radio" name="more" value="Estadistica"> Estadística</div></td>
                                    </tr>
                                </table>
                            </fieldset>
                            <div id="data">
                                <fieldset>
                                    <legend style="font-size:8px"><b>INGRESO DE DATOS</b></legend>
                                    <div id="stylist"><table border='0' cellpadding='5' cellspacing='5' class='tabla' width='100%'>
                                            <tr><td>&nbsp;</td>
                                                <td align='right'><font color='#385B88' style='font-size:12px'><b>INGRESE EL DATO A BUSCAR:</b</font>
                                                </td>
                                                <td style='font-size:20px;color:#000000;'><input id='inn' type='text' style='font-size:20px;text-align:right;' name='inn' />
                                                </td></tr></table></div>
                                    <table border='0' cellpadding='5' cellspacing='5' class='tabla' width='100%'>
                                        <tr>
                                            <td style="font-size:16px;color:#018d00;">
                                                HOY <%=DateUtil.getDateTime("dd/MM/yyyy", new Date())%> &nbsp;<input type="button" value="BUSCAR AHORA" onclick="busqueda('fast')"/>
                                            </td>
                                            <td id="hard1">
                                                <input type="button" value="BUSCAR EN:" onclick="busqueda('hard');"/>&nbsp;
                                            </td>
                                            <td id="hard2"><font style="font-size:11px"><b>FECHA:</b</font><input id="desde" name="desde" type="text" value="<%=DateUtil.getDateTime("dd/MM/yyyy", new Date())%>" onclick="popUpCalendar(this, fbus.desde , 'dd/mm/yyyy');"/>
                                                <!--<br/>
                                                <font style="font-size:11px"><b>HASTA:</b</font><input id="hasta" name="hasta" type="text" value="<%=DateUtil.getDateTime("dd-MM-yyyy", new Date())%>" onclick="popUpCalendar(this, fbus.hasta , 'dd-mm-yyyy');"/>
                                                -->
                                            </td>
                                        </tr>
                                    </table>
                                </fieldset></div>
                        </form>
                        <fieldset>
                            <legend style="font-size:9px"><b>RESULTADOS</b></legend>
                            <table border='0' cellpadding='5' cellspacing='5' class='tabla' width='100%'>
                                <tr>
                                    <td width="100%">
                                        <div id="BUSQUEDAS"></div>
                                    </td>
                                </tr>
                            </table>
                        </fieldset><div id="divshowre">
                            <fieldset>
                                <legend style="font-size:9px"><b>DATOS DE LA OPERACION</b></legend>
                                <table border='0' cellpadding='5' cellspacing='5' id="h1" class='tabla' width='100%'>
                                    <tr>
                                        <td valign="top">
                                            NO RESULTS.
                                        </td>
                                    </tr>
                                </table>
                            </fieldset></div>
                    </div>
                    <div id="divesperarres" style="display: none"></div>                    
                </div>
            </div><div id="divr" style="display: none"></div>
        </div>
        <%@include file="../common/footer.jsp" %>
        <script type="text/javascript">
            document.getElementById('busca').focus();
        </script>
    </body>
</html>