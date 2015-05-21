<%--
    Document   : anexoplazofijo
    Created on : 13/04/2010, 05:09:05 PM
    Author     : roger
--%>

<%@page session="true" import="org.finance.bank.model.*,
        java.util.*,
        java.math.*,
        org.finance.bank.bean.*,
        org.finance.bank.model.dao.*,
        org.finance.bank.util.*,
        java.io.*,
        java.net.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%
                    StringBuffer anexoLeido = null;
                    String tmp = null;
                    TCertificado tanexo = null;
                    tmp = request.getParameter("anexo_postback");
                    String idanexo = null;
                    String mensaje = "";
                    Map ticket;
                    ticket = (Map) session.getAttribute("ticket");
                    DAOGeneral d= new DAOGeneral();
                    if (session.isNew()) {
                        mensaje = "Error de petición";
                        out.write("<meta http-equiv='REFRESH' content='0;principal.htm?mensaje=" + URLEncoder.encode(mensaje, "UTF8") + "' />");
                        response.sendRedirect("principal.htm?mensaje=" + URLEncoder.encode(mensaje, "UTF8"));
                    } else if (session.getAttribute("SESSION") != null && !session.getAttribute("SESSION").equals("false")) {
                        /* not first time *//* it wasn't a reset */
                        idanexo = request.getParameter("anexoplazofijoGen");
                        if (tmp != null) {
                            FileManager fm = new FileManager();
                            if (idanexo != null) {
                                StringBuffer sb = new StringBuffer();
                                tanexo = (TCertificado) d.load(TCertificado.class, idanexo);
                                if ("nonurl".equals(tanexo.getDescripcion())) {

                                    sb.append(""
                                            + "<center><h1><u>ANEXO</u></h1></center><br>"
                                            + "<center><h1><u>CARTILLA DE INFORMACION</u></h1></center><br>"
                                            + "<center><h1><u>PLAZO FIJO - CLASICO</u></h1></center><br>"
                                            + "<p>El presente documento forma parte integrante de las condiciones aplicables al Contrato de Operacion Pasivas, suscritos por las "
                                            + "partes, tiene por finalidad cumplir con la normatividad del marco legal del Decreto Legislativo N&deg; 716 ley de Protecci&oacute;n al "
                                            + "Consumidor, la Ley N&deg; 28587 - Ley Complementaria en materia de servicios financieros y Resoluci&oacute;n SBS N&deg; 1765-2005 Reglamento "
                                            + "de Transparencia de Informaci&oacute;n y Disposiciones Aplicables a la Contrataci&oacute;n con Usuarios del Sistema Financiero.<br>"
                                            + "</p><table>"
                                            + "<tr>"
                                            + "<td>Nombre: " + ticket.get("APELLIDOS") + ", " + ticket.get("NOMBRE") + "</td><td>DNI: " + (ticket.get("DNI") == null ? "" : ticket.get("DNI")) + (ticket.get("RUC") == null ? "" : "/RUC:" + ticket.get("RUC")) + "</td>"
                                            + "</tr>"
                                            + "<tr>"
                                            + "<td>Agencia</td><td>" + ticket.get("FILIAL") + "</td>"
                                            + "</tr>"
                                            + "<tr>"
                                            + "<td>Modalidad de Cuenta</td><td>INDIVIDUAL</td>"
                                            + "</tr>"
                                            + "<tr>"
                                            + "<td>Tasa de Inter&eacute;s Compensatoria Efectiva Anual Fija</td><td>" + ticket.get("INTERES") + "% Expresada a 360 días</td>"
                                            + "</tr>"
                                            + "<tr>"
                                            + "<td>Inter&eacute;s a se Pagado:</td><td>(Al finalizar el periodo, Sin retiro de intereses)</td>"
                                            + "</tr>"
                                            + "<tr>"
                                            + "<td>fecha de Corte para el abono de intereses</td><td>Al final del plazo pactado</td>"
                                            + "</tr>"
                                            + "<tr>"
                                            + "<td>Fecha de Vencimiento</td><td>" + ticket.get("FECHAPF") + "</td>"
                                            + "</tr>"
                                            + "<tr>"
                                            + "<td>Retiro de Intereses</td><td>LIBRE</td>"
                                            + "</tr>"
                                            + "<tr>"
                                            + "<td>Frecuencia de Capitalizaci&oacute;n</td><td>Diaria</td>"
                                            + "</tr>"
                                            + "<tr>"
                                            + "<td>Gastos y comisiones aplicables</td><td>De acuerdo al Tarifario Vigente</td>"
                                            + "</tr>"
                                            + "</table><br>"
                                            + "<b>DATOS DE LA APERTURA</b>"
                                            + "<table>"
                                            + "<tr>"
                                            + "<td>Monto Apertura y Moneda </td><td>" + ticket.get("IMPORTE") + "  " + ticket.get("MONEDA") + "</td>"
                                            + "</tr>"
                                            + "<tr>"
                                            + "<td>Total Saldo</td><td>" + ticket.get("SALDO") + "  " + ticket.get("MONEDA") + "</td>"
                                            + "</tr>"
                                            + "<tr>"
                                            + "<td>Plazo</td><td>" + ticket.get("NUMERODIASPF") + "</td>"
                                            + "</tr>"
                                            + "<tr>"
                                            + "<td>Fhecha de Apertura</td><td>" + DateUtil.getDateTime("dd 'de' MMMM 'del' yyyy", new Date()) + "</td>"
                                            + "</tr>"
                                            + "</table>"
                                            + "<br>"
                                            + "<b>INFORMACION ADICIONAL<b><br>"
                                            + "<ul>"
                                            + "<li>Dep&oacute;sito cubierto por el Fondo de Depósitos, establecido por el Banco Central de Reserva del per&uacute; hasta "
                                            + "S/. 82,037.00</li>"
                                            + "<li>Una vez concluido el Plazo pactado y al no recibir instrucciones de <b>EL CLIENTE, LA CASA<b> renovar&aacute; autom&aacute;ticamente el "
                                            + "dep&oacute;sito al plazo inicial y con la tasa de tarifario vigente.</li><br>"
                                            + "<li>A efectos de la plena conformidad de <b>EL CLIENTE</b> respecto a sus operaciones y saldo(s) de su(s) cuenta(s) aperturada(s) en <b>LA</b> "
                                            + "<b>CASA, EL CLIENTE</b> asume la obligaci&oacute;n de verificar peri&oacute;dicamente el saldo de su(s) cuenta(s), quien podr&aacute; utilizar los medios "
                                            + "puestos a su dispocici&oacute;n por <b>LA CASA</b> como son: Oficinas o Agencias de Atenci&oacute;n, P&aacute;gina Web o Medios Electr&oacute;nicos; con la "
                                            + "finalidad de acceder a la informaci&oacute;n descrita anteriormente. Asimismo <b>EL CLIENTE</b> podr&aacute; instruir a la <b>CASA</b>, remitir a su "
                                            + "domicilio la documentaci&oacute;n correspondiente, previa solicitud suscrita para la finalidad y con costos establecidos en nuestro "
                                            + "tarifario vigente.</li>"
                                            + "<li>La asignaci&oacute;n de tasa especiales s&oacute;lo tienen una vigencia de 365 d&iacute;as posterior a ello el plazo fijo se renovar&aacute; al plazo inicial y "
                                            + "con la tasa efectiva del tarifario vigente.</li>"
                                            + "</ul>"
                                            + "<b>PENALIDADES EN CASO DE INCUMPLIMIENTO DEL PLAZO PACTADO</b>"
                                            + "<ul>"
                                            + "<li>Al momento de cancelar se le recalculará con la tasa de inter&eacute;s de ahorro corriente, si el inter&eacute;s se pag&oacute; por adelantado el monto "
                                            + "ser&aacute; descontado del capital al momento de la cancelaci&oacute;n.</li>"
                                            + "</ul>"
                                            + "<b>DECLARACION FINAL DEL CLIENTE:</b> Declaro haber le&iacute;do previamente las condiciones establecidas en el Contrato de Dep&oacute;sito y la "
                                            + "Cartilla de Informaci&oacute;n, as&iacute; como haber sido instruido acerca de los alcances y significado de los t&eacute;rminos y condiciones "
                                            + "establecidos en dicho documento habiendo sido absueltas y aclaradas a mi satisfacci&oacute;n todas las consultas efectuadas y/o dudas, "
                                            + "suscribo el presente documento en duplicado y con pleno y exacto conocimiento de los mismos.<br>"
                                            + "ESTABLECIDO POR EL BANCO CENTRAL DE RESERVA DEL PERU HASTA 82,073.00 <br>"
                                            + "LAS PER. JUR. SIN FINES DE LUCRO SON CUBIERTAS POR EL FONDO SEGURO DE DEPOSITOS <br>"
                                            + "<br><b>Anexo INTRANSFERIBLE</b><br><br>"
                                            + "                                                                   AYACUCHO, " + DateUtil.getDateTime("dd 'de' MMMM 'del' yyyy", new Date()) + "<br>"
                                            + "<br>"
                                            + "<br>"
                                            + "<br>"
                                            + "<br>"
                                            + "<table>"
                                            + "<tr>"
                                            + "<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;_________________&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;__________________ </td>"
                                            + "</tr>"
                                            + "<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;CLIENTE&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;    </td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;LA CASA </td>"
                                            + "</tr>"
                                            + "</table>"
                                            + "");
                                    String pathF = FileUtil.getUniqueFolderId() + ".htm";
                                    String url = getServletContext().getRealPath("/") + "resources/anexos/" + pathF;
                                    File anexo = new File(url);
                                    if (anexo.getParentFile() != null) {
                                        anexo.getParentFile().mkdirs();
                                    }
                                    fm.saveFile(anexo.getAbsolutePath(), sb.toString(), false);
                                    //String contratoCreado = fm.replaceValues(contrato.getAbsolutePath(), busqueda, reemplazo);
                                    //fm.saveFile(contrato.getAbsolutePath(), contratoCreado, false);
                                    anexoLeido = fm.readFile(anexo.getAbsolutePath());
                                    tanexo.setDescripcion(anexo.getAbsolutePath());
                                    tanexo.setEstado("ACTIVO");
                                    d.update();
                                } else if (request.getParameter("descripCont") != null) {
                                    String xMAnexo = request.getParameter("descripCont");
                                    //String xNumContrato = request.getParameter("contrato_postback");
                                    File xanexo = new File(tanexo.getDescripcion());
                                    anexoLeido = sb.append(URLDecoder.decode(xMAnexo, "UTF8"));
                                    fm.saveFile(xanexo.getAbsolutePath(), sb.toString(), false);
                                } else {
                                    mensaje = "Ya existe un anexo generado, cerrar este módulo.";
                                    response.sendRedirect("menu.htm?mensaje=" + URLEncoder.encode(mensaje, "UTF8"));
                                }
                            } else {
                                mensaje = "Alerta, contacte con el Administrador del sistema, cerrar este módulo.";
                                response.sendRedirect("menu.htm?mensaje=" + URLEncoder.encode(mensaje, "UTF8"));
                            }
                        } else {
                            mensaje = "La página del anexo no estaba en la petición, cerrar este módulo.";
                            response.sendRedirect("menu.htm?mensaje=" + URLEncoder.encode(mensaje, "UTF8"));
                        }
                    } else {
                        mensaje = "Error de petición";
                        out.write("<meta http-equiv='REFRESH' content='0;principal.htm?mensaje=" + URLEncoder.encode(mensaje, "UTF8") + "' />");
                        response.sendRedirect("principal.htm?mensaje=" + URLEncoder.encode(mensaje, "UTF8"));
                        if (session.getAttributeNames().hasMoreElements()) {
                            session.invalidate();
                        }
                    }
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>FinanceBank</title>
        <meta name="keywords" content="FinanceBank" />
        <meta name="description" content="Sistema financiero" />
        <link href="default.css" rel="stylesheet" type="text/css" media="All" />
        <link rel="shortcut icon" href="./images/favicon.ico" />
        <style type="text/css" media="All">
            #myInstance2 {
                border: 2px dashed #0000ff;
            }
            .nicEdit-selected {
                border: 2px solid #0000ff !important;
            }
            .nicEdit-panel {
                background-color: #fff !important;
            }
            .nicEdit-button {
                background-color: #fff !important;
            }
            #main{
                float:left;
            }
            p{
                color: #000099;
                font-size:12pt;
            }
            h2 {
                font-size:14pt;
            }
        </style>
        <style type="text/css" media="print">
            #myNicPanel{
                display:none;
            }
            #nonUseEdition{
                display:none;
            }
            #contratos{
                display:none;
            }
            #Imprimir{
                display:none;
            }
            #Guardar{
                display:none;
            }
            #Cerrar{
                display:none;
            }
            #divfooter{
                display:none;
            }
            #mes{
                display:none;
            }
            #dia{
                display:none;
            }
            #intro{
                display:none;
            }
        </style>
        <script type="text/javascript" src="js/anexo.js">
        </script>
    </head>
    <body>
        <div id="content">
            <div id="main">
                <div id="welcome" class="post">
                    <img id="imgLogo" width="129" height="100" alt="logo01" src="images/logo04.png"> CASA DE CAMBIOS VENTURA
                    <h3 class="date"><span id="mes" class="month"><script type="text/javascript">
                        var fecha=new Date();
                        var fmtmonthnamesshort=new Array("Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre");
                        document.write(fmtmonthnamesshort[fecha.getMonth()]);
                            </script></span> <span id="mes" class="day"><script type="text/javascript">
                                document.write(fecha.getDate());
                            </script></span><span class="year">, <script type="text/javascript">document.write(fecha.getFullYear());</script></span></h3>
                    <div class="meta">
                    </div>
                    <div class="story">
                        <div id="sample">
                            <script src="js/nicEdit.js" type="text/javascript"></script>
                            <script type="text/javascript">
                                bkLib.onDomLoaded(function() {
                                    var myNicEditor = new nicEditor();
                                    myNicEditor.setPanel('myNicPanel');
                                    myNicEditor.addInstance('myInstance2');
                                });
                            </script>
                            <span id="nonUseEdition">Use la paleta para su edición</span>
                            <div id="myNicPanel" style="width: 850px;"></div>
                            <br />
                            <h4>Contrato Nº 1</h4>
                            <div contenteditable="true" lang="ES" class="                   " id="myInstance2" style="padding: 3px; font-family: 'Arial,sans-serif'; font-size: 12px; background-color: rgb(254, 254, 224); width: 855px;">
                                <%if (anexoLeido != null) {%>
                                <%=anexoLeido.toString()%>
                                <% }%>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="anexos" id="anexos">
                    <blockquote>
                        <blockquote>
                            <blockquote>
                                <blockquote>
                                    <blockquote>
                                        <form name="formAnexo" method="post" action="#">
                                            <textarea id="anexo" rows="6" cols="140" name="descripCont" lang=ES style='font-size:7.5pt;font-family:"Arial","sans-serif";display:none'>
                                            </textarea>
                                            <%if (tanexo != null) {%>
                                            <input type="hidden" name="anexoplazofijoGen" value="<%=tanexo.getIdcertificado()%>">

                                            <input maxlength="4" id="numAnexoNow" name="anexo_postback" type="hidden" value="<%=tanexo.getNumCertificado()%>" readonly>
                                            <% }%>
                                            <input id="Imprimir" name="Imprimir" type="button" onClick="imprimeAnexo();" value="Imprimir">
                                            <input type="button" id="Guardar" name="abrirContrato" value="Guardar Contrato" onclick="guardarrr();">
                                        </form>
                                        <p><input id="Cerrar" type=button value="Cerrar" onclick="cerrar()"> </p>
                                    </blockquote>
                                </blockquote>
                            </blockquote>
                        </blockquote>
                    </blockquote>
                    <p>&nbsp;</p>
                </div>
            </div>
        </div>
        <div id="divfooter">
            <%@include file="../common/footer.jsp" %>
        </div>
        <noscript>The browser does not support JavaScript. The calculations created using financeBank will not work. Please access the web page using another browser.<p></p></noscript>
    </body>
</html>


