package org.finance.bank.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.finance.bank.bean.TCategoriaPersona;
import org.finance.bank.bean.TDepartamento;
import org.finance.bank.bean.TFilial;
import org.finance.bank.bean.TTipoCredito;
import org.finance.bank.model.dao.DAOGeneral;

/**
 *Carga catálogos en general como: CAJA, FILIAL y PERSONA
 * @author ZAMORA
 */
public class SAdminCatalogo extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        DAOGeneral dao = new DAOGeneral();
        request.getSession(true);
        try {
            String a = request.getParameter("a");
            String b = request.getParameter("b");
            if (b == null || "0".equals(b)) {
                out.print("&nbsp;");
                out.close();
                return;
            }
            out.println("<table id='tcontenedor'>");
            out.println("<tr><td width='40%' valign='top'>");
            if ("FILIAL".equals(b)) {
                List result = dao.findAll("from TFilial order by codFilial");
                Iterator it = result.iterator();
                out.println("<div id='divCatalogoElement'>");
                out.println("<div id='menu'>");
                out.println("<ul>");
                int i = 0;
                while (it.hasNext()) {
                    TFilial filial = (TFilial) it.next();
                    i = i + 1;
                    out.println("<li style='cursor: pointer' id='li" + i + "'><a id='a" + i + "' onclick=\"selected('" + i + "'); verDetalleCatalogo('" + filial.getCodFilial() + "');\">" + filial.getCodFilial() + " " + filial.getNombre());
                    out.println("</a></li>");
                }
                out.println("</ul>");
                out.println("</div>");
                out.println("</div>");
            } else if ("CAJA".equals(b)) {
                List result = dao.findAll("from TFilial order by codFilial");
                Iterator it = result.iterator();
                out.println("<div id='divCatalogoElement'>");
                out.println("<table id='tTipoFilial' name='tTipoFilial' class='tabla' border='0' width='100%'>");
                out.println("<tr>");
                out.println("<td>");
                int i = 0;
                if (result.size() > 0) {
                    out.println("<select id='selmiFilial' onchange='mostrarcaja();'><option value='0'>(Seleccione Filial)</option>");
                    while (it.hasNext()) {
                        TFilial filial = (TFilial) it.next();
                        i = i + 1;
                        out.println("<option value='" + filial.getCodFilial() + "'>" + filial.getNombre() + "</option>");
                    }
                    out.println("</select></td>");
                } else {
                    out.println("Aún no hay un filial creado!</td>");
                }
                out.println("</tr>");
                out.println("</table>");
                out.println("<div id='divMisCajas' style='overflow:auto;height:420px'>");
                out.println("<div id='menu'>");
                out.println("<ul>");
                out.println("<li>");
                out.println("</li>");
                out.println("<ul>");
                out.println("</div>");
                out.println("</div>");
            } else if ("A".equals(b)) {
                List result = dao.findAll("from TTipoCredito order by nombre");
                out.println("<div id='divCatalogoElement'><div id='menu'><ul>");
                int i = 0;
                for (Iterator c = result.iterator(); c.hasNext();) {
                    TTipoCredito d = (TTipoCredito) c.next();
                    i++;
                    out.println("<li style='cursor: pointer' id='li" + i + "'><a id='a" + i + "' onclick=\"selected('" + i + "'); verDetalleCatalogo('" + d.getIdtipocredito() + "');\">" + d.getNombre() + " (" + d.getDescripcion() + ")");
                    out.println("</a></li>");
                }
                if (i == 0) {
                    out.println("Aún no hay una L&iacute;nea de Cr&eacute;dito creado!");
                }
                out.println("</ul></div></div>");
            } else if ("PERSONA".equals(b)) {
                out.println("<div id='divCatalogoElement'>");
                out.println("<table id='tTipoFilial' name='tTipoFilial' class='tabla' border='0' width='100%'>");
                out.println("<tr>");
                out.println("<th><select id='selPersona'>"
                        + "<option value='NOMBRE'>NOMBRE/R.S.</option>"
                        + "<option value='APELLIDO'>APELLIDOS</option>"
                        + "<option value='DNI'>DNI/RUC</option></select>"
                        + "<input type='text' id='txtPersona' name='txtPersona' value=''><input type='button' id='btnBuscar' onclick='mostrarpersona();' value='Buscar'>");
                out.println("</th>");
                out.println("</tr>");
                out.println("</table>");
                out.println("<div id='divMisPersonas' style='overflow:auto;height:420px'>");
                out.println("</div>");

            } else if ("PERSC".equals(b)) {
                List result = dao.findAll("from TFilial order by codFilial");
                Iterator it = result.iterator();
                out.println("<div id='divCatalogoElement'>");
                out.println("<table id='tTipoFilial' name='tTipoFilial' class='tabla' border='0' width='100%'>");
                out.println("<tr>");
                out.println("<td>");
                int i = 0;
                if (result.size() > 0) {
                    out.println("<select id='selmiFilial' onchange='mostrarpersc();'><option value='0'>(Seleccione Filial)</option>");
                    while (it.hasNext()) {
                        TFilial filial = (TFilial) it.next();
                        i = i + 1;
                        out.println("<option value='" + filial.getCodFilial() + "'>" + filial.getNombre() + "</option>");
                    }
                    out.println("</select></td>");
                } else {
                    out.println("Aún no hay un filial creado!</th>");
                }
                out.println("</tr>");
                out.println("</table>");
                out.println("<div id='divMisCajas' style='overflow:auto;height:420px'>");
                out.println("<div id='menu'>");
                out.println("<ul>");
                out.println("<li style='cursor: pointer'>");
                out.println("</li>");
                out.println("<ul>");
                out.println("</div>");
                out.println("</div>");
            } else {
                out.println("<div id='divElement'>");
                out.println("<table id='tTipo' name='tTipo' class='tabla' border='0' width='100%'>");
                out.println("<tr>");
                out.println("<td>Sin Catálogos");
            }
            out.println("</td>");
            out.println("<td width='10%'><center>");
            out.println("<hr style='background-color: #1E90FF;height: 1px;'><input type='button' id='cmdNuevo' name='cmdNuevo' value='Nuevo' onclick='nuevoCatalogo();'><hr style='background-color: #1E90FF;height: 1px;'><input type='button' id='cmdGuardar' name='cmdGuardar' value='Guardar' onclick='guardarCatalogo();' disabled><hr style='background-color: #1E90FF;height: 1px;'>");
            out.println("<input type='button' id='cmdActualizar' name='cmdActualizar' value='Actualizar' onclick='actualizarCatalogo();' disabled><hr style='background-color: #1E90FF;height: 1px;'><input type='button' id='cmdEliminar' name='cmdEliminar' value='Eliminar' onclick='eliminarCatalogo();' disabled><hr style='background-color: #1E90FF;height: 1px;'>");
            out.println("</center></td>");
            out.println("<td width='50%'>");
            if ("FILIAL".equals(b)) {
                out.println("<div id='divCatalogoDetalle' style='vertical-align:top'>");
                out.println("<form id='formDetalle' method='post' action=''>");
                out.println("<table id='tCatalogoDetalle' name='tCatalogoDetalle' class='tabla' border='1' style='vertical-align:top'>");
                out.println("<tr>");
                out.println("<th colspan='2'><font color='#385b88' style='font-size: 12px;'><b>Detalle Filial:</b></font>");
                out.println("</th>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Codigo</b></font></td>");
                out.println("<td><table><tr><td><input type='text' id='txtCodigo' name='txtCodigo' value='' disabled></td><td style='color:red'>*</td></tr></table></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Nombre</b></font></td>");
                out.println("<td><table><tr><td><input type='text' id='txtNombre' name='txtNombre' value='' disabled></td><td style='color:red'>*</td></tr></table></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Direci&oacute;n</b></font></td>");
                out.println("<td><input type='text' id='txtDireccion' name='txtDireccion' value='' disabled></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Estado</b></font></td>");
                out.println("<td>");
                out.println("<select id='selEstado' name='selEstado'>");
                out.println("<option>ACTIVO</option>");
                out.println("<option>INACTIVO</option>");
                out.println("</option>");
                out.println("</select>");
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Teléfono</b></font></td>");
                out.println("<td><input type='text' id='txtTelefono' name='txtTelefono' value='' disabled></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Codigo para numero cuenta</b></font></td>");
                out.println("<td><table><tr><td><input type='text' id='txtCodNumCuenta' name='txtCodNumCuenta' value='' disabled></td><td style='color:red'>*</td></tr></table></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Departamento</b></font></td>");
                out.println("<td>");
                List result = dao.findAll("from TDepartamento");
                Iterator it = result.iterator();
                out.println("<select id='seldepartamento' name='seldepartamento' onchange='listarProvincia()'>");
                out.println("<option value='0'>(Seleccione Departamento)</option>");
                while (it.hasNext()) {
                    TDepartamento departamento = (TDepartamento) it.next();
                    out.println("<option value='" + departamento.getIddepartamento() + "'>" + departamento.getDescripcion() + "</option>");
                }
                out.println("</select>");
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Provincia</b></font></td>");
                out.println("<td>");
                out.println("<div id='divprovincia'>");
                out.println("<select id='selprovincia' name='selprovincia' disabled>");
                out.println("<option value=''>Seleccione Provincia</option>");
                out.println("</select>");
                out.println("</div>");
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Distrito</b></font></td>");
                out.println("<td>");
                out.println("<div id='divDistrito'>");
                out.println("<select id='seldistrito' name='seldistrito' disabled>");
                out.println("<option value=''>Seleccione Distrito</option>");
                out.println("</select>");
                out.println("</div>");
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr style='display:none'>");
                out.println("<td>");
                out.println("<input type='text' id='txtcatalogo' name='txtcatalogo' value='" + b + "'>");
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><div id='divrptaInsertar'>");
                out.println("<input type='hidden' id='txtRptaInsertar' name='txtRptaInsertar' value='NO'>");
                out.println("</div></td>");
                out.println("</tr>");
                out.println("</table>");
                out.println("</form>");
                out.println("</div>");
            } else if ("CAJA".equals(b)) {
                out.println("<div id='divCatalogoDetalle' style='vertical-align:top'>");
                out.println("<form id='formDetalle' method='post' action=''>");
                out.println("<table id='tCatalogoDetalle' name='tCatalogoDetalle' class='tabla' border='1' style='vertical-align:top'>");
                out.println("<tr>");
                out.println("<th colspan='2'><font color='#385b88' style='font-size: 12px;'><b>Detalle Caja:</b></font>");
                out.println("</th>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Codigo</b></font></td>");
                out.println("<td><table><tr><td><input type='text' id='txtCodigo' name='txtCodigo' value='' disabled></td><td style='color:red'>*</td></tr></table></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Nombre</b></font></td>");
                out.println("<td><table><tr><td><input type='text' id='txtNombre' name='txtNombre' value='' disabled></td><td style='color:red'>*</td></tr></table></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Filial</b></font></td>");
                out.println("<td><div id='ulfi'>");
                out.println("<input id='ulfit' type='hidden' value='0'>");
                List result = dao.findAll("from TFilial fil where fil.estado='ACTIVO'");
                Iterator it = result.iterator();
                out.println("<select id='selfilial' name='selfilial'>");
                while (it.hasNext()) {
                    TFilial filial = (TFilial) it.next();
                    out.println("<option value='" + filial.getCodFilial() + "'>" + filial.getNombre() + "</option>");
                }
                out.println("</select>");
                out.println("</div></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Tipo</b></font></td>");
                out.println("<td>");
                out.println("<select id='selTipo' name='selTipo'>");
                out.println("<option value='PRIMARY'>PRIMARIA</option>");
                out.println("<option value='SECONDARY'>SECUNDARIA</option>");
                out.println("</select>");
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Estado</b></font></td>");
                out.println("<td>");
                out.println("<select id='selEstado' name='selEstado'>");
                out.println("<option>ACTIVO</option>");
                out.println("<option>INACTIVO</option>");
                out.println("</select>");
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr style='display:none'>");
                out.println("<td>");
                out.println("<input type='text' id='txtcatalogo' name='txtcatalogo' value='" + b + "'>");
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><div id='divrptaInsertar'>");
                out.println("<input type='hidden' id='txtExisteCat' name='txtExisteCat' value='NO'>");
                out.println("<input type='hidden' id='txtRptaInsertar' name='txtRptaInsertar' value='NO'>");
                out.println("</div></td>");
                out.println("</tr>");
                out.println("</table>");
                out.println("</form>");
                out.println("</div>");
            } else if ("A".equals(b)) {
                out.println("<div id='divCatalogoDetalle' style='vertical-align:top'>");
                out.println("<form id='formDetalle' method='post' action=''>");
                out.println("<table id='tCatalogoDetalle' name='tCatalogoDetalle' class='tabla' border='1' style='vertical-align:top'>");
                out.println("<tr>");
                out.println("<th colspan='2'><font color='#385b88' style='font-size: 12px;'><b>Detalle de Cr&eacute;ditos:</b></font>");
                out.println("</th>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Nombre</b></font></td>");
                out.println("<td><table><tr><td><input type='text' id='txtN' name='txtN' value='' disabled></td><td style='color:red'>*</td></tr></table></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Descripci&oacute;n</b></font></td>");
                out.println("<td><input type='text' id='txtD' name='txtD' value='' disabled></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Monto Máximo (EN SOLES)</b></font></td>");
                out.println("<td><table><tr><td><input type='text' id='txtM' name='txtM' value='' disabled style='text-align:right;'></td><td style='color:red'>*</td></tr></table></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Número de cuotas máxima</b></font></td>");
                out.println("<td><table><tr><td><input type='text' id='txtI' name='txtI' value='' disabled style='text-align:right;'></td><td style='color:red'>*</td></tr></table></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Tasa de Interés Máxima(TEA) a 360 días</b></font></td>");
                out.println("<td><table><tr><td><input type='text' id='txtA' name='txtA' value='' disabled style='width:100px;text-align:right;'>%</td><td style='color:red'>*</td></tr></table></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Tipo de Tasa de Interés</b></font></td>");
                out.println("<td><select id='selE' name='selE' disabled>");
                out.println("<option selected>1 ANUAL </option>");
                out.println("<option>2 MENSUAL </option>");
                out.println("<option>3 DIARIO </option>");
                out.println("</select></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Tasa de Interés Moratoria Máxima(TEA)</b></font></td>");
                out.println("<td><table><tr><td><input type='text' id='txtB' name='txtB' value='' disabled style='width:100px;text-align:right;'>%</td><td style='color:red'>*</td></tr></table></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Tipo de Tasa de Interés Moratoria</b></font></td>");
                out.println("<td><select id='selF' name='selF' disabled>");
                out.println("<option>1 ANUAL </option>");
                out.println("<option>2 MENSUAL </option>");
                out.println("<option>3 DIARIO </option>");
                out.println("</select></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Forma de cobro de Intereses</b></font></td>");
                out.println("<td><select id='selG' name='selG' disabled>");
                out.println("<option>1 Fijo o Adelantado </option>");
                out.println("<option selected>2 A la Fecha de Pago </option>");
                out.println("</select></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Modo de cálculo de Intereses</b></font></td>");
                out.println("<td><select id='selH' name='selH' disabled>");
                out.println("<option>1 A Rebatir </option>");
                out.println("<option selected>2 Al Monto Prestado </option>");
                out.println("</select></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Número de cuotas Mínima</b></font></td>");
                out.println("<td><table><tr><td><input type='text' id='txtG' name='txtG' value='2' disabled style='width:160px;text-align:right;'></td><td style='color:red'>*</td></tr></table></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Monto Mínimo (EN SOLES)</b></font></td>");
                out.println("<td><table><tr><td><input type='text' id='txtE' name='txtE' value='10.00' disabled style='width:160px;text-align:right;'></td><td style='color:red'>*</td></tr></table></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Tasa de Interés Mínima (TEA)</b></font></td>");
                out.println("<td><table><tr><td><input type='text' id='txtH' name='txtH' value='23.872' disabled style='width:100px;text-align:right;'>%</td><td style='color:red'>*</td></tr></table></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Días Entre Cuotas</b></font></td>");
                out.println("<td><table><tr><td><input type='text' id='txtJ' name='txtJ' value='30' disabled style='width:100px;text-align:right;'></td><td style='color:red'>*</td></tr></table></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Modo de pago de cuotas(Capital + Intereses)</b></font></td>");
                out.println("<td><select id='selI' name='selI'>");
                out.println("<option value='1' selected>1 Días fijos </option>");
                out.println("<option value='2'>2 Fechas fijas (A Rebatir) </option>");
                out.println("</select></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Requiere garantía?</b></font></td>");
                out.println("<td><select id='selJ' name='selJ'>");
                out.println("<option value='1'>1 SI </option>");
                out.println("<option value='2' selected>2 NO </option>");
                out.println("</select></td>");
                out.println("</tr>");                
                out.println("<tr style='display:none'>");
                out.println("<td>");
                out.println("<input type='text' id='txtcatalogo' name='txtcatalogo' value='" + b + "'>");
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><div id='divrptaInsertar'>");
                out.println("<input type='hidden' id='txtRptaInsertar' name='txtRptaInsertar' value='NO'>");
                out.println("</div></td>");
                out.println("</tr>");
                out.println("</table>");
                out.println("</form>");
                out.println("</div>");
            } else if ("PERSONA".equals(b) || "PERSC".equals(b)) {
                out.println("<div id='divCatalogoDetalle' style='vertical-align:top'>");
                out.println("<form id='formDetalle' method='post' action=''>");
                out.println("<table id='tCatalogoDetalle' name='tCatalogoDetalle' class='tabla' border='1' style='vertical-align:top'>");
                out.println("<tr>");
                out.println("<th colspan='2'><font color='#385b88' style='font-size: 12px;'><b>Detalle Persona:</b></font>");
                out.println("</th>");
                out.println("</tr>");
                out.println("<tr style='display:none'>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Id</b></font></td>");
                out.println("<td><input type='text' id='txtId' name='txtId' value='' disabled></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>DNI</b></font></td>");
                out.println("<td><input type='text' id='txtDNI' name='txtDNI' disabled value='' onblur=\"buscarcuenta(document.getElementById('txtDNI').value,'DNI');\" onkeypress='return solonumeros(event);' maxlength='8'><font color=red>*</font></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>RUC</b></font></td>");
                out.println("<td><input type='text' id='txtRUC' name='txtRUC' disabled value='' onblur=\"if(document.getElementById('txtDNI').value.lenght<5)buscarcuenta(document.getElementById('txtRUC').value,'RUC');\" onkeypress='return solonumeros(event);' maxlength='12'></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Nombre</b></font></td>");
                out.println("<td><input type='text' id='txtNombre' name='txtNombre' disabled value=''><font color=red>*</font></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Apellidos</b></font></td>");
                out.println("<td><input type='text' id='txtApellidos' name='txtApellidos' disabled value=''><font color=red>*</font></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Email</b></font></td>");
                out.println("<td><input type='text' id='txtEmail' name='txtEmail' disabled value=''></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Ubigeo</b></font></td>");
                out.println("<td><input type='text' id='txtUbigeo' name='txtUbigeo' disabled value='' onkeypress='return solonumeros(event);'></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Telefono</b></font></td>");
                out.println("<td><input type='text' id='txtTelefono' disabled name='txtTelefono' value='' onkeypress='return soloNumeroTelefonico(event);'></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Celular</b></font></td>");
                out.println("<td><input type='text' id='txtCelular' disabled name='txtCelular' value=''></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Url foto</b></font></td>");
                out.println("<td><input type='file' id='txtUrlFoto' name='txtUrlFoto' value='' maxlength='200' onfocus='enviarsession();' accept='image/jpg;image/jpeg;image/gif;image/png;image/bmp'/></td>");
                out.println("</tr>");
                out.println("<tr id='trFoto' style='display:none'>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>foto</b></font></td>");
                out.println("<td><img id='imgfoto' src=''></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Url firma</b></font></td>");
                out.println("<td><input type='file' id='txtUrlFirma' name='txtUrlFirma' value='' maxlength='200' onfocus='enviarsession();' accept='image/jpg;image/jpeg;image/gif;image/png;image/bmp'/></td>");
                out.println("</tr>");
                out.println("<tr id='trFirma' style='display:none'>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>foto</b></font></td>");
                out.println("<td><img src=''></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Direcci&oacute;n</b></font></td>");
                out.println("<td><input type='text' id='txtDireccion' disabled name='txtDireccion' value=''></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Estado</b></font></td>");
                out.println("<td>");
                out.println("<select id='selEstado' name='selEstado'>");
                out.println("<option>ACTIVO</option>");
                out.println("<option>INACTIVO</option>");
                out.println("</select>");
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Categoria</b></font></td>");
                out.println("<td>");
                List result = dao.findAll("from TCategoriaPersona");
                Iterator it = result.iterator();
                out.println("<select id='selCategPersona' name='selCategPersona'>");
                while (it.hasNext()) {
                    TCategoriaPersona catper = (TCategoriaPersona) it.next();
                    out.println("<option value='" + catper.getIdcategoriapersona() + "'>" + catper.getDescripcion() + "</option>");
                }
                out.println("</select>");
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr style='display:none'>");
                out.println("<td>");
                out.println("<input type='text' id='txtcatalogo' name='txtcatalogo' value='" + b + "'>");
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><div id='divrptaInsertar'>");
                out.println("<input type='hidden' id='txtRptaInsertar' name='txtRptaInsertar' value='NO'>");
                out.println("</div></td>");
                out.println("</tr>");
                out.println("</table>");
                out.println("</form>");
                out.println("</div>");
            } else {
                out.println("...");
            }
            out.println("</td>");
            out.println("</tr>");
            out.println("</table>");
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
