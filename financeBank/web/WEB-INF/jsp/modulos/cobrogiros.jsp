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
        <script type="text/javascript" src="js/scriptConvertTest.js"></script>
        <script type="text/javascript" src="js/scriptshow/scriptCobrogiro.js"></script>
    </head>
    <body onload="document.getElementById('refrescar').focus();">
        <%@include file="../common/header.jsp" %>
        <%
                    request.setAttribute("idmenu", "20110228202911507851");
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
                    <h2 class="title">MODULO COBRO GIROS</h2>
                    <div class="story">
                        <form id="form1" method="post" action="" name="form1">
                            <input type="submit" id="refrescar" name="REFRESCAR" title="REFRESCAR" value="REFRESCAR" />
                            <fieldset>
                                <legend><b>DATOS DEL GIRO</b></legend>
                                <br>
                                <table  border="0" width="100%">
                                    <tr>
                                        <td>
                                            <div id="lista_giro"  style="overflow-y:auto; height:200px">
                                                <table border='0' cellpadding='0' cellspacing='0' class='tabla'  width='100%'>
                                                    <tr>
                                                        <th>
                                                            &nbsp;
                                                        </th>
                                                        <th align="center" style="font-size:10px;color:#000000"  >
                                                            FECHA
                                                        </th>
                                                        <th align="center" style="font-size:10px;color:#000000">
                                                            RECEPTOR
                                                        </th>
                                                        <th align="center" style="font-size:10px;color:#000000">
                                                            EMISOR
                                                        </th>
                                                        <th align="center" style="font-size:10px;color:#000000">
                                                            FILIAL ORIGEN
                                                        </th>
                                                        <th align="center" style="font-size:10px;color:#000000">
                                                            TIPO
                                                        </th>
                                                        <th align="center" style="font-size:10px;color:#000000">
                                                            MONTO
                                                        </th>
                                                        <th align="center" style="font-size:10px;color:#000000">
                                                            ESTADO
                                                        </th>
                                                    </tr>
                                                    <%
                                                                String hqlx = "from TRegistroGiro where estado='ESPERA' AND TFilial.codFilial ='" + codFilial + "'"
                                                                        + " order by idregistro desc ";
                                                                List result1 = d.findAll(hqlx);
                                                                Iterator itx1 = result1.iterator();
                                                                int k = 0;
                                                                while (itx1.hasNext()) {
                                                                    TRegistroGiro lGiro = (TRegistroGiro) itx1.next();
                                                                    Double valorComi = Double.parseDouble(lGiro.getComision() + "");

                                                                    Double valor = 0.00;
                                                                    if (lGiro.getFpagoComision().equals("DESCONTAR")) {
                                                                        valor = Double.parseDouble(lGiro.getImporte() + "") - Double.parseDouble(lGiro.getComision() + "");
                                                                    } else {
                                                                        valor = Double.parseDouble(lGiro.getImporte() + "");
                                                                    }
                                                                    TPersona receptor = (TPersona) d.load(TPersona.class, lGiro.getIdUserPkDestino());
                                                                    boolean borrado = false;
                                                                    if (receptor == null) {
                                                                        borrado = true;
                                                                        receptor = (TPersona) d.load(TPersona.class, "20110228202911739108");
                                                                    }
                                                                    boolean sinUserr = receptor.getIdUserPk().equals("20110228202911739108");
                                                                    boolean sinUsere = lGiro.getTPersona().getIdUserPk().equals("20110228202911739108");
                                                                    if (k % 2 == 0) {
                                                                        out.println("<tr class='modo1'>");
                                                                    } else {
                                                                        out.println("<tr class='modo2'>");
                                                                    }
                                                                    out.println("<td style='font-size:10px' > <input type='radio' name='giro' value='" + lGiro.getIdregistro()
                                                                            + "' onClick=\"selectGiro('" + lGiro.getIdregistro() + "','"
                                                                            + (sinUsere ? "" : lGiro.getTPersona().getDocIdentidad()) + "','"
                                                                            + (sinUsere ? "" : lGiro.getTPersona().getRuc()) + "','"
                                                                            + (sinUserr ? "" : receptor.getDocIdentidad()) + "','"
                                                                            + (sinUserr ? "" : receptor.getRuc()) + "','"
                                                                            + lGiro.getTOperacion().getTMoneda().getSimbolo() + "','"
                                                                            + fm.formatMoneda(valor) + "','"
                                                                            + fm.formatMoneda(valorComi) + "','"
                                                                            + lGiro.getPagadorComision() + "','"
                                                                            + (sinUsere ? "CLIENTE" : lGiro.getTPersona().getTCategoriaPersona().getDescripcion()) + "','"
                                                                            + (sinUserr ? "CLIENTE" : receptor.getTCategoriaPersona().getDescripcion()) + "','"
                                                                            + lGiro.getTFilial().getNombre()
                                                                            + "')\"  /></td>");
                                                                    out.println("<td style='font-size:10px' >" + lGiro.getFecha() + "</td>");
                                                                    out.println("<td style='font-size:10px' >" + (sinUserr ? "" + lGiro.getRecibidor().substring(0, lGiro.getRecibidor().indexOf("|")) : receptor.getNombre()) + " " + (sinUserr ? "" + lGiro.getRecibidor().substring(lGiro.getRecibidor().indexOf("|") + 1, lGiro.getRecibidor().length()) : receptor.getApellidos()) + (borrado ? lGiro.getIdUserPkDestino().substring(0, 8) : "") + "</td>");
                                                                    out.println("<td style='font-size:10px' >" + (sinUsere ? "" + lGiro.getGirador().substring(0, lGiro.getGirador().indexOf("|")) : lGiro.getTPersona().getNombre()) + " " + (sinUsere ? "" + lGiro.getGirador().substring(lGiro.getGirador().indexOf("|") + 1, lGiro.getGirador().length()) : lGiro.getTPersona().getApellidos()) + "</td>");
                                                                    out.println("<td style='font-size:10px' >" + lGiro.getTOperacion().getTPersonaCaja().getTCaja().getTFilial().getNombre() + "</td>");
                                                                    out.println("<td style='font-size:10px' ><center>" + lGiro.getTOperacion().getTMoneda().getSimbolo() + "</center> </td>");
                                                                    out.println("<td style='font-size:10px' >" + fm.formatMoneda(valor) + "</td>");
                                                                    out.println("<td style='font-size:10px' > " + lGiro.getEstado() + "</td>");
                                                                    out.println("</tr>");
                                                                    k++;
                                                                }
                                                    %>
                                                </table>
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                            </fieldset>
                        </form>
                        <form id="f1" name="f1" method="post" action="">
                            <table border="0" width="100%">
                                <tr>                                                                       
                                    <td width="45%">
                                        <fieldset>
                                            <legend><b>DATOS DEL RECEPTOR</b></legend>
                                            <table border='0' width='100%'>
                                                <tr>
                                                    <td align='right'>
                                                        <font color="#385B88" style="font-size:12px"><b>Categoria&nbsp;</b></font>
                                                    </td>
                                                    <td align='left' id="cateR">
                                                    </td>
                                                </tr>
                                                <tr style='display:none'>
                                                    <td align='right'>
                                                        <font color="#385B88" style="font-size:12px"><b>idpersona</b></font>
                                                    </td>
                                                    <td align='left'>
                                                        <input   id="Ridpersona" type='text' id="Ridpersona" />
                                                    </td>
                                                </tr>
                                                <tr id="Rtr_dni">
                                                    <td align='right'>
                                                        <font color="#385B88" style="font-size:12px"><b>D.N.I&nbsp;</b></font>
                                                    </td>
                                                    <td align='left'>
                                                        <input id='Rdni' type='text' name='Rdni' maxlength="8" readonly />
                                                    </td>
                                                </tr>
                                                <tr id="Rtr_ruc" style='display:none'>
                                                    <td align='right'>
                                                        <font color="#385B88" style="font-size:12px"><b>R.U.C&nbsp;</b></font>
                                                    </td>
                                                    <td align='left'>
                                                        <input id='Rruc' type='text' name='Rruc' maxlength="11" readonly />
                                                    </td>
                                                </tr>
                                                <tr id="Rtr_nombres">
                                                    <td align='right' id="Rtd_nombres">
                                                        <font color="#385B88" style="font-size:12px"><b> Nombres&nbsp;</b></font>
                                                    </td>
                                                    <td align='right' id="Rtd_razonsocial"  style='display:none'>
                                                        <font color="#385B88" style="font-size:12px"><b>Razon Social</b></font>
                                                    </td>
                                                    <td align='left'>
                                                        <div id="Rdetalle" style='display:none' ></div>
                                                        <input  id='Rnombres' type='text' name='Rnombres' maxlength="50" readonly/>
                                                    </td>
                                                </tr>
                                                <tr id="Rtr_apellidos">
                                                    <td align='right'>
                                                        <font color="#385B88" style="font-size:12px"><b>Apellidos&nbsp;</b></font>
                                                    </td>
                                                    <td align='left'>
                                                        <input  id='Rapellidos' type='text' name='Rapellidos' maxlength="50" readonly />
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td  colspan='2'>
                                                        <b>&nbsp;</b>
                                                    </td>
                                                </tr>
                                                <tr id="Rtr_email">
                                                    <td align='right'>
                                                        <font color="#385B88" style="font-size:12px"><b>Email&nbsp;</b></font>
                                                    </td>
                                                    <td align='left'>
                                                        <input id='Remail' type='text' name='Remail' readonly maxlength="50"/>
                                                    </td>
                                                </tr>
                                                <tr id="Rtr_ubigeo">
                                                    <td align='right'>
                                                        <font color="#385B88" style="font-size:12px"><b>Ubigeo&nbsp;</b></font>

                                                    </td>
                                                    <td align='left'>
                                                        <input id='Rubigeo' type='text' name='Rubigeo' maxlength="10" readonly/>
                                                    </td>
                                                </tr>
                                                <tr id="Rtr_telefono">
                                                    <td align='right'>
                                                        <font color="#385B88" style="font-size:12px"><b>Tel&eacute;fono&nbsp;</b></font>
                                                    </td>
                                                    <td align='left'>
                                                        <input id='Rtelefono' type='text' name='Rtelefono' maxlength="10" readonly />
                                                    </td>
                                                </tr>
                                                <tr id="Rtr_celular">
                                                    <td align='right'>
                                                        <font color="#385B88" style="font-size:12px"><b>Celular&nbsp;</b></font>
                                                    </td>
                                                    <td align='left'>
                                                        <input id='Rcelular' type='text' name='Rcelular' maxlength="12" readonly/>
                                                    </td>
                                                </tr>
                                                <tr id="Rtr_direccion">
                                                    <td align='right'>
                                                        <font color="#385B88" style="font-size:12px"><b>Direccion&nbsp;</b></font>
                                                    </td>
                                                    <td align='left'>
                                                        <input id='Rdireccion' type='text' name='Rdireccion' readonly maxlength="100"/>
                                                    </td>
                                                </tr>
                                            </table>
                                            <br>
                                        </fieldset>
                                    </td>
                                    <td width="10%">
                                        &nbsp;
                                    </td>
                                    <td width="45%" >
                                        <fieldset>
                                            <legend><b>DATOS DEL EMISOR</b></legend>
                                            <table border='0' width='100%'>
                                                <tr>
                                                    <td align='right'>
                                                        <font color="#385B88" style="font-size:12px"><b>Categoria&nbsp;</b></font>
                                                    </td>
                                                    <td align='left' id="cateE">
                                                    </td>
                                                </tr>
                                                <tr style='display:none'>
                                                    <td align='right'>
                                                        <font color="#385B88" style="font-size:12px"><b>idpersona</b></font>
                                                    </td>
                                                    <td align='left'>
                                                        <input id="Eidpersona" type='text'/>
                                                    </td>
                                                </tr>
                                                <tr id="Etr_dni">
                                                    <td align='right'>
                                                        <font color="#385B88" style="font-size:12px"><b>D.N.I&nbsp;</b></font>
                                                    </td>
                                                    <td align='left'>
                                                        <input id='Edni' type='text' name='Edni' maxlength="8" readonly />
                                                    </td>
                                                </tr>
                                                <tr id="Etr_ruc" style='display:none'>
                                                    <td align='right'>
                                                        <font color="#385B88" style="font-size:12px"><b>R.U.C&nbsp;</b></font>
                                                    </td>
                                                    <td align='left'>
                                                        <input id='Eruc' type='text' name='Eruc' maxlength="11" readonly/>
                                                    </td>
                                                </tr>
                                                <tr id="Etr_nombres">
                                                    <td align='right' id="Etd_nombres">
                                                        <font color="#385B88" style="font-size:12px"><b> Nombres&nbsp;</b></font>
                                                    </td>
                                                    <td align='right' id="Etd_razonsocial"  style='display:none'>
                                                        <font color="#385B88" style="font-size:12px"><b>Razon Social</b></font>
                                                    </td>
                                                    <td align='left'>
                                                        <div id="Edetalle" style='display:none' ></div>
                                                        <input id='Enombres' type='text' name='Enombres' maxlength="50" readonly />
                                                    </td>
                                                </tr>
                                                <tr id="Etr_apellidos">
                                                    <td align='right'>
                                                        <font color="#385B88" style="font-size:12px"><b>Apellidos&nbsp;</b></font>
                                                    </td>
                                                    <td align='left'>
                                                        <input  id='Eapellidos' type='text' name='Eapellidos' maxlength="50" readonly/>
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
                                                        <input id='Eemail' type='text' name='Eemail' maxlength="50" readonly/>
                                                    </td>
                                                </tr>
                                                <tr id="Etr_ubigeo">
                                                    <td align='right'>
                                                        <font color="#385B88" style="font-size:12px"><b>Ubigeo&nbsp;</b></font>
                                                    </td>
                                                    <td align='left'>
                                                        <input id='Eubigeo' type='text' name='Eubigeo' maxlength="10" readonly/>
                                                    </td>
                                                </tr>
                                                <tr id="Etr_telefono">
                                                    <td align='right'>
                                                        <font color="#385B88" style="font-size:12px"><b>Tel&eacute;fono&nbsp;</b></font>
                                                    </td>
                                                    <td align='left'>
                                                        <input id='Etelefono' type='text' name='Etelefono' maxlength="10" readonly />
                                                    </td>
                                                </tr>
                                                <tr id="Etr_celular">
                                                    <td align='right'>
                                                        <font color="#385B88" style="font-size:12px"><b>Celular&nbsp;</b></font>
                                                    </td>
                                                    <td align='left'>
                                                        <input id='Ecelular' type='text' name='Ecelular' maxlength="12" readonly/>
                                                    </td>
                                                </tr>
                                                <tr id="Etr_direccion">
                                                    <td align='right'>
                                                        <font color="#385B88" style="font-size:12px"><b>Direccion&nbsp;</b></font>
                                                    </td>
                                                    <td align='left'>
                                                        <input id='Edireccion' type='text' name='Edireccion' maxlength="100" readonly/>
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
                                <table  border="0"  cellpadding="10" cellspacing="10" width="100%">
                                    <tr>
                                        <td align="left">
                                            <font color="#385B88" style="font-size:12px"><b>CONDICION DEL EMISOR&nbsp;&nbsp;&nbsp;&nbsp;</b></font>
                                            <input  id='grupo' type='text' name='grupo'  style='color:red; font-weight:bold ;background:transparent' readonly='true' value="" size="10" />
                                        </td>
                                        <td>
                                            <table border="0">
                                                <tr>
                                                    <td align="right">
                                                        <font color="#385B88" style="font-size:12px"><b>RECEPCI&Oacute;N :</b></font>
                                                    </td>
                                                    <td style="font-size:12px;color:#E08934">
                                                        <b> <div id="filial"></div></b>
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="left">
                                            <table border="0" cellpadding="10" cellspacing="10" width="100%">
                                                <tr>
                                                    <td width="60%">
                                                        <font color="#385B88" style="font-size:12px">MONTO GIRADO &nbsp;&nbsp;&nbsp;&nbsp;</font>
                                                    </td>
                                                    <td id="giroSimbolo" style="font-size:20px;color:#E08934" width="10%">

                                                    </td>
                                                    <td id="giroImporte" style="font-size:20px;color:#E08934" >
                                                    </td>
                                                </tr>
                                                <tr id="tr_comi" style="visibility:hidden">
                                                    <td width="60%">
                                                        <font color="#385B88" style="font-size:12px">COBRAR COMISION <b>1.5%</b></font>
                                                    </td>
                                                    <td width="10%">
                                                        <div id="simbolo1" style="font-size:15px;color:#000000;font-weight: bold;"></div>
                                                    </td >
                                                    <td >
                                                        <div id="div_comision" style="font-size:15px;color:#000000;font-weight: bold;"></div>
                                                    </td>
                                                </tr>
                                                <tr id="tr_formap"  >
                                                    <td>
                                                        <font color="#385B88" style="font-size:12px"><b>EFECTIVO:</b></font>
                                                        <input type="radio" name="formap" id="formap1" value="EFECTIVO" checked   onclick="forma_pago()"/>
                                                    </td>
                                                    <td colspan="2">
                                                        <font color="#385B88" style="font-size:12px"><b>DESCONTAR:</b></font>
                                                        <input type="radio" name="formap" id="formap2" value="DESCONTAR" onclick="forma_pago()"/>
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td align="right">
                                            <table border="0"  cellspacing="5">
                                                <tr>
                                                    <td>
                                                        <font color="#385B88" style="font-size:12px"><b>PAGO </b></font>
                                                    </td>
                                                    <td id="simbolo2" style="font-size:20px;color:#018d00" >

                                                    </td>
                                                    <td>
                                                        <input id="pago" type="text" style="font-size:20px;width:140px;color:#018d00;background:transparent" readonly='true' name="giro_neto" value="" />
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <font color="#385B88" style="font-size:12px"><b>COBRO </b></font>
                                                    </td>
                                                    <td id="simbolo3" style="font-size:20px;color:#E08934">

                                                    </td>
                                                    <td>
                                                        <input id="cobro" type="text" style="font-size:20px;width:140px;color:#E08934;background:transparent" readonly='true' name="total" value="" />
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
                                            <input type="button" onclick="cobrogiro()" value="ENTREGA EFECTUADA" />
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
                                                String hql = "from TOperacion where TTipoOperacion.codigoTipoOperacion ='TIPC8' AND TPersonaCaja.idpersonacaja='" + session.getAttribute("USER_ID_PERSONA_CAJA") + "' AND estado='ACTIVO' and fecha like '" + DateUtil.getDate(new Date()) + "%' order by fecha desc";
                                                List resultOp = d.findAll(hql);
                                                Iterator itOp = resultOp.iterator();
                                                Double Monto = 0.00;
                                                String Tipo = "TIPC8";
                                                int kv = 0;
                                                while (itOp.hasNext()) {
                                                    TOperacion op = (TOperacion) itOp.next();
                                                    String hqli = "from TRegistroGiro where idoperacioncobro='" + op.getIdOperacion() + "'";
                                                    List resultRg = d.findAll(hqli);
                                                    Iterator itRg = resultRg.iterator();
                                                    while (itRg.hasNext()) {
                                                        TRegistroGiro opreg = (TRegistroGiro) itRg.next();
                                                        Monto = opreg.getImporte().doubleValue() + opreg.getComision().doubleValue();
                                                        if (kv % 2 == 0) {
                                                            out.println("<tr class='modo1'>");
                                                        } else {
                                                            out.println("<tr class='modo2'>");
                                                        }
                                    %>
                                    <td width="20%" style="font-size:10px;"><%=opreg.getFecha()%></td>
                                    <td width="20%" style="font-size:10px;"><%=opreg.getTOperacion().getNumeroOperacion()%></td>
                                    <td width="20%" style="font-size:10px;">COBRO <%=opreg.getTOperacion().getTTipoOperacion().getNombre()%></td>
                                    <td width="20%" style="font-size:10px;"><%=fm.formatMoneda(Monto)%></td>
                                    <td width="20%" style="font-size:10px;"><%=opreg.getTOperacion().getTMoneda().getNombre()%></td>
                                    <td width="19%" style="font-size:10px; text-align: right;"><input type="button" value="Extornar" onclick="extornar('<%=opreg.getIdregistro().toString()%>','<%=Tipo%>')" /></td>
                                        <%  out.println("</tr>");
                                                            kv++;
                                                        }
                                                    }
                                        %>
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