package org.finance.bank.util;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.finance.bank.bean.TBalancexmoneda;
import org.finance.bank.bean.TCaja;
import org.finance.bank.bean.TCobranza;
import org.finance.bank.bean.TDetalleCaja;
import org.finance.bank.bean.TLogFinance;
import org.finance.bank.bean.TMoneda;
import org.finance.bank.bean.TOperacion;
import org.finance.bank.bean.TPatrimonioTransit;
import org.finance.bank.bean.TPersonaCaja;
import org.finance.bank.bean.TRegistroCompraVenta;
import org.finance.bank.bean.TRegistroDepositoRetiro;
import org.finance.bank.bean.TRegistroOtros;
import org.finance.bank.bean.TRegistroPrestamo;
import org.finance.bank.bean.TTipoOperacion;
import org.finance.bank.bean.TTransferenciaCaja;
import org.finance.bank.model.dao.DAOGeneral;
import org.finance.bank.model.iniDetalleCaja;

/**
 *
 * @author ronald
 */
public final class DeleteData {

    /**
     * @param args 10000×((0.392899)^(1÷360)-1)^360
     */
    public static void main(String[] args) {

        double a=2.0;
        double b=3.1;
        double c=5.266;
        double d=-0.0025;
        System.out.println("d = " + CurrencyConverter.formatToMoneyUS(d, 2));
//        System.out.println("a = " + DateUtil.padNmbOnlyDecFull(a, 2, "0"));
//        System.out.println("b = " + DateUtil.padNmbOnlyDecFull(b, 2, "0"));
//        System.out.println("c = " + DateUtil.padNmbOnlyDecFull(c, 2, "0"));
//        System.out.println("c = " + DateUtil.padNmbOnlyDecFull(d, 2, "0"));
//        Date d1 = DateUtil.convertStringToDate("yyyyMMdd", "20140228");
//        Date d2 = DateUtil.convertStringToDate("yyyyMMdd", "20140331");
//        Date d3 = DateUtil.convertStringToDate("yyyyMMdd", "20140401");
//        System.out.println("di = " + PlazosUtil.getDias360(d1, d2, true));
//        System.out.println("di = " + PlazosUtil.getDias360(d1, d2, false));
//        System.out.println("di = " + PlazosUtil.getDias360(d1, d3, true));
//        System.out.println("di = " + PlazosUtil.getDias360(d1, d3, false));
//        System.out.println("di = " + PlazosUtil.getDias360(d2, d3, true));
//        System.out.println("di = " + PlazosUtil.getDias360(d2, d3, false));
//        System.out.println("di = " + DateUtil.daysBetween(d1, d2));
//        System.out.println("di = " + DateUtil.daysDifBetween(d1, d2));
//        System.out.println("di = " + DateUtil.daysBetween(d1, d3));
//        System.out.println("di = " + DateUtil.daysDifBetween(d1, d3));
//        System.out.println("di = " + DateUtil.daysBetween(d2, d3));
//        System.out.println("di = ".substring(0, 3) + DateUtil.daysDifBetween(d2, d3));
        
    }

