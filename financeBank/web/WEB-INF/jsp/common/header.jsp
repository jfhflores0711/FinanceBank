<%--
    Document   : newjsp
    Created on : 11/02/2010, 04:41:24 PM
    Author     : ronald
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<%@page import="org.finance.bank.bean.*,
        org.finance.bank.util.*,
        org.finance.bank.model.*,
        org.finance.bank.model.dao.*,
        java.text.*,
        java.util.*,
        java.math.*"%>
<script type="text/javascript">
    var timerID 	= null;
    var timerRunning 	= false;
    var ver 		= 0;

    function stopclock(){
        if(timerRunning) window.clearTimeout(timerID);
        timerRunning = false;
    }

    function startclock(){
        stopclock();
        showtime();
    }

    function showtime (){
        timerID = window.setTimeout("startclock()",120*60000);
        if ( ver==1){
            window.close();
            document.out.submit();
        }
        ver = 1;
        timerRunning = true;
    }

    startclock();
    window.setTimeout("cycleBan_B()", 90*60*1000);
    var bannerImg_B = new Array();
    bannerImg_B[0]="images/baner02.jpg";
    bannerImg_B[1]="images/baner04.jpg";
    bannerImg_B[2]="images/img03.jpg";
    bannerImg_B[3]="images/baner01.jpg";
    bannerImg_B[4]="images/baner03.jpg";
    var newBanner_B = 0;
    var totalBan_B = bannerImg_B.length;

    function cycleBan_B() {
        newBanner_B++;
        if (newBanner_B == totalBan_B) {
            newBanner_B = 0;
        }
        document.getElementById('baner1').src=bannerImg_B[newBanner_B];
        setTimeout("cycleBan_B()", 90*60*1000);
    }
