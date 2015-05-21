package org.finance.bank.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.finance.bank.bean.*;
import org.finance.bank.model.dao.DAOGeneral;
import org.finance.bank.util.DateUtil;

/**
 *
 * @author roger
 */
public class SExtornarOperacion extends HttpServlet {

    /**
     * ProcessRequest del servlet ExtornarOperacion: (SELF EXTORNO)
     * Este proceso lleva las siguientes acciones a cabo:
     * El Primero es Cambiar de estado a la operacion para que no se tenga que usar
     * como parte contable de la caja. lo cual es necesario pasar los parámetros
     * siguientes: El Id de la operación, ya que esto es muy intrínseco al sistema y luego
     * el id del administrador que se ha autentificado para dicha operacion.
     *
     * Además es necesario la manipulación de los asientos respectivos realizados por la caja
     * en el día, esto ya no se puede usar cuando la caja está cerrada.
     * Aunque no hay mecanismos de verificación de la caja en el día; aunque posteriormente se usará este
     * método de seguridad en las sesiones, por el momento es lógico trabajar y superar algunas
     * limitaciones que se han mostrado en el sistema mismo.
     *
     * A través del response se puede Extornar todas las operaciones
     *
     **/
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String caja1 = session.getAttribute("USER_CODCAJA").toString();
        String idForInitNewestTable;
        String idAdminExtorno = session.getAttribute("ID_USER_ADMIN_AUTORIZER").toString();
        DAOGeneral dao = new DAOGeneral();
        String IdAdminExtorno = request.getParameter("userAdminExt");
        if (IdAdminExtorno == null) {
            IdAdminExtorno = idAdminExtorno;
        }
        String relleno = "";
        try {
            idForInitNewestTable = request.getParameter("IdOperacion").toString();
            TOperacion op = (TOperacion) dao.load(TOperacion.class, idForInitNewestTable);
            if (op == null) {
                out.print("ERROR: NO HAY NADA QUE EXTORNAR!!!<input id='txtExtorno' name='txtExtorno' type='hidden' value='NO'>");
                out.close();
                dao.cerrarSession();
                return;
            }
            int chain = (int) Integer.parseInt(op.getTTipoOperacion().getCodigoTipoOperacion().substring(4));
            if (chain >= 11) {
                out.println("<input id='txtExtorno' name='txtExtorno' type='text' value='NO'>");
                out.close();
                dao.cerrarSession();
                return;
            }
            op.setEstado("EXTORNADO");
            op.setFechaExtornacion(DateUtil.getNOW_S());
            op.setIdAdminExtorno(idAdminExtorno);
            dao.update();
//            boolean estado = false;
            switch (chain) {
                case 1:
                    TRegistroCompraVenta compra = (TRegistroCompraVenta) dao.load(TRegistroCompraVenta.class, idForInitNewestTable);
                    if (compra.getEstado().equals("EXTORNADO")) {
                        out.println("<input id='txtExtorno' name='txtExtorno' type='text' value='SI'>");
                        out.close();
                        dao.cerrarSession();
                        return;
                    }
                    compra.setEstado("EXTORNADO");
                    dao.update();
                    TLogFinance logcompra = (TLogFinance) dao.load(TLogFinance.class, "LOG" + caja1 + op.getTMoneda().getCodMoneda());
                    logcompra.setMontoFinal(logcompra.getMontoFinal().subtract(compra.getMontoRecibido()));
                    logcompra.setMontoRecibido(logcompra.getMontoRecibido().subtract(compra.getMontoRecibido()));
                    logcompra.setActivoCajaybanco(logcompra.getActivoCajaybanco().subtract(compra.getMontoRecibido()));
                    logcompra.setMonto(logcompra.getMonto().subtract(compra.getMontoRecibido()));
                    dao.update();
                    TLogFinance logcompra2 = (TLogFinance) dao.load(TLogFinance.class, "LOG" + caja1 + ((TMoneda) dao.load(TMoneda.class, "PEN")).getCodMoneda());
                    logcompra2.setMontoFinal(logcompra2.getMontoFinal().add(compra.getMontoEntregado()));
                    logcompra2.setMontoEntregado(logcompra2.getMontoEntregado().subtract(compra.getMontoEntregado()));
                    logcompra2.setActivoCajaybanco(logcompra2.getActivoCajaybanco().add(compra.getMontoEntregado()));
                    logcompra2.setMonto(logcompra2.getMonto().add(compra.getMontoEntregado()));
                    dao.update();
                    relleno = "COMPRA EXTORNADA CORRECTAMENTE!!<input id='txtExtorno' name='txtExtorno' type='text' value='SI'>";
                    break;
                case 2:
                    TRegistroCompraVenta venta = (TRegistroCompraVenta) dao.load(TRegistroCompraVenta.class, idForInitNewestTable);
                    if (venta.getEstado().equals("EXTORNADO")) {
                        out.println("<input id='txtExtorno' name='txtExtorno' type='text' value='SI'>");
                        dao.cerrarSession();
                        out.close();
                        return;
                    }
                    venta.setEstado("EXTORNADO");
                    dao.update();
                    TLogFinance logcajav = (TLogFinance) dao.load(TLogFinance.class, "LOG" + caja1 + op.getTMoneda().getCodMoneda());
                    //logcajav.setMontoFinal(new BigDecimal(logcajav.getMontoFinal().doubleValue() + venta.getMontoEntregado().doubleValue()));
                    logcajav.setMontoFinal(logcajav.getMontoFinal().add(venta.getMontoEntregado()));
                    logcajav.setMontoEntregado(logcajav.getMontoEntregado().subtract(venta.getMontoEntregado()));
                    logcajav.setActivoCajaybanco(logcajav.getActivoCajaybanco().add(venta.getMontoEntregado()));
                    logcajav.setMonto(logcajav.getMonto().add(venta.getMontoEntregado()));
                    dao.update();
                    TLogFinance logVenta2 = (TLogFinance) dao.load(TLogFinance.class, "LOG" + caja1 + ((TMoneda) dao.load(TMoneda.class, "PEN")).getCodMoneda());
                    //logVenta2.setMontoFinal(new BigDecimal(logVenta2.getMontoFinal().doubleValue() - venta.getMontoRecibido().doubleValue()));
                    logVenta2.setMontoFinal(logVenta2.getMontoFinal().subtract(venta.getMontoRecibido()));
                    logVenta2.setMontoRecibido(logVenta2.getMontoRecibido().subtract(venta.getMontoRecibido()));
                    logVenta2.setActivoCajaybanco(logVenta2.getActivoCajaybanco().subtract(venta.getMontoRecibido()));
                    logVenta2.setMonto(logVenta2.getMonto().subtract(venta.getMontoRecibido()));
                    dao.update();
//                    estado = true;
                    relleno = "VENTA EXTORNADA CORRECTAMENTE!!<input id='txtExtorno' name='txtExtorno' type='text' value='SI'>";
                    break;
                case 3:
                    TRegistroDepositoRetiro deposito = (TRegistroDepositoRetiro) dao.load(TRegistroDepositoRetiro.class, idForInitNewestTable);
                    deposito.setDateUser(DateUtil.getNOW_S());
                    deposito.setEstado("EXTORNADO");
                    dao.update();
                    TCuentaPersona cuentaDeposito = (TCuentaPersona) dao.load(TCuentaPersona.class, deposito.getTCuentaPersona().getIdcuentapersona());
                    cuentaDeposito.setSaldo(cuentaDeposito.getSaldo().subtract(deposito.getImporte()));
                    BigDecimal sinInte = cuentaDeposito.getSaldoSinInteres();
                    cuentaDeposito.setSaldoSinInteres(cuentaDeposito.getSaldoSinInteres().subtract(deposito.getImporte()));
                    dao.update();
                    if (cuentaDeposito.getSaldoSinInteres().doubleValue() > 0.0D) {
                        cuentaDeposito.setEstado("ACTIVO");
                        dao.update();
                    } else {
                        List l2 = dao.findAll("from TSobregiro where TCuentaPersona.idcuentapersona='" + cuentaDeposito.getIdcuentapersona() + "' order by dateUser desc");
                        for (Iterator it = l2.iterator(); it.hasNext();) {
                            TSobregiro s1 = (TSobregiro) it.next();
                            if (s1.getEstado().equals("CANCELADO")) {
                                s1.setMontoSinInteres(s1.getMontoSinInteres().add(deposito.getImporte()));
                                s1.setMontoActual(s1.getMontoActual().add(deposito.getImporte()));
                                s1.setEstado("ACTIVO");
                                dao.update();
                                break;
                            } else {
                                s1.setMontoActual(s1.getMontoActual().add(deposito.getImporte()));
                                s1.setMontoSinInteres(s1.getMontoSinInteres().add(deposito.getImporte()));
                                s1.setEstado("ACTIVO");
                                dao.update();
                                break;
                            }
                        }
                    }
                    if (sinInte.doubleValue() < deposito.getImporte().doubleValue()) {
                        sinInte = deposito.getImporte().subtract(sinInte);
                    } else {
                        sinInte = BigDecimal.ZERO;
                    }
//                    TDetalleCaja xDCDeposito = (TDetalleCaja) dao.load(TDetalleCaja.class, iniDetalleCaja.detalleActivaCaja(op.getTPersonaCaja().getTCaja().getCodCaja(), deposito.getTCuentaPersona().getTMoneda().getCodMoneda()));
                    //TDetalleCaja xDCDeposito = (TDetalleCaja) dao.load(TDetalleCaja.class, DateUtil.getDate(op.getFd()).replaceAll("/", "") + op.getTPersonaCaja().getTCaja().getCodCaja() + deposito.getTCuentaPersona().getTMoneda().getCodMoneda() + "00");
                    //xDCDeposito.setMontoFinal(xDCDeposito.getMontoFinal().subtract(deposito.getImporte()));
                    //xDCDeposito.setMontoRecibido(xDCDeposito.getMontoRecibido().subtract(deposito.getImporte()));
                    TLogFinance logDeposito = (TLogFinance) dao.load(TLogFinance.class, "LOG" + op.getTPersonaCaja().getTCaja().getCodCaja() + deposito.getTCuentaPersona().getTMoneda().getCodMoneda());
                    logDeposito.setMontoFinal(logDeposito.getMontoFinal().subtract(deposito.getImporte()));
                    logDeposito.setMontoRecibido(logDeposito.getMontoRecibido().subtract(deposito.getImporte()));
                    logDeposito.setActivoCajaybanco(logDeposito.getActivoCajaybanco().subtract(deposito.getImporte()));
                    logDeposito.setActivoCuentaxcobrar(logDeposito.getActivoCuentaxcobrar().add(sinInte));
                    logDeposito.setMonto(logDeposito.getMonto().subtract(sinInte));
                    logDeposito.setPasivo(logDeposito.getPasivo().subtract(deposito.getImporte().subtract(sinInte)));
                    logDeposito.setEncaje(logDeposito.getEncaje().add(sinInte));
                    dao.update();
                    //corregir(deposito.getNumCta(), dao);
                    relleno += "<input id='txtExtorno' name='txtExtorno' type='text' value='SI'>";
                    break;
                case 4:
                    TRegistroDepositoRetiro retiro = (TRegistroDepositoRetiro) dao.load(TRegistroDepositoRetiro.class, idForInitNewestTable);
                    retiro.setEstado("EXTORNADO");
                    retiro.setDateUser(DateUtil.getNOW_S());
                    dao.update();
                    BigDecimal sRetiro = retiro.getTCuentaPersona().getSaldoSinInteres();
                    TCuentaPersona cuentaRetiro = (TCuentaPersona) dao.load(TCuentaPersona.class, retiro.getTCuentaPersona().getIdcuentapersona());
                    cuentaRetiro.setSaldo(cuentaRetiro.getSaldo().add(retiro.getImporte()));
                    cuentaRetiro.setSaldoSinInteres(cuentaRetiro.getSaldoSinInteres().add(retiro.getImporte()));
                    dao.update();
                    BigDecimal mExtornado = BigDecimal.ZERO;
                    if (cuentaRetiro.getEstado().equals("SOBREGIRO")) {
                        if (sRetiro.add(retiro.getImporte()).doubleValue() > 0) {
                            cuentaRetiro.setEstado("ACTIVO");
                            dao.update();
                        }
                        TSobregiro s1 = (TSobregiro) dao.load(TSobregiro.class, retiro.getIddepositoretiro());
                        if (s1 != null) {
                            if (s1.getEstado().equals("ACTIVO") && s1.getMontoSinInteres().doubleValue() > 0D) {
//                                s1.setMontoSinInteres(s1.getMontoSinInteres().add(retiro.getImporte()));
//                                s1.setMontoActual(s1.getMontoActual().add(retiro.getImporte()));
                                mExtornado = s1.getMontoSinInteres();
                                s1.setEstado("EXTORNADO");
                                dao.update();
                            }
                        }
                    }
//                    TDetalleCaja detaCaja = (TDetalleCaja) dao.load(TDetalleCaja.class, iniDetalleCaja.detalleActivaCaja(op.getTPersonaCaja().getTCaja().getCodCaja(),
//                            retiro.getTCuentaPersona().getTMoneda().getCodMoneda()));
                    //TDetalleCaja detaCaja = (TDetalleCaja) dao.load(TDetalleCaja.class, DateUtil.getDate(op.getFd()).replaceAll("/", "") + op.getTPersonaCaja().getTCaja().getCodCaja() + retiro.getTCuentaPersona().getTMoneda().getCodMoneda() + "00");
                    //detaCaja.setMontoFinal(detaCaja.getMontoFinal().add(retiro.getImporte()));
                    //detaCaja.setMontoEntregado(detaCaja.getMontoEntregado().subtract(retiro.getImporte()));
                    TLogFinance logCaja = (TLogFinance) dao.load(TLogFinance.class, "LOG" + op.getTPersonaCaja().getTCaja().getCodCaja() + retiro.getTCuentaPersona().getTMoneda().getCodMoneda());
                    logCaja.setMontoFinal(logCaja.getMontoFinal().add(retiro.getImporte()));
                    logCaja.setMontoEntregado(logCaja.getMontoEntregado().subtract(retiro.getImporte()));
                    logCaja.setActivoCajaybanco(logCaja.getActivoCajaybanco().subtract(retiro.getImporte()));
                    logCaja.setActivoCuentaxcobrar(logCaja.getActivoCuentaxcobrar().subtract(mExtornado));
                    logCaja.setMonto(logCaja.getMonto().add(mExtornado));
                    logCaja.setPasivo(logCaja.getPasivo().add(retiro.getImporte().subtract(mExtornado)));
                    logCaja.setEncaje(logCaja.getEncaje().subtract(mExtornado));
                    dao.update();
                    TSobregiro sgretiro = (TSobregiro) dao.load(TSobregiro.class, idForInitNewestTable);
                    if (sgretiro != null) {
                        sgretiro.setEstado("EXTORNADO");
                        dao.update();
                    }
                    //corregir(retiro.getNumCta(), dao);
                    relleno += "<input id='txtExtorno' name='txtExtorno' type='text' value='SI'>";
                    break;
                case 5:
                    TRegistroGiro giro = (TRegistroGiro) dao.load(TRegistroGiro.class, idForInitNewestTable);
                    if (giro.getEstado().equals("EXTORNADO")) {
                        out.println("<input id='txtExtorno' name='txtExtorno' type='text' value='SI'>");
                        out.close();
                        dao.cerrarSession();
                        return;
                    }
                    giro.setEstado("EXTORNADO");
                    dao.update();
                    break;
                case 6:
                    TRegistroPrestamo rp = (TRegistroPrestamo) dao.load(TRegistroPrestamo.class, idForInitNewestTable);
                    if ("EXTORNADO".equals(rp.getEstado())) {
                        out.println("<input id='txtExtorno' name='txtExtorno' type='text' value='SI'>");
                    } else {
                        rp.setEstado("EXTORNADO");
                        dao.update();
                        BigDecimal misaldos3 = rp.getTCuentaPersona().getSaldo();
                        TCuentaPersona cuenta2 = (TCuentaPersona) dao.load(TCuentaPersona.class, rp.getTCuentaPersona().getIdcuentapersona());
                        misaldos3 = misaldos3.add(rp.getMonto());
                        cuenta2.setSaldo(misaldos3);
                        cuenta2.setSaldoSinInteres(misaldos3);
                        cuenta2.setEstado("ACTIVO");
                        dao.update();
//                        String idIniDcxp = iniDetalleCaja.detalleActivaCaja(op.getTPersonaCaja().getTCaja().getCodCaja(), rp.getTCuentaPersona().getTMoneda().getCodMoneda());
                        //TDetalleCaja dep = (TDetalleCaja) dao.load(TDetalleCaja.class, DateUtil.getDate(op.getFd()).replaceAll("/", "") + op.getTPersonaCaja().getTCaja().getCodCaja() + rp.getTCuentaPersona().getTMoneda().getCodMoneda() + "00");
                        //dep.setMontoFinal(dep.getMontoFinal().add(rp.getMonto()));
                        //dep.setMontoEntregado(dep.getMontoEntregado().subtract(rp.getMonto()));
                        TLogFinance log = (TLogFinance) dao.load(TLogFinance.class, "LOG" + op.getTPersonaCaja().getTCaja().getCodCaja() + rp.getTCuentaPersona().getTMoneda().getCodMoneda());
                        log.setMontoFinal(log.getMontoFinal().add(rp.getMonto()));
                        log.setMontoEntregado(log.getMontoEntregado().subtract(rp.getMonto()));
                        log.setActivoCajaybanco(log.getActivoCajaybanco().add(rp.getMonto()));
                        log.setActivoCuentaxcobrar(log.getActivoCuentaxcobrar().subtract(rp.getMonto()));
                        log.setMonto(log.getMonto().add(rp.getMonto()));
                        log.setPRespaldo(log.getPRespaldo().subtract(rp.getMonto()));
                        dao.update();
                        out.println("<input id='txtExtorno' name='txtExtorno' type='text' value='SI'>");
                    }
                    break;
                case 7:
                    TCobranza c = (TCobranza) dao.load(TCobranza.class, idForInitNewestTable);
                    if ("EXTORNADO".equals(c.getEstado())) {
                        out.println("<input id='txtExtorno' name='txtExtorno' type='text' value='SI'>");
                    } else {
                        c.setEstado("EXTORNADO");
                        dao.update();
                        BigDecimal misaldos3 = c.getTDetallePrestamo().getTRegistroPrestamo().getTCuentaPersona().getSaldo();
                        TCuentaPersona cuenta3 = (TCuentaPersona) dao.load(TCuentaPersona.class, c.getTDetallePrestamo().getTRegistroPrestamo().getTCuentaPersona().getIdcuentapersona());
                        misaldos3 = misaldos3.subtract(c.getTDetallePrestamo().getMontoCapital());
                        cuenta3.setSaldo(misaldos3);
                        cuenta3.setSaldoSinInteres(misaldos3);
                        cuenta3.setEstado("PRESTAMO");
                        dao.update();
//                        TDetalleCaja detaCaja3 = (TDetalleCaja) dao.load(TDetalleCaja.class, iniDetalleCaja.detalleActivaCaja(op.getTPersonaCaja().getTCaja().getCodCaja(), c.getTOperacion().getTMoneda().getCodMoneda()));
                        //TDetalleCaja detaCaja3 = (TDetalleCaja) dao.load(TDetalleCaja.class, DateUtil.getDate(op.getFd()).replaceAll("/", "") + op.getTPersonaCaja().getTCaja().getCodCaja() + c.getTOperacion().getTMoneda().getCodMoneda() + "00");
                        //detaCaja3.setMontoFinal(detaCaja3.getMontoFinal().subtract(c.getTDetallePrestamo().getMontoTotal()));
                        //detaCaja3.setMontoRecibido(detaCaja3.getMontoRecibido().subtract(c.getTDetallePrestamo().getMontoTotal()));
                        TLogFinance log3 = (TLogFinance) dao.load(TLogFinance.class, "LOG" + op.getTPersonaCaja().getTCaja().getCodCaja() + c.getTOperacion().getTMoneda().getCodMoneda());
                        log3.setMontoFinal(log3.getMontoFinal().subtract(c.getTDetallePrestamo().getMontoTotal()));
                        log3.setMontoRecibido(log3.getMontoRecibido().subtract(c.getTDetallePrestamo().getMontoTotal()));
                        log3.setActivoCajaybanco(log3.getActivoCajaybanco().subtract(c.getTDetallePrestamo().getMontoTotal()));
                        log3.setActivoCuentaxcobrar(log3.getActivoCuentaxcobrar().add(c.getTDetallePrestamo().getInteresPrestamo().add(c.getTDetallePrestamo().getInteresMoratorio())));
                        log3.setMonto(log3.getMonto().subtract(c.getTDetallePrestamo().getMontoTotal()));
                        log3.setPRespaldo(log3.getPRespaldo().add(c.getTDetallePrestamo().getInteresPrestamo().add(c.getTDetallePrestamo().getInteresMoratorio())));
                        dao.update();
                        out.println("<input id='txtExtorno' name='txtExtorno' type='text' value='SI'>");
                    }
                    break;
                case 8:
                    try {
                        TRegistroGiro giro_cobro = (TRegistroGiro) dao.findAll("TRegistroGiro where idoperacioncobro='" + idForInitNewestTable + "'").get(0);
                        giro_cobro.setEstado("ESPERA");
                        dao.update();
                    } catch (Exception ex) {
                        out.print("FALLO DEL OBJETO!!!");
                    }
                    break;
                case 9:
                    TTransferenciaCaja ttc = (TTransferenciaCaja) dao.load(TTransferenciaCaja.class, idForInitNewestTable);
                    if (ttc.getEstado().equals("ACTIVO")) {
                        ttc.setEstado("EXTORNADO");
                        ttc.setFecha(DateUtil.getNOW_S());
                        dao.update();
                        TLogFinance log1 = (TLogFinance) dao.load(TLogFinance.class, "LOG" + ttc.getTCaja().getCodCaja() + ttc.getTOperacion().getTMoneda().getCodMoneda());
                        log1.setMontoFinal(log1.getMontoFinal().add(ttc.getMonto()));
                        log1.setMontoEntregado(log1.getMontoEntregado().subtract(ttc.getMonto()));
                        log1.setActivoCajaybanco(log1.getActivoCajaybanco().add(ttc.getMonto()));
                        log1.setMonto(log1.getMonto().add(ttc.getMonto()));
                        dao.update();
                        if (ttc.getIdope() != null) {
                            TOperacion op2 = (TOperacion) dao.load(TOperacion.class, ttc.getIdope());
                            op2.setEstado("EXTORNADO");
                            op2.setIdAdminExtorno(idAdminExtorno);
                            op2.setFechaExtornacion(DateUtil.getNOW_S());
                            dao.update();
                            //TDetalleCaja desca = (TDetalleCaja) dao.load(TDetalleCaja.class, DateUtil.getDate(op.getFd()).replaceAll("/", "") + ttc.getCodCajaDestino() + ttc.getTOperacion().getTMoneda().getCodMoneda() + "00");
                            TLogFinance logDes = (TLogFinance) dao.load(TLogFinance.class, "LOG" + ttc.getCodCajaDestino() + ttc.getTOperacion().getTMoneda().getCodMoneda());
                            if (ttc.getTipo().equals("911")) {
                                //desca.setMontoInicial(desca.getMontoInicial().subtract(ttc.getMonto()));
                                logDes.setMontoInicial(logDes.getMontoInicial().subtract(ttc.getMonto()));
                            } else {
                                logDes.setMontoRecibido(logDes.getMontoRecibido().subtract(ttc.getMonto()));
                            }
                            logDes.setMontoFinal(logDes.getMontoFinal().subtract(ttc.getMonto()));
                            logDes.setActivoCajaybanco(logDes.getActivoCajaybanco().subtract(ttc.getMonto()));
                            logDes.setMonto(logDes.getMonto().subtract(ttc.getMonto()));
                            dao.update();
                        }
                        out.println("<input id='txtExtorno' name='txtExtorno' type='text' value='SI'>");
                        dao.cerrarSession();
                        out.close();
                        return;
                    }
                    if ("CAPITAL SOCIAL".equals(ttc.getTipo())) {
                        out.println("ERROR:<input id='txtExtorno' name='txtExtorno' type='text' value='NO'>");
                        out.close();
                        dao.cerrarSession();
                        return;
                    }
                    if (ttc.getEstado().equals("EXTORNADO")) {
                        out.println("<input id='txtExtorno' name='txtExtorno' type='text' value='SI'>");
                        dao.cerrarSession();
                        out.close();
                        return;
                    }
                    relleno += "<input id='txtExtorno' name='txtExtorno' type='text' value='SI'>";
                    break;
                case 10:
                    TRegistroOtros ro = (TRegistroOtros) dao.load(TRegistroOtros.class, idForInitNewestTable);
                    ro.setEstado("EXTORNADO");
                    dao.update();
//                    TDetalleCaja detallecaja = (TDetalleCaja) dao.load(TDetalleCaja.class,
//                            iniDetalleCaja.detalleActivaCaja(op.getTPersonaCaja().getTCaja().getCodCaja(), op.getTMoneda().getCodMoneda()));
                    //TDetalleCaja detallecaja = (TDetalleCaja) dao.load(TDetalleCaja.class, DateUtil.getDate(op.getFd()).replaceAll("/", "") + op.getTPersonaCaja().getTCaja().getCodCaja() + op.getTMoneda().getCodMoneda() + "00");
                    //detallecaja.setMontoFinal(detallecaja.getMontoFinal().add(ro.getMonto()));
                    //detallecaja.setMontoEntregado(detallecaja.getMontoEntregado().subtract(ro.getMonto()));
                    //detallecaja.setDateUser(DateUtil.getNOW_S());
                    TLogFinance log = (TLogFinance) dao.load(TLogFinance.class, "LOG" + op.getTPersonaCaja().getTCaja().getCodCaja() + op.getTMoneda().getCodMoneda());
                    log.setMontoFinal(log.getMontoFinal().add(ro.getMonto()));
                    log.setMontoEntregado(log.getMontoEntregado().subtract(ro.getMonto()));
                    dao.update();
                    out.println("ERROR:<input id='txtExtorno' name='txtExtorno' type='text' value='SI'>");
                    out.close();
                    return;
//                    break;
                case 11:
                    out.println("ERROR:<input id='txtExtorno' name='txtExtorno' type='text' value='NO'>");
                    out.close();
                    dao.cerrarSession();
//                    break;
                    return;
                case 12:
                    out.println("ERROR:<input id='txtExtorno' name='txtExtorno' type='text' value='NO'>");
                    out.close();
                    dao.cerrarSession();
                    break;
                case 13:
                    out.println("ERROR:<input id='txtExtorno' name='txtExtorno' type='text' value='NO'>");
                    out.close();
                    dao.cerrarSession();
                    return;
//                    break;
                case 14:
                    out.println("ERROR:<input id='txtExtorno' name='txtExtorno' type='text' value='NO'>");
                    out.close();
                    dao.cerrarSession();
                    return;
//                    break;
                default:
                    relleno = "ERROR: NO hay NADA que extornar!!!<input id='txtExtorno' name='txtExtorno' type='text' value='NO'>";
            }
        } finally {
            out.print(relleno);
            out.close();
            dao.cerrarSession();
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


    }
    // </editor-fold>