    private void pir() {
        DAOGeneral dao = new DAOGeneral();
        List bigList = dao.findAll("from TOperacion where fecha not like '2012/05/14%' order by fecha");
        Iterator bigIt = bigList.iterator();
        while (bigIt.hasNext()) {
            TOperacion op = (TOperacion) bigIt.next();
            revisarRecepcion(op, dao);
            TBalancexmoneda bx = null;
            TBalancexmoneda bx1 = null;
            TPatrimonioTransit pa = null;
            bx = (TBalancexmoneda) dao.load(TBalancexmoneda.class, DateUtil.getDate(op.getFd()).replaceAll("/", "") + op.getTPersonaCaja().getTCaja().getCodCaja() + op.getTMoneda().getCodMoneda() + "00");
            pa = (TPatrimonioTransit) dao.findAll("from TPatrimonio where TBalancexmoneda='" + bx.getIdbalance()
                    + "' and estado='ACTIVO" + op.getTPersonaCaja().getTCaja().getTFilial().getCodFilial() + "'").get(0);
            String idForInitNewestTable = op.getIdOperacion();
            int chain = (int) Integer.parseInt(op.getTTipoOperacion().getCodigoTipoOperacion().substring(4));
            //String s = "";
            boolean resetedTransit = true;
            switch (chain) {
                case 1:
                    List compra = dao.findAll("from TRegistroCompraVenta where TOperacion.idOperacion='" + idForInitNewestTable + "'");
                    if (compra.size() > 0) {
                        TRegistroCompraVenta c = (TRegistroCompraVenta) compra.iterator().next();
//                        String moneda1 = c.getTTipoCambio().getTMoneda().getCodMoneda();
//                        String moneda2 = c.getTTipoCambio().getCodMonedaA();
                        TMoneda mo2 = (TMoneda) dao.load(TMoneda.class, c.getTTipoCambio().getCodMonedaA());
//            String hql = iniDetalleCaja.detalleActivaCaja(myCodC, moneda1);
//                        TDetalleCaja detallecaja = iniDetalleCaja.detalleActivaCaja(op.getTPersonaCaja().getTCaja().getCodCaja(), moneda1, DateUtil.getDate(op.getFd()));
                        TDetalleCaja detallecaja = (TDetalleCaja) dao.load(TDetalleCaja.class, DateUtil.getDate(op.getFd()).replaceAll("/", "") + op.getTPersonaCaja().getTCaja().getCodCaja() + c.getTTipoCambio().getTMoneda().getCodMoneda() + "00");
                        detallecaja.setMontoFinal(detallecaja.getMontoFinal().add(c.getMontoRecibido()));
                        detallecaja.setMontoRecibido(detallecaja.getMontoRecibido().add(c.getMontoRecibido()));
                        dao.update();
//            String hql2 = iniDetalleCaja.detalleActivaCaja(myCodC, moneda2);
//                        TDetalleCaja detallecaja2 = iniDetalleCaja.detalleActivaCaja(op.getTPersonaCaja().getTCaja().getCodCaja(), moneda2, DateUtil.getDate(op.getFd()));
                        TDetalleCaja detallecaja2 = (TDetalleCaja) dao.load(TDetalleCaja.class, DateUtil.getDate(op.getFd()).replaceAll("/", "") + op.getTPersonaCaja().getTCaja().getCodCaja() + mo2.getCodMoneda() + "00");
                        detallecaja2.setMontoFinal(detallecaja2.getMontoFinal().subtract(c.getMontoEntregado()));
                        detallecaja2.setMontoEntregado(detallecaja2.getMontoEntregado().add(c.getMontoEntregado()));
                        dao.update();
//                        bx = (TBalancexmoneda) dao.load(TBalancexmoneda.class, DateUtil.getDate(op.getFd()).replaceAll("/", "") + "0" + op.getTMoneda().getCodMoneda() + "0000000000");
                        bx1 = (TBalancexmoneda) dao.load(TBalancexmoneda.class, DateUtil.getDate(op.getFd()).replaceAll("/", "") + op.getTPersonaCaja().getTCaja().getCodCaja() + "100");
//                        pa = (TPatrimonio) dao.findAll("from TPatrimonio where TBalancexmoneda='" + bx.getIdbalance()
//                                + "' and estado='ACTIVO" + op.getTPersonaCaja().getTCaja().getTFilial().getCodFilial() + "'").get(0);
                        TPatrimonioTransit pa1 = (TPatrimonioTransit) dao.findAll("from TPatrimonio where TBalancexmoneda='" + bx1.getIdbalance()
                                + "' and estado='ACTIVO" + op.getTPersonaCaja().getTCaja().getTFilial().getCodFilial() + "'").get(0);
                        if (resetedTransit && bx != null) {
                            bx.setActivoCajaybanco(bx.getActivoCajaybanco().add(c.getMontoRecibido()));
                            bx1.setActivoCajaybanco(bx1.getActivoCajaybanco().subtract(c.getMontoEntregado()));
                            dao.update();
                            pa.setPatrimonioActual(pa.getPatrimonioActual().add(c.getMontoRecibido()));
                            pa1.setPatrimonioActual(pa1.getPatrimonioActual().subtract(c.getMontoEntregado()));
                            dao.update();
                        }
                    } else {
                        dao.delete(op);
                    }
                    break;
                case 2:
                    List venta = dao.findAll("from TRegistroCompraVenta where TOperacion.idOperacion='" + idForInitNewestTable + "'");
                    if (venta.size() > 0) {
                        TRegistroCompraVenta v = (TRegistroCompraVenta) venta.iterator().next();
//                        String moneda1 = v.getTTipoCambio().getTMoneda().getCodMoneda();
//                        String moneda2 = v.getTTipoCambio().getCodMonedaA();
                        TMoneda mo = (TMoneda) dao.load(TMoneda.class, v.getTTipoCambio().getCodMonedaA());
//            String hql = iniDetalleCaja.detalleActivaCaja(myCodC, moneda1);
//                        TDetalleCaja detallecaja = iniDetalleCaja.detalleActivaCaja(op.getTPersonaCaja().getTCaja().getCodCaja(), moneda1, DateUtil.getDate(op.getFd()));
                        TDetalleCaja detallecaja = (TDetalleCaja) dao.load(TDetalleCaja.class, DateUtil.getDate(op.getFd()).replaceAll("/", "") + op.getTPersonaCaja().getTCaja().getCodCaja() + v.getTTipoCambio().getTMoneda().getCodMoneda() + "00");
                        detallecaja.setMontoFinal(detallecaja.getMontoFinal().add(v.getMontoRecibido()));
                        detallecaja.setMontoRecibido(detallecaja.getMontoRecibido().add(v.getMontoRecibido()));
                        dao.update();
//            String hql2 = iniDetalleCaja.detalleActivaCaja(myCodC, moneda2);
//                        TDetalleCaja detallecaja2 = iniDetalleCaja.detalleActivaCaja(op.getTPersonaCaja().getTCaja().getCodCaja(), moneda2, DateUtil.getDate(op.getFd()));
                        TDetalleCaja detallecaja2 = (TDetalleCaja) dao.load(TDetalleCaja.class, DateUtil.getDate(op.getFd()).replaceAll("/", "") + op.getTPersonaCaja().getTCaja().getCodCaja() + mo.getCodMoneda() + "00");
                        detallecaja2.setMontoFinal(detallecaja2.getMontoFinal().subtract(v.getMontoEntregado()));
                        detallecaja2.setMontoEntregado(detallecaja2.getMontoEntregado().add(v.getMontoEntregado()));
                        dao.update();
//                        bx = (TBalancexmoneda) dao.load(TBalancexmoneda.class, DateUtil.getDate(op.getFd()).replaceAll("/", "") + "0" + op.getTMoneda().getCodMoneda() + "0000000000");
                        bx1 = (TBalancexmoneda) dao.load(TBalancexmoneda.class, DateUtil.getDate(op.getFd()).replaceAll("/", "") + op.getTPersonaCaja().getTCaja().getCodCaja() + "100");
//                        pa = (TPatrimonio) dao.findAll("from TPatrimonio where TBalancexmoneda='" + bx.getIdbalance()
//                                + "' and estado='ACTIVO" + op.getTPersonaCaja().getTCaja().getTFilial().getCodFilial() + "'").get(0);
                        TPatrimonioTransit pa1 = (TPatrimonioTransit) dao.findAll("from TPatrimonio where TBalancexmoneda='" + bx1.getIdbalance()
                                + "' and estado='ACTIVO" + op.getTPersonaCaja().getTCaja().getTFilial().getCodFilial() + "'").get(0);
                        if (resetedTransit && bx != null) {
                            bx.setActivoCajaybanco(bx.getActivoCajaybanco().subtract(v.getMontoEntregado()));
                            bx1.setActivoCajaybanco(bx1.getActivoCajaybanco().add(v.getMontoRecibido()));
                            dao.update();
                            pa.setPatrimonioActual(pa.getPatrimonioActual().subtract(v.getMontoEntregado()));
                            pa1.setPatrimonioActual(pa1.getPatrimonioActual().add(v.getMontoRecibido()));
                            dao.update();
                        }
                    } else {
                        dao.delete(op);
                    }
                    break;
                case 3:
                    List deposito = dao.findAll("from TRegistroDepositoRetiro where TOperacion.idOperacion='" + idForInitNewestTable + "'");
                    if (deposito.size() > 0) {
                        TRegistroDepositoRetiro d = (TRegistroDepositoRetiro) deposito.iterator().next();
                        //TDetalleCaja dep = (TDetalleCaja) dao.load(TDetalleCaja.class, iniDetalleCaja.detalleActivaCaja(codCaja, codM));
                        TDetalleCaja dep = (TDetalleCaja) dao.load(TDetalleCaja.class, DateUtil.getDate(op.getFd()).replaceAll("/", "") + op.getTPersonaCaja().getTCaja().getCodCaja() + op.getTMoneda().getCodMoneda() + "00");
                        dep.setMontoFinal(dep.getMontoFinal().add(d.getImporte()));
                        dep.setMontoRecibido(dep.getMontoRecibido().add(d.getImporte()));
                        dep.setDateUser(DateUtil.getDate(op.getFd()));
                        dao.update();
                        if (resetedTransit && bx != null) {
                            bx.setActivoCajaybanco(bx.getActivoCajaybanco().add(d.getImporte()));
                            Double x = d.getTCuentaPersona().getSaldoSinInteres().doubleValue() - d.getImporte().doubleValue();
                            if (x < 0.0D) {
                                Double x1 = d.getImporte().doubleValue() - d.getTCuentaPersona().getSaldoSinInteres().doubleValue();
                                if (x1 > d.getImporte().doubleValue()) {
                                    //A=0
                                    bx.setActivoCuentaxcobrar(bx.getActivoCuentaxcobrar().subtract(d.getImporte()));
                                    bx.setEncaje(bx.getEncaje().subtract(d.getImporte()));
                                    pa.setPatrimonioActual(pa.getPatrimonioActual().add(d.getImporte()));
                                } else {
                                    //Importe=A+B
                                    bx.setPasivo(bx.getPasivo().add(d.getTCuentaPersona().getSaldoSinInteres()));
                                    bx.setActivoCuentaxcobrar(bx.getActivoCuentaxcobrar().subtract(new BigDecimal(x1)));
                                    bx.setEncaje(bx.getEncaje().subtract(new BigDecimal(x1)));
                                    pa.setPatrimonioActual(pa.getPatrimonioActual().add(new BigDecimal(x1)));
                                }
                            } else {
                                //B=0
                                bx.setPasivo(bx.getPasivo().add(d.getImporte()));
                            }
                            dao.update();
                        }
                    } else {
                        dao.delete(op);
                    }
                    break;
                case 4:
                    List retiro = dao.findAll("from TRegistroDepositoRetiro where TOperacion.idOperacion='" + idForInitNewestTable + "'");
                    if (retiro.size() > 0) {
                        TRegistroDepositoRetiro r = (TRegistroDepositoRetiro) retiro.iterator().next();
                        //                        TDetalleCaja dep = (TDetalleCaja) dao.load(TDetalleCaja.class, iniDetalleCaja.detalleActivaCaja(codCaja, codM));
                        TDetalleCaja dep = (TDetalleCaja) dao.load(TDetalleCaja.class, DateUtil.getDate(op.getFd()).replaceAll("/", "") + op.getTPersonaCaja().getTCaja().getCodCaja() + op.getTMoneda().getCodMoneda() + "00");

                        dep.setMontoFinal(dep.getMontoFinal().subtract(r.getImporte()));
                        dep.setMontoRecibido(dep.getMontoRecibido().subtract(r.getImporte()));
                        dep.setDateUser(DateUtil.getDate(op.getFd()));
                        dao.update();
//                        bx = (TBalancexmoneda) dao.load(TBalancexmoneda.class, DateUtil.getDate(op.getFd()).replaceAll("/", "") + "0" + op.getTMoneda().getCodMoneda() + "0000000000");
//                        pa = (TPatrimonio) dao.findAll("from TPatrimonio where TBalancexmoneda='" + bx.getIdbalance()
//                                + "' and estado='ACTIVO" + op.getTPersonaCaja().getTCaja().getTFilial().getCodFilial() + "'").get(0);
                        if (resetedTransit && bx != null) {
                            bx.setActivoCajaybanco(bx.getActivoCajaybanco().subtract(r.getImporte()));
//                            Double x = r.getTCuentaPersona().getSaldoSinInteres().doubleValue() + r.getImporte().doubleValue();
                            if (r.getTCuentaPersona().getSaldoSinInteres().doubleValue() < 0.0D) {
                                Double x1 = r.getImporte().doubleValue() + r.getTCuentaPersona().getSaldoSinInteres().doubleValue();
                                if (x1 < 0.0D) {
                                    //A=0
                                    bx.setActivoCuentaxcobrar(bx.getActivoCuentaxcobrar().add(r.getImporte()));
                                    bx.setEncaje(bx.getEncaje().add(r.getImporte()));
                                    pa.setPatrimonioActual(pa.getPatrimonioActual().subtract(r.getImporte()));
                                } else {
                                    //Importe=A+B
                                    bx.setPasivo(bx.getPasivo().subtract(new BigDecimal(x1)));
                                    bx.setActivoCuentaxcobrar(bx.getActivoCuentaxcobrar().add(r.getTCuentaPersona().getSaldoSinInteres()));
                                    bx.setEncaje(bx.getEncaje().add(r.getTCuentaPersona().getSaldoSinInteres()));
                                    pa.setPatrimonioActual(pa.getPatrimonioActual().subtract(r.getTCuentaPersona().getSaldoSinInteres()));
                                }
                            } else {
                                //B=0
                                bx.setPasivo(bx.getPasivo().subtract(r.getImporte()));
                            }
                            dao.update();
                        }
                    } else {
                        dao.delete(op);
                    }
                    break;
                case 5:
                    List cgiro = dao.findAll("from TRegistroGiro where TOperacion.idOperacion='" + idForInitNewestTable + "'");
                    if (cgiro.size() < 1) {
                        dao.delete(op);
                    }
                    break;
                case 6:
                    List rpr = dao.findAll("from TRegistroPrestamo where TOperacion.idOperacion='" + idForInitNewestTable + "'");
                    if (rpr.size() > 0) {
                        TRegistroPrestamo rp = (TRegistroPrestamo) rpr.iterator().next();
//                        TDetalleCaja detaCaja = iniDetalleCaja.detalleActivaCaja(op.getTPersonaCaja().getTCaja().getCodCaja(), op.getTMoneda().getCodMoneda(), DateUtil.getDate(op.getFd()));
                        TDetalleCaja detaCaja = (TDetalleCaja) dao.load(TDetalleCaja.class, DateUtil.getDate(op.getFd()).replaceAll("/", "") + op.getTPersonaCaja().getTCaja().getCodCaja() + op.getTMoneda().getCodMoneda() + "00");
                        detaCaja.setMontoFinal(detaCaja.getMontoFinal().subtract(rp.getMonto()));
                        detaCaja.setMontoEntregado(detaCaja.getMontoEntregado().add(rp.getMonto()));
                        dao.update();
                        if (resetedTransit && bx != null) {
                            bx.setActivoCajaybanco(bx.getActivoCajaybanco().subtract(rp.getMonto()));
                            bx.setActivoCuentaxcobrar(bx.getActivoCuentaxcobrar().add(rp.getMonto()));
                            pa.setPatrimonioActual(pa.getPatrimonioActual().subtract(rp.getMonto()));
                            bx.setPRespaldo(bx.getPRespaldo().add(rp.getMonto()));
                            dao.update();
                        }
                    } else {
                        dao.delete(op);
                    }
                    break;
                case 7:
                    List c = dao.findAll("from TCobranza where TOperacion.idOperacion='" + idForInitNewestTable + "'");
                    if (c.size() > 0) {
                        TCobranza xc = (TCobranza) c.iterator().next();
//                         iniDetalleCaja.detalleActivaCaja(op.getTPersonaCaja().getTCaja().getCodCaja(), op.getTMoneda().getCodMoneda(), DateUtil.getDate(op.getFd()));
                        TDetalleCaja detaCaja = (TDetalleCaja) dao.load(TDetalleCaja.class, DateUtil.getDate(op.getFd()).replaceAll("/", "") + op.getTPersonaCaja().getTCaja().getCodCaja() + op.getTMoneda().getCodMoneda() + "00");
                        detaCaja.setMontoFinal(detaCaja.getMontoFinal().add(xc.getTDetallePrestamo().getMontoTotal()));
                        detaCaja.setMontoInicial(detaCaja.getMontoRecibido().add(xc.getTDetallePrestamo().getMontoTotal()));
                        dao.update();
                        if (resetedTransit && bx != null) {
                            bx.setActivoCajaybanco(bx.getActivoCajaybanco().add(xc.getTDetallePrestamo().getMontoTotal()));
                            bx.setActivoCuentaxcobrar(bx.getActivoCuentaxcobrar().subtract(xc.getTDetallePrestamo().getMontoCapital()));
                            pa.setPatrimonioActual(pa.getPatrimonioActual().subtract(xc.getTDetallePrestamo().getMontoTotal()));
                            bx.setPRespaldo(bx.getPRespaldo().add(xc.getTDetallePrestamo().getMontoCapital()));
                            dao.update();
                        }
                    } else {
                        dao.delete(op);
                    }
                    break;
                case 8:
                    List giro_cobro = dao.findAll("from TRegistroGiro where idoperacioncobro='" + idForInitNewestTable + "'");
                    if (giro_cobro.size() < 1) {
                        dao.delete(op);
                    }
                    break;
                case 9:
                    List ttc = dao.findAll("from TTransferenciaCaja where TOperacion.idOperacion='" + idForInitNewestTable + "'");
                    if (ttc.size() > 0) {
                        TTransferenciaCaja xt = (TTransferenciaCaja) ttc.iterator().next();
//                        TDetalleCaja actual = iniDetalleCaja.detalleActivaCaja(xt.getTCaja().getCodCaja(), op.getTMoneda().getCodMoneda(), DateUtil.getDate(op.getFd()));
                        TDetalleCaja actual = (TDetalleCaja) dao.load(TDetalleCaja.class, DateUtil.getDate(op.getFd()).replaceAll("/", "") + xt.getTCaja().getCodCaja() + op.getTMoneda().getCodMoneda() + "00");
                        actual.setMontoFinal(new BigDecimal(actual.getMontoFinal().doubleValue() - xt.getMonto().doubleValue()));
                        actual.setDateUser(DateUtil.getDate(op.getFd()));
                        dao.update();
//                        TDetalleCaja actualD = iniDetalleCaja.detalleActivaCaja(xt.getCodCajaDestino(), op.getTMoneda().getCodMoneda(), DateUtil.getDate(op.getFd()));
                        TDetalleCaja actualD = (TDetalleCaja) dao.load(TDetalleCaja.class, DateUtil.getDate(op.getFd()).replaceAll("/", "") + xt.getCodCajaDestino() + op.getTMoneda().getCodMoneda() + "00");
                        if (op.getTPersonaCaja().getTCaja().getCodCaja().endsWith("CA001")) {
                            actualD.setMontoInicial(new BigDecimal(actualD.getMontoInicial().doubleValue() + xt.getMonto().doubleValue()));
                        }
                        actualD.setMontoFinal(new BigDecimal(actualD.getMontoFinal().doubleValue() + xt.getMonto().doubleValue()));
                        actualD.setDateUser(DateUtil.getDate(op.getFd()));
                        dao.update();
                    } else {
                        dao.delete(op);
                    }
                    break;
                case 10:
                    List ro = dao.findAll("from TRegistroOtros where TOperacion.idOperacion='" + idForInitNewestTable + "'");
                    if (ro.size() > 0) {
                        TRegistroOtros xro = (TRegistroOtros) ro.iterator().next();
//                        TDetalleCaja detallecaja = iniDetalleCaja.detalleActivaCaja(op.getTPersonaCaja().getTCaja().getCodCaja(), op.getTMoneda().getCodMoneda(), DateUtil.getDate(op.getFd()));
                        TDetalleCaja detallecaja = (TDetalleCaja) dao.load(TDetalleCaja.class, DateUtil.getDate(op.getFd()).replaceAll("/", "") + op.getTPersonaCaja().getTCaja().getCodCaja() + op.getTMoneda().getCodMoneda() + "00");
                        detallecaja.setMontoFinal(detallecaja.getMontoFinal().subtract(xro.getMonto()));
                        detallecaja.setMontoEntregado(detallecaja.getMontoEntregado().add(xro.getMonto()));
                        detallecaja.setDateUser(DateUtil.getDate(op.getFd()));
                        dao.update();
                        if (resetedTransit && bx != null) {
                            bx.setActivoCajaybanco(bx.getActivoCajaybanco().subtract(xro.getMonto()));
                            pa.setPatrimonioActual(pa.getPatrimonioActual().subtract(xro.getMonto()));
                            dao.update();
                        }
                    } else {
                        dao.delete(op);
                    }
                    break;
                case 11:
                    List tro = dao.findAll("from TRegistroDepositoRetiro where TOperacion.idOperacion='" + idForInitNewestTable + "'");
                    if (tro.size() > 0) {
                        TRegistroDepositoRetiro xr = (TRegistroDepositoRetiro) tro.iterator().next();
                        if (resetedTransit && bx != null) {
                            bx.setPasivo(bx.getPasivo().add(xr.getImporte()));
                            pa.setPatrimonioActual(pa.getPatrimonioActual().subtract(xr.getImporte()));
                            dao.update();
                        }
                    } else {
                        dao.delete(op);
                    }
                    break;
                case 12:
                    List tr = dao.findAll("from TTransferenciaCaja where TOperacion.idOperacion='" + idForInitNewestTable + "'");
                    if (tr.size() < 1) {
                        dao.delete(op);
                    }
                    break;
                case 13:
                    List rac = dao.findAll("from TTransferenciaCaja where TOperacion.idOperacion='" + idForInitNewestTable + "'");
                    if (rac.size() > 0) {
                        TTransferenciaCaja ac = (TTransferenciaCaja) rac.iterator().next();
                        //TDetalleCaja xd = iniDetalleCaja.detalleActivaCaja(op.getTPersonaCaja().getTCaja().getCodCaja(), op.getTMoneda().getCodMoneda(), DateUtil.getDate(op.getFd()));
                        TLogFinance xd = (TLogFinance) dao.load(TLogFinance.class, "LOG" + op.getTPersonaCaja().getTCaja().getCodCaja() + op.getTMoneda().getCodMoneda());
                        xd.setMontoRecibido(xd.getMontoRecibido().add(ac.getMonto()));
                        xd.setMontoFinal(xd.getMontoFinal().add(ac.getMonto()));
                        //xd.setDateUser(DateUtil.getDate(op.getFd()));
                        dao.update();
                        if (resetedTransit && bx != null) {
                            bx.setActivoCajaybanco(bx.getActivoCajaybanco().add(ac.getMonto()));
                            pa.setPatrimonioActual(pa.getPatrimonioActual().add(ac.getMonto()));
                            dao.update();
                        }
                    } else {
                        dao.delete(op);
                    }
                    break;
                case 14:
                    List rtf = dao.findAll("from TTransferenciaCaja where TOperacion.idOperacion='" + idForInitNewestTable + "'");
                    if (rtf.size() > 0) {
                        TTransferenciaCaja tf = (TTransferenciaCaja) rtf.iterator().next();
                        TDetalleCaja detallCajap = (TDetalleCaja) dao.load(TDetalleCaja.class, DateUtil.getDate(op.getFd()).replaceAll("/", "") + op.getTPersonaCaja().getTCaja().getTFilial().getCodFilial() + "CA001" + op.getTMoneda().getCodMoneda() + "00");
                        detallCajap.setMontoFinal(detallCajap.getMontoFinal().add(tf.getMonto()));
                        detallCajap.setMontoRecibido(detallCajap.getMontoRecibido().add(tf.getMonto()));
                        dao.update();
                        TDetalleCaja mica = (TDetalleCaja) dao.load(TDetalleCaja.class, DateUtil.getDate(new Date()).replaceAll("/", "") + op.getTPersonaCaja().getTCaja().getCodCaja() + op.getTMoneda().getCodMoneda() + "00");
                        mica.setMontoEntregado(mica.getMontoEntregado().add(tf.getMonto()));
                        mica.setMontoFinal(mica.getMontoFinal().subtract(tf.getMonto()));
                        dao.update();
                        TPatrimonioTransit pa1 = (TPatrimonioTransit) dao.findAll("from TPatrimonio where TBalancexmoneda='" + bx1.getIdbalance()
                                + "' and estado='ACTIVO" + tf.getCodCajaDestino().substring(0, 4) + "'").get(0);
                        if (resetedTransit && bx != null) {
                            pa.setPatrimonioActual(pa.getPatrimonioActual().subtract(tf.getMonto()));
                            pa1.setPatrimonioActual(pa1.getPatrimonioActual().add(tf.getMonto()));
                            dao.update();
                        }
                    } else {
                        dao.delete(op);
                    }
                    break;
                default:
                    Logger.getLogger(DeleteData.class.getName()).log(Level.INFO, "Nada");
            }
        }
    }

