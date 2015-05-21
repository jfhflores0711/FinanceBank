package org.finance.bank.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.finance.bank.bean.TBalancexmoneda;
import org.finance.bank.bean.TCaja;
import org.finance.bank.bean.TMoneda;
import org.finance.bank.bean.TPatrimonioTransit;
import org.finance.bank.model.dao.DAOGeneral;
import org.finance.bank.util.DateUtil;

/**
 *
 * @author Administrador
 */
public class iniPatrimonio {

    public static synchronized String comprobarIni() {
        String r = "OK";
        String g = DateUtil.getDate(new Date()).replaceAll("/", "");
        DAOGeneral dg = new DAOGeneral();
        List result = dg.findAll("from TMoneda where estado ='ACTIVO'");
        Iterator itPmoneda = result.iterator();
        while (itPmoneda.hasNext()) {
            TMoneda moneda = (TMoneda) itPmoneda.next();
            List l = dg.findAll("from TCaja where estado = 'ACTIVO'");
            if (l.isEmpty()) {
                return "Error de Lectura de BD.";
            }
            Iterator f = l.iterator();
            while (f.hasNext()) {
                TCaja c = (TCaja) f.next();
                String idForInitNewesttable = g + c.getCodCaja() + moneda.getCodParaNumCuenta() + "00";
                //TBalancexmoneda actual = (TBalancexmoneda) dg.load(TBalancexmoneda.class, idForInitNewesttable + "");
                List baList = dg.findAll("from TBalancexmoneda where TMoneda.codMoneda='" + moneda.getCodMoneda() + "' and idbalance='%" + c.getCodCaja() + "%'");
                if (baList.isEmpty()) {
                    TBalancexmoneda h = (TBalancexmoneda) dg.load(TBalancexmoneda.class, idForInitNewesttable);
                    if (h == null) {
                        h = new TBalancexmoneda(idForInitNewesttable, moneda, BigDecimal.ZERO,
                                BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
                                "TRANSIT", "ADMIN", "LOCAL", DateUtil.getNOW_S());
                        h.setFd(new Date());
                        dg.persist(h);
                        TPatrimonioTransit p = new TPatrimonioTransit(idForInitNewesttable, h, BigDecimal.ZERO,
                                BigDecimal.ZERO, "ACTIVO" + c.getTFilial().getCodFilial(), "ADMIN", "LOCAL", DateUtil.getNOW_S());
                        p.setTipoVariacion("INICIAL");
                        dg.persist(p);
                    }

                } else {
//public TBalancexmoneda(String idbalance, TMoneda TMoneda, BigDecimal activoCajaybanco,
//BigDecimal activoCuentaxcobrar, BigDecimal pasivo, BigDecimal encaje, BigDecimal PRespaldo,
//String estado, String idUser, String ipUser, String dateUser)
                    TBalancexmoneda bx = (TBalancexmoneda) baList.get(0);
                    TPatrimonioTransit j = (TPatrimonioTransit) bx.getTPatrimonioTransits().iterator().next();

                    TBalancexmoneda h = (TBalancexmoneda) dg.load(TBalancexmoneda.class, idForInitNewesttable);
                    if (h == null) {
                        //public TBalancexmoneda(String idbalance, TMoneda TMoneda,
                        //BigDecimal activoCajaybanco, BigDecimal activoCuentaxcobrar,
                        //BigDecimal pasivo, BigDecimal encaje, BigDecimal PRespaldo,
                        //String estado, String idUser, String ipUser, String dateUser)
                        h = new TBalancexmoneda(idForInitNewesttable, moneda, j.getTBalancexmoneda().getActivoCajaybanco(),
                                j.getTBalancexmoneda().getActivoCuentaxcobrar(), j.getTBalancexmoneda().getPasivo(), j.getTBalancexmoneda().getEncaje(), j.getTBalancexmoneda().getPRespaldo(),
                                "TRANSIT", "ADMIN", "LOCAL", DateUtil.getNOW_S());
                        h.setFd(new Date());
                        dg.persist(h);
                        TPatrimonioTransit d = new TPatrimonioTransit(idForInitNewesttable, h, j.getPatrimonio(),
                                j.getPatrimonioActual(), "ACTIVO" + c.getTFilial().getCodFilial(), "ADMIN", "LOCAL", DateUtil.getNOW_S());
                        d.setTipoVariacion("INICIAL");
                        dg.persist(d);
                    }
                }
            }
        }//fin monedas
        dg.cerrarSession();
        return r;
    }

