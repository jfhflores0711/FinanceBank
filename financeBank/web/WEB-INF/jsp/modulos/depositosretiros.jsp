<%--
    Document   : newjsp
    Created on : 11/02/2010, 04:41:24 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8" />
        <link rel="shortcut icon" href="./images/favicon.ico" />
        <title>FinanceBank - Depósitos, Retiros</title>
        <meta name="keywords" content="FinanceBank" />
        <meta name="description" content="Sistema financiero" />
        <link href="default.css" rel="stylesheet" type="text/css" />
        <link href='css/tabla.css' rel='stylesheet' type='text/css' /><!-- WIN PROTOTYPE -->
        <script type="text/javascript" src="js/prototype.js"> </script>
        <script type="text/javascript" src="js/effects.js"> </script>
        <script type="text/javascript" src="js/window.js"> </script>
        <script type="text/javascript" src="js/debug.js"> </script>
        <link href="css/themes/default.css" rel="stylesheet" type="text/css"/>
        <link href="css/themes/alert.css" rel="stylesheet" type="text/css"/>
        <link href="css/themes/alphacube.css" rel="stylesheet" type="text/css"/>
        <script type="text/javascript" src="js/validacion.js"> </script>
        <script type="text/javascript" src="js/scriptMath.js"></script>
        <script type="text/javascript" src="js/scriptConvertTest.js"></script>
        <script type="text/javascript" src="js/scriptshow/scriptDepositoRetiro.js"></script>
        <style type="text/css">
            .tableItems {border: 1px solid #008585;border-spacing: 0px;border-collapse: collapse;}
            .thItems {background: #008585;font-family: Arial;font-size: 12pt;color: #FFFFFF;}
            .tdItems {border: 1px solid #008585;font-family: Arial;font-size: 10pt;}
            .trItems {background: #FFFFFF;}
            .trItemspar {background: #9BCFCF;}
        </style>
        <script type="text/javascript" src="js/calendar/popcalendar.js"> </script>
    </head>
    <body>
        <%@include file="../common/header.jsp" %>
        <%
                    request.setAttribute("idmenu", "20110228202911519729");
        %>
        <div id="content">
            <div id="sidebar">
                <div id="menu">
                    <%@include file="../common/menu.jsp" %>
                </div>
                <div id="login" class="boxed">
                    <h2 class="title">Cuenta del Cliente</h2>
                    <div class="content">
                        <form id="form1" method="post" action="principal.htm">
                            <input id="inputsubmit1" type="submit" name="inputsubmit1" value="Salir" />
                        </form>
                    </div>
                </div>
            </div>
            <div id="main">
                <div id="welcome" class="post">
                    <h2 class="title">Modulo de Dep&oacute;sitos y Retiros!</h2>
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
                        <div style="clear:both">
                        </div>
                        <p>
                            <span class="login_label">Usuario</span>
                            <span class="login_input">
                                <input type="text" id="user"/>
                            </span>
                        </p>
                        <div style="clear:both"></div>
                        <p>
                            <span class="login_label">Contrase&ntilde;a</span>
                            <span class="login_input">
                                <input type="password" id="passwd"/>
                            </span>
                        </p>
                        <div style="clear:both">
                        </div>
                        <div id="res" style="display:none">
                        </div>
                    </div>
                    <div class="story">
                        <form id="frmdeposito" name="frmdeposito" action="" method="post">
                            <table>
                                <tr>
                                    <td>
                                        <fieldset   style="border-width:3px">
                                            <legend><b>TIPO DE TRANSACCION:</b></legend>
                                            <center>
                                                <table style="line-height: ">
                                                    <tr>
                                                        <td><font color='#385b88' style='font-size: 12px;'><b>&nbsp;</b></font></td>
                                                        <td><b> <a onclick="document.getElementById('rdDeposito').checked=true; document.getElementById('txtTipTrans').value='DEPOSITO';" onmouseover="this.style.cursor='default'" style="cursor:default;font-size:16px">DEPOSITO</a><input id="rdDeposito" name="rdTipoTransaccion" type="radio" onclick="document.getElementById('txtTipTrans').value='DEPOSITO'; cambiartipoTrans();"></b> &nbsp;&nbsp;&nbsp;&nbsp;
                                                            <b> <a onclick="document.getElementById('rdRetiro').checked=true; document.getElementById('txtTipTrans').value='RETIRO';" onmouseover="this.style.cursor='default'" style="cursor:default;font-size:16px">RETIRO</a><input id="rdRetiro"  name="rdTipoTransaccion" type="radio" onclick="document.getElementById('txtTipTrans').value='RETIRO'; cambiartipoTrans();"></b>
                                                            <input id="txtTipTrans" name="txtTipTrans" type="text" value="" style="display:none">
                                                        </td>
                                                    </tr>
                                                </table>
                                            </center>
                                        </fieldset>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2"><div id="divTipoTrans">
                                            <table>
                                                <tr><td>
                                                        <div id="divBotonCrear">
                                                        </div>
                                                    </td></tr>
                                                <tr>
                                                    <td colspan="3">
                                                        <fieldset   style="border-width:3px">
                                                            <legend><b>BUSCAR POR:</b></legend>
                                                            <table>
                                                                <tr>
                                                                    <td><input type="text" id="txtTempSelTipB" name="txtTempSelTipB" value="" style="display:none">
                                                                        <input type="text" id="txtTempdataB" name="txtTempdataB" value="" style="display:none">
                                                                        <input type="text" id="txtbanz" name="txtbanz" value="" style="display:none">
                                                                        <input type="text" id="txtestados" name="txtestados" value="0" style="display:none">
                                                                        <select id="selTipoBus" name="selTipoBus" onchange="validartxtBusqueda()" >
                                                                            <option value="0">(Seleccione Tipo Busqueda)</option>
                                                                            <option>NUMERO DE CUENTA</option>
                                                                            <option>DNI</option>
                                                                            <option>RUC</option>
                                                                            <option>NOMBRE</option>
                                                                            <option>APELLIDO</option>
                                                                        </select>
                                                                    </td>
                                                                    <td><div id="divtxtBuscar"><input id="txtBuscar" disabled type="text" name="txtBuscar" value="" onkeypress='return soloNumeroTelefonico(event);' /></div></td>
                                                                    <td colspan="4"><input id="btnBuscarCtas" type="button" value="BuscarCuenta" onclick="vercuenta()" /></td>
                                                                </tr>
                                                            </table>
                                                        </fieldset>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td colspan="6"><div id="divlistPerCuentas">
                                                        </div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td colspan="6"><div id="tableMiscuentas">
                                                        </div>
                                                    </td>
                                                </tr>
                                            </table>
                                        </div>
                                    </td>
                                </tr>
                                <tr><td><div id="divcuenta"></div></td></tr>
                            </table>
                            <div id="divKnegativo">
                                <input id="txtKnegativo" name="txtKnegativo" type="text" value="NO" style="display:none">
                            </div>
                            <center><input id="cmdRegistrar" name="cmdRegistrar" type="button" value="Registrar" onclick="registrarDepRet();" disabled/><br>
                                <div id="divpermisoKnegativo" style="display:none">
                                    <table border="0">
                                        <tr>
                                            <td><input id="chkKnegativo" name="chkKnegativo" type="checkbox" value="valor" onclick="ventanaNueva();" style="display:none"></td>
                                            <td><label id="blqKnegativo" style="display:none">Permitir prestamo si cliente no cuenta con saldo</label></td>
                                        </tr>
                                        <tr>
                                            <td colspan="2"><label id="lblinteres" style="display:none">Inter&eacute;s</label><input type="text" id="txtinteress" name="txtinteress" value="0" maxlength="23" onfocus="this.select();" onkeypress="return validar(event);" onchange="range = /^\d{1,3}(\.\d{1,3})?$/;
                                                hundred = /^1000$/;
                                                if(!(range.test(this.value) || hundred.test(this.value))){alert('La tasa de interés es inválida.'); document.getElementById('txtTasa').focus();};" style="display:none;">%</td>
                                        </tr>
                                        <tr>
                                            <td colspan="2"><label id="lblCobroKnegativo" style="display:none">Fecha de Pago</label><input type="text" id="txtCobroKnegativo" name="txtCobroKnegativo" value="<%=DateUtil.getDateTime("dd/MM/yyyy", new Date())%>" onclick="popUpCalendar(this, frmdeposito.txtCobroKnegativo , 'dd/mm/yyyy');" style="display:none"></td>
                                        </tr></table></div><div><table>
                                        <tr>
                                            <td><input id="chkCobroPF" name="chkCobroPF" type="checkbox" value="valor" onclick="ventanaNueva2();" style="display:none"></td>
                                            <td><label id="blqCobroPF" style="display:none">Permitir Cobro a Plazo Fijo antes de Fecha Pactada</label></td>
                                        </tr>
                                    </table>
                                </div>
                            </center>
                            <div id="divRespuestaRegistro">
                                <input id="txtRespReg" name="txtRespReg" type="text" style="display:none" value="">
                                <input id="txtExisteMontoCaja" name="txtExisteMontoCaja" type="text" style="display:none" value="">
                                <input id="txtVanPF" name="txtVanPF" type="text" style='display:none' value="">
                                <input id="txtCobroPF" name="txtCobroPF" type="text" style='display:none' value="x">
                            </div>
                        </form>
                        <form id="frmiraticket" name="frmiraticket" method="post" action="ticket.htm">
                        </form>
                        <input type="hidden" id="txtind" name="txtind" value="">
                    </div>
                </div>
            </div>
        </div>
        <script type="text/javascript">
            document.getElementById('rdDeposito').focus();
        </script>
        <%@include file="../common/footer.jsp" %>
    </body>
</html>