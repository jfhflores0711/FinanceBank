package org.finance.bank.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.finance.bank.bean.TCuentaPersona;
import org.finance.bank.bean.TDetalleCuentaPersona;
import org.finance.bank.bean.TDetalleIntereses;
import org.finance.bank.bean.TLogFinance;
import org.finance.bank.bean.TMoneda;
import org.finance.bank.bean.TOperacion;
import org.finance.bank.bean.TPersonaCaja;
import org.finance.bank.bean.TRegistroDepositoRetiro;
import org.finance.bank.bean.TSobregiro;
import org.finance.bank.bean.TTipoOperacion;
import org.finance.bank.model.dao.DAOGeneral;
import org.finance.bank.util.DateUtil;
import org.finance.bank.util.PrestamoUtil;
import org.finance.bank.util.numeroOperacion;

/**
 * Este servlet se encarga de actualizar y grabar las operaciones de pago de intereses
 * y las actualizaciones sucesivas de las mismas.
 * Aunque uno ya puede preubicar una cuenta con antelación o poder extraer desde
 * el objeto.
 * @author admin
 */
public final class CheckCapital {

    static DAOGeneral dao;
    static TCuentaPersona cuenta = null;
    public static double[] mes = new double[]{31.00, 28.00, 31.00, 30.00, 31.00, 30.00, 31.00, 31.00, 30.00, 31.00, 30.00, 31.00};
    public static double diasPromedioxMes = 30.4375D;

