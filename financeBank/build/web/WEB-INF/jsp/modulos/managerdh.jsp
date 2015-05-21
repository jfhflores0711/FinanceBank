<%-- 
    Document   : managerdh.jsp
    Created on : 07-abr-2010, 9:37:57
    Author     : oscar
--%>

<%@page session="true" import="org.finance.bank.util.*,org.finance.bank.model.*,java.util.*, java.math.*,java.io.*,org.apache.poi.hssf.usermodel.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="shortcut icon" href="./images/favicon.ico" />
        <title>FinanceBank</title>
        <meta name="keywords" content="FinanceBank" />
        <meta name="description" content="Sistema financiero" />
        <link href="default.css" rel="stylesheet" type="text/css" />
        <link href="css/francesGral.css" rel="stylesheet" type="text/css" media="All">
        <link href="css/tabla.css" rel="stylesheet" type="text/css" media="screen">
        <link href="css/francesScreen.css" rel="stylesheet" type="text/css" media="screen">
        <link href="css/francesPrint.css" rel="stylesheet" type="text/css" media="print">
        <style type="text/css" media="print">
            .eebuttonbar_top {display:none;}
            .eebuttonbar_bottom {display:none;}
            #dh{display:none;}
            #footer{display: none;}
            #sidebar {display:none;width:0px;}
            #logo {display:none;}
            .conf{display:block;}
            #main{width: 1050px;margin: -20px;font-size: 7pt;}
            #panel1{font-size: 7pt;}
            .tabla{font-size: 7pt;}
            .ee115{font-size: 7pt;}
            .modo1{font-size: 7pt;}
            .modo2{font-size: 7pt;}
        </style>
        <script type="text/javascript">
            //Script original de KarlanKas para forosdelweb.com
            if (history.forward(1)){location.replace(history.forward(1))}
        </script>
        <script type="text/javascript" src="js/scriptConvertTest.js"></script>
        <script type="text/javascript" src="js/scriptshow/scriptMDebeHaber.js"></script>
        <%
                    boolean au = false;
                    String tmp = request.getParameter("postback");
                    String submited = request.getParameter("submit_bottom");
                    if (session.getAttributeNames().hasMoreElements()) {
                        if (tmp != null && submited != null) {
                            au = true;
                        }
                    } else {
                        if (response.isCommitted()) {
                            out.println("<script language='JavaScript' type='text/javascript'>var pagina='" + request.getContextPath() + "/principal.htm?mensaje=Inicie+una+nueva+sesion.'; "
                                    + "function redireccionar() {location.href=pagina;} setTimeout ('redireccionar()', 50);</script>");
                        } else {
                            response.sendRedirect("principal.htm?mensaje=Inicie+una+nueva+sesion.");
                        }
                        if (session.getAttributeNames().hasMoreElements()) {
                            session.invalidate();
                        }
                    }
        %>
        <style type="text/css" media="screen">
            #content{width:1201px;}
            #sidebar{width:200px;}
            #main{width:1000px;}
        </style>
    </head><!-- Header end -->
    <body onload="document.getElementById('debehaber').focus();">
        <%
                    request.setAttribute("idmenu", "20110228202911466912");
        %>
        <%@include file="../common/header.jsp" %>
        <%
                    String y = "";
                    ArrayList a = new ArrayList();
                    ArrayList b = new ArrayList();
                    Map c = new HashMap();
                    Map e = new HashMap();
                    Map g = new HashMap();
                    if (au) {
                        int in = 0;
                        List f = d.findAll("from TMoneda where estado='ACTIVO' order by codMoneda");
                        for (Iterator it = f.iterator(); it.hasNext();) {
                            TMoneda h = (TMoneda) it.next();
                            c.put("" + in, BigDecimal.ZERO);
                            e.put("" + in, BigDecimal.ZERO);
                            g.put("" + in++, h);
                        }
                        if (!g.isEmpty()) {
                            Set h = new HashSet();
                            h.add("p.TCategoriaPersona.descripcion='NATURAL' order by p.apellidos, p.nombre");
                            h.add("p.TCategoriaPersona.descripcion='JURIDICA' order by p.nombre");
                            for (Iterator k = h.iterator(); k.hasNext();) {
                                String i = (String) k.next();
                                List j = d.findAll("from TPersona p where p.estado='ACTIVO' and " + i);
                                for (Iterator l = j.iterator(); l.hasNext();) {
                                    TPersona m = (TPersona) l.next();
                                    Set n = m.getTCuentaPersonas();
                                    Map q1 = new HashMap();
                                    Map q2 = new HashMap();
                                    for (Iterator o = n.iterator(); o.hasNext();) {
                                        TCuentaPersona p = (TCuentaPersona) o.next();
                                        if (!"INACTIVO".equals(p.getEstado()) && p.getSaldoSinInteres().doubleValue() != 0.0D) {
                                            if ((q1.isEmpty() && p.getSaldoSinInteres().doubleValue() > 0.0D) || (q2.isEmpty() && p.getSaldoSinInteres().doubleValue() < 0.0D)) {
                                                if (p.getSaldoSinInteres().doubleValue() > 0.0D) {
                                                    q1.put("n", "" + ("NATURAL".equals(m.getTCategoriaPersona().getDescripcion()) ? "" + m.getApellidos() + ", " + m.getNombre() : "" + m.getNombre()));
                                                } else {
                                                    q2.put("n", "" + ("NATURAL".equals(m.getTCategoriaPersona().getDescripcion()) ? "" + m.getApellidos() + ", " + m.getNombre() : "" + m.getNombre()));
                                                }
                                                for (int r = 0; r < g.size(); r++) {
                                                    TMoneda s = (TMoneda) g.get(r + "");
                                                    if (p.getSaldoSinInteres().doubleValue() > 0.0D) {
                                                        if (p.getTMoneda().getCodMoneda().equals(s.getCodMoneda())) {
                                                            q1.put("" + r, p.getSaldoSinInteres());
                                                            c.put("" + r, ((BigDecimal) c.get("" + r)).add(p.getSaldoSinInteres()));
                                                        } else {
                                                            q1.put("" + r, BigDecimal.ZERO);
                                                        }
                                                    } else {
                                                        if (p.getTMoneda().getCodMoneda().equals(s.getCodMoneda())) {
                                                            q2.put("" + r, p.getSaldoSinInteres());
                                                            e.put("" + r, ((BigDecimal) e.get("" + r)).add(p.getSaldoSinInteres()));
                                                        } else {
                                                            q2.put("" + r, BigDecimal.ZERO);
                                                        }
                                                    }
                                                }
                                            } else {
                                                for (int r = 0; r < g.size(); r++) {
                                                    TMoneda s = (TMoneda) g.get(r + "");
                                                    if (p.getTMoneda().getCodMoneda().equals(s.getCodMoneda())) {
                                                        if (p.getSaldoSinInteres().doubleValue() > 0.0D) {
                                                            q1.put("" + r, ((BigDecimal) q1.get("" + r)).add(p.getSaldoSinInteres()));
                                                            c.put("" + r, ((BigDecimal) c.get("" + r)).add(p.getSaldoSinInteres()));
                                                        } else {
                                                            q2.put("" + r, ((BigDecimal) q2.get("" + r)).add(p.getSaldoSinInteres()));
                                                            e.put("" + r, ((BigDecimal) e.get("" + r)).add(p.getSaldoSinInteres()));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (!q1.isEmpty()) {
                                        a.add(q1);
                                    }
                                    if (!q2.isEmpty()) {
                                        b.add(q2);
                                    }
                                }
                            }
                        } else {
                            y = "No hay ninguna moneda en donde mostrar los datos";
                        }
                    }
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
                            <fieldset><legend>&nbsp;</legend>
                                <input id="inputsubmit1" type="submit" name="inputsubmit1" value="Salir" />
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
            <div id="main">
                <div id="welcome" class="post">
                    <h2 class="title">Cuadro de DEBE-HABER POR MONEDAS - financeBank!</h2>
                    <h3 class="date"><span class="month"><script type="text/javascript">
                        var fecha=new Date();
                        var fmtmonthnamesshort=new Array("Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre");
                        document.write(fmtmonthnamesshort[fecha.getMonth()]);
                            </script></span> <span class="day"><script type="text/javascript">
                                document.write(fecha.getDate());
                            </script></span><span class="year">, <script type="text/javascript">document.write(fecha.getFullYear());</script></span></h3>
                    <div class="meta">
                        <p>Casa de cambios Ventura <%=(au ? "<font color='#FF0000' class='conf'>CONFIDENCIAL</font>" : " <.> Pulse en el Boton generar")%></p>
                    </div>
                    <div class="story" style="display:<%=(au ? "block" : "none")%>">
                        <form id="formdh" name="formdh" method="post" action="">
                            <div class="eebuttonbar_top">
                                <input class="eebuttons" type="button" value="Imprimir" name="print_top" onclick="window.print();">
                            </div><div id="panel1">
                                <%
                                            String ab = FileUtil.getUniqueFolderId() + ".xls";
                                            String ac = getServletContext().getRealPath("/") + "resources/certificados/" + ab;
                                            File ad = new File(ac);
                                            if (ad.getParentFile() != null) {
                                                ad.getParentFile().mkdirs();
                                            }
                                            FileOutputStream aa = new FileOutputStream(ac);
                                            HSSFWorkbook ae = new HSSFWorkbook();
                                            HSSFSheet af = ae.createSheet("REPORTE DE DEBE Y HABER");
                                            HSSFRow ag = null;
                                            HSSFCell ah = null;
                                            HSSFRow ai = af.createRow(0);
                                            ai.setHeight((short) (ai.getHeight() * 2));
                                            HSSFCellStyle aj = ae.createCellStyle();
                                            aj.setWrapText(true);
                                            HSSFFont ak = ae.createFont();
                                            ak.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                                            aj.setFont(ak);
                                            aj.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                                            HSSFFont al = ae.createFont();
                                            al.setFontName(HSSFFont.FONT_ARIAL);
                                            al.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
                                            HSSFCellStyle am = ae.createCellStyle();
                                            am.setFont(al);
                                            int z = (a.size() > b.size() ? a.size() : b.size());
                                            HSSFRow an = af.createRow(z + 1);
                                            String mx = "";
                                            if (!(a.isEmpty() || a.isEmpty())) {
                                                for (int t = 0; t < g.size(); t++) {
                                                    mx += "<th class='ee115' width='" + Math.round(240 / g.size()) + "px'>";
                                                    mx += "" + ((TMoneda) g.get("" + t)).getNombre() + " (" + ((TMoneda) g.get("" + t)).getSimbolo() + ")";
                                                    mx += "</th>";
                                                    HSSFCell ao = ai.createCell(t + 1);
                                                    ao.setCellValue(new HSSFRichTextString("" + ((TMoneda) g.get("" + t)).getNombre() + " (" + ((TMoneda) g.get("" + t)).getCodMoneda() + ")"));
                                                    ao.setCellStyle(aj);
                                                    af.setColumnWidth(t + 1, 3500);
                                                    HSSFCell ap = ai.createCell(g.size() + t + 3);
                                                    ap.setCellValue(new HSSFRichTextString("" + ((TMoneda) g.get("" + t)).getNombre() + " (" + ((TMoneda) g.get("" + t)).getCodMoneda() + ")"));
                                                    ap.setCellStyle(aj);
                                                    af.setColumnWidth(g.size() + t + 3, 3500);
                                                    HSSFCell aq = an.createCell(t + 1);
                                                    aq.setCellValue(new HSSFRichTextString("" + ((TMoneda) g.get("" + t)).getNombre() + " (" + ((TMoneda) g.get("" + t)).getCodMoneda() + ")"));
                                                    aq.setCellStyle(aj);
                                                    HSSFCell ar = an.createCell(g.size() + t + 3);
                                                    ar.setCellValue(new HSSFRichTextString("" + ((TMoneda) g.get("" + t)).getNombre() + " (" + ((TMoneda) g.get("" + t)).getCodMoneda() + ")"));
                                                    ar.setCellStyle(aj);
                                                }
                                                af.setColumnWidth(g.size() + 1, 2250);
                                                StringBuffer sb = new StringBuffer();
                                                sb.append("<table><tr><td colspan='2' align='center' valign='top'>REPORTE DE DEBE Y HABER</td></tr><tr><td width='500px' style='max-width: 500px;'>"
                                                        + "<table class='tabla' border='1' cellspacing='0' cellpadding='0' bgcolor='#FFFFFF'>"
                                                        + "<tr style='height:14pt'>"
                                                        + "<th class='ee115'>"
                                                        + "NOMBRES (DEBE)-" + a.size()
                                                        + "</th>" + mx + "</tr>");
                                                af.setColumnWidth(0, 11000);
                                                HSSFCell as = ai.createCell(0);
                                                as.setCellValue(new HSSFRichTextString("NOMBRES (DEBE)-" + a.size()));
                                                as.setCellStyle(aj);
                                                int tabla = 0;
                                                for (Iterator f = a.iterator(); f.hasNext();) {
                                                    Map h = (Map) f.next();
                                                    String x = "<td>" + h.get("n") + "</td>";
                                                    ag = af.createRow(tabla + 1);
                                                    ah = ag.createCell(0);
                                                    ah.setCellValue("" + h.get("n"));
                                                    for (int j = 0; j < g.size(); j++) {
                                                        x += "<td align='right'>" + "" + ((BigDecimal) h.get("" + j)).toString().replace(",", "").replace(".", ",") + "</td>";
                                                        ah = ag.createCell(j + 1);
                                                        ah.setCellValue(((BigDecimal) h.get("" + j)).doubleValue());
                                                    }
                                                    sb.append("<tr class='" + (tabla++ % 2 == 0 ? "modo2" : "modo1") + "'>" + x + "</tr>");
                                                }
                                                af.setColumnWidth(g.size() + 2, 11000);
                                                HSSFCell at = ai.createCell(g.size() + 2);
                                                at.setCellValue(new HSSFRichTextString("NOMBRES (HABER)-" + b.size()));
                                                at.setCellStyle(aj);
                                                tabla = 0;
                                                sb.append("</table>"
                                                        + "</td><td valign='top' style='max-width: 500px;'>"
                                                        + "<table class='tabla' border='1' cellspacing='0' cellpadding='0' bgcolor='#FFFFFF'>"
                                                        + "<tr style='height:14pt'>"
                                                        + "<th class='ee115'>"
                                                        + "NOMBRES (HABER)-" + b.size()
                                                        + "</th>" + mx + "</tr>");
                                                for (Iterator h = b.iterator(); h.hasNext();) {
                                                    Map k = (Map) h.next();
                                                    String x = "<td>" + k.get("n") + "</td>";
                                                    ag = af.getRow(tabla + 1);
                                                    if (ag == null) {
                                                        ag = af.createRow(tabla + 1);
                                                        ah = ag.createCell(g.size() + 2);
                                                        ah.setCellValue("" + k.get("n"));
                                                    } else {
                                                        ah = ag.createCell(g.size() + 2);
                                                        ah.setCellValue("" + k.get("n"));
                                                    }
                                                    for (int j = 0; j < g.size(); j++) {
                                                        x += "<td align='right'>" + "" + ((BigDecimal) k.get("" + j)).toString().replace(",", "").replace(".", ",").replace("-", "") + "</td>";
                                                        ah = ag.createCell(g.size() + j + 3);
                                                        ah.setCellValue(((BigDecimal) k.get("" + j)).abs().doubleValue());
                                                    }
                                                    sb.append("<tr class='" + (tabla++ % 2 == 0 ? "modo2" : "modo1") + "'>" + x + "</tr>");
                                                }
                                                HSSFCell av = an.createCell(0);
                                                av.setCellValue(new HSSFRichTextString("TOTAL EN DEBE"));
                                                av.setCellStyle(aj);
                                                sb.append("</table></td></tr>"
                                                        + "<tr><td align='right'><table class='tabla' border='1' cellspacing='0' cellpadding='0' bgcolor='#FFFFFF'>"
                                                        + "<tr style='height:14pt'>"
                                                        + "<th class='ee115'>"
                                                        + "TOTAL EN DEBE"
                                                        + "</th>" + mx + "</tr>");
                                                String t = "";
                                                ag = af.createRow(z + 2);
                                                ah = ag.createCell(0);
                                                ah.setCellValue("----------------->");
                                                for (int j = 0; j < g.size(); j++) {
                                                    t += "<td align='right'>";
                                                    t += "" + ((BigDecimal) c.get("" + j)).toString().replace(",", "").replace(".", ",");
                                                    t += "</td>";
                                                    ah = ag.createCell(j + 1);
                                                    ah.setCellValue(((BigDecimal) c.get("" + j)).doubleValue());
                                                }
                                                HSSFCell aw = an.createCell(g.size() + 2);
                                                aw.setCellValue(new HSSFRichTextString("TOTAL EN HABER"));
                                                aw.setCellStyle(aj);
                                                sb.append("<tr class='" + (tabla % 2 == 0 ? "modo2" : "modo1") + "'><td class='ee115'>-----------------></td>" + t + "</tr>");
                                                sb.append("</table></td><td align='right'>"
                                                        + "<table border='1' cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' class='tabla'>"
                                                        + "<tr style='height:14pt'>"
                                                        + "<th class='ee115'>"
                                                        + "TOTAL EN HABER"
                                                        + "</th>" + mx + "</tr>");
                                                t = "";
                                                ag = af.getRow(z + 2);
                                                ah = ag.createCell(g.size() + 2);
                                                ah.setCellValue("----------------->");
                                                for (int j = 0; j < g.size(); j++) {
                                                    t += "<td align='right'>";
                                                    t += "" + ((BigDecimal) e.get("" + j)).toString().replace(",", "").replace(".", ",").replace("-", "");
                                                    t += "</td>";
                                                    ah = ag.createCell(g.size() + j + 3);
                                                    ah.setCellValue(((BigDecimal) e.get("" + j)).abs().doubleValue());
                                                }
                                                sb.append("<tr class='" + (tabla % 2 == 0 ? "modo2" : "modo1") + "'><td class='ee115'>-----------------></td>" + t + "</tr>");
                                                sb.append("</table> "
                                                        + "</td></tr>"
                                                        + "</table>");
                                                out.write(sb.toString());
                                                try {
                                                    ae.write(aa);
                                                } catch (Exception ex) {
                                                    ex.printStackTrace();
                                                } finally {
                                                    aa.close();
                                                }
                                %>
                            </div>
                            <div class="eebuttonbar_bottom">
                                <input class="eebuttons" type="button" value="Imprimir" name="print_bottom" onclick="window.print();">
                                <input class="eebuttons" type="button" value="Obtener XLS (Excel)" name="print_bottom_xls" onclick="window.open('<%="resources/certificados/" + ab%>','xls', '');">
                                <span style="margin-left:1px;"></span>
                            </div>
                            <%
                                        } else {
                                            out.write("<div>" + y + "</div>");
                                        }
                            %>
                        </form>
                    </div>
                </div>
                <div id="dh">
                    <blockquote>
                        <blockquote>
                            <blockquote>
                                <blockquote>
                                    <blockquote>
                                        <form name="form1" method="post" action="#">
                                            <input name="postback" id="postback" type="hidden" value="1">
                                            <span>
                                                <input type="hidden" name="submit_bottom" value="1"><input type="submit" id="debehaber" name="submit_bottom1" value="Generar reporte del día" onclick="abrir();this.disabled=true;">
                                            </span><div id="await"></div>
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
