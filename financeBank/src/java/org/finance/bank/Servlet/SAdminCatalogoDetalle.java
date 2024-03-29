package org.finance.bank.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.finance.bank.bean.TCaja;
import org.finance.bank.bean.TCategoriaPersona;
import org.finance.bank.bean.TDepartamento;
import org.finance.bank.bean.TDistrito;
import org.finance.bank.bean.TFilial;
import org.finance.bank.bean.TPersona;
import org.finance.bank.bean.TProvincia;
import org.finance.bank.bean.TTipoCredito;
import org.finance.bank.model.dao.DAOGeneral;

/**
 *
 * @author ZAMORA
 */
public class SAdminCatalogoDetalle extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        request.getSession(true);
        DAOGeneral dao = new DAOGeneral();
        try {
            String idcat = request.getParameter("a");
            String catalogo = request.getParameter("b");
            if (catalogo == null || idcat == null) {
                out.close();
                return;
            }
            if (catalogo.equals("FILIAL")) {
                String hql = "from TFilial fil where fil.codFilial='" + idcat + "' ";
                List l = dao.findAll(hql);
                if (!(l.size() > 0)) {
                    out.print("No hay un filial a la vista.");
                    out.close();
                    return;
                }
                TFilial filial = (TFilial) l.get(0);
                out.println("<form id='formDetalle' method='post' action=''>");
                out.println("<table id='tDetalle' name='tDetalle' class='tabla' border='1' style='vertical-align:top'>");
                out.println("<tr>");
                out.println("<td>");
                out.println("<table id='tCatalogoDetalle' name='tCatalogoDetalle' class='tabla' border='1' style='vertical-align:top'>");
                out.println("<tr>");
                out.println("<th colspan='2'><font color='#385b88' style='font-size: 12px;'><b>Detalle Filial:</b></font>");
                out.println("</th>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Codigo</b></font></td>");
                out.println("<td><table><tr><td><input type='text' readonly id='txtCodigo' name='txtCodigo' value='" + filial.getCodFilial() + "' disabled></td><td style='color:red'>*</td></tr></table></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Nombre</b></font></td>");
                out.println("<td><table><tr><td><input type='text' id='txtNombre' name='txtNombre' value='" + filial.getNombre() + "'></td><td style='color:red'>*</td></tr></table></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Direci&oacute;n</b></font></td>");
                out.println("<td><input type='text' id='txtDireccion' name='txtDireccion' value='" + filial.getDireccion() + "'></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Estado</b></font></td>");
                out.println("<td>");
                String banActi = "";
                if (filial.getEstado().equals("INACTIVO")) {
                    banActi = "selected";
                }
                out.println("<select id='selEstado' name='selEstado'>");
                out.println("<option>ACTIVO</option>");
                out.println("<option " + banActi + ">INACTIVO</option>");
                out.println("</option>");
                out.println("</select>");
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Teléfono</b></font></td>");
                out.println("<td><input type='text' id='txtTelefono' name='txtTelefono' value='" + filial.getTelefono() + "'></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Codigo para numero cuenta</b></font></td>");
                out.println("<td><table><tr><td><input type='text' readonly id='txtCodNumCuenta' name='txtCodNumCuenta' value='" + filial.getCodParaNumCuenta() + "'></td><td style='color:red'>*</td></tr></table></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Departamento</b></font></td>");
                out.println("<td>");
                List result = dao.findAll("from TDepartamento");
                Iterator it = result.iterator();
                String codDepartamento = filial.getTDistrito().getTProvincia().getTDepartamento().getIddepartamento();
                String miban;
                out.println("<select id='seldepartamento' name='seldepartamento' onchange='listarProvincia();'>");
                while (it.hasNext()) {
                    miban = "";
                    TDepartamento departamento = (TDepartamento) it.next();
                    if (departamento.getIddepartamento().equals(codDepartamento)) {
                        miban = "selected";
                    }
                    out.println("<option value='" + departamento.getIddepartamento() + "' " + miban + ">" + departamento.getDescripcion() + "</option>");
                }
                out.println("</select>");
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Provincia</b></font></td>");
                out.println("<td>");
                String codProvincia = filial.getTDistrito().getTProvincia().getIdprovincia();
                result = dao.findAll("from TProvincia pro where pro.TDepartamento='" + codDepartamento + "'");
                it = result.iterator();
                out.println("<div id='divprovincia'>");
                out.println("<select id='selprovincia' name='selprovincia' onchange='listarDistrito();'>");
                while (it.hasNext()) {
                    miban = "";
                    TProvincia provincia = (TProvincia) it.next();
                    if (provincia.getIdprovincia().equals(codProvincia)) {
                        miban = "selected";
                    }
                    out.println("<option value='" + provincia.getIdprovincia() + "' " + miban + ">" + provincia.getDescripcion() + "</option>");
                }
                out.println("</select>");
                out.println("</div>");
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Distrito</b></font></td>");
                out.println("<td>");
                result = dao.findAll("from TDistrito dis where dis.TProvincia='" + codProvincia + "'");
                it = result.iterator();
                String codDistrito = filial.getTDistrito().getIddistrito();
                out.println("<div id='divDistrito'>");
                out.println("<select id='seldistrito' name='seldistrito'>");
                while (it.hasNext()) {
                    miban = "";
                    TDistrito distrito = (TDistrito) it.next();
                    if (distrito.getIddistrito().equals(codDistrito)) {
                        miban = "selected";
                    }
                    out.println("<option value='" + distrito.getIddistrito() + "' " + miban + ">" + distrito.getDescripcion() + "</option>");
                }
                out.println("</select>");
                out.println("</div>");
                out.println("</td>");
                out.println("</tr>");
                out.println("</table>");
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr style='display:none'>");
                out.println("<td>");
                out.println("<input type='text' id='txtcatalogo' name='txtcatalogo' value='" + catalogo + "'>");
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><div id='divrptaInsertar'>");
                out.println("<input type='hidden' id='txtRptaInsertar' name='txtRptaInsertar' value='NO'>");
                out.println("</div></td>");
                out.println("</tr>");
                out.println("</table>");
                out.println("</form>");
            } else if (catalogo.equals("CAJA")) {
                List l = dao.findAll("from TCaja caja where caja.codCaja='" + idcat + "'");
                if (!(l.size() > 0)) {
                    out.print("No hay una caja " + idcat + " a la vista.");
                    out.close();
                    return;
                }
                TCaja caja = (TCaja) l.get(0);
                out.println("<form id='formDetalle' method='post' action=''>");
                out.println("<table id='tDetalle' name='tDetalle' class='tabla' border='1' style='vertical-align:top'>");
                out.println("<tr>");
                out.println("<td>");
                out.println("<table id='tCatalogoDetalle' name='tCatalogoDetalle' class='tabla' border='1' style='vertical-align:top'>");
                out.println("<tr>");
                out.println("<th colspan='2'><font color='#385b88' style='font-size: 12px;'><b>Detalle Caja:</b></font>");
                out.println("</th>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Codigo</b></font></td>");
                out.println("<td><table><tr><td><input type='text' id='txtCodigo' readonly name='txtCodigo' value='" + caja.getCodCaja() + "' disabled></td><td style='color:red'>*</td></tr></table></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Nombre</b></font></td>");
                out.println("<td><table><tr><td><input type='text' id='txtNombre' name='txtNombre' value='" + caja.getNombreCaja() + "'></td><td style='color:red'>*</td></tr></table></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Filial</b></font></td>");
                out.println("<td><div id='ulfi'>");
                List result = dao.findAll("from TFilial fil where fil.estado='ACTIVO'");
                Iterator it = result.iterator();
                String codfilial = caja.getTFilial().getCodFilial();
                String miban = "";
                out.println("<input id='ulfit' type='hidden' value='1'>"
                        + "<select disabled id='selfilial' name='selfilial'>");
                while (it.hasNext()) {
                    miban = "";
                    TFilial filial = (TFilial) it.next();
                    if (filial.getCodFilial().equals(codfilial)) {
                        miban = "selected";
                    }
                    out.println("<option value='" + filial.getCodFilial() + "' " + miban + ">" + filial.getNombre() + "</option>");
                }
                out.println("</select></div>");
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Tipo</b></font></td>");
                String ban1 = "";
                if (caja.getTipo().equals("SECONDARY")) {
                    ban1 = "selected";
                }
                out.println("<td>");
                out.println("<select id='selTipo' name='selTipo'>");
                out.println("<option value='PRIMARY'>PRIMARIA</option>");
                out.println("<option " + ban1 + " value='SECONDARY'>SECUNDARIA</option>");
                out.println("</select>");
                out.println("</td>");
                out.println("</tr>");
                String banActi = "";
                if (caja.getEstado().equals("INACTIVO")) {
                    banActi = "selected";
                }
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Estado</b></font></td>");
                out.println("<td>");
                out.println("<select id='selEstado' name='selEstado'>");
                out.println("<option>ACTIVO</option>");
                out.println("<option " + banActi + ">INACTIVO</option>");
                out.println("</select>");
                out.println("</td>");
                out.println("</tr>");
                out.println("</table>");
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr style='display:none'>");
                out.println("<td>");
                out.println("<input type='text' id='txtcatalogo' name='txtcatalogo' value='" + catalogo + "'>");
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
            } else if (catalogo.equals("A")) {
                List l = dao.findAll("from TTipoCredito where idtipocredito='" + idcat + "'");
                for (Iterator it = l.iterator(); it.hasNext();) {
                    TTipoCredito c = (TTipoCredito) it.next();
                    out.println("<form id='formDetalle' method='post' action=''>");
                    out.println("<table id='tCatalogoDetalle' name='tCatalogoDetalle' class='tabla' border='1' style='vertical-align:top'>");
                    out.println("<tr>");
                    out.println("<th colspan='2'><font color='#385b88' style='font-size: 12px;'><b>Detalle de Cr&eacute;ditos:</b></font>");
                    out.println("</th>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Nombre</b></font></td>");
                    out.println("<td><table><tr><td><input type='text' id='txtN' name='txtN' value='" + c.getNombre() + "'></td><td style='color:red'>*</td></tr></table></td>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Descripci&oacute;n</b></font></td>");
                    out.println("<td><input type='text' id='txtD' name='txtD' value='" + c.getDescripcion() + "'></td>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Monto Máximo (EN SOLES)</b></font></td>");
                    out.println("<td><table><tr><td><input type='text' id='txtM' name='txtM' value='" + c.getMontoMaximo() + "' style='text-align:right;'></td><td style='color:red'>*</td></tr></table></td>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Número de cuotas máxima</b></font></td>");
                    out.println("<td><table><tr><td><input type='text' id='txtI' name='txtI' value='" + c.getNumeroCuotasMax() + "' style='text-align:right;'></td><td style='color:red'>*</td></tr></table></td>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Tasa de Interés Máxima(TEA) a 360 días</b></font></td>");
                    out.println("<td><table><tr><td><input type='text' id='txtA' name='txtA' value='" + c.getTasaInteresMax() + "' style='width:100px;text-align:right;'>%</td><td style='color:red'>*</td></tr></table></td>");
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
                    out.println("<td><table><tr><td><input type='text' id='txtB' name='txtB' value='" + c.getTasaInteresMoratoria() + "' style='width:100px;text-align:right;'>%</td><td style='color:red'>*</td></tr></table></td>");
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
                    out.println("<td><table><tr><td><input type='text' id='txtG' name='txtG' value='" + c.getNumeroCuotasMin() + "' style='width:160px;text-align:right;'></td><td style='color:red'>*</td></tr></table></td>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Monto Mínimo (EN SOLES)</b></font></td>");
                    out.println("<td><table><tr><td><input type='text' id='txtE' name='txtE' value='" + c.getMontoMinimo() + "' disabled style='width:160px;text-align:right;'></td><td style='color:red'>*</td></tr></table></td>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Tasa de Interés Mínima (TEA)</b></font></td>");
                    out.println("<td><table><tr><td><input type='text' id='txtH' name='txtH' value='" + c.getTasaInteresMin() + "' disabled style='width:100px;text-align:right;'>%</td><td style='color:red'>*</td></tr></table></td>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Días Entre Cuotas</b></font></td>");
                    out.println("<td><table><tr><td><input type='text' id='txtJ' name='txtJ' value='" + c.getDiasEntreCuotas() + "' disabled style='width:100px;text-align:right;'></td><td style='color:red'>*</td></tr></table></td>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Modo de pago de cuotas(Capital + Intereses)</b></font></td>");
                    out.println("<td><select id='selI' name='selI'>");
                    out.println("<option value='1'>1 Días fijos </option>");
                    out.println("<option value='2' " + (c.getFormaCalculoIntereses() == 2 ? "selected" : "") + ">2 Fechas fijas (A Rebatir) </option>");
                    out.println("</select></td>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Requiere garantía?</b></font></td>");
                    out.println("<td><select id='selJ' name='selJ'>");
                    out.println("<option value='1'>1 SI </option>");
                    out.println("<option value='2' " + (c.getRequiereGarantia() == 2 ? "selected" : "") + ">2 NO </option>");
                    out.println("</select></td>");
                    out.println("</tr>");
                    out.println("<tr style='display:none'>");
                    out.println("<td>");
                    out.print("<input type='text' id='txtId' name='txtId' value='" + c.getIdtipocredito() + "' disabled>");
                    out.println("<input type='text' id='txtcatalogo' name='txtcatalogo' value='" + catalogo + "'>");
                    out.println("</td>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println("<td><div id='divrptaInsertar'>");
                    out.println("<input type='hidden' id='txtRptaInsertar' name='txtRptaInsertar' value='NO'>");
                    out.println("</div></td>");
                    out.println("</tr>");
                    out.println("</table>");
                    out.println("</form>");
                }
                if (l.isEmpty()) {
                    out.print("No hay un detalle " + idcat + " a la vista.");
                    out.close();
                    return;
                }
            } else if (catalogo.equals("PERSONA") || catalogo.equals("PERSC")) {
                List l = dao.findAll("from TPersona per where per.idUserPk='" + idcat + "'");
                if (!(l.size() > 0)) {
                    out.print("No hay una PERSONA a la vista.");
                    out.close();
                    dao.cerrarSession();
                    return;
                }
                TPersona persona = (TPersona) l.get(0);
                out.println("<form id='formDetalle' method='post' action=''>");
                out.println("<table id='tDetalle' name='tDetalle' class='tabla' border='1' style='vertical-align:top'>");
                out.println("<tr>");
                out.println("<td>");
                out.println("<table id='tCatalogoDetalle' name='tCatalogoDetalle' class='tabla' border='1' style='vertical-align:top'>");
                out.println("<tr>");
                out.println("<th colspan='2'><font color='#385b88' style='font-size: 12px;'><b>Detalle Persona:</b></font>");
                out.println("</th>");
                out.println("</tr>");
                out.println("<tr style='display:none'>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Id</b></font></td>");
                out.println("<td><input type='text' id='txtId' name='txtId' value='" + persona.getIdUserPk() + "' disabled></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>DNI</b></font></td>");
                out.println("<td><table><tr><td><input type='text' id='txtDNI' name='txtDNI' value='" + (persona.getDocIdentidad() == null ? "" : persona.getDocIdentidad()) + "' onkeypress='return solonumeros(event);' maxlength='8'></td><td style='color:red'>*</td></tr></table></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>RUC</b></font></td>");
                out.println("<td><input type='text' id='txtRUC' name='txtRUC' value='" + (persona.getRuc() == null ? "" : persona.getRuc()) + "' onkeypress='return solonumeros(event);' maxlength='12'></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Nombre</b></font></td>");
                out.println("<td><table><tr><td><input type='text' id='txtNombre' name='txtNombre' value='" + persona.getNombre() + "'></td><td style='color:red'>*</td></tr></table></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Apellidos</b></font></td>");
                out.println("<td><table><tr><td><input type='text' id='txtApellidos' name='txtApellidos' value='" + persona.getApellidos() + "'></td><td style='color:red'>*</td></tr></table></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Email</b></font></td>");
                out.println("<td><input type='text' id='txtEmail' name='txtEmail' value='" + (persona.getEmail() == null ? "" : persona.getEmail()) + "'></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Ubigeo</b></font></td>");
                out.println("<td><input type='text' id='txtUbigeo' name='txtUbigeo' value='" + (persona.getUbigeo() == null ? "" : persona.getUbigeo()) + "'></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Telefono</b></font></td>");
                out.println("<td><input type='text' id='txtTelefono' name='txtTelefono' value='" + (persona.getTelefono() == null ? "" : persona.getTelefono()) + "'></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Celular</b></font></td>");
                out.println("<td><input type='text' id='txtCelular' name='txtCelular' value='" + (persona.getCelular() == null ? "" : persona.getCelular()) + "'></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Url foto</b></font></td>");
                String widthh = "";
                if (persona.getUrlFoto() != null) {
                    widthh = "width='200px'";
                    out.println("<td><input type='text' id='txtUrlFoto' name='txtUrlFoto' value='" + (persona.getUrlFoto() == null ? "" : persona.getUrlFoto()) + "'></td>");
                    out.println("</tr>");
                    out.println("<tr id='trFoto'>");
                    out.println("<td><font color='#385b88' style='font-size: 12px;'><b>foto</b></font></td>");
                    Logger.getLogger(SAdminCatalogoDetalle.class.getName()).log(Level.INFO, "pers  = " + persona.getUrlFoto());
                    if (persona.getUrlFoto().replace(" ", "").length() > 6) {
                        out.println("<td><img id='imgfoto' src='" + persona.getUrlFoto() + "'" + widthh + "></td>");
                    } else {
                        out.println("<td>Vuelva a subir la foto en otro momento</td>");
                    }
                } else {
                    out.println("<td><input type='text' id='txtUrlFoto' name='txtUrlFoto' value='" + (persona.getUrlFoto() == null ? "" : persona.getUrlFoto()) + "'></td>");
                    out.println("</tr>");
                    out.println("<tr id='trFoto'>");
                }
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Url firma</b></font></td>");
                out.println("<td><input type='text' id='txtUrlFirma' name='txtUrlFirma' value='" + (persona.getUrlFirma() == null ? "" : persona.getUrlFirma()) + "'></td>");
                out.println("</tr>");
                out.println("<tr id='trFirma'>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>firma</b></font></td>");
                String widthhh = "";
                if (persona.getUrlFirma() != null) {
                    widthhh = "width='200px'";
                    Logger.getLogger(SAdminCatalogoDetalle.class.getName()).log(Level.INFO, "pers  = " + persona.getUrlFirma());
                    if (persona.getUrlFirma().replace(" ", "").length() > 6) {
                        out.println("<td><img id='imgFirma' src='" + persona.getUrlFirma() + "'" + widthhh + "></td>");
                    } else {
                        out.println("<td>Reactualice la firma</td>");
                    }
                }
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Direcci&oacute;n</b></font></td>");
                out.println("<td><input type='text' id='txtDireccion' name='txtDireccion' value='" + (persona.getDireccion() == null ? "" : persona.getDireccion()) + "'></td>");
                out.println("</tr>");
                String banActi = "";
                if (persona.getEstado().equals("INACTIVO")) {
                    banActi = "selected";
                } else {
                    banActi = "";
                }
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Estado</b></font></td>");
                out.println("<td>");
                out.println("<select id='selEstado' name='selEstado'>");
                out.println("<option " + banActi + ">ACTIVO</option>");
                out.println("<option " + banActi + ">INACTIVO</option>");
                out.println("</select>");
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<tr>");
                out.println("<td><font color='#385b88' style='font-size: 12px;'><b>Categoria</b></font></td>");
                out.println("<td>");
                List result = dao.findAll("from TCategoriaPersona");
                Iterator it = result.iterator();
                String codcatper = persona.getTCategoriaPersona().getIdcategoriapersona();
                String miban = "";
                out.println("<select id='selCategPersona' name='selCategPersona'>");
                while (it.hasNext()) {
                    miban = "";
                    TCategoriaPersona catper = (TCategoriaPersona) it.next();
                    if (catper.getIdcategoriapersona().equals(codcatper)) {
                        miban = "selected";
                    }
                    out.println("<option value='" + catper.getIdcategoriapersona() + "' " + miban + ">" + catper.getDescripcion() + "</option>");
                }
                out.println("</select>");
                out.println("</td>");
                out.println("</tr>");
                out.println("</table>");
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr'>");
                out.println("<td>CREADO:" + persona.getDateUser());
                out.println("<input type='hidden' id='txtcatalogo' name='txtcatalogo' value='" + catalogo + "'>");
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td><div id='divrptaInsertar'>");
                out.println("<input type='hidden' id='txtRptaInsertar' name='txtRptaInsertar' value='NO'>");
                out.println("</div></td>");
                out.println("</tr>");
                out.println("</table>");
                out.println("</form>");
            }
        } finally {
            dao.cerrarSession();
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
