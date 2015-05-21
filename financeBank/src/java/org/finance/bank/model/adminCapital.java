package org.finance.bank.model;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.finance.bank.bean.*;
import org.finance.bank.model.dao.DAOGeneral;
import org.finance.bank.util.DateUtil;
import org.finance.bank.util.formatMoneda;

/**
 *
 * @author ronald
 */
public final class adminCapital {

    private static TLogFinance createfisrtLogFor(String idlogfinance, DAOGeneral dao) {
        TLogFinance l0g = new TLogFinance(idlogfinance, "SNAPSHOT", "",
                BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
                "FECHA", idlogfinance.substring(3, 12), "ACTIVO", idlogfinance.substring(3, 7), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        dao.persist(l0g);
        return l0g;
    }

    private static List createfirstSumasShot(String idSumas, DAOGeneral dao) {
        List sumasList = new ArrayList();
        TMoneda moneda = (TMoneda) dao.load(TMoneda.class, idSumas.substring(12, 15));
        Set listaDetalleDen = moneda.getTDenominacionMonedas();
        if (listaDetalleDen.size() > 0) {
            Iterator iteraDen = listaDetalleDen.iterator();
            while (iteraDen.hasNext()) {
                TDenominacionMoneda denActual = (TDenominacionMoneda) iteraDen.next();
                Sumasnashot suma = new Sumasnashot();
                suma.setCajero(idSumas.substring(3, 12));
                suma.setCantidad(0);
                suma.setEstado("ACTIVO" + denActual.getIddenominacionmoneda());
                String tamanio = String.valueOf(Double.parseDouble(denActual.getMonto()) * 100);
                suma.setIdsuma(idSumas + "000000000".substring(tamanio.length()) + tamanio);
                suma.setMoneda(moneda.getCodMoneda());
                suma.setMontodenominacio(denActual.getMonto());
                dao.persist(suma);
                sumasList.add(suma);
            }
        }
        return sumasList;
    }

    private static void sacarHistorial(TLogFinance logCaja, List listSumas, Date fecha, DAOGeneral dao) {
        try {
            String idBalance = DateUtil.getDate(fecha).replaceAll("/", "") + logCaja.getIdlogfinance().substring(3, 15);
            Logger.getLogger(adminCapital.class.getName()).log(Level.INFO, "idBalance = " + idBalance);
            TBalancexmoneda balanceBackup = (TBalancexmoneda) dao.load(TBalancexmoneda.class, idBalance + "00");
            if (balanceBackup == null) {
                TMoneda moneda = (TMoneda) dao.load(TMoneda.class, logCaja.getIdlogfinance().substring(12, 15));
                balanceBackup = new TBalancexmoneda(idBalance + "00", moneda, logCaja.getActivoCajaybanco(),
                        logCaja.getActivoCuentaxcobrar(), logCaja.getPasivo(), logCaja.getEncaje(), logCaja.getPRespaldo(),
                        "BACKUP", "SYSTEM", "LOCAL", DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", fecha));
                balanceBackup.setFd(fecha);
                balanceBackup.setPatrimonio(logCaja.getPatrimonio());
                balanceBackup.setPatrimonioActual(logCaja.getPatrimonio());
                dao.persist(balanceBackup);
                Logger.getLogger(adminCapital.class.getName()).log(Level.INFO, "moneda = " + moneda.getCodMoneda());
            }
            TDetalleCaja cajaBackup = (TDetalleCaja) dao.load(TDetalleCaja.class, idBalance + "00");
            if (cajaBackup == null) {
                cajaBackup = new TDetalleCaja(idBalance + "00", ((TCaja) dao.load(TCaja.class, logCaja.getIdlogfinance().substring(3, 12))), ((TMoneda) dao.load(TMoneda.class, logCaja.getIdlogfinance().substring(12, 15))),
                        DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", fecha), "SYSTEM", "LOCAL", DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", fecha));
                cajaBackup.setMontoEntregado(logCaja.getMontoEntregado());
                cajaBackup.setMontoFinal(logCaja.getMontoFinal());
                cajaBackup.setMontoInicial(logCaja.getMontoInicial());
                cajaBackup.setMontoRecibido(logCaja.getMontoRecibido());
                cajaBackup.setEstado("ACTIVO");
                dao.persist(cajaBackup);
            }
            logCaja.setMontoInicial(logCaja.getMontoFinal());
            logCaja.setMontoEntregado(BigDecimal.ZERO);
            logCaja.setMontoRecibido(BigDecimal.ZERO);
            dao.update();
            /*TSumaMoneda sumaBackup = (TSumaMoneda) dao.load(TSumaMoneda.class, idBalance + "00");
            if (sumaBackup == null) {
                sumaBackup = new TSumaMoneda(idBalance + "00", "NOP");
                sumaBackup.setEstado("BACKUP");
                dao.persist(sumaBackup);
                Iterator it = listSumas.iterator();
                while (it.hasNext()) {
                    Sumasnashot suma = (Sumasnashot) it.next();
                    TDenominacionMoneda den = (TDenominacionMoneda) dao.load(TDenominacionMoneda.class, suma.getEstado().substring(6) + "");
                    String tamanio = String.valueOf(Double.parseDouble(den.getMonto()) * 100);
                    TDetalleSuma sumaBackupDetalle = (TDetalleSuma) dao.load(TDetalleSuma.class, idBalance + "000000000".substring(tamanio.length()) + tamanio + "");
                    if (sumaBackupDetalle == null) {
                        sumaBackupDetalle = new TDetalleSuma(idBalance + "000000000".substring(tamanio.length()) + tamanio + "", den, sumaBackup);
                        sumaBackupDetalle.setEstado(suma.getEstado());
                        sumaBackupDetalle.setCantidad(suma.getCantidad());
                        dao.persist(sumaBackupDetalle);
                        //Logger.getLogger(adminCapital.class.getName()).log(Level.INFO, "sumaBackupDetalle = " + sumaBackupDetalle.getIddetallesuma());
                    }
                    Logger.getLogger(adminCapital.class.getName()).log(Level.INFO, "den = " + den.getTMoneda().getNombre());
                }
            }*/
        } catch (Exception e) {
        }
    }
    static DAOGeneral daoAdminK;
    String codmoneda;
    String idUser;
    String ipUser;
    TPatrimonioTransit tpatrimonio;
    static String filial;

    public TPatrimonioTransit ponerAsiento() {
        return this.tpatrimonio;
    }

    public static double xCalcularUtilidad(String codCaja) throws Exception {
        daoAdminK = new DAOGeneral();
        Double uAcPEN = 0.0D;
        filial = codCaja.substring(0, 4);
        if (filial != null) {
            List xut = daoAdminK.findAll("from TUtilidad");
            Iterator xi = xut.iterator();
            while (xi.hasNext()) {
                TUtilidad u = (TUtilidad) xi.next();
                daoAdminK.delete(u);
            }
            String xf = DateUtil.getNOW_S();
            Double k = 0.0D;
            Double p = 0.0D;
            Double u = 0.0D;
            List lMo = daoAdminK.findAll("from TMoneda where estado='ACTIVO'");
            if (lMo.isEmpty()) {
                return 0.0D;
            } else {
                Iterator iMo = lMo.iterator();
                while (iMo.hasNext()) {
                    TMoneda xm = (TMoneda) iMo.next();
                    String hqlUtilidad = "from TLogFinance where idlogFinance='LOG" + filial + "_____" + xm.getCodMoneda() + "'";
                    List l = daoAdminK.findAll(hqlUtilidad);
                    Iterator i = l.iterator();
                    while (i.hasNext()) {
                        TLogFinance xpatrimonio = (TLogFinance) i.next();
                        k = xpatrimonio.getPatrimonio().doubleValue();
                        p = xpatrimonio.getMonto().doubleValue();
                        u += p - k;
                    }
                    TBalancexmoneda xbm = (TBalancexmoneda) daoAdminK.load(TBalancexmoneda.class, "" + DateUtil.getDate(new Date()).replace("/", "") + codCaja + xm.getCodMoneda() + "00");
                    if (xbm == null) {
                        return 0D;
                    }
                    TUtilidad xu = new TUtilidad();
                    xu.setIdutilidad(xbm.getIdbalance());
                    xu.setFecha(xf);
                    xu.setIdUser(" " + xbm.getIdUser());
                    xu.setDateUser(xf);
                    xu.setIpUser(xbm.getIpUser());
                    xu.setTBalancexmoneda(xbm);
                    xu.setUtilidad(new BigDecimal(u));
                    daoAdminK.persist(xu);
                    if ("PEN".equals(xm.getCodMoneda())) {
                        uAcPEN += u;
                    } else {
                        TTipoCambio ttc;
                        Set hm = xm.getTTipoCambios();
                        int sh = hm.size();
                        if (sh > 0) {
                            Iterator ih = hm.iterator();
                            while (ih.hasNext()) {
                                ttc = (TTipoCambio) ih.next();
                                if ("PEN".equals(ttc.getCodMonedaA())) {
                                    Set sTTasa = ttc.getTTasas();
                                    int sttc = sTTasa.size();
                                    if (sttc > 0) {
                                        Iterator ittc = sTTasa.iterator();
                                        while (ittc.hasNext()) {
                                            TTasa ta = (TTasa) ittc.next();
                                            if ("ACTIVO".equals(ta.getEstado()) && "TASA MERCADO".equals(ta.getTipoTasa())) {
                                                uAcPEN += (u) * ta.getFConversion().doubleValue();
                                                break;
                                            }
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        } else {
            return 0.0D;
        }
        return uAcPEN;
    }

    public static double utilidad(String codM, String codCaja) {
        daoAdminK = new DAOGeneral();
        TUtilidad u = (TUtilidad) daoAdminK.load(TUtilidad.class, "" + DateUtil.getDate(new Date()).replace("/", "") + codCaja + codM + "00");
        if (u == null) {
            return 0D;
        }
        return u.getUtilidad().doubleValue();
    }

    public static void initChecksDatabaseForWork() {
        Long inicio = System.currentTimeMillis();
        Calendar calendario = Calendar.getInstance();
        SimpleDateFormat formatoSistema = new SimpleDateFormat("yyyyMMdd");
        String formato = formatoSistema.format(calendario.getTime());
        Logger.getLogger(adminCapital.class.getName()).log(Level.INFO, "formato = " + formato);
        String aniomesdia = String.valueOf(calendario.get(Calendar.YEAR)) + String.valueOf(calendario.get(Calendar.MONTH)) + String.valueOf(calendario.get(Calendar.DATE));
        Logger.getLogger(adminCapital.class.getName()).log(Level.INFO, "aniomesdia = " + aniomesdia);
        DAOGeneral dao = new DAOGeneral();
        List listaTransaccionesTop2 = dao.findAll("from TTransaccionC t order by t.idtranscapita desc");
        if (listaTransaccionesTop2.size() < 2) {
            Logger.getLogger(adminCapital.class.getName()).log(Level.WARNING, "No se puede Realizar la contrastacion de datos, que subsane un Admisnitrador del sistema");
            return;
        }
        TTransaccionC ultimo = (TTransaccionC) listaTransaccionesTop2.get(0);
        TTransaccionC penultimo = (TTransaccionC) listaTransaccionesTop2.get(1);
        if (!formato.equals(ultimo.getIdtranscapita().substring(0, 8))) {
            Logger.getLogger(adminCapital.class.getName()).log(Level.INFO, "Llamada no autorizada...");
            return;
        }
        Date fechaUltimo = DateUtil.convertStringToDate("yyyyMMdd", ultimo.getIdtranscapita().substring(0, 8));
        Date fechaPenultima = DateUtil.convertStringToDate("yyyyMMdd", penultimo.getIdtranscapita().substring(0, 8));
        int diferenciaEntreFechas = DateUtil.daysBetween(fechaUltimo, fechaPenultima);
        if (diferenciaEntreFechas > 1) {
            Calendar c = Calendar.getInstance();
            c.setTime(fechaPenultima);
            while (fechaPenultima.before(fechaUltimo)) {
                List cajaActivosList = dao.findAll("from TCaja where estado='ACTIVO'");
                if (cajaActivosList.size() > 0) {
                    Iterator iteracion = cajaActivosList.iterator();
                    while (iteracion.hasNext()) {
                        TCaja cajaActual = (TCaja) iteracion.next();
                        List monedasList = dao.findAll("from TMoneda where estado='ACTIVO' order by codMoneda");
                        if (monedasList.size() > 0) {
                            Iterator iteraMoneda = monedasList.iterator();
                            while (iteraMoneda.hasNext()) {
                                TMoneda moneda = (TMoneda) iteraMoneda.next();
                                TLogFinance logCaja = (TLogFinance) dao.load(TLogFinance.class, "LOG" + cajaActual.getCodCaja() + moneda.getCodMoneda());
                                List listSumas = dao.findAll("from Sumasnashot where idsuma like 'SUM" + cajaActual.getCodCaja() + moneda.getCodMoneda() + "%'");
                                if (logCaja == null) {
                                    logCaja = createfisrtLogFor("LOG" + cajaActual.getCodCaja() + moneda.getCodMoneda(), dao);
                                }
                                if (listSumas.isEmpty()) {
                                    listSumas = createfirstSumasShot("SUM" + cajaActual.getCodCaja() + moneda.getCodMoneda(), dao);
                                }
                                sacarHistorial(logCaja, listSumas, fechaPenultima, dao);
                            }
                        }
                    }
                }
                c.add(Calendar.DATE, +1);
                fechaPenultima = c.getTime();
                c.setTime(fechaPenultima);
            }
            Logger.getLogger(adminCapital.class.getName()).log(Level.INFO, "diferenciaEntreFechas = " + diferenciaEntreFechas);
        } else if (diferenciaEntreFechas < 1) {
            Logger.getLogger(adminCapital.class.getName()).log(Level.INFO, "Llamada no autorizada... Acceso de Base de datos Incorrecta");
            return;
        }
        List cajaActivosList = dao.findAll("from TCaja where estado='ACTIVO'");
        if (cajaActivosList.size() > 0) {
            Iterator iteracion = cajaActivosList.iterator();
            while (iteracion.hasNext()) {
                TCaja cajaActual = (TCaja) iteracion.next();
                List monedasList = dao.findAll("from TMoneda where estado='ACTIVO' order by codMoneda");
                if (monedasList.size() > 0) {
                    Iterator iteraMoneda = monedasList.iterator();
                    while (iteraMoneda.hasNext()) {
                        TMoneda moneda = (TMoneda) iteraMoneda.next();
                        TLogFinance logCaja = (TLogFinance) dao.load(TLogFinance.class, "LOG" + cajaActual.getCodCaja() + moneda.getCodMoneda());
                        List listSumas = dao.findAll("from Sumasnashot where idsuma like 'SUM" + cajaActual.getCodCaja() + moneda.getCodMoneda() + "%'");
                        if (logCaja == null) {
                            logCaja = createfisrtLogFor("LOG" + cajaActual.getCodCaja() + moneda.getCodMoneda(), dao);
                        }
                        Logger.getLogger(adminCapital.class.getName()).log(Level.INFO, "logcaja = " + logCaja.getIdlogfinance());
                        if (listSumas.size() < 1) {
                            listSumas = createfirstSumasShot("SUM" + cajaActual.getCodCaja() + moneda.getCodMoneda(), dao);
                        }
                        Logger.getLogger(adminCapital.class.getName()).log(Level.INFO, "listSumas = " + listSumas.size());
                        sacarHistorial(logCaja, listSumas, new Date(), dao);
                    }
                }
            }
        }
        Logger.getLogger(adminCapital.class.getName()).log(Level.INFO, "Ha durado " + (System.currentTimeMillis() - inicio) / 1000 + "sec");
    }

    public static TLogFinance instantLog(String codcaja, String codmoneda) {
        TLogFinance log = new TLogFinance(codcaja, "", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        log.setActivoCuentaxcobrar(BigDecimal.ZERO);
        log.setPRespaldo(BigDecimal.ZERO);
        if (codcaja != null && codmoneda != null) {
            if (codcaja.length() >= 4 && codmoneda.length() > 1) {
                filial = codcaja.substring(0, 4);
                daoAdminK = new DAOGeneral();
                List l = daoAdminK.findAll("from TLogFinance where idlogFinance like 'LOG" + filial + "_____" + codmoneda + "'");
                Iterator i = l.iterator();
                while (i.hasNext()) {
                    TLogFinance xpatrimonio = (TLogFinance) i.next();
                    log.setActivoCajaybanco(log.getActivoCajaybanco().add(xpatrimonio.getActivoCajaybanco()));
                    log.setActivoCuentaxcobrar(log.getActivoCuentaxcobrar().add(xpatrimonio.getActivoCuentaxcobrar()));
                    log.setEncaje(log.getEncaje().add(xpatrimonio.getEncaje()));
                    log.setMonto(log.getMonto().add(xpatrimonio.getMonto()));
                    log.setPRespaldo(log.getPRespaldo().add(xpatrimonio.getPRespaldo()));
                    log.setPasivo(log.getPasivo().add(xpatrimonio.getPasivo()));
                    log.setPatrimonio(log.getPatrimonio().add(xpatrimonio.getPatrimonio()));
                }
            }
        }
        return log;
    }

    public static String[] patPorFilial(String filial, String codmoneda) {
        String mensaje = "";
        daoAdminK = new DAOGeneral();
        TMoneda m = (TMoneda) daoAdminK.load(TMoneda.class, codmoneda);
        List result1 = daoAdminK.findAll("from TFilial");
        Iterator itMO = result1.iterator();
        int jx = 0;
        Double TMonto = 0.0D;
        Double SMonto = 0.0D;
        Double Mdisponible = 0.0D;
        while (itMO.hasNext()) {
            TMonto = 0.0D;
            TFilial Lfilial = (TFilial) itMO.next();
            List R = daoAdminK.findAll("from TLogFinance where idLogFinance like 'LOG" + Lfilial.getCodFilial() + "_____" + codmoneda + "'");
            for (Iterator I = R.iterator(); I.hasNext();) {
                TLogFinance dcaja = (TLogFinance) I.next();
                TMonto = dcaja.getMontoFinal().doubleValue() + TMonto;
            }
            SMonto += TMonto;
            if (jx % 2 == 0) {
                mensaje += ("<tr class='modo1'>");
            } else {
                mensaje += ("<tr class='modo2'>");
            }
            mensaje += ("<td>" + Lfilial.getNombre() + "</td>");
            mensaje += ("<td><div align='right' style='font-size:15px' >" + m.getSimbolo() + " " + formatMoneda.formatMoneda(TMonto) + "</div></td>");
            if (!Lfilial.getCodFilial().equals(filial)) {
                mensaje += ("<td> <input type='button' onclick=\"cerrarfilial('" + Lfilial.getCodFilial() + "','" + jx + "');document.fpatrimonio.submit();\" value='Cerrar' /></td>");
                mensaje += ("<td ><div align='right' style='font-size:15px' > " + m.getSimbolo() + " <input id='montoFilial" + jx + "' style='font-size:20px;width:150px;;text-align:right' type='text' "
                        + " name='montoFilial" + jx + "' value='0.00' "
                        + " onKeyPress=\"return(currencyFormat(this,',','.',event))\" "
                        + " onkeyup=\"actualizarSaldo('" + jx + "')\" "
                        + " /> <input type='button' onclick=\"agregarMfilial('" + Lfilial.getCodFilial() + "','" + jx + "');document.fpatrimonio.submit();\" value='Agregar' /> </div>"
                        + "</td>");
            } else {
                mensaje += ("<td> &nbsp;</td>");
                mensaje += ("<td ><div align='right' style='font-size:15px' > " + m.getSimbolo()
                        + " <input id='montoFilial" + jx + "' style='font-size:20px;width:150px;text-align:right;' type='text' readonly='true'"
                        + " name='montoFilial" + jx + "' value='0.00' /> </div>"
                        + "</td>");
                Mdisponible = TMonto;
            }
            mensaje += ("</tr>");
            jx++;
        }
        mensaje += ("<script language='JavaScript'>");
        mensaje += ("numeroTfiliales(" + jx + ")");
        mensaje += ("</script>");
        mensaje += ("<tr><td colspan='2' align='right'><font color='#385B88' style='font-size:12px'><b>TOTAL FILIALES</b></font><br>"
                + "</td><td colspan='2'><table border='0' cellpadding='0' cellspacing='0'><tr><td style='font-size:20px;color:#E08934;' width='10px'>"
                + "" + m.getSimbolo() + "</td><td><input id='total' type='text' style='font-size:20px;width:156px;color:#E08934;background:transparent;text-align:right' readonly='true'"
                + "name='total' value=" + formatMoneda.formatMoneda(SMonto) + " />"
                + "</td></tr></table></td></tr>");
        String[] r = new String[3];
        r[0] = mensaje;
        r[1] = formatMoneda.formatMoneda(Mdisponible);
        r[2] = formatMoneda.formatMoneda(SMonto);
        return r;
    }
}
