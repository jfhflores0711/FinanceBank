<%-- 
    Document   : newjsp
    Created on : 11/02/2010, 04:41:24 PM
    Author     : eloy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="org.finance.bank.bean.*,
        org.hibernate.*,
        org.finance.bank.util.*,
        org.finance.bank.model.*,
        java.text.*,
        java.util.*,
        java.math.*"%>
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
        <style type="text/css" media="print"> .botones{display:none;} </style>
    </head>
    <body>
        <%
                    Map ticket = (Map) session.getAttribute("ticket");
                    String cajero = "";
                    cajero = session.getAttribute("USER_CAJA").toString();
                    if (session.getAttribute("NUMERO_OP") != null && !session.getAttribute("NUMERO_OP").equals("")) {
                    }
                    Double DDeterioro = Double.parseDouble(ticket.get("D_DETERIORO").toString());
        %>
        <table align="center" >
            <tr>
                <td>
                    <table width="400" border="0" cellpadding="0" cellspacing="0" >
                        <tr>
                            <td>
                                &nbsp;&nbsp;&nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td>
                                &nbsp;&nbsp;&nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td>
                                &nbsp;&nbsp;&nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td>
                                &nbsp;&nbsp;&nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3" align="center"><b><%=ticket.get("TIPO")%> M.E </b></td>
                        </tr>
                        <tr>
                            <td>N&deg; OP :<%=ticket.get("NOP").toString().substring(ticket.get("NOP").toString().length() - 4, ticket.get("NOP").toString().length())%></td>
                            <td>&nbsp;</td>
                        </tr>
                        <tr>
                            <td> &nbsp;</td>
                            <td><%=cajero%></td>
                        </tr>
                        <tr>
                            <td>FECHA : <%=ticket.get("FECHA")%></td>
                            <td>HORA : <%=ticket.get("HORA")%></td>
                        </tr>
                        <tr>
                            <td colspan="2">MONEDA :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=ticket.get("MONEDA")%> </td>
                        </tr>
                        <%
                                    if (ticket.get("RZS").toString().length() > 0) {
                        %>
                        <tr>
                            <td colspan="2">&nbsp;<%=ticket.get("RZS").toString().toUpperCase()%> </td>
                        </tr>
                        <%
                                    }
                                    if (ticket.get("TIPO").equals("COMPRA")) {
                        %>
                        <tr>
                            <th>OPERACION</th>
                            <th>IMPORTE</th>
                        </tr>
                        <tr>
                            <td align="left"><%=ticket.get("TIPO")%> M.E&nbsp;</td>
                            <td align='right'><%=ticket.get("SIMBOLO")%> <%=ticket.get("RECIBIDO")%>&nbsp;&nbsp;</td>
                        </tr>
                        <tr>
                            <td align="left">TIPO DE CAMBIO :</td>
                            <td align='right'>S/. <%=ticket.get("TASA")%>&nbsp;&nbsp;</td>
                        </tr>
                        <% if (DDeterioro > 0) {%>
                        <tr>
                            <td align="left" >DESCUENTO POR DETERIORO :</td>
                            <td align='right'>S/. -<%=DDeterioro%>0&nbsp;&nbsp;</td>
                        </tr>
                        <% }%>
                        <tr>
                            <td align="left">TOTAL : M.N </td>
                            <td align='right'>S/.&nbsp;<%=ticket.get("ENTREGADO")%>&nbsp;&nbsp;</td>
                        </tr>
                        <%
                                    }
                                    if (ticket.get("TIPO").equals("VENTA")) {
                        %>
                        <tr>
                            <th>OPERACION</th>
                            <th>IMPORTE</th>
                        </tr>
                        <tr>
                            <td align="left"><%=ticket.get("TIPO")%> M.E</td>
                            <td align='right'><%=ticket.get("SIMBOLO")%> <%=ticket.get("ENTREGADO")%>&nbsp;&nbsp;</td>
                        </tr>
                        <tr>
                            <td align="left">TIPO DE CAMBIO :</td>
                            <td align='right'>S/. <%=ticket.get("TASA")%></td>
                        </tr>
                        <tr>
                            <td align="left">TOTAL : M.N </td>
                            <td align='right'>S/.&nbsp;<%=ticket.get("RECIBIDO")%>&nbsp;&nbsp;</td>
                        </tr>
                        <% }%>
                    </table>
                </td>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                </td>
                <td>
                    <table width="400" border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <td>
                                &nbsp;&nbsp;&nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td>
                                &nbsp;&nbsp;&nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td>
                                &nbsp;&nbsp;&nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td>
                                &nbsp;&nbsp;&nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3" align="center"><b><%=ticket.get("TIPO")%> M.E </b></td>
                        </tr>
                        <tr>
                            <td>N&deg; OP :<%=ticket.get("NOP").toString().substring(ticket.get("NOP").toString().length() - 4, ticket.get("NOP").toString().length())%></td>
                            <td>&nbsp;</td>
                        </tr>
                        <tr>
                            <td><%=cajero%></td>
                        </tr>
                        <tr>
                            <td>FECHA : <%=ticket.get("FECHA")%></td>
                            <td>HORA : <%=ticket.get("HORA")%></td>
                        </tr>
                        <tr>
                            <td colspan="2">MONEDA :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=ticket.get("MONEDA")%> </td>
                        </tr>
                        <%if (ticket.get("RZS").toString().length() > 0) {%>
                        <tr>
                            <td colspan="2">&nbsp;<%=ticket.get("RZS").toString().toUpperCase()%> </td>
                        </tr>
                        <%
                                    }
                                    if (ticket.get("TIPO").equals("COMPRA")) {
                        %>
                        <tr>
                            <th>OPERACION</th>
                            <th>IMPORTE</th>
                        </tr>
                        <tr>
                            <td align="left"><%=ticket.get("TIPO")%> M.E&nbsp;</td>
                            <td align='right'><%=ticket.get("SIMBOLO")%> <%=ticket.get("RECIBIDO")%>&nbsp;&nbsp;</td>
                        </tr>
                        <tr>
                            <td align="left">TIPO DE CAMBIO :</td>
                            <td align='right' style='font-weight: bold'>S/. <%=ticket.get("TASA")%></td>
                        </tr>
                        <%
                                                                if (DDeterioro > 0) {
                        %>
                        <tr>
                            <td align="left">DESCUENTO POR DETERIORO :</td>
                            <td align='right' style='font-weight: bold'>S/. -<%=DDeterioro%>0&nbsp;&nbsp;</td>
                        </tr>
                        <%            }%>
                        <tr>
                            <td align="left">TOTAL : M.N </td>
                            <td align='right' style='font-weight: bold'>S/.&nbsp;<%=ticket.get("ENTREGADO")%>&nbsp;&nbsp;</td>
                        </tr>
                        <%
                                    }
                                    if (ticket.get("TIPO").equals("VENTA")) {
                        %>
                        <tr>
                            <th>OPERACION</th>
                            <th>IMPORTE</th>
                        </tr>
                        <tr>
                            <td align="left"><%=ticket.get("TIPO")%> M.E </td>
                            <td align='right'><%=ticket.get("SIMBOLO")%> <%=ticket.get("ENTREGADO")%>&nbsp;&nbsp;</td>
                        </tr>
                        <tr>
                            <td align="left">TIPO DE CAMBIO :</td>
                            <td align='right' style='font-weight: bold'>S/. <%=ticket.get("TASA")%>&nbsp;&nbsp;</td>
                        </tr>
                        <tr>
                            <td align="left">TOTAL : M.N </td>
                            <td align='right' style='font-weight: bold'>S/.&nbsp;<%=ticket.get("RECIBIDO")%>&nbsp;&nbsp;</td>
                        </tr>
                        <%
                                    }
                        %>
                    </table>
                </td>
            </tr>
            <tr class="botones">
                <td align="center">
                    <input type="submit" value="IMPRIMIR" onclick="document.title=''; if (window.print)window.print();else alert('SU NAVEGADOR NO SOPORTA ESTA FUNCIÓN');"/>
                </td>
                <td align="center">
                    <form name="fcambiomu" method="post" action="cambiomonedauser.htm">
                        <% if (ticket.get("h") == null) {%>
                        <input type="submit" value="VOLVER" />
                        <% } else {%>
                        <input type="button" value="CERRAR" onclick="window.close();"/>
                        <%}%>
                    </form>
                </td>
            </tr>
        </table>
    </body>
</html>
