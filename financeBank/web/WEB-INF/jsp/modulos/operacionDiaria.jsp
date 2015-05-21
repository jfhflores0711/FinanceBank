<%-- 
    Document   : newjsp
    Created on : 11/02/2010, 04:41:24 PM
    Author     : Oscar
--%>
<%@page session="true" import="org.finance.bank.util.*,org.finance.bank.model.*,java.util.*, java.math.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="shortcut icon" href="./images/favicon.ico" />
        <title><%=(DateUtil.getDateTime("yyyymmddhhmmssS", new Date()))%>|financeBank! </title>
        <meta name="keywords" content="FinanceBank" />
        <meta name="description" content="Sistema financiero" />
        <link href="default.css" rel="stylesheet" type="text/css" />
        <link href="css/francesGral.css" rel="stylesheet" type="text/css" media="All">
        <link href="css/francesScreen.css" rel="stylesheet" type="text/css" media="screen">
        <link href="css/francesPrint.css" rel="stylesheet" type="text/css" media="print">
        <script type="text/javascript">
            //Script original de KarlanKas para forosdelweb.com
            if (history.forward(1)){location.replace(history.forward(1))}
        </script>
        <script type="text/javascript" src="js/scriptConvertTest.js"></script>
        <script type="text/javascript" src="js/scriptshow/scriptOperacionDiaria.js"></script>
        <style type="text/css">
            #content{
                width:1100px;
            }
            #sidebar{
                width:200px;
            }
            #main{
                width:900px;
            }
        </style>
    </head><!-- Header end -->
    <body>
        <%@include file="../common/header.jsp" %>
        <div id="content">
            <div id="sidebar">
                <div id="menu">
                    <%@include file="../common/menu.jsp" %>
                </div>
                <div id="login" class="boxed">
                    <h2 class="title">Cuenta del Cliente</h2>
                    <div class="content">
                        <form id="form1" method="post" action="principal.htm">
                            <fieldset><legend>&nbsp;</legend>
                                <input id="inputsubmit1" type="submit" name="inputsubmit1" value="Salir" />
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
            <div id="main">
                <div id="welcome" class="post">
                    <h2 class="title">Cuadro de Amortizacion financeBank</h2>
                    <h3 class="date"><span class="month"><script type="text/javascript">
                        var fecha=new Date();
                        var fmtmonthnamesshort=new Array("Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre");
                        document.write(fmtmonthnamesshort[fecha.getMonth()]);
                            </script></span> <span class="day"><script type="text/javascript">
                                document.write(fecha.getDate());
                            </script></span><span class="year">, <script type="text/javascript">document.write(fecha.getFullYear());</script></span></h3>
                    <div class="meta">
                        <p>Casa de cambios || INVERSIONES VENTURA</p>
                    </div>
                    <div class="story">
                        <form id="formc" name="formc" method="post" action=""> <div style="display:none">
                                <input name="xl_postback" id="xl_postback" type="hidden" value="1">
                            </div>
                            <div class="eebuttonbar_top">
                                <input class="eebuttons" type="button" value="Imprimir" name="xl_print_top" onclick="window.print();">
                            </div>
                            <div>
                                <table border="0">
                                    <tr>
                                        <td colspan="2" class="ee106">CRONOGRAMA DE PAGOS, CON PERIODO DE <=periodo%> DÍAS</td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" class="ee130">TASA DE INTERÉS COMPENSATORIA EFECTIVA ANUAL A 360 DÍAS: <%/*=DateUtil.eedisplayPercentND(Math.pow(1 + calc.getXLEW_1_3_2() / 12, 12) - 1.0D, 2)*/%> </td>
                                    </tr>
                                </table>
                            </div>
                            <hr>
                            <div id="panel1" style='display:<%--= (tmp==null) ? "block" : "none"--%>'>
                                <table style='border-collapse:collapse' border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF">
                                    <col width="78.00" /><col width="130.00" /><col width="120.00" /><col width="115.00" /><col width="135.00" /><col width="244.00" /><col width="1.00" />
                                    <tr style='height:17pt'>
                                        <td class='ee100'>Desembolso:</td>
                                        <td class='ee100'>
                                            &nbsp;
                                        </td>
                                    </tr>
                                    <tr style='height:13pt'>
                                        <td class='ee100'>
                                            Plazo :
                                        </td>
                                        <td class='ee100'>&nbsp;</td>
                                    </tr>
                                    <tr style='height:14pt'>
                                        <td class='ee100'>
                                            TEA:
                                        </td>
                                        <td class='ee100'>&nbsp;</td>
                                    </tr>
                                </table><table>
                                    <tr style='height:14pt'>
                                        <td class='ee115'>
                                            Mes cuota
                                        </td>
                                        <td class='ee118'>
                                            Cuota Mensual
                                        </td>
                                        <td class='ee115'>
                                            Intereses
                                        </td>
                                        <td class='ee115'>
                                            Amortizacion
                                        </td>
                                        <td class='ee115'>
                                            Capital Pendiente
                                        </td>
                                        <td class='ee115'>
                                            Vencimiento:
                                        </td>
                                        <td class='ee100'>
                                            &nbsp;
                                        </td>
                                    </tr>
                                </table></div><br><hr>
                            <div class="cl">
                                <p>AMIGO CLIENTE, UD. PUEDE CANCELAR SUS CUOTAS EN CUALQUIERA DE NUESTRAS OFICINAS, PARA LO CUAL DEBE TENER LA CONSIDERACIÓN DEL COBRO DE UNA COMISIÓN, QUE ES DE 1.5% DEL MONTO APROBADO AL MOMENTO DEL DESEMBOLSO.</p>
                                <p style="font-weight:bold">SI UD. TUVIERA ALGÚN RECLAMO Y/O DENUNCIA SOBRE NUESTROS SERVICIOS, REALÍCELO EN PRIMERA INSTANCIA A TRAVÉS DE NUESTRAS OFICINAS DE ATENCIÓN AL CLIENTE Y/O NUESTRA PÁGINA WEB. LA SBS E
                                    INDECOPI, REPRESENTAN LA SEGUNDA INSTANCIA DE RESOLUCIÓN DE RECLAMOS AL QUE PUEDA RECURRIR.</p></div>
                            <div class="eebuttonbar_bottom">
                                <input class="eebuttons" type="button" value="Imprimir" name="xl_print_bottom" onclick="window.print();">
                                <input class="eebuttons" type="button" value="Imprimir Comprobante" name="xl_bottom" onclick="printDes();">
                                <span style="margin-left:1px;"> Disfrute el préstamo! </span>
                            </div>
                        </form>
                    </div>
                </div>
                <div id="conContrato">
                    <blockquote>
                        <blockquote>
                            <blockquote>
                                <blockquote>
                                    <blockquote>
                                        <form name="form1" method="post" action="">
                                            <input type="checkbox" id="sinContrato" name="sinContrato" value="" onclick="contratos();" >
                                            <p id="habilitar">Habilitar contrato.</p><p id="inhabilitar" style="display:none;">Inhabilitar contrato.</p><br />
                                            <span>
                                                <input type="button" id="contrato" name="abrirContrato" value="Contrato" disabled onclick="abrirVentanac();">
                                                <input name="cerrarContrato" id="contrato2" type="button" disabled onClick="cerrarVentanac();this.disabled=true" value="Cerrar Contrato">
                                            </span>
                                            <input type="hidden" id="contratoGen" name="contratoGen" value="<=tcontrato.getIdcontrato()%>">
                                        </form>
                                        <p>&nbsp;</p>
                                    </blockquote>
                                </blockquote>
                            </blockquote>
                        </blockquote>
                    </blockquote>
                    <p>&nbsp;</p>
                </div>
            </div>
        </div>
        <%@include file="../common/footer.jsp" %>
        <noscript>The browser does not support JavaScript. The calculations created using financeBank will not work. Please access the web page using another browser.<p></p></noscript>
    </body>
</html>
