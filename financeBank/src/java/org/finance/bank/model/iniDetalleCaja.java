package org.finance.bank.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.finance.bank.bean.TCaja;
import org.finance.bank.bean.TDenominacionMoneda;
import org.finance.bank.bean.TDetalleCaja;
import org.finance.bank.bean.TDetalleSuma;
import org.finance.bank.bean.TMoneda;
import org.finance.bank.bean.TOperacion;
import org.finance.bank.bean.TPersonaCaja;
import org.finance.bank.bean.TSumaMoneda;
import org.finance.bank.bean.TTipoOperacion;
import org.finance.bank.bean.TTransferenciaCaja;
import org.finance.bank.model.dao.DAOGeneral;
import org.finance.bank.util.DateUtil;
import org.finance.bank.util.numeroOperacion;

/**
 *
 * @author ronald
 */
public class iniDetalleCaja {

    static DAOGeneral daog = null;

    /***
     * Este método ejecuta un chequeo de la transferencia inicial
     * @param cod_caja código de caja a verificar
     * @param session1 sesion actual en el sistema
     *
     */
    /*public static void comprobarIni(String cod_caja, String idUser, String ipUser) {
    String myPC = null;
    daog = new DAOGeneral();
    List result = daog.findAll("from TMoneda where estado ='ACTIVO'");
    int sy = result.size();
    TCaja caja = (TCaja) daog.load(TCaja.class, cod_caja);
    Set a = caja.getTPersonaCajas();
    if (!a.isEmpty()) {
    Iterator b = a.iterator();
    while (b.hasNext()) {
    TPersonaCaja c = (TPersonaCaja) b.next();
    if (c.getEstado().equals("ACTIVO")) {
    myPC = c.getIdpersonacaja();
    break;
    }
    }
    } else {
    Logger.getLogger(iniDetalleCaja.class.getName()).log(Level.INFO, "CAJA no ACTUALIZADA y FORZADA = " + caja.getCodCaja());
    Iterator f = daog.findAll("from TMoneda where estado ='ACTIVO'").iterator();
    String s = DateUtil.getDate(new Date());
    while (f.hasNext()) {
    TMoneda m = (TMoneda) f.next();
    crearNuevoDetalleCajaxMo(caja, m, s);
    }
    return;
    }
    if (caja != null) {
    if (sy > 0) {
    Iterator itPmoneda = result.iterator();
    while (itPmoneda.hasNext()) {
    TMoneda xmo = (TMoneda) itPmoneda.next();
    String idForInitNewesttable = DateUtil.getDate(new Date()).replaceAll("/", "") + cod_caja + xmo.getCodMoneda() + "00";
    if ("ACTIVO".equals(xmo.getEstado())) {
    TDetalleCaja l = (TDetalleCaja) daog.load(TDetalleCaja.class, idForInitNewesttable);
    if (l == null) {
    List cajaActiva = daog.findAll("from TDetalleCaja where TCaja.codCaja='" + caja.getCodCaja()
    + "' and TMoneda.codMoneda='" + xmo.getCodMoneda() + "' order by fechaTransaccion desc");
    TDetalleCaja detallecaja;
    if (cajaActiva.isEmpty()) {
    detallecaja = new TDetalleCaja(idForInitNewesttable, caja,
    xmo, DateUtil.getNOW_S(), idUser + " PRIMERA VEZ", ipUser, DateUtil.getNOW_S());
    detallecaja.setMontoInicial(BigDecimal.ZERO);
    detallecaja.setMontoFinal(BigDecimal.ZERO);
    detallecaja.setMontoEntregado(BigDecimal.ZERO);
    detallecaja.setMontoRecibido(BigDecimal.ZERO);
    detallecaja.setEstado("ACTIVO");
    daog.persist(detallecaja);
    TOperacion recep = new TOperacion();
    recep.setDateUser(DateUtil.getNOW_S());
    recep.setDescripcion("TRASLADO DEL CAJA ANTERIOR");
    recep.setEstado("ACTIVO");
    recep.setFd(new Date());
    recep.setFecha(DateUtil.getNOW_S());
    recep.setFechaExtornacion(null);
    recep.setIdAdminExtorno(null);
    TPersonaCaja pcaja = (TPersonaCaja) daog.load(TPersonaCaja.class, myPC);
    recep.setTPersonaCaja(pcaja);
    recep.setIdOperacion(idForInitNewesttable);
    recep.setIdUser(idUser);
    recep.setIpUser(ipUser);
    recep.setNumeroOperacion(numeroOperacion.getNumber(caja.getTFilial().getCodFilial(), cod_caja));
    recep.setSaldofinal(detallecaja.getMontoFinal());
    recep.setTMoneda(xmo);
    TTipoOperacion tipoOper = (TTipoOperacion) daog.load(TTipoOperacion.class, "TIPC12");
    recep.setTTipoOperacion(tipoOper);
    recep.setFd(new Date());
    daog.persist(recep);
    TTransferenciaCaja nTransf = new TTransferenciaCaja();
    nTransf.setIdtransferenciacaja(idForInitNewesttable);
    nTransf.setFecha(DateUtil.getNOW_S());
    nTransf.setMonto(detallecaja.getMontoFinal());
    nTransf.setIdOperacion(xmo.getCodMoneda());
    nTransf.setDescripcion("* TRASLADO DE INICIAL " + DateUtil.getNOW_S());
    nTransf.setCodCajaDestino(caja.getCodCaja());
    nTransf.setTCaja(pcaja.getTCaja());
    nTransf.setTipo("TRASLADO");
    nTransf.setTOperacion(recep);
    nTransf.setIdUser(idUser + " TRASLADO");
    nTransf.setIpUser(ipUser);
    nTransf.setEstado("ACTIVO");
    nTransf.setDateUser(DateUtil.getNOW_S());
    nTransf.setIdope(idForInitNewesttable);
    daog.persist(nTransf);
    } else {
    TDetalleCaja detAnt = (TDetalleCaja) cajaActiva.get(0);
    detallecaja = new TDetalleCaja(idForInitNewesttable, caja,
    xmo, DateUtil.getNOW_S(), detAnt.getIddetallecaja() + " TRASLADO", ipUser, DateUtil.getNOW_S());
    detallecaja.setMontoInicial(detAnt.getMontoFinal());
    detallecaja.setMontoFinal(detAnt.getMontoFinal());
    detallecaja.setMontoRecibido(BigDecimal.ZERO);
    detallecaja.setMontoEntregado(BigDecimal.ZERO);
    detallecaja.setEstado("ACTIVO");
    daog.persist(detallecaja);
    TOperacion recep = new TOperacion();
    recep.setDateUser(DateUtil.getNOW_S());
    recep.setDescripcion("TRASLADO DEL CAJA ANTERIOR");
    recep.setEstado("ACTIVO");
    recep.setFd(new Date());
    recep.setFecha(DateUtil.getNOW_S());
    recep.setFechaExtornacion(null);
    recep.setIdAdminExtorno(null);
    TPersonaCaja pcaja = (TPersonaCaja) daog.load(TPersonaCaja.class, myPC);
    recep.setTPersonaCaja(pcaja);
    recep.setIdOperacion(idForInitNewesttable);
    recep.setIdUser(idUser);
    recep.setIpUser(ipUser);
    recep.setNumeroOperacion(numeroOperacion.getNumber(caja.getTFilial().getCodFilial(), cod_caja));
    recep.setSaldofinal(detallecaja.getMontoFinal());
    recep.setTMoneda(xmo);
    TTipoOperacion tipoOper = (TTipoOperacion) daog.load(TTipoOperacion.class, "TIPC12");
    recep.setTTipoOperacion(tipoOper);
    recep.setFd(new Date());
    daog.persist(recep);
    TTransferenciaCaja nTransf = new TTransferenciaCaja();
    nTransf.setIdtransferenciacaja(idForInitNewesttable);
    nTransf.setFecha(DateUtil.getNOW_S());
    nTransf.setMonto(detallecaja.getMontoFinal());
    nTransf.setIdOperacion(xmo.getCodMoneda());
    nTransf.setDescripcion("* TRASLADO DE INICIAL " + DateUtil.getNOW_S());
    nTransf.setCodCajaDestino(caja.getCodCaja());
    nTransf.setTCaja(pcaja.getTCaja());
    nTransf.setTipo("TRASLADO");
    nTransf.setTOperacion(recep);
    nTransf.setIdUser(idUser + " TRASLADO");
    nTransf.setIpUser(ipUser);
    nTransf.setEstado("ACTIVO");
    nTransf.setDateUser(DateUtil.getNOW_S());
    nTransf.setIdope(idForInitNewesttable);
    daog.persist(nTransf);
    }
    }
    }
    }
    }
    }
    daog = null;
    }*/

