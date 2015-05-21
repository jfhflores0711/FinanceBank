<%--
   Document   : adminCaja.jsp
   Created on : 28/10/2011, 02:41:24 PM
   Author     : oscar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8" />
        <link rel="shortcut icon" href="./images/favicon.ico">
        <title>FinanceBank adminCaja</title>
        <meta name="keywords" content="FinanceBank" />
        <meta name="description" content="Sistema financiero" />
        <link href="default.css" rel="stylesheet" type="text/css" />
        <link href='css/tabla.css' rel='stylesheet' type='text/css' />
        <script type="text/javascript" src="js/validacion.js"> </script>
        <script type="text/javascript" src="js/prototype.js"> </script>
        <script type="text/javascript" src="js/effects.js"> </script>
        <script type="text/javascript" src="js/window.js"> </script>
        <script type="text/javascript" src="js/debug.js"> </script>
        <link href="css/themes/default.css" rel="stylesheet" type="text/css"/>
        <link href="css/themes/alert.css" rel="stylesheet" type="text/css"/>
        <link href="css/themes/alphacube.css" rel="stylesheet" type="text/css"/>
        <style type="text/css" media="print">
            #sidebar{display: none;}
            #logo{display: none;}
            .story{display:none;}
            #frmOperaciones{display: none;}
            #main{width:940px;}
            #Submit{display: none;}
            #btnRegresar{display: none;}
            #footer{display: none;}
            #divOperacionf{height:auto;overflow: visible;position: absolute;top: 0;}
        </style>
        <script type="text/javascript" src="js/scriptConvertTest.js"></script>
        <script type="text/javascript" src="js/scriptshow/scriptAdmCaja.js"></script>
    </head>
    <body onload="document.getElementById('montoTranferir0').focus();">
        <%@include file="../common/header.jsp" %>
        <%
                    request.setAttribute("idmenu", "20111028142911505556");
        %>
        <div id="content">
            <div id="sidebar">
                <div id="menu">
                    <%@include file="../common/menu.jsp" %>
                </div>
                <div id="xlogin" class="boxed">
                    <h2 class="title">Cuenta del Cajero</h2>
                    <div class="content">
                        <form id="form1" method="post" action="logout.htm">
                            <fieldset>
                                <input id="inputsubmit1" type="submit" name="inputsubmit1" value="Salir" />
                            </fieldset>
                        </form>
                    </div>
                </div>
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
            </div>
            <div id="main">
                <div id="welcome" class="post">
                    <h2 class="title">ADMINISTRACION DE CAJA</h2>
                    <div class="story" style="width:800px;"><form id="foper" name="foper" method="post" action="">
                            <fieldset>
                                <legend style="font-size:8px"><b>Operaciones Entre Cajas</b></legend>
                                <table border='0' cellpadding='5' cellspacing='5' class='tabla'  width='100%'>
                                    <tr>
                                        <th style='display:none'>&nbsp;</th><th>MONEDA</th>
                                        <th>SIMBOLO</th>
                                        <th>MONTO ACTUAL</th>
                                        <th>MONTO</th>
                                        <th>SALDO</th>
                                        <th>CAJA</th>
                                        <th>&nbsp;</th>
                                    </tr>
                                    <%
                                                //iniDetalleCaja.comprobarIni(session.getAttribute("USER_CODCAJA").toString(), session);
                                                String hqlm = "from TMoneda where estado ='ACTIVO'";
                                                List result = d.findAll(hqlm);
                                                Iterator MONE_DC = result.iterator();
                                                int j2 = 0;
                                                String cajasx = "";
                                                List res = d.findAll("from TCaja where estado ='ACTIVO' "
                                                        + " AND TFilial.codFilial ='" + session.getAttribute("USER_CODFILIAL").toString() + "' order by codCaja");
                                                Iterator in = res.iterator();
                                                while (in.hasNext()) {
                                                    TCaja caja3 = (TCaja) in.next();
                                                    if (!session.getAttribute("USER_CODCAJA").toString().equals(caja3.getCodCaja())) {
                                                        cajasx += "<option value='" + caja3.getCodCaja() + "' >" + caja3.getNombreCaja() + "</option>";
                                                    }
                                                }
                                                while (MONE_DC.hasNext()) {
                                                    TMoneda mo = (TMoneda) MONE_DC.next();
                                                    //TDetalleCaja detCaja = iniDetalleCaja.detalleActivaCaja(session.getAttribute("USER_CODCAJA").toString(), mo.getCodMoneda(), DateUtil.getDate(new Date()));
                                                    TLogFinance detCaja = (TLogFinance) d.load(TLogFinance.class, "LOG" + session.getAttribute("USER_CODCAJA").toString() + mo.getCodMoneda());
                                                    //TMoneda Moneda = mo;
                                                    if (j2 % 2 == 0) {
                                    %>
                                    <tr class='modo1' style="color:<%=mo.getColor() %>;">
                                        <%} else {%>
                                    <tr class='modo2' style="color:<%=mo.getColor() %>;">
                                        <%}%>
                                        <td id="CodigoMoneda<%=j2%>" style='display:none;' >
                                            <%= mo.getCodMoneda()%>
                                        </td>
                                        <td>
                                            <%= mo.getNombre()%>
                                        </td>
                                        <td style='font-size:20px;' >
                                            <center>
                                                <%= mo.getSimbolo()%>
                                            </center>
                                        </td>
                                        <td  id="montoActual<%=j2%>" style='text-align:right;'>
                                            <%=detCaja.getMontoFinal()%>
                                        </td>
                                        <td>
                                            <input id="montoTranferir<%=j2%>" type="text" name="" value='0.00'
                                                   onKeyPress="return(currencyFormat(this,',','.',event));"
                                                   style='font-size:20px;width:150px;text-align:right;'
                                                   onkeyup="actualizarM('<%=j2%>')" />
                                        </td>
                                        <td id="montoSaldo<%=j2%>" style='text-align:right;' >
                                            <%=detCaja.getMontoFinal()%>
                                        </td>
                                        <td>
                                            <select id="Caja<%=j2%>" name="Caja<%=j2%>">
                                                <%=cajasx%>
                                            </select>
                                        </td>
                                        <td>
                                            <input type="button" value="TRANSFERIR" onclick="TransferirMonto(<%=j2%>)"/>
                                        </td>
                                        <%
                                                        j2++;
                                                    }
                                                    out.println("<script language='JavaScript'>");
                                                    out.print("formaterMontos(" + j2 + ")");
                                                    out.println("</script>");
                                        %>
                                    </tr>
                                </table>
                            </fieldset>
                        </form>
                        <%  if (session.getAttribute("USER_TIPOCAJA").toString().equals("PRIMARY")) {%>
                        <fieldset>
                            <legend style="font-size:8px"><b>LISTA DE TODOS LOS CAJEROS</b></legend>
                            <table border='0' cellpadding='5' cellspacing='5' class='tabla'  width='100%'>
                                <tr>
                                    <th>COD. FILIAL</th>
                                    <th>OFICINA</th>
                                    <th>COD. CAJA</th>
                                    <th>NOMBRES DEL CAJERO</th>
                                    <th>LOGIN</th>
                                </tr>
                                <%
                                      List resultc = d.findAll("from TPersonaCaja where estado='ACTIVO' order by TCaja.codCaja");
                                      Iterator MONE = resultc.iterator();
                                      int j = 0;
                                      while (MONE.hasNext()) {
                                          TPersonaCaja perCaja = (TPersonaCaja) MONE.next();
                                          if (j % 2 == 0) {
                                %>
                                <tr class='modo1'>
                                    <%                                                                                            } else {%>
                                <tr class='modo2'>
                                    <%                                                                                            }%>
                                    <td>
                                        <%= perCaja.getTCaja().getTFilial().getCodFilial()%>
                                    </td>
                                    <td>
                                        <%= perCaja.getTCaja().getTFilial().getNombre()%>
                                    </td>
                                    <td style='font-size:14px;' >
                                        <center>
                                            <%= perCaja.getTCaja().getCodCaja()%>
                                        </center>
                                    </td>
                                    <td>
                                        <%=(perCaja.getTPersona().getNombre() + " " + perCaja.getTPersona().getApellidos())%>
                                    </td>
                                    <td style='text-align:right;' >
                                        <%=((TCuentaAcceso) perCaja.getTPersona().getTCuentaAccesos().iterator().next()).getLogin()%>
                                    </td>
                                    <%
                                              j++;
                                          }
                                    %>
                                </tr>
                            </table>
                        </fieldset>                        
                        <%
                                    }
                        %>
                    </div>
                    <div id="misoper">
                        <form id="misOperaciones" method="post" action="">
                            <table id="xxoper">
                                <tr>
                                    <td>
                                        <input type="button" onclick="if (window.open)window.open('ticketcuenta.htm?tipoOperacion=TODO','operacion','width=800,height=840,menubar=yes,resizable=yes,scrollbars=yes,status=yes');else alert('Su Navegador no dispone de esta opcion, Actualicelo con una version mas reciente');" value="REPORTAR OPERACIONES..." />
                                    </td>
                                    <td style="<%= (codcaja.endsWith("001") ? "" : "display: none;")%>"><input type="button" onclick="if (window.open)window.open('admincajarep.htm?caja=<%=codcaja%>','operacion','width=800,height=840,menubar=yes,resizable=yes,scrollbars=yes,status=yes');else alert('Su Navegador no dispone de esta opcion, Actualicelo con una version mas reciente');" value="REPORTAR CAJAS ..." /></td>
                                </tr>
                            </table>
                        </form>
                    </div>
                    <div>
                        <form id="frmOperaciones" method="post" action="">
                            <table id="">
                                <tr>
                                    <td><fieldset style="border-width: 3px;">
                                            <legend>Buscar Por: </legend>
                                            <table>
                                                <tr>
                                                    <td></td>
                                                    <td>Escriba N&uacute;mero de Operaci&oacute;n:</td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td><select id="selTipoOperacion">
                                                            <option>TODO</option>
                                                            <option>CAJA</option>
                                                            <option>COMPRA</option>
                                                            <option>VENTA</option>
                                                            <option>DEPOSITO O RETIRO</option>
                                                            <option>GIRO</option>
                                                            <option>COBROGIRO</option>
                                                            <option>PRESTAMO</option>
                                                            <option>COBRANZA</option>
                                                            <option>RETIRO OTRO</option>
                                                        </select>
                                                    </td>
                                                    <td><input type="text" id="txtNumOperacion" name="txtNumOperacion" onkeypress="return solonumeros(event);" value=""></td>
                                                    <td><input type="button" id="btnuscar" onclick="buscarOperacione();" value="Buscar Operaci&oacute;n"></td>
                                                    <td></td>
                                                </tr>
                                            </table>
                                        </fieldset>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <div id="divOperacion">
                                            <table id="tablaOperacion" class="tabla" border="0" style="display:none"></table>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div id="divExtorno" style="display: none"></div>
        <%@include file="../common/footer.jsp" %>
    </body>
</html>