</script>
<div id="logo" >
    <h1><img src="images/logo04.png" width="129" height="100" alt="logo01"/></h1>
    <br><br><br>
    <div id="headermain" class="post">
        <p><img id="baner1" src="images/baner01.jpg" alt="" width="700" height="150" /></p>
    </div>
    <%
                DAOGeneral d = new DAOGeneral();
                TTipoPersona cargo = null;
                String Cargo = "";
                String userName = "";
                String filial = "";
                String codFilial = "";
                String caja = "";
                String codcaja = "";
                String Montos = "";
                String fecha = "";
                String hid = null;
                String id_role = "20100923111707627123";
                formatMoneda fm = new formatMoneda();
                if (session.getAttribute("SESSION") != null && !session.getAttribute("SESSION").equals("false")) {
                    if (session.getAttribute("hid") == null) {
                        if (response.isCommitted()) {
                            out.println("<script language='JavaScript' type='text/javascript'>var pagina='" + request.getContextPath() + "/principal.htm'; "
                                    + "function redireccionar() {location.href=pagina;} setTimeout ('redireccionar()', 50);</script>");
                        } else {
                            response.sendRedirect("principal.htm");
                        }
                    } else {
                        hid = session.getAttribute("hid").toString();
                    }
                    codcaja = session.getAttribute("USER_CODCAJA").toString();
                    if (codcaja == null) {
                        if (response.isCommitted()) {
                            out.println("<script language='JavaScript' type='text/javascript'>var pagina='" + request.getContextPath() + "/principal.htm'; "
                                    + "function redireccionar() {location.href=pagina;} setTimeout ('redireccionar()', 50);</script>");
                        } else {
                            response.sendRedirect("principal.htm");
                        }
                        if (session.getAttributeNames().hasMoreElements()) {
                            session.invalidate();
                        }
                    }
                    cargo = (TTipoPersona) d.load(TTipoPersona.class, session.getAttribute("USER_ID_ROLE").toString());
                    String hql = "from TMoneda where estado ='ACTIVO'";
                    // + " AND TCaja.codCaja ='" + session.getAttribute("USER_CODCAJA").toString() + "' and fechaTransaccion like '" + DateUtil.getDate(new Date()) + "%'";
                    List r = d.findAll(hql);
                    Iterator i = r.iterator();
                    while (i.hasNext()) {
                        TMoneda mo = (TMoneda) i.next();
                        //TDetalleCaja detallecajaO = iniDetalleCaja.detalleActivaCaja(codcaja, mo.getCodMoneda(), DateUtil.getDate(new Date()));
                        TLogFinance detallecajaO = (TLogFinance) d.load(TLogFinance.class, "LOG" + codcaja + mo.getCodMoneda());
                        Double valor = Double.parseDouble(detallecajaO.getMontoFinal() + "");
                        Montos = " | <input type='hidden' id='xM" + mo.getCodMoneda() + "' value='" + NumberUtil.converseN2S(detallecajaO.getMontoFinal().toString()) + "'><b>" + mo.getSimbolo() + "</b> " + fm.formatMoneda(valor) + " " + Montos;
                        session.setAttribute("xM" + mo.getCodMoneda(), NumberUtil.converseN2S(detallecajaO.getMontoFinal().toString()));
                    }
                    Cargo = cargo.getDescripcion();
                    userName = session.getAttribute("USER_NAME").toString();
                    filial = session.getAttribute("USER_FILIAL").toString();
                    caja = session.getAttribute("USER_CAJA").toString();
                    fecha = session.getAttribute("FECHA").toString();
                    id_role = session.getAttribute("USER_ID_ROLE").toString();
                    codFilial = session.getAttribute("USER_CODFILIAL").toString();
                } else {
                    if (response.isCommitted()) {
                        out.println("<script language='JavaScript' type='text/javascript'>var pagina='" + request.getContextPath() + "/principal.htm'; "
                                + "function redireccionar() {location.href=pagina;} setTimeout ('redireccionar()', 50);</script>");
                    } else {
                        response.sendRedirect("principal.htm");
                    }
                    if (session.getAttributeNames().hasMoreElements()) {
                        session.invalidate();
                    }
                }
    %>
    <br>
    <form name="out" id="flogout" method="post" action="logout.htm">
        <table border='0' cellpadding='0' cellspacing='0' width="100%"  bgcolor="#e4ffdd">
            <tr>
                <td><font color="#018d00"  style="font-size: 9px;"> &nbsp;</font></td>
                <td width="7%" align="left"><font color="#018d00" style="font-size: 9px;"><b>USUARIO:&nbsp; </b></font></td>
                <td width="20%" align="left"><font color="#018d00" style="font-size: 9px;"><%=userName%></font></td>
                <td width="10%" align="right"> <font color="#018d00" style="font-size: 9px;"><b>FILIAL:&nbsp; </b></font></td>
                <td width="20%" align="left"><font color="#018d00" style="font-size: 9px;"><%=filial%></font></td>
                <td><font color="#018d00" style="font-size: 9px;"><b>FECHA:&nbsp; </b><%=fecha%></font></td>
                <td align="right"><input id="inputsubmit1" type="submit" name="inputsubmit1" value="Cerrar Session" /></td>
            </tr>
            <tr>
                <td><font color="#018d00" style="font-size: 9px;"> &nbsp;</font></td>
                <td width="7%" align="left"><font color="#018d00" style="font-size: 9px;"><b> CARGO:&nbsp; </b></font></td>
                <td width="20%" align="left"><font color="#018d00" style="font-size: 9px;"><%=Cargo%></font></td>
                <td width="10%" align="right"><font color="#018d00" style="font-size: 9px;"><b>CAJA:&nbsp; </b></font></td>
                <td width="20%" align="left"><font color="#018d00" style="font-size: 9px;"><%=caja%></font></td>
                <td colspan="2"><font color="#018d00" style="font-size: 9px;"><%=Montos%></font></td>
            </tr>
        </table>
    </form>
</div>