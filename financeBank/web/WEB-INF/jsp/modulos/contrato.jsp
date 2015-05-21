<%-- 
    Document   : contrato
    Created on : 12-mar-2010, 15:45:33
    Author     : oscar
--%>

<%@page session="true" import="org.finance.bank.model.*,java.util.*, java.math.*,org.finance.bank.bean.*,
        org.finance.bank.model.dao.*,
        org.finance.bank.util.*,
        java.io.*,
        java.net.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
    <head><link rel="shortcut icon" href="./images/favicon.ico" />
        <%
                    StringBuffer contratoLeido = null;
                    String tmp = null;
                    TContrato tcontrato = null;
                    tmp = request.getParameter("contrato_postback");
                    String idcontrato = null;
                    String mensaje = "";
                    DAOGeneral d = new DAOGeneral();
                    if (session.isNew()) {
                        mensaje = "Error de petición";
                        out.write("<meta http-equiv='REFRESH' content='0;principal.htm?mensaje=" + URLEncoder.encode(mensaje, "UTF-8") + "' />");
                        response.sendRedirect("principal.htm?mensaje=" + URLEncoder.encode(mensaje, "UTF-8"));
                    } else if (session.getAttribute("SESSION") != null && !session.getAttribute("SESSION").equals("false")) {
                        /* not first time *//* it wasn't a reset */
                        String city = ((TFilial) d.load(TFilial.class, session.getAttribute("USER_CODFILIAL").toString())).getTDistrito().getDescripcion();
                        idcontrato = request.getParameter("contratoGen");
                        if (tmp != null) {
                            FileManager fm = new FileManager();
                            if (idcontrato != null) {
                                StringBuffer sb = new StringBuffer();
                                tcontrato = (TContrato) d.load(TContrato.class, idcontrato);
                                if ("nonurl".equals(tcontrato.getDescripcion())) {
                                    TPersona tpersona = tcontrato.getTRegistroPrestamo().getTCuentaPersona().getTPersona();
                                    String mo = tcontrato.getTRegistroPrestamo().getTCuentaPersona().getTMoneda().getNombre();
                                    sb.append(""
                                            + "<h2>CONTRATO DE PRÉSTAMO:&nbsp;</h2>"
                                            + "<p>&nbsp;En la ciudad de " + city + " a " + DateUtil.getDateTime("dd 'de' MMMM 'del' yyyy", new Date()) + ". <br></p>"
                                            + "<h2>REUNIDOS<br></h2>"
                                            + "<p>De una parte la Casa de Cambios Ventura, constituida en la ciudad de Ayacucho, con domicilio en El Portal CONSTITUCI&Oacute;N Nro. 7 y con RUC Nº 10282431845, en adelante parte prestamista. <br></p>"
                                            + "<p>&nbsp;Y de otra parte Don(a) " + tpersona.getNombre().toUpperCase() + " " + tpersona.getApellidos().toUpperCase() + ", mayor de edad, vecino de la ciudad de " + city + ", con domicilio en " + tpersona.getDireccion().toUpperCase() + " y con D.N.I. Nro. " + tpersona.getDocIdentidad() + ", en adelante prestatario.<br></p>"
                                            + "<h2>COMPARECEN <br></h2><p>Ambos en su propio nombre y representación y reconociéndose mutuamente capacidad suficiente para el otorgamiento de este contrato y en su virtud: <br></p>"
                                            + "<h2>MANIFIESTAN <br></h2>"
                                            + "<p>Que la casa de Cambios Ventura entrega en este acto a Don(a) " + tpersona.getNombre().toUpperCase() + " " + tpersona.getApellidos().toUpperCase() + "., que lo recibe, la cantidad de " + NumberUtil.convertirLetrasDecimal(tcontrato.getTRegistroPrestamo().getMonto().doubleValue()) + " (" + tcontrato.getTRegistroPrestamo().getMonto().toString() + ") " + ("NUEVO SOL" == mo ? "NUEVOS SOLES" : ("DOLAR" == mo ? "D&Oacute;LARES AMERICANOS" : ("EURO" == mo ? "EUROS" : mo))) + ", en metálico, en concepto de préstamo, con sujeción a las condiciones convenidas en el presente documento. <br></p>"
                                            + "<h2>ESTIPULACIONES<br></h2>"
                                            + "<p>&nbsp;Primera: El capital prestado devengará hasta el momento de su devolución, y sin necesidad de requerimiento alguno, el interés del " + DateUtil.String_valueOf(tcontrato.getTRegistroPrestamo().getTasaInteresPrestamo().doubleValue()).replace(",", "") + "% anual(TEA). De igual modo, los intereses no pagados a sus respectivos vencimientos devengarán, sin necesidad de requerimiento alguno, el interés anteriormente convenido hasta el momento de su abono. <br></p>"
                                            + "<p>Segunda: El capital del préstamo será revisado el día de hoy en cada uno de los a&ntilde;os futuros y hasta la fecha de su pago total. Para tal efecto se fija como único módulo el índice general de precios al consumo se&ntilde;alado por el Instituto Nacional de Estadística e Informática u Organismo similar que pueda sustituirle. La prueba del índice vigente será la publicación oficial o el certificado del Organismo correspondiente. <br></p>"
                                            + "<p>Tercera: El plazo será de " + String.valueOf(tcontrato.getTRegistroPrestamo().getNumeroCuotas()) + " meses a contar desde la fecha de suscripción del presente documento. De común acuerdo, las partes podrán prorrogar el plazo de vencimiento. Los intereses se abonarán anticipadamente. <br></p>"
                                            + "<p>Cuarta: El deudor podrá reembolsar el capital prestado antes de su vencimiento; como asimismo entregar cantidades a cuenta, siempre que estuviere al corriente de los intereses que hubiere y que la entrega total no fuere inferior al 100% del capital prestado. No podrá exigir los intereses que hubiere satisfecho y las cantidades entregadas a cuenta del principal dejarán de devengar interés. <br></p>"
                                            + "<p>Quinta: A requerimiento del acreedor, el deudor se obliga a prestar las garantías personales o reales que aquél tuviere por conveniente para asegurar la devolución de lo debido. De igual modo se compromete a que cualquier otra deuda que contrajere no goce de prioridad alguna sobre el presente crédito. <br></p>"
                                            + "<p>Sexta: En cualquiera de los casos en que el deudor incumpliere algunas de las obligaciones que le competen según lo dispuesto en el presente documento, el acreedor podrá resolver el contrato antes del vencimiento del plazo para el pago del capital e intereses, siempre que previamente requiera al deudor al cumplimiento de sus obligaciones; y transcurridos 5 días del citado requerimiento sin que aquél atendiere el contenido del mismo, quedará de pleno derecho resuelto el contrato. <br></p>"
                                            + "<p>Séptima: Los gastos e impuestos que se devenguen por el presente contrato serán de cuenta y cargo del deudor. <br></p>"
                                            + "<p>Octava: Los comparecientes se someten a la jurisdicción de los Juzgados y Tribunales de esta capital, con renuncia expresa al fuero propio, para el ejercicio de las acciones derivadas de este contrato. Y para que así conste y surta los efectos oportunos, firman los comparecientes el presente contrato en duplicado ejemplar, en el lugar y fecha del encabezamiento. <br></p>"
                                            + "<h2><br></h2>"
                                            + "<table align='center' style='width: 800px; height: 75px;'><tr><td><h2>_____________<br>EL PRESTAMISTA<br>INVERSIONES VENTURA</h2></td><td><h2>______________<br>EL PRESTATARIO<br>" + tpersona.getNombre().toUpperCase() + " " + tpersona.getApellidos().toUpperCase() + "<br></h2></td></tr></table>"
                                            + "");
                                    String pathF = FileUtil.getUniqueFolderId() + ".htm";
                                    String url = getServletContext().getRealPath("/") + "resources/files/" + pathF;
                                    File contrato = new File(url);
                                    if (contrato.getParentFile() != null) {
                                        contrato.getParentFile().mkdirs();
                                    }
                                    fm.saveFile(contrato.getAbsolutePath(), sb.toString(), false);
                                    //String contratoCreado = fm.replaceValues(contrato.getAbsolutePath(), busqueda, reemplazo);
                                    //fm.saveFile(contrato.getAbsolutePath(), contratoCreado, false);
                                    contratoLeido = fm.readFile(contrato.getAbsolutePath());
                                    tcontrato.setDescripcion(contrato.getAbsolutePath());
                                    tcontrato.setEstado("ACTIVO");
                                    d.update();
                                } else if (request.getParameter("descripCont") != null) {
                                    String xMContrato = request.getParameter("descripCont");
                                    //String xNumContrato = request.getParameter("contrato_postback");
                                    File xcontrato = new File(tcontrato.getDescripcion());
                                    contratoLeido = sb.append(URLDecoder.decode(xMContrato, "UTF-8"));
                                    fm.saveFile(xcontrato.getAbsolutePath(), sb.toString(), false);
                                } else {
                                    mensaje = "Ya existe un contrato generado, cerrar este módulo.";
                                    response.sendRedirect("passwordHint.htm?mensaje=" + URLEncoder.encode(mensaje, "UTF-8"));
                                }
                            } else {
                                mensaje = "Alerta, contacte con el Administrador del sistema, cerrar este módulo.";
                                response.sendRedirect("passwordHint.htm?mensaje=" + URLEncoder.encode(mensaje, "UTF-8"));
                            }
                        } else {
                            mensaje = "La página del contrato no estaba en la petición, cerrar este módulo.";
                            response.sendRedirect("passwordHint.htm?mensaje=" + URLEncoder.encode(mensaje, "UTF-8"));
                        }
                    } else {
                        mensaje = "Error de petición";
                        out.write("<meta http-equiv='REFRESH' content='0;principal.htm?mensaje=" + URLEncoder.encode(mensaje, "UTF8") + "' />");
                        response.sendRedirect("principal.htm?mensaje=" + URLEncoder.encode(mensaje, "UTF-8"));
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
        <style type="text/css" media="All">
            #myInstance1 {
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
                font-size:10pt;
            }
            h2 {
                font-size:12pt;
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
            #footer{
                display: none;
            }
        </style>
        <script type="text/javascript" src="js/contrato.js">
        </script>
    </head>
    <body onload="preguntar();">
        <div id="content">
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
                        <p>Casa de cambios Ventura</p>
                    </div>
                    <div class="story">
                        <div>
                            <p style="color:purple">El contrato una vez impreso se vuelve irreversible, siendo necesario una revocatoria o expiración de la misma</p>
                        </div>
                        <div id="sample">
                            <script src="js/nicEdit.js" type="text/javascript"></script>
                            <script type="text/javascript">
                                bkLib.onDomLoaded(function() {
                                    var myNicEditor = new nicEditor();
                                    myNicEditor.setPanel('myNicPanel');
                                    myNicEditor.addInstance('myInstance1');
                                });
                            </script>
                            <span id="nonUseEdition">Use la paleta para su edición</span>
                            <div id="myNicPanel" style="width: 850px;"></div>
                            <br />
                            <center><h4>ANEXO: Hoja Resumen del Contrato de Crédito</h4></center>
                            <div contenteditable="true" lang="ES" class="                   " id="myInstance1" style="padding: 3px; font-family: 'Arial,sans-serif'; font-size: 7.5pt; background-color: rgb(254, 254, 224); width: 850px;">
                                <%if (contratoLeido != null) {%>
                                <%=contratoLeido.toString()%>
                                <% }%>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="contratos" id="contratos">
                    <blockquote>
                        <blockquote>
                            <blockquote>
                                <blockquote>
                                    <blockquote>
                                        <form name="formContrato" method="post" action="#">
                                            <textarea id="contrato" rows="6" cols="140" name="descripCont" lang=ES style='font-size:7.5pt;font-family:"Arial","sans-serif";display:none'>
                                            </textarea>
                                            <%if (tcontrato != null) {%>
                                            <input type="hidden" name="contratoGen" value="<%=tcontrato.getIdcontrato()%>">
                                            Contrato de Préstamo nº
                                            <input maxlength="4" id="numContratoNow" name="contrato_postback" type="text" value="<%=tcontrato.getNumContrato()%>" readonly>
                                            <% }%>
                                            <input name="ImprimirGuardar" type="button" onclick="imprimeContrato();" value="ImprimirGuardar">
                                            <input name="Imprimir" type="button" onClick="window.print();" value="Imprimir" id="pr">
                                            <input type="button" id="Guardar" name="abrirContrato" value="Guardar Contrato" onclick="guardar();">
                                        </form>
                                        <p><input type=button value="Cerrar" onclick="cerrarse();"> </p>
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
