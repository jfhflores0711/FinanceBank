
<%-- 
    Document   : newjsp
    Created on : 11/02/2010, 04:41:24 PM
    Author     : eloy
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
        <script type="text/javascript" language="javascript" src="js/colorPicker.js"></script>
        <link rel="stylesheet" href="css/colorPicker.css" type="text/css" />
        <script type="text/javascript" language="javascript" src="js/validacion.js"></script>
        <script type="text/javascript" src="js/prototype.js"> </script>
        <script type="text/javascript" src="js/effects.js"> </script>
        <script type="text/javascript" src="js/window.js"> </script>
        <script type="text/javascript" src="js/debug.js"> </script>
        <link href="css/themes/default.css" rel="stylesheet" type="text/css"/>
        <link href="css/themes/alert.css" rel="stylesheet" type="text/css"/>
        <link href="css/themes/alphacube.css" rel="stylesheet" type="text/css"/>
        <style type="text/css">
            .tableItems {border: 1px solid #008585;border-spacing: 0px;border-collapse: collapse;}
            .thItems {background: #008585;font-family: Arial;font-size: 12pt;color: #FFFFFF;}
            .trItems {background: #FFFFFF;}
            .tdItems {border: 1px solid #008585;font-family: Arial;font-size: 10pt;}
            .trItemspar {background: #9BCFCF;}
            .tamanno1 {font-size: 15pt; font-family: arial,helvetica}
            .fieldset {width: 94%;border: solid gray 1px;background-color: #f7fff7;padding: 6px;display: block;}
        </style>
        <script type="text/javascript" src="js/scriptMath.js"></script>
        <script type="text/javascript" src="js/scriptConvertTest.js"></script>
        <script type="text/javascript" src="js/scriptshow/scriptCambios.js"></script>
    </head>
    <body onload="document.getElementById('compraM0').focus();">
        <%
                    request.setAttribute("idmenu", "20110228202911471839");
        %>
        <%@include file="../common/header.jsp" %>
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
                    <h2 class="title">Cuenta del Cliente</h2>
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
                    <h2 class="title">CAMBIO DE MONEDA</h2>
                    <h3 class="date"><span class="month"><script type="text/javascript">
                        var fecha=new Date();
                        var fmtmonthnamesshort=new Array("Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre");
                        document.write(fmtmonthnamesshort[fecha.getMonth()]);
                            </script></span> <span class="day"><script type="text/javascript">
                                document.write(fecha.getDate());
                            </script></span><span class="year">, <script type="text/javascript">document.write(fecha.getFullYear());</script></span></h3>
                    <div class="story">
                        <%
                                    List result = d.findAll("from TMoneda money where money.estado='ACTIVO' order by codParaNumCuenta");
                                    Iterator it = result.iterator();
                        %>
                        <div id="divdos">
                            <form name="fcambio1" action="">
                                <fieldset class="fieldset">
                                    <legend style="font-size:12px"><b><font color="#487388">ACTUALIZAR PRECIOS MONETARIOS</font></b> </legend>
                                    <br>
                                    <table id="tablamoneda" border="1" cellspacing="0" bordercolor="#3E8992">
                                        <tr>
                                            <td align="right" colspan="8">
                                                Eliminar Moneda <input type="checkbox" name="Eliminacion" id="Eliminacion"  value="ON" onchange="if(this.checked)ventanaCodigo();" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="display:none"><center><b>CODIGO MONEDA</b></center></td>
                                            <td><center><b><font color="#487388">MONEDA</font></b></center></td>
                                            <td><center><b><font color="#487388">COMPRA</font></b></center></td>
                                            <td><center><b><font color="#487388">VENTA</font></b></center></td>
                                            <td><center><b><font color="#487388">DESCRIPCI&Oacute;N</font></b></center></td>
                                            <td><center><b><font color="#487388">FECHA</font></b></center></td>
                                            <td><center><b><font color="#487388">HORA</font></b></center></td>
                                        </tr>
                                        <%
                                                    int u = 0;
                                                    while (it.hasNext()) {
                                                        TMoneda mon = (TMoneda) it.next();
                                                        if (!mon.getCodMoneda().equals("PEN")) {
                                        %>
                                        <tr valign="middle">
                                            <td style="display:none" id="codmoneda<%=u%>"><%=mon.getCodMoneda()%></td>
                                            <td><h1><font color="<%=mon.getColor()%>"><b><%=mon.getNombre()%></b></font></h1></td>
                                            <td><input class="tamanno1" id="compraM<%=u%>" name="compraM" type="text" value="" style="width:58px" onkeypress="return validar(event);" onchange="range = /^\d{1,3}(\.\d{1,3})?$/;
                                                hundred = /^1000$/;if(!(range.test(this.value) || hundred.test(this.value))){alert('La tasa de interes es invalida.'); };"/></td>
                                            <td><input class="tamanno1" id="ventaM<%=u%>" name="ventaM" type="text" value="" style="width:58px" onkeypress="return validar(event);" onchange="range = /^\d{1,3}(\.\d{1,3})?$/;
                                                hundred = /^1000$/;if(!(range.test(this.value) || hundred.test(this.value))){alert('La tasa de interes es invalida.'); };"/></td>
                                            <td><select id="tipoT<%=u%>" name="tipoT" >
                                                    <option>TASA MERCADO</option>
                                                    <option>TASA ESPECIAL</option>
                                                </select>
                                            </td>
                                            <%
                                                                                                        String unafecha = DateUtil.getDateTime("dd/MM/yyyy", new Date());
                                                                                                        String unahora = DateUtil.getDateTime("HH:mm:ss", new Date());
                                            %>
                                            <td><%=unafecha%></td>
                                            <td><%=unahora%></td>
                                            <td><input id="guardarTasa<%=u%>" name="guardarTasa<%=u%>"  type="button" value="GUARDAR" onclick="GuardarTasaMoneda('<%=u%>')"/></td>
                                            <td><input id="eliminarTasa<%=u%>" name="eliminarTasa<%=u%>" type="button" value="ELIMINAR" onclick="EliminarMoneda('<%=u%>')" disabled/></td>
                                        </tr>
                                        <%
                                                            u++;
                                                        }
                                                    }
                                        %>
                                    </table>
                                    <br>
                                </fieldset>
                            </form>
                            <br>
                            <br>
                            <fieldset>
                                <legend style="font-size:12px"><font color="#487388" ><b>INGRESE NUEVA MONEDA</b></font></legend>
                                <br>
                                <table>
                                    <tr>
                                        <td><font color="#1E551D">NOMBRE</font></td>
                                        <td><input  type="text" id="txtNombre" name="txtNombre" value="" /></td>
                                    </tr>
                                    <tr>
                                        <td><font color="#1E551D">C&Oacute;DIGO</font></td>
                                        <td><input type="text" id="txtCodigo" name="txtCodigo" value="" /></td>
                                    </tr>
                                    <tr>
                                        <td><font color="#1E551D">SIMBOLO</font><font color="#000000"><b>(&#)</b></font></td>
                                        <td><input type="text" id="txtSimbolo" name="txtSimbolo" value="" onkeypress="return solonumeros(event);"/></td>
                                    </tr>
                                    <tr>
                                        <td><font color="#1E551D">COLOR</font></td>
                                        <td><input type="text" id="txtColor" name="txtColor" value="" onclick='startColorPicker(this)' onkeyup='maskedHex(this)' /></td>
                                        <td><input type="button" id="btnguardarM" name="btnguardarM" value="GUARDAR" onclick="GuardarMoneda()" /></td>
                                    </tr>
                                </table>
                            </fieldset>
                            <%
                                        List resulta = d.findAll("from TMoneda money where money.estado='INACTIVO' order by codParaNumCuenta");
                                        Iterator ita = resulta.iterator();
                            %>
                            <br>
                            <br>
                            <fieldset>
                                <legend style="font-size:12px" ><b><font color="#487388">LISTA DE MONEDAS INABILITADAS</font></b></legend>
                                <table id="tablainferior" border="1" cellspacing="0">
                                    <tr>
                                        <td style="display:none"><center><b>CODIGO MONEDA</b></center></td>
                                        <td><center><b><font color="#487388">MONEDA</font></b></center></td>
                                        <td><center><b><font color="#487388">ESTADO</font></b></center></td>
                                    </tr>
                                    <%
                                                int ut = 0;
                                                while (ita.hasNext()) {
                                                    TMoneda mona = (TMoneda) ita.next();
                                                    if (!mona.getCodMoneda().equals("PEN")) {
                                    %>
                                    <tr>
                                        <td style="display:none" id="codmoneda2<%=ut%>"><%=mona.getCodMoneda()%></td>
                                        <td><%=mona.getNombre()%></td>
                                        <td><%=mona.getEstado()%></td>
                                        <td><input id="guardarT2" type="button" value="ACTIVAR" onclick="ActivarMoneda('<%=ut%>')"/></td>
                                    </tr>
                                    <%
                                                        ut++;
                                                    }
                                                }
                                    %>
                                </table>
                            </fieldset>
                        </div>
                        <%
                                    List resultd = d.findAll("from TMoneda money where money.estado='ACTIVO' order by codParaNumCuenta");
                                    Iterator itd = resultd.iterator();
                        %>
                        <br>
                        <br>
                        <fieldset>
                            <legend style="font-size:12px"><font color="#487388"><b>INGRESAR BILLETES Y MONEDAS</b></font> </legend>
                            <br>
                            <table>
                                <tr>
                                    <td><font color="#1E551D"> CODIGO MONEDA</font></td>
                                    <td>
                                        <select id="idCodigo" name="idCodigo">
                                            <%
                                                        int ud = 0;
                                                        while (itd.hasNext()) {
                                                            TMoneda monad = (TMoneda) itd.next();
                                            %>
                                            <option><%=monad.getCodMoneda()%></option>
                                            <%
                                                            ud++;
                                                        }
                                            %>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td><font color="#1E551D">TIPO</font></td>
                                    <td>
                                        <select id="idTipo" name="idtipo">
                                            <option>BILLETE</option>
                                            <option>MONEDA</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td><font color="#1E551D">MONTO</font></td>
                                    <td ><input id="idMonto" name="idMonto" type="text" name="" value="" onkeypress="return solodecimales(event);"/></td>
                                    <td><input  id="idGuardarD" type="button" value="GUARDAR" onclick="GuardarDenominacion()"/></td>
                                </tr>
                            </table>
                        </fieldset>
                        <br>
                        <br>
                        <div id="divsubirfichero">
                            <form id="frmsubirfile" name="frmsubirfile" method="POST" action="SUploadArchivo" enctype='multipart/form-data'>
                                <table style="display:none">
                                    <tr>
                                        <td colspan="2">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><font color="#385b88" style="font-size: 12px;"><b>BUSCAR IMAGEN </b></font></td>
                                        <td><input type="file" id="ExaminarFoto" name="ExaminarFoto" value="" maxlength="200" onfocus="enviarsessionimagen();" accept="image/jpg;image/jpeg;image/gif;image/png;image/bmp"/></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            <input id="cmdsubir" type="submit" value="Subir" disabled >
                                            <input type="button" id="cmdLimpiar" value="Limpiar" onclick="limpiarcelda();" disabled>
                                        </td>
                                    </tr>
                                </table>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="../common/footer.jsp" %>
    </body>
</html>