    /**
     *  Este método regresa el Patrimonio actual para actualizar las transacciones del día
     * @param a Valor que representa al código de Caja
     * @param b Valor que representa al código de Moneda
     * @return  El patrimonio seleccionado
     */
    public static synchronized String patActual(String a, String b) {
        TPatrimonioTransit c = null;
        DAOGeneral d = new DAOGeneral();
        TMoneda e = (TMoneda) d.load(TMoneda.class, b);
        TCaja f = (TCaja) d.load(TCaja.class, a);
        String g = DateUtil.getDate(new Date()).replaceAll("/", "");
        if (e != null && f != null) {
            String i = e.getCodParaNumCuenta();
            c = (TPatrimonioTransit) d.load(TPatrimonioTransit.class, g + a + i + "00");
            if (c == null) {
                c = _h(a, e, g);
            }
        }
        d.cerrarSession();
        return c.getIdpatrimonio();
    }

    private static TPatrimonioTransit _h(String a, TMoneda b, String g) {
        TPatrimonioTransit c = null;
        String idForInitNewesttable = g + a + b.getCodParaNumCuenta() + "00";
            DAOGeneral d = new DAOGeneral();
        c = (TPatrimonioTransit) d.load(TPatrimonioTransit.class, idForInitNewesttable);
        if (c == null) {
            List f = d.findAll("from TPatrimonioTransit where idpatrimonio like '%" + a + "%' and TBalancexmoneda.TMoneda.codMoneda='" + b.getCodMoneda() + "' order by dateUser desc");
            if (f.isEmpty()) {
                TBalancexmoneda h = (TBalancexmoneda) d.load(TBalancexmoneda.class, idForInitNewesttable);
                if (h == null) {
                    h = new TBalancexmoneda(idForInitNewesttable, b, BigDecimal.ZERO,
                            BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
                            "TRANSIT", "ADMIN", "LOCAL", DateUtil.getNOW_S());
                    h.setFd(new Date());
                    d.persist(h);
                    c = new TPatrimonioTransit(idForInitNewesttable, h, BigDecimal.ZERO,
                            BigDecimal.ZERO, "ACTIVO" + a.substring(0, 4), "ADMIN", "LOCAL", DateUtil.getNOW_S());
                    c.setTipoVariacion("INICIAL");
                    d.persist(c);
                }
            } else {
                TPatrimonioTransit j = (TPatrimonioTransit) f.get(0);
                TBalancexmoneda h = (TBalancexmoneda) d.load(TBalancexmoneda.class, idForInitNewesttable);
                if (h == null) {
                    //public TBalancexmoneda(String idbalance, TMoneda TMoneda,
                    //BigDecimal activoCajaybanco, BigDecimal activoCuentaxcobrar,
                    //BigDecimal pasivo, BigDecimal encaje, BigDecimal PRespaldo,
                    //String estado, String idUser, String ipUser, String dateUser)
                    h = new TBalancexmoneda(idForInitNewesttable, b, j.getTBalancexmoneda().getActivoCajaybanco(),
                            j.getTBalancexmoneda().getActivoCuentaxcobrar(), j.getTBalancexmoneda().getPasivo(), j.getTBalancexmoneda().getEncaje(), j.getTBalancexmoneda().getPRespaldo(),
                            "TRANSIT", "ADMIN", "LOCAL", DateUtil.getNOW_S());
                    h.setFd(new Date());
                    d.persist(h);
                    c = new TPatrimonioTransit(idForInitNewesttable, h, j.getPatrimonio(),
                            j.getPatrimonioActual(), "ACTIVO" + a.substring(0, 4), "ADMIN", "LOCAL", DateUtil.getNOW_S());
                    c.setTipoVariacion("INICIAL");
                    d.persist(c);
                }
            }
        }
        d.cerrarSession();
        return c;
    }
}
