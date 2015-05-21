<%-- 
    Document   : newjsp
    Created on : 11/02/2010, 04:41:24 PM
    Author     : ronald
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
        <script type="text/javascript" src="js/validacion.js"></script>
        <script type="text/javascript" src="js/scriptMath.js"></script>
        <script type="text/javascript" src="js/scriptConvertTest.js"></script>
        <script type="text/javascript" src="js/scriptshow/scriptGiros.js"></script>
    </head>
    <body onload="document.getElementById('Ecategoria').focus();">
        <%@include file="../common/header.jsp" %>
        <%
                    request.setAttribute("idmenu", "20110228202911538690");
        %>
        <div id="login" style="display:none">
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
        <div id="content">
            <div id="sidebar">
                <div id="menu">
                    <%@include file="../common/menu.jsp" %>
                </div>
                <div id="login" class="boxed">
                    <h2 class="title">Cuentra del cliente</h2>
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
                    <h2 class="title">MODULO DE EMISION DE GIROS</h2>
                    <div class="story">
                        <form id="f1" name="f1" method="post" action="">
                            <table border="0" width="100%">
                                <tr>
                                    <td width="45%">
                                        <fieldset>
                                            <legend><b>DATOS DEL EMISOR</b></legend>
                                            <table border='0' width='100%'>
                                                <tr>
                                                    <td align='right'>
                                                        <font color="#385B88" style="font-size:12px"><b>Categoria&nbsp;</b></font>
                                                    </td>
                                                    <td align='left'>
                                                        <%
                                                                    String er = "EF";
                                                                    List result = d.findAll("from TCategoriaPersona");
                                                                    Iterator it4 = result.iterator();
                                                                    int j = 0;
                                                                    String check = "onclick=\"juridica('E')\"";
                                                                    while (it4.hasNext()) {
                                                                        if (j == 0) {
                                                                            check = "checked onclick=\"natural('E')\"";
                                                                        } else {
                                                                            check = "onclick=\"juridica('E')\"";
                                                                        }
                                                                        TCategoriaPersona cate = (TCategoriaPersona) it4.next();
                                                                        out.println(cate.getDescripcion() + "<input type='radio' name='Ecategoria' id='Ecategoria' value='" + cate.getIdcategoriapersona() + "' " + check + "  />");
                                                                        j++;
                                                                    }
                                                        %>
                                                    </td>
                                                </tr>
                                                <tr style='display:none'>
                                                    <td align='right'>
                                                        <font color="#385B88" style="font-size:12px"><b>idpersona</b></font>
                                                    </td>
                                                    <td align='left'>
                                                        <input id="Eidpersona" type='hidden' name="Eidpersona"/>
                                                    </td>
                                                </tr>
                                                <tr id="Etr_dni">
                                                    <td align='right'>
                                                        <font color="#385B88" style="font-size:12px"><b>D.N.I&nbsp;</b></font>
                                                    </td>
                                                    <td align='left'>
                                                        <input id='Edni' type='text' name='Edni' onkeypress='return soloNumeroTelefonico(event);' maxlength="8" onblur="buscarpersona('<%=er%>')" /><font color='red'>&nbsp;</font>
                                                    </td>
                                                </tr>
                                                <tr id="Etr_ruc">
                                                    <td align='right'>
                                                        <font color="#385B88" style="font-size:12px"><b>R.U.C&nbsp;</b></font>
                                                    </td>
                                                    <td align='left'>
                                                        <input disabled="true" id='Eruc' type='text' name='Eruc' onkeypress='return soloNumeroTelefonico(event);' maxlength="11" onblur="buscarpersona('<%=er%>') " /><font color='red'>*</font>
                                                    </td>
                                                </tr>
                                                <tr id="Etr_nombres">
                                                    <td align='right' id="Etd_nombres">
                                                        <font color="#385B88" style="font-size:12px"><b> Nombres&nbsp;</b></font>
                                                    </td>
                                                    <td align='right' id="Etd_razonsocial" style='display:none'>
                                                        <font color="#385B88" style="font-size:12px"><b>Razon Social</b></font>
                                                    </td>
                                                    <td align='left'>
                                                        <input id='Enombres' type='text' name='Enombres' maxlength="50" /><font color='red'>*</font>
                                                    </td>
                                                </tr>
                                                <tr id="Etr_apellidos">
                                                    <td align='right'>
                                                        <font color="#385B88" style="font-size:12px"><b>Apellidos&nbsp;</b></font>
                                                    </td>
                                                    <td align='left'>
                                                        <input id='Eapellidos' type='text' name='Eapellidos' maxlength="50" onblur="buscarpersona('EH') "/><font color='red'>*</font>
                                                    </td>
                                                </tr>
                                                <tr >
                                                    <td  colspan='2'>
                                                        <b>&nbsp;</b>
                                                    </td>
                                                </tr>
                                                <tr  id="Etr_email">
                                                    <td align='right'>
                                                        <font color="#385B88" style="font-size:12px"><b>Email&nbsp;</b></font>
                                                    </td>
                                                    <td align='left'>
                                                        <input id='Eemail' type='text' name='Eemail' maxlength="50"/>
                                                    </td>
                                                </tr>
                                                <tr id="Etr_ubigeo">
                                                    <td align='right'>
                                                        <font color="#385B88" style="font-size:12px"><b>Ubigeo&nbsp;</b></font>
                                                    </td>
                                                    <td align='left'>
                                                        <input id='Eubigeo' type='text' name='Eubigeo' onkeypress='return soloNumeroTelefonico(event);' maxlength="10"/><font color='red'>&nbsp;</font>
                                                    </td>
                                                </tr>
                                                <tr id="Etr_telefono">
                                                    <td align='right'>
                                                        <font color="#385B88" style="font-size:12px"><b>Tel&eacute;fono&nbsp;</b></font>
                                                    </td>
                                                    <td align='left'>
                                                        <input id='Etelefono' type='text' name='Etelefono' onkeypress='return soloNumeroTelefonico(event);' maxlength="10" />
                                                    </td>
                                                </tr>
                                                <tr id="Etr_celular">
                                                    <td align='right'>
                                                        <font color="#385B88" style="font-size:12px"><b>Celular&nbsp;</b></font>
                                                    </td>
                                                    <td align='left'>
                                                        <input id='Ecelular' type='text' name='Ecelular' onkeypress='return soloNumeroTelefonico(event);' maxlength="12" />
                                                    </td>
                                                </tr>
                                                <tr id="Etr_direccion">
                                                    <td align='right'>
                                                        <font color="#385B88" style="font-size:12px"><b>Direccion&nbsp;</b></font>
                                                    </td>
                                                    <td align='left'>
                                                        <input id='Edireccion' type='text' name='Edireccion' maxlength="100"/>
                                                    </td>
                                                </tr>
                                            </table>
                                            <br>
                                        </fieldset>
                                    </td>
                                    <td width="10%">
                                        &nbsp;
                                    </td>
                                    <td width="45%">
                                        <fieldset>
                                            <legend><b>DATOS DEL RECEPTOR</b></legend>
                                            <table border='0' width='100%'>
                                                <tr>
                                                    <td align='right'>
                                                        <font color="#385B88" style="font-size:12px"><b>Categoria&nbsp;</b></font>
                                                    </td>
                                                    <td align='left'>
                                                        <%
                                                                    String Rer = "RF";
                                                                    List Rresult = d.findAll("from TCategoriaPersona");
                                                                    Iterator it4R = Rresult.iterator();
                                                                    int jR = 0;
                                                                    String checkR = "onclick=\"juridica('R')\"";
                                                                    while (it4R.hasNext()) {
                                                                        if (jR == 0) {
                                                                            checkR = "checked onclick=\"natural('R')\"";
                                                                        } else {
                                                                            checkR = "onclick=\"juridica('R')\"";
                                                                        }
                                                                        TCategoriaPersona cate = (TCategoriaPersona) it4R.next();
                                                                        out.println(cate.getDescripcion() + "<input type='radio' name='Rcategoria' id='Rcategoria' value='" + cate.getIdcategoriapersona() + "' " + checkR + "  />");
                                                                        jR++;
                                                                    }
                                                        %>
                                                    </td>
                                                </tr>
                                                <tr style='display:none'>
                                                    <td align='right'>
                                                        <font color="#385B88" style="font-size:12px"><b>idpersona</b></font>
                                                    </td>
                                                    <td align='left'>
                                                        <input id="Ridpersona" type='hidden' name="Ridpersona" />
                                                    </td>
                                                </tr>
                                                <tr id="Rtr_dni">
                                                    <td align='right'>
                                                        <font color="#385B88" style="font-size:12px"><b>D.N.I&nbsp;</b></font>
                                                    </td>
                                                    <td align='left'>
                                                        <input id='Rdni' type='text' name='Rdni' onkeypress='return soloNumeroTelefonico(event);' maxlength="8" onblur="buscarpersona('<%=Rer%>')" /><font color='red'>&nbsp;</font>
                                                    </td>
                                                </tr>
                                                <tr id="Rtr_ruc">
                                                    <td align='right'>
                                                        <font color="#385B88" style="font-size:12px"><b>R.U.C&nbsp;</b></font>
                                                    </td>
                                                    <td align='left'>
                                                        <input disabled="true" id='Rruc' type='text' name='Rruc' onkeypress='return soloNumeroTelefonico(event);' maxlength="11" onblur="buscarpersona('<%=Rer%>') " /><font color='red'>*</font>
                                                    </td>
                                                </tr>
                                                <tr id="Rtr_nombres">
                                                    <td align='right' id="Rtd_nombres">
                                                        <font color="#385B88" style="font-size:12px"><b> Nombres&nbsp;</b></font>
                                                    </td>
                                                    <td align='right' id="Rtd_razonsocial" style='display:none'>
                                                        <font color="#385B88" style="font-size:12px"><b>Razon Social</b></font>
                                                    </td>
                                                    <td align='left'>
                                                        <div id="detalle" style='display:none'></div>
                                                        <input id='Rnombres' type='text' name='Rnombres' maxlength="50" /><font color='red'>*</font>
                                                    </td>
                                                </tr>
                                                <tr id="Rtr_apellidos">
                                                    <td align='right'>
                                                        <font color="#385B88" style="font-size:12px"><b>Apellidos&nbsp;</b></font>
                                                    </td>
                                                    <td align='left'>
                                                        <input id='Rapellidos' type='text' name='Rapellidos' maxlength="50" onblur="buscarpersona('RH')"/><font color='red'>*</font>
                                                    </td>
                                                </tr>
                                                <tr >
                                                    <td colspan='2'>
                                                        <b>&nbsp;</b>
                                                    </td>
                                                </tr>
                                                <tr id="Rtr_email">
                                                    <td align='right'>
                                                        <font color="#385B88" style="font-size:12px"><b>Email&nbsp;</b></font>
                                                    </td>
                                                    <td align='left'>
                                                        <input id='Remail' type='text' name='Remail' maxlength="50"/>
                                                    </td>
                                                </tr>
                                                <tr id="Rtr_ubigeo">
                                                    <td align='right'>
                                                        <font color="#385B88" style="font-size:12px"><b>Ubigeo&nbsp;</b></font>

                                                    </td>
                                                    <td align='left'>
                                                        <input id='Rubigeo' type='text' name='Rubigeo' onkeypress='return soloNumeroTelefonico(event);' maxlength="10"/><font color='red'>&nbsp;</font>
                                                    </td>
                                                </tr>
                                                <tr id="Rtr_telefono">
                                                    <td align='right'>
                                                        <font color="#385B88" style="font-size:12px"><b>Tel&eacute;fono&nbsp;</b></font>
                                                    </td>
                                                    <td align='left'>
                                                        <input id='Rtelefono' type='text' name='Rtelefono' onkeypress='return soloNumeroTelefonico(event);' maxlength="10" />
                                                    </td>
                                                </tr>
                                                <tr id="Rtr_celular">
                                                    <td align='right'>
                                                        <font color="#385B88" style="font-size:12px"><b>Celular&nbsp;</b></font>
                                                    </td>
                                                    <td align='left'>
                                                        <input id='Rcelular' type='text' name='Rcelular' onkeypress='return soloNumeroTelefonico(event);' maxlength="12" />
                                                    </td>
                                                </tr>
                                                <tr id="Rtr_direccion">
                                                    <td align='right'>
                                                        <font color="#385B88" style="font-size:12px"><b>Direccion&nbsp;</b></font>
                                                    </td>
                                                    <td align='left'>
                                                        <input id='Rdireccion' type='text' name='Rdireccion' maxlength="100"/>
                                                    </td>
                                                </tr>
                                            </table>
                                            <br>
                                        </fieldset>
                                    </td>
                                </tr>
                            </table>
                            <fieldset>
                                <legend><b>DATOS DEL GIRO</b></legend>
                                <table border="0" cellpadding="10" cellspacing="10" width="100%">
                                    <tr>
                                        <td align="left">
                                            <font color="#385B88" style="font-size:12px"><b>CONDICION DEL EMISOR&nbsp;&nbsp;&nbsp;&nbsp;</b></font>
                                            <input id='grupo' type='text' name='grupo' style='color:red; font-weight:bold ;background:transparent' readonly='true' value="" size="10" />
                                        </td>
                                        <td align="right">
                                            <font color="#385B88" style="font-size:12px"><b>RECEPCI&Oacute;N :</b></font>
                                            <select name="filial_recepcion" id="filial_recepcion" >
                                                <%
                                                            result = d.findAll("from TFilial");
                                                            Iterator it3 = result.iterator();
                                                            while (it3.hasNext()) {
                                                                TFilial filial_recept = (TFilial) it3.next();
                                                                out.println("<option value='" + filial_recept.getCodFilial() + "' >" + filial_recept.getNombre() + "</option>");
                                                            }
                                                %>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="left">
                                            <font color="#385B88" style="font-size:12px">MONTO A GIRAR</font>
                                            <select name="moneda" style="font-size:20px" onchange="actmoneda()" >
                                                <%
                                                            result = d.findAll("from TMoneda where estado ='ACTIVO'");
                                                            Iterator itMONEDA = result.iterator();
                                                            String sel = "";
                                                            while (itMONEDA.hasNext()) {
                                                                TMoneda Moneda = (TMoneda) itMONEDA.next();
                                                                if (Moneda.getCodMoneda().equals("PEN")) {
                                                                    sel = "selected";
                                                                } else {
                                                                    sel = "";
                                                                }
                                                                out.println("<option label='" + Moneda.getSimbolo() + "' value='" + Moneda.getCodMoneda() + "' " + sel + ">" + Moneda.getSimbolo() + "</option>");
                                                            }
                                                %>
                                            </select>
                                            <input id="monto" type="text" style="font-size:20px;width:140px;text-align:right" name="monto" value="0.00" onkeypress="return validar(event);" onblur="this.value=eedisplayFloatNDTh(eeparseFloatTh(this.value),2);calculo_comision2();calulo_gironeto();"  onfocus="this.select();" />
                                        </td>
                                        <td align="right">
                                            <font color="#385B88" style="font-size:12px"><b>EFECTIVO :</b></font>
                                            <input type="radio" name="forpago" id="forpago1" value="EFECTIVO" checked onclick="efectivo();"/>
                                            <font color="#385B88" style="font-size:12px"><b>CARGO A CUENTA:</b></font>
                                            <input type="radio" name="forpago" id="forpago2" disabled value="CUENTA" onclick="porcuenta();" />
                                        </td>
                                    <tr>
                                        <td align="left">
                                            <table border="0 " cellpadding="10" cellspacing="10" width="100%">
                                                <tr>
                                                    <td>
                                                        <font color="#385B88" style="font-size:12px">COBRAR COMISION <b>
                                                                <input id="p_comision" type="text" style="font-size:12px;width:50px;text-align:right" name="p_comision" value="1.50" onkeypress="return validar(event);" onfocus="this.select();" onblur="this.value=eedisplayFloatNDTh(eeparseFloatTh(this.value),2);calculo_comision();calulo_gironeto();" disabled />
                                                                %
                                                            </b>
                                                        </font>
                                                        <input type='checkbox' id='Icomision' name='Icomision' onclick='comision()' />
                                                    </td>
                                                    <td>
                                                        <table border="0" cellspacing="5">
                                                            <tr>
                                                                <td>
                                                                    <div id="simbolo1" style="visibility:hidden;font-size:15px;color:#000000;font-weight: bold;"></div>
                                                                </td>
                                                                <td>
                                                                    <div id="div_comision" style="font-size:15px;color:#000000;font-weight: bold;"></div>
                                                                    <input id="monto_comision" type="text" style="font-size:12px;width:50px;text-align:right" name="monto_comision" value="5.00" onkeypress="return validar(event);" onfocus="this.select();" onblur="this.value=eedisplayFloatNDTh(eeparseFloatTh(this.value),2);calculo_comision2();calulo_gironeto();" disabled />
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                                <tr id="tr_usuariop" style="visibility:hidden">
                                                    <td>
                                                        <font color="#385B88" style="font-size:12px"><b>EMISOR:</b></font>
                                                        <input type="radio" name="usuariop" id="usuariop1" value="PEMISOR" checked onclick='rpemisor();calulo_gironeto();'/>
                                                    </td>
                                                    <td>
                                                        <font color="#385B88" style="font-size:12px"><b>RECEPTOR:</b></font>
                                                        <input type="radio" name="usuariop" id="usuariop2" value="PRECEPTOR" onclick='rpreceptor();calulo_gironeto();' />
                                                    </td>
                                                </tr>
                                                <tr id="tr_formap" style="visibility:hidden">
                                                    <td>
                                                        <font color="#385B88" style="font-size:12px"><b>EFECTIVO:</b></font>
                                                        <input type="radio" name="formap" id="formap1" value="EFECTIVO" checked onclick="calulo_gironeto()"/>
                                                    </td>
                                                    <td>
                                                        <font color="#385B88" style="font-size:12px"><b>DESCONTAR:</b></font>
                                                        <input type="radio" name="formap" id="formap2" value="DESCONTAR" onclick="calulo_gironeto()"/>
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td align="right">
                                            <table border="0" cellspacing="5">
                                                <tr>
                                                    <td>
                                                        <font color="#385B88" style="font-size:12px"><b>GIRO NETO </b></font>
                                                    </td>
                                                    <td id="simbolo2" style="font-size:20px;color:#018d00" >
                                                    </td>
                                                    <td>
                                                        <input id="giro_neto" type="text" style="font-size:20px;width:140px;color:#018d00;background:transparent;text-align:right" readonly='true' name="giro_neto" value="" />
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <font color="#385B88" style="font-size:12px"><b>TOTAL </b></font>
                                                    </td>
                                                    <td id="simbolo3" style="font-size:20px;color:#E08934">
                                                    </td>
                                                    <td>
                                                        <input id="total" type="text" style="font-size:20px;width:140px;color:#E08934;background:transparent;text-align:right" readonly='true' name="total" value="" />
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">
                                            <div id="detalle_cuenta" ></div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" align="center">
                                            <input type="button" onclick="procesar();" value="PROCESAR" />
                                        </td>
                                    </tr>
                                </table>
                            </fieldset>
                        </form>
                        <br>
                        <br>
                        <fieldset>
                            <table border="0" class='tabla' width="100%" cellpadding="5" cellspacing="5">
                                <tr>
                                    <th width="17%" align="center" style="font-size:10px;color:#000000">Fecha</th>
                                    <th width="22%" align="center" style="font-size:10px;color:#000000">N&deg; OP</th>
                                    <th width="16%" align="center" style="font-size:10px;color:#000000">Operacion</th>
                                    <th width="16%" align="center" style="font-size:10px;color:#000000">Monto</th>
                                    <th width="16%" align="center" style="font-size:10px;color:#000000">Moneda</th>
                                    <th width="19%" align="center" style="font-size:10px;color:#000000">&nbsp;</th>
                                    <th width="1%" align="center" style="font-size:10px;color:#000000">&nbsp;</th>
                                </tr>
                            </table>
                            <div id='extorno' style='overflow-y:auto; height:100px;width:100%' >
                                <table border="0" class='tabla' width="100%" cellpadding="2" cellspacing="2">
                                    <%
                                                String hql = "from TRegistroGiro where TOperacion.TPersonaCaja.idpersonacaja='" + session.getAttribute("USER_ID_PERSONA_CAJA") + "'  AND TOperacion.TTipoOperacion.codigoTipoOperacion ='TIPC5' AND estado='ESPERA' AND TOperacion.estado='ACTIVO' AND fecha like '" + DateUtil.getDate(new Date()) + "%' order by fecha desc";
                                                List resultOp = d.findAll(hql);
                                                Iterator itOp = resultOp.iterator();
                                                Double Monto = 0.00;
                                                String Tipo = "TIPC5";
                                                int k = 0;
                                                while (itOp.hasNext()) {
                                                    TRegistroGiro opreg = (TRegistroGiro) itOp.next();
                                                    Monto = opreg.getImporte().doubleValue() + opreg.getComision().doubleValue();
                                                    if (k % 2 == 0) {
                                                        out.println("<tr class='modo1'>");
                                                    } else {
                                                        out.println("<tr class='modo2'>");
                                                    }
                                    %>
                                    <td width="20%" style="font-size:10px;"><%=opreg.getFecha()%></td>
                                    <td width="20%" style="font-size:10px;"><%=opreg.getTOperacion().getNumeroOperacion()%></td>
                                    <td width="20%" style="font-size:10px;"><%=opreg.getTOperacion().getTTipoOperacion().getNombre()%></td>
                                    <td width="20%" style="font-size:10px;"><%=fm.formatMoneda(Monto)%></td>
                                    <td width="20%" style="font-size:10px;"><%=opreg.getTOperacion().getTMoneda().getNombre()%></td>
                                    <td width="19%" style="font-size:10px; text-align: right;"><input type="button" value="Extornar" onclick="extornar('<%=opreg.getIdregistro().toString()%>','<%=Tipo%>')" /></td>
                                        <% out.println("</tr>");
                                                        k++;
                                                    }%>
                                </table>
                            </div>
                        </fieldset>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="../common/footer.jsp" %>
    </body>
</html>