    private static void revisarRecepcion(TOperacion op, DAOGeneral dao) {
        List result = dao.findAll("from TMoneda where estado ='ACTIVO'");
        int sy = result.size();
        TCaja caja = (TCaja) dao.load(TCaja.class, op.getTPersonaCaja().getTCaja().getCodCaja());
        if (caja != null) {
            if (sy > 0) {
                Iterator itPmoneda = result.iterator();
                while (itPmoneda.hasNext()) {
                    TMoneda xmo = (TMoneda) itPmoneda.next();
                    String idForInitNewesttable = DateUtil.getDate(op.getFd()).replaceAll("/", "") + op.getTPersonaCaja().getTCaja().getCodCaja() + xmo.getCodMoneda() + "00";
                    if ("ACTIVO".equals(xmo.getEstado())) {
                        TDetalleCaja b = (TDetalleCaja) dao.load(TDetalleCaja.class, idForInitNewesttable);
                        if (b == null) {
                            String idForInit = DateUtil.convertDateId(op.getTPersonaCaja().getIdpersonacaja(), DeleteData.class.getSimpleName());
                            TDetalleCaja detallecaja = (TDetalleCaja) dao.load(TDetalleCaja.class, DateUtil.getDateAnt(op.getFd()).replaceAll("/", "") + op.getTPersonaCaja().getTCaja().getCodCaja() + xmo.getCodMoneda() + "00");
                            if (detallecaja == null) {
                                detallecaja = new TDetalleCaja(idForInitNewesttable, caja,
                                        xmo, DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", op.getFd()), "CUADRADAJE" + " PRIMERA VEZ", "CUADRAJE", DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", op.getFd()));
                                detallecaja.setMontoInicial(BigDecimal.ZERO);
                                detallecaja.setMontoFinal(BigDecimal.ZERO);
                                detallecaja.setMontoEntregado(BigDecimal.ZERO);
                                detallecaja.setMontoRecibido(BigDecimal.ZERO);
                                detallecaja.setEstado("ACTIVO");
                                dao.persist(detallecaja);
                                TOperacion recep = new TOperacion();
                                recep.setIdOperacion(idForInit);
                                recep.setDateUser(DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", op.getFd()));
                                recep.setDescripcion("TRASLADO DEL CAJA ANTERIOR");
                                recep.setEstado("ACTIVO");
                                recep.setFd(op.getFd());
                                recep.setFecha(DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", op.getFd()));
                                recep.setFechaExtornacion(null);
                                recep.setIdAdminExtorno(null);
                                TPersonaCaja pcaja = (TPersonaCaja) dao.load(TPersonaCaja.class, op.getTPersonaCaja().getIdpersonacaja());
                                recep.setTPersonaCaja(pcaja);
                                recep.setIdUser("CUADRAJE");
                                recep.setIpUser("CUADRAJE");
                                recep.setNumeroOperacion(numeroOperacion.getNumber(caja.getTFilial().getCodFilial(), op.getTPersonaCaja().getTCaja().getCodCaja()));
                                recep.setSaldofinal(detallecaja.getMontoFinal());
                                recep.setSaldoFinalSinInteres(detallecaja.getMontoFinal());
                                recep.setTMoneda(xmo);
                                TTipoOperacion tipoOper = (TTipoOperacion) dao.load(TTipoOperacion.class, "TIPC12");
                                recep.setTTipoOperacion(tipoOper);
                                recep.setFd(op.getFd());
                                dao.persist(recep);
                                TTransferenciaCaja nTransf = new TTransferenciaCaja();
                                nTransf.setIdtransferenciacaja(idForInit);
                                nTransf.setFecha(DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", op.getFd()));
                                nTransf.setMonto(detallecaja.getMontoFinal());
                                nTransf.setIdOperacion(xmo.getCodMoneda());
                                nTransf.setDescripcion("* TRASLADO DE INICIAL " + DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", op.getFd()));
                                nTransf.setCodCajaDestino(caja.getCodCaja());
                                nTransf.setTCaja(pcaja.getTCaja());
                                nTransf.setTipo("TRASLADO");
                                nTransf.setTOperacion(recep);
                                nTransf.setIdUser("CUADRAJE" + " TRASLADO");
                                nTransf.setIpUser("CUADRAJE");
                                nTransf.setEstado("ACTIVO");
                                nTransf.setDateUser(DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", op.getFd()));
                                nTransf.setIdope(idForInitNewesttable);
                                dao.persist(nTransf);
                            } else {
                                TDetalleCaja detAnt = detallecaja;
                                detallecaja = new TDetalleCaja(idForInitNewesttable, caja,
                                        xmo, DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", op.getFd()), detAnt.getIddetallecaja() + " TRASLADO", "CUADRAJE", DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", op.getFd()));
                                detallecaja.setMontoInicial(detAnt.getMontoFinal());
                                detallecaja.setMontoFinal(detAnt.getMontoFinal());
                                detallecaja.setMontoRecibido(BigDecimal.ZERO);
                                detallecaja.setMontoEntregado(BigDecimal.ZERO);
                                detallecaja.setEstado("ACTIVO");
                                dao.persist(detallecaja);
                                TOperacion recep = new TOperacion();
                                recep.setIdOperacion(idForInit);
                                recep.setDateUser(DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", op.getFd()));
                                recep.setDescripcion("TRASLADO DEL CAJA ANTERIOR");
                                recep.setEstado("ACTIVO");
                                recep.setFd(op.getFd());
                                recep.setFecha(DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", op.getFd()));
                                recep.setFechaExtornacion(null);
                                recep.setIdAdminExtorno(null);
                                TPersonaCaja pcaja = (TPersonaCaja) dao.load(TPersonaCaja.class, op.getTPersonaCaja().getIdpersonacaja());
                                recep.setTPersonaCaja(pcaja);
                                recep.setIdUser("CUADRAJE EXPLICIT");
                                recep.setIpUser("CUADRAJE");
                                recep.setNumeroOperacion(numeroOperacion.getNumber(caja.getTFilial().getCodFilial(), op.getTPersonaCaja().getTCaja().getCodCaja()));
                                recep.setSaldofinal(detallecaja.getMontoFinal());
                                recep.setSaldoFinalSinInteres(detallecaja.getMontoFinal());
                                recep.setTMoneda(xmo);
                                TTipoOperacion tipoOper = (TTipoOperacion) dao.load(TTipoOperacion.class, "TIPC12");
                                recep.setTTipoOperacion(tipoOper);
                                recep.setFd(op.getFd());
                                dao.persist(recep);
                                TTransferenciaCaja nTransf = new TTransferenciaCaja();
                                nTransf.setIdtransferenciacaja(idForInit);
                                nTransf.setFecha(DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", op.getFd()));
                                nTransf.setMonto(detallecaja.getMontoFinal());
                                nTransf.setIdOperacion(xmo.getCodMoneda());
                                nTransf.setDescripcion("* TRASLADO DE INICIAL " + DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", op.getFd()));
                                nTransf.setCodCajaDestino(caja.getCodCaja());
                                nTransf.setTCaja(pcaja.getTCaja());
                                nTransf.setTipo("TRASLADO");
                                nTransf.setTOperacion(recep);
                                nTransf.setIdUser("CUADRAJE EXPLICIT" + " TRASLADO");
                                nTransf.setIpUser("CUADRAJE");
                                nTransf.setEstado("ACTIVO");
                                nTransf.setDateUser(DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", op.getFd()));
                                nTransf.setIdope(idForInitNewesttable);
                                dao.persist(nTransf);
                            }
                        }
                        caja = (TCaja) dao.load(TCaja.class, caja.getTFilial().getCodFilial() + "CA001");
                        TDetalleCaja l = (TDetalleCaja) dao.load(TDetalleCaja.class, DateUtil.getDate(new Date()).replaceAll("/", "") + caja.getTFilial().getCodFilial() + "CA001" + xmo.getCodMoneda() + "00");
                        if (l == null) {
                            TDetalleCaja detallecaja = (TDetalleCaja) dao.load(TDetalleCaja.class, DateUtil.getDateAnt(new Date()).replaceAll("/", "") + caja.getTFilial().getCodFilial() + "CA001" + xmo.getCodMoneda() + "00");
                            String idForInit = DateUtil.convertDateId(op.getTPersonaCaja().getIdpersonacaja(), DeleteData.class.getSimpleName());
                            if (detallecaja == null) {
                                detallecaja = new TDetalleCaja(DateUtil.getDate(new Date()).replaceAll("/", "") + caja.getTFilial().getCodFilial() + "CA001" + xmo.getCodMoneda() + "00", caja,
                                        xmo, DateUtil.getNOW_S(), "IDDE" + " PRIMERA VEZ", "IPDE", DateUtil.getNOW_S());
                                detallecaja.setMontoInicial(BigDecimal.ZERO);
                                detallecaja.setMontoFinal(BigDecimal.ZERO);
                                detallecaja.setMontoEntregado(BigDecimal.ZERO);
                                detallecaja.setMontoRecibido(BigDecimal.ZERO);
                                detallecaja.setEstado("ACTIVO");
                                dao.persist(detallecaja);
                                TOperacion recep = new TOperacion();
                                recep.setIdOperacion(idForInit);
                                recep.setDateUser(DateUtil.getNOW_S());
                                recep.setDescripcion("TRASLADO DEL CAJA ANTERIOR");
                                recep.setEstado("ACTIVO");
                                recep.setFd(new Date());
                                recep.setFecha(DateUtil.getNOW_S());
                                recep.setFechaExtornacion(null);
                                recep.setIdAdminExtorno(null);
                                TPersonaCaja pcaja = (TPersonaCaja) dao.load(TPersonaCaja.class, op.getTPersonaCaja().getIdpersonacaja());
                                recep.setTPersonaCaja(pcaja);
                                recep.setIdUser("IDDE");
                                recep.setIpUser("IPDE");
                                recep.setNumeroOperacion(numeroOperacion.getNumber(caja.getTFilial().getCodFilial(), caja.getCodCaja()));
                                recep.setSaldofinal(detallecaja.getMontoFinal());
                                recep.setSaldoFinalSinInteres(detallecaja.getMontoFinal());
                                recep.setTMoneda(xmo);
                                TTipoOperacion tipoOper = (TTipoOperacion) dao.load(TTipoOperacion.class, "TIPC12");
                                recep.setTTipoOperacion(tipoOper);
                                recep.setFd(new Date());
                                dao.persist(recep);
                                TTransferenciaCaja nTransf = new TTransferenciaCaja();
                                nTransf.setIdtransferenciacaja(idForInit);
                                nTransf.setFecha(DateUtil.getNOW_S());
                                nTransf.setMonto(detallecaja.getMontoFinal());
                                nTransf.setIdOperacion(xmo.getCodMoneda());
                                nTransf.setDescripcion("* TRASLADO DE INICIAL " + DateUtil.getNOW_S());
                                nTransf.setCodCajaDestino(caja.getCodCaja());
                                nTransf.setTCaja(pcaja.getTCaja());
                                nTransf.setTipo("TRASLADO");
                                nTransf.setTOperacion(recep);
                                nTransf.setIdUser("IDDE" + " TRASLADO");
                                nTransf.setIpUser("IPDE");
                                nTransf.setEstado("ACTIVO");
                                nTransf.setDateUser(DateUtil.getNOW_S());
                                nTransf.setIdope(idForInitNewesttable);
                                dao.persist(nTransf);
                            } else {
                                TDetalleCaja detAnt = detallecaja;
                                detallecaja = new TDetalleCaja(idForInitNewesttable, caja,
                                        xmo, DateUtil.getNOW_S(), detAnt.getIddetallecaja() + " TRASLADO", "IDDE", DateUtil.getNOW_S());
                                detallecaja.setMontoInicial(detAnt.getMontoFinal());
                                detallecaja.setMontoFinal(detAnt.getMontoFinal());
                                detallecaja.setMontoRecibido(BigDecimal.ZERO);
                                detallecaja.setMontoEntregado(BigDecimal.ZERO);
                                detallecaja.setEstado("ACTIVO");
                                dao.persist(detallecaja);
                                TOperacion recep = new TOperacion();
                                recep.setIdOperacion(idForInit);
                                recep.setDateUser(DateUtil.getNOW_S());
                                recep.setDescripcion("TRASLADO DEL CAJA ANTERIOR");
                                recep.setEstado("ACTIVO");
                                recep.setFd(new Date());
                                recep.setFecha(DateUtil.getNOW_S());
                                recep.setFechaExtornacion(null);
                                recep.setIdAdminExtorno(null);
                                TPersonaCaja pcaja = (TPersonaCaja) dao.load(TPersonaCaja.class, op.getTPersonaCaja().getIdpersonacaja());
                                recep.setTPersonaCaja(pcaja);
                                recep.setIdUser("IDDE");
                                recep.setIpUser("IPDE");
                                recep.setNumeroOperacion(numeroOperacion.getNumber(caja.getTFilial().getCodFilial(), caja.getCodCaja()));
                                recep.setSaldofinal(detallecaja.getMontoFinal());
                                recep.setSaldoFinalSinInteres(detallecaja.getMontoFinal());
                                recep.setTMoneda(xmo);
                                TTipoOperacion tipoOper = (TTipoOperacion) dao.load(TTipoOperacion.class, "TIPC12");
                                recep.setTTipoOperacion(tipoOper);
                                recep.setFd(new Date());
                                dao.persist(recep);
                                TTransferenciaCaja nTransf = new TTransferenciaCaja();
                                nTransf.setIdtransferenciacaja(idForInit);
                                nTransf.setFecha(DateUtil.getNOW_S());
                                nTransf.setMonto(detallecaja.getMontoFinal());
                                nTransf.setIdOperacion(xmo.getCodMoneda());
                                nTransf.setDescripcion("* TRASLADO DE INICIAL " + DateUtil.getNOW_S());
                                nTransf.setCodCajaDestino(caja.getCodCaja());
                                nTransf.setTCaja(pcaja.getTCaja());
                                nTransf.setTipo("TRASLADO");
                                nTransf.setTOperacion(recep);
                                nTransf.setIdUser("IDDE" + " TRASLADO");
                                nTransf.setIpUser("IPDE");
                                nTransf.setEstado("ACTIVO");
                                nTransf.setDateUser(DateUtil.getNOW_S());
                                nTransf.setIdope(idForInitNewesttable);
                                dao.persist(nTransf);
                            }
                        }
                    }
                }
            }
        }
    }
}

