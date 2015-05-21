<%--
 Document : admincuenta
 Created on : 01/09/2010, 04:41:24 PM
 Author  : roger
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
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
        <script type="text/javascript" src="js/prototype.js"> </script>
        <script type="text/javascript" src="js/effects.js"> </script>
        <script type="text/javascript" src="js/window.js"> </script>
        <script type="text/javascript" src="js/debug.js"> </script>
        <link href="css/themes/default.css" rel="stylesheet" type="text/css"/>
        <link href="css/themes/alert.css" rel="stylesheet" type="text/css"/>
        <link href="css/themes/alphacube.css" rel="stylesheet" type="text/css"/>
        <script type="text/javascript" src="js/validacion.js"> </script>
        <script type="text/javascript" src="js/calendar/popcalendar.js"> </script>
        <script type="text/javascript" src="js/scriptMath.js"></script>
        <script type="text/javascript" src="js/scriptConvertTest.js"></script>
        <script type="text/javascript" src="js/scriptshow/scriptMInt.js"></script>
        <style type="text/css">
            .tableItems {
                border: 1px solid #008585;
                border-spacing: 0px;
                border-collapse: collapse;
            }

            .thItems {
                background: #008585;
                font-family: Arial;
                font-size: 12pt;
                color: #FFFFFF;
            }

            .tdItems {
                border: 1px solid #008585;
                font-family: Arial;
                font-size: 10pt;
            }

            .trItems {
                background: #FFFFFF;
            }

            .trItemspar {
                background: #9BCFCF;
            }
        </style>
    </head>
    <body>
        <%@include file="../common/header.jsp" %>
        <%
                    request.setAttribute("idmenu", "20110809142911505556");
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
                            <input id="inputsubmit1" type="submit" name="inputsubmit1" value="Salir" />
                        </form>
                    </div>
                </div>
            </div>
            <div id="main">
                <div id="welcome" class="post">
                    <img alt='Cargando ...' src='images/ajax/ajax-loader1.gif' style='display: none'/>
                    <h2 class="title">Administrar Intereses!</h2>
                    <h3 class="date"><span class="month"><script type="text/javascript">
                        var fecha=new Date();
                        var fmtmonthnamesshort=new Array("Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre");
                        document.write(fmtmonthnamesshort[fecha.getMonth()]);
                            </script></span> <span class="day"><script type="text/javascript">
                                document.write(fecha.getDate());
                            </script></span><span class="year">, <script type="text/javascript">document.write(fecha.getFullYear());</script></span></h3>
                    <div id="loginn" style="display:none">
                        <p>
                            <span id='login_error_msg' class="login_error" style="display:none;font-size:15px;">&nbsp;</span>
                        </p>
                        <div style="clear:both;">
                        </div>
                        <p>
                            <span class="login_label">Usuario</span>
                            <span class="login_input">
                                <input type="text" id="user"/>
                            </span>
                        </p>
                        <div style="clear:both;"></div>
                        <p>
                            <span class="login_label">Contrase&ntilde;a</span>
                            <span class="login_input">
                                <input type="password" id="passwd"/>
                            </span>
                        </p>
                        <div style="clear:both;">
                        </div>
                        <div id="res" style="display:none;">
                        </div>
                    </div>
                    <div class="story">
                        <form id="frmadmincuenta" name="frmadmincuenta" action="<%= request.getContextPath()%>/NuevaCuentaController.java" method="post">
                            <table>
                                <tr>
                                    <td colspan="2"><div id="divTipoTrans">
                                            <table>
                                                <tr><td>
                                                        <div id="divBotonCrear">
                                                        </div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td colspan="3">
                                                        <fieldset style="border-width:3px">
                                                            <legend><b>BUSCAR POR:</b></legend>
                                                            <table>
                                                                <tr>
                                                                    <td><input type="text" id="txtTempSelTipB" name="txtTempSelTipB" value="" style="display:none">
                                                                        <input type="text" id="txtTempdataB" name="txtTempdataB" value="" style="display:none">
                                                                        <input type="text" id="txtbanz" name="txtbanz" value="" style="display:none">
                                                                        <input type="text" id="txtestados" name="txtestados" value="0" style="display:none">
                                                                        <select id="selTipoBus" name="selTipoBus" onchange="validartxtBusqueda();" >
                                                                            <option value="0">(Seleccione Tipo Busqueda)</option>
                                                                            <option>NUMERO DE CUENTA</option>
                                                                            <option>DNI</option>
                                                                            <option>RUC</option>
                                                                            <option>NOMBRE</option>
                                                                            <option>APELLIDO</option>
                                                                        </select>
                                                                    </td>
                                                                    <td><div id="divtxtBuscar"><input id="txtBuscar" type="text" disabled name="txtBuscar" value="" onkeypress='return soloNumeroTelefonico(event);' /></div></td>
                                                                    <td colspan="4"><input id="btnBuscarCtas" type="button" value="BuscarCuenta"onclick="vercuenta()" /></td>
                                                                </tr>
                                                            </table>
                                                        </fieldset>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td colspan="6"><div id="divlistPerCuentas"></div></td>
                                                </tr>
                                                <tr>
                                                    <td colspan="6"><div id="tableMiscuentas"></div></td>
                                                </tr>
                                                <tr style="display: none;">
                                                    <td colspan="6">
                                                        <input type="hidden" id="txtfilial" name="txtfilial" value="<%= session.getAttribute("USER_FILIAL")%>">
                                                        <input type="hidden" id="txtcodCaja" name="txtcodCaja" value="<%= session.getAttribute("USER_CODCAJA")%>">
                                                        <input type="hidden" id="txtFecha" name="txtFecha" value="<%= DateUtil.getNOW_S()%>">
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td colspan="6"><div id="tableReports"></div><div id="tableReportx" style="display: none"></div></td>
                                                </tr>
                                            </table>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td><div id="divesperarCap" style="display: none;"></div><div id="divesperarservlet"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <div id="divcuenta"></div>
                                        <script type="text/javascript">document.getElementById('selTipoBus').focus();</script>
                                    </td>
                                </tr>
                                <tr>
                                    <td><div id="ver"></div>
                                    </td>
                                </tr>
                            </table>
                        </form>
                        <form id="frmiraticket" name="frmiraticket" method="post" action="ticket.htm">
                        </form>
                        <input type="hidden" id="txtind" name="txtind" value="">
                        <form id="frmIrEstadoCuenta" name="frmIrEstadoCuenta" method="post" action="estadodecuenta.htm">
                        </form>
                        <form id="frmActualizar" name="frmActualizar" method="post" action="managerint.htm">
                        </form>
                    </div>
                </div>
            </div><img alt="Cargando ..." src="images/ajax/ajax-loader1.gif" style="display: none;"/>
        </div>
        <%@include file="../common/footer.jsp" %>
    </body>
</html>
