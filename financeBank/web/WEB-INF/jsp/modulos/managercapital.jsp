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
        <title>FinanceBank admins kapital!</title>
        <meta name="keywords" content="FinanceBank" />
        <meta name="description" content="Sistema financiero" />
        <link href="default.css" rel="stylesheet" type="text/css" />
        <link href='css/tabla.css' rel='stylesheet' type='text/css' />
        <style type="text/css" media="print">
            .eebuttonbar_top {display:none;}
            .eebuttonbar_bottom {display:none;}
            #dh{display:none;}
            #footer{display: none;}
            #sidebar {display:none; width:0px;}
            #logo {display:none;}
            #h1{display: none;}
            #h2{display: none;}
            .conf{display:block;}
            #main{width: 950px; margin: -20px; font-size: 7pt;}
            #panel1{font-size: 7pt;}
            .tabla{font-size: 7pt;}
            .ee115{font-size: 7pt;}
            .modo1{font-size: 7pt;}
            .modo2{font-size: 7pt;}
        </style>
        <script type="text/javascript" src="js/scriptConvertTest.js"></script>
        <script type="text/javascript" src="js/scriptshow/scriptMCapital.js"></script>
        <script type="text/javascript" src="js/calendar/popcalendar.js"> </script>
    </head>
    <body>
        <%@include file="../common/header.jsp" %>
        <%
                    request.setAttribute("idmenu", "20110228202911451947");
        %>
        <div id="content">
            <div id="sidebar">
                <div id="menu">
                    <%@include file="../common/menu.jsp" %>
                </div>
                <div id="login" class="boxed">
                    <h2 class="title">Cuenta del Cliente</h2>
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
                    <h2 class="title">ADMIN CAPITAL!</h2>
                    <div class="story">
                        <form id="fpatrimonio" name="fpatrimonio" method="post" action="">
                            <fieldset>
                                <legend style="font-size:8px"><b>Moneda Seleccionada</b></legend>
                                <table border='0' cellpadding='5' cellspacing='5' class='tabla' width='100%'>
                                    <tr>
                                        <td>
                                            <font color="#385B88" style="font-size:12px"><b>MONEDA :&nbsp;&nbsp;&nbsp;</b</font>
                                            <select name="moneda" style="font-size:20px" onchange="document.fpatrimonio.submit()" >
                                                <%
                                                            String codigo = request.getParameter("moneda");
                                                            if (codigo == null) {
                                                                codigo = "PEN";
                                                            }
                                                            //String codFor = "1";
                                                            String SimboloMoneda = "";
                                                            List Presult = d.findAll("from TMoneda where estado ='ACTIVO'");
                                                            Iterator itPmoneda = Presult.iterator();
                                                            String sel = "";
                                                            while (itPmoneda.hasNext()) {
                                                                TMoneda moneda = (TMoneda) itPmoneda.next();
                                                                if (moneda.getCodMoneda().equals(codigo)) {
                                                                    sel = "selected";
                                                                    SimboloMoneda = moneda.getSimbolo();
                                                                    //codFor = moneda.getCodParaNumCuenta();
                                                                } else {
                                                                    sel = "";
                                                                }
                                                                out.println("<option label='" + moneda.getSimbolo() + "'  value='" + moneda.getCodMoneda() + "' " + sel + ">" + moneda.getNombre() + "(" + moneda.getSimbolo() + ")</option>");
                                                            }
                                                %>
                                            </select>
                                            <%
                                                        //iniPatrimonio.comprobarIni();
                                                        //String hql = "from TPatrimonio where estado ='ACTIVO" + session.getAttribute("USER_CODFILIAL").toString() + "'"
                                                        //       + " AND TBalancexmoneda.idbalance='" + DateUtil.getDate(new Date()).replaceAll("/", "") + "0" + codFor + "0000000000" + "'";
                                                        //String patrimonio = iniPatrimonio.patActual(codcaja, codigo);
                                                        //TPatrimonioTransit tpatrimonio = (TPatrimonioTransit) d.load(TPatrimonioTransit.class, patrimonio);
                                                        out.println(codigo);
                                                        out.print(codcaja);
                                                        TLogFinance logActual = (TLogFinance) adminCapital.instantLog(codcaja, codigo);
                                            %>
                                        </td><td style="font-size: smaller;font-style: italic;color: red;font-weight: bold;">ESTA INFORMACIÓN PUEDE VARIAR EN CUALQUIER INSTANTE. </td>
                                    </tr>
                                </table>
                            </fieldset>
                            <table border='0' cellpadding='5' cellspacing='5' width='100%' >
                                <tr>
                                    <td valign="top" width="50%">
                                        <fieldset>
                                            <legend style="font-size:15px"><b>ACTIVOS GENERAL</b></legend>
                                            <table border='0' cellpadding='5' cellspacing='5' class='tabla' width='100%'>
                                                <tr>
                                                    <td valign="top" width="50%">
                                                        <table border='0' cellpadding='5' cellspacing='5' class='tabla'  width='100%'>
                                                            <tr>
                                                                <td>
                                                                    <font color="#385B88" style="font-size:10px"><b>CAJA Y BANCOS GENERAL</b</font>
                                                                </td>
                                                                <td style="font-size:20px;color:#018d00;" width="10px">
                                                                    <%=SimboloMoneda%>
                                                                </td>
                                                                <td>
                                                                    <input id="cajaybancos" type="text"
                                                                           style="font-size:20px;width:180px;color:#018d00;background:transparent;text-align:right" readonly='true'
                                                                           name="cajaybancos" value="<%=fm.formatMoneda(logActual.getActivoCajaybanco().doubleValue())%>" />
                                                                </td>
                                                            </tr>
                                                            <tr><td colspan="3" align="right"><font color="#385B88" style="font-size:8px"><b>Activo Caja y banco (CyB)</b</font></td></tr>
                                                            <tr>
                                                                <td>
                                                                    <font color="#385B88" style="font-size:10px"><b>CUENTAS POR COBRAR GENERAL</b</font>
                                                                </td>
                                                                <td style="font-size:20px;color:#018d00;" width="10px">
                                                                    <%=SimboloMoneda%>
                                                                </td>
                                                                <td>
                                                                    <input id="cuentasporcobrar" type="text"
                                                                           style="font-size:20px;width:180px;color:#018d00;background:transparent;text-align:right" readonly='true'
                                                                           name="cuentasporcobrar" value="<%=fm.formatMoneda(logActual.getActivoCuentaxcobrar().doubleValue())%>" />
                                                                </td>
                                                            </tr>
                                                            <tr><td colspan="3" align="right"><font color="#385B88" style="font-size:8px"><b>Cuentas por Cobrar (CxC)</b</font></td></tr>
                                                            <tr>
                                                                <td>
                                                                    <font color="#385B88" style="font-size:12px"><b>CUENTAS POR PAGAR</b</font>
                                                                </td>
                                                                <td style="font-size:20px;color:#018d00;" width="10px">
                                                                    <%=SimboloMoneda%>
                                                                </td>
                                                                <td>
                                                                    <input id="pasivos1" type="text"
                                                                           style="font-size:20px;width:180px;color:#018d00;background:transparent;text-align:right" readonly='true'
                                                                           name="pasivos1" value="<%=fm.formatMoneda(logActual.getPasivo().doubleValue())%>" />
                                                                </td>
                                                            </tr>
                                                            <tr><td colspan="3" align="right"><font color="#385B88" style="font-size:8px"><b>Cuenta por pagar (CxP)</b</font></td></tr>
                                                            <tr>
                                                                <td>
                                                                    <font color="#385B88" style="font-size:10px"><b>TOTAL ACTIVOS</b</font>
                                                                </td>
                                                                <td style="font-size:20px;color:#E08934;" width="10px">
                                                                    <%=SimboloMoneda%>
                                                                </td>
                                                                <td>
                                                                    <input id="totalactivos" type="text"
                                                                           style="font-size:20px;width:180px;color:#E08934;background:transparent;text-align:right" readonly='true'
                                                                           name="totalactivos" value="<%=fm.formatMoneda(logActual.getActivoCajaybanco().doubleValue() + logActual.getActivoCuentaxcobrar().doubleValue() - logActual.getPasivo().doubleValue())%>" />
                                                                </td>
                                                            </tr>
                                                            <tr><td colspan="3" align="right"><font color="#385B88" style="font-size:8px"><b>ACTIVOS (CyB + CxC - CxP)</b</font></td></tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                            </table>
                                        </fieldset>
                                    </td>
                                    <td valign="top" width="50%">
                                        <fieldset>
                                            <legend style="font-size:15px"><b>CUENTAS POR COBRAR</b></legend>
                                            <table border='0' cellpadding='5' cellspacing='5' class='tabla' width='100%'>
                                                <tr>
                                                    <td>
                                                        <font color="#385B88" style="font-size:12px"><b>SOBREGIROS</b</font>
                                                    </td>
                                                    <td style="font-size:20px;color:#018d00;" width="10px">
                                                        <%=SimboloMoneda%>
                                                    </td>
                                                    <td>
                                                        <input id="pasivos2" type="text"
                                                               style="font-size:20px;width:180px;color:#018d00;background:transparent;text-align:right" readonly='true'
                                                               name="pasivos2" value="<%=fm.formatMoneda(logActual.getEncaje().doubleValue())%>" />
                                                    </td>
                                                </tr>
                                                <tr><td colspan="3" align="right"><font color="#385B88" style="font-size:8px"><b>Sobregiros (SG)</b</font></td></tr>
                                                <tr>
                                                    <td>
                                                        <font color="#385B88" style="font-size:12px"><b>PR&Eacute;STAMOS</b</font>
                                                    </td>
                                                    <td style="font-size:20px;color:#018d00;" width="10px">
                                                        <%=SimboloMoneda%>
                                                    </td>
                                                    <td>
                                                        <input id="pasivos3" type="text"
                                                               style="font-size:20px;width:180px;color:#018d00;background:transparent;text-align:right" readonly='true'
                                                               name="pasivos3" value="<%=fm.formatMoneda(logActual.getPRespaldo().doubleValue())%>" />
                                                    </td>
                                                </tr>
                                                <tr><td colspan="3" align="right"><font color="#385B88" style="font-size:8px"><b>Pr&eacute;stamos (PR)</b</font></td></tr>
                                                <tr>
                                                    <td>
                                                        <font color="#385B88" style="font-size:12px"><b>TOTAL</b</font>
                                                    </td>
                                                    <td style="font-size:20px;color:#E08934;" width="10px">
                                                        <%=SimboloMoneda%>
                                                    </td>
                                                    <td>
                                                        <input id="pasivos" type="text"
                                                               style="font-size:20px;width:180px;color:#E08934;background:transparent;text-align:right" readonly='true'
                                                               name="pasivos" value="<%=fm.formatMoneda(logActual.getEncaje().doubleValue() + logActual.getPRespaldo().doubleValue())%>" />
                                                    </td>
                                                </tr>
                                                <tr><td colspan="3" align="right"><font color="#385B88" style="font-size:8px"><b>Pasivos total (SG + PR)</b</font></td></tr>
                                            </table>
                                        </fieldset>
                                                    <p style="font-size: small">ACTIVO=PASIVO + PATRIMONIO + UTILIDADES</p>
                                        <p style="font-size: small"> ACTIVO = CAJAS + BANCOS + CUENTAS POR COBRAR</p>
                                        <p style="font-size: small">PASIVO = CUENTAS POR PAGAR + PRESTAMOS(incl. SOBREGIROS)</p>
                                    </td>
                                </tr>
                            </table>
                            <fieldset>
                                <legend style="font-size:8px"><b>Patrimonio</b></legend>
                                <table border='0' cellpadding='5' cellspacing='5' class='tabla' width='100%'>
                                    <tr>
                                        <td>
                                            <font color="#385B88" style="font-size:12px"><b>PATRIMONIO</b</font>
                                        </td>
                                        <td style="font-size:20px;color:#018d00;" width="10px">
                                            <%=SimboloMoneda%>
                                        </td>
                                        <td>
                                            <input id="patrimonio" type="text" style="font-size:20px;width:180px;color:#018d00;background:transparent;text-align:right" readonly='true' name="patrimonio" value="<%=fm.formatMoneda(logActual.getPatrimonio().doubleValue())%>" />
                                        </td>
                                    </tr>
                                    <tr><td colspan="3" align="right"><font color="#385B88" style="font-size:8px"><b>Patrimonio Inicial (Ki)</b</font></td></tr>
                                    <tr>
                                        <td>
                                            <font color="#385B88" style="font-size:12px"><b>PATRIMONIO ACTUAL EN EL FILIAL</b</font>
                                        </td>
                                        <td style="font-size:20px;color:#E08934;" width="10px">
                                            <%=SimboloMoneda%>
                                        </td>
                                        <td>
                                            <input id="patrimonio" type="text" style="font-size:20px;width:180px;color:#E08934;background:transparent;text-align:right" readonly='true' name="patrimonio" value="<%=fm.formatMoneda(logActual.getMonto().doubleValue())%>" />
                                        </td>
                                    </tr>
                                    <tr><td colspan="3" align="right"><font color="#385B88" style="font-size:8px"><b>Patrimonio Actual en el filial (Kf)</b</font></td></tr>
                                    <tr>
                                        <td>
                                            <font color="#385B88" style="font-size:12px"><b>INCREMENTAR PATRIMONIO</b</font>
                                        </td>
                                        <td style="font-size:20px;color:#000000;" width="10px">
                                            <%=SimboloMoneda%>
                                        </td>
                                        <td>
                                            <input id="addpatrimonio" type="text" style="font-size:20px;width:180px;text-align:right;" name="addpatrimonio" value="0.00"  onKeyPress="return(currencyFormat(this,',','.',event))"   />
                                        </td>
                                    </tr>
                                    <tr><td>&nbsp;</td><td>&nbsp;</td>
                                        <td>
                                            <input type="button" value="AGREGAR +" onclick="add();"/>
                                        </td>
                                    </tr>
                                </table>
                            </fieldset>
                            <center>
                                <table border='0' cellpadding='5' cellspacing='5' class='tabla' width='60%'>
                                    <tr><td valign="top" width="100%">&nbsp;</td></tr>
                                </table>
                            </center>
                            <center>
                                <table border="0" width="100%">
                                    <tr>
                                        <td colspan="2">
                                            <labeL>ACTIVOS TOTALES EN LOS FILIALES</labeL>
                                            <fieldset>
                                                <table border='0' cellpadding='10' cellspacing='10' class='tabla' width='100%'>
                                                    <tr>
                                                        <th>FILIAL</th><th>PAT. ACTUAL</th><th>&nbsp;</th><th>AGREGAR</th>
                                                    </tr>
                                                    <%
                                                                String r[] = adminCapital.patPorFilial(codFilial, codigo);
                                                                out.print(r[0]);
                                                    %>                                                    
                                                </table>
                                            </fieldset>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="5" align="center">
                                            <fieldset>
                                                <legend style="font-size:15px"><b>Monto Disponible</b></legend>
                                                <table border='0' cellpadding='5' cellspacing='5' class='tabla' width='100%'>
                                                    <tr>
                                                        <td>
                                                            <font color="#385B88" style="font-size:10px"><b>Efectivo Total Disponible</b></font><br>
                                                            <table border='0' cellpadding='0' cellspacing='0'>
                                                                <tr>
                                                                    <td style="font-size:15px;color:#018d00;font-weight:bold;" width="10px">
                                                                        <%=SimboloMoneda%>
                                                                    </td>
                                                                    <td>
                                                                        <input id="Edisponible" type="text" 
                                                                               style="font-size:15px;width:150px;color:#018d00;background:transparent;text-align:right;font-weight:bold;" readonly='true'
                                                                               name="Edisponible" value="<%=fm.formatMoneda(logActual.getActivoCajaybanco().doubleValue())%>" />
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                            <font style="font-size:10px">Caja y Bancos - Total Respaldo</font>
                                                        </td>
                                                        <td>
                                                            <font color="#385B88" style="font-size:10px"><b>No Distribuido</b></font><br>
                                                            <table border='0' cellpadding='0' cellspacing='0'>
                                                                <tr>
                                                                    <td style="font-size:15px;color:#018d00;font-weight:bold;" width="10px">
                                                                        <%=SimboloMoneda%>
                                                                    </td>
                                                                    <td>
                                                                        <input id="disponible" type="text" 
                                                                               style="font-size:15px;width:150px;color:#018d00;background:transparent;text-align:right;font-weight:bold;" readonly='true'
                                                                               name="disponible" value="<%=r[1]%>" />
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                            <font style="font-size:10px">Efectivo Total Disponible - Total Filial</font>
                                                        </td>
                                                        <td>
                                                            <font color="#385B88" style="font-size:10px"><b>Saldo</b></font><br>
                                                            <table border='0' cellpadding='0' cellspacing='0'>
                                                                <tr>
                                                                    <td style="font-size:15px;color:#E08934;font-weight:bold;" width="10px">
                                                                        <%=SimboloMoneda%>
                                                                    </td>
                                                                    <td>
                                                                        <input id="saldo" type="text" 
                                                                               style="font-size:15px;width:150px;color:#E08934;background:transparent;text-align:right;font-weight:bold;" readonly='true'
                                                                               name="saldo" value="<%=r[2]%>" />
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                            &nbsp;
                                                        </td>
                                                    </tr>
                                                </table>
                                            </fieldset>
                                        </td>
                                    </tr>
                                </table>
                            </center>
                        </form>
                        <fieldset>
                            <legend style="font-size:9px"><b>Administrar Utilidades</b></legend>
                            <table border='0' cellpadding='5' cellspacing='5' class='tabla' width='100%'>
                                <tr>
                                    <td width="50%">
                                        <table border='0' cellpadding='5' cellspacing='5' class='tabla' width='100%'>
                                            <tr>
                                                <td>
                                                    <font color="#385B88" style="font-size:12px"><b>UTILIDAD</b</font>
                                                </td>
                                                <td style="font-size:20px;color:#018d00;" width="10px">
                                                    NUEVOS SOLES
                                                </td>
                                                <td>
                                                    <input id="utilidad" type="text" 
                                                           style="font-size:20px;width:180px;color:#018d00;background:transparent;text-align:right;" readonly='true'
                                                           name="utilidad" value="<%=fm.formatMoneda(adminCapital.xCalcularUtilidad(codcaja))%>" />
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                    <td valign="top" width="50%">
                                        <table border='0' cellpadding='5' cellspacing='5' class='tabla' width='100%'>
                                            <tr>
                                                <td colspan="2" align="center">
                                                    <input type="text" value="<%=fm.formatMoneda(adminCapital.utilidad(codigo, codcaja))%>" />
                                                    <br><font style="font-size:8px"><b>Utilidad en esta moneda</b</font>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td colspan="2" align="center">
                                                    <form action="retirootro.htm" name="retirootro" id="retirootro">
                                                        <input type="submit" value="Retiro Utilidad" />
                                                        <br><font style="font-size:8px"><b>Se grabará la utilidad obtenida a la fecha</b</font>
                                                        <br>
                                                        <%
                                                                    //if (!adminCapital.estaBalanceado(codigo)) {
                                                                    //    out.print("<font style='font-size:8px;color:#FF0000;'><b>Los montos se han descuadrado! Que se verifique</b</font>");
                                                                    //}
                                                        %>
                                                    </form>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </table>
                        </fieldset>
                        <fieldset>
                            <legend style="font-size:9px"><b>VER ACCIONES DEL CAPITAL</b></legend>
                            <table border='0' cellpadding='5' cellspacing='5' id="h1" class='tabla' width='100%'>
                                <tr>
                                    <td width="50%">
                                        <table border='0' cellpadding='5' cellspacing='5' class='tabla' width='100%'>
                                            <tr>
                                                <td>
                                                    <font color="#385B88" style="font-size:10px"><b>VER CAMBIOS DE HOY DÍA</b</font>
                                                </td>
                                                <td style="font-size:16px;color:#018d00;" width="10px">
                                                    <%=DateUtil.getDateTime("dd/MM/yyyy", new Date())%>
                                                </td>
                                                <td>
                                                    <input id="report1" type="button" style="font-size:12px;width:180px;" name="report1" value="REPORTAR CAMBIOS" onclick="if (window.open) window.open('SReportAdmin?type=now','operacion','width=1200,height=840,menubar=yes,resizable=yes,scrollbars=yes,status=yes');else alert('Su Navegador no dispone de esta opcion, Actualicelo con una version mas reciente');"/>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                    <td valign="top" width="50%">
                                        <form action="" name="report2" id="report2">
                                            <table border='0' cellpadding='5' cellspacing='5' class='tabla' width='100%'>
                                                <tr>
                                                    <td colspan="2" align="center">
                                                        <font style="font-size:8px"><b>BUSCAR FECHA:</b</font>
                                                        <input id="hasta" type="text" value="<%=DateUtil.getDateTime("dd/MM/yyyy", new Date())%>" onclick="popUpCalendar(this, report2.hasta, 'dd/mm/yyyy');"/>
                                                        <br><input type="button" onclick="if (window.open)window.open('SReportAdmin?type=lease&conf=no&hasta='+document.getElementById('hasta').value,'operacion','width=1200,height=840,menubar=yes,resizable=yes,scrollbars=yes,status=yes');else alert('Su Navegador no dispone de esta opcion, Actualicelo con una version mas reciente');" value="REPORTAR" />
                                                        <br><font style="font-size:8px"><b>Desde 28/04/2011 en adelante</b</font>
                                                    </td>
                                                </tr>
                                            </table>
                                        </form>
                                    </td>
                                </tr>
                            </table>
                        </fieldset>
                        <fieldset>
                            <legend style="font-size:9px"><b>IMPRIMIR LA VISTA</b></legend>
                            <table border='0' cellpadding='5' cellspacing='5' id="h2" class='tabla' width='100%'>
                                <tr>
                                    <td width="100%">
                                        <table border='0' cellpadding='5' cellspacing='5' class='tabla' width='100%'>
                                            <tr>
                                                <td>
                                                    <font color="#385B88" style="font-size:10px"><b>CAPITAL TOTAL</b</font>
                                                </td>
                                                <td style="font-size:16px;color:#018d00;" width="10px">
                                                    <%=DateUtil.getDateTime("dd/MM/yyyy", new Date())%>
                                                </td>
                                                <td>
                                                    <input id="report3" type="button" style="font-size:12px;width:180px;" name="report3" value="IMPRIMIR DATA" onclick="window.print();"/>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>                                    
                                </tr>
                            </table>
                        </fieldset>
                    </div>
                    <div id="divesperarres" style="display: none"></div>
                    <div id="divshowre"></div>

                </div>
            </div><div id="divr" style="display: none"></div>
        </div>
        <%@include file="../common/footer.jsp" %>
        <script type="text/javascript">
            document.getElementById('addpatrimonio').focus();
        </script>
    </body>
</html>