    /**
     * Se obtiene una cantidad de cuentas con valores de interes diferentes de cero y
     * en estado activo, además los sobregiros y las cuentas con plazo fijo tambien
     * son detectados en cada uno de los casos y se cargan en el día.
     */
    public static void capitalizar(Date date) {
        Logger.getLogger(CheckCapital.class.getName()).log(Level.INFO, "date = ENTRADO " + date);
        dao = new DAOGeneral();
        List l = dao.findAll("from TCuentaPersona where (TTipoCuenta.codigoCuenta='CCO' or TTipoCuenta.codigoCuenta='CAH') and estado='ACTIVO' and interes > 0 and saldoSinInteres>0");
        if (!l.isEmpty()) {
            Iterator i = l.iterator();
            while (i.hasNext()) {
                cuenta = (TCuentaPersona) i.next();
                CheckCapital.checkDesembolsoAct(date, cuenta);
            }
        }
        List l0 = dao.findAll("from TCuentaPersona where (TTipoCuenta.codigoCuenta='CCO' or TTipoCuenta.codigoCuenta='CAH') and estado='ACTIVO' and interes = 0 and saldoSinInteres<0");
        if (!l0.isEmpty()) {
            Iterator i = l0.iterator();
            while (i.hasNext()) {
                cuenta = (TCuentaPersona) i.next();
                cuenta.setEstado("SOBREGIRO");
                dao.update();
            }
        }
        List l1 = dao.findAll("from TCuentaPersona where (TTipoCuenta.codigoCuenta='CCO' or TTipoCuenta.codigoCuenta='CAH') and estado='ACTIVO' and interes > 0 and saldoSinInteres=0");
        if (!l1.isEmpty()) {
            Iterator i = l1.iterator();
            while (i.hasNext()) {
                cuenta = (TCuentaPersona) i.next();
                cuenta.setFechaActualizacion(DateUtil.getNOW_S());
                cuenta.setSaldo(cuenta.getSaldo().setScale(2, RoundingMode.HALF_UP));
                cuenta.setEstado("ACTIVO");
                dao.update();
            }
        }
        List l3 = dao.findAll("from TDetalleCuentaPersona where TCuentaPersona.estado='ACTIVO' and interes > 0");
        if (!l3.isEmpty()) {
            Iterator i3 = l3.iterator();
            while (i3.hasNext()) {
                TDetalleCuentaPersona cuenta2 = (TDetalleCuentaPersona) i3.next();
                CheckCapital.checkDesembolsoAct2(date, cuenta2);
                String fnp = PrestamoUtil.eedatefmt(PrestamoUtil.fmtdate9, Double.parseDouble(cuenta2.getFechaPlazo())).replace(",", "");
                if ((new Date()).before(DateUtil.convertStringToDate("dd/MM/yyyy", fnp))) {
                    fnp = fnp.trim();
                    String[] fh = fnp.split("/");
                    fnp = Double.toString(DateUtil.date(Double.parseDouble(fh[2]), Double.parseDouble(fh[1]), Double.parseDouble(fh[0])) + Double.parseDouble(cuenta2.getNumDias()));
                    cuenta2.setFechaPlazo(fnp);
                }
                dao.update();
            }
        }
        List l2 = dao.findAll("from TSobregiro where estado='ACTIVO' and interessg > 0");
        if (!l2.isEmpty()) {
            Iterator i2 = l2.iterator();
            while (i2.hasNext()) {
                TSobregiro sg = (TSobregiro) i2.next();
                int dias = DateUtil.daysDifBetween(DateUtil.convertStringToDate("yyyy/MM/dd HH:mm:ss", sg.getFechaActualizacion()), date);
                if (dias > 0) {
                    TSobregiro sg1 = (TSobregiro) dao.load(TSobregiro.class, sg.getIdsobregiro());
                    sg1.setFechaActualizacion(DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", date));
                    Double ns = sg1.getMontoSinInteres().doubleValue();
                    Calendar calsg = Calendar.getInstance();
                    calsg.setTime(date);
                    Double as = ((ns * (sg1.getInteressg().doubleValue() / 1200)) / ((mes[calsg.get(Calendar.MONTH)]) == 28.00 ? (DateUtil.year(DateUtil.today()) % 4 == 0 ? 29.00 : 28.00) : mes[calsg.get(Calendar.MONTH)])) * dias;
                    sg1.setMontoActual(sg1.getMontoActual().add(new BigDecimal(as)));
                    dao.update();
                    TCuentaPersona cuentax = (TCuentaPersona) dao.load(TCuentaPersona.class, sg1.getTCuentaPersona().getIdcuentapersona());
                    cuentax.setSaldo(new BigDecimal(cuentax.getSaldo().doubleValue() - as));
                    cuentax.setFechaActualizacion(DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", date));
                    dao.update();
                    Logger.getLogger(CheckCapital.class.getName()).log(Level.INFO, "cuentax = " + cuentax.getIdcuentapersona());
                }
            }
        }
    }

    /***
     * Cuando se detecta las fechas fines del mes se le hace un pago
     * 
     * @param ff feccha de hoy día
     * @param c La cuenta del usuario que se actualizará
     *
     */
    private static void checkDesembolsoAct(Date ff, TCuentaPersona c) {
        String idForInit1 = DateUtil.convertDateId(cuenta.getIdcuentapersona(), CheckCapital.class.getSimpleName());
        String idForInit2 = DateUtil.convertDateId(cuenta.getIdcuentapersona(), CheckCapital.class.getSimpleName());
        Calendar ca = Calendar.getInstance();
        ca.setTime(ff);
        Date fechaAct = DateUtil.convertStringToDate("yyyy/MM/dd HH:mm:ss", c.getFechaActualizacion());
        Calendar feCal = Calendar.getInstance();
        feCal.setTime(fechaAct);
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.MONTH) == 0) {
            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);
            calendar.set(Calendar.MONTH, 11);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
        } else {
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
        }
        Date fechaVirtualCap = calendar.getTime();
        //Logger.getLogger(CheckCapital.class.getName()).log(Level.INFO, "calendar = " + fechaVirtualCap);
        if (fechaAct.before(fechaVirtualCap)) {
            int dias1 = DateUtil.daysDifBetween(fechaAct, fechaVirtualCap);
            if (dias1 > 0) {
                TCuentaPersona cp1 = (TCuentaPersona) dao.load(TCuentaPersona.class, c.getIdcuentapersona());
                cp1.setFechaActualizacion(DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", fechaVirtualCap));
                Double ns = cp1.getSaldo().doubleValue();
                Double bs = cp1.getSaldoSinInteres().doubleValue();
                Double as = ((bs * (cp1.getInteres().doubleValue() / 1200)) / ((mes[feCal.get(Calendar.MONTH)]) == 28.00 ? (DateUtil.year(DateUtil.today()) % 4 == 0 ? 29.00 : 28.00) : mes[feCal.get(Calendar.MONTH)])) * dias1;
                ns += as;
                cp1.setSaldo(new BigDecimal(ns));
                dao.update();
                TDetalleIntereses newdi = new TDetalleIntereses();
                newdi.setIddetalleintereses(idForInit1);
                newdi.setFecha(DateUtil.getNOW_S());
                newdi.setMontoBase(cp1.getSaldoSinInteres());
                newdi.setMontoFinal(cp1.getSaldo());
                newdi.setMontoTotal(new BigDecimal(ns - as));
                newdi.setMontoint(new BigDecimal(as));
                newdi.setNumOrden("10");
                newdi.setTCuentaPersona(cp1);
                newdi.setTasaInt(c.getInteres());
                newdi.setDias(dias1);
                newdi.setTipo("PRE");
                dao.persist(newdi);
            }
            int dias2 = DateUtil.daysDifBetween(fechaVirtualCap, ff);
            if (dias2 > 0) {
                TCuentaPersona cp2 = (TCuentaPersona) dao.load(TCuentaPersona.class, c.getIdcuentapersona());
                cp2.setFechaActualizacion(DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", ff));
                Double ns = cp2.getSaldo().doubleValue();
                Double bs = cp2.getSaldoSinInteres().doubleValue();
                Double as = ((bs * (cp2.getInteres().doubleValue() / 1200)) / ((mes[ca.get(Calendar.MONTH)]) == 28.00 ? (DateUtil.year(DateUtil.today()) % 4 == 0 ? 29.00 : 28.00) : mes[ca.get(Calendar.MONTH)])) * dias1;
                ns += as;
                cp2.setSaldo(new BigDecimal(ns));
                dao.update();
                TDetalleIntereses newdi = new TDetalleIntereses();
                newdi.setIddetalleintereses(idForInit2);
                newdi.setFecha(DateUtil.getNOW_S());
                newdi.setMontoBase(cp2.getSaldoSinInteres());
                newdi.setMontoFinal(cp2.getSaldo());
                newdi.setMontoTotal(new BigDecimal(ns - as));
                newdi.setMontoint(new BigDecimal(as));
                newdi.setNumOrden("20");
                newdi.setTCuentaPersona(cp2);
                newdi.setTasaInt(c.getInteres());
                newdi.setDias(dias2);
                newdi.setTipo("PRE");
                dao.persist(newdi);
            }
        } else {
            if (fechaAct.before(ff)) {
                int dias = DateUtil.daysDifBetween(fechaAct, ff);
                if (dias > 0) {
                    TCuentaPersona cp = (TCuentaPersona) dao.load(TCuentaPersona.class, c.getIdcuentapersona());
                    cp.setFechaActualizacion(DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", ff));
                    Double ns = cp.getSaldo().doubleValue();
                    Double bs = cp.getSaldoSinInteres().doubleValue();
                    Double as = ((bs * (cp.getInteres().doubleValue() / 1200)) / ((mes[ca.get(Calendar.MONTH)]) == 28.00 ? (DateUtil.year(DateUtil.today()) % 4 == 0 ? 29.00 : 28.00) : mes[ca.get(Calendar.MONTH)])) * dias;
                    ns += as;
                    cp.setSaldo(new BigDecimal(ns));
                    dao.update();
                    TDetalleIntereses newdi = new TDetalleIntereses();
                    newdi.setIddetalleintereses(idForInit1);
                    newdi.setFecha(DateUtil.getNOW_S());
                    newdi.setMontoBase(cp.getSaldoSinInteres());
                    newdi.setMontoFinal(cp.getSaldo());
                    newdi.setMontoTotal(new BigDecimal(ns - as));
                    newdi.setMontoint(new BigDecimal(as));
                    newdi.setNumOrden("30");
                    newdi.setTCuentaPersona(cp);
                    newdi.setTasaInt(c.getInteres());
                    newdi.setDias(dias);
                    newdi.setTipo("PRE");
                    dao.persist(newdi);
                }
            } else {
                Logger.getLogger(CheckCapital.class.getName()).log(Level.INFO, "fA = " + fechaAct);
            }
        }
    }

    private static void checkDesembolsoAct2(Date date, TDetalleCuentaPersona cuenta2) {
        String idForInit1 = DateUtil.convertDateId(cuenta.getIdcuentapersona(), CheckCapital.class.getSimpleName());
        String idForInit2 = DateUtil.convertDateId(cuenta.getIdcuentapersona(), CheckCapital.class.getSimpleName());
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        Date fechaAct = DateUtil.convertStringToDate("yyyy/MM/dd HH:mm:ss", cuenta2.getTCuentaPersona().getFechaActualizacion());
        Calendar feCal = Calendar.getInstance();
        feCal.setTime(fechaAct);
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.MONTH) == 0) {
            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);
            calendar.set(Calendar.MONTH, 11);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
        } else {
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
        }
        Date fechaVirtualCap = calendar.getTime();
        //Logger.getLogger(CheckCapital.class.getName()).log(Level.INFO, "calendar = " + fechaVirtualCap);
        if (fechaAct.before(fechaVirtualCap)) {
            int dias1 = DateUtil.daysDifBetween(fechaAct, fechaVirtualCap);
            if (dias1 > 0) {
                TCuentaPersona cp1 = (TCuentaPersona) dao.load(TCuentaPersona.class, cuenta2.getTCuentaPersona().getIdcuentapersona());
                cp1.setFechaActualizacion(DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", fechaVirtualCap));
                Double ns = cp1.getSaldo().doubleValue();
                Double bs = cp1.getSaldoSinInteres().doubleValue();
                Double as = ((bs * (cuenta2.getInteres().doubleValue() / 1200)) / ((mes[feCal.get(Calendar.MONTH)]) == 28.00 ? (DateUtil.year(DateUtil.today()) % 4 == 0 ? 29.00 : 28.00) : mes[feCal.get(Calendar.MONTH)])) * dias1;
                ns += as;
                cp1.setSaldo(new BigDecimal(ns));
                dao.update();
                TDetalleIntereses newdi = new TDetalleIntereses();
                newdi.setIddetalleintereses(idForInit1);
                newdi.setFecha(DateUtil.getNOW_S());
                newdi.setMontoBase(cp1.getSaldoSinInteres());
                newdi.setMontoFinal(cp1.getSaldo());
                newdi.setMontoTotal(new BigDecimal(ns - as));
                newdi.setMontoint(new BigDecimal(as));
                newdi.setNumOrden("10");
                newdi.setTCuentaPersona(cp1);
                newdi.setTasaInt(cuenta2.getInteres());
                newdi.setDias(dias1);
                newdi.setTipo("PRE");
                dao.persist(newdi);
            }
            int dias2 = DateUtil.daysDifBetween(fechaVirtualCap, date);
            if (dias2 > 0) {
                TCuentaPersona cp2 = (TCuentaPersona) dao.load(TCuentaPersona.class, cuenta2.getTCuentaPersona().getIdcuentapersona());
                cp2.setFechaActualizacion(DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", date));
                Double ns = cp2.getSaldo().doubleValue();
                Double bs = cp2.getSaldoSinInteres().doubleValue();
                Double as = ((bs * (cuenta2.getInteres().doubleValue() / 1200)) / ((mes[ca.get(Calendar.MONTH)]) == 28.00 ? (DateUtil.year(DateUtil.today()) % 4 == 0 ? 29.00 : 28.00) : mes[ca.get(Calendar.MONTH)])) * dias2;
                ns += as;
                cp2.setSaldo(new BigDecimal(ns));
                dao.update();
                TDetalleIntereses newdi = new TDetalleIntereses();
                newdi.setIddetalleintereses(idForInit2);
                newdi.setFecha(DateUtil.getNOW_S());
                newdi.setMontoBase(cp2.getSaldoSinInteres());
                newdi.setMontoFinal(cp2.getSaldo());
                newdi.setMontoTotal(new BigDecimal(ns - as));
                newdi.setMontoint(new BigDecimal(as));
                newdi.setNumOrden("20");
                newdi.setTCuentaPersona(cp2);
                newdi.setTasaInt(cuenta2.getInteres());
                newdi.setTipo("PRE");
                newdi.setDias(dias2);
                dao.persist(newdi);
            }
        } else {
            if (fechaAct.before(date)) {
                int dias = DateUtil.daysDifBetween(fechaAct, date);
                if (dias > 0) {
                    TCuentaPersona cp = (TCuentaPersona) dao.load(TCuentaPersona.class, cuenta2.getTCuentaPersona().getIdcuentapersona());
                    cp.setFechaActualizacion(DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", date));
                    Double ns = cp.getSaldo().doubleValue();
                    Double bs = cp.getSaldoSinInteres().doubleValue();
                    Double as = ((bs * (cuenta2.getInteres().doubleValue() / 1200)) / ((mes[ca.get(Calendar.MONTH)]) == 28.00 ? (DateUtil.year(DateUtil.today()) % 4 == 0 ? 29.00 : 28.00) : mes[ca.get(Calendar.MONTH)])) * dias;
                    ns += as;
                    cp.setSaldo(new BigDecimal(ns));
                    dao.update();
                    TDetalleIntereses newdi = new TDetalleIntereses();
                    newdi.setIddetalleintereses(idForInit1);
                    newdi.setFecha(DateUtil.getNOW_S());
                    newdi.setMontoBase(cp.getSaldoSinInteres());
                    newdi.setMontoFinal(cp.getSaldo());
                    newdi.setMontoTotal(new BigDecimal(ns - as));
                    newdi.setMontoint(new BigDecimal(as));
                    newdi.setNumOrden("30");
                    newdi.setTCuentaPersona(cp);
                    newdi.setTasaInt(cuenta2.getInteres());
                    newdi.setTipo("PRE");
                    newdi.setDias(dias);
                    dao.persist(newdi);
                }
            } else {
                Logger.getLogger(CheckCapital.class.getName()).log(Level.INFO, "fA = " + fechaAct);
            }
        }
    }

    public CheckCapital() {
    }

    /* Cuando se quiera pagar un monto como retiro final y dejar cero el saldo
     * Es necesario una autorizacion del admin
     **/
    public static boolean capitalizarForced(HttpSession session, Date ff, String idcuenta, Double monto) {
        String idForInit1 = DateUtil.convertDateId(idcuenta, CheckCapital.class.getSimpleName());
        dao = new DAOGeneral();
        TCuentaPersona cuentaPersona = (TCuentaPersona) dao.load(TCuentaPersona.class, idcuenta);
        if (cuentaPersona == null) {
            return false;
        }
        if (cuentaPersona.getSaldo().doubleValue() != 0) {
            if (cuentaPersona.getSaldo().doubleValue() == cuentaPersona.getSaldoSinInteres().doubleValue()) {
                return false;
            }
        }
        cuentaPersona.setSaldoSinInteres(new BigDecimal(monto + cuentaPersona.getSaldoSinInteres().doubleValue()));
        if (cuentaPersona.getSaldo().doubleValue() < cuentaPersona.getSaldoSinInteres().doubleValue()) {
            cuentaPersona.setSaldo(cuentaPersona.getSaldoSinInteres());
        }
        cuentaPersona.setFechaCap(DateUtil.getDateTime("yyyy/MM/dd", ff));
        dao.update();
        String id_user = (String) session.getAttribute("USER_ID");
        TOperacion operacion = new TOperacion();
        operacion.setIdOperacion(idForInit1);
        operacion.setDescripcion(DateUtil.getDate(new Date()) + " PAGO INTERES " + cuentaPersona.getNumCta());
        operacion.setFecha(DateUtil.getNOW_S());
        operacion.setEstado("ACTIVO");
        operacion.setIdUser(id_user);
        operacion.setIpUser((String) session.getAttribute("USER_IP"));
        operacion.setDateUser(DateUtil.getNOW_S());
        TPersonaCaja pcaja = (TPersonaCaja) dao.load(TPersonaCaja.class, session.getAttribute("USER_ID_PERSONA_CAJA").toString());
        TTipoOperacion tipoOperacion;
        tipoOperacion = (TTipoOperacion) dao.load(TTipoOperacion.class, "TIPC11");
        operacion.setTTipoOperacion(tipoOperacion);
        TMoneda mimoneda = (TMoneda) dao.load(TMoneda.class, cuentaPersona.getTMoneda().getCodMoneda());
        operacion.setTMoneda(mimoneda);
        operacion.setTPersonaCaja(pcaja);
        operacion.setSaldofinal(cuentaPersona.getSaldo());
        operacion.setSaldoFinalSinInteres(cuentaPersona.getSaldoSinInteres());
        operacion.setNumeroOperacion(numeroOperacion.getNumber(session.getAttribute("USER_CODFILIAL").toString(), session.getAttribute("USER_CODCAJA").toString()));
        operacion.setFd(new Date());
        dao.persist(operacion);
        TRegistroDepositoRetiro RegistroDepRet = new TRegistroDepositoRetiro();
        RegistroDepRet.setIddepositoretiro(idForInit1);
        RegistroDepRet.setFecha(DateUtil.getNOW_S());
        RegistroDepRet.setNumCta(cuentaPersona.getNumCta());
        RegistroDepRet.setImporte(new BigDecimal(monto));
        RegistroDepRet.setNombreRepresentante("FINANCEBANK! INTERESES");
        RegistroDepRet.setApellidosRepresentante("");
        RegistroDepRet.setEstado("ACTIVO");
        RegistroDepRet.setNumeroCheque("0");
        RegistroDepRet.setIdUser(id_user);
        RegistroDepRet.setIpUser((String) session.getAttribute("USER_IP"));
        RegistroDepRet.setDateUser(DateUtil.getNOW_S());
        RegistroDepRet.setTCuentaPersona(cuentaPersona);
        RegistroDepRet.setTOperacion(operacion);
        dao.persist(RegistroDepRet);
        TDetalleIntereses newdi = new TDetalleIntereses();
        newdi.setIddetalleintereses(idForInit1);
        newdi.setFecha(DateUtil.getNOW_S());
        newdi.setMontoBase(cuentaPersona.getSaldoSinInteres().subtract(new BigDecimal(monto)));
        newdi.setMontoFinal(cuentaPersona.getSaldo());
        newdi.setMontoTotal(cuentaPersona.getSaldoSinInteres());
        newdi.setMontoint(new BigDecimal(monto));
        newdi.setNumOrden("200");
        newdi.setTCuentaPersona(cuentaPersona);
        newdi.setTasaInt(cuentaPersona.getInteres());
        newdi.setDias(1);
        newdi.setTipo("PRE");
        dao.persist(newdi);
        TLogFinance log = (TLogFinance) dao.load(TLogFinance.class, "LOG" + pcaja.getTCaja().getCodCaja() + mimoneda.getCodMoneda());
        log.setMonto(log.getMonto().subtract(new BigDecimal(monto)));
        log.setPasivo(log.getPasivo().add(new BigDecimal(monto)));
        dao.update();
        Set di = cuentaPersona.getTDetalleIntereseses();
        if (!di.isEmpty()) {
            Iterator i = di.iterator();
            while (i.hasNext()) {
                TDetalleIntereses e = (TDetalleIntereses) i.next();
                if (e.getTipo().equals("PRE")) {
                    e.setTipo("ANT");
                    dao.update();
                } else {
                    dao.delete(e);
                }
            }
        }
        dao.cerrarSession();
        return true;
    }
}