    /*public static TDetalleCaja detalleActivaCaja(String codCaja, String codMoneda, String fecha) {
    TDetalleCaja dtA = null;
    daog = new DAOGeneral();
    TMoneda moneda = (TMoneda) daog.load(TMoneda.class, codMoneda);
    TCaja caja = (TCaja) daog.load(TCaja.class, codCaja);
    if (caja != null && moneda != null) {
    //dtA = (TDetalleCaja) daog.load(TDetalleCaja.class, fecha.replaceAll("/", "") + codCaja + moneda.getCodParaNumCuenta() + "00");
    dtA = (TDetalleCaja) daog.load(TDetalleCaja.class, fecha.replaceAll("/", "") + codCaja + moneda.getCodMoneda() + "00");
    if (dtA == null) {
    dtA = crearNuevoDetalleCajaxMo(caja, moneda, fecha);
    }
    }
    daog.cerrarSession();
    daog = null;
    return dtA;
    }

    private static TDetalleCaja crearNuevoDetalleCajaxMo(TCaja caja, TMoneda xmo, String fecha) {
    TDetalleCaja detallecaja = null;
    String idForInitNewesttable = fecha.replaceAll("/", "") + caja.getCodCaja() + xmo.getCodMoneda() + "00";
    String myPC = null;
    Set a = caja.getTPersonaCajas();
    if (!a.isEmpty()) {
    Iterator b = a.iterator();
    while (b.hasNext()) {
    TPersonaCaja c = (TPersonaCaja) b.next();
    if (c.getEstado().equals("ACTIVO")) {
    myPC = c.getIdpersonacaja();
    break;
    }
    }
    }
    if (myPC == null) {
    detallecaja = (TDetalleCaja) daog.load(TDetalleCaja.class, idForInitNewesttable);
    if (detallecaja == null) {
    detallecaja = new TDetalleCaja(idForInitNewesttable, caja,
    xmo, DateUtil.getNOW_S(), "CREATE FORCED: ADMIN", "local", fecha + " 00:00:00");
    detallecaja.setMontoInicial(BigDecimal.ZERO);
    detallecaja.setMontoFinal(BigDecimal.ZERO);
    detallecaja.setMontoFinal(BigDecimal.ZERO);
    detallecaja.setMontoEntregado(BigDecimal.ZERO);
    detallecaja.setMontoRecibido(BigDecimal.ZERO);
    detallecaja.setEstado("ACTIVO");
    daog.persist(detallecaja);
    }
    return detallecaja;
    }
    detallecaja = (TDetalleCaja) daog.load(TDetalleCaja.class, idForInitNewesttable);
    if (detallecaja == null) {
    List cajaActiva = daog.findAll("from TDetalleCaja where TCaja.codCaja='" + caja.getCodCaja()
    + "' and TMoneda.codMoneda='" + xmo.getCodMoneda() + "' order by fechaTransaccion desc");
    if (cajaActiva.isEmpty()) {
    detallecaja = new TDetalleCaja(idForInitNewesttable, caja,
    xmo, DateUtil.getNOW_S(), "ADMIN" + " PRIMERA VEZ", "LOCAL", DateUtil.getNOW_S());
    detallecaja.setMontoInicial(BigDecimal.ZERO);
    detallecaja.setMontoFinal(BigDecimal.ZERO);
    detallecaja.setMontoEntregado(BigDecimal.ZERO);
    detallecaja.setMontoRecibido(BigDecimal.ZERO);
    detallecaja.setEstado("ACTIVO");
    daog.persist(detallecaja);
    TOperacion recep = new TOperacion();
    recep.setDateUser(DateUtil.getNOW_S());
    recep.setDescripcion("TRASLADO DEL CAJA ANTERIOR");
    recep.setEstado("ACTIVO");
    recep.setFd(new Date());
    recep.setFecha(DateUtil.getNOW_S());
    recep.setFechaExtornacion(null);
    recep.setIdAdminExtorno(null);
    TPersonaCaja pcaja = (TPersonaCaja) daog.load(TPersonaCaja.class, myPC);
    recep.setTPersonaCaja(pcaja);
    recep.setIdOperacion(idForInitNewesttable);
    recep.setIdUser("ADMIN");
    recep.setIpUser("LOCAL");
    recep.setNumeroOperacion(numeroOperacion.getNumber(caja.getTFilial().getCodFilial(), caja.getCodCaja()));
    recep.setSaldofinal(detallecaja.getMontoFinal());
    recep.setTMoneda(xmo);
    TTipoOperacion tipoOper = (TTipoOperacion) daog.load(TTipoOperacion.class, "TIPC12");
    recep.setTTipoOperacion(tipoOper);
    recep.setFd(new Date());
    daog.persist(recep);
    TTransferenciaCaja nTransf = new TTransferenciaCaja();
    nTransf.setIdtransferenciacaja(idForInitNewesttable);
    nTransf.setFecha(DateUtil.getNOW_S());
    nTransf.setMonto(detallecaja.getMontoFinal());
    nTransf.setIdOperacion(xmo.getCodMoneda());
    nTransf.setDescripcion("* TRASLADO DE INICIAL " + DateUtil.getNOW_S());
    nTransf.setCodCajaDestino(caja.getCodCaja());
    nTransf.setTCaja(pcaja.getTCaja());
    nTransf.setTipo("TRASLADO");
    nTransf.setTOperacion(recep);
    nTransf.setIdUser("ADMIN" + " TRASLADO");
    nTransf.setIpUser("LOCAL");
    nTransf.setEstado("ACTIVO");
    nTransf.setDateUser(DateUtil.getNOW_S());
    nTransf.setIdope(idForInitNewesttable);
    daog.persist(nTransf);
    } else {
    TDetalleCaja detAnt = (TDetalleCaja) cajaActiva.get(0);
    detallecaja = new TDetalleCaja(idForInitNewesttable, caja,
    xmo, DateUtil.getNOW_S(), detAnt.getIddetallecaja() + " TRASLADO", "ADMIN", DateUtil.getNOW_S());
    detallecaja.setMontoInicial(detAnt.getMontoFinal());
    detallecaja.setMontoFinal(detAnt.getMontoFinal());
    detallecaja.setMontoRecibido(BigDecimal.ZERO);
    detallecaja.setMontoEntregado(BigDecimal.ZERO);
    detallecaja.setEstado("ACTIVO");
    daog.persist(detallecaja);
    TOperacion recep = new TOperacion();
    recep.setDateUser(DateUtil.getNOW_S());
    recep.setDescripcion("TRASLADO DEL CAJA ANTERIOR");
    recep.setEstado("ACTIVO");
    recep.setFd(new Date());
    recep.setFecha(DateUtil.getNOW_S());
    recep.setFechaExtornacion(null);
    recep.setIdAdminExtorno(null);
    TPersonaCaja pcaja = (TPersonaCaja) daog.load(TPersonaCaja.class, myPC);
    recep.setTPersonaCaja(pcaja);
    recep.setIdOperacion(idForInitNewesttable);
    recep.setIdUser("ADMIN");
    recep.setIpUser("LOCAL");
    recep.setNumeroOperacion(numeroOperacion.getNumber(caja.getTFilial().getCodFilial(), caja.getCodCaja()));
    recep.setSaldofinal(detallecaja.getMontoFinal());
    recep.setTMoneda(xmo);
    TTipoOperacion tipoOper = (TTipoOperacion) daog.load(TTipoOperacion.class, "TIPC12");
    recep.setTTipoOperacion(tipoOper);
    recep.setFd(new Date());
    daog.persist(recep);
    TTransferenciaCaja nTransf = new TTransferenciaCaja();
    nTransf.setIdtransferenciacaja(idForInitNewesttable);
    nTransf.setFecha(DateUtil.getNOW_S());
    nTransf.setMonto(detallecaja.getMontoFinal());
    nTransf.setIdOperacion(xmo.getCodMoneda());
    nTransf.setDescripcion("* TRASLADO DE INICIAL " + DateUtil.getNOW_S());
    nTransf.setCodCajaDestino(caja.getCodCaja());
    nTransf.setTCaja(pcaja.getTCaja());
    nTransf.setTipo("TRASLADO");
    nTransf.setTOperacion(recep);
    nTransf.setIdUser("ADMIN" + " TRASLADO");
    nTransf.setIpUser("LOCAL");
    nTransf.setEstado("ACTIVO");
    nTransf.setDateUser(DateUtil.getNOW_S());
    nTransf.setIdope(idForInitNewesttable);
    daog.persist(nTransf);
    }
    }
    return detallecaja;
    }*/
    public static String devolverMinimoPack(String codcaja, String codmoneda, double valor) {
        daog = new DAOGeneral();
        TMoneda m = (TMoneda) daog.load(TMoneda.class, codmoneda);
        if (m == null) {
            return "";
        }
        String idForInitNewestTable = DateUtil.convertDateId("NOUSER", iniDetalleCaja.class.getSimpleName());
        TSumaMoneda suma = new TSumaMoneda(idForInitNewestTable, "S" + m.getSimbolo().substring(0, 1) + iniDetalleCaja.getIdSumaOrden(codcaja + m.getCodMoneda()));
        suma.setEstado("" + codcaja + m.getCodMoneda());
        daog.persist(suma);
        List a = daog.findAll("from TDenominacionMoneda where TMoneda.codMoneda='" + m.getCodMoneda() + "'  order by orden desc");
        for (Iterator it = a.iterator(); it.hasNext();) {
            TDenominacionMoneda object = (TDenominacionMoneda) it.next();
            Double valorDiv = Double.parseDouble(object.getMonto());
            Integer cantidad = (new Double(valor / valorDiv)).intValue();
            valor -= valorDiv * cantidad;
            TDetalleSuma ds = new TDetalleSuma();
            ds.setIddetallesuma(DateUtil.convertDateId("NOUSER", iniDetalleCaja.class.getSimpleName()));
            ds.setCantidad(cantidad);
            ds.setEstado("ACTIVO");
            ds.setTDenominacionMoneda(object);
            ds.setTSumaMoneda(suma);
            daog.persist(ds);
        }
        return suma.getIdsumamoneda();
    }

    public static String getIdSumaOrden(String cod) {
        String id = "-0";
        DAOGeneral dao = new DAOGeneral();
        List l = dao.findAll("from TSumaMoneda where estado='" + cod + "' and idsumamoneda like '" + DateUtil.getDate(new Date()).replace("/", "") + "%'");
        if (l.size() < 10) {
            id = "-" + String.valueOf(l.size() + 1);
        } else {
            id = "" + String.valueOf(l.size() + 1);
        }
        dao.cerrarSession();
        return id;
    }
}