    /**
     * Esta Función genera errores...
     **/
//    private void corregir(String cta, DAOGeneral dao) {
//        String sqlRun = "from TRegistroDepositoRetiro where numCta='" + cta + "' and (not estado='EXTORNADO') and fecha >='" + DateUtil.getDate(new Date()) + "%' order by fecha";
//        List result = dao.findAll(sqlRun);
//        Iterator it = result.iterator();
//        boolean first = true;
//        BigDecimal rentail = BigDecimal.ZERO;
//        while (it.hasNext()) {
//            TRegistroDepositoRetiro depret = (TRegistroDepositoRetiro) it.next();
//            if (depret.getEstado().equals("EXTORNADO")) {
//                continue;
//            }
//            if (first) {
//                rentail = depret.getTOperacion().getSaldofinal();
//                first = false;
//                continue;
//            }
//            if (depret.getTOperacion().getTTipoOperacion().getCodigoTipoOperacion().equals("TIPC3")) {
//                rentail = rentail.add(depret.getImporte());
//            } else {
//                rentail = rentail.subtract(depret.getImporte());
//            }
//            TOperacion o = (TOperacion) dao.load(TOperacion.class, depret.getTOperacion().getIdOperacion());
//            o.setSaldofinal(rentail);
//            dao.update();
//            TCuentaPersona c = (TCuentaPersona) dao.load(TCuentaPersona.class, depret.getTCuentaPersona().getIdcuentapersona());
////            c.setSaldo(rentail);
////            c.setSaldoSinInteres(rentail);
//            dao.update();
//        }
//    }
}

