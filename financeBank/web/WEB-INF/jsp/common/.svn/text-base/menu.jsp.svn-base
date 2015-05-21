<ul>
    <li class="active1"><a href="#" title="">Modulos</a></li>
    <%
                String reqSelect = "0";
                if (request.getAttribute("idmenu") != null) {
                    reqSelect = (String) request.getAttribute("idmenu");
                }
                if (id_role.equals("")) {
                    if (response.isCommitted()) {
                        out.println("<script language='JavaScript' type='text/javascript'>var pagina='" + request.getContextPath() + "/principal.htm'; "
                                + "function redireccionar() {location.href=pagina;} setTimeout ('redireccionar()', 50);</script>");
                    } else {
                        response.sendRedirect("principal.htm");
                    }
                }
                String sqlM = "from TControlModulo where TTipoPersona.idtipopersona ='" + id_role + "' " + " and estado='1' order by TModulo.descripcion";
                List resultM = d.findAll(sqlM);
                Iterator itM = resultM.iterator();
                int cont = 0;
                while (itM.hasNext()) {
                    TControlModulo cm = (TControlModulo) itM.next();
                    TModulo modulo = (TModulo) d.load(TModulo.class, cm.getTModulo().getIdmodulo());
                    if (reqSelect.equals(modulo.getIdmodulo())) {
                        out.println("<li><a id='menu" + cont + "' href='" + modulo.getUrl() + ".htm?hid=" + hid + "' style='color: #FFFFFF;background: #ecb784;' ><b>" + modulo.getDescripcion() + "</b></a></li>");
                    } else {
                        out.println("<li><a id='menu" + cont + "' href='" + modulo.getUrl() + ".htm?hid=" + hid + "'>" + modulo.getDescripcion() + "</a></li>");
                    }
                    cont++;
                }
    %>
    <li class="active1"><a  title="">&nbsp;</a></li>
</ul>
